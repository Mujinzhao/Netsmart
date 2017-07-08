package datacvg.dbconfg.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import core.util.RegexValidate;

/**
 * 
 *  DBUtil，数据库访问工具类
 * 
 * @preserve all
 */
public class DBUtil {
	    private static final Logger excel = Logger.getLogger(DBUtil.class);
        private static Connection con = null;
        private final static String ORACLE_DRIVER="oracle.jdbc.driver.OracleDriver";
        private final static String MYSQL_DRIVER="com.mysql.jdbc.Driver";
        private final static String MSSQL_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        private final static String SAPHANA_DRIVER="com.sap.db.jdbc.Driver";
        
        static SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        public static String getDbDriverStr(String dbtype){
        	
        	if("oracle".equalsIgnoreCase(dbtype)){
        		
        		return ORACLE_DRIVER;
        		
        	}else if("mysql".equalsIgnoreCase(dbtype)){
        		
        		return MYSQL_DRIVER;
        		
        	}else if("mssql".equalsIgnoreCase(dbtype)){
        		
        		return MSSQL_DRIVER;
        	}else if("hana".equalsIgnoreCase(dbtype)){
        		
        		return SAPHANA_DRIVER;
        	}
        	
        	return ORACLE_DRIVER;
        }
        
        public static Connection openConnection() throws SQLException, ClassNotFoundException, IOException {
                if (null == con || con.isClosed()) {
                        Properties p = new Properties();
                        p.load(DBUtil.class.getResourceAsStream("/config/dbconf.properties"));
                        Class.forName(p.getProperty("oracle.driverClass"));
                        con = DriverManager.getConnection(p.getProperty("oracle.url"), p.getProperty("oracle.user"),
                                        p.getProperty("oracle.password"));
                }
                return con;
        }
        
        public static Connection openConnection(String dbtype,String dburl,String user,String pass) throws SQLException, ClassNotFoundException, IOException{
        
        	  if (null == con || con.isClosed()) {
        		  
        		  Class.forName(getDbDriverStr(dbtype));
        		  con = DriverManager.getConnection(dburl, user,pass);
        	  }
        	  
        	return con;
        }
        
        public static Connection openConnection(String dburl,String user,String pass) throws SQLException, ClassNotFoundException, IOException{
            
      	  if (null == con || con.isClosed()) {
      		  
      		  Class.forName(getDbDriverStr("oracle"));
      		  con = DriverManager.getConnection(dburl, user,pass);
      	  }
      	  
      	  return con;
      }
        

        public static void closeConnection(Connection con) throws SQLException {
                try {
                        if (null != con)
                                con.close();
                } finally {
                        con = null;
                        System.gc();
                }
        }
        public static void closeConnection() throws SQLException {
            try {
                    if (null != con)
                            con.close();
            } finally {
                    con = null;
                    System.gc();
            }
    }
    	public List execSQL(String sqlTxt) {
    		Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
    		List list = null;
    		try {
    			list = new ArrayList();
    			conn = openConnection();
            	pstmt = conn.prepareStatement(sqlTxt);
                rs = pstmt.executeQuery();
                while(rs.next()){
                	list.add(rs);
                }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} catch (ClassNotFoundException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
    			try {
                    if (null != rs) {
                        rs.close();
                    }
                    if (null != pstmt) {
                        pstmt.close();
                    }
                    if(null !=conn){
                    	closeConnection(conn);
                    }
                } catch (SQLException e) {
                	e.printStackTrace();
                }
    			
    		}
    		return list;
    	}
    	
