//
//  NewCostCrop.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "NewControlCost.h"
#import "ControlCostItem.h"
#import "NewCropValue.h"
#import "SharedDataSingleton.h"

/**
 NewControlCost implementation file. 
 */
@interface NewControlCost ()

@property NSMutableArray *controlCostList;
@property (nonatomic, assign) BOOL cellSelected;

@end

@implementation NewControlCost


- (void)viewDidLoad {
    [super viewDidLoad];
    [self loadInitialData];
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
 Loads the initial data for each control cost item that is then stored in the controlCostList.
 */
-(void)loadInitialData{
    
    self.controlCostList = [[NSMutableArray alloc] init];
    
    ControlCostItem *cci1 = [[ControlCostItem alloc] init];
    cci1.controlCost = @"4.00";
    [self.controlCostList addObject:cci1];
    ControlCostItem *cci2 = [[ControlCostItem alloc] init];
    cci2.controlCost = @"5.00";
    [self.controlCostList addObject:cci2];
    ControlCostItem *cci3 = [[ControlCostItem alloc] init];
    cci3.controlCost = @"6.00";
    [self.controlCostList addObject:cci3];
    ControlCostItem *cci4 = [[ControlCostItem alloc] init];
    cci4.controlCost = @"7.00";
    [self.controlCostList addObject:cci4];
    ControlCostItem *cci5 = [[ControlCostItem alloc] init];
    cci5.controlCost = @"8.00";
    [self.controlCostList addObject:cci5];
    ControlCostItem *cci6 = [[ControlCostItem alloc] init];
    cci6.controlCost = @"9.00";
    [self.controlCostList addObject:cci6];
    ControlCostItem *cci7 = [[ControlCostItem alloc] init];
    cci7.controlCost = @"10.00";
    [self.controlCostList addObject:cci7];
    ControlCostItem *cci8 = [[ControlCostItem alloc] init];
    cci8.controlCost = @"11.00";
    [self.controlCostList addObject:cci8];
    ControlCostItem *cci9 = [[ControlCostItem alloc] init];
    cci9.controlCost = @"12.00";
    [self.controlCostList addObject:cci9];
    
    //Reload our table view
    [self.tableView reloadData];
    
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    //Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    //Return the number of rows in the section.
    
    return [self.controlCostList count];
}

-(void)viewWillDisappear:(BOOL)animated{
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    [super viewWillDisappear:animated];
    
    if(self.isMovingFromParentViewController){
        sharedManager.controlCost = @"";
    }
}

/**
 Adds a control cost item to the table view and makes the table cell text equal to the control cost string value.
 */
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ControlCostPrototypeCell" forIndexPath:indexPath];
    
    ControlCostItem *controlCostItem = [self.controlCostList objectAtIndex:indexPath.row];
    cell.textLabel.text = controlCostItem.controlCost;
    
    return cell;
}

/**
 Adds a checkmark to the cell that is selected.
 */
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    
    ControlCostItem *controlCostItem = [self.controlCostList objectAtIndex:indexPath.row];
    cell.textLabel.text = controlCostItem.controlCost;
    
    if (cell.accessoryType == UITableViewCellAccessoryNone) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        self.cellSelected = true;
        sharedManager.controlCost = controlCostItem.controlCost;
        NSLog(@"Control Cost is %@", sharedManager.controlCost);
        
        // Reflect selection in data model

    }
    else if (cell.accessoryType == UITableViewCellAccessoryCheckmark) {
        cell.accessoryType = UITableViewCellAccessoryNone;
        self.cellSelected = false;
        sharedManager.controlCost = @"";
        NSLog(@"Control Cost is %@", sharedManager.controlCost);
    }
}

/**
 Next button action that goes to the next page which is NewCropValue.
 */
-(IBAction)nextButton:(UIBarButtonItem *)sender {
    UIAlertView *noCellAlert = [[UIAlertView alloc]
                                initWithTitle:@"No Cell Selected!" message:@"Please select a cell. " delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    
    if(self.cellSelected == true){
        NewCropValue *ncv = [self.storyboard instantiateViewControllerWithIdentifier:@"NewCropValueID"];
        [self.navigationController pushViewController:ncv animated:YES];
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

@end
