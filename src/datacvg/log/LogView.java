package datacvg.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.core.tag.util.SystemConstant;

import core.util.RegexValidate;
import datacvg.excel.util.ExcelStaticVar;

public class LogView {
	public static long lastTimeFileSize = 0; // 上次文件大小
	public static String tmp = "";
//	/**
//	 * 实时输出日志信息
//	 * 
//	 * @param logFile
//	 *            日志文件
//	 * @throws IOException
//	 */
//	public String realtimeShowLog(File logFile) throws IOException {
//		// 指定文件可读可写
//		final RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
////		 String basedir = this.getClass().getClassLoader().getResource("/").getPath();
//		 String lastimefile = (new StringBuilder(String.valueOf("E:/360YunPan/项目工具/Tomcat/tomcat_datacvg_ims/webapps/DMC/WEB-INF/classes/"))).append("parser/readloglasttime.properties").toString();
//        if(RegexValidate.StrisNull(lastimefile)){
//        	lastimefile = SystemConstant.get("getLastTimeFileDir");
//        }
//		String tmpstr = "";
//		if (lastTimeFileSize == 0) {
//			// 获取配置文件中的文件大小
//			lastTimeFileSize = Long.parseLong(getPropertyTime(lastimefile));
//		}
//		// 获得变化部分的
////		System.out.println("lastTimeFileSize " + lastTimeFileSize);
//		randomFile.seek(lastTimeFileSize);
//		tmp = "";
//		while ((tmpstr = randomFile.readLine()) != null) {
//			tmp += new String(tmpstr.getBytes("ISO8859-1"), "GBK") + "<br/>";
//		}
//		lastTimeFileSize = randomFile.length();
//		return tmp;
//
//	}

	
	
	/**
	 * 实时输出日志信息
	 * 
	 * @param logFile
	 *            日志文件
	 * @throws IOException
	 */
	public String realtimeShowLog(File logFile) throws IOException {
		 //指定文件可读可写
		final RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
		 String lastimefile = this.getClass().getClassLoader().getResource("/").getPath();
		 lastimefile +="readloglasttime.properties";
//		 String lastimefile = (new StringBuilder("/readloglasttime.properties")).append("parser/readloglasttime.properties").toString();
        if(RegexValidate.StrisNull(lastimefile)){
        	lastimefile = SystemConstant.get("getLastTimeFileDir");
        }
		String tmpstr = "";
		if (lastTimeFileSize == 0) {
			// 获取配置文件中的文件大小
			lastTimeFileSize = Long.parseLong(getPropertyTime(lastimefile));
		}
		// 获得变化部分的
//		System.out.println("lastTimeFileSize " + lastTimeFileSize);
		randomFile.seek(lastTimeFileSize);
		tmp = "";
//		tmpstr = new String(randomFile.readLine().getBytes("ISO8859-1"),"UTF-8");
//		while (tmpstr!=null){
		while ((tmpstr = randomFile.readLine()) != null) {
			tmp += new String(tmpstr.getBytes("ISO8859-1"), "GBK") + "<br/>";
		}
		lastTimeFileSize = randomFile.length();
		return tmp;

	}


	/**
	 * 获取配置文件 最后修改时间信息
	 */
	public String getPropertyTime(String filePath) throws IOException {
		Properties prop = loadProperty(filePath);
		return prop.getProperty("lastTimeFileSize");
	}

	/**
	 * 保存修改后的配置文件
	 */

	public void saveProperty(String lastTimeFileSize,String filePath) throws IOException {
		filePath = SystemConstant.get("getLastTimeFileDir");
//		 String basedir = this.getClass().getClassLoader().getResource("/").getPath();
//		filePath = this.getClass().getResource("/readloglasttime.properties").getPath();
//		System.out.println(filePath);
//		filePath = (new StringBuilder(String.valueOf("E:/360YunPan/项目工具/Tomcat/tomcat_datacvg_ims/webapps/DMC/WEB-INF/classes/"))).append("parser/readloglasttime.properties").toString();
       
		Properties prop = loadProperty(filePath);
		//this.getClass().getResource("/readloglasttime.properties").getPath();
//		lastimefile = lastimefile.substring(1, lastimefile.length());
		OutputStream fos = new FileOutputStream(filePath);
		prop.setProperty("lastTimeFileSize", lastTimeFileSize);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		prop.store(fos, df.format(new Date()));
	}

	/**
	 * 读取配置文件
	 */
	private Properties loadProperty(String filePath) {
		Properties prop = new Properties();
		try {
			FileInputStream is = new FileInputStream(filePath);
			prop.load(is);
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

	public static void main(String[] args) throws Exception {
		LogView view = new LogView();
		final File tmpLogFile = new File("c:/logs/error.log");
		view.realtimeShowLog(tmpLogFile);
		if (RegexValidate.StrNotNull(view.tmp)) {
			System.out.println(view.tmp);
			ExcelStaticVar.lastTimeFileMap.put(tmpLogFile.getPath(),view.lastTimeFileSize);
			view.tmp = "";
		}
//		view.saveProperty("10qqq00111");
	}
}
