package core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
/**
 * 
 * @描述：工具类
 * 
 * @时间：2012-08-29 下午16:30:40
 */

public class WDWUtil
{

	/**
	 * 
	 * @描述：是否是2003的excel，返回true是2003
	 * 
	 * @参数：@param filePath　文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：boolean
	 */

	public static boolean isExcel2003(String filePath)
	{

		return filePath.matches("^.+\\.(?i)(xls)$");

	}

	/**
	 * 
	 * @描述：是否是2007的excel，返回true是2007
	 * 
	 * @参数：@param filePath　文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：boolean
	 */

	public static boolean isExcel2007(String filePath)
	{

		return filePath.matches("^.+\\.(?i)(xlsx)$");

	}
	 /**
	 * 
	 * @描述：验证XML文件
	 * 
	 * @参数：@param filePath　文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：boolean
	 */

	public static boolean validateXML(String filePath)
	{

		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null || !filePath.matches("^.+\\.(?i)(xml)$"))
		{
			return false;
		}
		/** 检查文件是否存在 */

		File file = new File(filePath);
		if (file == null || !file.exists())
		{
			return false;
		}
		return true;

	}
	/**
	 * 生成随机采集时间：当前年月日时分秒+4位随机数
	 * 
	 * @return
	 */
	public static String getRandomSpiderTime() {

		SimpleDateFormat simpleDateFormat;

		simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		Date date = new Date();

		String str = simpleDateFormat.format(date);

		Random random = new Random();
//		int rannum = random.nextInt(999999);
		int rannum = (int) (random.nextDouble() * (9999 - 1000 + 1)) + 1000;// 获取4位随机数

		return str+rannum;// 当前时间
	}

	public  static String getSeqNextval(){
		Random random = new Random();
		return System.currentTimeMillis()+""+random.nextInt(999999);
	}
	
	//获取当前时间
	public static Date getCurrentTime(){
		return new Date();
	}
	//oracle.sql.Clob类型转换成String类型
    public static String ClobToString(Clob clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 得到流
        BufferedReader br = new BufferedReader(is);
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            //执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            try {
                s = br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        reString = sb.toString();
        return reString;
    }

    /**
	 * 文件大小转换
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	
	public static long  getFileSizes(File f) throws Exception{//取得文件大小
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
           s= fis.available();
        } else {
            f.createNewFile();
        }
        return s;
    }
	// 返回文件的截取后缀后的名称 '***'
	public static String getExtentionType(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf("."),fileName.length());
	}
	// 返回文件的截取后缀后的名称 '***'
	public static String getExtentionName(String fileName) {
		
		return fileName.substring(0,fileName.lastIndexOf("."));
	}
	// 动态配置log4j信息
	
	public static Properties setLog4jParam(String filename){
		String fileName = RegexValidate.StrisNull(filename)!=true?filename:"startlog.log";
		
		  Properties pro=new Properties();
		  // 日志控制台
		  pro.put("log4j.rootCategory", "R,stdout,RDEBUG,A1");
		  pro.put("log4j.logger.com.haier.dataspider.util", "R,stdout,RDEBUG,A1");
		  pro.put("log4j.logger.com.haier.dataspider.param.action", "R,stdout,RDEBUG,A1");
		  pro.put("log4j.logger.com.haier.dataspider.param.service", "R,stdout,RDEBUG,A1");
		  pro.put("log4j.logger.com.haier.startup.myjnotify", "R,stdout,RDEBUG,A1");
		  pro.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		  pro.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		  pro.put("log4j.appender.stdout.layout.ConversionPattern", "[QC] %-4r [%t] %-5p %c - %m%n");
//	      // 日志写入到文件中		
		  pro.put("log4j.appender.A1", "org.apache.log4j.DailyRollingFileAppender");
		  pro.put("log4j.appender.A1.Append", "true");
		  pro.put("log4j.appender.A1.File", "D:/UDFILE/UPLOAD/PARSERLOG/"+fileName);
		  pro.put("log4j.appender.A1.MaxFileSize", "100KB");
		  pro.put("log4j.appender.A1.MaxBackupIndex", "1");
		  pro.put("log4j.appender.A1.DatePattern", "'.'yyyy-MM-dd'.log'");
		  pro.put("log4j.appender.A1.layout", "org.apache.log4j.PatternLayout");
		  pro.put("log4j.appender.A1.ConversionPattern", "[%-5p] %d{yyyy-MM-dd HH\\:mm\\:ss} Method\\: %l%n%m%n");
		  return pro;
//				log4j.appender.A1=org.apache.log4j.DailyRollingFileAppender
//				log4j.appender.A1.Append=true  
//				log4j.appender.A1.MaxFileSize=100KB
//				log4j.appender.A1.MaxBackupIndex=1 
//				#log4j.appender.A1.File=D\:/UDFILE/UPLOAD/PARSERLOG/parserlog.log
//				log4j.appender.A1.File=D\:/UDFILE/UPLOAD/PARSERLOG/[%X{filename}]
//				log4j.appender.A1.DatePattern = '.'yyyy-MM-dd'.log'  
//				log4j.appender.A1.layout= 
//				log4j.appender.A1.layout.ConversionPattern=[%-5p] %d{yyyy-MM-dd HH\:mm\:ss} Method\: %l%n%m%n
	}
	public static void main(String[] args) {
//		String fileName = WDWUtil.getRandomSpiderTime();
	    String dirstr= System.getProperty("user.dir")+"\\";
	    System.out.println(dirstr);
	    String basedir = WDWUtil.class.getClassLoader().getResource("/").getPath();
//	    String basedir1 = getClass().getClassLoader().getResource("/").getPath();
        String filePath =basedir+"template/parserxml/指标数据.xml";
        System.out.println(filePath);
//		boolean falg = WDWUtil.validateXML(filePath);
//		if(!falg){
//			System.out.println("sssssssss");
//		}
		System.out.println("sssssssssqqq");
//		System.out.println("ssa");
	}
}