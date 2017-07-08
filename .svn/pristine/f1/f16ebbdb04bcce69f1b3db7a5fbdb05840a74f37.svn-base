package core.spider.fun;


import java.util.Map;
import java.util.Map.Entry;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.datacvg.crawler.indexspider.fun.GetHtml;

/**
 * 此类用于htmlparser过滤出符合设置条件的节点
 */
public class GetNodebyAttr {
	private static final String ENCODING = "UTF-8";
	/**
	 * htmlparser过滤出同时符合所有条件的节点
	 * 
	 * @param parser
	 *            要过滤的parser
	 * @param tag
	 *            标签名，过滤条件
	 * @param atrAndMap
	 *            属性Map，过滤条件
	 * @return 符合的NodeList列表，如果过滤条件都是空，返回所有节点列表。可返回null
	 */
	public static NodeList getAndNode(Parser parser, String tag,
			Map<String, String> atrAndMap) {

		if (null == parser) {
			return null;
		}

		try {
			// 统计过滤条件个数，来创建filter数组
			int n = 0;
			int mapsize = 0;
			if (null != tag) {
				n++;
			}
			if (null != atrAndMap) {
				mapsize = atrAndMap.size();
				n += mapsize;
			}

			// 没有设置过滤条件
			if (n == 0) {
				return parser.extractAllNodesThatMatch(new AndFilter());
			}

			// 构造filters
			NodeFilter[] filters = new NodeFilter[n];
			int cur = 0;
			if (null != tag) {
				filters[cur] = new TagNameFilter(tag);
				cur++;
			}
			if (mapsize != 0) {
				for (Entry<String, String> entry : atrAndMap.entrySet()) {
					filters[cur] = new HasAttributeFilter(entry.getKey(), entry
							.getValue());
					cur++;
				}
			}

			NodeFilter filter = new AndFilter(filters);
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			return nodeList;
		} catch (ParserException e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * htmlparser过滤出符合组合条件的节点
	 * 
	 * @param parser
	 *            要过滤的parser
	 * @param tag
	 *            标签名，过滤条件
	 * @param atrAndMap
	 *            属性Map，过滤条件
	 * @return 符合的NodeList列表，如果过滤条件都是空，返回所有节点列表。可返回null
	 */
	public static NodeList getOrNode(Parser parser, String tag,
			Map<String, String> atrAndMap) {

		if (null == parser) {
			return null;
		}

		try {
			// 统计过滤条件个数，来创建filter数组
			int n = 0;
			int mapsize = 0;
			if (null != tag) {
				n++;
			}
			if (null != atrAndMap) {
				mapsize = atrAndMap.size();
				n += mapsize;
			}

			// 没有设置过滤条件
			if (n == 0) {
				return parser.extractAllNodesThatMatch(new AndFilter());
			}

			// 构造filters
			NodeFilter[] filters = new NodeFilter[2];
			int cur = 0;
			if (null != tag) {
				filters[cur] = new TagNameFilter(tag);
			}
			
			NodeFilter[] orFilters = new NodeFilter[mapsize];
			if (mapsize != 0) {
				for (Entry<String, String> entry : atrAndMap.entrySet()) {
					orFilters[cur] = new HasAttributeFilter(entry.getValue(), entry
							.getKey());
					cur++;
				}
			}
			NodeFilter orFilter = new OrFilter(orFilters);
			filters[1] = orFilter;

			NodeFilter filter = new AndFilter(filters);
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			return nodeList;
		} catch (ParserException e) {
//			e.printStackTrace();
			return null;
		}
	}

	/**
	 * htmlparser过滤出同时符合条件的列表NodeList
	 * 
	 * @param parser
	 *            要过滤的parser
	 * @param tag
	 *            标签名，过滤条件
	 * @param attr
	 *            属性名，过滤条件
	 * @param value
	 *            属性值，过滤条件
	 * @return 符合的NodeList列表。可返回null
	 */
	public static NodeList getNodeByAttribute(Parser parser, String tag,
			String attr, String value) {

		if (null == parser) {
			return null;
		}
		// Tag过滤
		NodeFilter filterTag = null;
		// 设定过滤filter
		NodeFilter filter = null;
		
		try {
		
			if (null != tag) {
				filterTag = new TagNameFilter(tag);
			}

			// attribute过滤
			NodeFilter filterAttr = null;
			if (null != attr && null != value) {
				filterAttr = new HasAttributeFilter(attr, value);
			}

		
			// 标签空
			if (null == filterTag) {
				// 属性也空
				if (null == filterAttr) {
					return null;
				} else {
					filter = new AndFilter(new NodeFilter[] { filterAttr });
				}
			}
			// 属性空
			else if (null == filterAttr) {
				filter = new AndFilter(new NodeFilter[] { filterTag });
			}
			// 都不空
			else {
				filter = new AndFilter(
						new NodeFilter[] { filterTag, filterAttr });
			}

			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			return nodeList;
		} catch (ParserException e) {
//			e.printStackTrace();
			try{
				GetHtml gethtml = 	new GetHtml();
				String content = gethtml.getHtmlFromConn(parser.getURL(),ENCODING);
				parser =  Parser.createParser(content,ENCODING);
				NodeList nodeList = parser.extractAllNodesThatMatch(filter);
				System.out.println(nodeList);
				return nodeList;
			}catch (Exception e1) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * htmlparser过滤出同时符合条件的列表NodeList
	 * 
	 * @param node
	 *            要过滤的Node
	 * @param tag
	 *            标签名，过滤条件
	 * @param attr
	 *            属性名，过滤条件
	 * @param value
	 *            属性值，过滤条件
	 * @return 符合的NodeList列表。可为null
	 */
	public static NodeList getNodeByAttribute(Node node, String tag,
			String attr, String value) {

		if (null == node) {
			return null;
		}

		NodeFilter filterTag = null;
		if (null != tag) {
			filterTag = new TagNameFilter(tag);
		}

		// attribute过滤
		NodeFilter filterAttr = null;
		if (null != attr && null != value) {
			filterAttr = new HasAttributeFilter(attr, value);
		}

		// 设定过滤filter
		NodeFilter filter = null;
		
		// 标签空
		if (null == filterTag) {
			// 属性也空
			if (null == filterAttr) {
				return null;
			} else {
				filter = new AndFilter(new NodeFilter[] { filterAttr });
			}
		}
		// 属性空
		else if (null == filterAttr) {
			filter = new AndFilter(new NodeFilter[] { filterTag });
		}
		// 都不空
		else {
			filter = new AndFilter(new NodeFilter[] { filterTag, filterAttr });
		}

		NodeList nodeList = new NodeList();
		node.collectInto(nodeList, filter);

		return nodeList;
	}
	/**
	 * 提取meta标签中name值为keytype的内容
	 * @param p  parser
	 * @param keytype 关键字
	 * @return
	 */
    public static String getMetaInfo(Parser p, String keytype){
        String metaInfo = "";
        try {
          NodeFilter nt = new NodeClassFilter(MetaTag.class) ;
          NodeList nodeList = p.parse(nt);
             for (int i = 0 ; i< nodeList.size(); i++) {
              MetaTag mt =(MetaTag) nodeList.elementAt(i) ;
              String cont  = mt.getAttribute("HTTP-EQUIV") ;

              if (cont!=null && cont.equalsIgnoreCase(keytype)) {
                metaInfo = mt.getAttribute("CONTENT");
                break;
              }
            }
        } catch (ParserException e) {
//          e.printStackTrace();
        }
        return metaInfo;
      }

	public static void main(String[] args) {
		String url = "http://www.bjjs.gov.cn/tabid/2167/default.aspx";
		try {
			Parser	parser = new Parser(url);
//			GetHtml gethtml = 	new GetHtml();
//			String content = gethtml.getHtmlFromConn(url,"utf-8");
//			parser =  parser.createParser(content,"utf-8");
			
			parser.setEncoding("utf-8");
			NodeList nodeList = getNodeByAttribute(parser, "table", "style", "border-collapse:collapse;");
			if(null != nodeList && nodeList.size()>0){
				Node node = nodeList.elementAt(0);
				System.out.println(replaceHTML(node.toHtml()).trim());
			}
		} catch (Exception e) {
			    e.printStackTrace();
				System.out.println("ERROR!");
		}
	}
	public static String replaceHTML(String str){
		str = str.replaceAll("<([^>])*>", "");
		return str;
	}
}
