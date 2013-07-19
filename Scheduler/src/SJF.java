import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class SJF 
{
	IO io = new IO();
	int[][] processTable = io.processTable;
	ArrayList ready = new ArrayList();
	ArrayList block = new ArrayList();
	int[] remain = new int[io.numberOfProcess];
	int cpu = -1;
	int time = 0;
	int[] turnaround = new int[io.numberOfProcess];
	int utilization = 0;
	BufferedWriter output = new BufferedWriter(new FileWriter("SJF.txt"));
	
	SJF() throws IOException
	{
	}
	
	public void shortest() throws IOException
	{
		while(ready.isEmpty() == false || block.isEmpty() == false || cpu != -1 || arrive() == true )
		{
			clockCycle();
		}
		
		statistics();
		output.close();
		System.out.println("The result of Shortest-Remaining-Job-First was written in \"SJF.txt\"");
	}
	
	public void clockCycle() throws IOException
	{
		for(int i = 0; i < io.numberOfProcess; i++)
		{
			if(processTable[i][4] == 0)
			{
				ready.add(i);
				remain[i] = processTable[i][1] * 2;
			}
		}
		
		for(int i = 0; i < block.size(); i++)
		{
			int temp = Integer.parseInt(block.get(i).toString());
			if(processTable[temp][2] == 0)
			{
				block.remove(Integer.valueOf(temp));
				ready.add(temp);
			}
		}		
		
		if(cpu != -1 && processTable[cpu][1] == 0)
		{
			processTable[cpu][1]--;
			if(processTable[cpu][2] > 0)
			{
				block.add(cpu);
				cpu = -1;
			}
			else if(processTable[cpu][2] <= 0)
			{
				ready.add(cpu);
				cpu = -1;
			}
		}
		
		int min;
		int index = -2;
		boolean changed = false;
		if(cpu != -1)
		{
			min = remain[cpu];
			index = cpu;
		}
		else
			min = 10000000;
		
		for(int i = 0; i < ready.size(); i++)
		{
			int temp = Integer.parseInt(ready.get(i).toString());
			
			if(remain[temp] < min)
			{
				index = temp;
				min = remain[index];
				changed = true;
			}
			if(remain[temp] == min)
			{
				if(temp < index)
				{
					index = temp;
					changed = true;
				}
			}
		}

		if(changed == true)
		{
			if(cpu != -1)
			{
				if(processTable[cpu][2] == 0)
					ready.add(cpu);
				else
					block.add(cpu);
			}
			
			cpu = index;
		}
		
        if(cpu != -1)
        {
        	ready.remove(Integer.valueOf(cpu));
			if(processTable[cpu][1] < 0)
			{
				processTable[cpu][3]--;
				remain[cpu]--;
			}
			else if(processTable[cpu][1] > 0)
			{
				processTable[cpu][1]--;
				remain[cpu]--;
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
			processTable[temp][2]--;
			output.write(temp + ":blocked ");
			turnaround[temp]++;
			
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
