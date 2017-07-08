package haier.dataspider.param.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.WDWUtil;


public class WebSitService {
	private static final Logger logger = Logger.getLogger(WebSitService.class);
	public WebSitService(){
	}
	public BaseDao dao = null;
	private MySessionFactory mySessionFactory;
	public MySessionFactory getMySessionFactory() {
		return mySessionFactory;
	}
	public void setMySessionFactory(MySessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}
	public BaseDao getDao() {
		return dao;
	}
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	// 自动补全参数集合
	Map paramrowMap = new HashMap();
	public Map getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
		// task参数
		String websiteid = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("websiteid"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("websiteid"), "UTF-8"):WDWUtil.getSeqNextval();
		
		String websitname = MyURLDecoder.decode(request.getParameter("websitname"), "UTF-8");
		String urlencoding = MyURLDecoder.decode(request.getParameter("urlencoding"), "UTF-8");
		String websiteinlet = MyURLDecoder.decode(request.getParameter("websiteinlet"), "UTF-8");
		String searchkeyword = MyURLDecoder.decode(request.getParameter("searchkeyword"), "UTF-8");
		String posturl = MyURLDecoder.decode(request.getParameter("posturl"), "UTF-8");
		String posturl1 = MyURLDecoder.decode(request.getParameter("posturl1"), "UTF-8");
		
		// checkbox
		String islogin = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("islogin"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("islogin"), "UTF-8"):"N";
		String isactive = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("isactive"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("isactive"), "UTF-8"):"F";
		
		// 登录
		String loginwebsite = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("loginwebsite"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("loginwebsite"), "UTF-8"):"";
		String loginother = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("loginother"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("loginother"), "UTF-8"):"";
		String defaulttag = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("default"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("default"), "UTF-8"):"";
		String default1 = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("default1"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("default1"), "UTF-8"):"";
		String username = MyURLDecoder.decode(request.getParameter("username"), "UTF-8");
		String password = MyURLDecoder.decode(request.getParameter("password"), "UTF-8");
		String postcertificate = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("postcertificate"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("postcertificate"), "UTF-8"):"";
		String viewstate = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("viewstate"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("viewstate"), "UTF-8"):"";
	   
		//正则
		String nextpageregex = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("nextpageregex"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("nextpageregex"), "UTF-8"):"";
//		nextpageregex =nextpageregex.replace("\'", "\"");
		String contentregex = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("contentregex"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("contentregex"), "UTF-8"):"";
//		contentregex =contentregex.replace("\'", "\"");
		String outregex = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("outregex"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("outregex"), "UTF-8"):"";
//		outregex =outregex.replace("\'", "\"");
		String regex_fileurl = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("regex_fileurl"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("regex_fileurl"), "UTF-8"):"";
//		regex_fileurl =regex_fileurl.replace("\'", "\"");
		//参数
		String mothdate = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("mothdate"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("mothdate"), "UTF-8"):"";
		String maxpagenum =  MyURLDecoder.decode(request.getParameter("maxpagenum"), "UTF-8");
		String otherparam = MyURLDecoder.decode(request.getParameter("paramgroup"), "UTF-8");
		String startimeparam   = MyURLDecoder.decode(request.getParameter("paramgroup1"), "UTF-8");
		String endtimeparam   = MyURLDecoder.decode(request.getParameter("paramgroup2"), "UTF-8");
		
		// 标签
		String tabletag   = MyURLDecoder.decode(request.getParameter("tableTag"), "UTF-8");
		String rowtag   = MyURLDecoder.decode(request.getParameter("rowTag"), "UTF-8");
		String celltag   = MyURLDecoder.decode(request.getParameter("cellTag"), "UTF-8");
		
		paramrowMap.clear();
		paramrowMap.put("website_id", websiteid);
		paramrowMap.put("websitname", websitname);
		paramrowMap.put("urlencoding", urlencoding);
		paramrowMap.put("websiteinlet", websiteinlet);
		paramrowMap.put("searchkeyword", searchkeyword);
		paramrowMap.put("posturl",posturl);
		paramrowMap.put("posturl1", posturl1);
		
		paramrowMap.put("islogin", islogin);
		paramrowMap.put("isactive", isactive);
		paramrowMap.put("loginwebsite", loginwebsite);
		paramrowMap.put("loginother", loginother);
		paramrowMap.put("defaulttag",defaulttag);
		paramrowMap.put("default1", default1);
		paramrowMap.put("username",username);
		paramrowMap.put("password",password);
		paramrowMap.put("postcertificate", postcertificate);
		paramrowMap.put("viewstate", viewstate);
		
		paramrowMap.put("nextpageregex", nextpageregex);
		paramrowMap.put("contentregex", contentregex);
		paramrowMap.put("outregex", outregex);
		paramrowMap.put("regex_fileurl", regex_fileurl);
		
