//
//  SharedDataSingleton.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/25/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SharedDataSingleton : NSObject{
    
    
}
@property (nonatomic, retain) NSString *cropValue;
@property (nonatomic, retain) NSString *controlCost;
@property (nonatomic, assign) int greenBugThreshold;
@property (nonatomic, assign) int aphidCount;
@property (nonatomic, assign) int mummyCount;
@property (nonatomic, assign) int fieldSampleCount;
@property (nonatomic, assign) int maxFieldSample;
@property (nonatomic, assign) int costValueThreshold;
@property (nonatomic, assign) int fieldSampleThreshold;
@property (nonatomic, assign) int fieldSampleBackCount;
@property (nonatomic, assign) int greenBugThresholdHigh;
@property (nonatomic, assign) int greenBugThresholdLow;
@property (nonatomic, assign) int mummyThreshold;


+ (SharedDataSingleton*) sharedInstance;

@end
