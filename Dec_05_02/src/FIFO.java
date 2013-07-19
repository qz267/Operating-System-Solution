import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class FIFO 
{
	Scaner sc = new Scaner();
	int[] input;
	ArrayList paging = new ArrayList();
	ArrayList pri = new ArrayList();
	int size = 0;
	BufferedWriter output;
	int pageFault = 0;
	String outputFileName;
	
	FIFO(String fileName, int memory) throws IOException
	{
	   input = sc.scaner(fileName);
	   size = memory;
	   StringTokenizer st = new StringTokenizer(fileName, ".");
       outputFileName = st.nextToken() + ".fifo.txt";
	   output = new BufferedWriter(new FileWriter(outputFileName));
	}
	
	void access() throws IOException
	{
	   for(int i = 0; i < input.length; i++)
	   {
		   if(paging.indexOf(Integer.valueOf(input[i])) == -1)
		   {
			   if(paging.size() < size)
			   {
				  paging.add(input[i]);
				  pri.add(input[i]);
			   }
			   else
			   {
				   paging.set(paging.indexOf(pri.get(0)), input[i]);
				   pri.remove(0);
				   pri.add(input[i]);
			   }
			   pageFault++;
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