		paramrowMap.put("mothdate", mothdate);
		paramrowMap.put("maxpagenum", maxpagenum);
		paramrowMap.put("otherparam", otherparam);
		paramrowMap.put("startimeparam", startimeparam);
		paramrowMap.put("endtimeparam", endtimeparam);
		
		paramrowMap.put("tabletag", tabletag);
		paramrowMap.put("rowtag", rowtag);
		paramrowMap.put("celltag", celltag);
		
		return paramrowMap;
	}
	
	/**
	 * 添加解析模板
	 * @param paramMap
	 * @return
	 */
	public Integer insertWebSite(Map<String, String> paramMap){
		Integer insertsql=0;
		String sql="insert into PARSER_WEBSIT_INFO(" +
				                "WEBSITE_ID," +
				                "DOMAINAME," +
				                "LOGINWEBSITE," +
				                "SPIDERURLMAIN," +
				                "POSTURL," +
				                "POSTURL1," +
				                "URLENCODING," +
				                "USERNAMETAG," +
				                "PASSWORDTAG," +
				                "EVENTVALIDATIONTAG," +
				                "VIEWSTATEQ," +
				                "LOGINOTHERTAG," +
				                "LOGINODEFULATPAM," +
				                "LOGINODEFULATPAM1," +
				                "MOTHVALUE,"+
				                "STARTIMETAG," +
				                "ENDTIMETAG," +
				                "PARAMOTHERTAG," +
				                "SEARCHWORDS," +
				                "MAXPAGEREGEX," +
				                "NEXTPAGERULE," +
				                "OUTREGEX," +
				                "URLREGEX," +
				                "MAXPAGENUM," +
				                "TABLETAG," +
				                "ROWTAG," +
				                "CELLTAG," +
				                "CREATIME," +
				                "UPDATETIME," +
				                "TASKSTATE," +
				                "LOGINFLAG)" +
				"values(" +
								":website_id," +
								":websitname," +
								":loginwebsite," +
								":websiteinlet," +
								":posturl," +
								":posturl1," +
								":urlencoding," +
								":username," +
								":password," +
								":postcertificate," +
								":viewstate," +
								":loginother," +
								":defaulttag," +
								":default1," +
								":mothdate," +
								":startimeparam," +
								":endtimeparam," +
								":otherparam," +
								":searchkeyword," +
								":contentregex," +
								":nextpageregex," +
								":outregex," +
								":regex_fileurl," +
								":maxpagenum," +
								":tabletag," +
								":rowtag," +
								":celltag," +
								"sysdate," +
								"sysdate," +
								":isactive," +
								":islogin)";
		try {
			insertsql=dao.exeSql(sql, paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			insertsql = -1;
		}
	    return insertsql;
	}
	
	/**
	 * 修改解析模板
	 * @param paramMap
	 * @return
	 */
	public Integer update(Map<String, String> paramMap){
		Integer editflag=0;
		try {
			String sql="update PARSER_WEBSIT_INFO set " +
		
								"DOMAINAME=:websitname," +
								"LOGINWEBSITE=:loginwebsite," +
								"SPIDERURLMAIN=:websiteinlet," +
								"POSTURL=:posturl," +
								"POSTURL1=:posturl1," +
								"URLENCODING=:urlencoding," +
								"USERNAMETAG=:username," +
								"PASSWORDTAG=:password," +
								"EVENTVALIDATIONTAG=:postcertificate," +
								"VIEWSTATEQ=:viewstate," +
								"LOGINOTHERTAG=:loginother," +
								"LOGINODEFULATPAM=:defaulttag," +
								"LOGINODEFULATPAM1=:default1," +
								"MOTHVALUE=:mothdate,"+
								"STARTIMETAG=:startimeparam," +
								"ENDTIMETAG=:endtimeparam," +
								"PARAMOTHERTAG=:otherparam," +
								"SEARCHWORDS=:searchkeyword," +
								"MAXPAGEREGEX=:contentregex," +
								"NEXTPAGERULE=:nextpageregex," +
								"OUTREGEX=:outregex," +
								"URLREGEX=:regex_fileurl," +
								"MAXPAGENUM=:maxpagenum," +
								"TABLETAG=:tabletag," +
								"ROWTAG=:rowtag," +
								"CELLTAG=:celltag," +
								"CREATIME=sysdate," +
								"UPDATETIME=sysdate," +
								"TASKSTATE=:isactive," +
								"LOGINFLAG=:islogin" +
					    " where WEBSITE_ID=:website_id";
			try {
				editflag=dao.exeSql(sql, paramMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				editflag = -1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return editflag;
	}
	
	// 删除模板信息
	public Integer del(Map<String, String> paramMap){
		Integer delflag=0;
		String sql="delete from parser_websit_info p where p.WEBSITE_ID=:website_id";
		try {
			delflag = dao.exeSql(sql, paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			delflag =-1;
		}
		return delflag;
	}
	
}
