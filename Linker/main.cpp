//
//  main.cpp
//  Linker
//
//  Created by ZHENG QIN on 9/20/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include "fileio.h"
#include "error_push.h"
#include "linker.h"
using namespace std;

int main ()
{
    fileio f;
    linker l;
    error_push error;
    vector<Module> module;
    vector<Symbol_Table> symbole_table;
    vector<Error_Code> error_temp;
    int count = 0, line = 0;
    count = l.linker_first_pass(module, symbole_table, error_temp) + l.linker_second_pass(module, symbole_table, error_temp);
    error.error_flage(error_temp);
    if (count == 0) {
        cout << "SYMBOL TABLE" <<endl;
        for (int i = 0; i < symbole_table.size(); i++) {
            cout << symbole_table[i].symbol << "=" << symbole_table[i].address <<endl;
        }
        cout << "MEMORY MAP" << endl;
        for (int i = 0; i < module.size() ; i++) {
            for (int k = 0; k < module[i].module_size; k++) {
                cout << line++ <<" "<< module[i].Code[k].address << endl;
            }
        }
        cout << endl;
    }
    fstream file("log.txt");
    if (!file) {
        cout << "CAN NOT OPEN LOG FILE" << endl;
        return 0;
    }
    int eom = 0;
    while (!file.eof()) {
        f.fileio_Module(file, module, eom);
        eom++;
    }
    error.push();
    file.close();
    return 1;
}

