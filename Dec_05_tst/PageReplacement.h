/*
 * PageReplacement.h
 *
 *  Created on: Dec 5, 2011
 *      Author: zhengqin
 */

//
//  PageReplacement.h
//  PageReplacement
//
//  Created by ZHENG QIN on 12/5/11.
//  Copyright 2011 NYU. All rights reserved.
//

#pragma once
#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

struct page
{
	int number;
	int rbit;
	page *next;
};

struct lrupage
{
	int number;
	int time;
};

class PageReplacement
{
private:
	page *hd,*tl,*cr;
	int total;
	float pagefault,all;
	vector<int> access;
	vector<int> memorymap;
public:
	PageReplacement(void);
	~PageReplacement(void);
	int IO(int t, string f);
	void FIFO(int t, string f);
	void SC(int t, string f);
	void LRU(int t, string f);
};

