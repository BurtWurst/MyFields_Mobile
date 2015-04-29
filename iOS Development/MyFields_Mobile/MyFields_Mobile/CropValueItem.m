//
//  CropValueItem.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "CropValueItem.h"

/**
 CropValueItem implementation file.
 */
@interface CropValueItem()

@end

@implementation CropValueItem

/**
 Method for initializing the cropval string. 
 */
-(id) initWithCropValue: (NSString *) cropVal{
    self = [super init];
    if (self){
        self.cropValue = cropVal;
    }
    return self;
}
@end
