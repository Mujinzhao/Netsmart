package datacvg.excel.startup;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

import org.apache.log4j.Logger;

import com.core.tag.util.SystemConstant;

import core.util.RegexValidate;

import datacvg.excel.action.ExcelController;
import datacvg.excel.util.DistoryCollection;

/**
 * 解析Excel任务监控事件处理
 * 
 * @author sxy
 * 
 */
public class ExcelJnotifyRun
{
	private static final Logger excel = Logger.getLogger(ExcelJnotifyRun.class);
	private JNotifyListener listener;
	private ExcelController extract = null;

	public ExcelJnotifyRun()
	{
		listener = new JNotifyListener()
		{
			public void fileRenamed(int wd, String parent, String oldName, String newName)
			{
				// excel.info("重命名文件个数 #" + wd + "  root = " + parent + " oldName = "+ oldName + "  newName = " + newName + " 开始解析");
			}

			public void fileModified(int wd, String parent, String name)
			{
				// excel.info("修改文件个数 #" + wd + "  root = " + parent + "  FileName = " + name + " 开始解析");
			}

			public void fileDeleted(int wd, String parent, String name)
			{
				// excel.info("删除文件个数 #" + wd + "  root = " + parent + "  FileName = " + name + " 开始解析");
			}

			public void fileCreated(int wd, String parent, String name)
			{
				try
				{
					excel.info("监控文件个数 #" + wd + "  root = " + parent + "  FileName = " + name);
					Thread.sleep(5000);
					excel.info("5秒后再执行解析操作");
					created(parent, name);
					excel.info("监控文件个数 #" + wd + "  root = " + parent + "  FileName = " + name + " 结束解析");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
					excel.error("解析 root = " + parent + "  FileName = " + name + " 文件异常" + e.getMessage());
				}
			}
		};
	}

	/** 监视目录的事件 */
	int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;
	/** 是否监视子目录，即级联监视 */
	boolean watchSubtree = true;

	/** 监听程序Id */
	public int watchID;

	/**
	 * 添加监控目录事件信息
	 * 
	 * @return
	 */
	public boolean beginWatch()
	{
		try
		{
			excel.info("启动 " + SystemConstant.get("templatePath") + "目录下的文件监控");
			this.watchID = (JNotify.addWatch(SystemConstant.get("templatePath"), mask, watchSubtree, listener));
			return true;
			// extract = new ExcelController(SystemConstant.get("templatePath"),"");
			// 死循环，线程一直执行，休眠一分钟后继续执行，主要是为了让主线程一直执行
			// 休眠时间和监测文件发生的效率无关（就是说不是监视目录文件改变一分钟后才监测到，监测几乎是实时的，调用本地系统库）
			// while (true)
			// {
			// try
			// {
			// Thread.sleep(60000);
			// }
			// catch (InterruptedException e)
			// {
			// e.printStackTrace();
			// return false;
			// }
			// return true;
			// }
		}
		catch (JNotifyException e)
		{
			e.printStackTrace();
			removeWatch();
			return false;
		}
	}

	/**
	 * 释放监控目录事件
	 */
	public void removeWatch()
	{
		boolean res = false;
		try
		{
			excel.info("结束 监控" + SystemConstant.get("templatePath") + "目录下的目录、文件信息" + watchID);
			res = JNotify.removeWatch(watchID);
		}
		catch (JNotifyException e)
		{
			e.printStackTrace();
		}
		if (!res)
		{
			// invalid watch ID specified.
		}
	}

	/**
	 * 执行解析任务
	 * 
	 * @param parent
	 * @param name
	 */
	protected void created(String parent, String name)
	{
		String filetype = name.substring(name.lastIndexOf(".") + 1, name.length());
		if (filetype.equals("xls") || filetype.equals("xlsx"))
		{
			String files = parent + name;
			String temp[] = files.split("[\\\\]");
			String templateName = temp[temp.length - 2];
			// 获取文件路径中的模板名称
			if (RegexValidate.StrNotNull(templateName))
			{
				// 初始化模板信息
				extract = new ExcelController(SystemConstant.get("gather_root_dir"), files);
				extract.ExcelPaserByExceName(templateName);
			}
			else
			{
				excel.info("未能提取文件路径中的模板别名信息");
			}
		}
		else
		{
			excel.info("系统检测到" + parent + "目录下 " + name + " 文件类型有误!");
		}
		DistoryCollection.distory();
	}
}
