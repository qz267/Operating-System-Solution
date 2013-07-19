//高优先权优先调度算法 
public class Scheduling { 
   public static void main(String[] args) { 
    MyQueue myqueue = new MyQueue();//声明队列 
         PCB[] pcb = {new PCB(001,8,1),new PCB(002,7,9),new PCB(003,3,8),new PCB(004,1,7),new PCB(005,7,4)}; 
         PCB para = new PCB(); 
         for(int i=0;i<pcb.length;i++){//初始化后首先执行一次排序，这里使用的是选择排序，优先级高的先入队 
         for(int j=i;j<pcb.length;j++){ 
            if(pcb[i].privilege < pcb[j].privilege){ 
             para = pcb[i]; 
             pcb[i] = pcb[j]; 
             pcb[j] = para; 
            } 
         } 
         } 
         System.out.println("初次入队后各进程的顺序："); 
         for(int i=0;i<pcb.length;i++){ 
         System.out.println("初次入队后 # processname : " + pcb[i].name + " totaltime : " + pcb[i].totaltime + " privilege :" + pcb[i].privilege); 
         } 
         System.out.println(); 
            myqueue.start(pcb); 
   } 
} 

class MyQueue { 
     int index = 0; 
     PCB[] pc = new PCB[5]; 
     PCB[] pc1 = new PCB[4]; 
     PCB temp = new PCB(); 
     
   public void enQueue(PCB process){//入队算法 
         if(index==5){ 
         System.out.println("out of bounds !"); 
         return; 
         } 
    pc[index] = process;   
         index++; 
     } 
  
     public PCB deQueue(){//出队算法 
     if(index==0) 
        return null; 
     for(int i=0;i<pc1.length;i++){ 
        pc1[i] = pc[i+1]; 
     } 
     index--; 
     temp = pc[0]; 
     for(int i=0;i<pc1.length;i++){ 
        pc[i] = pc1[i]; 
     } 
     return temp;    
     } 
    
     public void start(PCB[] pc){//显示进程表算法 
     while(pc[0].isNotFinish==true||pc[1].isNotFinish==true||pc[2].isNotFinish==true||pc[3].isNotFinish==true||pc[4].isNotFinish==true){ 
        //*注意：||运算符，所有表达式都为false结果才为false,否则为true 
        for(int i=0;i<pc.length;i++){ 
                   pc[i].run(this);          
         } 
         System.out.println(); 
         for(int i=0;i<pc.length;i++){//所有进程每执行完一次时间片长度的运行就重新按优先级排列一次 
         for(int j=i;j<pc.length;j++){ 
            if(pc[i].privilege < pc[j].privilege){ 
             temp = pc[i]; 
             pc[i] = pc[j]; 
             pc[j] = temp; 
            } 
         } 
         } 
     }        
     }   
} 

class PCB {//声明进程类 
   int name,totaltime,runtime,privilege; 
   boolean isNotFinish; 
     
   public PCB(){ 
   
   } 
  
   public PCB(int name, int totaltime, int privilege){ 
    this.name = name;//进程名 
    this.totaltime = totaltime;//总时间 
    this.privilege = privilege;//优先级别 
    this.runtime = 2;//时间片，这里设值为2 
    this.isNotFinish = true;//是否执行完毕 
    System.out.println("初始值： processname : " + name + " totaltime : " + totaltime + " privilege :" + privilege ); 
    System.out.println(); 
   } 
  
   public void run (MyQueue mq){//进程的基于时间片的执行算法  
    if(totaltime>1){ 
       totaltime-=runtime;//在总时间大于1的时候，总时间=总时间-时间片 
       privilege--; 
       System.out.println(" processname : " + name + " remaintime : " + totaltime + " privilege :" + privilege ); 
    }else if(totaltime==1){ 
     totaltime--;//在总时间为1时，执行时间为1 
     privilege--; 
     System.out.println(" processname : " + name + " remaintime : " + totaltime + " privilege :" + privilege ); 
    }else{ 
     isNotFinish = false;//总时间为0，将isNotFinish标记置为false 
    }     
      if(isNotFinish==true){ 
      mq.deQueue(); 
                mq.enQueue(this); 
      } 
   } 
} 
