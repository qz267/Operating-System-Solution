import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class FileTransfer {
	public static void main(String arg[]){
		
//		ReadLineFromFile rlff = new ReadLineFromFile();
//		rlff.setFileName("textfile.txt");
		
		 try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream("textfile.txt");
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  // Create file 
			  FileWriter fstream1 = new FileWriter("out.txt");
			  BufferedWriter out = new BufferedWriter(fstream1);
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null) {
			  // Print the content on the console
//			  System.out.println (strLine);
			  out.write(strLine);
			  out.newLine();
			  }
			  //Close the input stream
			  in.close();
			  out.close();
			  }catch (Exception e)
			  {//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		}
}
