package core.spider.fun;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import cpdetector.io.ASCIIDetector;
import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;
import cpdetector.io.ParsingDetector;
import cpdetector.io.UnicodeDetector;


public class UpdateFile {

	private static String getEncode(String str){
		String encode = null;
		int i=str.indexOf("charset=");
		if (i > 0) {
			str = str.substring(i);
			i = str.indexOf("\"");
			if (i > 8) {
				encode = str.substring(8, i);
			}
		}
		return encode;
	}
	public static void replaceTxtByStr(String fileName) {
        String temp = "";
        String encode = null;
        try {
        	String fileEncode = getEncoding(fileName);
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,fileEncode);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该行后面的内容
            while ((temp = br.readLine()) != null) {
            	if (encode == null || encode.equals("")) {
					encode = getEncode(temp);
				}else {
					String otherEncode = getEncode(temp);
					if (otherEncode != null && !otherEncode.equals("")) {
						temp = temp.replace("charset="+otherEncode+"\"", "charset="+encode+"\"");
					}
				}
                buf = buf.append(temp);
            }

            br.close();
            OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(file), fileEncode); 
            PrintWriter pw = new PrintWriter(outputStream);
            pw.write(buf.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		String fileName = "F:\\Documents and Settings\\admin\\桌面\\a.html";
		if (getEncoding(fileName).equals("UTF-8")) {
			System.out.println("aaaaa");
		}
	}
	
	public static boolean isTxt(String filepath) {
	  if (filepath != null) {
	      return filepath.endsWith(".txt");
	  } else {
	      return false;
	  }
	}

    public static String getEncoding(String filepath) {
    String fileEncode = "gbk";
    CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
    detector.add(new ParsingDetector(false)); 
    /*--------------------------------------------------------------------------
      JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
      测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
      再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
     ---------------------------------------------------------------------------*/ 
    detector.add(JChardetFacade.getInstance());
    //ASCIIDetector用于ASCII编码测定
    detector.add(ASCIIDetector.getInstance());
    //UnicodeDetector用于Unicode家族编码的测定
    detector.add(UnicodeDetector.getInstance());
    java.nio.charset.Charset charset = null;
    File f=new File(filepath);
    try {
          charset = detector.detectCodepage(f.toURL());
          fileEncode = charset.name();
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
    return fileEncode;
    }
}
