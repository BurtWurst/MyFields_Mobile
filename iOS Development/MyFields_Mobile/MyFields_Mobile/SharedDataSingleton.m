//
//  SharedDataSingleton.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/25/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "SharedDataSingleton.h"


/**
 Singleton that is used throughout the app to store data that is needed at different times but needs to always be there. Global variables that can be called anywhere in the app. 
 */
static SharedDataSingleton *_instance = nil;
@implementation SharedDataSingleton

@synthesize cropValue;
@synthesize controlCost;
@synthesize greenBugThreshold;
@synthesize mummyCount;
@synthesize aphidCount;
@synthesize fieldSampleCount;
@synthesize maxFieldSample;
@synthesize costValueThreshold;
@synthesize fieldSampleThreshold;
@synthesize fieldSampleBackCount;
@synthesize greenBugThresholdHigh;
@synthesize greenBugThresholdLow;
@synthesize mummyThreshold;
@synthesize stopCount;

+(SharedDataSingleton*) sharedInstance{
    @synchronized(self){
        if (_instance == nil){
            _instance = [[self alloc]init];
        }
    }
    return _instance;
}

-(id)init{
    if(self = [super init]){
        cropValue = @"";
        controlCost = @"";
        greenBugThreshold = 0;
        mummyCount = 0;
        aphidCount = 0;
        fieldSampleCount = 0;
        maxFieldSample = 29;
        costValueThreshold = 0;
        fieldSampleThreshold = 4;
        fieldSampleBackCount = 0;
        greenBugThresholdHigh = 0;
        greenBugThresholdLow = 0;
        mummyThreshold = 0;
        stopCount = 1;
        
    }
    return self;
}

-(void) dealloc{
    
}
@end
