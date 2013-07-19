import java.io.*;
import java.util.*;
public class FCFS 
{
	private File file;
	public FCFS() {
		file=new File("C:\\Users\\JYY\\workspace\\scheduler\\input1.txt");
	}
	public Process[] processList;
	public void readfile()
	{
		try 
		{
			Scanner s = new Scanner(file);
			int num = 4;
			processList = new Process[num];
			for (int i = 0; i < num; i++) 
			{	int A = s.nextInt();
				int B = s.nextInt();
				int C = s.nextInt();
				int D = s.nextInt();
				processList[i] = new Process(A, B, C, D);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
/*	debug//output the read file
public static void main(String[] args)
{
	FCFS fcfs = new FCFS();
	fcfs.readfile();
	for(int i=0;i<fcfs.processList.length;i++)
	{
		
		System.out.println(fcfs.processList[i]);	
	}
}*/
public void Schedule()
{
	int time=0;
	int finishedProcess = 0;
	int runningProcess = -1;
	LinkedList<Integer> readyProcess = new LinkedList<Integer>();
	Arrays.sort(processList);
	while (finishedProcess != processList.length) 
	{
		for (int i = 0; i < processList.length; i++) 
		{
			if(time==processList[i].D)
			{
				processList[i].state=Process.stateType.ready;
				readyProcess.offer(i);
			}
		}
		System.out.println(Integer.toString(time)+" ");
		/*if(runningProcess!=-1)
		{
			System.out.print(processList[runningProcess].A +":"+ processList[runningProcess].state);
		}*/
		for (int i = 0; i < processList.length; i++) 
		{
			switch (processList[i].getState())
			{
			case blocked: 
			{
				
				if (processList[i].C > 0) 
				{
					System.out.print(processList[i].A +":"+ processList[i].state);
					processList[i].C--;
				} else {
					processList[i].state = Process.stateType.ready;
					readyProcess.offer(i);
				}
			}//end blocked
			break;
			case ready:
			{
				if(runningProcess==-1){
					runningProcess=readyProcess.poll();
					processList[runningProcess].state=Process.stateType.running;
					System.out.print(processList[runningProcess].A+":"+processList[runningProcess].state);
				}else{
					System.out.print(processList[i].A+":"+processList[i].state);
				}	
				/*for(int j=0;j<processList.length;j++)
						{
						if(j!=i){
							if(processList[j].state==Process.stateType.ready)
							{
								if(i>j)
								{
									runningProcess=readyProcess.poll();
									processList[runningProcess].state=Process.stateType.running;
									System.out.print(processList[i].A+":"+processList[i].state);
									System.out.print(processList[runningProcess].A +":"+ processList[runningProcess].state);
								}else{
									runningProcess=readyProcess.poll();
									processList[runningProcess].state=Process.stateType.running;
									System.out.print(processList[j].A+":"+processList[j].state);
									System.out.print(processList[runningProcess].A +":"+ processList[runningProcess].state);
								}
							}
						}else{runningProcess=readyProcess.poll();
						processList[runningProcess].state=Process.stateType.running;
						System.out.print(processList[runningProcess].A+":"+processList[runningProcess].state);}
						}*/
				}//end ready
			break;
			case running:
			{
				processList[runningProcess].remainingTime--;
				if (processList[runningProcess].remainingTime == processList[runningProcess].B / 2) {
					processList[runningProcess].state = Process.stateType.blocked;
					System.out.print(processList[runningProcess].A +":"+ processList[runningProcess].state);
					runningProcess = -1;			
				} else{
					if(processList[runningProcess].remainingTime==0)
						{						
						finishedProcess++;
						processList[runningProcess].state=Process.stateType.finished;
						runningProcess =-1;
						}
				}
			}//end running
			break;
			default:
				break;
		}//end switch
	}//end for
	time++;
}//end while
}//end schedule
/*
	for(int i=0;i<processList.length;i++)
	{
	switch(processList[i].getState())
	{
	case ready:
		if (runningProcess == -1 && readyProcess.peek() == i) 
		{
			readyProcess.poll();
			runningProcess = i;
			processList[i].setState(Process.stateType.running);
		}
				
	case blocked:
		if (processList[i].C > 0) 
		{
			processList[i].C--;
		} else 
			{
			processList[i].state = Process.stateType.ready;
			readyProcess.offer(i);
			}
	case running:
	}
	}*/
	/*for(int i=0;i<processList.length;i++)
	{
	System.out.println(Integer.toString(time) +" "+ processList[i].A+":"+processList[i].state);
	//if()
	}*/
public static void main(String[] args)
{
	FCFS fcfs = new FCFS();
	fcfs.readfile();
	fcfs.Schedule();
}//end main
}//end class




	
		//processList[num]
		/*
		for(int i=0;i<processList.length;i++)
	{
		if(time==processList[i].D)
		{
			int num=i;
			//import(processList[i])
		//start time=0
		int time=0; 
		//sort arrive time
		for(int i=0;i<4;i++)
		{
			int[] num= new int[]{num1[3],num2[3],num3[3],num4[3]};
			Arrays.sort(num);
			//find the minimal number in num1[3],num2[3],num3[3],num4[3]
			if(num[0]=num1[3])
				
			//find first number in the arrays
			
			//numi[0]
		}
			
			for(int j=0;j<4;j++)
			{
		
				num1[]
			}
		//process the FCFS scheduler
		int comptime=0;
		int runtime=0;
		
		while(comptime<runtime)
			if()
		
		
		for(int i=0; i<, i++)
		{	
			int id;
			
			
			
			System.out.println("i"+id+":"+status+id+":"+status+id+":"+status);
		}
		}
			//turnaround time:	cycle process completed - cycle process arrived + 1 
	*/

