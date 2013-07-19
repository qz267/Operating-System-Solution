import java.util.*;

public class test {
	public static void main(String[] args){
		Scanner input=new Scanner(System.in);
	    System.out.println("Please insert N:");
	    int  number =input.nextInt();
		divide(number);
	   }
	
	static   void   divide(int   num) 
	{
		if(num==1) 
		{ 
			System.out.print( "1 "); 
			}
		else 
		{ 
			for(int   i=2;i <=num;i++) 
			{ 
				if(num%i==0)
				{ 
					System.out.print(i+ "* "); 
					num   =   num/i; 
					break; 
				} 
			} 
			divide(num); 
		} 
	}
}