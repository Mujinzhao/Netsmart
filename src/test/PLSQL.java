package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PLSQL {
	/**
	 * 定义常见的时间格式
	 */
	private static String[] dateFormat = { "yyyy-MM-dd HH:mm:ss", // 0
			"yyyy/MM/dd HH:mm:ss", // 1
			"yyyy年MM月dd日HH时mm分ss秒", // 2
			"yyyy-MM-dd", // 3
			"yyyy/MM/dd", // 4
			"yy-MM-dd", // 5
			"yy/MM/dd", // 6
			"yyyy年MM月dd日", // 7
			"HH:mm:ss", // 8
			"yyyyMMddHHmmss", // 9
			"yyyyMMdd", // 10
			"yyyy.MM.dd", // 11
			"yy.MM.dd", // 12
			"HH:mm" // 13
	};
	/**
	 * 内部方法，根据某个索引中的日期格式解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @param index
	 *            日期格式的索引
	 * @return 返回解析结果
	 */
	public static Date parseDate(String dateStr, int index) {
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[13]);

			return df.parse(dateStr);
		} catch (ParseException pe) {
			return parseDate(dateStr, index + 1);
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	public static void main(String[] args) {
		//new PLSQL().exeSqlplus("datacvg", "datacvg", "XE", "init.sql", "D:/db/integrate/", "D:/log/gts1031.log");
	  String datestr ="2013-1-5";
	  datestr = datestr.substring(5,datestr.length());
	  System.out.println(datestr);
//	  Date date =  parseDate(datestr, 13);
//	  System.out.println(date);
	}
	/**
	 * 执行  .sql 文件信息
	 * @param username  数据库用户
	 * @param password  数据库密码
	 * @param host      端口号
	 * @param fileName  执行的文件名称
	 * @param dir       存储文件地址
	 * @param log       执行文件日志
	 */
	public void exeSqlplus(String username,String password,
			String host,
			String fileName,
			String dir,
			String log){
		FileOutputStream fos = null;
		InputStream in = null;
		Process p=null;
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("sqlplus ");
			sb.append(username);
			sb.append("/");
			sb.append(password);
			sb.append("@");
			sb.append(host);
			sb.append(" @");
			sb.append(fileName);
//			String cmd = "sqlplus datacvg/datacvg@XE @D:/db/integrate/stundents.sql >D:/log/stundents.log";
			String cmd=sb.toString();
			System.out.println(cmd);
			Runtime rt = Runtime.getRuntime();
//			p = rt.exec(cmd,null,new File("D:/db/integrate/"));
			p = rt.exec(cmd,null,new File(dir));
			in = p.getInputStream();
//			fos = new FileOutputStream("D:/gts1031.log");
			fos = new FileOutputStream(log);
			byte[] b = new byte[1024];
			int br = 0;
			while ((br = in.read(b)) != -1) {
				String str=new String(b,0,br);
				int i=str.indexOf("SP2-0310");
				int j=str.indexOf("SQL>");
				if(i!=-1){
					p.destroy();
				}
				fos.write(b, 0, br);
				if(j!=-1){
					p.destroy();
				}
			}
			p.waitFor();
			fos.flush();
			fos.close();
			in.close();
			p.destroy();
			System.out.println("执行结束");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
