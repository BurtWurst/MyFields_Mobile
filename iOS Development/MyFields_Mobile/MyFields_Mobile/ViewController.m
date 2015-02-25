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
    
//    loginDictionary = [[NSDictionary alloc] initWithObjects:[NSArray arrayWithObjects:@"password",@"password", nil] forKeys: [NSArray arrayWithObjects:@"username",@"bmerriam", nil]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)enterLogin
{
    
    NSInteger success = 0;
    @try{
        if ([[username text] isEqualToString:@""] || [[password text] isEqualToString:@""]) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Incorrect Username or Password" message:@"The username or password is incorrect." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                [alert show];
                password.text = @"";
        }
        
        else {
            NSString *get = [[NSString alloc] initWithFormat:@"user=%@&pass=%@", [username text],[self sha1:password.text]];
            NSLog(@"GetData: %@",get);
            
            //NSURL *url=[NSURL URLWithString:@"http://people.cis.ksu.edu/~dgk2010/API.php?"];
            
            
            
            //NSData *getData = [get dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
            
            //NSString *getLength = [NSString stringWithFormat:@"%lu", (unsigned long)[getData length]];
            
            NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
            //[request setURL:url];
            [request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://people.cis.ksu.edu/~dgk2010/API.php?user=%@&pass=%@", [username text], [self sha1:password.text]]]];
            [request setHTTPMethod:@"GET"];
//            [request setValue:getLength forHTTPHeaderField:@"Content-Length"];
//            [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
//            [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
//            [request setHTTPBody:getData];
            
            NSURLResponse *requestResponse;
            NSData *requestHandler = [NSURLConnection sendSynchronousRequest:request returningResponse:&requestResponse error:nil];
            
            NSString *requestReply = [[NSString alloc] initWithBytes:[requestHandler bytes] length:[requestHandler length] encoding:NSASCIIStringEncoding];
            NSLog(@"requestReply: %@", requestReply);
            
            NSError *error = [[NSError alloc] init];
            NSHTTPURLResponse *response = nil;
            NSData *urlData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
            
            NSLog(@"Respond code: %ld", (long)[response statusCode]);
            
            if([response statusCode] >= 200 && [response statusCode] <300){
                //NSString *responseData = [[NSString alloc]initWithData:urlData encoding:NSUTF8StringEncoding];
                //NSLog(@"Response ==> %@", responseData);
                
                NSError *error = nil;
                NSDictionary *jsonData = [NSJSONSerialization JSONObjectWithData:urlData options:NSJSONReadingMutableContainers error:&error];
                success = [jsonData[@"success"] integerValue];
                NSLog(@"Success: %ld",(long)success);
                
                if(success == 1){
                    NSLog(@"Login Successful");
                }
                else{
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Sign in Failed" message:@"Your sign in attempt has failed." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                    [alert show];
                    password.text = @"";
                }
            }
            else{
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Connection Failed" message:@"Sign in Failed." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                [alert show];
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
    if(success){
        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
            [self.navigationController pushViewController:uo animated:YES];
            username.text = @"";
            password.text = @"";
    }
//    if([[loginDictionary objectForKey:username.text]isEqualToString:password.text])
//    {
//        UserOptions *uo = [self.storyboard instantiateViewControllerWithIdentifier:@"UserOptionsID"];
//        [self.navigationController pushViewController:uo animated:YES];
//        username.text = @"";
//        password.text = @"";
//    }
//    else
//    {
//        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Incorrect Username or Password" message:@"The username or password is incorrect." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
//        [alert show];
//        password.text = @"";
//    }
}

-(NSString*)sha1:(NSString*)input
{
    //const char *cstr = [input cStringUsingEncoding:NSUTF8StringEncoding];
    //NSData *data = [NSData dataWithBytes:cstr length:input.length];
    
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




