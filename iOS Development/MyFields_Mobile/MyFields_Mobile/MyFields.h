//
//  MyFields.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FieldItem.h"

/**
 MyFields header file. Creates a field list mutable array for storing the list that is created when the data in the database is pulled and jsonized. Creates an index for keeping track of which field is selected for viewing all field data. Creates a shared field item for showing detailed view of all field data. 
 Has a currently unused method of unwindToList which is used when creating a new field in app. May or may not be included in final release. 
 Creates a retrieveData method which is used to pull data from the file written to the user profile on the phone and jsonize it.
 */
@interface MyFields: UITableViewController{
    
    NSMutableArray *fieldList;
    int fieldIndex;
    FieldItem *shareFieldObject;
}

- (IBAction)unwindToList:(UIStoryboardSegue *)segue;

- (void) retrieveData;

@property(strong) NSMutableArray *fieldList;
@property int fieldIndex;
@property(strong) FieldItem *shareFieldObject;

@end
