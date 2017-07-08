package core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * 获取页面链接
 *
 */
public class WebConnection {

	public static HttpURLConnection getConnection(String str)
	throws IOException {
		URL url = new URL(str);
		return getConnection(url);
	}
	
	public static HttpURLConnection getConnection(URL url){
		HttpURLConnection con = null;
		try {
//			Proxy.initProxy(GlobalStatic.proxy,Integer.parseInt(GlobalStatic.port),GlobalStatic.name,GlobalStatic.pass);
			con = (HttpURLConnection) url.openConnection();
		//	con.setRequestMethod("GET");
			con.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8");
//					"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.10) Gecko/2009042316 Firefox/3.0.10");
			con.setRequestProperty("Host", url.getHost());
			con.setRequestProperty("Accept-Language", "zh-cn");
			con.setRequestProperty("Accept-Charset", "gb2312,gbk,utf-8;q=0.7,*;q=0.7n");
//			con.setFollowRedirects(false);
			con.setInstanceFollowRedirects(true);
			con.setConnectTimeout(6000);
			con.setReadTimeout(6000);
		} catch (IOException e) {
//			e.printStackTrace();
			e.printStackTrace();
		}
		return con;
	}
	
	public static HttpURLConnection getImgConnection(URL url) throws IOException
	{
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.8.1.10) Gecko/20071115 Firefox/2.0.0.10");
		con.setRequestProperty("Host", url.getHost());
		con.setRequestProperty("Accept-Language", "zh-cn");
		con.setRequestProperty("Accept-Charset", "gb2312,utf-8;q=0.7,*;q=0.7n");
		con.setConnectTimeout(9000);
		con.setReadTimeout(9000);
		return con;
	}
	//请求html源数据
	public static String getHtmlSource(String url) throws Exception{
		return getHtmlSource(url,null);
	}
	//请求html元数据，根据编码
	public static String  getHtmlSource(String url,String htmlencode)throws Exception{
		if (url == null) {
			return null;
		}
		String encode =htmlencode;
		StringBuffer sb = new StringBuffer();
		BufferedReader buf = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {

			if (encode == null || encode.equals("")) {
				encode = "gbk";
			}
			
			conn = WebConnection.getConnection(url);
			if (conn == null) {
				return null;
			}
			is = conn.getInputStream();
			buf = new BufferedReader(
					 new InputStreamReader(is,encode));
			String line = "";
			while((line = buf.readLine()) != null){
				sb.append(line+"\n");
			}
		} catch (IOException e) {
			//e.printStackTrace();
			throw e;
		} catch (Exception e) {
		
			//e.printStackTrace();
			throw e;
			
		}finally{
			try {
				if (is != null) {
					is.close();
				}
				if (buf != null) {
					buf.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static  String GetHtmlEncode(String httpurl) throws Exception{
		
		 String source=WebConnection.getHtmlSource(httpurl);
		 
		 return TranCharset.getEncoding(source);
	} 
	
	public static void main(String[] args)throws Exception {
		//String url = "http://www.lygfdc.com/WebSite/PreSale/Home/Default.aspx?homes_id=B34700440C0395FE";
		//String url = "http://www.jszb.com.cn/jszb/YW_info/ZhongBiaoGS/ViewGSDetail.aspx?RowID=262829&categoryNum=012&siteid=1";
		//String url="http://www.lygfdc.com/WebSite/PreSale/Home/Default.aspx?homes_id=52D710246D4013C8";
		//String url ="http://0518.1f.cn/house/intro236.html";
		String url="http://blog.163.com/";
//	
	// Document doc = Jsoup.connect(url).get();
	// WebConnection.getHtmlSource(url);
	//  String text = new String(abc.getBytes(),"utf-8");
	try{
	 //String source=WebConnection.getHtmlSource(url);
/*	 String encoding = TranCharset.getEncoding(source);
	
	 if(encoding.toLowerCase().equals("utf-8")){
		 String newsource=WebConnection.getHtmlSource(url,encoding);
		 System.out.println(newsource);
	 }else{
		 
		 String newsource=WebConnection.getHtmlSource(url,encoding);
	 
		 System.out.println(newsource);
	 }*/
		System.out.println(getHtmlSource(url));
	 System.out.println(GetHtmlEncode(url));
	 
	// System.out.println(source);
	}catch(Exception e){
		System.out.println("aaa");
		e.printStackTrace();
	}

	
	 
 
	}
}
