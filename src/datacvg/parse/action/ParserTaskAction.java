package datacvg.parse.action;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.watij.webspec.dsl.WebSpec;

import com.core.tag.util.SystemConstant;

import core.BaseAction;
import core.spider.entity.RuleTemplate;
import core.spider.entity.StaticVar;
import core.spider.fun.HtmlReader;
import core.spider.pagedetail.RuleExtract;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.WDWUtil;
import datacvg.gather.util.ParseDownload;
import datacvg.parse.service.ParserTaskService;
import datacvg.parse.service.ParseroperateTask;

/**
 * 解析任务操作类
 * @author admin
 *
 */
public class ParserTaskAction extends BaseAction {

	private static final Logger parser = Logger.getLogger(ParserTaskAction.class);
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String, Object> parsemap = new HashMap<String, Object>();
	ParserTaskService parsertaskService;
	public ParserTaskService getParsertaskService() {
		return parsertaskService;
	}
	public void setParsertaskService(ParserTaskService parsertaskService) {
		this.parsertaskService = parsertaskService;
	}
//	public ParamService getParamService() {
//		return paramService;
//	}
//	public void setParamService(ParamService paramService) {
//		this.paramService = paramService;
//	}
//	ParamService paramService;
	
    ParseroperateTask paseroptask;
	
	public ParseroperateTask getPaseroptask() {
		return paseroptask;
	}
	public void setPaseroptask(ParseroperateTask paseroptask) {
		this.paseroptask = paseroptask;
	}
	private File parsefile;
	
