//
//  MyFields.h
//  MyFields_Mobile
//
//  Created by Brett Merriam on 2/8/15.
//  Copyright (c) 2015 MyFields Dev Team. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FieldItem.h"

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
