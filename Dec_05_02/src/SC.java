import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

public class SC 
{ 
	Scaner ri = new Scaner();
	int[] input;
	ArrayList sequence = new ArrayList();
	ArrayList paging = new ArrayList();
    ArrayList reference = new ArrayList();
    int pageFault = 0;
    BufferedWriter output;
    String outputFileName;
    
	int pageSize = 3;
	
	SC(String fileName, int memory) throws IOException
	{
		input = ri.scaner(fileName);
		pageSize = memory;
		StringTokenizer st = new StringTokenizer(fileName, ".");
		outputFileName = st.nextToken() + ".sc.txt";
		output = new BufferedWriter(new FileWriter(outputFileName));
	}
	
	void access() throws IOException
	{
		for(int i = 0; i < input.length;i++)
		{
			if(paging.indexOf(Integer.valueOf(input[i])) == -1)
			{
				if(paging.size() < pageSize)
				{
					paging.add(input[i]);
					sequence.add(input[i]);
					reference.add(0);
				}
				else
				{
					while(Integer.parseInt(reference.get(0).toString()) != 0)
					{					
						sequence.add(sequence.get(0));
						sequence.remove(0);
						reference.add(0);
						reference.remove(0);
					}
					paging.set(paging.indexOf(sequence.get(0)), input[i]);
					sequence.remove(0);
					sequence.add(input[i]);
					reference.remove(0);
					reference.add(0);
				}
				pageFault++;
			}
			else
			{
				int index = sequence.indexOf(Integer.valueOf(input[i]));
				reference.set(index, 1);
			}
			for(int j = 0; j < paging.size(); j++)
		    {
			    output.write(Integer.parseInt(paging.get(j).toString()) + " ");		   
		    }	   
		    output.newLine();   
		}
		DecimalFormat df = new DecimalFormat("#0.00");  
	    output.newLine();
	    output.write("Percentage of page faults: " + df.format((double)pageFault / (double)input.length));
	    output.close();
	    System.out.println("The result was written in " + outputFileName + " file.");
	}

}
