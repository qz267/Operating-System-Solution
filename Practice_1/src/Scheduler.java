import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
* @author QIN ZHENG
*/
public class Scheduler{
static List<String>   proName   = new ArrayList<String>();
static List<Integer> arriveTime = new ArrayList<Integer>();
static List<Integer> serviceTime = new ArrayList<Integer>();
static List<Integer> timeSize = new ArrayList<Integer>();

public static void main(String[] args) throws IOException{

   readFileByLines("source0.txt", " ");

   List<Process> listPro = new ArrayList<Process>();
   for(int i = 0; i < proName.size(); i++){
    Process pro = new Process(proName.get(i), i, arriveTime.get(i), serviceTime.get(i));
    listPro.add(pro);
   }
   List<Process> list = new ArrayList<Process>();

   for(int time: timeSize){
    listPro.clear();
    for(int i = 0; i < proName.size(); i++){
     Process pro = new Process(proName.get(i), i, arriveTime.get(i), serviceTime.get(i));
     listPro.add(pro);
    }
    list = timeSizeManage(listPro, time);
    System.out.println("时间片大小: " + time);
    printProcessDetail(list);
   }
}

private static void printProcessDetail(List<Process> list){
   for(Process process: list){
    System.out.println("进程: " + process.getName() + " 到达时间: " + process.getArriveTime() + " 服务时间: " + process.getServiceTime() + " 开始时间: " + process.getBeginTime() + " 完成时间: " + process.getEndTime() + " 周转时间: " + (process.getEndTime() - process.getBeginTime()) + " 带权周转时间:" + process.getWeightTime());
   }
   Double avgTotalTime = 0.0;
   for(Process process: list){
    avgTotalTime += process.getTotalTime();
   }
   System.out.print("平均周转时间:" + avgTotalTime / list.size());
   Double avgWeightTime = 0.0;
   for(Process process: list){
    avgWeightTime += process.getWeightTime();
   }
   System.out.println(" 平均带权周转时间:" + avgWeightTime / list.size());
}

public static List<Process> FirstComeFirstService(List<Process> list){
   List<Process> listPro = new ArrayList<Process>(list);
   double curTime = 0.0;
   for(Process process: listPro){
    process.setStatus(Process.RUNNING);
    process.setBeginTime(curTime); // 设置开始时间=当前时间
    curTime += process.getServiceTime(); // 设置当前时间=当前时间+服务时间
    process.setEndTime(curTime); // 设置结束时间
    process.setStatus(Process.COMPLETE);
    process.setTotalTime(); // 设置周转时间 = 完成时间-到达时间
    process.setWeightTime(); // 设置带权周转时间 = 完成时间/服务时间
   }
   return listPro;
}

public static List<Process> ShortTaskFirst(List<Process> list){
   List<Process> listPro = new ArrayList<Process>(list);
   Process curPro = new Process();
   double curTime = 0.0;
   int count = 0;

   curPro = listPro.get(0);
   curPro.setStatus(Process.RUNNING);
   curPro.setBeginTime(curTime);
   curTime += curPro.getServiceTime();
   curPro.setEndTime(curTime);
   curPro.setStatus(Process.COMPLETE);
   curPro.setTotalTime();
   curPro.setWeightTime();
   count++;
   listPro.set(0, curPro);

   List<Process> tempList = new ArrayList<Process>();

   while(count < listPro.size()){
    tempList.clear();
    for(int i = 0; i < listPro.size(); i++){
     if(listPro.get(i).getArriveTime() > curTime){
      continue;
     }
     if(listPro.get(i).getStatus() != Process.COMPLETE){
      tempList.add(listPro.get(i));
     }
    }
    curPro = tempList.get(0);
    for(int i = 0; i < tempList.size(); i++){
     if(curPro.getServiceTime() > tempList.get(i).getServiceTime()){
      curPro = tempList.get(i);
     }
    }
    curPro.setStatus(Process.RUNNING);
    curPro.setBeginTime(curTime);
    curTime += curPro.getServiceTime();
    curPro.setEndTime(curTime);
    curPro.setStatus(Process.COMPLETE);
    curPro.setTotalTime();
    curPro.setWeightTime();
    count++;
    listPro.set(curPro.getProcessID(), curPro);
   }
   return listPro;
}

public static List<Process> timeSizeManage(List<Process> list, int timeSize){
   List<Process> listPro = new ArrayList<Process>(list);
   Queue<Process> qPro = new LinkedList<Process>();
   Process curPro = new Process();
   double time = 0.0; // 当前时间
   do{
    if(qPro.size() == 0){
     for(Process process: listPro){// 遍历当前进程列表找到第一个到达的进程
      if(process.getArriveTime() <= time){
       process.setStatus(Process.READY);
       qPro.add(process); // 将这个进程加入就绪队列中 并设置其状态为READY
      }
     }
     continue; // 退出本次循环
    }
    curPro = qPro.poll(); // 取得队首进程
    if(curPro.getRunningTime() == 0){// 如果运行时间为0 则设置开始时间为 当前时间time
     curPro.setBeginTime(time);
    }
    curPro.setRunningTime(curPro.getRunningTime() + timeSize); // 设置进程已经运行时间
    time += timeSize; // 修改当前时间
    if(curPro.getRunningTime() >= curPro.getServiceTime()){
     time -= curPro.getRunningTime() - curPro.getServiceTime(); // 修正当前时间
     curPro.setEndTime(time); // 设置完成时间
     curPro.setRunningTime(curPro.getServiceTime()); // 设置运行时间
     curPro.setStatus(Process.COMPLETE);// 设置完成标志
     curPro.setTotalTime(); // 设置周转时间
     curPro.setWeightTime(); // 设置带权周转时间
    }

    for(Process process: listPro){
     if((process.getStatus() == Process.BLOCK) && process.getArriveTime() <= time){
      process.setStatus(Process.READY); // 设置进程状态为就绪
      qPro.add(process); // 加入就绪队列
     }
    }
    if(curPro.getStatus() != Process.COMPLETE){
     curPro.setStatus(Process.READY);
     qPro.add(curPro); // 将当前进程加入就绪队列
    }
   } while(!qPro.isEmpty());
   return listPro;
}

public static void readFileByLines(String fileName, String token){
   File file = new File(fileName);
   BufferedReader reader = null;
   try{
    reader = new BufferedReader(new FileReader(file));
    String tempString = null;
    while((tempString = reader.readLine()) != null){
     if(tempString.startsWith("进程名称:")){
      tempString = tempString.substring(tempString.indexOf(":") + 1);
      while((tempString.indexOf(token) != -1) && (tempString.length() != 0)){
       proName.add(tempString.substring(0, tempString.indexOf(token)));
       tempString = tempString.substring(tempString.indexOf(token) + 1);
      }
     }
     else if(tempString.startsWith("到达时间:")){
      tempString = tempString.substring(tempString.indexOf(":") + 1);
      while((tempString.indexOf(token) != -1) && (tempString.length() != 0)){
       arriveTime.add(new Integer(tempString.substring(0, tempString.indexOf(token))));
       tempString = tempString.substring(tempString.indexOf(token) + 1);
      }
     }
     else if(tempString.startsWith("服务时间:")){
      tempString = tempString.substring(tempString.indexOf(":") + 1);
      while((tempString.indexOf(token) != -1) && (tempString.length() != 0)){
       serviceTime.add(new Integer(tempString.substring(0, tempString.indexOf(token))));
       tempString = tempString.substring(tempString.indexOf(token) + 1);
      }
     }
     else if(tempString.startsWith("时间片:")){
      tempString = tempString.substring(tempString.indexOf(":") + 1);
      while((tempString.indexOf(token) != -1) && (tempString.length() != 0)){
       timeSize.add(new Integer(tempString.substring(0, tempString.indexOf(token))));
       tempString = tempString.substring(tempString.indexOf(token) + 1);
      }
     }
    }
    reader.close();
   }
   catch (IOException e){
    e.printStackTrace();
   }
   finally{
    if(reader != null){
     try{
      reader.close();
     }
     catch (IOException e1){
      System.out.println(e1.getMessage());
     }
    }
   }
}
}
