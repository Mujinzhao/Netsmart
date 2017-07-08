package datacvg.log;

import java.net.URL;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright:  Copyright (c) 2008  上海数聚信息技术有限公司、</p>
 * <p>Company: css</p>
 * @author leipan,zhanghf
 * @version 1.0
 */

public class FileURL {
    public FileURL() {
    }

    /**
     * 得到文件的URL连接 ;
     */
    public URL getFileURL(String fileName){
        ClassLoader loader = this.getClass().getClassLoader() ;
        URL fileUrl = loader.getResource(fileName) ;
        fileUrl.getPath();
        return fileUrl ;

    }


}