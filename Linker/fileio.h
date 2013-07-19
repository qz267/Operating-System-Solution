//
//  fileio.h
//  Linker
//
//  Created by ZHENG QIN on 9/20/11.
//  Copyright 2011 NYU. All rights reserved.
//

#ifndef Linker_scanner_h
#define Linker_scanner_h
#include<iostream>
#include<string>
#include<vector>
#include<fstream>
#include<sstream>
using namespace std;

typedef struct
{
    string symbol;
    int address;
}
Def_List_Node, Code_Node;

struct Module
{
    int base_address, module_size;
    vector<Def_List_Node> Def_List;
    vector<string> Use_List;
    vector<Code_Node> Code;
};

struct Symbol_Table
{
    string symbol;
    int address, module_number, user_flag;
};

typedef struct
{
    string error_code;
    int error_number, value;
}
Error_Code;

class fileio
{
    
public:
    fileio(void);
    void fileio_Def_List(fstream &file, vector<Def_List_Node> &Def);
    void fileio_Use_List(fstream &file, vector<string> &Usl);
    void fileio_Code(fstream &file, vector<Code_Node> &Code);
    void fileio_Module(fstream &file, vector<Module> &module, int i);
    ~fileio(void);
};
#endif
