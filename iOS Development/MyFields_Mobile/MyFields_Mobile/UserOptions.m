//
//  UserOptions.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "ViewController.h"
#import "UserOptions.h"
#import "MyFields.h"
#import "PestSampler.h"

@interface UserOptions ()

@end

@implementation UserOptions

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)viewWillAppear:(BOOL)animated
{
    self.navigationController.navigationBar.hidden = YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)backToLogin{
    
    UIAlertView *messageAlert = [[UIAlertView alloc]
                                 initWithTitle:@"Logout" message:@"Are you sure you want to logout?" delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"OK", nil];
    
    // Display Alert Message
    [messageAlert show];
    
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    if(buttonIndex == 1){
            
            ViewController *vc = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewControllerID"];
            [self.navigationController pushViewController:vc animated:YES];

    }
    else{
        NSLog(@"cancel");
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(indexPath.row == 0){
        
        MyFields *mf = [self.storyboard instantiateViewControllerWithIdentifier:@"MyFieldsID"];
        [self.navigationController pushViewController:mf animated:YES];

    }
    else if(indexPath.row == 1){
        
        PestSampler *ps = [self.storyboard instantiateViewControllerWithIdentifier:@"PestSamplerID"];
        [self.navigationController pushViewController:ps animated:YES];
    }
    
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
