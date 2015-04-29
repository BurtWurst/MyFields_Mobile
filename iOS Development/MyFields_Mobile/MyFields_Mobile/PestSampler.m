//
//  PestSampler.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "PestSampler.h"
#import "FieldItem.h"
#import "NewPestSample.h"

/**
 Pest sampler implementation file.
 */
@interface PestSampler ()

@property NSMutableArray *fieldList;
@property NSMutableArray *jsonArray;
@property (nonatomic, assign) BOOL cellSelected;

//MyFields *myFields = [[MyFields alloc] init];
//NSMutableArray *mfArray = [myFields.fields mutableCopy];

@end

@implementation PestSampler

- (void)viewDidLoad {
    [super viewDidLoad];
    [self retrieveData];
    // Do any additional setup after loading the view.
}

- (void)viewWillAppear:(BOOL)animated
{
    self.navigationController.navigationBar.hidden = NO;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/**
 Pulls json data from saved file. Loops through the json array and puts each Field object into the fieldList array.
 */
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
        NSArray *fPlantingList = [[self.jsonArray objectAtIndex:i] objectForKey:@"PlantingList"];
        NSArray *fPestSamples = [[self.jsonArray objectAtIndex:i] objectForKey:@"PestSamples"];
        
        [self.fieldList addObject:[[FieldItem alloc]initWithFieldName:fID andFieldName:fName andFieldLocation:fLocation andFieldSize:fSize andFieldSoil:fSoil andFieldTillage:fTillage andFieldIrrigation:fIrrigation andPlantingList:fPlantingList andFieldSamples:fPestSamples]];
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

/**
 Adds a field item to the table view and makes the table cell text equal to the field name.
 */
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ListPrototypeCell" forIndexPath:indexPath];
    
    
    FieldItem *fieldItem = [self.fieldList objectAtIndex:indexPath.row];
    cell.textLabel.text = fieldItem.fieldName;
    
    return cell;
}

/**
 Adds a checkmark to the cell that is selected.
 */
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    if (cell.accessoryType == UITableViewCellAccessoryNone) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        self.cellSelected = true;
        // Reflect selection in data model
    }
    else if (cell.accessoryType == UITableViewCellAccessoryCheckmark) {
        cell.accessoryType = UITableViewCellAccessoryNone;
        self.cellSelected = false;
    }
}

/**
 Next button action that goes to the next page which is NewPestSample
 */
-(IBAction)nextButton:(UIBarButtonItem *)sender {
    UIAlertView *noCellAlert = [[UIAlertView alloc]
                                initWithTitle:@"No Cell Selected!" message:@"Please select a cell. " delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    
    if(self.cellSelected == true){
        NewPestSample *nps = [self.storyboard instantiateViewControllerWithIdentifier:@"PestListID"];
        [self.navigationController pushViewController:nps animated:YES];
    }
    else{
        [noCellAlert show];
    }
    
}

/**
 Deselects selected row when another row is selected. 
 */
-(void)tableView:(UITableView *)tableView didDeselectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView cellForRowAtIndexPath:indexPath].accessoryType = UITableViewCellAccessoryNone;
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