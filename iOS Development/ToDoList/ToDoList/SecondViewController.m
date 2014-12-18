//
//  SecondViewController.m
//  ToDoList
//
//  Created by Brett Merriam on 12/11/14.
//
//

#import "SecondViewController.h"
#import "FirstFieldViewController.h"
#import "SecondFieldViewController.h"


@interface SecondViewController ()

@end

@implementation SecondViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [scroller setScrollEnabled:YES];
    [scroller setContentSize:CGSizeMake(320, 1000)];
    
    //UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTap:)];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)enterFieldOne{
    FirstFieldViewController *firstField = [self.storyboard instantiateViewControllerWithIdentifier:@"FirstField"];
    [self.navigationController pushViewController:firstField animated:YES];
    
}

- (IBAction)enterFieldTwo{
    FirstFieldViewController *secondField = [self.storyboard instantiateViewControllerWithIdentifier:@"SecondField"];
    [self.navigationController pushViewController:secondField animated:YES];
    
}

//- (void)tapAction:(UITapGestureRecognizer *)tapGesture {
    //UIImageView *imageTapped = (UIImageView *)tapGesture.view;
    //delete it using removeFromSuperView or do whatever you need with tapped label
    
//}

//-(IBAction)handleTap:(UIGestureRecognizer *)sender{
    //if(sender.state == UIGestureRecognizerStateEnded){
        //UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"file1721269013523.jpg"]];
        //imageView.userInteractionEnabled = YES;
    
        //UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapAction:)];
        //[imageView addGestureRecognizer:tapGesture];
        //[self.view addSubview:imageView];
        
        //FirstFieldViewController *firstFieldController = [[FirstFieldViewController alloc] //initWithNibName:@"firstField" bundle:nil];
        //[self.navigationController pushViewController:firstFieldController animated:YES];
    //}
//}



/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end




































