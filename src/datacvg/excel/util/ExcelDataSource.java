package datacvg.excel.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import core.util.RegexValidate;
import datacvg.excel.entity.ParserDB;
/**
 * Excel数据解析 dataSource注入
 * @author admin
 *
 */
public class ExcelDataSource {
	private static final Logger excel = Logger.getLogger(ExcelDataSource.class);
	//数据源
	private  DataSource ds;
	//解析文件
	private String parserxmlFile;

	public String getParserxmlFile() {
		return parserxmlFile;
	}

	public void setParserxmlFile(String parserxmlFile) {
		this.parserxmlFile = parserxmlFile;
	}

	public DataSource getDs()
    {
        return ds;
    }

    public void setDs(DataSource ds)
    {
        this.ds = ds;
    }
    public  ExcelDataSource(){
	}
    public  ExcelDataSource(DataSource ds){
    	this.ds = ds;
   	}
    /**
     *  构造方法注入
     * @param ds  数据源
     * @param parserxmlFile 解析结构规则文件
     */
    public  ExcelDataSource(DataSource ds,String parserxmlFile){
    	this.ds=ds;
	    String basedir = getClass().getClassLoader().getResource("/").getPath();
        this.parserxmlFile = (new StringBuilder(String.valueOf(basedir))).append(parserxmlFile).toString();
        ParserDB.parserxmlFile=this.parserxmlFile;
        ParserDB.ds=ds;
	}

    /**
     * 查询SQL 
     * @param conn
     * @param sqlTxt
     * @return
     */
	public List execSQL(String sqlTxt) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn=null;
		List list = null;
		try {
			list = new ArrayList();
			conn = getDs().getConnection();
        	pstmt =conn.prepareStatement(sqlTxt);
            rs = pstmt.executeQuery();
            while(rs.next()){
            	list.add(rs);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
                if (null != rs) {
                    rs.close();
                }
                if (null != pstmt) {
                    pstmt.close();
                }
                if (null != conn) {
                	conn.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
			
		}
		return list;
	}
	/**
	 * sql 增、删、改操作
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Integer execUPdate(String sql) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn=null;
		Integer m = 0;
		
		try {
			conn = getDs().getConnection();
//			excel.info("打开数据库连接！");
        	pstmt = conn.prepareStatement(sql);
//        	excel.info("执行SQL预编译！");
        	m = pstmt.executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
			excel.error("操作失败！"+e.getMessage());
			ExcelStaticVar.parserrorList.add(this.getClass()+"操作失败！"+e.getMessage());
			m=-1;
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			excel.error("执行SQL异常！SQL语句："+sql+ "Message "+e.getMessage());
			ExcelStaticVar.parserrorList.add(this.getClass()+"执行SQL异常！"+e.getMessage());
			m=-1;
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
                if (null != conn) {
                	conn.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            	throw e;
            }
		}
		return m;
	}
	
	
	/**
	 * sql 增、删、改操作
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
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
			}if(t > 0){
				tag = i;
			}
		}
		return tag;
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
	    				if(dbfiledtype.equals("NUMBER") || dbfiledtype.equals("INTEGER")){
			    			value = value + "" +  Integer.parseInt(esvalue) + ",";
			    		}else if(dbfiledtype.equals("DATE")){
			    			value = value + "" + java.sql.Date.valueOf(esvalue) + ",";
			    		}else if(dbfiledtype.equals("LONG")){
			    			value = value + "" + Long.parseLong(esvalue) + ",";
			    		}else{
			    			 if(dbfiledtype.equals("VARCHAR2")){
					    			 if(esvalue.equals("sysdate")){
					    				value = value + "" + esvalue + ",";
					    			 }
					    			 if(esvalue.equals("to_char")){
					    				//日期自定义类型
						    			String datastr = "to_char(sysdate,'yyyy-MM-dd HH24:mi:ss')";
						    			value = value + "" + datastr + ",";
						    		 }else{
						    			 value = value + "'" + esvalue + "',";
						    		 }
					    	 }else{
					    		 value = value + "'" + esvalue + "',";
					    	 }
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
					excel.info("添加一条数据 "+insertSql);
					ExcelStaticVar.parserrorList.add(this.getClass()+"添加一条数据 "+insertSql);
			        
					executeSql=insertSql;
			}else{
				    excel.info("数据库检测存在该条数据 : " +selectSql);
				    ExcelStaticVar.parserrorList.add(this.getClass()+"数据库检测存在该条数据 : " +selectSql);
			        
				    return 1;
			}
		}else{
			executeSql=insertSql; 
		}
		try {
			return execUPdate(insertSql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}
}
