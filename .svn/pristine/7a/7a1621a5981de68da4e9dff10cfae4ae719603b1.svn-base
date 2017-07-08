package core.spider.fun;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import core.util.RegexValidate;

public class DeleteFile {

	/**
	 * 删除文件
	 * @param fileName
	 */
	public static void deleteFile(String fileName){
		if (RegexValidate.StrisNull(fileName)) {
			return ;
		}
		File file = new File(fileName);
		file.delete();
	}
	/**
	 * 获取文件大小
	 * @param fileName
	 * @return
	 */
	public static int getFileSize(String fileName){
		int size = 0;
		File f = new File(fileName);   
		if (f.exists()) {   
			  
            FileInputStream fis = null;   
  
            try {
				fis = new FileInputStream(f);
				size = fis.available();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return size;
	}
	//获取文件流的大小
	public   void   saveToFile(String   destUrl,   String   fileName)   throws   IOException   
	{ 
	        FileOutputStream   fos   =   null; 
	        BufferedInputStream   bis   =   null; 
	        HttpURLConnection   httpUrl   =   null; 
	        URL   url   =   null; 
	        byte[]   buf   =   new   byte[1000]; 
	        int   size   =   0; 
	        //建立链接 
	        url   =   new   URL(destUrl); 
	        httpUrl   =   (HttpURLConnection)   url.openConnection(); 
	        //连接指定的资源 
	        httpUrl.connect(); 
	        //获取网络输入流 
	        bis   =   new   BufferedInputStream(httpUrl.getInputStream()); 
	        //建立文件 
	        fos   =   new   FileOutputStream(fileName); 
	        
	        System.out.println( "正在获取链接[ "   +   destUrl   +   "]的内容...\n将其保存为文件[ "   +   fileName   +   "] "); 
	        //保存文件 
	        while   (   (size   =   bis.read(buf))   !=   -1)   
	        { 
	        fos.write(buf,   0,   size); 
	        //++s; 
	        System.out.println(size); 
	        //System.out.println(s/1024+ "M "); 
	        } 
	            
	        fos.close(); 
	        bis.close(); 
	        httpUrl.disconnect(); 
	    }
}
