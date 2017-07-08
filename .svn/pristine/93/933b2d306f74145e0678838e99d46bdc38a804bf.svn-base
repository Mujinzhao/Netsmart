package datacvg.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * 实现系统日志的分层记录功能。应用系统需要记录2类日志：系统日志和业务日志。每类日志都有
 * 一个对应的日志文件。系统日志主要用来记录程序的调试信息、启动和初始化状态、出错信息等；
 * 业务日志用来记录用户对系统的使用情况，这主要包括用户在系统中执行的操作、对系统资源的
 * 访问情况等等。可以将日志信息记录到指定的日志文件，并将日志信息打印到console上。
 *
 * 将系统日志划分为DEBUG,INFO和ERROR三个级别，划分的原则如下：
 *   DEBUG级别：详细的记录程序的调试信息。该级别在调试程序时使用。
 *   INFO级别 ：详细的记录系统启动时的信息以及一些重要属性的加载信息。一般用在系统初始化
 *             和一些重要的属性的初始化时使用。
 *   ERROR级别：详细的描述程序的出错信息和程序中捕获到的违例信息。用在出错和违例捕获时。
 *
 * 业务日志分为以下2个日志级别：
 *   DETAILINFO级别：详细的用户对系统的操作，在系统中的流程以及对系统资源的访问情况。
 *   INFO级别      ：简单的记录用户在系统中的流程和对重要资源的访问情况。
 *
 * 该类还需要一份属性配置文件，在属性配置文件中需要描述如下内容：
 *   1 日志文件的名称、存储地点、大小和备份数量。
 *   2 日志文件不同等级的不同显示格式。
 *   3 日志文件的存储等级。
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008 上海数聚信息技术有限公司</p>
 * <p>Company: 上海数聚信息技术有限公司</p>
 * @author leipan
 * @version 1.0
 */

public class LogWritter {
    /**
     * systemLogger代表系统日志。他是org.apache.log4j.Logger类的一个实例，我们会用它的
     * 一些方法来记录系统日志。systemLogger会在init方法中被附初值。
     */
    private static Logger parserLogger = null ;

    /**
     * 实现系统日志的分层记录功能。应用系统需要记录2类日志：系统日志和业务日志。每类日志都有
     * 一个对应的日志文件。系统日志主要用来记录程序的调试信息、启动和初始化状态、出错信息等；
     * 业务日志用来记录用户对系统的使用情况，这主要包括用户在系统中执行的操作、对系统资源的访
     * 问情况等等。可以将日志信息记录到指定的日志文件，并将日志信息打印到console上。将系统
     * 日志划分为DEBUG,INFO和ERROR三个级别，划分的原则如下：
     *   DEBUG级别：详细的记录程序的调试信息。该级别在调试程序时使用。
     *   INFO级别： 详细的记录系统启动时的信息以及一些重要属性的加载信息。一般用在系统初始化
     *             和一些重要的属性的初始化时使用。
     *   ERROR级别：详细的描述程序的出错信息和程序中捕获到的违例信息。用在出错和违例捕获时。
     * 业务日志分为以下2个日志级别：
     *   DETAILINFO级别：详细的用户对系统的操作，在系统中的流程以及对系统资源的访问情况。
     *   INFO级别：简单的记录用户在系统中的流程和对重要资源的访问情况。
     * 该类还需要一份属性配置文件，在属性配置文件中需要描述如下内容：
     *   1 日志文件的名称、存储地点、大小和备份数量。
     *   2 日志文件不同等级的不同显示格式。
     *   3 日志文件的存储等级。
     */
    private static Logger gatherLogger = null;

    private static Logger haierLogger = null;

    /**
     * logWritter的配置文件名称,该存放在同级系统路径下面
     */
    private static String confFileName = "logWriter.properties";

    /**
     * java语言的静态初始化方法。这个方法用来初始化LogWritter类中的两个静态Logger属性和
     * 读取属性文件中的内容。它会首先调用org.apache.log4j.PropertyConfigurator类的静
     * 态方法configure（：String），来读取属性文件中的参数。configure方法需要一个String
     * 类型的参数，来表示属性文件的存放路径。属性文件的存放路径是与该LogWritter.class存放
     * 在相同路径下的，因此只需要传递近参数"log4j.porperites"即可。然后，调用
     * org.apache.log4j.Logger类的getLogger方法为systemLogger和businessLogger附初
     * 始值。两次调用getLogger方法时的参数分别是"system"和"business"。
     */
    static {
        FileURL fileUrl = new FileURL() ;
        PropertyConfigurator.configure(fileUrl.getFileURL(confFileName));
        gatherLogger = Logger.getLogger("gather");
        parserLogger = Logger.getLogger("parser");
        haierLogger  = Logger.getLogger("haier");
    }

   

