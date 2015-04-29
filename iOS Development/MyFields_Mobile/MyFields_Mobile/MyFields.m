//
//  MyFields.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "MyFields.h"
#import "FieldItem.h"
#import "AddNewField.h"
#import "ViewFieldData.h"


/**
 MyFields implementation file
 */
@interface MyFields ()

@property NSMutableArray *jsonArray;

@end

@implementation MyFields

@synthesize fieldList;
@synthesize fieldIndex;
@synthesize shareFieldObject;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self retrieveData];
    
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewWillAppear:(BOOL)animated
{
    self.navigationController.navigationBar.hidden = NO;
}

/**
 Unwind to list view after adding a new field. Might not be used in final release.
 */
- (IBAction)unwindToList:(UIStoryboardSegue *)segue {
    AddNewField *source = [segue sourceViewController];
    FieldItem *item = source.fieldItem;
    if (item != nil) {
        [fieldList addObject:item];
        [self.tableView reloadData];
    }
}

/**
 Pulls json data from saved file. Loops through the json array and puts each Field object into the fieldList array.
 */
- (void) retrieveData{
    
    fieldList = [[NSMutableArray alloc] init];
    
    NSArray *jsonPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [jsonPath objectAtIndex:0];
    NSError *error = nil;
    NSString *jsonFilePath = [NSString stringWithFormat:@"%@/%@", documentsDirectory,@"fieldData.json"];
    NSData *jsonData = [NSData dataWithContentsOfFile:jsonFilePath options:kNilOptions error:&error];
    self.jsonArray = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:&error];
    
    //Loop through jsonArray
    for (int i = 0; i < self.jsonArray.count; i++){
        NSString *fID = [[self.jsonArray objectAtIndex:i] objectForKey:@"ID"];
        NSString *fName = [[self.jsonArray objectAtIndex:i] objectForKey:@"FieldName"];
        NSString *fLocation = [[self.jsonArray objectAtIndex:i] objectForKey:@"Location"];
        NSString *fSize = [[self.jsonArray objectAtIndex:i] objectForKey:@"Size"];
        NSString *fSoil = [[self.jsonArray objectAtIndex:i] objectForKey:@"TypeOfSoil"];
        NSString *fTillage = [[self.jsonArray objectAtIndex:i] objectForKey:@"TillageSystem"];
        NSString *fIrrigation = [[self.jsonArray objectAtIndex:i] objectForKey:@"IrrigationSystem"];
        NSMutableArray *fPlantingList = [[self.jsonArray objectAtIndex:i] objectForKey:@"PlantingList"];
        NSMutableArray *fPestSamples = [[self.jsonArray objectAtIndex:i] objectForKey:@"PestSamples"];
        
        [fieldList addObject:[[FieldItem alloc]initWithFieldName:fID andFieldName:fName andFieldLocation:fLocation andFieldSize:fSize andFieldSoil:fSoil andFieldTillage:fTillage andFieldIrrigation:fIrrigation andPlantingList:fPlantingList andFieldSamples:fPestSamples]];
    }
    
    //Reload our table view
    [self.tableView reloadData];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    //Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    //Return the number of rows in the section.
    return [fieldList count];
}

/**
 Adds a field item to the table view and makes the table cell text equal to the field name.
 */
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ListPrototypeCell" forIndexPath:indexPath];
    
    FieldItem *fieldItem = [fieldList objectAtIndex:indexPath.row];
    cell.textLabel.text = fieldItem.fieldName;
    
    return cell;
}

/**
 Passes the fieldIndex, which is set to the index of the row selected, to the ViewFieldData controller. This allows the data from each field to be viewed in the next view controller. 
 */
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    fieldIndex = (int)indexPath.row;
    shareFieldObject = [fieldList objectAtIndex:indexPath.row];
    ViewFieldData *vfd = [self.storyboard instantiateViewControllerWithIdentifier:@"FieldDataID"];
    [self.navigationController pushViewController:vfd animated:YES];
    vfd.pointerToMyFields = self;
    
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