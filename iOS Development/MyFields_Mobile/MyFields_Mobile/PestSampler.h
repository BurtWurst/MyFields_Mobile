//
//  PestSampler.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>

/**
 Pest sampler header file. Creates retrieveData method, action method nextButton, and a pest sample array.
 */
@interface PestSampler: UITableViewController

- (void) retrieveData;
@property(strong) NSMutableArray *pestSample;


@end