    /**
     * 用来记录系统日志文件的INFO级的日志。需要调用systemLogger属性的info方法。
     * 记录格式：
     *  [日期] [时间] - [所在的类：行] [方法名] - 描述信息
     *  %d [%c:%L] [%t] - %m%n
     *
     * @param message 需要记录的日志信息
     */
    public  static void gatherInfo(String message){
    	gatherLogger.info(message);
    }

    /**
     * 用来记录系统日志文件的ERROR级的日志。需要调用systemLogger属性的error方法。只记录描述信息。
     * 记录格式：
     *  [日期] [时间] - [所在的类：行] [方法名] - 描述信息
     *  %d [%c:%L] [%t] - %m%n
     *
     * @param message 需要记录的日志信息
     */
    public  static void gatherError(String message){
    	gatherLogger.error(message);
    }

    /**
     * 用来记录系统日志文件的ERROR级的日志。需要调用systemLogger属性的error方法。这个方法可以同时记录描述信息和被捕获的违例信息。
     * 记录格式：
     *  [日期] [时间] - [所在的类：行] [方法名] - 描述信息  捕获的违例信息
     *  %d [%c:%L] [%t] - %m%n
     *
     * @param message      需要记录的日志信息
     * @param exception    被捕获的违例信息
     */
    public  static void gatherError(String message,Throwable exception){
    	gatherLogger.error(message,exception);
    }

    /**
     * 这个方法返回属性配置文件中描述的，当前系统日志的日志等级。该方法调用
     * org.apache.log4j.Logger类的getLevel方法。
     *
     * @return : String - 当前系统日志的等级。一共有三种结果：DEBUG，INFO和ERROR。他们分别代表DEBUG等级、INFO等级和ERROR等级。
     * @return 日志等级描述
     */
    public  static String getGatherLevel(){
        return gatherLogger.getLevel().toString();
    }

    /**
     * 这个方法返回属性配置文件中描述的，当前业务日志的日志等级。该方法调用org.apache.log4j.Logger类的getLevel方法。
     * @return : String - 当前系统日志的等级。一共有二种结果：DETAILINFO和INFO。他们分别代表DETAILINFO等级和INFO等级。

     * @return 日志等级描述
     */
    public  static String getParserLevel(){
        return parserLogger.getLevel().toString();
    }

    /**
     * 用来记录业务日志文件的INFO级的日志。需要调用businessLogger属性的info方法。
     * 记录格式：
     *  [日期] [时间] - 描述信息
     * %d   - %m%n
     *
     * @param message
     */
    public  static void parserInfo(String message){
    	parserLogger.info(message);
    }
    public  static void parserError(String message){
    	parserLogger.error(message);
    }
    public  static void parserError(String message,Throwable exception){
    	parserLogger.error(message,exception);
    }

    public  static void haierError(String message){
        haierLogger.error(message);
    }
    public  static void haierError(String message,Throwable exception){
        haierLogger.error(message,exception);
    }
    public  static void haierInfo(String message){
        haierLogger.info(message);
    }

    public  static void main(String[] args) {
        LogWritter log = new LogWritter();
        log.gatherLogger.info("启动采集任务");
        
        
        log.gatherLogger.error("启动采集任务异常");
//        log.g.getConnection();
//        LogWritter.gatherInfo("启动采集任务");
//        LogWritter.gatherError("启动采集任务异常");
//        
//        LogWritter.parserInfo("启动解析任务");
//        LogWritter.parserError("启动解析任务异常");
//        
//        LogWritter.haierInfo("启动海尔解析程序");
//        LogWritter.haierError("启动海尔解析程序异常");
        
        
//        LogWritter.sysDebug("========");
//        LogWritter.sysDebug("System Debug Information.");
//        LogWritter.sysDebug(null);
//        LogWritter.sysInfo("System Info Information.");
//        LogWritter.sysInfo(null);
//        LogWritter.sysError("System Error Information.");
//        LogWritter.sysError(null);
//
//        LogWritter.bizDetailInfo("Business DetailInfo");
//        LogWritter.bizDetailInfo(null);
//        LogWritter.bizInfo("Business Info");
//        LogWritter.bizInfo(null);
    }
}