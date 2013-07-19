import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * First Pass
 * @author QIN ZHENG
 *
 */
public class FirstPass 
{
	static ArrayList list = new ArrayList();  //A List to store all information
	int length;    //Length of the List
	
	static ArrayList varList = new ArrayList();   //All variables 
	static ArrayList varAdd = new ArrayList();    //variables' address in symbol table
	static ArrayList totalUseList = new ArrayList();  //All variables appear in use list
	
	static int numOfModule = 0;    //Record the total number of modules
	static int[] numOfDeflist = new int[100];    //Record the variables' number in define list in every module
	static int[] numOfUselist = new int[100];    //Record the variables' number in use list in every module
	static int[] moduleSize = new int[100];      //Record the size of every module
	
	static int[] addOfModule = new int[100];     //Module's base address after first pass
	
	static int useP[] = new int[100];   //The position of the digit that shows the number of variables used in this module
	static int modP[] = new int[100];   //The position of the digit that shows the size of module
	static int defP[] = new int[100];	//The position of the digit that shows the number of variables defined in this module
	
	String fileName = "";
	
	public void firstPass() throws IOException
	{
		fileName = JOptionPane.showInputDialog(null, "Please enter the file's name: " , 
				"Linker" , JOptionPane.QUESTION_MESSAGE );
		readFile(fileName);
		symbolTable();
	}
	
	/**
	 * Read input from a file 
	 * Create a list to save it and reformat it. 
	 * @throws IOException
	 */
	public void readFile(String fileName) throws IOException
	{
		String inLine;
		
		String content = "";  //Content without space, table and enter
		String abc = "";
		
		//Read file by line
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		
		while((inLine = input.readLine()) != null)
		{
			abc = abc + inLine + " ";
		}
		
		//Remove table "	"
		StringTokenizer tokenizer1 = new StringTokenizer(abc , "	");
		
		while(tokenizer1.hasMoreTokens())
		{
			content = content + tokenizer1.nextToken();
		}
		
		//Remove space
		StringTokenizer tokenizer = new StringTokenizer(content , " ");
		
		//Add the content to list
		for(int i = 0; tokenizer.hasMoreTokens(); i++)
		{
			list.add(i , tokenizer.nextToken());
		}	
	}
	
	/**
	 * Create symbol table
	 * @throws IOException
	 */
	public void symbolTable() throws IOException
    {
		//Initial 
		length = list.size();
		defP[0] = 0;
		useP[0] = 0;
		modP[0] = 0;
		
		while(defP[numOfModule] < length)
		{
			numOfDeflist[numOfModule] = Integer.parseInt(list.get(defP[numOfModule]).toString());
			useP[numOfModule] = defP[numOfModule] + numOfDeflist[numOfModule] * 2 + 1;
			numOfUselist[numOfModule] = Integer.parseInt(list.get(useP[numOfModule]).toString());
			modP[numOfModule] = useP[numOfModule] + 1 +numOfUselist[numOfModule];
			moduleSize[numOfModule] = Integer.parseInt(list.get(modP[numOfModule]).toString());
			defP[numOfModule + 1] = moduleSize[numOfModule] * 2 + 1 +modP[numOfModule];
			
			numOfModule++;
			
		}  
		
		createUseList();
		
		int count = 0;
		
		addOfModule = createBaseAddress(numOfModule , moduleSize);
		
		//Create the symbol table
		for(int i = 0; i < numOfModule; i++)
		{
			if(numOfDeflist[i] != 0)
			{
				for(int j = 1; j <= numOfDeflist[i]; j++)
				{
					int a;
					a = defP[i] + (j - 1) * 2 + 1;
					
					//Check Error: Multiply defined
					CatchError.MulDef(varList , list.get(a));
					varList.add(count,list.get(a));
					//Check Error: Define exceeds size of module
					CatchError.defExceedSize(list.get(a), list.get(a+1), moduleSize[i], i+1);
					varAdd.add(count , Integer.parseInt(list.get(a+1).toString()) + addOfModule[i]);
					//Check Error: Define without use
					CatchError.defWithoutUse(list.get(a), totalUseList, i+1);
					count++;
				}
			}
		}
		//Check Error 
		CatchError.useWithoutDef(varList, totalUseList);
    } 
	
	/**
	 * Create the base address table
	 * @param number
	 * @param size
	 * @return
	 */
	public int[] createBaseAddress(int number, int[] size)
	{
		ArrayList addOfModuleList = new ArrayList();
		
		addOfModuleList.add(0 , 0);

		for(int i = 1; i < number; i++)
		{
			int baseAdd = 0;
			for(int j = i; j > 0; j--)
			{
				baseAdd = baseAdd + size[j - 1];
			}
			addOfModuleList.add(i , baseAdd);
		}
		
		int[] addOfModule = new int[addOfModuleList.size()];
		
		for(int i = 0; i < addOfModuleList.size(); i++)
		{
			addOfModule[i] = Integer.parseInt(addOfModuleList.get(i).toString());
		}
		return addOfModule;
	}
	
	/**
	 * Create a total use list with all symbols appear in every use list
	 */
	public void createUseList()
	{
		for(int i = 0; i < numOfModule; i++)
		{
			for(int j = 1; j <= numOfUselist[i]; j++)
			{
				totalUseList.add(list.get(useP[i] + 1 + (j - 1)));
			}
		}
		totalUseList = trimList(totalUseList);
	}
	
	/**
	 * Remove the same element in the list
	 * @param list
	 * @return
	 */
	static public ArrayList trimList(ArrayList list)
	{
		ArrayList list2 = new ArrayList();
		for (int i = list.size() - 1; i >= 0; i--) 
		{
			Object o = list.get(i);

			if (list2.indexOf(o) == -1) 
			{
				list2.add(o);
			}
		}
		return list2;
	}
}