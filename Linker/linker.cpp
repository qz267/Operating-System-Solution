//
//  linker.cpp
//  Linker
//
//  Created by ZHENG QIN on 9/26/11.
//  Copyright 2011 NYU. All rights reserved.
//
//  THIS IS CORE CALCULATOR PART OF LINKER, CONTIAN 2 PASSES PROCESSES, SYMBOL AND ERROR CHECK. 
//

#include <iostream>
#include <vector>
#include <string>
#include "fileio.h"
#include "linker.h"

using namespace std;

linker::linker(void){
    
}//constructure function
linker::~linker(void){
    
}//distructure function

void linker::error_check(int type, int value, int module_number, string symbol, vector<Error_Code> &error_code)
{
    Error_Code error_temp;
    error_temp.error_code = symbol;
    error_temp.error_number = type;
    error_temp.value = value;
    error_code.push_back(error_temp);
}// this function checks errors, record their information. 

int linker::symbol_check(Def_List_Node def, vector<Symbol_Table> &symbol_table)
{
    if (symbol_table.size() == 0) {
        return 1;
    }
    for (int i = 0; i < symbol_table.size(); i++) {
        if (def.symbol.compare(symbol_table[i].symbol) == 0) {
            return 0;
        }
    }
    return 1;
}
// this function checks symbols, to judge if two variables are same.

int linker::linker_first_pass(vector<Module> &module, vector<Symbol_Table> &symbol_table, vector<Error_Code> &error_code)
{
    int n=0 ,m=0;
    for (int i ; i < module.size(); i++) {
        module[i].module_size = module[i].Code.size();
        module[i].base_address = m;
        for (int j; j < module[i].Def_List.size(); j++) {
            Symbol_Table st;
            Def_List_Node deftemp = module[i].Def_List[j];
            if (deftemp.address > module[i].module_size) {
                n = 1;
                error_check(4, deftemp.address, module[i].module_size, deftemp.symbol, error_code);
            }// end if
            st.user_flag = 0;
            st.symbol = deftemp.symbol;
            st.address = deftemp.address;
            st.module_number = i;
            if (!symbol_check(deftemp, symbol_table)) {
                n = 1;
                error_check(1, deftemp.address, -1, deftemp.symbol, error_code);
            }
            else
                symbol_table.push_back(st);
        }//end for
        m += module[i].module_size;
    }
    return n;
}// this function complete first pass of modules.

int linker::linker_second_pass(vector<Module> &module, vector<Symbol_Table> &symbol_table, vector<Error_Code> &error_code)
{
    int n = 0;
    for (int i = 0 ; i < module.size(); i++) 
    {
        vector<int> k;
        for (int h; h < module[i].Code.size(); h++) 
        {
            k.push_back(0);
            for (int l = 0; l < symbol_table.size(); l++) 
            {
                if (module[i].Use_List[h].compare(symbol_table[l].symbol) == 0) 
                {
                    symbol_table[l].user_flag = 1;
                }// end if
            }//endfor
        }//endfor
        for (int j = 0; j < module[i].Code.size(); j++) 
        {
            if (module[i].Code[j].symbol.compare("R" )== 0) 
            {
                if ((module[i].Code[j].address % 1000) >= module[i].module_size) 
                {
                    n = 1;
                    error_check(8, module[i].Code[j].address, i, module[i].Code[j].symbol, error_code);
                }//endif
                module[i].Code[j].address += module[i].base_address;
            }//endif
            else 
                if (module[i].Code[j].symbol.compare("E")==0)
            {
                    int p = module[i].Code[j].address % 1000;
                    string st;
                    if (p >= module[i].Use_List.size())
                    {
                        n = 1;
                        error_check(5, module[i].Code[j].address, i, module[i].Code[j].symbol, error_code);
                    }
                    else
                    {
                        k[p] = 1;
                        st = module[i].Use_List[p];
                        int ttemp = 0;
                        for (int f = 0; f < symbol_table.size(); f++)
                        {
                            if (symbol_table[f].symbol.compare(st)==0) 
                            {
                                int i1 = module[i].Code[j].address;
                                module[i].Code[j].address = (i1 / 1000) * 1000 + symbol_table[f].address;
                                if ((module[i].Code[j].address %1000) >= 600)
                                {
                                    n = 1;
                                    error_check(7, i1, i, module[i].Code[j].symbol, error_code);
                                }//endif
                                ttemp = 1;
                            }//endif
                        }//endfor
                        if (ttemp == 0) 
                        {
                            n = 1;
                            error_check(2, -1, i, st, error_code);
                        }//endif
                    }//end else
            }//end elseif
        }//endfor
    for (int j = 0; j < k.size(); j++) 
    {
        if (k[j] == 0) 
        {
            error_check(6, -1, i, module[i].Use_List[j], error_code);
        }
    }
    for (int i = 0; i < symbol_table.size(); i++) 
    {
        if (symbol_table[i].user_flag == 0) 
        {
            error_check(3, symbol_table[i].address, symbol_table[i].module_number, symbol_table[i].symbol, error_code);
        }
    }
    }
    return n; 
}// this function complete second pass of modules.






















