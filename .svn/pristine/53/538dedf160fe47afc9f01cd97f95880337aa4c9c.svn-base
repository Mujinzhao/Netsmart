package core.spider.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import datacvg.parse.entity.ParserInfo;

public class StaticVar {

	//解析字段不为空
	public static HashMap<String, HashMap<String, List<String>>> notNullField = new HashMap<String, HashMap<String,List<String>>>();
	//前后缀解析模板(<任务编号,<多结构编号,模板>>)
	public static HashMap<String, HashMap<String, List<RuleTemplate>>> ruleMap = new HashMap<String, HashMap<String,List<RuleTemplate>>>();
	//标签解析模板(<任务编号,<多结构编号,模板>>)
	public static HashMap<String, HashMap<String, TagruleTemplate>> tagruleMap = new HashMap<String, HashMap<String,TagruleTemplate>>();
	public static HashMap<String,HashMap<String,List<RuleTemplate>>> relateMap = new HashMap<String, HashMap<String,List<RuleTemplate>>>();
	public static HashMap<String, HashMap<String, List<TagBean>>> tagMap = new HashMap<String, HashMap<String,List<TagBean>>>();
	
	//解析任务
	public static List<ParserInfo> parserlist = new ArrayList<ParserInfo>();
	//解析任务(多结构编号,任务信息模板>>)
	public static HashMap<String, List<ParserInfo>> parserState = new HashMap<String,List<ParserInfo>>();
	
	// 解析任务对应解析目录编号 由于一个任务对应多种模板，每个模板对应一个目录（taskcode,目录集合）
	public static  HashMap<String, String> taskflie = new HashMap<String,String>();
	//解析验证文件源码内容集合
	public static  HashMap<String, String> htmlsource = new HashMap<String,String>();

	// 存储运行的线程集合 任务名称，线程对象集合
	public static  HashMap<String,HashMap<String,Boolean>> taskandthreadstate = new HashMap<String,HashMap<String,Boolean>>();
	//存储任务是否过滤URL排重 taske编号，状态 T表示需要  F表示不需要
	public static  HashMap<String,String> taskheavy = new HashMap<String,String>();
		
	
}
