//
//  PageReplacement.cpp
//  PageReplacement
//
//  Created by ZHENG QIN on 12/5/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include "PageReplacement.h"
#include "iostream.h"
#include "iomanip.h"

PageReplacement::PageReplacement(void)
{
	hd = tl = cr = NULL;
	total = 0;
	pagefault = all = 0;
}

PageReplacement::~PageReplacement(void)
{
}

int PageReplacement::IO(int t, string f)
{
	fstream file;
	file.open(f.c_str());
	if(!file)
	{
		cout << "Can not open file!" << endl;
		file.close();
		return 0;
	}
	else
	{
		while(!file.eof())
		{
			int temp;

			file >> temp;
			if(file.fail())
				break;
			access.push_back(temp);
		}
		file.close();
		return 1;
	}
}

void PageReplacement::FIFO(int t, string fn)
{
	total = t;
	string filename = fn.substr(0,fn.length()-4);
	filename.append(".FIFO.txt");
	ofstream outfile(filename.c_str());
	for(int i = 0; i < access.size(); i++)
	{

		int flag = 0;
		for( int j = 0; j < memorymap.size(); j++)
		{
			if(access[i] == memorymap[j])
				flag = 1;
		}

		if(flag == 0)
		{
			pagefault++;

			if(memorymap.size() < total)
			{
				memorymap.push_back(access[i]);

				page *p = new page;
				p->number = access[i];
				p->rbit = 0;
				p->next = NULL;

				if(hd == NULL)
				{
					hd = tl = p;
				}
				else
				{
					tl->next = p;
					tl = p;
				}
			}
			else
			{
				page *pg = hd;
				hd = hd->next;

				for(int k = 0; k < memorymap.size(); k++)
				{
					if(pg->number == memorymap[k])
						memorymap[k] = access[i];
				}

				pg->number = access[i];
				pg->next = NULL;
				tl->next = pg;
				tl = pg;
			}
		}

		for( int j = 0; j < memorymap.size(); j++)
		{
			outfile << memorymap[j] << " ";
		}
		outfile << endl;
	}

	all = access.size();
	double rs = pagefault/all;
	outfile <<  endl << "Percentage of Page faults = " <<setiosflags(ios::fixed)<< setprecision(2) << rs << endl;
}

void PageReplacement::LRU(int t, string fn)
{
	vector<lrupage> mm;
	total = t;
	string filename = fn.substr(0,fn.length()-4);
	filename.append(".LRU.txt");
	ofstream outfile(filename.c_str());
	for(int i = 0; i < access.size(); i++)
	{

		int flag = 0;
		for( int j = 0; j < mm.size(); j++)
		{
			if(access[i] == mm[j].number)
			{
				flag = 1;
				mm[j].time = i;
			}
		}

		if(flag == 0)
		{
			pagefault++;

			if(mm.size() < total)
			{
				lrupage p;
				p.number = access[i];
				p.time = i;
				mm.push_back(p);
			}
			else
			{
				int mtemp = 0;
				int ttemp = mm[0].time;
				for(int k = 0; k < mm.size(); k++)
				{
					if(ttemp > mm[k].time)
					{
						ttemp = mm[k].time;
						mtemp = k;
					}
				}

				mm[mtemp].number = access[i];
				mm[mtemp].time = i;
			}
		}

		for( int j = 0; j < mm.size(); j++)
		{
			outfile << mm[j].number << " ";
		}
		outfile << endl;
	}

	all = access.size();
	double rs = pagefault/all;
	outfile <<  endl << "Percentage of Page faults = " <<setiosflags(ios::fixed)<< setprecision(2) << rs << endl;
}

void PageReplacement::SC(int t, string fn)
{
	total = t;
	string filename = fn.substr(0,fn.length()-4);
	filename.append(".SC.txt");
	ofstream outfile(filename.c_str());
	for(int i = 0; i < access.size(); i++)
	{
		int flag = 0;

		for( int j = 0; j < memorymap.size(); j++)
		{
			if(access[i] == memorymap[j])
				flag = 1;
		}

		if(flag == 0)
		{

			pagefault++;

			if(memorymap.size() < total)
			{
				memorymap.push_back(access[i]);

				page *p = new page;
				p->number = access[i];
				p->rbit = 0;
				p->next = NULL;

				if(hd == NULL)
				{
					hd = tl = p;
				}
				else
				{
					tl->next = p;
					tl = p;
				}
			}
			else
			{
				page *pg = hd;

				while(hd->rbit == 1)
				{
					pg = hd;
					hd = hd->next;
					pg->rbit = 0;
					pg->next = NULL;
					tl->next = pg;
					tl = pg;
				}

				pg = hd;
				hd = hd->next;

				for(int k = 0; k < memorymap.size(); k++)
				{
					if(pg->number == memorymap[k])
						memorymap[k] = access[i];
				}
				pg->number = access[i];
				pg->next = NULL;
				tl->next = pg;
				tl = pg;
			}
		}
		else
		{
			page *ptemp = hd;

			while(ptemp->number != access[i])
				ptemp = ptemp->next;
			ptemp->rbit = 1;
		}

		for( int j = 0; j < memorymap.size(); j++)
		{
			outfile << memorymap[j] << " ";
		}
		outfile << endl;
	}

	all = access.size();
	double rs = pagefault/all;
	outfile <<  endl << "Percentage of Page faults = " <<setiosflags(ios::fixed)<< setprecision(2) << rs << endl;
}

