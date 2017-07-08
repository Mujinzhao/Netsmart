package datacvg.dbconfg.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.util.RegexValidate;

public class ReadSQLFile {
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	/**
	 * Oracle数据库连接URL
	 */
	private final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	/**
	 * Oracle数据库连接驱
	 */
	private final static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 数据库用户名
	 */
	private final static String DB_USERNAME = "datacvg";
	/**
	 * 数据库密码
	 */
	private final static String DB_PASSWORD = "datacvg";

	public static void main(String[] args) {
		String content = "create table <font color=\"lightblue;\">test</font><br>" +
				" ( <br>" +
				"aaa number " +
				"<br>)" +
				"<br> tablespace <font color=\"lightblue;\">DT_DATA</font><br> " +
				"pctfree <font color=\"lightblue;\">10</font><br>" +
				" initrans <font color=\"lightblue;\">1</font><br>" +
				" maxtrans <font color=\"lightblue;\">255</font><br> " +
				" storage<br>(<br> initial <font color=\"lightblue;\">64</font>K<br>" +
				" minextents <font color=\"lightblue;\">1</font><br>" +
				" maxextents unlimited<br>);<br>" +
				" comment on column <font color=\"lightblue;\">test</font>.aaa is 'aaaa';<br>";
		try{
			String savepath ="D:/db/integrate/tests.sql";
			readSQLFile(savepath);
		     
//		        //String str = "【AA】XXX,【BB】XXX";
//		        String str = "xxx,xxx,xxx";
//		      //  String regex = "【(.*?)】";
//		        
//		        str=str.replace(",", "','");
//		        System.out.println("'"+str+"'");
		        
		        
		        
//		        String regex = "【(.*?)】";
//		        Pattern p = Pattern.compile(regex);
//
//		        Matcher m = p.matcher(str);
//
//		        while(m.find()){
//
//		            System.out.println(m.group(1));
//
//		        }
		     
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 读取SQLFILE 文件内容
	 */
	public static void readSQLFile(String savepath){
		try {
			ReadSQLFile rsf = new ReadSQLFile();
			Connection conn = rsf.getConnection();
			File f = new File(savepath);
			InputStream is = new FileInputStream(f);
			StringBuffer bu = new StringBuffer();
			byte[] buf = new byte[1024];
			int br = 0;
			while ((br = is.read(buf)) != -1) {
				bu.append(new String(buf, 0, br));
				Statement stmt = conn.createStatement();
				String sqlStr = bu.toString();
				String[] sql = sqlStr.split(";");
				if (sql != null && sql.length > 0) {
					for (int j = 0; j < sql.length; j++) {
						try {
							System.out.println("KONGZHI " + sql[j]+"值");
							if(RegexValidate.StrNotNull(sql[j])){
								stmt.executeUpdate(sql[j]);
							}
						} catch (Exception e) {
							// 打印日志
							e.printStackTrace();
							continue;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	public static void readSQLContent(String content) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		try {
				content = content.replaceAll("<br>", "\n");
				Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
		        Matcher m_html = p_html.matcher(content);  
		        String sqlStr  = m_html.replaceAll(""); // 过滤html标签  
				conn = DBUtil.openConnection();
				stmt = conn.createStatement();
				String[] sql = sqlStr.split(";");
				if (sql != null && sql.length > 0) {
					for (int j = 0; j < sql.length; j++) {
						try {
							if(RegexValidate.StrNotNull(sql[j])){
								stmt.executeUpdate(sql[j]);
							}
						} catch (Exception e) {
							// 打印日志
							e.printStackTrace();
							continue;
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if (conn != null && !conn.isClosed()) {
					DBUtil.closeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	// 向文件写入内容(输出流)
	public static void writeSQLFile(String savepath,String content) throws Exception{
		        // html <br> 标签 换成java 中的换行符
				content = content.replaceAll("<br>", "\n");
				Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
		        Matcher m_html = p_html.matcher(content);  
		        content = m_html.replaceAll(""); // 过滤html标签  
		        File file = new File(savepath);
		        if(!file.exists()){
		        	file.createNewFile();
		        }
				byte bt[] = new byte[1024];
				bt = content.getBytes();
				try {
					FileOutputStream in = new FileOutputStream(file);
					try {
						in.write(bt, 0, bt.length);
						in.close();
						// boolean success=true;
						// System.out.println("写入文件成功");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					// 读取文件内容 (输入流)
					FileInputStream out = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(out);
					int ch = 0;
					while ((ch = isr.read()) != -1) {
						//System.out.print((char) ch);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	}
	
	
	/**
	 * 获取数据库连接
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		//实例化配置dbconfig配置文件 
		Properties dbpro = new Properties();
//		InputStream inStream = new in
//		dbpro.load(new  InputinStream)
		/**
		 * 声明Connection连接对象
		 */
		Connection conn = null;
		try {
			/**
			 * 加载驱动
			 */
			Class.forName(DB_DRIVER);
			/**
			 * 获取数据库连接
			 */
			conn = DriverManager
					.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
