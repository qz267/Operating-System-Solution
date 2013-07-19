import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class LRU 
{
	Scaner sc = new Scaner();
	int[] input;
	ArrayList paging = new ArrayList();
	ArrayList sequence = new ArrayList();
	int pageSize;
	int pageFault;
	BufferedWriter output;
	String outputFileName;
	
	LRU(String fileName, int memory) throws IOException
	{
		input = sc.scaner(fileName);
		pageSize = memory;
		StringTokenizer st = new StringTokenizer(fileName, ".");
		outputFileName = st.nextToken() + ".lru.txt";
		output = new BufferedWriter(new FileWriter(outputFileName));
	}

	void access() throws IOException
	{
		for(int i = 0; i < input.length; i++)
		{
			 if(paging.indexOf(Integer.valueOf(input[i])) == -1)
			 {
				 if(paging.size() < pageSize)
				 {
					 paging.add(input[i]);
					 sequence.add(input[i]);
				 }
				 else
				 {
					 int index = paging.indexOf(sequence.get(0));
					 paging.set(index, input[i]);
					 sequence.remove(0);
					 sequence.add(input[i]);
				 }
				 pageFault++;
			 }
			 else
			 {
				 int index = sequence.indexOf(Integer.valueOf(input[i]));
				 sequence.remove(index);
				 sequence.add(input[i]);
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
