//
//  error_push.cpp
//  Linker
//
//  Created by ZHENG QIN on 9/20/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include <iostream>
#include <vector>
#include <string>
#include "error_push.h"
using namespace std;

error_push::error_push(void)
{
    
}
error_push::~error_push(void)
{
    
}

void error_push::error_flage(vector<Error_Code> eflag)
{
     error = eflag;
}
void error_push::push()
{
    for (int i ; i < error.size(); i++) {
        switch (error[i].error_number) {
            case 1:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].error_code  << ". " << "IT IS MULTIPLY DEFINIED!" << endl;
                break;
            case 2:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].error_code  << ". " << "IT IS UNDEFINIED" << endl;;
                break;
            case 3:
                cout << "WARNING! WARNING CONTAINT IS " << error[i].error_code  << ". " << "IT IS DEFINIED BUT NOT USED!" << endl;
                break;
            case 4:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].error_code  << ". " << error[i].value << " IS OVERFLOWED MODULE!" << endl;
                break;
            case 5:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].error_code  << ". " << error[i].value << "IS TOO LARGE TO REFERENCE AN ENTER IN THE USE LIST!" << endl;
                break;
            case 6:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].error_code  << ". " << "IT IS NOT USED IN THE MODULE!";
                break;
            case 7:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].value  << ". " << "IT EXCEEDS THE SIZE OF THE MACHINE!";
                break;
            case 8:
                cout << "ERROR! ERROR CONTAINT IS " << error[i].value  << ". " << "IT EXCEEDS THE SIZE OF THE MODULE!";
                break;
            default:
                break;
        }
    }
}