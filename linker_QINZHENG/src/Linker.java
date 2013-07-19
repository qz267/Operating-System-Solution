import java.io.IOException;

/**
 * Linker
 * @author QIN ZHENG
 *
 */
public class Linker 
{
	public static void main(String[] args) throws IOException
	{
		FirstPass fp = new FirstPass();
		SecondPass sp = new SecondPass();
		
		fp.firstPass();
		sp.secondPass();
	}
}