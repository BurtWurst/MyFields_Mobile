//
//  CostCropValueItem.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "ControlCostItem.h"

@interface ControlCostItem()

@end

@implementation ControlCostItem

-(id) initWithControlCost:(NSString *)controlCostVal{
    self = [super init];
    if (self){
        self.controlCost = controlCostVal;
    }
    return self;
}
@end
