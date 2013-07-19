//
//  RR.cpp
//  FCFS
//
//  Created by ZHENG QIN on 11/6/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include <stdlib.h>
#include <iomanip>
#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <deque>
#include <cstring>

using namespace std;

class Process2 {	
public:
	int arrivalTime;
	int CPUBurst;
	int totalCPU;
	int IOBurst;
	int CPUcount;
	int finishingTime;
	int IOTime;
	int waitingTime;
	int runningTime;
	int blockedTime;
	int leftRunningTime; //just for RR
	string state; //state can be only one of {unstarted, running, blocked, ready, terminated}
	Process2 (int at, int cb, int tc, int iob) {
		arrivalTime = at;
		CPUBurst = cb;
		totalCPU = tc;
		IOBurst = iob;
		state = "unstarted";
		finishingTime = 0;
	    IOTime = 0;
	    waitingTime = 0;	
		CPUcount = 0;
		leftRunningTime = 0;
	}
	
	bool   operator <  (const Process2& prs) const {  
		return arrivalTime < prs.arrivalTime;
    }
};

class Cycle2 {
public:
	int cycleNum; //current cycle'th
	int termNum; //the number of terminated process
};

deque<int> readyPDeque2; //the running process in each cycle
vector<Process2> pVector;
bool isDetailed2 = false;

string readToken2 (ifstream* infi) {
	char ch;
	string strToken;
	bool isNext = false;
	while (infi -> get(ch)) {
		if ((int)ch != 10 && (int)ch != 32 && (int)ch != 9 && (int)ch != 40 && (int)ch != 41) {
			strToken += ch;
			isNext = true;
		}
		else if (isNext)
			break;
	}
	return strToken;
}

int randomOS2 (int U, ifstream* rn) {
	string randNum;
	randNum = readToken2(rn);
	return 1+atoi(randNum.c_str())%U;
}

