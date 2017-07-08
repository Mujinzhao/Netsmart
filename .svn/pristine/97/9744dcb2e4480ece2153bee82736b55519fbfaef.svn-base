package datacvg.parse.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;

public class parserHTML {
	private static final Logger parser = Logger.getLogger(parserHTML.class);
	
	/**
	 * 通过正则获取页面源码中的总页数
	 * @param extractRegex
	 * @param htmlSource
	 * @return
	 */
	public int getPageToHtml(String extractRegex,String htmlSource){
		int totalpage =0;
		String pagestr=null;
		String outRegex="<.*?>";
		if(extractRegex != null && !extractRegex.trim().equals("")){
			extractRegex = extractRegex.trim();
			Pattern pattern = Pattern.compile(extractRegex, Pattern.DOTALL+Pattern.CASE_INSENSITIVE+Pattern.UNICODE_CASE);
			Matcher matcher = pattern.matcher(htmlSource);
			if(matcher.find())
				pagestr = matcher.group(1);
		}
		
		if (pagestr != null && outRegex != null) {
			String[] array = outRegex.split("\\|\\|");
			for (String s:array) {
				pagestr = pagestr.replaceAll("(?i)"+s, "").trim();
			}
			totalpage = Integer.parseInt(pagestr);
		}
		return totalpage;
	}
	public static void main(String[] args) {
		
		String morestruts="1";
		String content="";
		String encoding="";
		parser.info("sfddddddddddddddddddd");
		Parser parser =  Parser.createParser(content,encoding);
//		NodeList nodeList = parser.extractAllNodesThatMatch(filter);
//		parser.extractAllNodesThatMatch(filter)
		
//		String structuredid = morestruts=="1"?WDWUtil.getSeqNextval():"2";
//		System.out.println(structuredid);
		String taskCode=null;
		String name = "共90页";
		//"(^.*?)(_.*)?$"
		Pattern pattern = Pattern.compile( "共(.*?)页");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			taskCode = matcher.group(1);
		}
		System.out.println(taskCode);
		
//		String htmlsource = " <td align=\"right\" class=\"f12a6\" style=\"color: yellow; font-size: 12px;\">"+
//                                     "(<span id=\"ess_ctr5964_FDCJY_ZPHT_Dateyear\">2014</span>年<span id=\"ess_ctr5964_FDCJY_ZPHT_DateMonth\">12</span>月统计结果)&nbsp;&nbsp;";
//	    String regexstr ="";
//		String regex ="\\(<span id=\"ess_ctr5964_FDCJY_ZPHT_Dateyear\">(.*?)统计结果\\)";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(htmlsource);
//		if (matcher.find()) {
//			regexstr = matcher.group(1);
//		}
//		System.out.println(regexstr);
		
//		String htmlsource1 = "<span id=\"ess_ctr5964_FDCJY_ZPHT_Dateyear\">";
//		String regexstr1 ="";
//		Pattern pattern1 = Pattern.compile(" |\\s");
//		Matcher matcher1 = pattern1.matcher(htmlsource1);
//		if (matcher1.find()) {
//		regexstr1 = matcher1.group(1);
//		}
//		System.out.println(regexstr1);
//			}
	}

}
