package datacvg.parse.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileUitl {

	  /**
     * 复制单个文件
     * 
     * @param srcFileName
     *            待复制的文件名
     * @param descFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
	  public static boolean copyFile(String srcFileName, String destFileName,
	            boolean overlay) {
	        File srcFile = new File(srcFileName);

	        // 判断源文件是否存在
	        if (!srcFile.exists()) {
	        	 System.out.println("源文件：" + srcFileName + "不存在！");
	          
	            return false;
	        } else if (!srcFile.isFile()) {
	        	 System.out.println("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
	         
	            return false;
	        }

	        // 判断目标文件是否存在
	        File destFile = new File(destFileName);
	        if (destFile.exists()) {
	            // 如果目标文件存在并允许覆盖
	            if (overlay) {
	                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
	                new File(destFileName).delete();
	            }
	        } else {
	            // 如果目标文件所在目录不存在，则创建目录
	            if (!destFile.getParentFile().exists()) {
	                // 目标文件所在目录不存在
	                if (!destFile.getParentFile().mkdirs()) {
	                    // 复制文件失败：创建目标文件所在目录失败
	                    return false;
	                }
	            }
	        }

	        // 复制文件
	        int byteread = 0; // 读取的字节数
	        InputStream in = null;
	        OutputStream out = null;

	        try {
	            in = new FileInputStream(srcFile);
	            out = new FileOutputStream(destFile);
	            byte[] buffer = new byte[1024];

	            while ((byteread = in.read(buffer)) != -1) {
	                out.write(buffer, 0, byteread);
	            }
	            return true;
	        } catch (FileNotFoundException e) {
	            return false;
	        } catch (IOException e) {
	            return false;
	        } finally {
	            try {
	                if (out != null)
	                    out.close();
	                if (in != null)
	                    in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    

    
    /**
     * 复制整个目录的内容
     * 
     * @param srcDirName
     *            待复制目录的目录名
     * @param destDirName
     *            目标目录名
     * @param overlay
     *            如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName,
            boolean overlay) {
        // 判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
        	
        	 System.out.println("复制目录失败：源目录" + srcDirName + "不存在！");
            return false;
        } else if (!srcDir.isDirectory()) {
        	 System.out.println("复制目录失败：" + srcDirName + "不是目录！");
         
            return false;
        }

        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                new File(destDirName).delete();
            } else {
             System.out.println("复制目录失败：目的目录" + destDirName + "已存在！");
           
                return false;
            }
        } else {
            // 创建目的目录
            //System.out.println("目的目录不存在，创建");
            if (!destDir.mkdirs()) {
            	
                System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
    
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {
            	System.out.println(files[i].getAbsolutePath());
            	final String sfile = files[i].getAbsolutePath();
            	final String tofile =  destDirName + files[i].getName();
            	new Thread(new Runnable(){

					public void run() {
						// TODO Auto-generated method stub
						 copyFile(sfile,tofile, true);
					}
            		
            	}).start();
                
                
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag =  copyDirectory(files[i].getAbsolutePath(),
                        destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }
		 
        if (!flag) {
           System.out.println("复制目录" + srcDirName + "至" + destDirName + "失败！");
         
            return false;
        } else {
            return true;
        }
    }
    
    
    //删除文件夹
    //param folderPath 文件夹完整绝对路径
     public static void delFolder(String folderPath) {
	       try {
	          delAllFile(folderPath); //删除完里面所有内容
	          String filePath = folderPath;
	          filePath = filePath.toString();
	          java.io.File myFilePath = new java.io.File(filePath);
	          myFilePath.delete(); //删除文件信息
	       } catch (Exception e) {
	         e.printStackTrace(); 
	       }
       }
     //删除文件夹
     //param folderPath 文件夹完整绝对路径
      public static void delFiles(String filepath) {
 	       try {
 	          String filePath = filepath;
 	          filePath = filePath.toString();
 	          java.io.File myFilePath = new java.io.File(filePath);
 	          //存在、非目录
 	          if(myFilePath.exists() && !myFilePath.isDirectory()){
 	        	  myFilePath.delete(); //删除文件信息
 	          }
 	       } catch (Exception e) {
 	         e.printStackTrace(); 
 	       }
        }
    //删除指定文件夹下所有文件
     //param path 文件夹完整绝对路径
     public static boolean delAllFile(String path) {
         boolean flag = false;
         File file = new File(path);
         if (!file.exists()) {
           return flag;
         }
         if (!file.isDirectory()) {
           return flag;
         }
         String[] tempList = file.list();
         File temp = null;
         for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
               temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
               temp.delete();
            }
            if (temp.isDirectory()) {
               delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
               delFolder(path + "/" + tempList[i]);//再删除空文件夹
               flag = true;
            }
         }
         return flag;
       }
     //获取当前年月
     public static String GetCurrentNY(String suffix,int addmonth){
  	   
  	    Calendar d = Calendar.getInstance();
  		int year = d.get(Calendar.YEAR);
  		int month=d.get(Calendar.MONTH)+1;
  		int date= d.get(Calendar.DAY_OF_MONTH);
  	    
  		System.out.println("year:"+year+"    "+"month:"+month +" date:"+date);
  		String ny="";
  		//对月做处理
  		month=month+addmonth;
  		//就算初始年月
  		if(month==0){
  			ny=year-1+"12";
  		}else if (month>0&month<10){
  			ny=year+"0"+month;
  		}else{
  			ny=year+""+month;
  		}
  		if(date<10){
  			 ny=ny+"0"+date;
  		}else{
  		  ny=ny+""+date;
  		}
  		if(suffix!=null&&!suffix.equals("")){
  			
  			ny=ny+suffix;
  		}
  	   return ny;
     }
     
     public static void main (String[] args){
    	 
    	 delFolder("D:\\EXCEL\\TEPLATE\\中联钢\\中联钢价格走势 - 副本.xls");
//    	  File srcDir = new File("e:/gather");
//    	  String todir =GetCurrentNY("_gather",0);
   	/*  if(srcDir.exists()){
    		   File[] files = srcDir.listFiles();
    		    
    	        for ( int i = 0; i < files.length; i++) {
    	            // 复制文件
    	        	 String filepath =files[i].getAbsolutePath();
    	        	 String fname="/"+files[i].getName();
    	        
					copyDirectory(filepath,"e:/"+todir+fname,true);
	
    	        }
    	        System.out.println("完成");
    	  }
    	  */
    	  //copyDirectory("e:/gather","e:/"+GetCurrentNY("_gather",0),true);
//    	 System.out.println(GetCurrentNY("_gather",0));
    	 
    	 
     }

}