void RR (ifstream* infi, ifstream* rn) {
	int remainingTime = 1;
	Cycle2* cycle = new Cycle2();
	int processNum = atoi(readToken2(infi).c_str());
	for (int i=0;i<processNum;i++) {
		int at = atoi(readToken2(infi).c_str());
		int cb = atoi(readToken2(infi).c_str());
		int ct = atoi(readToken2(infi).c_str());
		int iob = atoi(readToken2(infi).c_str());
		pVector.push_back(Process2(at, cb, ct, iob));
	}
	
	//before priority
	cout<<"The original input was: "<<processNum;
	for(int i=0; i<processNum; i++) {
		cout<<" ("<<pVector[i].arrivalTime<<" "<<pVector[i].CPUBurst<<" "<<pVector[i].totalCPU<<" "<<pVector[i].IOBurst<<")";
	}
	cout<<endl;
	sort(pVector.begin(), pVector.end());
	
	//after priority
	cout<<"The (sorted) input is:  "<<processNum;
	for(int i=0; i<processNum; i++) {
		cout<<" ("<<pVector[i].arrivalTime<<" "<<pVector[i].CPUBurst<<" "<<pVector[i].totalCPU<<" "<<pVector[i].IOBurst<<")";
	}
	cout<<endl;
	cout<<endl;
	if (isDetailed2)
        cout<<"This detailed printout gives the state and remaining burst for each process"<<endl;
	cout<<endl;
	//cycle begins
	int IOCount = 0;
	cycle->cycleNum = 0;
	cycle->termNum = 0;
	while (cycle->termNum != processNum) {
		int IOUtilization = 0;
		if (isDetailed2)
            cout<<"Before cycle  "<<setw(2)<<cycle->cycleNum<<":"; 
        
		for (int i=0; i<processNum; i++) {
			if (isDetailed2)
                cout<<"  "<<setw(11)<<pVector[i].state<<"  ";
			if (pVector[i].state == "unstarted") {
				if (isDetailed2)
                    cout<<0;
				if (pVector[i].arrivalTime == cycle->cycleNum) {
					pVector[i].state = "ready";
					readyPDeque2.push_back(i);
				}
			}
			else if (pVector[i].state == "running") {
				remainingTime = pVector[i].runningTime;
				if (isDetailed2)
                    cout<<remainingTime;
				if (pVector[i].totalCPU != 1) {
					pVector[i].totalCPU--;
					pVector[i].CPUcount++;
					if (pVector[i].runningTime != 1)
						pVector[i].runningTime--;	
					else if (pVector[i].leftRunningTime != 0) {
						pVector[i].state = "ready";
						readyPDeque2.push_back(i);
					}
					else {
						pVector[i].state = "blocked";
						pVector[i].blockedTime = randomOS2(pVector[i].IOBurst, rn);
					}
				}
				else {
					pVector[i].state = "terminated";
					pVector[i].finishingTime = cycle->cycleNum;
					cycle->termNum++;
					remainingTime = 1;
				}
			}
			else if (pVector[i].state == "blocked") {
                IOUtilization++;
                if (isDetailed2)
                    cout<<pVector[i].blockedTime;
				pVector[i].IOTime++;
				if (pVector[i].blockedTime != 1)
					pVector[i].blockedTime--;
				else {
					pVector[i].state = "ready";
					readyPDeque2.push_back(i);
				}
			}
			else if (pVector[i].state == "ready") {
				if (isDetailed2)
                    cout<<0;
				pVector[i].waitingTime++;
			}
			else if (pVector[i].state == "terminated")
				if (isDetailed2)
                    cout<<0;
		}
        
		//Count I/O
		if (IOUtilization > 0)
			IOCount++;
        
		if (!readyPDeque2.empty() && remainingTime == 1) {
			int pToRun = readyPDeque2[0];
			pVector[pToRun].state = "running";
			if (pVector[pToRun].leftRunningTime != 0) {
				pVector[pToRun].runningTime = pVector[pToRun].leftRunningTime;
				pVector[pToRun].leftRunningTime = 0;
			}
			else pVector[pToRun].runningTime = randomOS2(pVector[pToRun].CPUBurst, rn);
			if (pVector[pToRun].runningTime > 2) {
				pVector[pToRun].leftRunningTime = pVector[pToRun].runningTime - 2;
				pVector[pToRun].runningTime = 2;
			}
			readyPDeque2.pop_front();
		}
		if (isDetailed2)
            cout<<"."<<endl;
		cycle->cycleNum++;
	}
    
	cout<<"The scheduling algorithm used was Round Robin with quantum 2"<<endl;
	cout<<endl;
    
	int CPUtilization = 0;
	int IOUtilization = 0;
	int totalTurnaround = 0;
	int totalWaiting = 0;
	for (int i=0; i<processNum; i++) {
		cout<<"Process "<<i<<":"<<endl;
		cout<<"          (A,B,C,IO) = ("<<pVector[i].arrivalTime<<","<<pVector[i].CPUBurst<<","<<pVector[i].CPUcount+1<<","<<pVector[i].IOBurst<<")"<<endl;
		cout<<"          Finishing time: "<<pVector[i].finishingTime<<endl;
		cout<<"          Turnaround time: "<<pVector[i].finishingTime-pVector[i].arrivalTime<<endl;
		cout<<"          I/O time: "<<pVector[i].IOTime<<endl;
		cout<<"          Waiting time: "<<pVector[i].waitingTime<<endl;
		cout<<endl;
        
		CPUtilization += (pVector[i].CPUcount+1);
		totalTurnaround += pVector[i].finishingTime-pVector[i].arrivalTime;
		totalWaiting += pVector[i].waitingTime;
	}
    
	cout<<"Summary Data:"<<endl;
	cout<<"          Finishing time: "<<cycle->cycleNum-1<<endl;
	cout<<"          CPU Utilization: "<<fixed<<setprecision(6)<<setiosflags(ios::showpoint)<<(double)CPUtilization/(cycle->cycleNum-1)<<endl;
	cout<<"          I/O Utilization: "<<(double)IOCount/(cycle->cycleNum-1)<<endl;
	cout<<"          Throughput: "<<(double)processNum*100/(cycle->cycleNum-1)<<" processes per hundred cycles"<<endl;
	cout<<"          Average turnaround time: "<<(double)totalTurnaround/processNum<<endl;
	cout<<"          Average waiting time: "<<(double)totalWaiting/processNum<<endl;
    
}

int main(int argc, char* argv[]) {
	string fileName1 = "random-numbers";
	ifstream rn(fileName1.c_str());
	string fileName2;
	if (strcmp(argv[1],(char*)"--verbose") == 0) {
		isDetailed2 = true;
		fileName2 = argv[2];}
	else fileName2 = argv[1];
	ifstream infi(fileName2.c_str());
	RR(&infi, &rn);
	cout<<endl;
	cout<<"Press Enter to Quit";
	getchar();
	return 0;
}

