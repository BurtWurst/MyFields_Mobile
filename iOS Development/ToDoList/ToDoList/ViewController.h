//
//  ViewController.h
//  ToDoList
//
//  Created by Brett Merriam on 12/10/14.
//
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController{

    IBOutlet UITextField *username;
    IBOutlet UITextField *password;
    
    NSDictionary *loginDictionary;
    
}

- (IBAction)enterLogin;

@end

