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
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)enterLogin
{
    
    //NSInteger success = 0;
    @try{
        if ([[username text] isEqualToString:@""] || [[password text] isEqualToString:@""]) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Enter a Username and Password" message:@"You must enter a Username and Password to login." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
                [alert show];
                password.text = @"";
        }
        
        else {
            
            NSArray *path = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
            NSString *documentsDirectory = [path objectAtIndex:0];
            
            NSString *get = [[NSString alloc] initWithFormat:@"user=%@&pass=%@", [username text],[self sha1:password.text]];
            NSLog(@"GetData: %@",get);
            
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
                
                //NSString *jsonString = @"[{\"id\": \"1\",\"name\":\"Aaa\",\"age\":\"18\"},{\"id\": \"2\",\"name\":\"Bbb\",\"age\":\"27\"}]";
                
//                NSString *jsonString = @"[{\"ID\": \"1\", \"FieldName\":\"Field1\",\"Location\": {\"Latitude\":\"39\",\"Longitude\":\"-99\"},\"Size\":\"10\",\"TypeOfSoil\":\"Clay\",\"TillageSystem\":\"Conventional\",\"IrrigationSystem\":\"Dry_Land\"}]";
//                
//                NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
//                NSError *e = nil;
//                NSMutableArray *jsonArray = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&e];
//                NSLog(@"%@", jsonArray);
                
                NSError *error = nil;
                
                NSString *jsonStr = [[NSString alloc] initWithData:urlData encoding:NSUTF8StringEncoding];
                NSString *jsonString = [jsonStr stringByReplacingOccurrencesOfString:@"\n" withString:@""];
                NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
                
                NSMutableArray *jsonArray = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&error];
                
                NSString *filePath = [documentsDirectory stringByAppendingPathComponent:@"fieldData.json"];
                BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:filePath];
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
            //Add reachability files to project to check if connected to the internet. 
            else{
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"No Internet Connection." message:@"Sign in Failed." delegate:self cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
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
}

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




