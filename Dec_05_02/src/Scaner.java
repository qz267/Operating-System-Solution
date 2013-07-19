import java.util.*;
import java.io.*;

public class Scaner 
{	
	Scaner()
	{
	}
	
	int[] scaner(String fileName) throws IOException
	{
		ArrayList input = new ArrayList();
		String inLine;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		while((inLine = br.readLine())!= null)
		{
			input.add(inLine);
		}
		
		int[] res = new int[input.size()];
		for(int i = 0; i < input.size(); i++)
		{
		   res[i] = Integer.parseInt(input.get(i).toString());
		}
		br.close();
		return res;
	}
}
