//
//  scanner.cpp
//  Linker
//
//  Created by ZHENG QIN on 9/20/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include <iostream>
#include "fileio.h"

fileio::fileio(void)
{
    
}
fileio::~fileio(void)
{
    
}

void fileio::fileio_Module(fstream &f, vector<Module> &module, int i)
{
    Module module_temp;
    fileio_Def_List(f , module_temp.Def_List);
    fileio_Use_List(f , module_temp.Use_List);
    fileio_Code(f , module_temp.Code);
    module.push_back(module_temp);
}

void fileio::fileio_Def_List(fstream &file, vector<Def_List_Node> &Def)
{
    int def=0;
    file >> def;
    if (def!=0) {
        for (int i ; i < def ; i++) {
            Def_List_Node Def_temp;
            file >> Def_temp.symbol >> Def_temp.address;
            Def.push_back(Def_temp);
        }
    }
}

void fileio::fileio_Use_List(fstream &file, vector<string> &Usl)
{
    int usl;
    file >> usl;
    for (int i = 0; i < usl; i++) {
        string Usl_temp;
        file >> Usl_temp;
        Usl.push_back(Usl_temp);
    }
}

void fileio::fileio_Code(fstream &file, vector<Code_Node> &Code)
{
    int code;
    file >> code;
    for (int i = 0; i < code; i++) {
        Code_Node code_temp;
        file >> code_temp.symbol >> code_temp.address;
        Code.push_back(code_temp);
    }
}