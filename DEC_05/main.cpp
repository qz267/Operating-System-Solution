#include "PageReplacement.h"

int main()
{
	string filename;
	int t = 0;
	int r = 0;
	PageReplacement rp;

	cout << ""
	cout << "Please enter PageReplacement Policy number:" << endl;
	cin >> r;
	cout << "Please enter total memory size in pages:" << endl;
	cin >> t;
	cout << "Plense enter access pattern file name(eg. in1.txt):" << endl;
	cin >> filename;

	rp.scanfile(t,filename);

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
	}

	return 0;
}