	public File getParsefile() {
		return parsefile;
	}
	public void setParsefile(File parsefile) {
		this.parsefile = parsefile;
	}
	public String parserconfig() throws UnsupportedEncodingException{
		request.setAttribute("taskcode", getStringParameter("taskcode"));
		request.setAttribute("asstable", getStringParameter("asstable"));
		request.setAttribute("parseurl", getStringParameter("parseurl"));
		request.setAttribute("tablename", getStringParameter("tablenamedesc"));
		request.setAttribute("structuredid", getStringParameter("structuredid"));
		request.setAttribute("parsertype", getStringParameter("parsertype"));
		String encode = MyURLDecoder.decode(request.getParameter("encode"), "UTF-8");
		request.setAttribute("encode", encode);
		request.setAttribute("owner", getStringParameter("owner"));
		return "parsergroup";
	}
	/**
	 * 解析任务界面跳转
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String parserpage() throws UnsupportedEncodingException{
		String jumpage = null;
		//网页数据解析方式  0 表示前后缀  1 表示标签  2 表示综合 3 表示元搜索
		String tagflag = getStringParameter("parsertype");
		// 跳转页面
		String tagpage = getStringParameter("tagpage");
		//RegexValidate.StrNotNull(getStringParameter("parsertype"))==true?"0":getStringParameter("parsertype");
//		System.out.println(tagflag);
		String parseurl =getStringParameter("parseurl");
		parseurl = parseurl.replace("$", "&");
		request.setAttribute("taskcode", getStringParameter("taskcode"));
		request.setAttribute("asstable", getStringParameter("asstable"));
		request.setAttribute("parseurl",parseurl);
		request.setAttribute("tablename", getStringParameter("tablenamedesc"));
		request.setAttribute("structuredid", getStringParameter("structuredid"));
//		System.out.println("owner  " + getStringParameter("owner"));
		request.setAttribute("owner", getStringParameter("owner"));
	
		
//		request.setAttribute("tagpage",tagpage);
		
		String encode = MyURLDecoder.decode(request.getParameter("encode"), "UTF-8");
		request.setAttribute("encode", encode);
		
		// 获取字段select 控件
		String fieldselect = parsefiledSelect(getParamMap(request));
		int tag  = Integer.parseInt(tagflag);
		switch (tag) {
			case 0:
				// 前后缀界面
				jumpage="beforesuffix";
				request.setAttribute("tagpage", tagpage);
				request.setAttribute("parsertype", "0");
				break;
	        case 1:
	        	//标签解析界面 出现下一步标识
	        	if(tagpage!= null && tagpage.equals("2")){
	        		//表示综合解析
	        		request.setAttribute("tagpage",'2');
	        	}else{
	        		//标签解析
	        		request.setAttribute("tagpage",'1');
	        	}
	        	request.setAttribute("parsertype", "1");
	        	jumpage="tagparse";
				break;
	        case 2:
	        	//综合解析界面  出现下一步标识
	        	request.setAttribute("tagpage",'2');
	        	request.setAttribute("parsertype", "0");
	        	jumpage="beforesuffix";
				break;
	        case 3:
	        	//综合解析界面  出现下一步标识
	        	if(tagpage!= null && tagpage.equals("2")){
	        		//表示综合解析
	        		request.setAttribute("tagpage",'2');
	        	}else{
	        		//标签解析
	        		request.setAttribute("tagpage",'1');
	        	}
	        	request.setAttribute("parsertype", "2");
	        	jumpage="tagrule";
				break;
	        case 4:
	        	//元搜索解析界面  出现下一步标识
	        	if(tagpage!= null && tagpage.equals("2")){
	        		//表示综合解析
	        		request.setAttribute("tagpage",'2');
	        	}else{
	        		//标签解析
	        		request.setAttribute("tagpage",'4');
	        	}
	        	request.setAttribute("parsertype", "4");
	        	jumpage="beforesuffix";
				break;
	        case 5:
	        	//元搜索解析界面 
	        	request.setAttribute("parsertype", "4");
	        	jumpage="matetag";
				break;
		default:
			jumpage="beforesuffix";
			break;
		}
		
		request.setAttribute("fieldselect", fieldselect);
		return jumpage;
	}
	
	public void checkparser() throws UnsupportedEncodingException{
		paseroptask.distory();
		String taskcode  = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		ExtractAction extract = new ExtractAction();
		extract.setParsertaskService(parsertaskService);
		extract.setPaseroptask(paseroptask);
		
		extract.initService();
		File file = new File(SystemConstant.get("gather_root_dir")+taskcode);
		extract.parserBytaskcode(taskcode,file.getAbsolutePath());
	}
    // 操作参数  MAP 集合
	public Map<String, String> getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String, String>  paramMap = new HashMap<String, String>();
		

		String id = MyURLDecoder.decode(request.getParameter("id"), "UTF-8");
		paramMap.put("id", RegexValidate.StrNotNull(id)==true?id:WDWUtil.getSeqNextval());
		
		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		paramMap.put("taskcode", taskcode);
		// 多结构编号 多结构表示 1 表示多结构 空值表示非多结构
		String morestruts = MyURLDecoder.decode(request.getParameter("morestruts"), "UTF-8");
		paramMap.put("morestruts", morestruts);
		
		//结构ID
		String structuredid = getStringParameter("structuredid");
		paramMap.put("structuredid", structuredid);
		
		//表字段数组
		String filedarray = MyURLDecoder.decode(request.getParameter("filedarray"), "UTF-8");//RegexValidate.StrNotNull(morestruts)==null?WDWUtil.getSeqNextval():morestruts;
		paramMap.put("filedarray", filedarray);
		
		// 表名称描述
		String asstabledesc = MyURLDecoder.decode(request.getParameter("tablearray"), "UTF-8");
		paramMap.put("asstabledesc", asstabledesc);
		
	    String asstable =  getStringParameter("asstable");
		paramMap.put("asstable", asstable);
		
	    String owner =  getStringParameter("owner");
		paramMap.put("owner", owner);
		
		/***********规则前后缀参数 tag***********/
		
		String parsertype = getStringParameter("parsertype");
		paramMap.put("parsertype", parsertype);
		
	    String parsefiled =  getStringParameter("parsefiled");
		paramMap.put("parsefiled", parsefiled);
		//规则前缀
	    String prefixrule =   getStringParameter("prefixrule");
	    paramMap.put("prefixrule", prefixrule);
	    //规则前缀
	    String suffixrule =   getStringParameter("suffixrule");
	    paramMap.put("suffixrule", suffixrule);
	    //正则表达式
	    String extractregex =request.getParameter("extractregex");   //getStringParameter("extractregex");
	    paramMap.put("extractregex", extractregex);
	    System.out.println("last1111"+extractregex);
	    //排除内容
	    String outRegex = RegexValidate.StrNotNull(getStringParameter("outRegex"))==true?getStringParameter("outRegex"):"<.*?>";
	    paramMap.put("outregex", outRegex);
	    
	    
	    /***********规则前后缀参数***********/
	    
