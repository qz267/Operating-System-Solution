import java.io.IOException;
import java.util.*;

/**
 * Second Pass
 * @author QIN ZHENG
 *
 */
public class SecondPass 
{	
	//Save the three kinds of warnings 
	static ArrayList notInModule = new ArrayList(); 
	static ArrayList useWithoutDefList = new ArrayList();
	static ArrayList<?> defWithoutUseList = new ArrayList();

	/**
	 * Second Pass
	 */
	public void secondPass()
	{
		for(int i = 0; i < FirstPass.numOfModule; i++)
		{
			int tempAdd = 0;
			int count = 0;
			
			ArrayList useList = new ArrayList(); //The use list of the module
			ArrayList actuallyUsedList = new ArrayList(); //The list of the symbols used in module
			actuallyUsedList.add(0);
			
			//Create the use list
			for(int j = 1; j <= FirstPass.numOfUselist[i];j++)
			{
				useList.add(count, FirstPass.list.get(FirstPass.useP[i] + 1 + (j - 1))); 
				count++;			
			//Analyze the code of the module
			for(int j1 = 1; j1 <= FirstPass.moduleSize[i]; j1++)
			{	
				tempAdd = FirstPass.modP[i] + 1 + (j1 - 1) * 2;
				
				//If the code is R
				if(FirstPass.list.get(tempAdd).toString().equals("R"))
				{
					CatchError.relativeAddress(FirstPass.list.get(tempAdd + 1), i);
					relocate(FirstPass.list , tempAdd + 1, FirstPass.addOfModule[i]);
				}
				
				//If the code is E
				if(FirstPass.list.get(tempAdd).toString().equals("E"))
				{
					resolve(FirstPass.list, actuallyUsedList, tempAdd + 1, 
							FirstPass.useP[i], FirstPass.varAdd, FirstPass.varList, i);
				}
				
				//If the code is A
				if(FirstPass.list.get(tempAdd).toString().equals("A"))
				{
					//Check error: Absolute address is wrong
					CatchError.absoluteAddress(FirstPass.list.get(tempAdd + 1) , i);
				}
			}
			
			//Check error: A symbol appears in a use list but it not actually used in the module
			CatchError.inUselistNotInModule(useList, actuallyUsedList, i);
		}
		
		printOutput();
		}
	}
	
	/**
	 * Relocation
	 * @param list
	 * @param indexOfList
	 * @param baseAdd
	 */
	public void relocate(ArrayList list, int indexOfList,int baseAdd)
	{
		int a = Integer.parseInt(list.get(indexOfList).toString()) + baseAdd;
		list.set(indexOfList, a);
	}
	
	/**
	 * Resolution
	 * @param list
	 * @param aUL
	 * @param indexOfList
	 * @param useP
	 * @param varAdd
	 * @param varList
	 * @param numOfModule
	 */
	public void resolve(ArrayList list, ArrayList aUL, int indexOfList, 
			int useP, ArrayList varAdd, ArrayList varList, int numOfModule)
	{
		int a = Integer.parseInt(list.get(indexOfList).toString());
		int varName = a % 1000;
		int count = -1;	
		boolean e = false;
		
		////Check error: Absolute address is wrong
		CatchError.externalAddress(list.get(indexOfList), list.get(useP), numOfModule);
		
		Object var = list.get(useP + varName + 1);
		
		//Create the actually used list
	    for(int i = 0; i < aUL.size(); i++)
	    {
	    	if(aUL.get(i).equals(var))
	    	{
	    		e = true;
	    	}
	    	else if(i  == (aUL.size() -1) && e == false)
	    	{
	    		aUL.add(i + 1 , var);
	    	}
	    }
		
	    //Find if the variable is used
		for(int i = 0; i < varList.size(); i++)
		{
			if(varList.get(i).equals(var))
			{
				count = i;
				break;
			}
		}	
		
		//Resolve
		list.set(indexOfList , a / 1000 * 1000 + Integer.parseInt(varAdd.get(count).toString()));
	}
	
	/**
	 * Print the output, warnings and errors
	 */
	public void printOutput()
	{
		int tempAdd = -1;
		int count = 0;
		
		System.out.println("Symbol Table");
		for(int i = 0; i < FirstPass.varList.size(); i++)
		{
			System.out.println(FirstPass.varList.get(i) + " = " + FirstPass.varAdd.get(i));
		}
		
		System.out.println("\nMemory Map");
		for(int i = 0; i < FirstPass.numOfModule; i++)
		{
			for(int j = 1; j <= FirstPass.moduleSize[i]; j++)
			{
				tempAdd = FirstPass.modP[i] + 2 + (j - 1) * 2;
				if(count < 10)
					System.out.println(count + ":  " + FirstPass.list.get(tempAdd));
				if(count >= 10 && count < 100)
					System.out.println(count + ": " + FirstPass.list.get(tempAdd));
				count++;
			}
		}
		
		if(notInModule.size() != 0)
		{
			for(int i = 0; i < notInModule.size(); i++)
			{
				System.out.println("Warning: " + notInModule.get(i) + 
						" appears in a use list but it not actually used in the module");
			}
		}
		
		if(defWithoutUseList.size() != 0)
		{
			System.out.println();
			for(int i = 0; i < defWithoutUseList.size(); i++)
			{
				String temp = defWithoutUseList.get(i).toString();
				StringTokenizer tokenizer = new StringTokenizer(temp , "_");
				while(tokenizer.hasMoreTokens())
				{
					System.out.print("Warning: " + tokenizer.nextToken() +  " was defined in module ");
					System.out.println(tokenizer.nextToken() + " but never used.");
				}
			}
		}
	}
}

