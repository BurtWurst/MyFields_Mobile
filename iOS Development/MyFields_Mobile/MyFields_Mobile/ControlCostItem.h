//
//  CostCropValueItem.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/**
 ControlCostItem header file.
 */
@interface ControlCostItem : NSObject

@property NSString *controlCost;

/**
 Method for initializing the control cost value. 
 */
-(id) initWithControlCost: (NSString *) controlCostVal;

@end
