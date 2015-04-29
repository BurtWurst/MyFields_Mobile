//
//  ViewFieldData.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 4/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MyFields.h"

/**
 ViewFieldData header file. Creates a pointer to the MyFields view controller. That way, the field list that was created there can be used in this view controller. 
 */
@interface ViewFieldData : UITableViewController{
    
    MyFields *pointerToMyFields;
    
}

@property(nonatomic, retain) MyFields *pointerToMyFields;
@property(strong) NSMutableArray *selectedFieldList;
@end
