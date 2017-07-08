package core.spider.pagedetail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.spider.entity.RuleTemplate;
import core.spider.entity.TagruleTemplate;
import core.spider.fun.DatePattern;
import core.util.HtmlFilter;
import core.util.RegexValidate;



public class RuleExtract {
	private HashMap<String, List<String>> taskNotNullField;//整个任务对应的非空字段信息（包括多个结构）
	private List<String> structNotNullField;//某个结构对应的非空字段信息
	private List<RuleTemplate> structuredRuleTemplete;//某个结构的网页前后缀模板
	private HashMap<String, List<RuleTemplate>> taskRuleTemplate;//整个任务的前后缀模板（包括多个结构）
	public  HashMap<String, String> ruleResult;//最终的解析结果
	public String tableName;//最终存储的数据库名称
	
	public RuleTemplate ruletemplate;//单个规则解析模板
	
	public RuleExtract(){
		
	}
	
	public RuleExtract(List<RuleTemplate> structuredRuleTemplete,
			List<String> structNotNullField){
		this.structuredRuleTemplete = structuredRuleTemplete;
		this.structNotNullField = structNotNullField;
	}
	
	public RuleExtract(RuleTemplate ruletemplate,List<String> structNotNullField) {
		this.ruletemplate = ruletemplate;
		this.structNotNullField = structNotNullField;
	}

	public RuleExtract(HashMap<String, List<RuleTemplate>> taskRuleTemplate,
			HashMap<String, List<String>> taskNotNullField){
		this.taskRuleTemplate = taskRuleTemplate;
		this.taskNotNullField = taskNotNullField;
	}
	
	
	/**
	 * 文章信息抽取
	 * @param htmlSource  文章网页源码
	 */
	public void getInfo(String htmlSource,String parsertype){
		HashMap<String, String> parserResult = null;
		if (structuredRuleTemplete == null || htmlSource == null || htmlSource.equals("")) {
			return ;
		}
		
		parserResult = new HashMap<String, String>();
		int count = 0;
		for(RuleTemplate rule : structuredRuleTemplete){
			String value = getFieldCon(rule, htmlSource);
			// 去除HTML标签
			value = HtmlFilter.filterHTML(value);
//			value.replaceAll("<.*?>", "");
//			value.replaceAll("&nbsp;", "");
			if (value == null) {
				continue;
			}
			tableName = rule.getSavetable();
			parserResult.put(rule.getFieldName(), value);
			// 指定该模板非空字段 并且该模板中存在该字段名称
			if (structNotNullField != null && structNotNullField.contains(rule.getFieldName())) {
				count++;
			}
		}
		//对于只有前后缀模板的 通过判断指定的几个字段是否为空来判断解析是否成功
		if(structuredRuleTemplete.get(0).getTagRuleId().equals("0") && parsertype.equals("0")){
			if (structNotNullField == null || count == structNotNullField.size()) {
				ruleResult = parserResult;
			}
		}
		//对于综合两种方式的 判断条件比较严格（判断每个字段是否解析）
		else if(structuredRuleTemplete.get(0).getTagRuleId().equals("2") || parsertype.equals("2")){
			if (parserResult.size() == structuredRuleTemplete.size()) {
				ruleResult = parserResult;
			}
		}
	}
	
