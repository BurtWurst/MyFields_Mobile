//
//  FieldItem.m
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/9/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import "FieldItem.h"

/**
 FieldItem implementation file.
 */
@interface FieldItem ()

@end

@implementation FieldItem

/**
 Method for initializing all field items data fields. 
 */
-(id) initWithFieldName: (NSString *) fID andFieldName: (NSString *) fName andFieldLocation: (NSString *) fLocation andFieldSize: (NSString *) fSize andFieldSoil: (NSString *) fSoil andFieldTillage: (NSString *) fTillage andFieldIrrigation: (NSString *) fIrrigation andPlantingList:(NSMutableArray*) fPlantingList andFieldSamples: (NSMutableArray *) fPestSamples{
    
    self = [super init];
    if (self){
        self.iD = fID;
        self.fieldName = fName;
        self.location = fLocation;
        self.size = fSize;
        self.typeOfSoil = fSoil;
        self.tillageSystem = fTillage;
        self.irrigationSystem = fIrrigation;
        self.plantingList = fPlantingList;
        self.pestSamples = fPestSamples;
    }
    return self;
}

@end

