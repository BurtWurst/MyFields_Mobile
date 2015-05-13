//
//  GreenbugSample.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "TakeFieldSample.h"
#import "SharedDataSingleton.h"
#import "NotesAndOtherPests.h"

@interface TakeFieldSample ()

//Count for the aphids in each stop
@property (nonatomic, assign) int aphidCount;
//Count for the mummys in each stop
@property (nonatomic, assign) int mummyCount;
//Aphid button 1, top left
@property (nonatomic, retain) IBOutlet UIButton *aphidTillerOne;
//Aphid button 2, top middle
@property (nonatomic, retain) IBOutlet UIButton *aphidTillerTwo;
//Aphid button 3, top right
@property (nonatomic, retain) IBOutlet UIButton *aphidTillerThree;
//Mummy button 1, bottom left
@property (nonatomic, retain) IBOutlet UIButton *mummyTillerOne;
//Mummy button 2, bottom middle
@property (nonatomic, retain) IBOutlet UIButton *mummyTillerTwo;
//Mummy button 3, bottom right
@property (nonatomic, retain) IBOutlet UIButton *mummyTillerThree;
//Integer that stores the month value
@property (nonatomic, assign) NSInteger month;
//Label for displaying the current stop
@property (weak, nonatomic) IBOutlet UILabel *stopCount;

@end

/**
 TakeFieldSample implementation file
 */
@implementation TakeFieldSample

