package datacvg.gather.util;

import java.util.ArrayList; 
import java.util.List; 

import org.apache.log4j.Logger;

import com.core.tag.util.SystemConstant;

import datacvg.gather.entity.GatherBean;
import datacvg.taskmanage.service.TaskManageService;
/**
 * 多线程采集执行
 */  
public class TaskDistributor { 
	private static final Logger gatherlogger = Logger.getLogger(TaskDistributor.class);
	//采集任务等待集合
	public static List<GatherBean> GatherPools = new ArrayList<GatherBean>();
	/**
	 * * 采集入口 *
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */  
	public static void main(String[] args) throws Exception { 
		
		// 获取采集任务信息
//		GatherTaskImpl gather =new GatherTaskImpl();
		TaskManageService  gather =new TaskManageService();
//		gather.setDao(HibernateSessionFactory.GetDao());
		//显示设置
		boolean isDisplay = false;
		if(args!=null&&args.length>0){
			isDisplay= (args[0].equals("1"))?true:false;
		}
		//获取采集任务
		String sql = SystemConstant.get("getGatherInfo");
		List<GatherBean> list = gather.getGatherInfo(sql, null);
		if(list==null&&list.size()==0){
			gatherlogger.info("数据库连接失败或者没有配置任何任务!");
			return;
		}
		List<Task> taskList = new ArrayList<Task>();  
		List<BaseThread> basethread = new ArrayList<BaseThread>();  
		
		// 设定要启动的工作线程数为 4 个
		int threadCount = 4; 
		
		try {
			int activenum=0;
			// 初始化要执行的任务列表
			for (int i = 0; i < list.size(); i++) {
				GatherExe ge = new GatherExe();
				if(list.get(i)!=null&&list.get(i).isIsvalid()){
					ge.setGatherbean(list.get(i));
					//是否显示容器
					ge.setDiaplay(isDisplay);
					//生成相等线程任务集合
					if(basethread.size()<threadCount){
						basethread.add(ge);
					}else{
						GatherPools.add(list.get(i));
					}
					activenum++;
				}
			}
			gatherlogger.info("任务数:"+list.size()+",激活:"+activenum+",等待："+GatherPools.size());
			return;
			 
		} catch (Exception e) {
			gatherlogger.info("运行发出错误!");
			e.printStackTrace();
			System.exit(0);
		}
	
		List<Task>[] taskListPerThread = distributeTasks(taskList, threadCount);  
		gatherlogger.info("实际要启动的工作线程数："+taskListPerThread.length); 
		for (int i = 0; i < taskListPerThread.length; i++) { 
			
				Thread workThread = new WorkThread(taskListPerThread[i],i);
				workThread.start(); 
				Thread.sleep(10000);
				
			}  
		} 
	/**
	 * 把 List 中的任务分配给每个线程，先平均分配，剩于的依次附加给前面的线程 * 返回的数组有多少个元素 (List)
	 * @param taskList 待分派的任务列表 
	 * @param threadCount 线程数 
	 * @return 列表的数组，每个元素中存有该线程要执行的任务列表
	 */  
	public static List[] distributeTasks(List<Task> taskList, int threadCount) { 
		// 每个线程至少要执行的任务数,假如不为零则表示每个线程都会分配到任务
		int minTaskCount = taskList.size() / threadCount;  
		// 平均分配后还剩下的任务数，不为零则还有任务依个附加到前面的线程中
		int remainTaskCount = taskList.size() % threadCount;   
		// 实际要启动的线程数,如果工作线程比任务还多
		// 自然只需要启动与任务相同个数的工作线程，一对一的执行
		// 毕竟不打算实现了线程池，所以用不着预先初始化好休眠的线程
		int actualThreadCount = minTaskCount > 0 ? threadCount : remainTaskCount;   
		// 要启动的线程数组，以及每个线程要执行的任务列表
		List<Task>[] taskListPerThread = new List[actualThreadCount]; 
		int taskIndex = 0;  
		// 平均分配后多余任务，每附加给一个线程后的剩余数，重新声明与 remainTaskCount
		// 相同的变量，不然会在执行中改变 remainTaskCount 原有值，产生麻烦
		int remainIndces = remainTaskCount; 
		for (int i = 0; i < taskListPerThread.length; i++) {  
			taskListPerThread[i] = new ArrayList();  
			// 如果大于零，线程要分配到基本的任务
			
			if (minTaskCount > 0) {
				for (int j = taskIndex; j < minTaskCount + taskIndex; j++) { 
					taskListPerThread[i].add(taskList.get(j)); 
					}   taskIndex += minTaskCount;   
			}  
			// 假如还有剩下的，则补一个到这个线程中
			if (remainIndces > 0) { 
				taskListPerThread[i].add(taskList.get(taskIndex++)); 
				remainIndces--; 
			}  
		}  
		// 打印任务的分配情况
		for (int i = 0; i < taskListPerThread.length; i++) {  
//			logger.info("线程 " + i + " 的任务数：" + 1 );
			gatherlogger.info("线程 " + i + " 的任务数：" +     taskListPerThread[i].size() + " 区间["   + taskListPerThread[i].get(0).getTaskId() + ","   + taskListPerThread[i].get(taskListPerThread[i].size() - 1).getTaskId() + "]");
		}
		return taskListPerThread;
	}
	//获取下一个待执行任务
	public static synchronized GatherBean NextGatherTask(){
		if(GatherPools.size()>0){
			try{
				GatherBean gt = GatherPools.get(0);
				//移除队列
				GatherPools.remove(0);
				return gt;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
