//
//  SecondViewController.h
//  ToDoList
//
//  Created by Brett Merriam on 12/11/14.
//
//

#import <UIKit/UIKit.h>

@interface SecondViewController : UIViewController{
    IBOutlet UIScrollView *scroller;
}

- (IBAction)enterFieldOne;

- (IBAction)enterFieldTwo;
//-(IBAction)handleTap:(UIGestureRecognizer *)sender;

//UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(imageTapped)];


@end
