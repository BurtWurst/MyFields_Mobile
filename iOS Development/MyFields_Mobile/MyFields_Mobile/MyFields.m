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

@end

@implementation MyFields

- (void)viewDidLoad {
    [super viewDidLoad];
    self.fieldList = [[NSMutableArray alloc] init];
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

- (IBAction)unwindToList:(UIStoryboardSegue *)segue {
    AddNewField *source = [segue sourceViewController];
    FieldItem *item = source.fieldItem;
    if (item != nil) {
        [self.fieldList addObject:item];
        [self.tableView reloadData];
    }
}

- (void)loadInitialData{
    FieldItem *field1 = [[FieldItem alloc] init];
    field1.fieldName = @"Wheat Field";
    [self.fieldList addObject:field1];
    
    FieldItem *field2 = [[FieldItem alloc] init];
    field2.fieldName = @"Corn Field";
    [self.fieldList addObject:field2];
    
    FieldItem *field3 = [[FieldItem alloc] init];
    field3.fieldName = @"Sorghum Field";
    [self.fieldList addObject:field3];
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