	/**
	 * 根据前后缀配置信息获取目标信息
	 * @param ruleTemplete  具体某个字段的前后缀配置信息
	 * @param htmlSource    网页源码
	 * @return              目标信息
	 */
	public String getFieldCon(RuleTemplate ruleTemplete,String htmlSource){
		if (ruleTemplete == null || htmlSource == null) {
			return null;
		}
		String fieldStr = null;
		String outRegex = ruleTemplete.getOutRegex();
		
		String prefix = ruleTemplete.getPrefix();
		String suffix = ruleTemplete.getSuffix();
		String extractRegex = ruleTemplete.getExtractregex();
		System.out.println("1111"+extractRegex);
		// 正则表达式提取匹配内容
		if(extractRegex != null && !extractRegex.trim().equals("")){
			extractRegex = extractRegex.trim();
			System.out.println("2222"+extractRegex);
			Pattern pattern = Pattern.compile(extractRegex, Pattern.DOTALL+Pattern.CASE_INSENSITIVE+Pattern.UNICODE_CASE);
			Matcher matcher = pattern.matcher(htmlSource);
			
			if(matcher.find()){
				fieldStr = matcher.group();
				System.out.println(fieldStr);
			}
			
		}else {
			if (prefix == null || suffix == null || 
					prefix.trim().equals("") || suffix.trim().equals("")) {
				return null;
			}
//			System.out.println(htmlSource);
			int start = htmlSource.toLowerCase().indexOf(prefix.toLowerCase());
			int startIndex = start + prefix.length();
			int end = htmlSource.indexOf(suffix,startIndex);
			if (start >= 0 && end > 0) {
				fieldStr = htmlSource.substring(startIndex,end);
			}
		}
//		fieldStr = fieldStr.replaceAll("\\s*", "");
		if (fieldStr != null && outRegex != null) {
			String[] array = outRegex.split("\\|\\|");
			for (String s:array) {
//				fieldStr = fieldStr.replaceAll("(?i)"+s, "").trim();
				fieldStr = fieldStr.replaceAll(s, "").trim();
			}
		}
		System.out.println(fieldStr);
		return fieldStr;
	}

//    public void getMateFieldCon(String htmlSource){
//    	
//    	HashMap<String, String> parserResult = null;
//		if (structuredRuleTemplete == null || htmlSource == null || htmlSource.equals("")) {
//			return ;
//		}
//		
//		parserResult = new HashMap<String, String>();
//		int count = 0;
//		for(RuleTemplate rule : structuredRuleTemplete){
////			System.out.println(htmlSource);
//			String value = getFieldCon(rule, htmlSource);
//			if (value == null) {
//				continue;
//			}
//			tableName = rule.getSavetable();
//			parserResult.put(rule.getFieldName(), value);
//			if (structNotNullField != null && structNotNullField.contains(rule.getFieldName())) {
//				count++;
//			}
//		}
//		//对于只有前后缀模板的 通过判断指定的几个字段是否为空来判断解析是否成功
//		if (structuredRuleTemplete.get(0).getTagRuleId().equals("0")) {
//			if (structNotNullField == null || count == structNotNullField.size()) {
//				//System.out.println("判断指定字段全部解析");
//				ruleResult = parserResult;
//			}
//		}
//		//对于综合两种方式的 判断条件比较严格（判断每个字段是否解析）
//		else if(structuredRuleTemplete.get(0).getTagRuleId().equals("0")){
//			if (parserResult.size() == structuredRuleTemplete.size()) {
//				//System.out.println("判断模版中的字段全部解析");
//				ruleResult = parserResult;
//			}
//		}
//    }
    
    
    /**
     * 元搜索解析处理
     * @param ruletemplateList
     * @param htmlSource
     * @return
     */
    public void getMateFieldCon(String htmlSource){
    	HashMap<String, String> parserResult = null;
		if (structuredRuleTemplete == null || htmlSource == null || htmlSource.equals("")) {
			return ;
		}
		parserResult = new HashMap<String, String>();
		for(RuleTemplate rule : structuredRuleTemplete){
			String value = "";
			// 判断元搜索发布时间提取 除微信外
			if(rule.getFieldName().equals("PUBDATE")){
				String pubdate = getFieldCon(rule, htmlSource);
				// 提取的发布时间内容为正整数  
				if(RegexValidate.isINTEGER_NEGATIVE(pubdate)){
					//调用通过正整数提取日期
					value = new DatePattern().vrTimeHandle552(pubdate);
				}else{
					// 提取的发布时间内容为String 类型
					Date date = new DatePattern().getDate(pubdate);
					if (date == null) {
						continue;
					}
					value = date.toString();
				}
			}else{
			    value = getFieldCon(rule, htmlSource);
			}
			if (value == null) {
				continue;
			}
			parserResult.put(rule.getFieldName(),value);
			
		}
		 //对于综合两种方式的 判断条件比较严格（判断每个字段是否解析）
		 if(structuredRuleTemplete.get(0).getTagRuleId().equals("4")){
			if (parserResult.size() == structuredRuleTemplete.size()) {
				//System.out.println("判断模版中的字段全部解析");
				ruleResult = parserResult;
			}
		 }
		
    }
    
    /**
     * 元搜索解析处理
     * @param ruletemplateList
     * @param htmlSource
     * @return
     */
    public HashMap<String, String>  getMateFieldCon(List<RuleTemplate>  ruletemplateList,String htmlSource){
    	HashMap<String, String> parserResult = null;
		if (ruletemplateList == null || htmlSource == null || htmlSource.equals("")) {
			return parserResult;
		}
		parserResult = new HashMap<String, String>();
		for(RuleTemplate rule : ruletemplateList){
			String value = "";
			// 判断元搜索发布时间提取 除微信外
			if(rule.getFieldName().equals("PUBDATE")){
				String pubdate = getFieldCon(rule, htmlSource);
				// 提取的发布时间内容为正整数  
				if(RegexValidate.isINTEGER_NEGATIVE(pubdate)){
					//调用通过正整数提取日期
					value = new DatePattern().vrTimeHandle552(pubdate);
				}else{
					// 提取的发布时间内容为String 类型
					Date date = new DatePattern().getDate(pubdate);
					if (date == null) {
						continue;
					}
					value = date.toString();
				}
			}else{
			    value = getFieldCon(rule, htmlSource);
			}
			if (value == null) {
				continue;
			}
			parserResult.put(rule.getFieldName(),value);
		}
		return parserResult;
		
	}
	public static void main(String[] args)
	{
		String htmlSource = "再审申请人（一审原告、反诉被告，二审被上诉人）：王勇，1961年1月9日出生。委托代理人：张礼，海南天皓律师事务所律师。被申请人（一审被告、反诉原告，二审上诉人）：衡阳市长江建设工程有限责任公司，住所地湖南省衡阳市石鼓区常胜中路希望广场B座A栋1602-1604室。法定代表人：查剑锋，该公司董事长。委托代理人：王涛，北京市雍泽律师事务所律师。被申请人（一审被告、二审上诉人）：澄迈金门水电有限公司，住所地海南省澄迈县金江镇文明路101号。法定代表人：王韦舜，该公司董事长。委托代理人：王涛，北京市雍泽律师事务所律师。被申请人（一审被告）：王汉昌，1960年3月17日出生。";
		String extractRegex = "被申请人.*?。";
		Pattern pattern = Pattern.compile(extractRegex, Pattern.DOTALL+Pattern.CASE_INSENSITIVE+Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(htmlSource);
		matcher.reset();
		if(matcher.find()){
			System.out.println(matcher.group());
		}
	}
}
