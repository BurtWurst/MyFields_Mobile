//
//  NotesAndOtherPests.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/28/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "NotesAndOtherPests.h"
#import "UserOptions.h"
#import "AppDelegate.h"
#import "SharedDataSingleton.h"

@interface NotesAndOtherPests()

@property (weak, nonatomic) IBOutlet UITextView *notes;
@property (weak, nonatomic) IBOutlet UITableView *otherPests;
@property (nonatomic, assign) BOOL cellSelected;
@property NSArray *otherPestList;

@end

@implementation NotesAndOtherPests


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
  Loads in the text file that stores the other pests list. Puts them into a local mutable array.
 */
-(void)loadInitialData{
    NSString* path = [[NSBundle mainBundle] pathForResource:@"otherPest"
                                                     ofType:@"txt"];
    NSString* content = [NSString stringWithContentsOfFile:path
                                                  encoding:NSUTF8StringEncoding
                                                     error:NULL];
    
    self.otherPestList = [[NSMutableArray alloc] init];
    self.otherPestList = [content componentsSeparatedByString:@","];
    NSLog(@"Other pest list %@", self.otherPestList);
    
    //Reload our table view
    //[self.tableView reloadData];
    [self.otherPests reloadData];
    
}


-(void)textViewDidBeginEditing:(UITextView *)textView{
    NSLog(@"Did begin editing");
    currentTextView = textView;
    [textView setBackgroundColor:[UIColor grayColor]];
}

-(void)textViewDidEndEditing:(UITextView *)textView{
    NSLog(@"Did End editing");
    [textView setBackgroundColor:[UIColor whiteColor]];
    
    
}
-(BOOL)textViewShouldEndEditing:(UITextView *)textView{
    [textView resignFirstResponder];
    
    return YES;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    //Return the number of sections.
    return 1;
}

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [self.otherPestList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"OtherPestPrototypeCell" forIndexPath:indexPath];
    
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"OtherPestPrototypeCell"];
    }
    
    NSUInteger rowIndex = [indexPath row];
    cell.textLabel.text = [self.otherPestList objectAtIndex:rowIndex];
    
    return cell;
}

/**
 Adds a checkmark to the cell that is selected.
 */
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    NSUInteger rowIndex = [indexPath row];
    cell.textLabel.text = [self.otherPestList objectAtIndex:rowIndex];
    
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
 Finish button resets the navagation array to go back to the user options at the beginning. Also resets all the values for the sharedManager singleton. 
 */
-(IBAction)finishButton:(UIBarButtonItem *)sender {
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];

    NSMutableArray *navigationArray = [[NSMutableArray alloc] initWithArray:self.navigationController.viewControllers];
    for(int i = 2; i <= navigationArray.count - 2; i++){
        [navigationArray removeObjectAtIndex:i];
        self.navigationController.viewControllers = navigationArray;
    }
    sharedManager.cropValue = @"";
    sharedManager.controlCost = @"";
    sharedManager.greenBugThreshold = 0;
    sharedManager.mummyCount = 0;
    sharedManager.aphidCount = 0;
    sharedManager.fieldSampleCount = 0;
    sharedManager.maxFieldSample = 29;
    sharedManager.costValueThreshold = 0;
    sharedManager.fieldSampleThreshold = 4;
    sharedManager.fieldSampleBackCount = 0;
    sharedManager.greenBugThresholdHigh = 0;
    sharedManager.greenBugThresholdLow = 0;
    sharedManager.mummyThreshold = 0;
    //UserOptions *uo = [[UserOptions alloc]init];
    UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
    [self.navigationController pushViewController:uo animated:YES];
    //self.navigationController.viewControllers = [[NSArray alloc] initWithObjects:uo, nil];
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
