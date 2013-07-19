//
//  linker.h
//  Linker
//
//  Created by ZHENG QIN on 9/20/11.
//  Copyright 2011 NYU. All rights reserved.
//

#ifndef Linker_linker_h
#define Linker_linker_h
#include<iostream>
#include<vector>
#include<string>
#include"fileio.h"
class linker
{
public:
    linker(void);
    int linker_first_pass(vector<Module> &module, vector<Symbol_Table> &symbol_table, vector<Error_Code> &error_code);
    int linker_second_pass(vector<Module> &module, vector<Symbol_Table> &symbol_table, vector<Error_Code> &error_code);
    int symbol_check(Def_List_Node def, vector<Symbol_Table> &symbol_table);
    void error_check(int type, int value, int module_number, string symbol, vector<Error_Code> &error_code);
    ~linker(void);
};
#endif
