//
//  iOSUtility.m
//  Unity-iPhone
//
//  Created by dtknowlove on 2020/1/15.
//
//

void outputAppendString(char *str1,char *str2)
{
    NSString *string1=[[NSString alloc] initWithUTF8String:str1];
    NSString *string2=[[NSString alloc] initWithUTF8String:str2];
   
    NSString *result=[NSString stringWithFormat:@"%@ %@",string1,string2];
    NSLog(@"输出字符串:%@",result);
    UnitySendMessage("GameCamera","Callback",result.UTF8String);
}

typedef void (*ResultHandler)(const char *object);

void outputAppendString2(char *str1,char *str2,ResultHandler resultHander)
{
    NSString *string1=[[NSString alloc] initWithUTF8String:str1];
    NSString *string2=[[NSString alloc] initWithUTF8String:str2];
   
    NSString *result=[NSString stringWithFormat:@"%@ %@",string1,string2];
    NSLog(@"输出字符串:%@",result);
    resultHander(result.UTF8String);
}
