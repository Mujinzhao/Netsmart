package datacvg.website.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;


import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
public class WebsiteService {
	
	private static final Logger logger = Logger.getLogger(WebsiteService.class);
	public WebsiteService(){
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
    public List<Map<String,Object>> getListMap(String sql,Map<String,Object> map){
		try {
			return dao.queryMapList(sql, map);
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
		return null;
		 
	}
    
    public List getFiledList(String sql,Map<String,String> map){
    	List<?> list = null;
    	List filedlist = new  ArrayList();
		try {
			list =  dao.selectSqlAuto(sql, map);
			
			if (list != null) {
				for (int i=0; i<list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					String row1 = String.valueOf(obj[0] == null ? "" : obj[0]);
					filedlist.add(row1);
				}
			}
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
		return filedlist;
		 
	}
    // 获取表字段信息  生成前台的jsp代码
 	public  String GetOptionsSelect(String sqlKey,Map<String,Object> paramMap) 
 	{
 		    String  temp = "";
 			try {
 				List<?> list = dao.selectSqlAuto(sqlKey,paramMap);
 				StringBuffer sb = new StringBuffer();	
 				if (list != null) {
 					for (int i=0; i<list.size(); i++) {
 						Object[] obj = (Object[]) list.get(i);
 						String row1 = String.valueOf(obj[0] == null ? "" : obj[0]);
 						String commdesc = String.valueOf(obj[1] == null ? "" : obj[1]);
 						if(paramMap != null && paramMap.size()>0 && row1.equals(paramMap.get("asstable"))){
 							temp += "<option selected='true' value=\'" + row1 + "\'>" + commdesc + "</option>\n";
 						}else{
 						    temp += "<option value=\'" + row1 + "\'>" + commdesc + "</option>\n";
 						}
 					}
 					sb.append(temp);
 					return sb.toString();
 				}
 			} catch (Exception e) {
 				// TODO: handle exception
 				e.printStackTrace();
 			}
 		return "";
 	} 
	  /**
	   * 
	   * @param sql
	   * @param paramMap
	   * @return
	   */
	  public Integer insertParserTask(String sql,Map<String, String> paramMap,Session session) throws Exception{
		     Integer executenum = dao.exeSql(session,sql, paramMap);
			 return executenum;
	  }

	  public Integer OpparsertaskAndSession(String sql,Map<String, String> paramMap,Session session)throws Exception{
		     Integer executenum = dao.exeSql(session,sql, paramMap);
			 return executenum;
	  }
	  
	  public Integer modifyEmptyFiled(String sql)throws Exception{
		     Integer executenum = dao.exeSqlQuery(sql);
			 return executenum;
	  }
	  public Integer modifyEmptyFiled(String sql,Map<String, String> paramMap)throws Exception{
		     Integer executenum = dao.exeSql(sql,paramMap);
			 return executenum;
	  }
	  
	  public Integer delParserTask(String sql,Map<String, String> paramMap)throws Exception{
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
		  
	  }


 	
}
