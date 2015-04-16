//
//  NewPestSample.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 3/30/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "NewPestSample.h"
#import "PestItem.h"
#import "NewControlCost.h"

@interface NewPestSample ()

@property NSMutableArray *pestList;

@end

@implementation NewPestSample

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

-(void)loadInitialData{
    
    self.pestList = [[NSMutableArray alloc] init];
    
    PestItem *pest1 = [[PestItem alloc] init];
    pest1.pestName = @"Glance N Go(Greenbug)";
    [self.pestList addObject:pest1];
    PestItem *pest2 = [[PestItem alloc] init];
    pest2.pestName = @"Russian Wheat Aphid... Coming Soon!";
    [self.pestList addObject:pest2];
    PestItem *pest3 = [[PestItem alloc] init];
    pest3.pestName = @"Soybean Aphid... Coming Soon!";
    [self.pestList addObject:pest3];
    PestItem *pest4 = [[PestItem alloc] init];
    pest4.pestName = @"Sugarcane Aphid... Coming Soon!";
    [self.pestList addObject:pest4];
    PestItem *pest5 = [[PestItem alloc] init];
    pest5.pestName = @"Wheat Stem Sawfly... Coming Soon!";
    [self.pestList addObject:pest5];
    
    //Reload our table view
    [self.tableView reloadData];
    
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    //Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    //Return the number of rows in the section.
    
    return [self.pestList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"PestListPrototypeCell" forIndexPath:indexPath];
    
    PestItem *pestItem = [self.pestList objectAtIndex:indexPath.row];
    cell.textLabel.text = pestItem.pestName;
    
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    PestItem *pestItem = [self.pestList objectAtIndex:indexPath.row];
    cell.textLabel.text = pestItem.pestName;
    
    UIAlertView *greenbugAlert = [[UIAlertView alloc]
                                 initWithTitle:@"Greenbug Sample Method" message:@"A presence/absence sampling method to make a treatment decision for greenbugs in winter wheat. First, sampling information is linked to a location or field site. Then, an economic threshold is determined by choosing the cost of treatment per acre ($/acre), and the expected value of the crop in $/bu. This economic threshold is then used to sample a minimum number of tillers for both live greenbugs and parasitized greenbugs (mummies, to account for biocontrol services working in the field). A treatment decision is provided in the report page of the sampler, which can be printed out and is automatically saved to a field site's history." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    UIAlertView *comingSoonAlert = [[UIAlertView alloc]
                                  initWithTitle:@"Coming Soon!" message:@"" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    
    if (cell.accessoryType == UITableViewCellAccessoryNone) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        // Reflect selection in data model
        
        if([pestItem.pestName isEqualToString:@"Glance N Go(Greenbug)"]){
            // Display Alert Message
            [greenbugAlert show];
        }
        else if ([pestItem.pestName isEqualToString:@"Russian Wheat Aphid... Coming Soon!"]){
            [comingSoonAlert show];
        }
        else if ([pestItem.pestName isEqualToString:@"Soybean Aphid... Coming Soon!"]){
            [comingSoonAlert show];
        }
        else if ([pestItem.pestName isEqualToString:@"Sugarcane Aphid... Coming Soon!"]){
            [comingSoonAlert show];
        }
        else if ([pestItem.pestName isEqualToString:@"Wheat Stem Sawfly... Coming Soon!"]){
            [comingSoonAlert show];
        }
    }
    else if (cell.accessoryType == UITableViewCellAccessoryCheckmark) {
        cell.accessoryType = UITableViewCellAccessoryNone;
    }
}

-(IBAction)nextButton:(UIBarButtonItem *)sender {
    NewControlCost *ncc = [self.storyboard instantiateViewControllerWithIdentifier:@"NewControlCostID"];
    [self.navigationController pushViewController:ncc animated:YES];
    
}


-(void)tableView:(UITableView *)tableView didDeselectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView cellForRowAtIndexPath:indexPath].accessoryType = UITableViewCellAccessoryNone;
}



@end
