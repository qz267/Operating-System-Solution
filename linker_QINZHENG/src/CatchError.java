import java.util.ArrayList;

/**
 * Check Errors
 * @author Zheng Qin
 *
 */
public class CatchError 
{
	/**
	 * Multiply defined
	 * @param list
	 * @param a
	 */
	public static void MulDef(ArrayList list, Object a)
	{
		
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(a)) 
			{
				System.out.println("Error: " + a + " is multifly defined");
				System.out.println("Exit");
				System.exit(0);
			}
		}
	}
	/**
	 * A symbol is used but not defined
	 * @param def
	 * @param use
	 */
	public static void useWithoutDef(ArrayList def, ArrayList use)
	{
		for(int i = 0; i < use.size();i++)
		{
			if(find(use.get(i), def) == false)
			{
				System.out.println("Error: " + use.get(i) + " is used but not defined");
				System.out.println("Exit");
				System.exit(0);
			}
		}
	}
	
	/**
	 * A symbol is defined but not used,
	 * @param o
	 * @param use
	 * @param numOfModule
	 */
	public static void defWithoutUse(Object o, ArrayList use, int numOfModule)
	{
			if(find(o, use) == false)
			{
				String temp = o.toString() + "_" + numOfModule;
			//	SecondPass.defWithoutUseList.add(temp);
			}
	}
	
	/**
	 * Find a object in a list
	 * @param o
	 * @param list
	 * @return
	 */
	static boolean find(Object o, ArrayList list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(o) == true)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Definition exceeds the size of the module
	 * @param var
	 * @param varAdd
	 * @param size
	 * @param numOfModule
	 */
	public static void defExceedSize(Object var, Object varAdd, int size, int numOfModule) 
	{
		if(Integer.parseInt(varAdd.toString()) > size)
		{
			System.out.println("Error: " + var + "'s address: " + varAdd + 
					" appearing in the definition exceeds the size of the module" + numOfModule + ": " +  size);
			System.out.println("Exit");
			System.exit(0);
		}
	}
	
	/**
	 * A symbol appears in a use list but it not actually used in the module
	 * @param list
	 * @param actList
	 * @param numOfModule
	 */
	public static void inUselistNotInModule(ArrayList list, ArrayList actList, int numOfModule)
	{
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 0; j < actList.size(); j++)
			{
				if(actList.get(j).equals(list.get(i)))
					list.set(i , 0);
			}
		}
		
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(0) == false)
			{
				SecondPass.notInModule.add(list.get(i));
			}
		}
	}
	
	/**
	 * An absolute address exceeds the size of the machine
	 * @param address
	 * @param numOfModule
	 */
	public static void absoluteAddress(Object address , int numOfModule)
	{
		int a = Integer.parseInt(address.toString()) % 1000;
		if(a > 600)
		{
			System.out.println("Error: " + "The absolute address " + address + " in module "
					+ (numOfModule + 1) + " exceeds the size of the machine");
			System.out.println("Exit");
			System.exit(0);
		}
	}
	
	/**
	 * A relative address exceeds the size of the module
	 * @param address
	 * @param numOfModule
	 */
	public static void relativeAddress(Object address , int numOfModule)
	{
		int a = Integer.parseInt(address.toString()) % 1000;
		if( a > FirstPass.moduleSize[numOfModule])
		{
			System.out.println("Error: " + "The relative address " + address + " in module"
					+ (numOfModule + 1) + " exceeds the size of the module: "
					+ FirstPass.moduleSize[numOfModule]);
			System.out.println("Exit");
			System.exit(0);
		}
	}
	
	/**
	 * An external address is too large to reference an entry in the use list
	 * @param address
	 * @param numOfUse
	 * @param numOfModule
	 */
	public static void externalAddress(Object address , Object numOfUse , int numOfModule)
	{
		int a = Integer.parseInt(address.toString()) % 1000;
		if(a > (Integer.parseInt(numOfUse.toString()) - 1))
		{
			System.out.println("Error: " + "The external address " + address + " in module"
					+ (numOfModule + 1) + " is too large to reference an entry in the use list ");
			System.out.println("Exit");
			System.exit(0);
		
		
		
		}
	
	
	
	}
	
	
	
}






