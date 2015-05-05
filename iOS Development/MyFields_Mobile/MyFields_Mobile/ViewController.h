//
//  ViewController.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/1/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//


/**
 Initial View controller header file. Creates outlet fields for the username and password
 text boxes. Creates an action method for pressing the login button as well as a method for 
 converting passwords to SHA1 encryption.
*/
#import <UIKit/UIKit.h>
#import <CommonCrypto/CommonDigest.h>

@interface ViewController : UIViewController{
    
    IBOutlet UITextField *username;
    IBOutlet UITextField *password;
    
}
-(IBAction)enterLogin;

-(NSString*) sha1:(NSString*)input;

@end

