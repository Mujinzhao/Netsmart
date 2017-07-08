package core.util;


/**
 * 工具类
 * @author wangchen
 *
 */
public class HtmlFilter {

	/**
	 * 过滤HTML中的字符 让其按HTML格式显示
	 * @param value
	 * @return
	 */
	public static String filterHTML(String value){
		if (value==null) {
			return "";
		}
		//缓冲对象
		StringBuffer buffer =new StringBuffer("");
		//循环遍历其中的字符
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i) ; //每次取得一个字符
			if (c=='<') {
				buffer.append("&lt;"); 
			}else if(c=='>'){
				buffer.append("&gt;");
			}else if(c=='"'){
				buffer.append("&quot;");
			}else if(c=='\r'){
				buffer.append("<br>");
			}else if(c=='\t'){
				buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			}else if(c==' '){
				buffer.append("&nbsp;");
			}else {
				buffer.append(c);
			}
		}
		//返回
		return buffer.toString();
	}
	
	public static void main(String [] args){
		String siteName="abc.b.c";
		String s=siteName.replaceAll("(http://|^)[^/]+", "");
		System.out.println(siteName.replaceAll("(http://|^)[^/]+", ""));
		
		System.out.println(siteName.substring(0,siteName.indexOf(s)));
	}
}
