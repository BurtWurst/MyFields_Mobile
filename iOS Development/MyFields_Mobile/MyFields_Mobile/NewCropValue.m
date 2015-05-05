//
//  NewCropValue.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "NewCropValue.h"
#import "CropValueItem.h"
#import "TakeFieldSample.h"
#import "SharedDataSingleton.h"

/**
 NewCropValue implementation file.
 */
@interface NewCropValue ()

@property NSMutableArray *cropValueList;
@property (nonatomic, assign) BOOL cellSelected;

@end

@implementation NewCropValue

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
 Loads the initial data for each crop value item that is then stored in the cropValueList.
 */
-(void)loadInitialData{
    
    self.cropValueList = [[NSMutableArray alloc] init];
    
    CropValueItem *cvi1 = [[CropValueItem alloc] init];
    cvi1.cropValue= @"2.50";
    [self.cropValueList addObject:cvi1];
    CropValueItem *cvi2 = [[CropValueItem alloc] init];
    cvi2.cropValue = @"3.00";
    [self.cropValueList addObject:cvi2];
    CropValueItem *cvi3 = [[CropValueItem alloc] init];
    cvi3.cropValue = @"3.50";
    [self.cropValueList addObject:cvi3];
    CropValueItem *cvi4 = [[CropValueItem alloc] init];
    cvi4.cropValue = @"4.00";
    [self.cropValueList addObject:cvi4];
    CropValueItem *cvi5 = [[CropValueItem alloc] init];
    cvi5.cropValue = @"4.50";
    [self.cropValueList addObject:cvi5];
    CropValueItem *cvi6 = [[CropValueItem alloc] init];
    cvi6.cropValue = @"5.00";
    [self.cropValueList addObject:cvi6];
    CropValueItem *cvi7 = [[CropValueItem alloc] init];
    cvi7.cropValue = @"5.50";
    [self.cropValueList addObject:cvi7];
    CropValueItem *cvi8 = [[CropValueItem alloc] init];
    cvi8.cropValue = @"6.00";
    [self.cropValueList addObject:cvi8];
    CropValueItem *cvi9 = [[CropValueItem alloc] init];
    cvi9.cropValue = @"6.50";
    [self.cropValueList addObject:cvi9];
    CropValueItem *cvi10 = [[CropValueItem alloc] init];
    cvi10.cropValue = @"7.00";
    [self.cropValueList addObject:cvi10];
    
    //Reload our table view
    [self.tableView reloadData];
    
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    //Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    //Return the number of rows in the section.
    
    return [self.cropValueList count];
}

-(void)viewWillDisappear:(BOOL)animated{
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    [super viewWillDisappear:animated];
    
    if(self.isMovingFromParentViewController){
        sharedManager.cropValue = @"";
        sharedManager.costValueThreshold = 0;
    }
}

/**
 Adds a crop value item to the table view and makes the table cell text equal to the crop value string value.
 */
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"CropValuePrototypeCell"forIndexPath:indexPath];
    
    
    CropValueItem *cropValueItem = [self.cropValueList objectAtIndex:indexPath.row];
    cell.textLabel.text = cropValueItem.cropValue;
    
    return cell;
}

/**
 Adds a checkmark to the cell that is selected.
 */
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    
    CropValueItem *cropValueItem = [self.cropValueList objectAtIndex:indexPath.row];
    cell.textLabel.text = cropValueItem.cropValue;
    
    if (cell.accessoryType == UITableViewCellAccessoryNone) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        self.cellSelected = true;
        
        sharedManager.cropValue = cropValueItem.cropValue;
        NSLog(@"Crop Value is %@", sharedManager.cropValue);
        // Reflect selection in data model
        
    }
    else if (cell.accessoryType == UITableViewCellAccessoryCheckmark) {
        cell.accessoryType = UITableViewCellAccessoryNone;
        self.cellSelected = false;
        sharedManager.cropValue = @"";
        NSLog(@"Crop Value is %@", sharedManager.cropValue);
    }
}

/**
 Next button action that goes to the next page which is NewCropValue.
 */
-(IBAction)nextButton:(UIBarButtonItem *)sender {
    UIAlertView *noCellAlert = [[UIAlertView alloc]
                                initWithTitle:@"No Cell Selected!" message:@"Please select a cell. " delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    
    if(self.cellSelected == true){
        TakeFieldSample *tfs = [self.storyboard instantiateViewControllerWithIdentifier:@"TakeFieldSampleID"];
        [self.navigationController pushViewController:tfs animated:YES];
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
