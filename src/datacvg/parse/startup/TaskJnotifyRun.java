package datacvg.parse.startup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

import org.apache.log4j.Logger;

import com.core.tag.util.SystemConstant;

import core.spider.entity.StaticVar;
import core.util.RegexValidate;

import datacvg.parse.action.ExtractAction;

/**
 * 解析任务监控事件处理
 * 
 * @author admin
 * 
 */
public class TaskJnotifyRun
{
	private static final Logger parser = Logger.getLogger(TaskJnotifyRun.class);
	private JNotifyListener listener;
	private ExtractAction extract = null;

	public TaskJnotifyRun()
	{
		listener = new JNotifyListener()
		{
			public void fileRenamed(int wd, String parent, String oldName, String newName)
			{
				// logger.info("重命名文件个数 #" + wd + "  root = " + parent + " oldName = "+ oldName + "  newName = " + newName + " 开始解析");
			}

			public void fileModified(int wd, String parent, String name)
			{
				// logger.info("修改文件个数 #" + wd + "  root = " + parent + "  FileName = " + name + " 开始解析");
			}

			public void fileDeleted(int wd, String parent, String name)
			{
				// logger.info("删除文件个数 #" + wd + "  root = " + parent + "  FileName = " + name + " 开始解析");
			}

			public void fileCreated(int wd, String parent, String name)
			{
				try
				{
					parser.info("监测到文件：" + parent + name);
					Thread.sleep(4000);
//					parser.info("4秒后再执行解析操作");
					created(parent, name);
//					parser.info("root = " + parent + "  FileName = " + name + " 结束解析");
				}
				catch (InterruptedException e)
				{
					parser.info("解析 root = " + parent + "  FileName = " + name + " 文件异常");
					e.printStackTrace();
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
			parser.info("启动 " + SystemConstant.get("gather_root_dir") + "目录下的文件监控");
			this.watchID = (JNotify.addWatch(SystemConstant.get("gather_root_dir"), mask, watchSubtree, listener));
			extract = new ExtractAction();
			extract.initService();
			// 解析全部采集信息
			extract.parserAlltaskcode();
			return true;
			// 死循环，线程一直执行，休眠一分钟后继续执行，主要是为了让主线程一直执行
			// 休眠时间和监测文件发生的效率无关（就是说不是监视目录文件改变一分钟后才监测到，监测几乎是实时的，调用本地系统库）
			// while(true){
			// try {
			// Thread.sleep(60000);
			// } catch (InterruptedException e) {
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
			parser.info("结束 监控" + SystemConstant.get("gather_root_dir") + "目录下的目录、文件信息" + watchID);
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
		if (filetype.equals("html") || filetype.equals("htm"))
		{
			// extract.parserAllByDir(parent+name,name);
			Pattern pattern = null;
			Matcher matcher = null;
			String taskCode;
			String files = parent + name;
			String temp[] = files.split("[\\\\]");
			String fileName = temp[temp.length - 2];
			pattern = Pattern.compile("(^.*?)(_.*)?$");
			matcher = pattern.matcher(fileName);
			if (matcher.find())
			{
				taskCode = matcher.group(1);
				if (RegexValidate.StrNotNull(taskCode))
				{
//					StaticVar.notNullField.clear();
					extract = new ExtractAction(SystemConstant.get("gather_root_dir"), "");
					extract.parserBytaskcode(taskCode, parent + name);
					extract.distory();
				}
				else
				{
					extract.distory();
					parser.info("上传的文件未能提取出任务编号!");
				}
			}
		}
		else
		{
			parser.info("发现解析文件" + parent + name + "不是html文件！");
		}
	}
}
