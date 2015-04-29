//
//  CropValueItem.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/**
 CropValueItem header file.
 */
@interface CropValueItem : NSObject

@property NSString *cropValue;

/**
 Method for initializing the crop value item. 
 */
-(id) initWithCropValue: (NSString *) cropVal;

@end