- (void)viewDidLoad {
    [super viewDidLoad];
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    NSDateComponents *components = [[NSCalendar currentCalendar] components:NSCalendarUnitDay | NSCalendarUnitMonth | NSCalendarUnitYear fromDate:[NSDate date]];
    self.month = [components month];
    self.aphidCount = 0;
    self.mummyCount = 0;
    self.aphidTillerOne.tag = 1;
    self.aphidTillerTwo.tag = 2;
    self.aphidTillerThree.tag = 3;
    self.mummyTillerOne.tag = 4;
    self.mummyTillerTwo.tag = 5;
    self.mummyTillerThree.tag = 6;
    self.stopCount.text = [NSString stringWithFormat:@"Stop %d of 5", sharedManager.stopCount];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/**
 View will disappear is called when the back button is pressed. Some of my math in this method for checking which stop the user is at and calculating the correct recommendation is wrong. Recommend going through and checking to figure out where the error is.
 */
-(void)viewWillDisappear:(BOOL)animated{
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    [super viewWillDisappear:animated];
    
    if(self.isMovingFromParentViewController){
        //Total aphid count from singleton
        sharedManager.aphidCount -= self.aphidCount;
        //Total mummy count from singleton
        sharedManager.mummyCount -= self.mummyCount;
        //Total stop count from singleton
        sharedManager.stopCount -= 1;
        
        sharedManager.fieldSampleCount--;
        NSLog(@"Field Sample Count is %d", sharedManager.fieldSampleCount);
        if(sharedManager.fieldSampleCount == 5){
            NSLog(@"Field Sample Count is %d", sharedManager.fieldSampleCount);
            NSLog(@"Field Sample Threshold is %d", sharedManager.fieldSampleThreshold);
            sharedManager.fieldSampleThreshold = 4;
            NSLog(@"Field Sample Threshold is %d", sharedManager.fieldSampleThreshold);
        }
        else if(sharedManager.fieldSampleCount == 10 || sharedManager.fieldSampleCount == 15 || sharedManager.fieldSampleCount == 20 || sharedManager.fieldSampleCount == 25 || sharedManager.fieldSampleCount == 30){
            NSLog(@"Field Sample Count is %d", sharedManager.fieldSampleCount);
            NSLog(@"Field Sample Threshold is %d", sharedManager.fieldSampleThreshold);
            sharedManager.fieldSampleThreshold -=5;
            NSLog(@"Field Sample Threshold is %d", sharedManager.fieldSampleThreshold);
        }
    }
}

//On button click method checks to see which button is pressed and adds 1 to the global cound and the local count of either mummy or aphid.
-(IBAction) onButtonClick:(UIButton*)sender{
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    NSLog(@"Button tag is %ld", sender.tag);
    if(sender.tag == 1 || sender.tag == 2 || sender.tag == 3){
        if(!sender.selected){
            [sender setSelected:YES];
            self.aphidCount++;
            sharedManager.aphidCount++;
            NSLog(@"Aphid Count is %d", self.aphidCount);
            NSLog(@"Aphid Count is %d", sharedManager.aphidCount);
        }
        else{
            [sender setSelected:NO];
            self.aphidCount--;
            sharedManager.aphidCount--;
            NSLog(@"Aphid Count is %d", self.aphidCount);
            NSLog(@"Aphid Count is %d", sharedManager.aphidCount);
        }
    }
    else{
        if(!sender.selected){
            [sender setSelected:YES];
            self.mummyCount++;
            sharedManager.mummyCount++;
            NSLog(@"Mummy Count is %d", self.mummyCount);
            NSLog(@"Mummy Count is %d", sharedManager.mummyCount);
        }
        else{
            [sender setSelected:NO];
            self.mummyCount--;
            sharedManager.mummyCount--;
            NSLog(@"Mummy Count is %d", self.mummyCount);
            NSLog(@"Mummy Count is %d", sharedManager.mummyCount);
        }

    }
}

//Next button is where all of the recommendations take place. Possibly something wrong with the math I am using to calculate the the fieldSampleCount from the sharedManager singleton.
-(IBAction)nextButton:(UIBarButtonItem *)sender {
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    sharedManager.fieldSampleCount++;
    NSLog(@"Field Sample Threshold is %d", sharedManager.fieldSampleThreshold);
    if(sharedManager.fieldSampleCount != sharedManager.maxFieldSample){
        if(sharedManager.fieldSampleCount <= sharedManager.fieldSampleThreshold){
            sharedManager.stopCount++;
            TakeFieldSample *tfs = [self.storyboard instantiateViewControllerWithIdentifier:@"TakeFieldSampleID"];
            [self.navigationController pushViewController:tfs animated:YES];
        }
        else{
            NSLog(@"Field Sample Threshold is %d", sharedManager.fieldSampleThreshold);
            [self findCostValueThreshold];
            [self findGreenbugTreshold];
            
            if(sharedManager.mummyCount >= sharedManager.mummyThreshold){
                UIAlertView *treatAlert = [[UIAlertView alloc]
                                           initWithTitle:@"Don't treat based on Mummy count." message:@"" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [treatAlert show];
                //Go to notes and other pest page.
                NotesAndOtherPests *naop = [self.storyboard instantiateViewControllerWithIdentifier:@"NotesAndOtherPestsID"];
                [self.navigationController pushViewController:naop animated:YES];
            }
            
            else if(sharedManager.aphidCount <= sharedManager.greenBugThresholdLow){
                UIAlertView *treatAlert = [[UIAlertView alloc]
                                           initWithTitle:@"Don't treat based on Greenbug count." message:@"" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [treatAlert show];
                //Go to notes and other pest page.
                NotesAndOtherPests *naop = [self.storyboard instantiateViewControllerWithIdentifier:@"NotesAndOtherPestsID"];
                [self.navigationController pushViewController:naop animated:YES];

            }
            else if(sharedManager.aphidCount >= sharedManager.greenBugThresholdHigh){
                UIAlertView *treatAlert = [[UIAlertView alloc]
                                           initWithTitle:@"Treat based on Greenbug count." message:@"" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [treatAlert show];
                //Go to notes and other pest page.
                NotesAndOtherPests *naop = [self.storyboard instantiateViewControllerWithIdentifier:@"NotesAndOtherPestsID"];
                [self.navigationController pushViewController:naop animated:YES];

            }
            else{
                UIAlertView *treatAlert = [[UIAlertView alloc]
                                           initWithTitle:@"Cannot determine based on current counts. Keep sampling." message:@"" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [treatAlert show];
                sharedManager.fieldSampleThreshold +=5;
                TakeFieldSample *tfs = [self.storyboard instantiateViewControllerWithIdentifier:@"TakeFieldSampleID"];
                [self.navigationController pushViewController:tfs animated:YES];
            }
        }
    }
    else{
        UIAlertView *maxSampleAlert = [[UIAlertView alloc]
                                   initWithTitle:@"Cannot determine based on max number of samples. Moving to notes page" message:@"" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [maxSampleAlert show];
        
        //go to notes page
        NotesAndOtherPests *naop = [self.storyboard instantiateViewControllerWithIdentifier:@"NotesAndOtherPestsID"];
        [self.navigationController pushViewController:naop animated:YES];

    }
}

/**
 Finds the threshold from the cost value and control cost. This was from an xml file presented to us. Could be done programmatically but did not have time to do so.
 */
-(void) findCostValueThreshold{
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    if([sharedManager.controlCost  isEqualToString: @"4.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"] || [sharedManager.cropValue isEqualToString:@"3.00"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.50"] ||[sharedManager.cropValue isEqualToString:@"4.00"] || [sharedManager.cropValue isEqualToString:@"4.50"] || [sharedManager.cropValue isEqualToString:@"5.00"] || [sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"]){
            sharedManager.costValueThreshold = 2;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 1;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"5.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"] || [sharedManager.cropValue isEqualToString:@"3.50"] ||[sharedManager.cropValue isEqualToString:@"4.00"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.50"] || [sharedManager.cropValue isEqualToString:@"5.00"] || [sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 2;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"6.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"] || [sharedManager.cropValue isEqualToString:@"3.50"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.00"] || [sharedManager.cropValue isEqualToString:@"4.50"] || [sharedManager.cropValue isEqualToString:@"5.00"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 2;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"7.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 6;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.50"] || [sharedManager.cropValue isEqualToString:@"4.00"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.50"] || [sharedManager.cropValue isEqualToString:@"5.00"] || [sharedManager.cropValue isEqualToString:@"5.50"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 2;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"8.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 6;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"] || [sharedManager.cropValue isEqualToString:@"3.50"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.00"] || [sharedManager.cropValue isEqualToString:@"4.50"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"5.00"] || [sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 2;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"9.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 7;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"]){
            sharedManager.costValueThreshold = 6;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.50"] || [sharedManager.cropValue isEqualToString:@"4.00"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.50"] || [sharedManager.cropValue isEqualToString:@"5.00"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"10.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 8;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"]){
            sharedManager.costValueThreshold = 7;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.50"]){
            sharedManager.costValueThreshold = 6;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.00"] || [sharedManager.cropValue isEqualToString:@"4.50"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"5.00"] || [sharedManager.cropValue isEqualToString:@"5.50"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"11.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 9;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"]){
            sharedManager.costValueThreshold = 7;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.50"] || [sharedManager.cropValue isEqualToString:@"4.00"]){
            sharedManager.costValueThreshold = 6;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.50"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"5.00"] || [sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 3;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
    else if([sharedManager.controlCost  isEqual: @"12.00"]){
        if([sharedManager.cropValue isEqualToString:@"2.50"]){
            sharedManager.costValueThreshold = 9;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.00"]){
            sharedManager.costValueThreshold = 8;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"3.50"]){
            sharedManager.costValueThreshold = 7;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.00"]){
            sharedManager.costValueThreshold = 6;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"4.50"] || [sharedManager.cropValue isEqualToString:@"5.00"]){
            sharedManager.costValueThreshold = 5;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
        else if([sharedManager.cropValue isEqualToString:@"5.50"] || [sharedManager.cropValue isEqualToString:@"6.00"] || [sharedManager.cropValue isEqualToString:@"6.50"] || [sharedManager.cropValue isEqualToString:@"7.00"]){
            sharedManager.costValueThreshold = 4;
            NSLog(@"Cost Value Threshold is %d", sharedManager.costValueThreshold);
        }
    }
}

/**
 Finds the greenbug threshold based off of numbers given to us. This was originally from an xml file but ran out of time to load it programmatically. 
 has different values for spring and fall. Sampler does not work in summer. 
 */
-(void) findGreenbugTreshold{
    SharedDataSingleton *sharedManager = [SharedDataSingleton sharedInstance];
    if (self.month <= 5){
        if (sharedManager.costValueThreshold == 1){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 2;
                sharedManager.greenBugThresholdHigh = 6;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 10;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 11;
                sharedManager.greenBugThresholdHigh = 14;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 15;
                sharedManager.greenBugThresholdHigh = 18;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 19;
                sharedManager.greenBugThresholdHigh = 23;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 23;
                sharedManager.greenBugThresholdHigh = 27;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 2){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 4;
                sharedManager.greenBugThresholdHigh = 9;
                sharedManager.mummyThreshold = 4;
                
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 11;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
                
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 18;
                sharedManager.greenBugThresholdHigh = 23;
                sharedManager.mummyThreshold = 5;
                
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 25;
                sharedManager.greenBugThresholdHigh = 30;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 32;
                sharedManager.greenBugThresholdHigh = 38;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 39;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 3){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 5;
                sharedManager.greenBugThresholdHigh = 12;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 14;
                sharedManager.greenBugThresholdHigh = 21;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 23;
                sharedManager.greenBugThresholdHigh = 30;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 32;
                sharedManager.greenBugThresholdHigh = 39;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 41;
                sharedManager.greenBugThresholdHigh = 48;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 51;
                sharedManager.greenBugThresholdHigh = 57;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 4){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 14;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 16;
                sharedManager.greenBugThresholdHigh = 25;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 27;
                sharedManager.greenBugThresholdHigh = 35;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 37;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 48;
                sharedManager.greenBugThresholdHigh = 56;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 58;
                sharedManager.greenBugThresholdHigh = 66;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 5){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 18;
                sharedManager.greenBugThresholdHigh = 27;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 29;
                sharedManager.greenBugThresholdHigh = 38;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 41;
                sharedManager.greenBugThresholdHigh = 50;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 52;
                sharedManager.greenBugThresholdHigh = 61;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 64;
                sharedManager.greenBugThresholdHigh = 73;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 6){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 7;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 19;
                sharedManager.greenBugThresholdHigh = 29;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 31;
                sharedManager.greenBugThresholdHigh = 41;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 43;
                sharedManager.greenBugThresholdHigh = 53;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 55;
                sharedManager.greenBugThresholdHigh = 66;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 68;
                sharedManager.greenBugThresholdHigh = 78;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 7){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 7;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 20;
                sharedManager.greenBugThresholdHigh = 30;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 32;
                sharedManager.greenBugThresholdHigh = 43;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 45;
                sharedManager.greenBugThresholdHigh = 56;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 58;
                sharedManager.greenBugThresholdHigh = 69;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 71;
                sharedManager.greenBugThresholdHigh = 81;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 8){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 7;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 20;
                sharedManager.greenBugThresholdHigh = 31;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 33;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 47;
                sharedManager.greenBugThresholdHigh = 58;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 60;
                sharedManager.greenBugThresholdHigh = 71;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 73;
                sharedManager.greenBugThresholdHigh = 84;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 9){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 8;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 22;
                sharedManager.greenBugThresholdHigh = 31;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 35;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 49;
                sharedManager.greenBugThresholdHigh = 58;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 62;
                sharedManager.greenBugThresholdHigh = 72;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 76;
                sharedManager.greenBugThresholdHigh = 85;
                sharedManager.mummyThreshold = 8;
            }
        }
        else if (sharedManager.costValueThreshold == 10){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 10;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 24;
                sharedManager.greenBugThresholdHigh = 31;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 38;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 52;
                sharedManager.greenBugThresholdHigh = 58;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 65;
                sharedManager.greenBugThresholdHigh = 72;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 79;
                sharedManager.greenBugThresholdHigh = 86;
                sharedManager.mummyThreshold = 8;
                
            }
        }
        else if (sharedManager.costValueThreshold == 11){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 11;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 25;
                sharedManager.greenBugThresholdHigh = 31;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 39;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 53;
                sharedManager.greenBugThresholdHigh = 59;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 67;
                sharedManager.greenBugThresholdHigh = 73;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 81;
                sharedManager.greenBugThresholdHigh = 87;
                sharedManager.mummyThreshold = 8;
                
            }
        }
        else if (sharedManager.costValueThreshold == 12){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 11;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
                
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 25;
                sharedManager.greenBugThresholdHigh = 31;
                sharedManager.mummyThreshold = 4;
                
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 39;
                sharedManager.greenBugThresholdHigh = 46;
                sharedManager.mummyThreshold = 5;
                
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 53;
                sharedManager.greenBugThresholdHigh = 60;
                sharedManager.mummyThreshold = 6;
                
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 67;
                sharedManager.greenBugThresholdHigh = 74;
                sharedManager.mummyThreshold = 7;
                
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 82;
                sharedManager.greenBugThresholdHigh = 88;
                sharedManager.mummyThreshold = 8;
                
            }
        }
    }
    else if (self.month <= 8){
        UIAlertView *notInSpring = [[UIAlertView alloc]
                                    initWithTitle:@"The Greenbug method is not designed to work in June, July or August." message:@"The season will be set to spring so that you can continue to test the system.  Data collected will be marked as testing data" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [notInSpring show];
    }
    
    else{
        if (sharedManager.costValueThreshold == 1){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 0;
                sharedManager.greenBugThresholdHigh = 5;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 3;
                sharedManager.greenBugThresholdHigh = 7;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 10;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 9;
                sharedManager.greenBugThresholdHigh = 13;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 11;
                sharedManager.greenBugThresholdHigh = 15;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 14;
                sharedManager.greenBugThresholdHigh = 18;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 2){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 7;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 12;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 10;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 15;
                sharedManager.greenBugThresholdHigh = 21;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 19;
                sharedManager.greenBugThresholdHigh = 25;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 24;
                sharedManager.greenBugThresholdHigh = 30;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 3){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 10;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 7;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 13;
                sharedManager.greenBugThresholdHigh = 22;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 19;
                sharedManager.greenBugThresholdHigh = 28;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 25;
                sharedManager.greenBugThresholdHigh = 34;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 31;
                sharedManager.greenBugThresholdHigh = 40;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 4){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 12;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 8;
                sharedManager.greenBugThresholdHigh = 19;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 15;
                sharedManager.greenBugThresholdHigh = 26;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 22;
                sharedManager.greenBugThresholdHigh = 33;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 29;
                sharedManager.greenBugThresholdHigh = 40;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 36;
                sharedManager.greenBugThresholdHigh = 47;
                sharedManager.mummyThreshold = 8;
            }
 
        }
        else if (sharedManager.costValueThreshold == 5){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 14;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 9;
                sharedManager.greenBugThresholdHigh = 22;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 16;
                sharedManager.greenBugThresholdHigh = 30;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 24;
                sharedManager.greenBugThresholdHigh = 37;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 32;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 40;
                sharedManager.greenBugThresholdHigh = 53;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 6){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 15;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 9;
                sharedManager.greenBugThresholdHigh = 24;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 18;
                sharedManager.greenBugThresholdHigh = 33;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 26;
                sharedManager.greenBugThresholdHigh = 41;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 35;
                sharedManager.greenBugThresholdHigh = 50;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 44;
                sharedManager.greenBugThresholdHigh = 59;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 7){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 9;
                sharedManager.greenBugThresholdHigh = 26;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 19;
                sharedManager.greenBugThresholdHigh = 35;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 28;
                sharedManager.greenBugThresholdHigh = 45;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 37;
                sharedManager.greenBugThresholdHigh = 54;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 46;
                sharedManager.greenBugThresholdHigh = 63;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 8){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 1;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 10;
                sharedManager.greenBugThresholdHigh = 28;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 19;
                sharedManager.greenBugThresholdHigh = 38;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 29;
                sharedManager.greenBugThresholdHigh = 47;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 39;
                sharedManager.greenBugThresholdHigh = 57;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 54;
                sharedManager.greenBugThresholdHigh = 67;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 9){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 2;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 12;
                sharedManager.greenBugThresholdHigh = 27;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 22;
                sharedManager.greenBugThresholdHigh = 37;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 21;
                sharedManager.greenBugThresholdHigh = 48;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 42;
                sharedManager.greenBugThresholdHigh = 58;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 53;
                sharedManager.greenBugThresholdHigh = 68;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 10){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 5;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 15;
                sharedManager.greenBugThresholdHigh = 27;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 26;
                sharedManager.greenBugThresholdHigh = 37;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 37;
                sharedManager.greenBugThresholdHigh = 48;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 47;
                sharedManager.greenBugThresholdHigh = 58;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 58;
                sharedManager.greenBugThresholdHigh = 69;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 11){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 15;
                sharedManager.greenBugThresholdHigh = 27;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 27;
                sharedManager.greenBugThresholdHigh = 38;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 38;
                sharedManager.greenBugThresholdHigh = 49;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 49;
                sharedManager.greenBugThresholdHigh = 61;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 60;
                sharedManager.greenBugThresholdHigh = 72;
                sharedManager.mummyThreshold = 8;
            }

        }
        else if (sharedManager.costValueThreshold == 12){
            if (sharedManager.fieldSampleCount == 5){
                sharedManager.greenBugThresholdLow = 6;
                sharedManager.greenBugThresholdHigh = 16;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 10){
                sharedManager.greenBugThresholdLow = 16;
                sharedManager.greenBugThresholdHigh = 28;
                sharedManager.mummyThreshold = 4;
            }
            else if (sharedManager.fieldSampleCount == 15){
                sharedManager.greenBugThresholdLow = 28;
                sharedManager.greenBugThresholdHigh = 40;
                sharedManager.mummyThreshold = 5;
            }
            else if (sharedManager.fieldSampleCount == 20){
                sharedManager.greenBugThresholdLow = 39;
                sharedManager.greenBugThresholdHigh = 51;
                sharedManager.mummyThreshold = 6;
            }
            else if (sharedManager.fieldSampleCount == 25){
                sharedManager.greenBugThresholdLow = 50;
                sharedManager.greenBugThresholdHigh = 62;
                sharedManager.mummyThreshold = 7;
            }
            else if (sharedManager.fieldSampleCount == 30){
                sharedManager.greenBugThresholdLow = 62;
                sharedManager.greenBugThresholdHigh = 74;
                sharedManager.mummyThreshold = 8;
            }

        }
    }
}

//



/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
