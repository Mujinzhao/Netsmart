package core.spider.fun;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HtmlReader {
	/**   
     * @param filePath 文件路径   
     * @return 获得html的全部内容   
     */   
    public static String readHtml(String filePath) {    
    	if (getFileSize(filePath) <= 2) {
			return "";
		}
        BufferedReader br=null;    
        StringBuffer sb = new  StringBuffer();    
        try {
        	String fileEncode = UpdateFile.getEncoding(filePath);
        	if (!fileEncode.equals("UTF-8")) {
				fileEncode = "GBK";
			}
//        	System.out.println(fileEncode);
//            br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),  fileEncode));  
        	br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String temp=null;           
            while((temp=br.readLine())!=null){    
                sb.append(temp+"\r\n");    
            }
            br.close();
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }finally{
        	if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        return sb.toString();    
    }    
    public static String readHtml(String filePath,String fileEncode) {    
    	if (getFileSize(filePath) <= 2) {
			return "";
		}
        BufferedReader br=null;    
        StringBuffer sb = new  StringBuffer();    
        try {
//        	System.out.println(fileEncode);
            br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),  fileEncode));  
//        	br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String temp=null;           
            while((temp=br.readLine())!=null){    
                sb.append(temp+"\r\n");    
//            	sb.append(temp);    
            }
            br.close();
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }finally{
        	if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        return sb.toString();    
    }    
    public static String readHtml(File parsefile,String fileEncode) {    
    	if (getFileSize(parsefile) <= 2) {
			return "";
		}
        BufferedReader br=null;    
        StringBuffer sb = new  StringBuffer();    
        try {
//        	System.out.println(fileEncode);
            br=new BufferedReader(new InputStreamReader(new FileInputStream(parsefile),  fileEncode));  
            String temp=null;           
            while((temp=br.readLine())!=null){    
                sb.append(temp+"\r\n");    
            }
            br.close();
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }finally{
        	if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        return sb.toString();    
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
    /**
     * 获取文件大小
     * @param fileName
     * @return
     */
    public static int getFileSize(File parsefile){
		int size = 0;
		if (parsefile.exists()) {   
			  
            FileInputStream fis = null;   
  
            try {
				fis = new FileInputStream(parsefile);
				size = fis.available();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return size;
	}
    public static void main(String[] args) {
		String htmlSource = readHtml("D:\\download\\5_1\\0.html");
		System.out.println(htmlSource);
	}
}
