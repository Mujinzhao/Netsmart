package datacvg.taskmanage.action;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.spider.entity.StaticVar;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.StringUtil;
import datacvg.gather.embeddedDB.DBInstance;
import datacvg.gather.entity.GatherBean;
import datacvg.gather.entity.SIMParam;
import datacvg.gather.service.GatherTaskService;
import datacvg.gather.util.BaseThread;
import datacvg.gather.util.GatherExe;
import datacvg.log.LogView;
import datacvg.parse.action.ExtractAction;
import datacvg.taskmanage.service.TaskManageService;

public class TaskManageAction extends BasePagingQueryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GatherTaskService taskService;

	public GatherTaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(GatherTaskService taskService) {
		this.taskService = taskService;
	}
	 /**
     * Service注入
     */
    private TaskManageService taskmanageService;
        public TaskManageService getTaskmanageService() {
		return taskmanageService;
	}
	public void setTaskmanageService(TaskManageService taskmanageService) {
		this.taskmanageService = taskmanageService;
	}
	
	/**************************************** Start 任务管理操作 **************************************************/

	/**
	 * 任务管理界面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String taskmanage() throws UnsupportedEncodingException {
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		request.setAttribute("taskcode", taskcode);

		String taskname = getStringParameter("taskname");
//		System.out.println(taskname);
		request.setAttribute("taskname", taskname);

		String spidertype = MyURLDecoder.decode(request.getParameter("spidertype"), "UTF-8");

		String tasktypehtml = "";
		if (spidertype.equals("0")) {
			tasktypehtml = "列表采集";
		}
		if (spidertype.equals("1")) {
			tasktypehtml = "模拟采集";
		}
		if (spidertype.equals("2")) {
			tasktypehtml = "元搜索采集";
		}
		if (spidertype.equals("3")) {
			tasktypehtml = "当前源采集";
		}
		request.setAttribute("spidertype", tasktypehtml);

		//任务状态
		String isactive = MyURLDecoder.decode(request.getParameter("isactive"), "UTF-8");
		request.setAttribute("isactive", isactive);
		
		String timetype = MyURLDecoder.decode(request.getParameter("timetype"), "UTF-8");
		request.setAttribute("timetype", timetype);
		
		String taskenabled = MyURLDecoder.decode(request.getParameter("taskenabled"), "UTF-8");
		request.setAttribute("taskenabled", taskenabled);

		String eventnum = MyURLDecoder.decode(request.getParameter("eventnum"), "UTF-8");
		request.setAttribute("eventnum", eventnum);

		String pagenum = MyURLDecoder.decode(request.getParameter("pagenum"), "UTF-8");
		request.setAttribute("pagenum", pagenum);

		String pagingnum = MyURLDecoder.decode(request.getParameter("pagingnum"), "UTF-8");
		request.setAttribute("pagingnum", pagingnum);

		String parsesnum = MyURLDecoder.decode(request.getParameter("parsesnum"), "UTF-8");
		request.setAttribute("parsesnum", parsesnum);

		return SUCCESS;
	}

	/**
	 * 启动采集任务
	 */
	public void runspiderTasks() {
		GatherExe gatherexecute = new GatherExe();
		// 根据任务ID 取出任务信息
		String sql = " select t.taskcode,t.taskname,t.taskurl,t.encodeurl,t.tasktype,t.isactive,t.isspider,t.jsparse,t.totalpage,t.timetype,t.taskenabled"
				+ " from gather_parser_task t where t.taskcode=:taskcode and t.isactive='T'";
		try {
			// 任务编号
			String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("taskcode",RegexValidate.StrisNull(taskcode) == true ? StringUtil
							.randNumber(3) : taskcode);
			//加载嵌入式数据库
			DBInstance.init(SystemConstant.get("myDBPath"));
			// 获取当前任务下的采集模板信息
			List<GatherBean> gatherbeanList = taskmanageService.getGatherInfo(sql,
					paramMap);
			HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate
					.get(taskcode);
			if (threadstate != null && threadstate.size() > 0) {
				// 重新启动解析线程 启动线程没有被终止并且 暂停状态位为true
				// if(!BaseThread.isDead && BaseThread.isStop){
				if (!threadstate.get("isDead") && threadstate.get("isStop")) {
					BaseThread baseThread = new BaseThread();
					baseThread.reStart(taskcode);
					threadstate.put("isDead", false);
					threadstate.put("isStop", BaseThread.isStop);
					StaticVar.taskandthreadstate.put(taskcode, threadstate);
				} else {
					for (GatherBean gatherbean : gatherbeanList) {
						gatherexecute.setGatherbean(gatherbean);
						threadstate.put("isStop", false);
						threadstate.put("isDead", false);
						StaticVar.taskandthreadstate.put(taskcode, threadstate);
						gatherexecute.execute();
					}
				}
			} else {
				for (GatherBean gatherbean : gatherbeanList) {

					HashMap<String, Boolean> threads = new HashMap<String, Boolean>();
					gatherexecute.setGatherbean(gatherbean);
					gatherexecute.setGatherbean(gatherbean);
					threads.put("isStop", false);
					threads.put("isDead", false);
					StaticVar.taskandthreadstate.put(taskcode, threads);
					gatherexecute.execute();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 启动解析任务
	 * @throws UnsupportedEncodingException 
	 */
	public void runparserTasks() throws UnsupportedEncodingException{
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		ExtractAction extract = new ExtractAction();
		extract.initService();
		extract.parserBytaskcode(taskcode,null);
	
	}
	/**
	 * 暂停任务线程
	 * @throws UnsupportedEncodingException 
	 */
	public void spiderTasksHalt() throws UnsupportedEncodingException {
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		HashMap<String, Boolean> threadstate = new HashMap<String, Boolean>();
		BaseThread baseThread = new BaseThread();
		baseThread.halt(taskcode);
		threadstate.put("isDead", false);
		threadstate.put("isStop", BaseThread.isStop);
		StaticVar.taskandthreadstate.put(taskcode, threadstate);

	}

	/**
	 * 终止任务线程
	 * @throws UnsupportedEncodingException 
	 */
	public void spiderTasksKill() throws UnsupportedEncodingException {
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		HashMap<String, Boolean> threadstate = new HashMap<String, Boolean>();
		BaseThread baseThread = new BaseThread();
		baseThread.kill(taskcode);
		threadstate.put("isStop", BaseThread.isStop);
		threadstate.put("isDead", BaseThread.isDead);
		StaticVar.taskandthreadstate.put(taskcode, threadstate);
	}

	/**
	 * jsp 实时展现采集、解析任务日志信息
	 */
	public void realtimeShowLog() {
		try {
			LogView view = new LogView();
			final File tmpLogFile = new File(".\\logs\\"+ "/website.log");
			view.realtimeShowLog(tmpLogFile);
			if (RegexValidate.StrNotNull(view.tmp)) {
				write(view.tmp);
				view.saveProperty(view.lastTimeFileSize+"","");
				view.tmp = "";
			}
		} catch (Exception e) {
			write("获取任务日志异常！");
		}

	}
	
	
	 /**
     * 功能描述：保存执行任务时间
     * 
     * 输入参数：
     * 
     * 输出参数：
     * 
     * 创建时间：2015-03-13 17:12
     */
    public void savetimingState(){
         try{   
        	 Map<String, String>  paramMap = new HashMap<String, String>();
        	 String taskcode= MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
        	 paramMap.put("taskcode", taskcode);
        	 String runtype= MyURLDecoder.decode(request.getParameter("runtype"), "UTF-8");
        	 paramMap.put("timestate", runtype);
        	 String mothed= MyURLDecoder.decode(request.getParameter("opmothed"), "UTF-8");
        	 String sql = "update  gather_parser_task t set t.timetype=:timestate,t.taskenabled=:enabled where t.taskcode=:taskcode";
        	 String opmothed = "";
        	 if(mothed.equals("startspider")){
        		 opmothed = "run";
        		 paramMap.put("enabled", "run");//启动
        	 }else if(mothed.equals("haltspider")){
        		 paramMap.put("enabled", "stop");//暂停
        		 opmothed = "stop";
	    	 }else if(mothed.equals("killspider")){
	    		 paramMap.put("enabled", "kill");//终止
	    		 opmothed = "kill";
	    	 }
        	 taskService.OpGathertask(sql, paramMap);
        	 // 动态启动、暂停任务触发器
        	 TimingSendTimer sendTimer = new TimingSendTimer();
        	 sendTimer.enableTrigger(taskcode, opmothed, runtype);
        	 
         }catch(Exception e){   
             e.printStackTrace();   
         }   
    }
	/**************************************** End 任务管理操作 ***************************************************/

}
