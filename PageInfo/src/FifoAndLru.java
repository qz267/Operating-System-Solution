import java.io.*;
import java.util.*;

public class FifoAndLru {
static int Psize;
static int Bsize;

PageInfo[] block=new PageInfo[Bsize];
PageInfo[] page=new PageInfo[Psize];

public void Init(){            //初始化
   int QString[]={7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
   for(int i=0;i<block.length;i++){
    block[i].content=-1;
    block[i].timer=0;
   }
   for(int i=0;i<page.length;i++){
    page[i].content=QString[i];
    page[i].timer=0;
   }
  
}

public int findSpace(){              // 查找是否有空闲内存
   for(int i=0;i<block.length;i++) {
    if (block[i].content ==-1) return i;
   }
   return -1;
}

public int findExist(int curpage){     //查找内存中是否存在该页面
   for(int i=0;i<block.length;i++){
    if(block[i].content==page[curpage].content)
     return i;
   }
   return -1;
}

public int findReplace(){            //查找需要替换的页面
   int pos=0;
   for(int i=0;i<block.length;i++)
    if (block[i].timer>=block[pos].timer)
     pos=i;
   return pos;
}

public void display(){            //打印内存块中的页面内容
   for(int i=0;i<block.length;i++){
     if(block[i].content != -1)
     System.out.print(block[i].content+ " ");
    System.out.println();
   }
}

public void FIFO(){ //First In First Out
   int exist,space,position;
   for (int i=0;i<page.length;i++){
    exist=findExist(i);
    if (exist!=-1)
     System.out.println(" 不缺页");
    else {
     space=findSpace();
     if(space!=-1){
      block[space].content=page[i].content;
      display();
     }
     else {
      position=findReplace();
      block[position].content=page[i].content;
      display();
     }
    }
    for(int j=0;j<block.length;j++){
     block[j].timer++;
    }
   }
}

public void LRU(){ //Least Recently Used
   int exist,space,position;
   for(int i=0;i<page.length;i++){
    exist = findExist(i);
     if(exist != -1){
      System.out.println(" 不缺页");
      block[exist].timer = -1;        // 恢复存在的并刚访问过的BLOCK中页面TIMER为-1
     }
     else{ 
      space = findSpace();
      if(space != -1){
       block[space] = page[i]; 
       display();
      }
      else{
       position = findReplace();
       block[position] = page[i];   
       display();
      }
     }
     for(int j=0; j<Bsize; j++)
        block[j].timer++;

    
   }
}


public void BlockClear(){
   for(int i=0;i<block.length;i++){
    block[i].content=-1;
    block[i].timer=0;
   }
}

public static void main(String[] args) throws IOException{     
  
   FifoAndLru fifoAndLru = new FifoAndLru();
   fifoAndLru.Init();
   fifoAndLru.FIFO();
   fifoAndLru.BlockClear();
   System.out.println("-------------------页面置换算法---------------------");
   System.out.println("页面号引用串:7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1");
   System.out.println("--------------------------------------------------");
   System.out.println("-----------------选择<1>应用FIFO算法----------------");
   System.out.println("-----------------选择<2>应用LRU算法-----------------");
   System.out.println("---------------------选择<0>退出-------------------");
   try {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    } catch (Exception e) {
     e.printStackTrace();
     System.exit(0);
    }
   int select;
   select = readInt();
   while(select!=0){
    switch(select){
     case 0 : break;
     case 1 : System.out.println("------------------FIFO算法结果如下:--------------");
      fifoAndLru.FIFO();
      fifoAndLru.BlockClear();
       System.out.println("------------------------------------------");
       break;
     case 2 : System.out.println("------------------LRU算法结果如下:--------------");
      fifoAndLru.FIFO();
      fifoAndLru.BlockClear();
      System.out.println("------------------------------------------");
      break;
     default : System.out.println("----------------请重新输入：------------------");
    }
   }
}

}
