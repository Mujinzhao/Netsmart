package core.spider.pagedetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.datacvg.crawler.general.ie.GetNodebyAttr;

import core.spider.entity.TagBean;
import core.spider.entity.TagruleTemplate;
import core.spider.fun.HtmlReader;
import core.util.ContentUtil;
import core.util.RegexValidate;

public class TagExtract {
	private HashMap<String, TagruleTemplate> tagTemplatehashmap;// 整个任务的标签模板（包括多结构）
	private TagruleTemplate tagTemplate;
	public List<HashMap<String, String>> tagResultList;// 最终的解析结果
	public String tableName;// 最终存储的数据库名称

	public TagExtract() {

	}

	public TagExtract(HashMap<String, TagruleTemplate> tagTemplatehashmap) {
		this.tagTemplatehashmap = tagTemplatehashmap;
	}
	
	public TagExtract(TagruleTemplate tagTemplate) {
		this.tagTemplate = tagTemplate;
	}

	/**
	 * 标签解析
	 * @param taskcode
	 * @param html
	 * @param encode
	 * @param rowtocell
	 * @return
	 */
	public String getInfo(String taskcode, String html, String encode,String rowtocell) {
		Parser parser = null;
		String structuredCode = null;
		if (tagTemplate == null) {
			return null;
		}
		try {
			parser = Parser.createParser(html, encode);
			parser.setEncoding(encode);
			TagruleTemplate tagRule = null;

			tagResultList = new ArrayList<HashMap<String, String>>();

			tagResultList.clear();
			tagRule = tagTemplate;
			tableName = tagRule.getSavetable();
			structuredCode = getTagInfo(taskcode, tagRule, parser,rowtocell);
			
			parser.reset();
			if (structuredCode != null) {// 通过判断多结构编号是否为空来判断解析是否成功
				return structuredCode;
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return structuredCode;
	}

	/**
	 * 元搜索解析
	 * @param taskcode
	 * @param html
	 * @param encode
	 * @param ruleex
	 * @return
	 */
	public void getInfo(String html,String encode, RuleExtract ruleex) {
		Parser parser = null;
		if (tagTemplate == null) {
			return ;
		}
		try {
			parser = Parser.createParser(html, encode);
			parser.setEncoding(encode);
			TagruleTemplate tagRule = null;
	
			tagResultList = new ArrayList<HashMap<String, String>>();
	
			tagResultList.clear();
			tagRule = tagTemplate;
			tableName = tagRule.getSavetable();
			parserMeta(tagRule,parser,ruleex);
			parser.reset();
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 标签模板解析方法
	 * 
	 * @param URL
	 * @return 多结构编号
	 */
	public String getInfo(String html, String encode) {
		Parser parser = null;
		String structuredCode = null;
		if (tagTemplate == null) {
			return null;
		}

		try {
			// parser = new Parser(filePath);
			parser = Parser.createParser(html, encode);
			parser.setEncoding(encode);// ?????????????????????????
			TagruleTemplate tagRule = null;

			tagResultList = new ArrayList<HashMap<String, String>>();

			for (Map.Entry<String, TagruleTemplate> m : tagTemplatehashmap.entrySet()) {// 循环每个结构模板
				tagResultList.clear();
				tagRule = m.getValue();
				tableName = tagRule.getSavetable();
				structuredCode = getTagInfo(m.getKey(), tagRule, parser,"false");
				parser.reset();
				if (structuredCode != null) {// 通过判断多结构编号是否为空来判断解析是否成功
					return structuredCode;
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return structuredCode;
	}

	/**
	 * 解析获取标签值
	 * @param key 多结构编号
	 * @param tagRule 标签模板
	 * @param parser html 解析操作类
	 * @param rowtocell 行转列标识
	 * @return
	 */
	public String getTagInfo(String key, TagruleTemplate tagRule, Parser parser,String rowtocell) {
		StringBuffer parserdb = null;
		if(tagRule==null || parser==null){
			return null;
		}
		String structuredCode = null;
		HashMap<String, String> rowDataList = null;
		String[] glnumarry = null;
		int glnumber =0;
		TagBean tagBean = tagRule.getTagBean();
		TagBean childTag = tagBean.getChildTag();
		TagBean grandTag = childTag.getChildTag();
		HashMap<Integer, String> relationMap = tagRule.getRelationMap();
		//获取第一级标签值List
		NodeList tableList = GetNodebyAttr.getNodeByAttribute(parser, tagBean.getTagName(),
				tagBean.getTagAttr(),tagBean.getTagAttrVal());
		if (tableList == null) {
			return null;
		}
		String begindexs = tagBean.getBegindexs();
		String endindexs = tagBean.getEndindexs();
		
		String[] begindexarry = begindexs.split("[,]");
		String[] endindexarry = endindexs.split("[,]");
		
		if(begindexarry.length == begindexarry.length){
			for(int index = 0;index<begindexarry.length;index++){
				parserdb = new StringBuffer();
				int begindex = Integer.parseInt(begindexarry[index]);
				int endindex = Integer.parseInt(endindexarry[index]);
				for(int i = begindex; i < (endindex == 0?tableList.size():endindex);i++){
					Node tableNode = tableList.elementAt(i);
					//获取第二级标签值List
					NodeList trList = GetNodebyAttr.getNodeByAttribute(tableNode, childTag.getTagName(), childTag.getTagAttr(), childTag.getTagAttrVal());
					if (trList == null) {
						return null;
					}	
					String childbegindexs = childTag.getBegindexs();
					String childendindexs = childTag.getEndindexs();
					String[] childbegindexarry = childbegindexs.split("[,]");
					String[] childendindexarry = childendindexs.split("[,]");
					
					int childbegindex = Integer.parseInt(childbegindexarry[index]);
					int childendindex = Integer.parseInt(childendindexarry[index]); 
					
					for(int j = childbegindex; 
							j < (childendindex == 0 ? trList.size():childendindex);
							j++){
						rowDataList = new HashMap<String, String>();
						boolean glflag=false;
						if(RegexValidate.StrNotNull(childTag.getGlcellrows())){
							// 过滤需要过了的行数据
							glnumarry = childTag.getGlcellrows().split("[,]");
							for(int g = 0; g<glnumarry.length;g++){
								glnumber = Integer.parseInt(String.valueOf(glnumarry[g])==null?"0":glnumarry[g]);
								if(j == glnumber){
								  glflag=true;
								  break;
								}
							}
						}
						if(!glflag){
							Node trNode = trList.elementAt(j);
							//获取第三级标签值List
							NodeList tdList = GetNodebyAttr.getNodeByAttribute(trNode, grandTag.getTagName(), grandTag.getTagAttr(), grandTag.getTagAttrVal());
							if (tdList == null) {
								continue;
							}
						
						
						String grandbegindexs = grandTag.getBegindexs();
						String grandendindexs = grandTag.getEndindexs();
						String[] grandbegindexarry = grandbegindexs.split("[,]");
						String[] grandendindexarry = grandendindexs.split("[,]");
						
						int grandbegindex = Integer.parseInt(grandbegindexarry[index]);
						int grandendindex = Integer.parseInt(grandendindexarry[index]); 
						String spestr = "|";
						String savevalue="\n";
						//循环获取最终的解析数据
						for(int k = grandbegindex; k < (grandendindex == 0 ? tdList.size():grandendindex);k++){
							Node tdNode = tdList.elementAt(k);
							String tvalue = tdNode.toPlainTextString().trim();
							if(RegexValidate.StrNotNull(relationMap.get(k))){
								rowDataList.put(relationMap.get(k),tvalue);
							}
							if(ContentUtil.StrNotNull(tvalue)){
								 savevalue +=tvalue.trim()+"∴";
							}else{
								 savevalue +="0"+"∴";
							}
//							
						}
						// 解析字段值，跟非空字段设置值相等
						if (rowDataList.size() == relationMap.size() && !rowtocell.equals("true"))
						{
							structuredCode = key;
							tagResultList.add(rowDataList);
						}
						parserdb.append(savevalue.trim()+spestr);
					}
				  }
				}
				if(RegexValidate.StrNotNull(rowtocell) && rowtocell.equals("true")){
					List<HashMap<String, String>> resulttmp = new ContentUtil().getTableParserData(parserdb.toString(),relationMap);
					tagResultList.addAll(resulttmp);
					structuredCode = key;
//					resultList =  new ContentUtil().getTableParserData(exceldb.toString(),relationMap);
				}
			}
		}
		return structuredCode;
	}
	
	
	public String getTagInfo(String key, TagruleTemplate tagRule, Parser parser) {
		String structuredCode = null;
		HashMap<String, String> rowDataList = null;
		TagBean tagBean = tagRule.getTagBean();
		TagBean childTag = tagBean.getChildTag();
		TagBean grandTag = childTag.getChildTag();
		HashMap<Integer, String> relationMap = tagRule.getRelationMap();
		// 获取第一级标签值List
		NodeList tableList = GetNodebyAttr.getNodeByAttribute(parser,
				tagBean.getTagName(), tagBean.getTagAttr(),
				tagBean.getTagAttrVal());
		if (tableList == null) {
			return null;
		}
		for (int i = tagBean.getBegIndex(); i < (tagBean.getEndIndex() == 0 ? tableList
				.size() : tagBean.getEndIndex()); i++) {
			Node tableNode = tableList.elementAt(i);
			// 获取第二级标签值List
			NodeList trList = GetNodebyAttr.getNodeByAttribute(tableNode,
					childTag.getTagName(), childTag.getTagAttr(),
					childTag.getTagAttrVal());
			if (trList == null) {
				return null;
			}
			for (int j = childTag.getBegIndex(); j < (childTag.getEndIndex() == 0 ? trList
					.size() : childTag.getEndIndex()); j++) {
				rowDataList = new HashMap<String, String>();
				Node trNode = trList.elementAt(j);
				// 获取第三级标签值List
				// System.out.println(trNode+"--->"+
				// grandTag.getTagName()+"--->"+ grandTag.getTagAttr()+"--->"+
				// grandTag.getTagAttrVal());
				NodeList tdList = GetNodebyAttr.getNodeByAttribute(trNode,
						grandTag.getTagName(), grandTag.getTagAttr(),
						grandTag.getTagAttrVal());
				if (tdList == null) {
					continue;
				}
				// 循环获取最终的解析数据
				for (int k = grandTag.getBegIndex(); k < (grandTag
						.getEndIndex() == 0 ? tdList.size() : grandTag
						.getEndIndex()); k++) {
					Node tdNode = tdList.elementAt(k);
					String data = tdNode.toPlainTextString().trim();
					data = data.replace("&nbsp;", " ");// 针对大麦网时间字段中&nbsp;的情况
					rowDataList.put(relationMap.get(k), data);
				}
				if (rowDataList.size() == relationMap.size()) {
					structuredCode = key;
					tagResultList.add(rowDataList);

				} else {
					structuredCode = null;
				}
			}
		}
		return structuredCode;
	}

	/**
	 * 验证
	 * 
	 * @param tagBeans
	 *            标签模版Bean 层级
	 * @param relationMap
	 *            //解析字段与表中列的对应下标
	 * @param parser
	 * @return
	 */
	public List<HashMap<String, String>> test(TagruleTemplate tagBeans,
			HashMap<Integer, String> relationMap, Parser parser) {
		if (tagBeans == null || relationMap == null || parser == null) {
			return null;
		}
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> rowDataList = null;
		TagBean tagBean = tagBeans.getTagBean();
		TagBean childTag = tagBean.getChildTag();
		TagBean grandTag = childTag.getChildTag();
		// 获取第一级标签值List
		NodeList tableList = GetNodebyAttr.getNodeByAttribute(parser,
				tagBean.getTagName(), tagBean.getTagAttr(),
				tagBean.getTagAttrVal());
		if (tableList == null) {
			return null;
		}
		for (int i = tagBean.getBegIndex(); i < (tagBean.getEndIndex() == 0 ? tableList
				.size() : tagBean.getEndIndex()); i++) {
			Node tableNode = tableList.elementAt(i);
			// 获取第二级标签值List
			NodeList trList = GetNodebyAttr.getNodeByAttribute(tableNode,
					childTag.getTagName(), childTag.getTagAttr(),
					childTag.getTagAttrVal());
			if (trList == null) {
				return null;
			}
			for (int j = childTag.getBegIndex(); j < (childTag.getEndIndex() == 0 ? trList
					.size() : childTag.getEndIndex()); j++) {
				rowDataList = new HashMap<String, String>();
				Node trNode = trList.elementAt(j);
				// 获取第三级标签值List
				NodeList tdList = GetNodebyAttr.getNodeByAttribute(trNode,
						grandTag.getTagName(), grandTag.getTagAttr(),
						grandTag.getTagAttrVal());
				if (tdList == null) {
					continue;
				}
				// 循环获取最终的解析数据
				for (int k = grandTag.getBegIndex(); k < (grandTag
						.getEndIndex() == 0 ? tdList.size() : grandTag
						.getEndIndex()); k++) {
					Node tdNode = tdList.elementAt(k);
					rowDataList.put(relationMap.get(k), tdNode
							.toPlainTextString().trim());
				}
				if (rowDataList.size() == relationMap.size()) {
					resultList.add(rowDataList);
				}
			}
		}
		return resultList;
	}

	/**
	 * 验证
	 * 
	 * @param tagBeans
	 *            标签模版Bean 层级
	 * @param relationMap
	 *            //解析字段与表中列的对应下标
	 * @param parser
	 * @return
	 */
	public String test(TagruleTemplate tagBeans,
			HashMap<Integer, String> relationMap, Parser parser,
			String rowtocell) {
		StringBuffer exceldb = new StringBuffer();
		if (tagBeans == null || relationMap == null || parser == null) {
			return null;
		}
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> rowDataList = null;
		TagBean tagBean = tagBeans.getTagBean();
		TagBean childTag = tagBean.getChildTag();
		TagBean grandTag = childTag.getChildTag();

		// 获取第一级标签值List
		NodeList tableList = GetNodebyAttr.getNodeByAttribute(parser,
				tagBean.getTagName(), tagBean.getTagAttr(),
				tagBean.getTagAttrVal());
		if (tableList == null) {
			return null;
		}
		for (int i = tagBean.getBegIndex(); i < (tagBean.getEndIndex() == 0 ? tableList
				.size() : tagBean.getEndIndex()); i++) {
			Node tableNode = tableList.elementAt(i);
			// 获取第二级标签值List
			NodeList trList = GetNodebyAttr.getNodeByAttribute(tableNode,
					childTag.getTagName(), childTag.getTagAttr(),
					childTag.getTagAttrVal());
			if (trList == null) {
				return null;
			}

			for (int j = childTag.getBegIndex(); j < (childTag.getEndIndex() == 0 ? trList
					.size() : childTag.getEndIndex()); j++) {
				rowDataList = new HashMap<String, String>();
				Node trNode = trList.elementAt(j);
				// 获取第三级标签值List
				NodeList tdList = GetNodebyAttr.getNodeByAttribute(trNode,
						grandTag.getTagName(), grandTag.getTagAttr(),
						grandTag.getTagAttrVal());
				if (tdList == null) {
					continue;
				}
				String spestr = "|";
				String savevalue = "\n";
				// 循环获取最终的解析数据
				for (int k = grandTag.getBegIndex(); k < (grandTag
						.getEndIndex() == 0 ? tdList.size() : grandTag
						.getEndIndex()); k++) {
					Node tdNode = tdList.elementAt(k);
					// System.out.println(tdNode.toPlainTextString().trim());
					rowDataList.put(relationMap.get(k), tdNode
							.toPlainTextString().trim());
					String tvalue = tdNode.toPlainTextString().trim();
					if (ContentUtil.StrNotNull(tvalue)) {
						savevalue += tvalue.trim() + "∴";
					} else {
						savevalue += "0" + "∴";
					}
				}
				exceldb.append(savevalue.trim() + spestr);
				if (rowDataList.size() == relationMap.size()) {
					resultList.add(rowDataList);
				}
			}

		}
		if (rowtocell.equals("true")) {
			new ContentUtil().getTableParserData(exceldb.toString(),
					relationMap);
		} else {
			tagResultList.add(rowDataList);
		}
		return null;

	}
	
	/**
	 * 验证
	 * @param tagBeans    标签模版Bean  层级
	 * @param relationMap //解析字段与表中列的对应下标
	 * @param parser
	 * @return
	 */
	public List<HashMap<String, String>> test(TagBean[] tagBeans,
			HashMap<Integer, String> relationMap,
			Parser parser,String rowtocell){
		StringBuffer exceldb = new StringBuffer();
		if(tagBeans==null || tagBeans.length<3 || relationMap==null || parser==null){
			return null;
		}
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>() ;
		HashMap<String, String> rowDataList = null;
		String[] glnumarry = null;
		int glnumber =0;
		TagBean tagBean =  tagBeans[0];
		TagBean childTag = tagBeans[1];
		TagBean grandTag = tagBeans[2];
		
	
		//获取第一级标签值List
		NodeList tableList = GetNodebyAttr.getNodeByAttribute(parser, tagBean.getTagName(),
				tagBean.getTagAttr(),tagBean.getTagAttrVal());
		if (tableList == null) {
			return null;
		}
		for (int i = tagBean.getBegIndex(); i < (tagBean.getEndIndex() == 0?tableList.size():tagBean.getEndIndex());i++){
			Node tableNode = tableList.elementAt(i);
			//获取第二级标签值List
			NodeList trList = GetNodebyAttr.getNodeByAttribute(tableNode, childTag.getTagName(), childTag.getTagAttr(), childTag.getTagAttrVal());
			if (trList == null) {
				return null;
			}
			
			for(int j = childTag.getBegIndex(); 
					j < (childTag.getEndIndex() == 0 ? trList.size():childTag.getEndIndex());
					j++){
				rowDataList = new HashMap<String, String>();
				boolean glflag=false;
				if(RegexValidate.StrNotNull(childTag.getGlcellrows())){
					// 过滤需要过了的行数据
					glnumarry = childTag.getGlcellrows().split("[,]");
					for(int g = 0; g<glnumarry.length;g++){
						glnumber = Integer.parseInt(String.valueOf(glnumarry[g])==null?"0":glnumarry[g]);
						if(j == glnumber){
						  glflag=true;
						  break;
						}
					}
				}
				if(!glflag){
					Node trNode = trList.elementAt(j);
					//获取第三级标签值List
					NodeList tdList = GetNodebyAttr.getNodeByAttribute(trNode, grandTag.getTagName(), grandTag.getTagAttr(), grandTag.getTagAttrVal());
					if (tdList == null) {
						continue;
					}
				
				String spestr = "|";
				String savevalue="\n";
				
				//循环获取最终的解析数据
				for(int k = grandTag.getBegIndex(); 
						k < (grandTag.getEndIndex() == 0 ? tdList.size():grandTag.getEndIndex());
						k++){
					Node tdNode = tdList.elementAt(k);
//					System.out.println(tdNode.toPlainTextString().trim());
					rowDataList.put(relationMap.get(k),tdNode.toPlainTextString().trim());
					String tvalue = tdNode.toPlainTextString().trim();
					if(ContentUtil.StrNotNull(tvalue)){
						 savevalue +=tvalue.trim()+"∴";
					}else{
						 savevalue +="0"+"∴";
					}
				}
				// 解析字段值，跟非空字段设置值相等
				if (rowDataList.size() == relationMap.size())
				{
					resultList.add(rowDataList);
				}
				exceldb.append(savevalue.trim()+spestr);
			}
		  }
		}
		if(RegexValidate.StrNotNull(rowtocell) && rowtocell.equals("true")){
			return new ContentUtil().getTableParserData(exceldb.toString(),
					relationMap);
		}
		
		else{
			return resultList;
		}
	}


	/**
	 * 一个table 解析多处数据源
	 * @param tagBeans 标签实体bean
	 * @param relationMap 非空字段
	 * @param parser  解析类
	 * @param rowtocell 列转行标识位
	 * @return
	 */
	public List<HashMap<String, String>> testmoreTable(TagBean[] tagBeans,
			HashMap<Integer, String> relationMap,
			Parser parser,String rowtocell){
		StringBuffer exceldb =null;
		if(tagBeans==null || tagBeans.length<3 || relationMap==null || parser==null){
			return null;
		}
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>() ;
		HashMap<String, String> rowDataList = null;
		String[] glnumarry = null;
		int glnumber =0;
		TagBean tagBean =  tagBeans[0];
		TagBean childTag = tagBeans[1];
		TagBean grandTag = tagBeans[2];
		
	
		//获取第一级标签值List
		NodeList tableList = GetNodebyAttr.getNodeByAttribute(parser, tagBean.getTagName(),
				tagBean.getTagAttr(),tagBean.getTagAttrVal());
		if (tableList == null) {
			return null;
		}
		
		//迭代循环页面table 标签信息
		String begindexs = tagBean.getBegindexs();
		String endindexs = tagBean.getEndindexs();
		
		String[] begindexarry = begindexs.split("[,]");
		String[] endindexarry = endindexs.split("[,]");
		
		if(begindexarry.length == begindexarry.length){
			for(int index = 0;index<begindexarry.length;index++){
				exceldb = new StringBuffer();
				int begindex = Integer.parseInt(begindexarry[index]);
				int endindex = Integer.parseInt(endindexarry[index]);
				for(int i = begindex; i < (endindex == 0?tableList.size():endindex);i++){
					Node tableNode = tableList.elementAt(i);
					//获取第二级标签值List
					NodeList trList = GetNodebyAttr.getNodeByAttribute(tableNode, childTag.getTagName(), childTag.getTagAttr(), childTag.getTagAttrVal());
					if (trList == null) {
						return null;
					}	
					String childbegindexs = childTag.getBegindexs();
					String childendindexs = childTag.getEndindexs();
					String[] childbegindexarry = childbegindexs.split("[,]");
					String[] childendindexarry = childendindexs.split("[,]");
					
					int childbegindex = Integer.parseInt(childbegindexarry[index]);
					int childendindex = Integer.parseInt(childendindexarry[index]); 
					
					for(int j = childbegindex; 
							j < (childendindex == 0 ? trList.size():childendindex);
							j++){
						rowDataList = new HashMap<String, String>();
						boolean glflag=false;
						if(RegexValidate.StrNotNull(childTag.getGlcellrows())){
							// 过滤需要过了的行数据
							glnumarry = childTag.getGlcellrows().split("[,]");
							for(int g = 0; g<glnumarry.length;g++){
								glnumber = Integer.parseInt(String.valueOf(glnumarry[g])==null?"0":glnumarry[g]);
								if(j == glnumber){
								  glflag=true;
								  break;
								}
							}
						}
						if(!glflag){
							Node trNode = trList.elementAt(j);
							//获取第三级标签值List
							NodeList tdList = GetNodebyAttr.getNodeByAttribute(trNode, grandTag.getTagName(), grandTag.getTagAttr(), grandTag.getTagAttrVal());
							if (tdList == null) {
								continue;
							}
						
						
						String grandbegindexs = grandTag.getBegindexs();
						String grandendindexs = grandTag.getEndindexs();
						String[] grandbegindexarry = grandbegindexs.split("[,]");
						String[] grandendindexarry = grandendindexs.split("[,]");
						
						int grandbegindex = Integer.parseInt(grandbegindexarry[index]);
						int grandendindex = Integer.parseInt(grandendindexarry[index]); 
						String spestr = "|";
						String savevalue="\n";
						//循环获取最终的解析数据
						for(int k = grandbegindex; k < (grandendindex == 0 ? tdList.size():grandendindex);k++){
							Node tdNode = tdList.elementAt(k);
//							System.out.println(tdNode.toPlainTextString().trim());
							String tvalue = tdNode.toPlainTextString().trim();
							if(RegexValidate.StrNotNull(relationMap.get(k))){
								rowDataList.put(relationMap.get(k),tvalue);
							}
							if(ContentUtil.StrNotNull(tvalue)){
								 savevalue +=tvalue.trim()+"∴";
							}else{
								 savevalue +="0"+"∴";
							}
						}
						// 解析字段值，跟非空字段设置值相等
						if (rowDataList.size() == relationMap.size()
								&& !RegexValidate.StrNotNull(rowtocell))
						{
							resultList.add(rowDataList);
						}
						exceldb.append(savevalue.trim()+spestr);
					}
				  }
				}
				if(RegexValidate.StrNotNull(rowtocell) && rowtocell.equals("true")){
					List<HashMap<String, String>> resulttmp = new ContentUtil().getTableParserData(exceldb.toString(),relationMap);
					resultList.addAll(resulttmp);
//					resultList =  new ContentUtil().getTableParserData(exceldb.toString(),relationMap);
				}
			}
//			if(RegexValidate.StrNotNull(rowtocell) && rowtocell.equals("true")){
//				return new ContentUtil().getTableParserData(exceldb.toString(),
//						relationMap);
//			}
		}
		else{
			return resultList;
		}
		return resultList;
		
	}

	
	public List<HashMap<String,String>> parserMeta(TagruleTemplate tagRule,
			Parser parser,
			RuleExtract ruleex){
		if(tagRule==null || ruleex==null){
			return null;
		}
		
		TagBean tagBeans = tagRule.getTagBean();
		//获取第一级标签值List
		NodeList divlist = GetNodebyAttr.getNodeByAttribute(parser, tagBeans.getTagName(),
				tagBeans.getTagAttr(),tagBeans.getTagAttrVal());
		if (divlist == null) {
			return null;
		}
		//迭代标签
		for(int i=0;i<divlist.size();i++){
			Node divNode = divlist.elementAt(i);
			String htmlSource = divNode.toHtml();
			// 解析前后缀规则
			ruleex.getMateFieldCon(htmlSource);
			tagResultList.add(ruleex.ruleResult);
		}
		return null;
		
	}
	
	public static void main(String[] args) throws ParserException,
			ParserException, ParseException {

		String url = "E:/1.html";
		TagBean[] tagList = new TagBean[3];
		TagBean tagBean = new TagBean();
		tagBean.setTagName("table");
		tagBean.setTagAttr("id");
		tagBean.setTagAttrVal("table_clf1");
		tagBean.setBegIndex(0);
		tagBean.setEndIndex(0);

		TagBean tagBean1 = new TagBean();
		tagBean1.setTagName("tr");
		tagBean1.setBegIndex(3);
		tagBean1.setEndIndex(0);
        tagBean1.setGlcellrows("8");
		
		TagBean tagBean2 = new TagBean();
		tagBean1.setTagName("td");
		tagBean1.setBegIndex(1);
		tagBean.setEndIndex(0);

		tagList[0] = tagBean;
		tagList[1] = tagBean1;
		tagList[2] = tagBean2;
		Date date = new SimpleDateFormat("yy-MM-dd").parse("2012-08-31");
		url = "D:\\gather\\162\\";
		String htmlSource = HtmlReader.readHtml(url);
		// Parser parser = new Parser().createParser(htmlSource, "utf-8");
		Parser parser = new Parser(url);
		parser.setEncoding("utf-8");
//		HashMap<Integer, String> relationMap = new HashMap<Integer, String>();
//		 new TagExtract().getTagInfo("162",tagList,parser,"false");
	}

}
