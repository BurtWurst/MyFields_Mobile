//
//  FieldItem.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/9/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "FieldItem.h"

@interface FieldItem ()

@end

@implementation FieldItem

-(id) initWithFieldName: (NSString *) fID andFieldName: (NSString *) fName andFieldLocation: (NSString *) fLocation andFieldSize: (NSString *) fSize andFieldSoil: (NSString *) fSoil andFieldTillage: (NSString *) fTillage andFieldIrrigation: (NSString *) fIrrigation{
    
    self = [super init];
    if (self){
        self.iD = fID;
        self.fieldName = fName;
        self.location = fLocation;
        self.size = fSize;
        self.typeOfSoil = fSoil;
        self.tillageSystem = fTillage;
        self.irrigationSystem = fIrrigation;
    }
    return self;
}

@end

