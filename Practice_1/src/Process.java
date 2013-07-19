public class Process {
   
    /**
     * @param name
     * @param processID
     * @param arriveTime
     * @param serviceTime
     */
    public Process(String name, int processID, double arriveTime, double serviceTime) {
super();
this.name = name;
this.processID = processID;
this.arriveTime = arriveTime;
this.serviceTime = serviceTime;
this.totalTime = 0.0;
this.weightTime = 0.0;
this.beginTime = 0.0;
this.runningTime = 0.0;
this.status = 2;
    }


    public Process() {
super();
    }


    private String name; //进程名
    private int processID; //进程id
    private double arriveTime; //到达时间
    private double serviceTime; //服务时间
    private double totalTime; //周转时间
    private double weightTime; //带权周转时间
    private double beginTime; //开始运行时间
    private double endTime; //完成时间
    private double runningTime; //已运行时间
    
    private int status; //进程状态
    
    public static int READY = 0; //就绪
    public static int RUNNING = 1; //运行
    public static int BLOCK = 2; //阻塞
    public static int COMPLETE = 4; //完成
    
    
    public void setProcessID(int processID) {
this.processID = processID;
    }

    
    public int getProcessID() {
return processID;
    }
    
    public void setName(String name) {
this.name = name;
    }
    
    public String getName() {
return name;
    }
    
    public void setArriveTime(double arriveTime) {
this.arriveTime = arriveTime;
    }
    
    public double getArriveTime() {
return arriveTime;
    }
    
    public void setServiceTime(double serviceTime) {
this.serviceTime = serviceTime;
    }
    
    public double getServiceTime() {
return serviceTime;
    }
    
    public void setTotalTime() {
this.totalTime = this.endTime-this.arriveTime;
    }
    
    public double getTotalTime() {
return totalTime;
    }
    
    public void setWeightTime() {
this.weightTime = this.totalTime/this.serviceTime;
    }
    
    public double getWeightTime() {
return weightTime;
    }
    
    public void setBeginTime(double beginTime) {
this.beginTime = beginTime;
    }
    
    public double getBeginTime() {
return beginTime;
    }
    
    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(double endTime) {
this.endTime = endTime;
    }


    /**
     * @return the endTime
     */
    public double getEndTime() {
return endTime;
    }


    public void setRunningTime(double runningTime) {
this.runningTime = runningTime;
    }
    
    public double getRunningTime() {
return runningTime;
    }
    
    public void setStatus(int status) {
this.status = status;
    }
   
    public int getStatus() {
return status;
    }

}
