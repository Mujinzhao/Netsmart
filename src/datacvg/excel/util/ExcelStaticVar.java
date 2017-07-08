package datacvg.excel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import datacvg.excel.entity.ExcelRule;
import datacvg.excel.entity.ExcelStruct;
/**
 * Excel 解析全局数据集合
 * @author admin
 *
 */
public class ExcelStaticVar {

	//Excel模板信息 模板存储地址，模板ID
	public static HashMap<String,String> templatebean = new HashMap<String,String>();
	
	//Excel模板信息 模板解析路径，模板名称
	public static HashMap<String,String> filePath = new HashMap<String,String>();
		
	public static HashMap<String,HashMap<Object,String>> templatePath = new HashMap<String,HashMap<Object,String>>();
	
	// 解析任务列表集合 结构对象
	public static List<ExcelStruct> excelStructList = new ArrayList<ExcelStruct>();
	// 解析任务列表集合 模板对象 模板ID, 结构ID， 结构对象
	public static HashMap<String,HashMap<String,ExcelStruct>> excelStructMap = new HashMap<String,HashMap<String,ExcelStruct>>();
	
	//解析字段不为空(<结构编号,非空字段名称集合>)
	public static HashMap<String,List<String>> notNullField = new HashMap<String,List<String>>();
	//解析字段下标定义
	public static HashMap<String,HashMap<Object,String>> filedIndex = new HashMap<String,HashMap<Object,String>>();
	//解析结构自定义字段值 (<结构编号,自定义字段值>)
	public static HashMap<String,HashMap<Object, String>> customFieldVar = new HashMap<String,HashMap<Object, String>>();
	//解析字段类型定义
	public static HashMap<String,HashMap<Object,String>> filedType = new HashMap<String,HashMap<Object,String>>();
	//字段信息描述定义
	public static HashMap<String,HashMap<Object,String>> filedInfo = new HashMap<String,HashMap<Object,String>>();
	//解析结构规则(<模板编号,Excel模板对象>)
	public static HashMap<String, List<ExcelRule>> structparseRule = new  HashMap<String,List<ExcelRule>>();
	//解析文件大小集合
	public static HashMap<String,Long> lastTimeFileMap = new  HashMap<String,Long>();
	//解析文件错误集合
	public static List<String> parserrorList = new  ArrayList<String>();

}
