//
//  FieldItem.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/9/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FieldItem : NSObject

@property NSString *iD;
@property NSString *fieldName;
@property NSString *location;
@property NSString *size;
@property NSString *typeOfSoil;
@property NSString *tillageSystem;
@property NSString *irrigationSystem;
//array
@property NSString *plantingList;
//array
@property NSString *pestSamples;

-(id) initWithFieldName: (NSString *) fID andFieldName: (NSString *) fName andFieldLocation: (NSString *) fLocation andFieldSize: (NSString *) fSize andFieldSoil: (NSString *) fSoil andFieldTillage: (NSString *) fTillage andFieldIrrigation: (NSString *) fIrrigation;


@end
