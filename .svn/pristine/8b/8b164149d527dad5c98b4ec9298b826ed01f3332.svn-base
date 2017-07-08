package datacvg.parse.util;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
 
public class EncodingDetector {
    /**
     * 检测文件编码,如果检测不出作为ASCII文件处理
     * @param file
     * @return
     */
	public static String detect(File file)  {
		byte[] buf = new byte[4096];
        java.io.FileInputStream fis = null;
        UniversalDetector detector = new UniversalDetector(null);
        String encoding="";
        try {
            fis = new java.io.FileInputStream(file);

        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
		detector.dataEnd();

		encoding = detector.getDetectedCharset();
		if (null==encoding) {
			encoding="ASCII";
		}
		fis.close();
              } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        detector.reset();
		return encoding;
	}
	
	public static void main (String[] args){
		
		File file = new File("D:\\gather\\122_0\\0.html");
		System.out.println(detect(file));
		
	}
}