package datacvg.parse.startup;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.core.tag.util.SystemConstant;

import datacvg.gather.embeddedDB.DBInstance;
import datacvg.log.LogView;

public class TaskJnotifyListener implements ServletContextListener
{
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		// 加载嵌入式数据库
		DBInstance.init(SystemConstant.get("myDBPath"));
		LogView view = new LogView();
		try
		{
			// 清空日志大小
			view.saveProperty("0", "");
			new TaskJnotifyRun().beginWatch();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
