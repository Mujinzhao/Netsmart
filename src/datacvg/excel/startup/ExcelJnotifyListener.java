package datacvg.excel.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ExcelJnotifyListener implements ServletContextListener
{
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		// 加载嵌入式数据库
		new ExcelJnotifyRun().beginWatch();
	}

}
