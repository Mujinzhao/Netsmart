package datacvg.website.action;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.StringUtil;
import core.util.WDWUtil;
import datacvg.website.service.WebsiteService;

public class WebsiteAction extends BasePagingQueryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WebsiteService websiteService;

	public WebsiteService getWebsiteService() {
		return websiteService;
	}

	public void setWebsiteService(WebsiteService websiteService) {
		this.websiteService = websiteService;
	}

	/**
	 * 站点任务类别
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String websiteList() throws UnsupportedEncodingException {
		String websiteid = MyURLDecoder.decode(request.getParameter("websiteid"), "UTF-8");
		request.setAttribute("websiteid", websiteid);
		return SUCCESS;
	}

	public String taskgroupPage() throws UnsupportedEncodingException {
		Map<String, Object>  paramMap = new HashMap<String, Object>();
		String taskid = getStringParameter("taskid");
		request.setAttribute("taskid", taskid);
		request.setAttribute("taskname", getStringParameter("taskname"));
		// 获取当前数据库中的所有用户
	    String usersql = SystemConstant.get("getDbausers");
	    String userselect = websiteService.GetOptionsSelect(usersql, null);
	    request.setAttribute("userselect", userselect);
	    
		
//		// 获取数据库中解析存储数据表信息ILF_
//		String tableTree = paramService.GetOptionsTree("", "FDC_");
//		request.setAttribute("tableTree", tableTree);
		
		// 获取数据库中任务对应的文件编号
		String sqlKey = SystemConstant.get("getParserFilecode");
		paramMap.put("taskcode", taskid);
		String filecodeTree = websiteService.GetOptionsSelect(sqlKey, paramMap);
		if(RegexValidate.StrNotNull(filecodeTree)){
			filecodeTree = filecodeTree.substring(0, filecodeTree.length() - 1);
		}
		request.setAttribute("filecodeTree", filecodeTree);
		
		
		return SUCCESS;
	}

	/********************* 解析任务操作 ******************************/
	/**
	 * 非空字段页面
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String emptyfiled() throws UnsupportedEncodingException {
		request.setAttribute("structuredid", getStringParameter("structuredid"));
		request.setAttribute("asstable", getStringParameter("asstable"));
		request.setAttribute("owner", getStringParameter("owner"));
		return SUCCESS;
	}

	/**
	 * 添加解析任务字段信息
	 * @throws UnsupportedEncodingException 
	 */
	public void saveParsertask() throws UnsupportedEncodingException {
		// 获取数据库中解析存储数据表信息ILF_
		// String sqlkey=SystemConstant.get("inserPaserCusField");

		String sqlkey = "insert into  spider_parse_cusfield " + " values( :id, :taskcode, :indcode, :structuredid, "
				  + ":morestruts,:asstable,:asstablename, :acqfield,  :acqfieldesc, '',:parsertype,sysdate,'',:rowtocell,:taskname,:parsercode,:owner) ";
		Map<String, String> map = getParamMap(request); 

		String[] filedanddescarray = map.get("filedarray").split("[∴]");
		String[] asstablearray = map.get("asstabledesc").split("[|]");
		Transaction tran = null;
		Session session = websiteService.getMySessionFactory().openSession();
		try {
			tran = session.beginTransaction();
			tran.begin();
			// 多结构ID
			String morestruts = map.get("morestruts").toString();
			String structuredid = morestruts.equals("1")?WDWUtil.getSeqNextval():map.get("taskcode").toString();
			map.put("structuredid", structuredid);// 多结构ID
			for (String filedstr : filedanddescarray) {
				// 字段名称及字段描述
				String[] filedstrArray = filedstr.split("[|]");
				map.put("id", WDWUtil.getSeqNextval());// 存储字段ID
				
				map.put("indcode", "5");// 行业编号
				map.put("asstable", asstablearray[0]);// 表名
				map.put("asstablename", asstablearray[1]);// 表名描述
				map.put("acqfield", filedstrArray[0]);// 字段
				map.put("acqfieldesc", filedstrArray[1]);// 字段描述
//				System.out.println(map);
				websiteService.insertParserTask(sqlkey, map, session);
			}
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			tran.rollback();
		} finally {
			if (session != null) {
				try {
					websiteService.getMySessionFactory().closeSession(session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 修改字段非空 状态
	 * @throws UnsupportedEncodingException 
	 */
	public void emptyfiledState() throws UnsupportedEncodingException {
		String sqlkey = MyURLDecoder.decode(request.getParameter("sqlkey"), "UTF-8");
		// 获取修改标识 0标识空值 1标识非空
		String emptyflag = getStringParameter("emptyflag");
		// 获取修改条件的字段ID
		String filedid = getStringParameter("filedidarray");
		
		// 将字段ID xxxx,xxxx 替换成'xxxx','xxxx'
		filedid = filedid.replace(",", "','");
		System.out.println(filedid);
		String sql = SystemConstant.get(sqlkey);
		sql += " set c.emptyacqfield='"+emptyflag+"' where c.id in ('"+filedid+"')";
		try {
			websiteService.modifyEmptyFiled(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 删除结构信息 根据结构编号
	 * @throws UnsupportedEncodingException 
	 */
	public void delParsertask() throws UnsupportedEncodingException {
		// 删除的结构编号
//		String structuredid = getStringParameter("structuredid");
//	
//		String sql = "delete from  spider_parse_cusfield c  where c.structuredid=:structuredid";
//	
//		
//		try {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("structuredid", structuredid);// 存储字段ID
//			websiteService.delParserTask(sql, map);
		String taskcode = getStringParameter("taskid");
		String sql="delete from spider_parse_cusfield c where c.taskcode=:taskcode";
		String sql1="delete from spider_parse_rule c where c.taskcode=:taskcode";
		String sql2="delete from spider_parse_tagrule c where c.taskcode=:taskcode";
		
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskcode", taskcode);// 存储字段ID
			websiteService.delParserTask(sql, map);
			websiteService.delParserTask(sql1, map);
			websiteService.delParserTask(sql2, map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 修改结构信息
	 * @throws UnsupportedEncodingException 
	 */
	public void modifyParsertask() throws UnsupportedEncodingException {

		// 获取数据库中解析存储数据表信息ILF_
		String insql=SystemConstant.get("inserPaserCusField");
        String delsql=SystemConstant.get("delPaserCusField");
        String tagdelsql = SystemConstant.get("delTagRuleField");
        String selectsql =  SystemConstant.get("getTableFiledByStructuredid");
		
    	Map<String, String> map =getParamMap(request);
    	
	
		String[] filedanddescarray = map.get("filedarray").split("[∴]");
		String[] asstablearray = map.get("asstabledesc").split("[|]");
		
		// 多结构标识
		String morestruts = map.get("morestruts");
		String structuredid = MyURLDecoder.decode(request.getParameter("structuredid"), "UTF-8");
		// 当修改选择的是多结构，取当前传入的多结构编号，反之取任务编号昨晚多结构编号
		String mstructuredid = morestruts.equals("1")?structuredid:map.get("taskcode").toString();
		Transaction tran = null;
		List<String> addlist = null;
		List<String> dellist = null;
		String delfiled = null;
		Session session = websiteService.getMySessionFactory().openSession();
		try {
			tran = session.beginTransaction();
			tran.begin();
			map.put("asstable", asstablearray[0]);// 表名
			map.put("structuredid",mstructuredid);// 表名
			
				// 删除移除部分字段信息
			    delsql    = "delete from  spider_parse_cusfield c where c.asstable=:asstable and c.structuredid=:structuredid ";
			    tagdelsql = "delete from  spider_parse_rule r     where r.datatable=:asstable and r.structuredcode=:structuredid ";
    			// 前台传入
//			    String modfiyarray = map.get("modfiyarray").replaceAll("∴", ",");
    			List arraylist = Arrays.asList(map.get("modfiyarray").split("∴"));
    			// 获取数据库部分
    			List filedlist =websiteService.getFiledList(selectsql, map);
    			if(filedlist !=null && filedlist.size()>0){
    				// 比较出需要添加部分数据
        			addlist= StringUtil.list1ComparisonList2(arraylist,filedlist);
        			// 比较出需要删除部分数据
        			dellist= StringUtil.list1ComparisonList2(filedlist,arraylist);
        			//格式化 去掉最后特殊字符
        		    delfiled = Arrays.toString(dellist.toArray());
        			delfiled =delfiled.substring(1, delfiled.length()-1);
    			}
    			
    			// 移除字段信息
    			if(dellist !=null && dellist.size()>0 && RegexValidate.StrNotNull(delfiled)){
    				String filedparam= delfiled.trim().replace(", ", "','");
    				
        			delsql += 	" and c.acqfield in ('"+filedparam+"')";
        			tagdelsql+= " and r.acqfield in ('"+filedparam+"')";
            		//System.out.println(delsql);
        			websiteService.OpparsertaskAndSession(delsql, map,session);
        			//删除规则信息
        			websiteService.OpparsertaskAndSession(tagdelsql, map,session);
    			}
				if(addlist !=null && addlist.size()>0 &&RegexValidate.StrNotNull(map.get("filedarray"))){
					// 将字段名称、字段描述存入Map集合中
					Map<String, String> filedanddescMap = new HashMap<String, String>();
					for (String filedstr : filedanddescarray) {
						String[] filedstrArray = filedstr.split("[|]");
						filedanddescMap.put(filedstrArray[0],filedstrArray[1]);
					}
					// 添加字段
					for (String filedstr : addlist) {
						map.put("id", WDWUtil.getSeqNextval());// 存储字段ID
						
						map.put("indcode", "5");// 行业编号
						map.put("asstable", asstablearray[0]);// 表名
						map.put("asstablename", asstablearray[1]);// 表名描述
						map.put("acqfield", filedstr);// 字段
						map.put("acqfieldesc", filedanddescMap.get(filedstr).toString());// 字段描述
						websiteService.insertParserTask(insql, map, session);
					}
				}else{
					// 修改结构状态
					String upsql="update spider_parse_cusfield c " +
							"set c.morestructured=:morestruts,c.celltorow=:rowtocell," +
							"c.parsertype=:parsertype,c.structuredesc=:taskname,c.parserfilecode=:parsercode," +
							"c.updatetime=sysdate,c.owner=:owner " +
							"where c.asstable=:asstable and c.structuredid=:structuredid ";
//					System.out.println(upsql);
					websiteService.insertParserTask(upsql, map, session);
					
					//当解析类型发生改变，修改规则中的解析类型
				    String rulesql = "update  spider_parse_rule r set r.tagruleid=:parsertype"+
				    " where r.datatable=:asstable and r.structuredcode=:structuredid ";
					websiteService.insertParserTask(rulesql, map, session);
					
				}
				tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		} finally {
			if (session != null) {
				try {
					websiteService.getMySessionFactory().closeSession(session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}

	/**
	 * 绑定表字段
	 * @throws UnsupportedEncodingException 
	 */
	public void parsefiledSelect() throws UnsupportedEncodingException {

		String dbname = getStringParameter("asstable");
		String sqlKey = SystemConstant.get(getStringParameter("sqlkey"));
		String structuredid = getStringParameter("structuredid");
		String owner = getStringParameter("owner");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("asstable", dbname);
		paramMap.put("structuredid", structuredid);
		paramMap.put("owner", owner);
		// 获取数据库中解析存储数据表信息
		String filedTree = websiteService.GetOptionsSelect(sqlKey, paramMap);
		filedTree = filedTree.substring(0, filedTree.length() - 1);
		write(filedTree);
	}
	
	/**
	 * 绑定用户表信息
	 * @throws UnsupportedEncodingException 
	 */
	public void parseTableSelect() throws UnsupportedEncodingException {

		String dbname = getStringParameter("asstable");
		String sqlKey = SystemConstant.get(getStringParameter("sqlkey"));
		String structuredid = getStringParameter("structuredid");
		String owner = getStringParameter("owner");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("asstable", dbname);
		paramMap.put("structuredid", structuredid);
		paramMap.put("owner", owner);
		// 获取数据库中解析存储数据表信息
		String filedTree = websiteService.GetOptionsSelect(sqlKey, paramMap);
		filedTree = filedTree.substring(0, filedTree.length() - 1);
		write("<option value=''>--请选择--</option>"+filedTree);
	}
	
	
	/**
	 * 绑定任务对应解析文件编号
	 * @throws UnsupportedEncodingException 
	 */
	public void parsefilecodeSelect() throws UnsupportedEncodingException {
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		String sqlKey = SystemConstant.get(getStringParameter("sqlkey"));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskcode", taskcode);
		// 获取数据库中任务对应的文件编号
		String filecodeTree = websiteService.GetOptionsSelect(sqlKey, paramMap);
		filecodeTree = filecodeTree.substring(0, filecodeTree.length() - 1);
		write(filecodeTree);
	}
	
	// 操作参数  MAP 集合
	public Map<String, String> getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String, String>  paramMap = new HashMap<String, String>();

		String id = MyURLDecoder.decode(request.getParameter("id"), "UTF-8");
		System.out.println(RegexValidate.StrNotNull(id));
		paramMap.put("id", RegexValidate.StrNotNull(id)==true?id:WDWUtil.getSeqNextval());
		
		String taskcode = MyURLDecoder.decode(request.getParameter("taskid"), "UTF-8");
		paramMap.put("taskcode", taskcode);
		
		// 任务名称
		String taskname = MyURLDecoder.decode(request.getParameter("taskname"), "UTF-8");
		paramMap.put("taskname", taskname);
		
		// 多结构编号 多结构表示 1 表示多结构 空值表示非多结构
		String morestruts =  RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("morestruts"), "UTF-8"))==true?MyURLDecoder.decode(request.getParameter("morestruts"), "UTF-8"):"0";
		paramMap.put("morestruts",morestruts);
		
		// 多结构任务名称
		String structdesc = MyURLDecoder.decode(request.getParameter("taskname"), "UTF-8");
		paramMap.put("structdesc",structdesc);
		
		String parsercode = MyURLDecoder.decode(request.getParameter("parsercode"), "UTF-8");
		paramMap.put("parsercode",parsercode);
		
		// 获取解析类型 0 表示前后缀解析 1表示标签解析  2 表示综合解析
		String parsertype = MyURLDecoder.decode(request.getParameter("parsertype"), "UTF-8");
		paramMap.put("parsertype",parsertype);
		
		//表字段数组
		String filedarray = MyURLDecoder.decode(request.getParameter("filedarray"), "UTF-8");//RegexValidate.StrNotNull(morestruts)==null?WDWUtil.getSeqNextval():morestruts;
		paramMap.put("filedarray", filedarray);
		
		// 修改表字段数组
		String modfiyarray = MyURLDecoder.decode(request.getParameter("modfiyarray"), "UTF-8");
		paramMap.put("modfiyarray", modfiyarray);
		
		// 表名称描述
		String asstabledesc = MyURLDecoder.decode(request.getParameter("tablearray"), "UTF-8");
		paramMap.put("asstabledesc", asstabledesc);
		
		//数据存储对应用户
		String dbuser = MyURLDecoder.decode(request.getParameter("owner"), "UTF-8");
		paramMap.put("owner", dbuser);
		
		//结构编号ID
//		System.out.println(RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("structuredid")));
		//String structuredid = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("structuredid"))==true?MyURLDecoder.decode(request.getParameter("structuredid"):taskcode;
//		String structuredid = morestruts=="1"?WDWUtil.getSeqNextval():taskcode;
//		paramMap.put("structuredid",structuredid);//WDWUtil.getSeqNextval():taskcode);RegexValidate.StrNotNull(morestruts)==true?WDWUtil.getSeqNextval():taskcode
		
		//获取行业id
		String industryid=MyURLDecoder.decode(request.getParameter("indsutryid"), "UTF-8");
		paramMap.put("indsutryid", industryid);
		
		// 是否列转行解析标识
		String rowtocell = MyURLDecoder.decode(request.getParameter("rowtocell"), "UTF-8");
		rowtocell = RegexValidate.StrNotNull(rowtocell)==true?"true":"false";
		paramMap.put("rowtocell", rowtocell);
		return paramMap;
		
	}
}
