import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class IO{
	int NumberOfPage;
	int visit[];
	String fileName ="in.txt";
	IO () throws IOException{
		ArrayList inputList = new ArrayList();
		
		String inLine;
		
        BufferedReader input = new BufferedReader(new FileReader(fileName));
        
        StringTokenizer tokenizer;
        
        while((inLine = input.readLine()) != null)
        {
    			inputList.add(inLine);
        }
        
        NumberOfPage = inputList.size();
        
        visit = new int [NumberOfPage];
        
        for(int i = 0; i < NumberOfPage; i++)
        {
        	String temp = inputList.get(i).toString();
        	tokenizer = new StringTokenizer(temp," ");
        	visit[i] = Integer.parseInt(tokenizer.nextToken().toString());
        }
	}
}



























/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IO {

public static String[] io(String[] args) throws IOException{
	File f = new File("in.txt");
	InputStream input = new FileInputStream(f);
	BufferedReader b = new BufferedReader(new InputStreamReader(input));
	StringBuffer buffer = new StringBuffer();
	String value = b.readLine();
	while(value != null){
		buffer.append(" "+value);
		value = b.readLine();
		}
	String[] temp = buffer.toString().replaceFirst(" ","").split("\\s+");
	return temp;
//	int[] number = new int[temp.length];
	for(int i=0;i<temp.length;i++){
		try{
			number[i] = Integer.parseInt(temp[i]);
			System.out.print(number[i]+" ");
			}
		catch(Exception e){
			System.out.println("input error!");
			}
		}
	}
}*/