package core.spider.fun;

import java.io.InputStream;
import java.util.Properties;

public class GetProperty {

    /**
     * 读取/路径下名为proName的配置文件中的para属性值.
     * <br>
     * properties文件放在源码src目录下
     *
     * @param name
     *            properties配置文件名
     * @param para
     *            属性名
     * @return 返回para对应的值
     */
    public static String readProperties(final String name, final String para) {
        if (null == name || null == para) {
            return null;
        }

        String proName = name;

        if (!proName.contains(".")) {
            proName += ".properties";
        }

        try {
            Properties properties = new Properties();
            InputStream is = Class.class.getResourceAsStream("/config/" + proName);
            properties.load(is);
            is.close();
            String ret = properties.getProperty(para);
            if (ret != null) {
                ret = new String(ret.getBytes("ISO8859-1"), "utf-8");
            }
            return ret;
        } catch (Exception err) {
            System.err.println("Read " + proName + " " + para + "  Error!");
            return null;
        }
    }
 
    /**
     * 读取/路径下名为proName的配置文件中的para属性值.
     * @param 
     *               folder 配置文件所在的文件夹
     * @param name 
     *               properties配置文件名
     * @param para
     *               属性名
     * @return       返回para对应的值
     */
    public static String readProperties(String folder,final String name, final String para) {
        if (null == name || null == para) {
            return null;
        }

        String proName = name;

        if (!proName.contains(".")) {
            proName += ".properties";
        }

        try {
            Properties properties = new Properties();
            InputStream is = Class.class.getResourceAsStream("/"+folder+"/" + proName);
            properties.load(is);
            is.close();
            String ret = properties.getProperty(para);
            if (ret != null) {
                ret = new String(ret.getBytes("ISO8859-1"), "utf-8");
            }
            return ret;
        } catch (Exception err) {
            System.err.println("Read " + proName + " " + para + "  Error!");
            return null;
        }
    }
    
    /**
     * 读取/路径下名为proName的配置文件中的Properties对象.
     * @param 
     *               folder 配置文件所在的文件夹
     * @param name 
     *               properties配置文件名
     * @param para
     *               属性名
     * @return       返回Properties对象
     */
    public static Properties getProperties(String folder,final String name, final String para) {
        if (null == name || null == para) {
            return null;
        }

        String proName = name;

        if (!proName.contains(".")) {
            proName += ".properties";
        }

        try {
            Properties properties = new Properties();
            InputStream is = Class.class.getResourceAsStream("/"+folder+"/" + proName);
            properties.load(is);
            is.close();
            return properties;
        } catch (Exception err) {
            System.err.println("Read " + proName + " " + para + "  Error!");
            return null;
        }
    }
    public static void main(String[] args) {
		System.out.println(GetProperty.readProperties("config","dbconf.properties","oracle.driverClass"));
		System.out.println(GetProperty.readProperties("config","system.properties","dburl"));	
		System.out.println(GetProperty.readProperties("config","system.properties","user"));	
		System.out.println(GetProperty.readProperties("config","system.properties","pass"));	
		System.out.println(GetProperty.readProperties("config","system.properties","webDeepth"));	
	}
}
