package datacvg.parse.service;
/*
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Session;


import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.spider.entity.RuleTemplate;
import core.util.RegexValidate;
public class ParserTaskService {
	
	/*
	 * 构造函数
	 */
	public ParserTaskService(){
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
	/**
	 * 获取标签解析字段对应关系
	 * @param sql
	 * @param map
	 * @return
	 */
    public HashMap<Integer, String> getTagindex(String sql,Map<String,String> map){
    	List<?> list = null;
    	HashMap<Integer, String> indexmap = new  HashMap<Integer, String>();
		try {
			list =  dao.selectSqlAuto(sql, map);
			if (list != null) {
				for (int i=0; i<list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					Integer row = Integer.parseInt(String.valueOf(obj[0] == null ? "" : obj[0])); 
					String row1 = String.valueOf(obj[1] == null ? "" : obj[1]);
					indexmap.put(row, row1);
				}
			}
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
		return indexmap;
		 
	}
    /**
     * 获取标签序列
     * @param sql
     * @return 
     */
    public Integer getTagSequence(String sql){
    	List<?> list = null;
    	Integer tagsequence = 0;
		try {
			list =  dao.selectSqlAuto(sql);
			if (list != null) {
				for (int i=0; i<list.size(); i++) {
					tagsequence = Integer.parseInt(String.valueOf(list.get(i) == null ? "" :list.get(i))); 
				}
			}
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
		return tagsequence;
    }
    public List<Map<String,Object>> getListMap(String sql,Map<String,Object> map){
 		try {
 			return dao.queryMapList(sql, map);
 		} catch (Exception e) {
 		 
 			e.printStackTrace();
 		}
 		return null;
 		 
 	}
    /**
     * 解析前后缀规则集合
     * @param sql
     * @param map
     * @return
     */
    
    public List<RuleTemplate> getRuleTemplate(String sql,Map<String,String> map){
    	List<RuleTemplate>  ruletemplateList = new ArrayList<RuleTemplate>();
    	List<?> list = null;
		try {
			list =  dao.selectSqlAuto(sql, map);
			if (list != null) {
				RuleTemplate ruletemplate = null;
				for (int i=0; i<list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
				    ruletemplate = new RuleTemplate();
					ruletemplate.setFieldName(String.valueOf(obj[0] == null ? "" : obj[0]));
					ruletemplate.setPrefix(String.valueOf(obj[1] == null ? "" : obj[1]));
					ruletemplate.setSuffix(String.valueOf(obj[2] == null ? "" : obj[2]));
					ruletemplate.setExtractregex(String.valueOf(obj[3] == null ? "" : obj[3]));
					ruletemplate.setOutRegex(String.valueOf(obj[4] == null ? "" : obj[4]));
					ruletemplateList.add(ruletemplate);
				}
			}
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
    	return ruletemplateList;
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
    // 获取表字段信息  
 	public  String GetOptionsSelect(String sqlKey,Map<String,String> paramMap) 
 	{
 		    String  temp = "";
 			try {
 				List<?> list = dao.selectSqlAuto(sqlKey,paramMap);
 				StringBuffer sb = new StringBuffer();	
 				if (list != null && list.size()>0) {
 					for (int i=0; i<list.size(); i++) {
 						Object[] obj = (Object[]) list.get(i);
 						String row1 = String.valueOf(obj[0] == null ? "" : obj[0]);
 						String commdesc = String.valueOf(obj[1] == null ? "" : obj[1]);
 						temp += "<option value=\'" + row1 + "\'>" + commdesc + "</option>\n";
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
	  
	  public Integer OparserSql(String sql)throws Exception{
		     Integer executenum = dao.exeSqlQuery(sql);
			 return executenum;
	  }
	  public Integer delParserTask(String sql,Map<String, String> paramMap)throws Exception{
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
		  
	  }

	  public Integer ParserExeSql(String sql,Map<String, String> paramMap)throws Exception{
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
		  
	  }
	  public Integer ParserExeSql(String sql,Map<String, String> paramMap,Session session)throws Exception{
		     Integer executenum = dao.exeSql(session,sql, paramMap);
			 return executenum;
		  
	  }
	  
	  public Integer OparserSql(String sql,HashMap<String, String> taskfile,Session session)throws Exception{
		  Integer executenum =0;
//		  List checklist =null;
		  for(Entry<String, String> entry :taskfile.entrySet()){
			  Map<String, Object> paramMap = new HashMap<String, Object>();
			  paramMap.put("taskcode", entry.getValue());
			  paramMap.put("parsercode", entry.getKey());
			  String seachsql = "select c.taskcode,c.parsercode  from  gather_Parser_code c where c.taskcode=:taskcode and c.parsercode=:parsercode";
//			  checklist =   getFiledList(seachsql, taskfile);
			  List<Map<String,Object>> checklist = getListMap(seachsql, paramMap);
			  if(null == checklist || checklist.size()==0){
				  executenum = dao.exeSql(session,sql,paramMap);
			  }
		  }
		  return executenum;
		    
	  }
	  
	  public  void readSQLContent(String content) throws Exception{
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
			try {
					content = content.replaceAll("<br>", "\n");
					Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
			        Matcher m_html = p_html.matcher(content);  
			        String sqlStr  = m_html.replaceAll(""); // 过滤html标签  
					String[] sql = sqlStr.split(";");
					if (sql != null && sql.length > 0) {
						for (int j = 0; j < sql.length; j++) {
							try {
								if(RegexValidate.StrNotNull(sql[j])){
									dao.exeSqlQuery(sql[j]);//.executeUpdate(sql[j]);
								}
							} catch (Exception e) {
								// 打印日志
								e.printStackTrace();
								continue;
							}
						}
					}
			} catch (Exception e) {
			}finally{
				
				
			}
		}
}