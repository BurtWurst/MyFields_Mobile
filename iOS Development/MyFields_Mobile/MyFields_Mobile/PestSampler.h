//
//  PestSampler.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PestSampler: UITableViewController

- (void) retrieveData;
-(IBAction)nextButton:(UIBarButtonItem *)sender;
@property(strong) NSMutableArray *pestSample;


@end
