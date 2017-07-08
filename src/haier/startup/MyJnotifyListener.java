package haier.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;



import com.core.tag.util.SystemConstant;

public class MyJnotifyListener implements ServletContextListener {
	private static final Logger haier = Logger.getLogger(MyJnotifyListener.class);
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	public void contextInitialized(ServletContextEvent arg0) {
		haier.info("加载  "+SystemConstant.get("haierfilePath")+" 目录下文件信息进行监听");
//		FileMonitor filemonitor = new FileMonitor();
		try {
			
			//初始化文件监控的目录，对应文件触发的事件
//			FileMonitorConfig config = new FileMonitorConfig(
//					SystemConstant.get("haierfilePath"),
//					true,/** 是否监视子目录，即级联监视 */
//					FileMonitorConfig.MASK.CREATED,FileMonitorConfig.MASK.MODIFIED);
			// 启动监听
//			boolean flag = new JnotifyRun().addWatch();
//			System.out.println(flag);
//			filemonitor.addWatch(config);
//			filemonitor.start();
		}catch(Exception e) {
			haier.error("加载文件监听程序失败！",e.fillInStackTrace());
			e.printStackTrace();
			
//			new JnotifyRun().removeWatch();
//			filemonitor.removeWatch(new File(
//					SystemConstant.get("haierfilePath"))); 
//			filemonitor.stop();
		}
		haier.info("加载  "+SystemConstant.get("haierfilePath")+" 目录下文件信息完成");
	}
} 
