package datacvg.gather.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.watij.webspec.dsl.WebSpec;

import core.BasePagingQueryAction;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.StringUtil;
import datacvg.gather.service.GatherTaskService;
import datacvg.gather.util.ExtractingGatherTags;
import datacvg.gather.util.GatherExe;
import datacvg.gather.util.SimulationEventCheck;
import datacvg.taskmanage.service.TaskManageService;

/**
 * 采集任务操作
 * 
 * @author admin
 * 
 */
public class GatherTaskAction extends BasePagingQueryAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger gather = Logger.getLogger(GatherTaskAction.class);

	GatherTaskService taskService;

	public GatherTaskService getTaskService()
	{
		return taskService;
	}

	public void setTaskService(GatherTaskService taskService)
	{
		this.taskService = taskService;
	}

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

	/*************************************** Strart 采集任务界面跳转配置 **********************/
	/**
	 * 采集任务配置
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String gathergroup() throws UnsupportedEncodingException
	{
		String tasktype = getStringParameter("tasktype");
		request.setAttribute("tasktype", tasktype);

		String taskcode = getStringParameter("taskcode");
		request.setAttribute("taskcode", taskcode);

		String taskname = request.getParameter("taskname");
		request.setAttribute("taskname", taskname);

		String taskurl = getStringParameter("taskurl");
		request.setAttribute("taskurl", taskurl.replace("$", "&"));

		String tasklogin = getStringParameter("tasklogin");
		request.setAttribute("tasklogin", tasklogin);
		return SUCCESS;
	}

	/**
	 * 采集页面跳转配置
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String gatherpage() throws UnsupportedEncodingException
	{
		String taskcode = getStringParameter("taskcode");
		request.setAttribute("taskcode", taskcode);

		String taskname = getStringParameter("taskname");
		request.setAttribute("taskname", taskname);

		String taskurl = getStringParameter("taskurl");
		request.setAttribute("taskurl", taskurl);

		// String loginflag = getStringParameter("loginflag");
		// request.setAttribute("loginflag", loginflag);

		String loginurl = getStringParameter("lgurl");
		request.setAttribute("loginurl", loginurl);

		String pageflag = getStringParameter("pageflag");

		String flag = getStringParameter("flag");
		request.setAttribute("flag", flag);

		String groupcode = getStringParameter("groupcode");
		request.setAttribute("groupcode", groupcode);

		String jumpage = null;
		switch (Integer.parseInt(pageflag))
		{
		case 0:
			// 页面配置
			jumpage = "pageconfig";
			break;
		case 1:
			// 组信息添加界面
			jumpage = "groupconfig";
			break;
		case 2:
			// 元搜索
			jumpage = "yuansearch";
			break;
		case 3:
			// 模拟采集
			jumpage = "eventconfig";
			break;
		case 4:
			// 分页配置
			jumpage = "pagingconfig";
			request.setAttribute("pageflag", "0");
			break;
		case 5:
			// 分页配置
			jumpage = "pagingconfig";
			request.setAttribute("pageflag", "2");
			break;
		default:
			break;
		}
		return jumpage;
	}

	// 任务操作(添加任务)
	public void optask()
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = taskService.getMySessionFactory().openSession();
			tran = session.beginTransaction();
			tran.begin();
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "insert into gather_parser_task values "
					+ "(:taskcode,:sitecode,:taskname,:taskurl,:encodeurl,:tasktype,:isspider,:isactive,"
					+ ":selectype,:totalpage,sysdate,'','false','','',:remark)";
			// 添加任务
			taskService.OpGathertaskAndSession(sql, paramMap, session);
			tran.commit();
		}
		catch (Exception e)
		{
			tran.rollback();
		}
		finally
		{
			if (session != null)
			{
				try
				{
					taskService.getMySessionFactory().closeSession(session);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除任务信息
	 */
	public void deltask()
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = taskService.getMySessionFactory().openSession();
			tran = session.beginTransaction();
			tran.begin();
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "delete from  gather_parser_task t where t.taskcode in (:taskcode)";
			// 添加任务
			taskService.OpGathertaskAndSession(sql, paramMap, session);
			tran.commit();
		}
		catch (Exception e)
		{
			tran.rollback();
		}
		finally
		{
			if (session != null)
			{
				try
				{
					taskService.getMySessionFactory().closeSession(session);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 修改采集任务信息
	 */
	public void modfiytask()
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = taskService.getMySessionFactory().openSession();
			tran = session.beginTransaction();
			tran.begin();
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "update  gather_parser_task set taskname=:taskname,taskurl=:taskurl,encodeurl=:encodeurl,tasktype=:tasktype,isactive=:isactive,isspider=:isspider,"
					+ "jsparse=:selectype,totalpage=:totalpage,updatetime=sysdate,remark=:remark " + "where taskcode=:taskcode";
			// 添加任务
			taskService.OpGathertaskAndSession(sql, paramMap, session);
			tran.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tran.rollback();
		}
		finally
		{
			if (session != null)
			{
				try
				{
					taskService.getMySessionFactory().closeSession(session);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	/*************************************** End 采集任务界面跳转配置 **********************/

	/**
	 * 任务添加、修改 参数信息
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, String> getTaskParam(HttpServletRequest request) throws UnsupportedEncodingException
	{

		Map<String, String> paramMap = new HashMap<String, String>();
		String id = MyURLDecoder.decode(request.getParameter("id"), "UTF-8");
		paramMap.put("id", id);
		// 站点ID
		String sitecode = MyURLDecoder.decode(request.getParameter("sitecode"), "UTF-8");
		paramMap.put("sitecode", sitecode);
		// 获取行业id
		String industryid = MyURLDecoder.decode(request.getParameter("indsutryid"), "UTF-8");
		paramMap.put("indsutryid", industryid);
		// 任务编号
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		paramMap.put("taskcode", RegexValidate.StrisNull(taskcode) == true ? StringUtil.randNumber(3) : taskcode);

		// 任务类型 0 列表采集 1模拟采集 2 元搜索采集  
		String tasktype = MyURLDecoder.decode(request.getParameter("tasktype"), "UTF-8");
		paramMap.put("tasktype", tasktype);
		
		// 网页编号
		String encode = MyURLDecoder.decode(request.getParameter("encodeurl"), "UTF-8");
		paramMap.put("encodeurl", encode);

		// 网页地址
		String taskurl = MyURLDecoder.decode(request.getParameter("taskurl"), "UTF-8");
		paramMap.put("taskurl", taskurl);

		// 任务名称
		String taskname = MyURLDecoder.decode(request.getParameter("taskname"), "UTF-8");
		paramMap.put("taskname", taskname);
		// 是否登录 T 需要登录采集 F 不需要登录采集
		String islogin = MyURLDecoder.decode(request.getParameter("islogin"), "UTF-8");
		paramMap.put("islogin", RegexValidate.StrisNull(islogin) == true ? "F" : islogin);

		// 是否采集
		String isspider = MyURLDecoder.decode(request.getParameter("isspider"), "UTF-8");
		paramMap.put("isspider", RegexValidate.StrisNull(isspider) == true ? "F" : isspider);
		// 是否采集
		String remark = MyURLDecoder.decode(request.getParameter("remark"), "UTF-8");
		paramMap.put("remark", RegexValidate.StrisNull(remark) == true ? "F" : remark);

		// 登录入口
		String loginurl = MyURLDecoder.decode(request.getParameter("loginurl"), "UTF-8");
		paramMap.put("loginurl", loginurl);

		// 组编号
		String groupcode = MyURLDecoder.decode(request.getParameter("groupcode"), "UTF-8");
		paramMap.put("groupcode", RegexValidate.StrisNull(groupcode) == true ? StringUtil.randNumber(3) : groupcode);

		String runsort = MyURLDecoder.decode(request.getParameter("runsort"), "UTF-8");
		paramMap.put("runsort", runsort);
		String groupdesc = MyURLDecoder.decode(request.getParameter("groupdesc"), "UTF-8");
		paramMap.put("groupdesc", groupdesc);

		// 是否激活 T 已激活 F 未激活
		String isactive = MyURLDecoder.decode(request.getParameter("isactive"), "UTF-8");
		paramMap.put("isactive", RegexValidate.StrisNull(isactive) == true ? "F" : isactive);

		// 解析方式 0 普通解析 1 JS解析
		String selectype = MyURLDecoder.decode(request.getParameter("selectype"), "UTF-8");
		paramMap.put("selectype", selectype);

		// 采集页面数
		String totalpage = MyURLDecoder.decode(request.getParameter("totalpage"), "UTF-8");
		paramMap.put("totalpage", totalpage);

		// 标签类型
		String tagtype = MyURLDecoder.decode(request.getParameter("tagtype"), "UTF-8");
		paramMap.put("tagtype", tagtype);
		// 父级编号
		String partagcode = MyURLDecoder.decode(request.getParameter("partagcode"), "UTF-8");
		paramMap.put("partagcode", partagcode);
		// 标签编号
		String tagcode = MyURLDecoder.decode(request.getParameter("tagcode"), "UTF-8");
		paramMap.put("tagcode", tagcode);

		// 标签名称
		String tagname = MyURLDecoder.decode(request.getParameter("tagname"), "UTF-8");
		paramMap.put("tagname", tagname);
		// 标签属性
		String tagattr = MyURLDecoder.decode(request.getParameter("tagattr"), "UTF-8");
		paramMap.put("tagattr", tagattr);
		// 标签属性值
		String tagval = MyURLDecoder.decode(request.getParameter("tagval"), "UTF-8");
		paramMap.put("tagval", tagval);

		// 事件类型
		String tagevent = MyURLDecoder.decode(request.getParameter("tagevent"), "UTF-8");
		paramMap.put("tagevent", tagevent);
		// 事件类型值
		String tageventval = MyURLDecoder.decode(request.getParameter("tageventval"), "UTF-8");
		paramMap.put("tageventval", tageventval);

		String keyword = MyURLDecoder.decode(request.getParameter("keyword"), "UTF-8");
		paramMap.put("keyword", keyword);

		// 是否钻取
		String isdrill = MyURLDecoder.decode(request.getParameter("isdrill"), "UTF-8");
		paramMap.put("isdrill", RegexValidate.StrisNull(isdrill) == true ? "F" : isdrill);

		// 目标属性值
		String tagattrval = MyURLDecoder.decode(request.getParameter("targetval"), "UTF-8");
		paramMap.put("targetval", tagattrval);

		return paramMap;

	}

	/**************************************** Start组事件操作 **************************************************/
	public void opgroup()
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = taskService.getMySessionFactory().openSession();
			tran = session.beginTransaction();
			tran.begin();
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			// System.out.println(paramMap);
			String groupsql = " insert into spider_taggroup values(group_seq.nextval,:taskcode,:groupcode,:groupdesc,null,:islogin,:loginurl,:isspider,:runsort)";
			// 添加任务
			taskService.OpGathertaskAndSession(groupsql, paramMap, session);
			tran.commit();
			write(paramMap.get("groupcode"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tran.rollback();
		}
		finally
		{
			if (session != null)
			{
				try
				{
					taskService.getMySessionFactory().closeSession(session);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void delgroup()
	{

		try
		{
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "delete from  spider_taggroup s where s.id in (:id)";
			// 删除模拟组信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	public void modfiygroup()
	{
		try
		{
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			// System.out.println(paramMap);
			String sql = "update spider_taggroup s set s.groupdesc=:groupdesc,s.islogin=:islogin,s.loginurl=:loginurl,s.isspider=:isspider,s.runsort=:runsort where s.id =:id";
			// 修改组信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	/**************************************** End 组事件操作 **************************************************/

	/****************************************
	 * Start 模拟标签配置操作
	 * 
	 * @throws UnsupportedEncodingException
	 **************************************************/
	public void addevent() throws UnsupportedEncodingException
	{
		// 获取事件配置添加的参数
		Map<String, String> paramMap = getTaskParam(request);
		String sql = "insert into spider_eventinfo values "
				+ "(group_seq.nextval,:taskcode,:groupcode,:tagcode,:partagcode,:tagtype,:tagname,:tagattr,:tagval,:tageventval,:tagevent,'')";
		try
		{
			taskService.OpGathertask(sql, paramMap);
			System.out.println(paramMap);
		}
		catch (Exception e)
		{
		}

	}

	public void modifyevent()
	{
		try
		{
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "update spider_eventinfo e " + "set " + "e.tagtype=:tagtype," + "e.tagname=:tagname," + "e.tagattr=:tagattr,"
					+ "e.tagattrval=:tagval," + "e.tagattr1val=:tagevent," + "e.tagattr1=:tageventval " + " where e.id =:id";
			// 修改组中的事件信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	public void delevent()
	{
		try
		{
			// 获取任务添加的参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "delete from  spider_eventinfo s where s.id in (:id)";
			// 添加任务
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	/**************************************** End 模拟标签配置操作 **************************************************/

	/****************************************
	 * Start 页面配置操作
	 * 
	 * @throws UnsupportedEncodingException
	 **************************************************/

	public void addpage() throws UnsupportedEncodingException
	{
		// 获取事件配置添加的参数
		Map<String, String> paramMap = getTaskParam(request);
		String sql = "insert into spider_itertaginfo values " + "(group_seq.nextval," + ":taskcode," + ":groupcode," + ":tagcode,"
				+ ":partagcode," + ":tagtype," + ":tagname," + ":tagattr," + ":tagval," + ":targetval," + ":isspider," + ":isdrill,"
				+ "''," + "''," + "''," + "sysdate)";
		try
		{
			taskService.OpGathertask(sql, paramMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void delpage()
	{
		try
		{
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			String sql = "delete from  spider_itertaginfo t where t.id in (:id)";
			// 删除页面配置信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	public void modfiypage()
	{
		try
		{
			// 获取参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			// TAGCODE,PARTAGCODE,TAGTYPE,TAGNAME,TAGATTR,TAGATTRVAL,TARGETATTR,ISGATHER,ISDRILL,UPDATETIME
			// :tagcode,:partagcode,:tagtype,:tagname,:tagattr,:tagval,:tagattrval,:isspider,:isdrill,'','',sysdate
			String sql = "update spider_itertaginfo s " + "set " + "s.tagcode=:tagcode," + "s.partagcode=:partagcode,"
					+ "s.tagtype=:tagtype," + "s.tagname=:tagname," + "s.tagattr=:tagattr," + "s.tagattrval=:tagval,"
					+ "s.targetattr=:targetval," + "s.isgather=:isspider," + "s.isdrill=:isdrill," + "s.updatetime=sysdate where s.id =:id";
			// 修改页面标签配置信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	/**************************************** End 页面配置操作 **************************************************/

	/****************************************
	 * Start 分页配置操作
	 * 
	 * @throws UnsupportedEncodingException
	 **************************************************/

	public void addpaging() throws UnsupportedEncodingException
	{
		// 获取事件配置添加的参数
		Map<String, String> paramMap = getTaskParam(request);
		String sql = "insert into spider_pageinginfo values " + "(group_seq.nextval," + ":taskcode," + ":groupcode," + ":tagcode,"
				+ ":partagcode," + ":tagtype," + ":tagname," + ":tagattr," + ":tagval," + ":targetval," + "sysdate," + "'')";
		try
		{
			taskService.OpGathertask(sql, paramMap);

		}
		catch (Exception e)
		{
		}
	}

	public void delpaging()
	{
		try
		{
			Map<String, String> paramMap = getTaskParam(request);
			String sql = "delete from  spider_pageinginfo t where t.id in (:id)";
			// 删除页面配置信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	public void modfiypaging()
	{
		try
		{
			// 获取参数
			Map<String, String> paramMap = getTaskParam(request);
			System.out.println(paramMap);
			// TAGCODE,PARTAGCODE,TAGTYPE,TAGNAME,TAGATTR,TAGATTRVAL,TARGETATTR,ISGATHER,ISDRILL,UPDATETIME
			// :tagcode,:partagcode,:tagtype,:tagname,:tagattr,:tagval,:tagattrval,:isspider,:isdrill,'','',sysdate
			String sql = "update spider_itertaginfo s " + "set " + "s.tagcode=:tagcode," + "s.partagcode=:partagcode,"
					+ "s.tagtype=:tagtype," + "s.tagname=:tagname," + "s.tagattr=:tagattr," + "s.tagattrval=:tagval,"
					+ "s.targetattr=:targetval," + "s.updatetime=sysdate where s.id =:id";
			// 修改页面标签配置信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	/**************************************** End 分页配置操作 **************************************************/

	/**************************************** Start 采集验证操作 **************************************************/
	/**
	 * 标签提取
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void extractingTags() throws UnsupportedEncodingException
	{
		@SuppressWarnings("unused")
		ExtractingGatherTags extractingtag = new ExtractingGatherTags();
		// 验证事件登录
		SimulationEventCheck eventcheck = new SimulationEventCheck();
		GatherExe gatherexecute = new GatherExe();
		WebSpec webspec = null;
		Map<String, String> paramMap = getTaskParam(request);
		gatherexecute.execute();

		// /String sql="select s.tagattr1val,s.tagattr,s.tagattrval,s.tagattr1 from spider_eventinfo s where s.gcode=:groupcode";
		// String sql="select s.tagname,s.tagattr,s.tagattrval,s.tagattr1,s.tagattr1val from spider_eventinfo s where s.gcode=:groupcode";

		try
		{
			// // 根据任务编号 获取组事件 模拟采集任务
			// List<EventEntity> eventInfo = taskmanageService.getEventInfo(sql, paramMap);
			// // 事件模拟
			// gatherexecute.setDiaplay(true);
			// webspec = gatherexecute.OpenURL(paramMap.get("taskurl").toString());
			// //webspec.hide();
			// eventcheck.exeTag(eventInfo, webspec);

			// extractingtag.extractingTags(url, txtkey, level);
			// List<Link> listurl = ParseDownload.IterationTags2(web.source(), listcontain2, task.getTaskurl(),task.getTaskcode());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**************************************** End 采集验证操作 **************************************************/

	/****************************************
	 * Start 元搜索采集操作
	 * 
	 * @throws UnsupportedEncodingException
	 **************************************************/
	public void savekeywords() throws UnsupportedEncodingException
	{

		// 获取参数
		Map<String, String> paramMap = getTaskParam(request);
		System.out.println("hahahahahahhahahaha  "+paramMap);
		String sql = "insert into  search_engine_keywords values(group_seq.nextval,:taskcode,:taskname,:keyword,sysdate,null,'',:isspider) ";
		try
		{
			// 查找关键词是否录入
			String keywordsql = "select * from  search_engine_keywords s where s.taskcode=:taskcode and s.keywords=:keyword";

			List<Map<String, Object>> infomap = taskService.getToSqlByInfo(keywordsql, paramMap);
			if (infomap != null && infomap.size() > 0)
			{
				write("waring");
			}
			else
			{
				// 元搜索关键词修改操作
				taskService.OpGathertask(sql, paramMap);
				write("success");
			}
		}
		catch (Exception e)
		{
			write("error");
		}

	}

	public void delkeywords()
	{
		try
		{
			Map<String, String> paramMap = getTaskParam(request);
			String sql = "delete from  search_engine_keywords t where t.id in (:id)";
			// 删除页面配置信息
			taskService.OpGathertask(sql, paramMap);
			write("success");
		}
		catch (Exception e)
		{
			write("error");
		}
	}

	public void updatekeywords() throws UnsupportedEncodingException
	{
		// 获取参数
		Map<String, String> paramMap = getTaskParam(request);
		System.out.println(paramMap);
		String sql = "update search_engine_keywords s " + "set " + "s.keywords=:keyword," + "s.isspider=:isspider,"
				+ "s.updatetime=sysdate" + " where s.id =:id";
		try
		{
			// 查找关键词是否录入
			String keywordsql = "select * from  search_engine_keywords s where s.taskcode=:taskcode and s.keywords=:keyword";

			List<Map<String, Object>> infomap = taskService.getToSqlByInfo(keywordsql, paramMap);
			if (infomap != null && infomap.size() > 0)
			{
				write("waring");
			}
			else
			{
				// 元搜索关键词修改操作
				taskService.OpGathertask(sql, paramMap);
				write("success");
			}
		}
		catch (Exception e)
		{
			write("error");
		}

	}

	/**************************************** End 元搜索采集操作 **************************************************/

}