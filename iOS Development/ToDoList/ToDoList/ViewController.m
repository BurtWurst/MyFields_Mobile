//
//  ViewController.m
//  ToDoList
//
//  Created by Brett Merriam on 12/10/14.
//
//

#import "ViewController.h"
#import "SecondViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    loginDictionary = [[NSDictionary alloc] initWithObjects:[NSArray arrayWithObjects:@"password",@"password", nil] forKeys: [NSArray arrayWithObjects:@"username",@"bmerriam", nil]];
}

- (IBAction)enterLogin
{
    if([[loginDictionary objectForKey:username.text]isEqualToString:password.text])
    {
        SecondViewController *second = [self.storyboard instantiateViewControllerWithIdentifier:@"SecondIdentifier"];
        [self.navigationController pushViewController:second animated:YES];
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



























