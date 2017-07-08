package datacvg.gather.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 路径解析
 *
 */
public class GetLink {

	 public static String getLink(String link,String parentUrl) {
	    	if(link.indexOf("./")==0){
				if(parentUrl.charAt(parentUrl.length()-1)=='/')
					link=parentUrl+link.substring(2);
				else
					link=parentUrl+link.substring(1);
			}else if(link.indexOf("../")==0){
				int  li=parentUrl.lastIndexOf("/");
				String tmpStr=parentUrl.substring(0,li);
				li=tmpStr.lastIndexOf("/");
				tmpStr=tmpStr.substring(0,li+1);
				if(li>0)
					link=tmpStr+link.substring(3);
				else
					link=null;
			}else if(link.indexOf("http:")!=0&&link.indexOf("https:")!=0&&link.indexOf("/")<link.indexOf(".")){
				int  li=parentUrl.lastIndexOf("/");
				String tmpStr=parentUrl.substring(0,li+1);
				if (link.startsWith("/")) {
					link = tmpStr + link.substring(1);
				}else {
					link=tmpStr+link;
				}
			}
	    	return link;
		}
 
	    /**
	     * 获取实际http链接
	     * @param fromURL 源url
	     * @param newURL 新url
	     * @return 实际url
	     */
		public static String getRealURL(String fromURL, String newURL) {
			try {
				if (null == fromURL) {
					return null;
				}
				URL from = new URL(fromURL);
				return getRealURL(from,newURL);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.err.println("DetailExtarctor getRealURL Err");
				return null;
			}
		}
		/**
		 * @param fromURL 传入URL
		 * @param newURL  需要替换URL
		 * @return 重新组装URL
		 */
		public static String getRealURL(URL fromURL,String newURL)
		{
			if(newURL == null || newURL.equals("")){
				return null;
			}
			if (newURL.startsWith("http://") || (newURL.startsWith("https://"))){
				try {
					 new URL(newURL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				}
				return newURL;
			}
			else if (newURL.indexOf("://") == -1 &&
					 !newURL.startsWith("mailto:") &&
					 !newURL.startsWith("#") &&
					 !newURL.startsWith("javascript:") &&
					 !newURL.startsWith("vbscript:")){
				URL u;
				try {
					if (fromURL == null) {
						return null;
					}
					String furl = fromURL.toString();
					if (newURL.startsWith("?")) {
						int iask = furl.lastIndexOf("/");
						if (iask < furl.lastIndexOf("?")) {
							return (furl.substring(0, furl.lastIndexOf("?"))+newURL);
						} else {
							return (furl +newURL);
						}
					}
					u = new URL(fromURL,newURL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				}
				return u.toString().trim();
			}
			return null;
		}
		public static void main(String[] args) {
			String p = "http://www.lygfdc.com/WebSite/PreSale/Home/Default.aspx?homes_id=B34700440C0395FE";
			String newUrl = "?homes_name=B";
			String url = getRealURL(p, newUrl);
			System.out.println(url);
		}
}
