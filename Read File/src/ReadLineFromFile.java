import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class ReadLineFromFile {
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

//	public ReadLineFromFile(String fileName) {
//		super();
//		this.fileName = fileName;
//	}
	
	public static void main(String arg[]){
		
		ReadLineFromFile rlff = new ReadLineFromFile();
		rlff.setFileName("textfile.txt");
		
		 try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(rlff.getFileName());
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null) {
			  // Print the content on the console
              // Modify new line.
              // System.out.newLine();
			  System.out.println (strLine);
			  }
			  //Close the input stream
			  in.close();
			  }catch (Exception e)
			  {//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		}
	
}
