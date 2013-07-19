import java.io.*;
import java.util.*;
import java.text.*;


public class FCFS 
{
	
	IO io = new IO();
	int[][] processTable = io.processTable;
	int cpu = -1;
	ArrayList ready = new ArrayList();
	ArrayList block = new ArrayList();
	int[] priority  = new int[io.numberOfProcess];
	int p = io.numberOfProcess;
	int time = 0;
	int[] turnaround  = new int[io.numberOfProcess];
	int utilization;
	BufferedWriter output = new BufferedWriter(new FileWriter("FCFS.txt"));
	
	FCFS() throws IOException
	{
	}
	
	public void fcfs() throws IOException
	{
		int x = 0;
		while(ready.isEmpty() == false || block.isEmpty() == false || cpu != -1 || arrive() == true )
		{
			second();
			time++;
		}
		statistics();
		output.close();
		System.out.println("The result of First-Come-First-Served was written in \"FCFS.txt\"");
	}
	
	public void second() throws IOException
	{				
		
		
		for(int i = 0; i < block.size(); i++)
		{
			int temp = Integer.parseInt(block.get(i).toString());
			if(processTable[temp][2] == 0)
			{
				//System.out.println(temp + " will be removed");
				block.remove(Integer.valueOf(temp));
				ready.add(temp);
			}
		}
		
		int readyToRun = findPriority(ready);
		
		if(cpu != -1 && priority[cpu] < priority[readyToRun])
		{
			//System.out.println("readyToRun : " + readyToRun);
			//System.out.println("cpu : " + cpu);
			if(processTable[cpu][2] > 0)
				block.add(cpu);
			else
				ready.add(cpu);
			ready.remove(Integer.valueOf(readyToRun));
			cpu = readyToRun;
			//System.out.println("cpu : " + cpu);
		}
		
		if(cpu != -1)
		{
			if(processTable[cpu][1] < 0)
			{
				if(processTable[cpu][3] > 0)
				{
					processTable[cpu][3]--;
					//System.out.println("cpu=" + cpu + "; [cpu][3]=" + processTable[cpu][3]);
				}
				else if(processTable[cpu][3] == 0)
				{
					cpu = -1;
					//System.out.println("cpu=");
				}
			}			
			else if(processTable[cpu][1] > 0)
			{
				processTable[cpu][1]--;
			}
			else if(processTable[cpu][1] == 0)
			{
				processTable[cpu][1]--;
				
				if(processTable[cpu][2] > 0)
				{
					block.add(cpu);
					cpu = -1;	
				}
				else if(processTable[cpu][2] == 0)
				{
					processTable[cpu][3]--;
				}
				//System.out.println("!@#");
			}
		}
		
		
		
		for(int i = 0; i < io.numberOfProcess; i++)
		{
			if(processTable[i][4] == 0)
			{
				priority[i] = p;
				p--;
			    ready.add(i);
			}
		}
	    
		if(cpu == -1 && ready.isEmpty()==false)
		{
			cpu = findPriority(ready);
			ready.remove(Integer.valueOf(cpu));
			if(processTable[cpu][1] <= 0)
			{
				processTable[cpu][3]--;
				processTable[cpu][1]--;
			}
			else if(processTable[cpu][1] > 0)
			{
				processTable[cpu][1]--;
			}			
		}
		
		
		
		
		output.write(time + " ");
		
		if(cpu != -1)
		{
			output.write(cpu + ":running ");
			//System.out.print(cpu + ":running ");
			turnaround[cpu]++;
			utilization++;
		}
		
		for(int i = 0; i < block.size(); i++)
		{
			output.write(block.get(i).toString() + ":blocked ");
			//System.out.print(Integer.parseInt(block.get(i).toString()) + ":blocked " );
			turnaround[Integer.parseInt(block.get(i).toString())]++;
		}
		
		for(int i = 0; i < ready.size(); i++)
		{
			output.write(ready.get(i).toString() + ":ready ");
			//System.out.print(Integer.parseInt(ready.get(i).toString()) + ":ready " );
			turnaround[Integer.parseInt(ready.get(i).toString())]++;
		}
		
		
				
		
		
		output.newLine();
		
		for(int i = 0; i < io.numberOfProcess; i++)
    	{
    		processTable[i][4]--;
    	}
		
		for(int i = 0; i < block.size(); i++)
		{
			int temp = Integer.parseInt(block.get(i).toString());
			if(processTable[temp][2] != 0)
				processTable[temp][2]--;
		}
		
		
		
		if(ready.isEmpty() == true && block.isEmpty() == true && processTable[cpu][3] == 0 && arrive() == false)
			cpu = -1;
		
		
	}
	
	public boolean arrive()
	{
		for(int i = 0; i < io.numberOfProcess;i++)
		{
			if(processTable[i][4] >= 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int findPriority(ArrayList list)
	{
		int temp = 0;
		int index = 0;
		for(int i = 0; i < list.size();i++)
		{
			int a = Integer.parseInt(list.get(i).toString());
			if(priority[a] > temp)
			{
				temp = priority[Integer.parseInt(list.get(i).toString())];
				index = a;
			}
		}
		return index;
	}
	
	public void statistics() throws IOException
	{
		DecimalFormat df = new DecimalFormat("#0.00");  

		output.newLine();
		output.write("Finishing time: " + (time-1));	
		output.newLine();
		output.write("CPU utilization: " + df.format((double)utilization/(double)time));
		output.newLine();
		for(int i = 0; i < turnaround.length; i++)
		{
			output.write("Turnaround process " + i + ": " + turnaround[i]);
			output.newLine();
		}
	}
}
