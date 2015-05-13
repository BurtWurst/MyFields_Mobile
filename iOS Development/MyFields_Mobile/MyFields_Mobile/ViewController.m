//
//  ViewController.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/1/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "ViewController.h"
#import "UserOptions.h"
//#import "KeychainItemWrapper.h"
#import "Reachability.h"

@interface ViewController ()

@property (nonatomic) Reachability *internetReachability;
@property (nonatomic) Reachability *wifiReachability;

@end

@implementation ViewController


/**
Needs to be redone with the correct method of logging in. Right now this is checking to see if the fieldData file exists on the phone and if it does then the app automatically logs in with that data. When keychain is implemented then this will no longer be necessary. Right now when a user logs out, the app logs right back in because the file still exists.
 */
- (void)viewDidLoad {
    [super viewDidLoad];
    NSArray *path = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [path objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:@"fieldData.json"];
    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:filePath];
    if(fileExists){
        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
        [self.navigationController pushViewController:uo animated:YES];
    }
    // Do any additional setup after loading the view, typically from a nib.
    
//    if(keyChainItem != NULL){
//        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
//        [self.navigationController pushViewController:uo animated:YES];
//    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/**
 Login method. Takes in username and password from text boxes on login page and passes password
 to sha1 method. Then checks to see if the encrypted password matches the password associated with 
 the username entered. If the username or password is empty, does not match, or there is no connetion
 to a network, shows an error message and explains what happened.
 */
- (IBAction)enterLogin
{
//    KeychainItemWrapper *keyChainItem = [[KeychainItemWrapper alloc] initWithIdentifier:@"MyFieldsLogin" accessGroup:nil];
//    
//    if(keyChainItem != NULL){
//        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
//        [self.navigationController pushViewController:uo animated:YES];
//    }
    NSArray *path = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [path objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:@"fieldData.json"];
    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:filePath];
    //NSString *remoteHostName = @"www.apple.com";
    self.internetReachability = [Reachability reachabilityForInternetConnection];
    self.wifiReachability = [Reachability reachabilityForInternetConnection];
    NetworkStatus netStatus = [self.internetReachability currentReachabilityStatus];
    
    switch(netStatus){
            
        case NotReachable:{
            //Add reachability files to project to check if connected to the internet.
            if(fileExists){
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"No Internet Connection." message:@"Cannot update Fields List." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                [alert show];
                UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
                [self.navigationController pushViewController:uo animated:YES];
                username.text = @"";
                password.text = @"";
            }
            else{
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"No Internet Connection." message:@"Sign in Failed." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                [alert show];
                password.text = @"";
            }
            break;
            
        }

        case ReachableViaWiFi:{
    
            @try{
                
                if ([[username text] isEqualToString:@""] || [[password text] isEqualToString:@""]) {
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Enter a Username and Password" message:@"You must enter a Username and Password to login." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                        [alert show];
                        password.text = @"";
                }
                
                else {
                    
                    
                    
                    NSString *get = [[NSString alloc] initWithFormat:@"user=%@&pass=%@", [username text],[self sha1:password.text]];
                    NSLog(@"GetData: %@",get);
                    
                    //[keyChainItem setObject:[username text] forKey:(__bridge id)(kSecValueData)];
                    //[keyChainItem setObject:[self sha1:password.text] forKey:(__bridge id)(kSecAttrAccount)];
                    
                    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
                    [request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://people.cis.ksu.edu/~dgk2010/API.php?user=%@&pass=%@", [username text], [self sha1:password.text]]]];
                    [request setHTTPMethod:@"GET"];
                    
                    NSURLResponse *requestResponse;
                    NSData *requestHandler = [NSURLConnection sendSynchronousRequest:request returningResponse:&requestResponse error:nil];
                    
                    NSString *requestReply = [[NSString alloc] initWithBytes:[requestHandler bytes] length:[requestHandler length] encoding:NSASCIIStringEncoding];
                    NSLog(@"requestReply: %@", requestReply);
                    
                    NSError *error = [[NSError alloc] init];
                    NSHTTPURLResponse *response = nil;
                    NSData *urlData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
                    if([response statusCode] == 0){
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Username or Password Incorrect" message:@"Enter a valid Username or Password." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                        [alert show];
                        password.text = @"";

                    }
                    
                    NSLog(@"Respond code: %ld", (long)[response statusCode]);
                    
                    if([response statusCode] >= 200 && [response statusCode] <300){
                        
                        NSLog(@"Login Successful");
                        NSError *error = nil;
                        NSString *jsonStr = [[NSString alloc] initWithData:urlData encoding:NSUTF8StringEncoding];
                        NSString *jsonString = [jsonStr stringByReplacingOccurrencesOfString:@"\n" withString:@""];
                        NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
                        NSMutableArray *jsonArray = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&error];
                        
                        
                        
                    @try{
                        if(fileExists){
                            
                            [[NSFileManager defaultManager] removeItemAtPath: filePath error: &error];
                            
                            NSOutputStream *outputStream = [NSOutputStream outputStreamToFileAtPath:filePath append:YES];
                            
                            [outputStream open];
                            
                            [NSJSONSerialization writeJSONObject:jsonArray toStream:outputStream options:kNilOptions error:&error];
                            
                            [outputStream close];
                        }
                        else{
                        
                            NSOutputStream *outputStream = [NSOutputStream outputStreamToFileAtPath:filePath append:YES];
                        
                            [outputStream open];
                        
                            [NSJSONSerialization writeJSONObject:jsonArray toStream:outputStream options:kNilOptions error:&error];
                        
                            [outputStream close];
                        }
                    }
                    @catch(NSException * e){
                        NSLog(@"Exception: %@", e);
                    }
                        
                        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
                        [self.navigationController pushViewController:uo animated:YES];
                        username.text = @"";
                        password.text = @"";
                    }
                }
                
            }
            @catch(NSException * e){
                NSLog(@"Exception: %@", e);
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Sign in Failed" message:@"Error." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                [alert show];
                password.text = @"";
            }
            break;
        }
        case ReachableViaWWAN:{
            break;
        }
    }
}

/**
 Sha1 encryption method. Takes in password string and converts it to an encrypted string that can
 then be checked against the password stored in the database.
 */
-(NSString*)sha1:(NSString*)input
{
    
    NSData *data = [input dataUsingEncoding:NSUTF8StringEncoding];
    
    uint8_t digest[CC_SHA1_DIGEST_LENGTH];
    
    CC_SHA1(data.bytes, (CC_LONG)data.length, digest);
    
    NSMutableString* output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH * 2];
    
    for(int i = 0; i < CC_SHA1_DIGEST_LENGTH; i++)
    {
        [output appendFormat:@"%02x", digest[i]];
    }
    
    return output;
}
@end




