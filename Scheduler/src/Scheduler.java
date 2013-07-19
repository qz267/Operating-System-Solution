import java.io.IOException;


public class Scheduler 
{
	public static void main(String[] args) throws IOException
	{
		
		FCFS f = new FCFS();
		f.fcfs();
		
		RR rr = new RR();
		rr.Round_Robin();
		
		SJF s = new SJF();
		s.shortest();
		
		
	}
	
}
