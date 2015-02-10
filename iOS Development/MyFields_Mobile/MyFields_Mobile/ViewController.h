//
//  ViewController.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/1/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController{
    
    IBOutlet UITextField *username;
    IBOutlet UITextField *password;
    
    NSDictionary *loginDictionary;
    
}
-(IBAction)enterLogin;

@end

