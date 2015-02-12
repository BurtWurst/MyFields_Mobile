//
//  ViewController.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/1/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "ViewController.h"
#import "UserOptions.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    loginDictionary = [[NSDictionary alloc] initWithObjects:[NSArray arrayWithObjects:@"password",@"password", nil] forKeys: [NSArray arrayWithObjects:@"username",@"bmerriam", nil]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)enterLogin
{
    if([[loginDictionary objectForKey:username.text]isEqualToString:password.text])
    {
        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
        [self.navigationController pushViewController:uo animated:YES];
        username.text = @"";
        password.text = @"";
    }
    else
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Incorrect Username or Password" message:@"The username or password is incorrect." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
        [alert show];
        password.text = @"";
    }
}
@end