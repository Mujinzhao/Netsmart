package core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;



public class LoginMysteel {

	private String beginUrl;
	private String usename;
	private String password;
	public LoginMysteel(String url,String name,String pass){
		beginUrl = url;
		usename = name;
		password = pass;
	}
	public static void main(String[] args) throws HttpException, IOException{
		String url = "http://passport.mysteel.com/login.jsp";
		String name = "zmmc888";
		String password = "345612";
		String newUrl = "http://jiancai.mysteel.com/m/11/1111/08/00AF236C2844A3BE.html";
		LoginMysteel loginMysteel = new LoginMysteel(url,name,password);
		String cookies = loginMysteel.getWebCookie();
		System.err.println("cookie:"+cookies);
		HttpURLConnection urlConn;
		BufferedReader br = null;
		urlConn = (HttpURLConnection)(new URL(newUrl)).openConnection();
		urlConn.setDoOutput(true);
		urlConn.setDoInput(true);
		urlConn.setUseCaches(false);
		urlConn.setAllowUserInteraction(false);
		urlConn.setRequestProperty("Cookie", cookies);
		urlConn.connect();
		br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"gb2312"));
		String str = br.readLine();
		StringBuffer sb = new StringBuffer();
		while(null != str){
			sb.append(str+"\r\n");
			str = br.readLine();
		}
		br.close();
		urlConn.disconnect();
		System.out.println(sb.toString());
		
//		GetMethod getMethod = new GetMethod(newUrl);
//		getMethod.setRequestHeader("Cookie",cookies);
//		client.executeMethod(getMethod);
//		System.out.println(getMethod.getStatusText());
//		InputStream inputStream = getMethod.getResponseBodyAsStream();
//		String line;
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//				inputStream, "gb2312"));
//		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//		}
//		getMethod.releaseConnection();
	}

	public String getWebCookie(){
		HttpClient client = new HttpClient();
//		String userAgent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) )";
		String userAgent = "IE/7.0";
//		userAgent="Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.14) Gecko/20101001 Firefox/3.5.14";
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, userAgent);

		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
		client.getHttpConnectionManager().getParams().setSoTimeout(8000);
		String cookies = "";
		try {
			PostMethod post = new PostMethod(beginUrl);
			NameValuePair usernameP = new NameValuePair("my_username", usename);
			NameValuePair passwordP = new NameValuePair("my_password", password);
			post.setRequestBody(new NameValuePair[] { usernameP, passwordP});
			int statusCode = client.executeMethod(post);
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = post.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					System.out.println("页面重定向:" + location);
				} else {
					System.err.println("Location field value is null.");
//					LogException.errLog("Location field value is null.");
				}
			}
			System.out.println("******************************登录成功******************************");
//			LogException.errLog("******************************登录成功******************************");
			Header[] headers = post.getResponseHeaders();
			for(Header header : headers) {
				if (header.getName().equals("Set-Cookie")) {
					String value = header.getValue();
					int index = value.lastIndexOf(";");
					if (index > 0) {
						value = value.substring(0,index+1);
					}
					cookies = cookies + value;
				}
			}
			post.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cookies;
	}
	
}
