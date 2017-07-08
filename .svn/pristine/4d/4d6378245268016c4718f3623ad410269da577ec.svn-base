package datacvg.taskmanage.action;

import haier.dataspider.param.service.SpringContextUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.core.tag.util.SystemConstant;

import core.spider.entity.StaticVar;
import core.util.RegexValidate;

import datacvg.gather.entity.GatherBean;
import datacvg.gather.util.GatherExe;
import datacvg.taskmanage.constants.TaskJobConstants;
import datacvg.taskmanage.service.TaskManageService;

public class TimingSendTimer implements Job
{
	private static final Logger gather = Logger.getLogger(TimingSendTimer.class);

	/**
	 * Service注入
	 */
	private TaskManageService taskmanageService;

	public TaskManageService getTaskmanageService()
	{
		return taskmanageService;
	}

	public void setTaskmanageService(TaskManageService taskmanageService)
	{
		this.taskmanageService = taskmanageService;
	}

	/**
	 * 容器启动时初始化所以有效任务
	 * 
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void initJobTrigger() throws SchedulerException, ParseException
	{
		// System.out.println("进入次数");
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		List<GatherBean> gatherlist = null;

		// 获取任务列表的SQL语句
		String sql = " select t.taskcode,t.taskname,t.taskurl,t.encodeurl,t.tasktype,t.isactive,t.isspider,t.jsparse,t.totalpage,t.timetype,t.taskenabled"
				+ " from gather_parser_task t where  t.isactive='T' and t.taskenabled='run'";
		// System.out.println("次数");
		try
		{
			gatherlist = taskmanageService.getGatherInfo(sql, new HashMap<String, String>());
			if (null != gatherlist && gatherlist.size() > 0)
			{
				for (GatherBean gatherbean : gatherlist)
				{
//					GatherExe gatherexecute = new GatherExe();
					// 获取执行的时间
					String timetype = gatherbean.getCronexpression();
					// 为触发器设置定时表达式
					String cronExpression = getDbCronExpression(timetype);
					// 数据库中该任务配置运行时间 及状态位
					if (RegexValidate.StrNotNull(cronExpression) && TaskJobConstants.JS_ENABLED.equals(gatherbean.getEnabled()))
					{
						// 新建任务，任务组为默认的Scheduler.DEFAULT_GROUP，需要执行的任务类为TimingSendTimerAction.class
						JobDetail jobDetail = new JobDetail("reportJob_" + gatherbean.getTaskcode(), Scheduler.DEFAULT_GROUP, TimingSendTimer.class);

						CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger("trigger_" + gatherbean.getTaskcode(), Scheduler.DEFAULT_GROUP);
						if (null != cronTrigger)
						{
							// gather.info("移除 采集任务"+gatherbean.getTaskcode() +"时间 "+cronExpression);
							// //移除触发器
							// scheduler.unscheduleJob(cronTrigger.getName(), Scheduler.DEFAULT_GROUP);
						}
						else
						{
							// 新建触发器，触发器为默认的Scheduler.DEFAULT_GROUP
							cronTrigger = new CronTrigger("trigger_" + gatherbean.getTaskcode(), Scheduler.DEFAULT_GROUP);
							cronTrigger.setCronExpression(cronExpression);
							gather.info("新建任务 采集任务" + gatherbean.getTaskcode() + "执行时间 " + cronExpression);
							try
							{
								scheduler.scheduleJob(jobDetail, cronTrigger);
								// // 执行采集程序
								// HashMap<String, Boolean> threadstate = new HashMap<String, Boolean>();
								// gatherexecute.setGatherbean(gatherbean);
								// threadstate.put("isStop", false);
								// threadstate.put("isDead", false);
								// StaticVar.taskandthreadstate.put(gatherbean.getTaskcode(), threadstate);
								// //启动新增定时器任务
								// gatherexecute.execute();
							}
							catch (SchedulerException e)
							{
								// 启动验证失败
								e.printStackTrace();
								gather.error("启动验证失败!" + e.getMessage());
							}
						}
					}
				}
			}
			// 初始化任务只需要执行一次，执行一次后移除初始化触发器
			scheduler.unscheduleJob("InitTrigger", Scheduler.DEFAULT_GROUP);
			// 任务启动
			scheduler.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			gather.error("启动时初始化有效任务失败!" + e.getMessage());
		}

	}

	/**
	 * 启动采集任务
	 */
	@Override
	public void execute(JobExecutionContext je) throws JobExecutionException
	{
		GatherExe gatherexecute = new GatherExe();
		Map<String, String> paramMap = new HashMap<String, String>();
		// 根据任务ID 取出任务信息
		String sql = " select t.taskcode,t.taskname,t.taskurl,t.encodeurl,t.tasktype,t.isactive,t.isspider,t.jsparse,t.totalpage,t.timetype,t.taskenabled"
				+ " from gather_parser_task t where t.taskcode=:taskcode and t.isactive='T'  and t.taskenabled='run' ";
		try
		{
			// 获取触发器名称
			String triggerName = je.getTrigger().getName();
			// 根据触发器名称得到对应的任务编号
			String taskcode = String.valueOf(triggerName.split("_")[1]);

			paramMap.put("taskcode", taskcode);

			gather.info("采集任务启动线程名称" + triggerName + "任务号:" + taskcode);
			if (null == taskmanageService)
			{
				taskmanageService = (TaskManageService) SpringContextUtils.getBean("taskmanageService");
			}
			// 获取当前任务下的采集模板信息
			List<GatherBean> gatherbeanList = taskmanageService.getGatherInfo(sql, paramMap);
			if (null != gatherbeanList && gatherbeanList.size() > 0)
			{
				HashMap<String, Boolean> threadstate = new HashMap<String, Boolean>();
				GatherBean gatherbean = gatherbeanList.get(0);
				gatherexecute.setGatherbean(gatherbean);
				threadstate.put("isStop", false);
				threadstate.put("isDead", false);
				StaticVar.taskandthreadstate.put(taskcode, threadstate);
				gatherexecute.execute();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			gather.error("启动采集任务失败!" + e.getMessage());
		}
	}

	/**
	 * 启动或禁止任务触发器
	 * 
	 * @param condition
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void enableTrigger(String taskcode, String mothed, String timetype) throws SchedulerException, ParseException
	{
		// 获取调度工厂对象
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		String cronExpression = getDbCronExpression(timetype);
		// 启动任务
		if (TaskJobConstants.JS_ENABLED.equals(mothed))
		{
			// 获取执行的时间
			JobDetail jobDetail = new JobDetail("reportJob_" + taskcode, Scheduler.DEFAULT_GROUP, TimingSendTimer.class);
			// 执行任务调度
			// 新建触发器，触发器为默认的Scheduler.DEFAULT_GROUP
			CronTrigger cronTrigger = new CronTrigger("trigger_" + taskcode, Scheduler.DEFAULT_GROUP);
			// 为触发器设置定时表达式
			cronTrigger.setCronExpression(cronExpression);
			// 启动新增定时器任务
			scheduler.scheduleJob(jobDetail, cronTrigger);
		}
		else
		{
			// 移除触发器
			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger("trigger_" + taskcode, Scheduler.DEFAULT_GROUP);
			if (null != cronTrigger)
			{
				scheduler.unscheduleJob(cronTrigger.getName(), Scheduler.DEFAULT_GROUP);
			}
		}
		// 调度器启动
		scheduler.start();
	}

	/**
	 * 获取执行任务时间格式转换
	 * 
	 * @param timetye
	 * @return
	 */
	private String getDbCronExpression(String timetype)
	{
		// 为触发器设置定时表达式
		String cronExpression = "";
		if (RegexValidate.StrNotNull(timetype))
		{
			if (timetype.equals("1"))
			{
				// cronExpression = "0 */10 * * * ?";//每隔十分钟执行一次
				// cronExpression = "0 59 23 * * ? *"; //每天凌晨23:59触发 " 0 59 23 * * ? *;
				cronExpression = SystemConstant.get("timeday");
			}
			else if (timetype.equals("2"))
			{
				cronExpression = SystemConstant.get("timeweek");
				// cronExpression = "0 59 23 ? * SAT"; //每天周六执行凌晨23:59触发 ";0 59 23 ? * SAT
			}
			else if (timetype.equals("3"))
			{
				cronExpression = SystemConstant.get("timemonth");
				// cronExpression = "0 59 23 01 * ?";//每月01号的23：59触发0 59 23 01 * ?
			}
		}
		return cronExpression;
	}
}
