//
//  main.cpp
//  PageReplacement
//
//  Created by ZHENG QIN on 12/5/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include "PageReplacement.h"

int main()
{
	string filename;
	int t = 0;
	int r = 0;
	PageReplacement rp;

	cout << "Page Replacement Policy" << endl << "Enter 0 For FIFO" << endl << "Enter 1 For Second Chance" << endl << "Enter 2 For LRU" << endl;
	cout << "Please select Replacement Policy number:" << endl;
	cin >> r;
	cout << "Please set total memory size in page:" << endl;
	cin >> t;
	cout << "Please enter input file:" << endl;
	cin >> filename;

	rp.IO(t,filename);

	switch(r)
	{
        case 0:
            rp.FIFO(t,filename);
            break;
        case 1:
            rp.SC(t,filename);
            break;
        case 2:
            rp.LRU(t,filename);
            break;
	}

	return 0;
}
