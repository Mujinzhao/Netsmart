package core.util;
import java.util.List;

import net.sf.json.JSONArray;


/**
 * Java对象转换为Json子串的接口
 * @author ZhangBo
 * @version 2012-8-14
 */
public class JsonGenerator
{
	
	
	/**
	 * 生成树的Json格式字符串
	 * 
	 * @param objList
	 * @return
	 * @author solo
	 * @version 2012-8-28
	 */
	public static String getJsonContent(List<?> objList) {
		String json = JSONArray.fromObject(objList).toString();
		System.out.println(json);
		return json;
	}

	
	public static String getObjectJsonContent(Object obj)
	{
		
		//生成Json串
		String json = JSONArray.fromObject(obj).toString();
		
		return json;
	}
	
}
