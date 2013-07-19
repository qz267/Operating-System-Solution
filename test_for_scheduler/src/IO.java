import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IO 
{
	int numberOfProcess;
	int[][] processTable;
	String fileName = "INPUT.txt";
	IO() throws IOException
	{
		ArrayList inputList = new ArrayList();
		
		String inLine;
		
        BufferedReader input = new BufferedReader(new FileReader(fileName));
        
        StringTokenizer tokenizer;
        
        while((inLine = input.readLine()) != null)
        {
    			inputList.add(inLine);
        }
        
        numberOfProcess = inputList.size();
        processTable = new int[numberOfProcess][5];
        
        for(int i = 0; i < numberOfProcess; i++)
        {
        	String temp = inputList.get(i).toString();
        	tokenizer = new StringTokenizer(temp," ");
    		for(int k = 0; tokenizer.hasMoreTokens();k++)
    		{
    			if(k == 1)
    			{
    				processTable[i][k] = (Integer.parseInt(tokenizer.nextToken().toString()))/2;
    				processTable[i][3] = processTable[i][k];
    			}
    			else if(k != 3)
    			{
    				processTable[i][k] = Integer.parseInt(tokenizer.nextToken().toString());
    			}   				
    		}
        }
	}
}
