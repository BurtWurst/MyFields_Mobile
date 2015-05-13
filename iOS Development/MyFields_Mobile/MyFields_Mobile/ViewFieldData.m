//
//  ViewFieldData.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "ViewFieldData.h"
#import "MyFields.h"
#import "FieldItem.h"

@interface ViewFieldData ()

@end

@implementation ViewFieldData

@synthesize pointerToMyFields;
@synthesize selectedFieldList;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self loadInitialData];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/**
 Initializes the selectedFieldList array and sets multiple strings equal to the values stored in the field object.
 This method needs to be finished to allow the field objects field plantings list and pest samples list to be displayed in new list views. 
 */
- (void)loadInitialData{
    
    selectedFieldList = [[NSMutableArray alloc] init];
    NSString *selectedFieldName = pointerToMyFields.shareFieldObject.fieldName;
    //NSString *selectedFieldLocation = pointerToMyFields.shareFieldObject.location;
    NSString *selectedFieldLocation = @"";
    NSString *selectedFieldSize = pointerToMyFields.shareFieldObject.size;
    NSString *selectedFieldSoil = pointerToMyFields.shareFieldObject.typeOfSoil;
    NSString *selectedFieldTillage = pointerToMyFields.shareFieldObject.tillageSystem;
    NSString *selectedFieldIrrigation = pointerToMyFields.shareFieldObject.irrigationSystem;
    NSMutableArray *selectedFieldPlantingList = pointerToMyFields.shareFieldObject.plantingList;
    //NSString *selectedFieldPlantingList = @"";
    NSMutableArray *selectedFieldPestSamples = pointerToMyFields.shareFieldObject.pestSamples;
    //NSString *selectedFieldPestSamples = @"";
    
    NSString *fName = @"Field Name:  ";
    NSString *fLocation = @"Field Location:  ";
    NSString *fSize = @"Field Size:  ";
    NSString *fSoil = @"Field Soil:  ";
    NSString *fTillage = @"Field Tillage:  ";
    NSString *fIrrigation = @"Field Irrigation:  ";
    NSString *fPlantings = @"Field Planting:  ";
    NSString *fPests = @"Field Pest Samples:  ";
    
    NSString *catName = [fName stringByAppendingString:selectedFieldName];
    NSString *catLocation = [fLocation stringByAppendingString:selectedFieldLocation];
    NSString *catSize = [fSize stringByAppendingString:selectedFieldSize];
    NSString *catSoil = [fSoil stringByAppendingString:selectedFieldSoil];
    NSString *catTillage = [fTillage stringByAppendingString:selectedFieldTillage];
    NSString *catIrrigation = [fIrrigation stringByAppendingString:selectedFieldIrrigation];
    //NSString *catPlantings = [fPlantings stringByAppendingString:selectedFieldPlantingList];
    //NSString *catPests = [fPests stringByAppendingString:selectedFieldPestSamples];
    
    selectedFieldList = [NSMutableArray arrayWithObjects:catName, catLocation, catSize, catSoil, catTillage, catIrrigation, fPlantings, fPests, nil];

    //Reload our table view
    [self.tableView reloadData];
    
     //NSLog(@"requestReply: %@", requestReply);
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    //Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    //Return the number of rows in the section.
    return [selectedFieldList count];
}

/**
 Sets each cell in the table equal to the strings stored in the selectedFieldList array.
 */
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"SelectedFieldPrototypeCell" forIndexPath:indexPath];
    
    cell.textLabel.text = selectedFieldList[indexPath.row];
    
    return cell;
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
