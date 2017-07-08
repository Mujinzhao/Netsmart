package datacvg.gather.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import datacvg.gather.entity.GatherBean;
public class BaseThread extends Thread{
	private static final Logger gather = Logger.getLogger(BaseThread.class);
	//采集任务等待集合
	public static List<GatherBean> gatherPools = new ArrayList<GatherBean>();
	public Vector<BaseThread> threads = new Vector<BaseThread>();//存放线程的向量  
	public static List<GatherBean> getGatherPools() {
		return gatherPools;
	}
	public static void setGatherPools(List<GatherBean> gatherPools) {
		BaseThread.gatherPools = gatherPools;
	}

	/**
     * isDead:是否杀死线程
     */
	public  static boolean isDead = false;
    
    /**
     * isStop:是否停止
     */
	public static boolean isStop = false;
    
    /**
     * isRun:是否已开始执行
     */
    static boolean isRun = false;
    
    /**
     * isWait:是否处于等待
     */
    private static boolean isSleep = false;
    
    /**
     * taskId:任务创建
     */
    private int taskId; 
    
    public BaseThread(){
		
	}
    public BaseThread(int taskId) {
            super();
            this.taskId = taskId;
            this.setDaemon(false);//设置为非守护线程
            gather.info("线程:["+this.taskId+"] 被创建");
    }
    
    public BaseThread(String taskcode) {
            super(taskcode);
            this.setDaemon(false);//设置为非守护线程
            gather.info("任务:["+taskcode+"] 被执行");
    }

    /**
     *<p>Title: run</p>
     *<p>Description:JDK线程类自带方法</p>
     * @param @return 设定文件
     * @return  boolean 返回类型
     * @throws
    */
    public void run() {
//            isRun = true;
//            while(!isDead){
//                    while(true){
//                            if(!isStop){
//                                    if(preConditions())
//                                    	execute();
//                            }else{
//                                    break;
//                            }
//                            //sleep(256);//缓解CPU压力，即唤醒线程需要至多256ms
//                    }
//            }
//            isRun = false;
//            gather.info("线程:[" + this.getName() +"-"+this.taskId+ "] 消亡");
    }
    
    /**
     *<p>Title: preConditions</p>
     *<p>Description:执行体前置条件</p>
     * @param @return 设定文件
     * @return  boolean 返回类型
     * @throws
    */
    protected boolean preConditions(){
            return true;
    }
    
    /**
     *<p>Title: execute</p>
     *<p>Description:线程执行体</p>
     * @param  设定文件
     * @return  void 返回类型
     * @throws
    */
    protected void execute(){
    	GatherExe gatherexecute =new GatherExe();
    	for(GatherBean gatherbean :gatherPools){
        	gatherexecute.setGatherbean(gatherbean);
        	gatherexecute.execute();
    	}
    }

    /**
     *<p>Title: kill</p>
     *<p>Description:结束线程</p>
     * @param  设定文件
     * @return  void 返回类型
     * @throws
    */
    public void kill(String taskcode){
            this.isStop = true;
            this.isDead = true;
            this.isRun = false;
            this.interrupt();
            gather.info("任务:["+taskcode+"] 被终止");
    }
    
    /**
     *<p>Title: halt</p>
     *<p>Description:暂停进程，非休眠</p>
     * @param  设定文件
     * @return  void 返回类型
     * @throws
    */
    public void halt(String taskcode){
            this.isStop = true;
            gather.info("任务:["+taskcode+"] 被暂停");
    }
    
    /**
     *<p>Title: reStart</p>
     *<p>Description:重新执行线程</p>
     * @param  设定文件
     * @return  void 返回类型
     * @throws
    */
    public void reStart(String taskcode){
            this.isStop = false;
            gather.info("任务:["+taskcode+"] 被重新启动");
    }

    /**
     *<p>Title: isRun</p>
     *<p>Description:是否处于执行态</p>
     * @param @return 设定文件
     * @return  boolean 返回类型
     * @throws
    */
    public boolean isRun() {
            return isRun;
    }

    /**
     *<p>Title: isSleep</p>
     *<p>Description:是否处于休眠态</p>
     * @param @return 设定文件
     * @return  boolean 返回类型
     * @throws
    */
    public boolean isSleep() {
            return isSleep;
    }
    
    public boolean isDead(){
            return isDead;
    }
    
    /**
     *<p>Title: sleep</p>
     *<p>Description:休眠线程</p>
     * @param @param millis
     * @param @throws InterruptedException 设定文件
     * @return  void 返回类型
     * @throws
    */
    public void sleep(int millis){
            isSleep = true;
            try {
                    Thread.sleep(millis);
//                    this.sleepTime += millis;
                    if(notifyPreConditions())
                    	notifyObs();
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
            isSleep = false;
    }
    
    private void notifyObs() throws InterruptedException  
    {  
           this.wait();  
    }  
     
    private boolean notifyPreConditions()  
    {  
           return true;  
    } 
}
