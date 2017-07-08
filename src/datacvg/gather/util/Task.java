package datacvg.gather.util;


import org.apache.log4j.Logger;

/**
 * 要执行的任务
 * @author mql
 **/   
public class Task {
	private static final Logger logger = Logger.getLogger(Task.class);
	public static final int READY = 0;
	public static final int RUNNING = 1;
	public static final int FINISHED = 2;
	private int status; // 声明一个任务的自有业务含义的变量，用于标识任务
	private int taskId; // 任务的初始化方法

	public Task(){
		
	}
	public Task(int taskId) {
		this.status = READY;
		this.taskId = taskId;
	}
   
	/** * 执行任务 */
	public void execute() {
		// 设置状态为运行中
		setStatus(Task.RUNNING);
		logger.info("当前线程 ID 是：" + Thread.currentThread().getName() + " | 任务 ID 是：" + this.taskId);
		
	}
	
	public void exit(){
		
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public int getTaskId() {
		return taskId;
	}

}

