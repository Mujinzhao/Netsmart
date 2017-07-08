package core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 字符串处理工具
 * @author sxy
 *
 */
public class StringUtil {
	
	/**
	 * 字符串分割
	 * @param oldString 需要分割的字符串
	 * @param splitstr 分隔符
	 * @return 返回分割后的数组
	 */
	public static String[] splitString(String oldString,String splitstr){
		//定义返回对象
		String[] newString = null;
		//截取对象成数组
		newString = oldString.split(splitstr);
		//返回对象
		return newString;
	}
	
	
	/**
	 * 字符串分割
	 * @param oldString 需要分割的字符串
	 * @param splitstr 分隔符
	 * @return 返回分割后的数组
	 */
	public static List<String> splitStringToList(String oldString,String splitstr){
		//定义返回对象
		String[] newString = null;
		//截取对象成数组
		newString = oldString.split(splitstr);
		//数组转成list
		List<String> list = new ArrayList<String>(Arrays.asList(newString));
		//返回对象
		return list;
	}
	
	/**
	 * 字符串截取
	 * @param oldString 需要截取的字符串
	 * @param beginIndex 起始位置
	 * @param endIndex 结束位置
	 * @param splitstr 分割字符串
	 * @param substrtype 截取类型：begin:只截取前段部分;beginend:截图区间部分，由用户定义;beginlen:截图区间部分，从开始位置直接截取到末尾
	 * @return 返回截取后的字符串
	 */
	public static String substrString(String oldString,int beginIndex,int endIndex,String substrtype){
		//定义返回对象
		String newString = null;
		//只截取前段部分
		if("begin".equals(substrtype)){
			//截取
			newString = oldString.substring(beginIndex);
		}
		//截图区间部分，由用户定义
		else if("beginend".equals(substrtype)){
			//截取
			newString = oldString.substring(beginIndex, endIndex);
		}
		//截图区间部分，从开始位置直接截取到末尾
		else if("beginlen".equals(substrtype)){
			//截取
			newString = oldString.substring(beginIndex, oldString.length());
		}
		//返回截取对象
		return newString;
	}

	/**
	 * 获取截取字符串所在位置
	 * @param oldString 需要截取的字符串
	 * @param splitstr 截取字符
	 * @param isEnd 是否从后往前截取，true:代表从后往前截取，false：从前往后截取
	 * @return 返回截取字符串所在位置
	 */
	public static int indexofString(String oldString,String splitstr,boolean isEnd){
		//定义返回变量
		int splitnum = 0;
		//判断是否从后往前截取，true:代表从后往前截取，false：从前往后截取
		if(isEnd){
			//从后往前截取
			splitnum = oldString.lastIndexOf(splitstr);
		}else{
			//从前往后截取
			splitnum = oldString.indexOf(splitstr);
		}
		//返回截取位置
		return splitnum;
	}
	
	/**
	 * list比较类
	 * @param list1 list1对象
	 * @param list2 list2对象
	 * @return
	 */
	public static List<String> list1ComparisonList2(List<String> list1,List<String> list2){
//		List<String> newlist = new ArrayList<String>();
		List<String> newlist = new ArrayList(Arrays.asList(new Object[list1.size()]));
		//对象赋值
		Collections.copy(newlist, list1);
//		newlist = newlist.c;
		//进行比对
		newlist.removeAll(list2);
		//返回对象
		return newlist;
	}
	
	/**
	 * //不考虑元素顺序是否相同 时的LIST比较相同的部分  
	 * @param list1 对象1
	 * @param list2 对象2
	 * @return 比较出相同部分对象
	 */ 
	public static List<String> getSameElementList(List<String> list1,List<String> list2){    
	    List<String> sameElementList = new ArrayList<String>();  
	    for(String tem : list1){    
	        if(list2.contains(tem)){//包含tem元素    
	            if(sameElementList == null){  
	              sameElementList = new ArrayList<String>();  
	            }  
	            sameElementList.add(tem);  
	        }    
	    }    
	    return sameElementList ;    
	}  
	
	/**
	 * 
	 * <p>
	 * Name: convertValue
	 * </p>
	 * <p>
	 * Title: 标题(数据空值装换)
	 * </p>
	 * <p>
	 * Description:描述(数据空值装换)
	 * </p>
	 * <p>
	 * 模块:
	 * </p>
	 * 
	 * @param object
	 * @return
	 * @author Administrator
	 * @version 1.0
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String convertValue(Object object) {
		String obj = "";
		try {
			if (object == null || "".equals(object)) {
				obj = "";
			} else {
				obj = object.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		String dateStr = "";
		if(date!=null&&!date.equals(""))
			dateStr = myFmt.format(date);
	    return dateStr;
	}

	public static String formatDate(Date date,String dateformat){
		SimpleDateFormat myFmt=new SimpleDateFormat(dateformat);   
		String dateStr = "";
		if(date!=null&&!date.equals(""))
			dateStr = myFmt.format(date);
	    return dateStr;
	}
	
	
	 public static String getDataType(Object obj) {
		    if (obj == null) {
		      return null;
		    }
		    String type = obj.getClass().getName();
		    int pos = type.lastIndexOf(".");
		    if (pos >= 0) {
		      type = type.substring(pos + 1);
		    }
		    return type;
		  }
	
	 /**
	     * 产生随机数数字字符串
	     * 注意产生的数字个数不能超过16位数
	     *  @param number 产生数字的个数
	     * @return 数字字符串
	     */
	    public static String randNumber(int num){
	    	Random random=new Random();
	    	String number=random.nextDouble()+"";
	    	if(num<16){
	    		number=number.substring(2,num+2);
	    	}else{
	    		number=number.substring(2);
	    	}
	    	return number;
	    }
	   
	    /**
	     * 
	     * @param key
	     * @param filedtypeMap
	     * @return
	     */
	    public static Object DBFiledType(String key,String value,HashMap<Object,String> filedtypeMap){
	    	String dbfiledtype = filedtypeMap.get(key);
	    	if(RegexValidate.StrNotNull(dbfiledtype)){
	    		if(dbfiledtype.equals("NUMBER")){
	    			return Integer.parseInt(value);
	    		}else if(dbfiledtype.equals("DATE")){
	    			return java.sql.Date.valueOf(value);
	    		}else if(dbfiledtype.equals("LONG")){
	    			return Long.parseLong(value);
	    		}
	    	}
	    	return value;
	    }
	 public static void main(String[] args) {
		 List list1 = new ArrayList();
		 list1.add("POSTNUMBER");
		 list1.add("SIGNINGNUMBER");
		 list1.add("CHECKOUTNUMBER");
		 
		 
		 List list2 = new ArrayList();
		 list2.add("POSTNUMBER");
		 list2.add("SIGNINGNUMBER");
		 list2.add("CHECKOUTNUMBER");
		 list2.add("LEASECONTRACTNUMBER");
		 list2.add("LEASECONTRACTAREA");
		 
		 List list3 = new ArrayList();
		 list3.add("POSTNUMBER");
		 list3.add("SIGNINGNUMBER");
		//获取删除部分
	    List dellist=  list1ComparisonList2(list1, list3);
	    System.out.println("删除部分 " +dellist);
		 // 获取增加部分
		List addlist=  list1ComparisonList2(list1,list2);
		System.out.println("增加部分 " +addlist);
	}
}