	    /***********规则标签参数***********/
	    String tagindex =   getStringParameter("tagindex");
	    paramMap.put("tagindex", tagindex);
	    String customvar = getStringParameter("acqfieldval");
	    paramMap.put("customvar", customvar);
	  
	    /***********规则标签参数***********/
	    return paramMap;
	}

	/**
	 * 绑定表字段
	 */
	public String parsefiledSelect(Map<String, String> paramMap) {

		String sqlKey = SystemConstant.get("getTableFiledByStructuredid");
		// 获取数据库中解析存储数据表信息
		String filedTree = parsertaskService.GetOptionsSelect(sqlKey, paramMap);
		if(RegexValidate.StrNotNull(filedTree)){
			filedTree = filedTree.substring(0, filedTree.length() - 1);
		}
		return filedTree;
	}
	
    /************************************ Start 规则前后缀解析配置操作 
     * @throws UnsupportedEncodingException *************************************/
	 public void savesuffix() throws UnsupportedEncodingException{
		 Map<String, String> paramMap = getParamMap(request);
		 Set <String> keys=paramMap.keySet();
		 for(String key:keys){
			 System.out.println(key+" "+paramMap.get(key));
		 }
		 String sql ="insert into spider_parse_rule values(:id,:taskcode,:structuredid,:asstable,:parsefiled,:prefixrule,:suffixrule,:outregex,:extractregex,:tagindex,:parsertype,'',sysdate,null)";
		 Transaction tran = null;
		 Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
			    tran = session.beginTransaction();
			    tran.begin();
		        parsertaskService.OpparsertaskAndSession(sql, paramMap, session);
		        tran.commit();
		        write("操作成功!");
		 }catch (Exception e) {
			 tran.rollback();
			 write("操作失败!");
		 }finally{
			 try{
				 if(session != null){
						parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
			}
			
		 }
		 
	 }
	 /**
	  * 删除前后缀配置规则信息
	 * @throws UnsupportedEncodingException 
	  */
	 public void delsuffix() throws UnsupportedEncodingException{

		 Map<String, String> paramMap = getParamMap(request);
		 String sql ="delete from  spider_parse_rule s where  s.id in (:id)";//(:id,:taskcode,:structuredid,:datatable,:acqfield,:prefixrule,:suffixrule,:extractregex,:outregex,'',:tagindex,'',sysdate,'')";
		 Transaction tran = null;
		 Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
			    tran = session.beginTransaction();
			    tran.begin();
		        parsertaskService.OpparsertaskAndSession(sql, paramMap, session);
		        tran.commit();
		        write("操作成功!");
		 }catch (Exception e) {
			 tran.rollback();
			 write("操作失败!");
		 }finally{
			 try{
				 if(session != null){
						parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
			}
			
		 }
	 }
	 // 修改前后缀规则信息
	 public void modifysuffix() throws UnsupportedEncodingException{
		 Map<String, String> paramMap = getParamMap(request);
		 String sql ="update spider_parse_rule s set s.ruleprefix=:prefixrule,s.rulesuffix=:suffixrule,s.extractregex=:extractregex,s.excludregex=:outregex  where  s.id in (:id)";//(:id,:taskcode,:structuredid,:datatable,:acqfield,:prefixrule,:suffixrule,:extractregex,:outregex,'',:tagindex,'',sysdate,'')";
		 Transaction tran = null;
		 Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
			    tran = session.beginTransaction();
			    tran.begin();
		        parsertaskService.OpparsertaskAndSession(sql, paramMap, session);
		        tran.commit();
		        write("操作成功!");
		 }catch (Exception e) {
			 tran.rollback();
			 write("操作失败!");
		 }finally{
			 try{
				 if(session != null){
						parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
			}
		 }
	 }
	 // 保存上传的文件信息
	 public void uploadparsfile() throws UnsupportedEncodingException{
		String htmlSource  = null;
		String encode = MyURLDecoder.decode(request.getParameter("encode"), "UTF-8");
		if(null != parsefile){
			StaticVar.htmlsource.clear();
			htmlSource = HtmlReader.readHtml(parsefile,encode);
			StaticVar.htmlsource.put("htmlSource", htmlSource);
			write("文件上传成功");
		}
		else{
			write("文件上传失败");
		}
	 }
	 // 前后缀验证
     public void validsuffix() throws UnsupportedEncodingException{
 		RuleTemplate rtmeplate = new RuleTemplate();
// 		Pattern patterntest = Pattern.compile(" |\\s"); 
 		String checktype = getStringParameter("checktype");
		//获取验证url
 		String testUrl = MyURLDecoder.decode(request.getParameter("testUrl"), "UTF-8");
        String encode = MyURLDecoder.decode(request.getParameter("encode"), "UTF-8");
// 		//获取下载方式
 		String htmlSource  = null;
 		String mes = "";
 		
 		// extractregex
 		Object obj=StaticVar.htmlsource.get("htmlSource");
 		try{
 			    //本地文件解析验证
 				if(checktype.equals("0")){
 					 // 获取本地html 数据源解析
 					if(obj!=null &&!"".equals(obj)){
 						htmlSource =(String)obj;
 					}else{
 						mes = "<label style='color:red;'>尚未上传需解析的 html文件信息</label>";
 						write(mes);
 						return;
 					}
 				}
 				//网址解析验证
 				else{
 					htmlSource = ParseDownload.RequestHTML(testUrl, encode);
// 					WebSpec gatherWeb = new WebSpec().ie();
// 					if (gatherWeb.isVisible())
// 					{
// 						gatherWeb.hide();
// 					}
// 					// 打开地址
// 					gatherWeb.open(testUrl);
// 					while (!gatherWeb.ready())
// 					{
// 						Thread.sleep(3000);
// 					}
// 					htmlSource = gatherWeb.source();
 				}
 				if(RegexValidate.StrNotNull(htmlSource)){
 		 			RuleExtract er = new RuleExtract();
 		 			//保存前后缀信息
 		 	 		parservailed(request,rtmeplate);
 		 			String con = er.getFieldCon(rtmeplate, htmlSource); 
 		 			if(!RegexValidate.StrNotNull(con))
 		 			{
 		 				mes =rtmeplate.getFieldName()+"字段解析失败";
 		 			}
 		 			else
 		 			{
// 		 				mes = ""+rtmeplate.getFieldName()+"字段解析成功:<h1 style=\"color:green;\"> <span style=\"background-color:yello;\">"+con+"</span></h1>";
// 		 				mes = rtmeplate.getFieldName()+"字段解析成功:<label style='color:green;'>"+con+"</label>";
 		 				mes = ""+rtmeplate.getFieldName()+"字段解析成功:"+con;
 		 			}
 				}else{
 					mes = "解析文件源码信息获取失败";
 				}
//	 			
	 		}catch (Exception e) {
	 			e.printStackTrace();
	 			mes = "<label style='color:red;'>"+rtmeplate.getFieldName()+"</lable>" +
	 					"字段解析失败!异常信息:" +
	 					"<label style='color:red;'>"+e.getMessage()+"</label>";
	 		}
 		System.out.println(mes.trim());
	 		write(mes.trim());
     
	 }
     // 保存页面传入的值，转换成RuleTemplate对象
     public RuleTemplate parservailed(HttpServletRequest request,RuleTemplate rtmeplate) throws UnsupportedEncodingException {
     	String outRegex   = MyURLDecoder.decode(request.getParameter("outRegex"), "UTF-8"); // 排除内容
     	if(!RegexValidate.StrNotNull(outRegex)){
     		outRegex= "<.*?>";  //默认排除内容
     	}
     	rtmeplate.setOutRegex(outRegex);
     	
 		String prefix     = MyURLDecoder.decode(request.getParameter("prefixrule"), "UTF-8");// 前缀规则
 		rtmeplate.setPrefix(prefix);
 		
 		String suffix     = MyURLDecoder.decode(request.getParameter("suffixrule"), "UTF-8");// 后缀规则
 		rtmeplate.setSuffix(suffix);
 		System.out.println(request.getParameter("extractregex"));
 		String extractregex=request.getParameter("extractregex");//MyURLDecoder.decode(request.getParameter("extractregex"), "UTF-8");  //正则表达式
 		rtmeplate.setExtractregex(extractregex);
 		//System.out.println("1111111111111111111"+extractregex);
 		
 		
 		String fieldName  = MyURLDecoder.decode(request.getParameter("parsefiled"), "UTF-8"); // 字段名称
     	rtmeplate.setFieldName(fieldName);
 		return rtmeplate;
 	}
	
     
     /************************************ End   规则前后缀解析配置操作 *************************************/
     
     
     /************************************ Start 标签解析配置操作 ******************************************/

     /**
 	 * 保存标签解析信息
 	 * @return
 	 * @throws Exception
 	 */
 	public void tagparserinfo()throws Exception
 	{
 		Map<String, String> paramMap = getParamMap(request);
 		String customop = MyURLDecoder.decode(request.getParameter("customop"), "UTF-8");
		String sql = "";
		if(RegexValidate.StrNotNull(customop) && customop.equals("0")){
			//自定义下标
			sql ="insert into spider_parse_rule (id,taskcode,structuredcode,datatable,acqfield,fieldindex,tagruleid,remarks,creattime,updatetime)" +
	 		 		"values(:id,:taskcode,:structuredid,:asstable,:parsefiled,'0',:parsertype,:customvar,sysdate,null)";
		}else{
			//自定义字段值
			sql ="insert into spider_parse_rule (id,taskcode,structuredcode,datatable,acqfield,fieldindex,tagruleid,remarks,creattime,updatetime)" +
	 		 		"values(:id,:taskcode,:structuredid,:asstable,:parsefiled,:customvar,:parsertype,'',sysdate,null)";
		}
		 Transaction tran = null;
		 Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
			    tran = session.beginTransaction();
			    tran.begin();
		        parsertaskService.OpparsertaskAndSession(sql, paramMap, session);
		        tran.commit();
		 }catch (Exception e) {
			 tran.rollback();
		 }finally{
			 try{
				 if(session != null){
				    parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
			}
			
		 }
 	}
 	/**
 	 * 删除标签解析信息
 	 * @return
 	 * @throws Exception
 	 */
 	public void delparserinfo()throws Exception
 	{
 		 Map<String, String> paramMap = getParamMap(request);
 		 String sql ="delete from  spider_parse_rule s where  s.id in ("+paramMap.get("id").toString()+")";//(:id,:taskcode,:structuredid,:datatable,:acqfield,:prefixrule,:suffixrule,:extractregex,:outregex,'',:tagindex,'',sysdate,'')";
 		 try{
 			   parsertaskService.OparserSql(sql);
 		 }catch (Exception e) {
 		 }
 	 }
 	/**
 	 *  修改 、标签迭代信息
 	 * @return
 	 * @throws Exception
 	 */
 	public void updateparserinfo()throws Exception
 	{
 		 Map<String, String> paramMap = getParamMap(request);
		
 		String customop = MyURLDecoder.decode(request.getParameter("customop"), "UTF-8");
		String sql = "";
		if(RegexValidate.StrNotNull(customop) && customop.equals("0")){
			//自定义下标
			sql ="update spider_parse_rule s set s.fieldindex=:customvar,s.acqfield=:parsefiled where id=:id";
		}else{
			//自定义字段值
			sql ="update spider_parse_rule s set s.remarks=:customvar,s.acqfield=:parsefiled where id=:id";
		}
 		 Transaction tran = null;
		 Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
			    tran = session.beginTransaction();
			    tran.begin();
		        parsertaskService.OpparsertaskAndSession(sql, paramMap, session);
		        tran.commit();
		 }catch (Exception e) {
			 tran.rollback();
		 }finally{
			 try{
				 if(session != null){
				    parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
			}
			
		 }
 	}
 	/************************************ End 标签解析配置操作 ********************************************/

}
