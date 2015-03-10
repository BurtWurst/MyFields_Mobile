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

@interface MyFields ()

@property NSMutableArray *fieldList;
@property NSMutableArray *jsonArray;

@end

@implementation MyFields

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

- (IBAction)unwindToList:(UIStoryboardSegue *)segue {
    AddNewField *source = [segue sourceViewController];
    FieldItem *item = source.fieldItem;
    if (item != nil) {
        [self.fieldList addObject:item];
        [self.tableView reloadData];
    }
}

- (void)loadInitialData{
}

- (void) retrieveData{
    
    self.fieldList = [[NSMutableArray alloc] init];
    
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

        [self.fieldList addObject:[[FieldItem alloc]initWithFieldName:fID andFieldName:fName andFieldLocation:fLocation andFieldSize:fSize andFieldSoil:fSoil andFieldTillage:fTillage andFieldIrrigation:fIrrigation]];
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
    
    return [self.fieldList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ListPrototypeCell" forIndexPath:indexPath];
    
    
    FieldItem *fieldItem = [self.fieldList objectAtIndex:indexPath.row];
    cell.textLabel.text = fieldItem.fieldName;
    
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