package datacvg.excel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.core.tag.util.SystemConstant;


import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.util.WDWUtil;

public class TemplateService {
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
	   * 
	   * @param sql
	   * @param paramMap
	   * @return
	   */
	  public Integer addCustomeFiled(String sql,Map<String, String> paraMap) throws Exception{
		  Session session = mySessionFactory.openSession();
		  Integer executenum = 0;
		  Transaction tran = null;
		  String[] filedanddescarray = paraMap.get("filedarray").split("[∴]");
		  String[] asstablearray = paraMap.get("asstabledesc").split("[|]");
			try {
				tran = session.beginTransaction();
				tran.begin();
				int i=1;
				for (String filedstr : filedanddescarray) {
					// 字段名称及字段描述
					String[] filedstrArray = filedstr.split("[|]");
					paraMap.put("id", WDWUtil.getSeqNextval());// 存储字段ID
					paraMap.put("asstable", asstablearray[0]);// 表名
					paraMap.put("asstablename", asstablearray[1]);// 表名描述
					paraMap.put("acqfield", filedstrArray[0]);// 字段
					paraMap.put("acqfieldesc", filedstrArray[1]);// 字段描述
					paraMap.put("filedindex", i+"");// 字段下标
//					System.out.println(paraMap);
				    executenum = dao.exeSql(session,sql, paraMap);
				    executenum++;
				    i++;
				}
				tran.commit();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tran.rollback();
			}finally{
				if(session != null)
				{
					mySessionFactory.closeSession(session);
				}
			}
			 return executenum;
	  }
	  
	  public Integer OparserSqlBySession(String sql,Map<String, String> paraMap) throws Exception{
		  Session session = mySessionFactory.openSession();
		  Integer executenum = 0;
		  Transaction tran = null;
			try {
				tran = session.beginTransaction();
				tran.begin();
				    executenum = dao.exeSql(session,sql, paraMap);
				tran.commit();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tran.rollback();
			}finally{
				if(session != null)
				{
					mySessionFactory.closeSession(session);
				}
			}
			 return executenum;
	  }
	  public Integer OparserSql(String sql,Map<String, String> paramMap) throws Exception{
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
	  }
	  public Integer OparserSql(String sql,Map<String, String> paramMap,Session session) throws Exception{
		     Integer executenum = dao.exeSql(session,sql, paramMap);
			 return executenum;
	  }
	  public Integer OparserSql(String sql) throws Exception{
		     Integer executenum = dao.exeSqlQuery(sql);
			 return executenum;
	  }
	  
	  // 组装option 集合控件 操作
	  public String SelectOptionControll(List<String> lists){
		  StringBuffer sbuff = new StringBuffer();
		  sbuff.append("--请选择-- \n");
		  for(String str:lists){
			  sbuff.append("<option value=\""+str+"\">"+str+"</option> \n");
		  }
		  return sbuff.toString();
	  }
	    // 获取表字段信息  
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
	  public List<Map<String,Object>> getListMap(String sql,Map<String,String> map){
			try {
				return dao.queryMapList(sql, map);
			} catch (Exception e) {
			 
				e.printStackTrace();
			}
			return null;
			 
		}
	  /**
		 * 获取标签解析字段对应关系
		 * @param sql
		 * @param map
		 * @return
		 */
	    public HashMap<Object, String> getTagindex(String sql,Map<String,String> map){
	    	List<?> list = null;
	    	HashMap<Object, String> indexmap = new  HashMap<Object, String>();
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
	    

	  public Integer Optanchongthong(String sql,Map<String, String> paraMap){
		  Session session = mySessionFactory.openSession();
		  Integer executenum = 0;
		  Transaction tran = null;
		  List<Map<String,Object>> paralist = getListMap(sql, paraMap);
		  String strunctsql = SystemConstant.get("getExcelStructure");
		  strunctsql = strunctsql.replaceAll("#", "");
		  List<Map<String,Object>> excelstrunct = getListMap(strunctsql, paraMap);
			try {
				tran = session.beginTransaction();
				tran.begin();
				String templateid =(String)paraMap.get("templateId"); 
				String strunctureid =  WDWUtil.getSeqNextval();
				if(paralist != null && paralist.size()>0){
					String savesql =SystemConstant.get("SaveTemplatePasers");
					for(Map<String,Object> map:paralist){
						map.put("id", WDWUtil.getSeqNextval());// 存储字段ID
						map.put("templateId",templateid);// 模板ID
						map.put("structuredid",strunctureid);// 结构ID
						map.put("sheetname",(String)map.get("sheetname"));// 结构ID
						// 非空字段
						map.put("emptyacqfield", (String)map.get("emptyacqfield"));
						// 字段值
						map.put("acqfieldval", (String)map.get("acqfieldvalue"));
						//字段下标
						map.put("filedindex", (String)map.get("filedindex"));
						executenum = dao.exeSql(session,savesql, map);
						executenum++;
					}
				}
				
				if(excelstrunct != null && excelstrunct.size()>0){
					String savesql =SystemConstant.get("saveExcelParser");
					for(Map<String,Object> map:excelstrunct){
						map.put("id", WDWUtil.getSeqNextval());// 存储字段ID
						map.put("templateId",templateid);// 模板ID
						map.put("structuredid",strunctureid);// 结构ID
						executenum = dao.exeSql(session,savesql, map);
						executenum++;
					}
				}
				tran.commit();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tran.rollback();
			}finally{
				if(session != null)
				{
					try {
						mySessionFactory.closeSession(session);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); 
					}
				}
			}
			 return executenum;
	  
	  }
}
