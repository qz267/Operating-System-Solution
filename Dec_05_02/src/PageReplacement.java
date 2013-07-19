import java.io.*;
import java.util.*;


public class PageReplacement
{
	static String command;
	static int method;
	static int memory;
	static String fileName;
	static String cmd;
	static Scanner in = new Scanner(System.in);
	
	void enter()
	{
		System.out.print("Please enter the command (e.g. replace_page 0 3 access.txt):");
		command = in.nextLine();	
		StringTokenizer tokenizer1 = new StringTokenizer(command);
		int k = 0;
		
		while(tokenizer1.hasMoreTokens())
		{

			switch (k)
			{
			   case 0: cmd = tokenizer1.nextToken(); 
			           break;
			   case 1: method = Integer.parseInt(tokenizer1.nextToken());
			           break;
			   case 2: memory = Integer.parseInt(tokenizer1.nextToken());
			           break;
			   case 3: fileName = tokenizer1.nextToken();
			           break;
			}
			k++;
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
		PageReplacement r = new PageReplacement();
		String answer = "Y";
		while(answer.equals("Y") || answer.equals("y"))
		{
			r.enter();
			switch(method)
			{
			case 0: FIFO ff = new FIFO(fileName, memory);
			        ff.access();
			        break;
			     
			case 1: SC sc= new SC(fileName, memory);
			        sc.access();
			        break;
			        
			case 2: LRU l = new LRU(fileName, memory);
			        l.access();
			        break;
			}
			System.out.print("Do you want to enter another command?(Y/N) ");
			answer = in.nextLine();
		}	
		in.close();
		System.out.println("Thanks for using!");
	}
}
