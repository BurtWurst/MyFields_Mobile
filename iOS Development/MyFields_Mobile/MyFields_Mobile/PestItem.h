//
//  PestItem.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 3/31/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/**
 PestItem header file.
 */
@interface PestItem : NSObject

@property NSString *pestName;

/**
 Method for initializing pest item with a name. 
 */
-(id) initWithPestName: (NSString *) pName;

@end
