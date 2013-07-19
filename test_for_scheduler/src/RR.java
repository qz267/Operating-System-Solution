import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.*;

public class RR 
{
	IO io = new IO();
	int[][] processTable = io.processTable;
	int cpu = -1;
	int count = 2;
	int utilization = 0;
	int[] turnaround = new int[io.numberOfProcess];
	int time = 0;
	BufferedWriter output = new BufferedWriter(new FileWriter("RR.txt"));
	
	RR()throws IOException
	{
	}
	
	ArrayList ready = new ArrayList();
	ArrayList block = new ArrayList();
	
	public void Round_Robin() throws IOException
	{
		while(ready.isEmpty() == false || block.isEmpty() == false || cpu != -1 || arrive() == true )
		{
			clockCycle();
		}
		statistics();
		output.close();
		System.out.println("The result of Round-Robin was written in \"RR.txt\"");
	}
	
	public void clockCycle() throws IOException
	{
		if(cpu != -1)
		{
			if(processTable[cpu][1]==0)
			{
				block.add(cpu);
				processTable[cpu][1]--;
				count = 2;
				cpu = -1;
			}
			else if(count==0)
			{
				block.add(cpu);
				count = 2;
				cpu = -1;
			}
		}
		
		for(int i = 0; i < io.numberOfProcess; i++)
		{
			if(processTable[i][4] == 0)
			{
				ready.add(i);
			}
		}
		
		ArrayList k = new ArrayList();
		for(int i = 0; i < block.size(); i++)
		{
			int temp = Integer.parseInt(block.get(i).toString());
			if(processTable[temp][2] == 0)
			{
				k.add(temp);
				ready.add(temp);
				
			}
		}
		
		for(int i = 0; i < k.size(); i++)
		{
			block.remove(k.get(i));
		}
		
		if(cpu == -1 && count > 0)
		{
			if(ready.isEmpty() == false)
			{
				int min = 1000000000;
				int temp;
				for(int i = 0; i < ready.size(); i++)
				{
					temp = Integer.parseInt(ready.get(i).toString());
					if(temp < min)
					{
						min = temp;
					}
				}
				
				cpu = min;
				ready.remove(Integer.valueOf(cpu));
			}
		}
			
		if(cpu == -1)
		{
			if(ready.isEmpty() == false)
			{
				cpu = Integer.parseInt(ready.get(0).toString());
				ready.remove(Integer.valueOf(cpu));
			}
		}
		
		if(cpu != -1)
		{
			if(processTable[cpu][1]< 0)
			{
				processTable[cpu][3]--;
				count--;
			}
			else
			{
				processTable[cpu][1]--;
				count--;
			}
		}
		
		output.write(time + " ");
		
		if(cpu != -1)
		{
			output.write(cpu + ":running ");
			turnaround[cpu]++;
			utilization++;
		}
		
		for(int i = 0; i < block.size(); i++)
		{
			int temp = Integer.parseInt(block.get(i).toString());
			if(processTable[temp][2] != 0)
			{
				processTable[temp][2]--;
				output.write(temp + ":blocked ");
				turnaround[temp]++;
			}
			else
			{
				block.remove(Integer.valueOf(temp));
				ready.add(temp);
			}
			
		}
		
		for(int i = 0; i < io.numberOfProcess; i++)
		{
			processTable[i][4]--;
		}
		
		for(int i = 0; i < ready.size(); i++)
		{
			int temp = Integer.parseInt(ready.get(i).toString());
			output.write(temp + ":ready ");
			turnaround[temp]++;
		}
		
		output.newLine();
		
		time++;
		
		if(cpu != -1 && processTable[cpu][3] == 0)
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
	
	public void statistics() throws IOException
	{
		output.newLine();
		output.write("Finishing time: " + (time-1));
		output.newLine();
		DecimalFormat df = new DecimalFormat("#0.00");  
		
		output.write("CPU utilization: " + df.format((double)utilization/(double)time));
		output.newLine();
		for(int i = 0; i < turnaround.length; i++)
		{
			output.write("Turnaround process " + i + ": " + turnaround[i]);
			output.newLine();
		}
	}
}
