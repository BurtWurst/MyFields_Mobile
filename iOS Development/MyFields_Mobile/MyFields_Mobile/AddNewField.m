//
//  AddNewField.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "AddNewField.h"

/**
 AddNewField implementation file. Creates 2 outlet properties for the text field for naming the field and the save button for saving the field to the field list. Will probably not be used in the final product. 
 */
@interface AddNewField ()
@property (weak, nonatomic) IBOutlet UITextField *textField;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *saveButton;

@end

@implementation AddNewField



- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    if (sender != self.saveButton)return;
    
    if (self.textField.text.length > 0){
        self.fieldItem = [[FieldItem alloc] init];
        self.fieldItem.fieldName = self.textField.text;
    }
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

@end