    	public Integer execUPdate(String sql) throws Exception {
    		Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
    		Integer m = 0;
    		try {
    			conn = openConnection();
    			excel.info("打开数据库连接！");
            	pstmt = conn.prepareStatement(sql);
            	excel.info("执行SQL预编译！");
            	m = pstmt.executeUpdate();
    		} catch (RuntimeException e) {
    			e.printStackTrace();
    			excel.error("操作失败！"+e.getMessage());
    			throw e;
    		} catch (SQLException e) {
				e.printStackTrace();
				excel.error("执行SQL异常！"+e.getMessage());
				throw e;
			} catch (ClassNotFoundException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
				excel.error("数据库连接失败！"+e.getMessage());
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
    		finally {
    			try {
                    if (null != rs) {
                        rs.close();
                    }
                    if (null != pstmt) {
                        pstmt.close();
                    }
                    if(null !=conn){
                    	closeConnection(conn);
                    }
                } catch (SQLException e) {
                	e.printStackTrace();
                }
    		}
    		return m;
    	}
    	
    	public int saveExcel(List<HashMap<String, String>> parserResult,
    			String tableName,
    			List<String> notNullField,
    			HashMap<Object,String> filedtypeMap){ 
    		if (parserResult == null || tableName == null || tableName.equals("")) {
    			return 0;
    		}
    		int tag = 1;
    		for(int i = 0 ; i < parserResult.size(); i++){
    			int t = saveDataOracle(parserResult.get(i),tableName,notNullField,filedtypeMap);
    			if (t == 0) {
    				tag = t;
    			}
    			if (t == -1) {
    				tag = t;
    				break;
    			}
    		}
    		return tag;
    	}
    	
    	/**
    	 * 保存网页解析数据到数据库
    	 * @param parserResult
    	 * @param tableName
    	 * @return 返回存储信息：0 存储失败，1 存储成功
    	 */
    	public int saveData(HashMap<String, String> result,String tableName,List<String> notNullField) {
    		if (result == null || tableName == null || tableName.equals("")) {
    			return 0;
    		}
    		
    		//!!!!!!保存数据之前先判断是否存在
    		String executeSql = "";
    		String insertSql = "insert into ";
    		String selectSql = "select * from ";
    		String updateSql = "update ";
    		String conditions = "";
    		List list = null;
    		try {
    			
    			insertSql = insertSql + tableName + " ";
    			selectSql = selectSql + tableName + " ";
    			updateSql = updateSql + tableName + " set ";
    			String field = "";
    			String value = "";
    			for(Map.Entry<String, String> m : result.entrySet()){
    				field = field + m.getKey() + ",";
    				value = value + "'" + m.getValue() + "',";
    				updateSql = updateSql + m.getKey() + "='" + m.getValue() + "',";
    			}
    			if (field.endsWith(",")) {
    				field = field.substring(0,field.length()-1);
    			}
    			if (value.endsWith(",")) {
    				value = value.substring(0,value.length()-1);
    			}
    			if (updateSql.endsWith(",")) {
    				updateSql = updateSql.substring(0,updateSql.length()-1);
    			}
    			
    			//修改
    			insertSql = insertSql + "(" + field + ",spidertime) values (" + value +",NOW()";
    			if (notNullField != null) {
    				for(String fieldName : notNullField){
    					conditions = conditions + fieldName + "='" + result.get(fieldName) + "' and ";
    				}
    				if (conditions.endsWith("' and ")) {
    					conditions = conditions.substring(0,conditions.length()-5);
    				}
    				if (!conditions.equals("")) {
    					selectSql = selectSql + "where " + conditions;
    					updateSql = updateSql + " where " + conditions;
    					 list = execSQL(selectSql);
    					if (list!=null&&list.size()>0) {
    						
    						executeSql = updateSql;
    					}else {
    					
    						executeSql = insertSql;
    					}
    				}else {
    					executeSql = insertSql;
    				}
    				
    			}else {
    				executeSql = insertSql;
    			}
    			execUPdate(executeSql);
    			return 1;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return -1;
    		} finally {
    			
    		}
    	}
    	
    	
    	/**
    	 * 保存网页解析数据到数据库
    	 * @param parserResult
    	 * @param tableName
    	 * @return 返回存储信息：0 存储失败，1 存储成功
    	 * @throws Exception 
    	 */
    	public int saveDataOracle(HashMap<String, String> result,String tableName,
    			List<String> notNullField,HashMap<Object,String> filedtypeMap){
    		if (result == null || tableName == null || tableName.equals("")) {
    			return 0;
    		}
    		int exnum=0;
    		String executeSql = "";
    		String insertSql = "insert into " +tableName +" ";
    		String selectSql = "select * from " +tableName+ " ";
    		List list = null;
    		String field = "";
    		String value = "";
    		String conditions="";
    		for(Map.Entry<String, String> m : result.entrySet()){
    			//处理ORACLE 特殊字符信息
    			String esvalue = RegexValidate.escapeStr(m.getValue());
    			if(filedtypeMap != null  && filedtypeMap.size()>0){
    				// 处理数据库字段类型
    				String dbfiledtype = filedtypeMap.get(m.getKey());
    		    	if(RegexValidate.StrNotNull(dbfiledtype)){
    	    			if(RegexValidate.StrNotNull(esvalue)){
    	    				field = field + m.getKey() + ",";
    	    				if(dbfiledtype.equals("NUMBER")){
    			    			value = value + "" +  Integer.parseInt(esvalue) + ",";
    			    		}else if(dbfiledtype.equals("DATE")){
    			    			value = value + "" + java.sql.Date.valueOf(esvalue) + ",";
    			    		}else if(dbfiledtype.equals("LONG")){
    			    			value = value + "" + Long.parseLong(esvalue) + ",";
    			    		}else{
    			    			//其他类型
    			    			value = value + "'" + esvalue + "',";
    			    		}
    	    			}
    		    	}
    			}
    		}
    		if (field.endsWith(",")) {
    			field = field.substring(0,field.length()-1);
    		}
    		if (value.endsWith(",")) {
    			value = value.substring(0,value.length()-1);
    		}
    		//增加
    		insertSql = insertSql + "("+ field + ") values ("+ value +")";
    		// 设置非空字段 
    		if(notNullField != null && notNullField.size()>0){
    			for(String fieldName : notNullField){
    				if(result.get(fieldName)==null){
    					conditions = conditions + fieldName + " is null" + " and ";
    				}else{
    					conditions = conditions + fieldName + "='" + result.get(fieldName) + "' and ";
    				}
    			}
    			if (conditions.endsWith(" and ")) {
    				conditions = conditions.substring(0,conditions.length()-5);
    			}
    			if (!conditions.equals("")) {
    				selectSql = selectSql + "where " + conditions;
    				list = execSQL(selectSql);
    			}
    			// 判断查询出的结果集合 数据库中是否存在
    			if(list==null || list.size()==0){
//    					excel.info("添加一条数据 "+insertSql);
    					executeSql=insertSql;
    			}else{
    				    excel.info("数据库检测存在该条数据 : " +selectSql);
    				    return 1;
    			}
    		}else{
    			executeSql=insertSql; 
    		}
    		try {
				execUPdate(insertSql);
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				excel.info("执行sql语句异常" +e.getMessage());
				return -1;
			}
    	}
}