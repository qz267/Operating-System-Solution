import java.util.LinkedList;  
import java.util.List;  
import java.util.Scanner;  



public class FIFO {
//	IO io = new IO();
//	int[] visit=io.visit;
	
	
	
	
     static int volum;//volum of disk  
     static List<content>list=new LinkedList<content>();  
     static int visit[];//page to visit
     static int count=0;//count the page switch times 
      public static void main(String[]args)  
      {  
          Scanner sc=new Scanner(System.in);  
          System.out.print("Please set the volum of disk:");  
          volum=sc.nextInt();  
          System.out.println("The number of pages is:");  
          int number=sc.nextInt();  
          visit=new int[number];  
          System.out.println("Please insert the coming pages' nymber:");  
          for(int i=0;i<number;i++)  
              visit[i]=sc.nextInt();  
          sFIFO();  
          System.out.println("page switch times:"+count);  
            
      }  
      public static void sFIFO()  
      {  
          int index=0;  
          while(index<visit.length)  
          {  
              boolean flag=false;  
              if(list.size()<=volum)  
              {  
                  for(int i=0;i<list.size();i++)  
                  {  
                      if(list.get(i).id==visit[index])  
                      {//if exist, return and check next  
                          for(int j=0;j<list.size();j++)  
                          {  
                              int te=list.get(j).count;  
                              list.get(j).setCount(te+1);  
                          }  
                          flag=true;  
                          break;  
                      }  
                  }  
                      if(!flag)  
                      {  
                          if(list.size()==volum)  
                          {//if full, switch page  
                           int old=list.get(0).count;  
                           int temp=0;//template save the oldest page 
                          //find the oldest page
                             for(int j=1;j<volum;j++)  
                             {  
                               if(old<(list.get(j).count))  
                               {  
                                 temp=j;  
                                 old=list.get(j).count;  
                               }  
                             }  
                           
                           System.out.println("page"+list.get(temp).id+"was switched");  
                             
                           for(int stt=0;stt<list.size();stt++)  
                               (list.get(stt).count)++;  
                           list.get(temp).count=1;  
                           list.get(temp).id=visit[index];  
                           count++;  
                          }  
                          else  
                          {  
                              for(int t=0;t<list.size();t++)  
                              {  
                                  int te=list.get(t).count;  
                                   list.get(t).setCount(te+1);   
                              }  
                              content ncontent=new content(visit[index],1);  
                              list.add(ncontent);  
                          }  
                            
                      }  
                  }  
              System.out.print("No."+(index+1)+" thimes memory map：");  
              for(int f=0;f<list.size();f++)  
                  System.out.print(list.get(f).id+" ");  
              System.out.println();  
              index++;  
              }  
            
                
          }  
            
      }  
  
class content  
{  
    int id;//page id
    int count;//old rank
    public void setCount(int count)  
    {  
        this.count=count;  
    }  
    public content(int id,int count)  
    {  
        this.id=id;  
        this.count=count;  
    }  
      
}  