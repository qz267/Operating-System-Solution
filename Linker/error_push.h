//
//  error_push.h
//  Linker
//
//  Created by ZHENG QIN on 9/20/11.
//  Copyright 2011 NYU. All rights reserved.
//

#ifndef Linker_error_push_h
#define Linker_error_push_h
#include<iostream>
#include<vector>
#include<string>
#include"fileio.h"
using namespace std;

class error_push{
private:
    vector<Error_Code> error;
public:
    error_push(void);
    void error_flage(vector<Error_Code> eflag);
    void push();
    ~error_push(void);
};
#endif
