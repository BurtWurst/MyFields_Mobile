//
//  PestItem.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 3/31/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "PestItem.h"

/**
 PestItem implementation file.
 */
@interface PestItem()

@end

@implementation PestItem

/**
 Method for initializing the name string of a pest item. 
 */
-(id) initWithPestName: (NSString *) pName{
    
    self = [super init];
    if (self){
        self.pestName = pName;

    }
    return self;
}

@end
