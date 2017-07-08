package datacvg.gather.util;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.watij.webspec.dsl.WebSpec;



public class LoginMysteel {

	private String beginUrl;
	private String usename;
	private String password;
	//采集容器
	private WebSpec gatherWeb;
	private boolean isDiaplay=false;
	public boolean isDiaplay() {
		return isDiaplay;
	}
	public void setDiaplay(boolean isDiaplay) {
		this.isDiaplay = isDiaplay;
	}
	public LoginMysteel(){
	}
	public LoginMysteel(String url,String name,String pass){
		beginUrl = url;
		usename = name;
		password = pass;
	}
	public static void main(String[] args) throws HttpException, IOException{
		
		LoginMysteel login = new LoginMysteel();
		//WebSpec  webspec = login.OpenURL("http://passport.mysteel.com/login.jsp");
		WebSpec spec = new WebSpec().ie(); 
			if(spec.ready())  
	        {     
	            spec.open("http://price.sci99.com/register.aspx");  
	            spec.pauseUntilReady();  
	            spec.find.input().with.name("txtUser").set.value("qdhaier"); 
	            spec.find.input().with.name("txtPassword").set.value("666666");  
	            spec.find.input().with.name("doLogin").set.value("true");  
	            spec.find.input().with.name("submit2").click();  
	            System.out.println(spec.source());  
	        }  
	        else{  
	            System.out.println("not ready");  
	        }  
	        spec.close();  

		
//		String url = "http://passport.mysteel.com/login.jsp";
//		String name = "zmmc888";
//		String password = "345612";
//		String newUrl = "http://jiancai.mysteel.com/m/11/1111/08/00AF236C2844A3BE.html";
//		LoginMysteel loginMysteel = new LoginMysteel(url,name,password);
//		String cookies = loginMysteel.getWebCookie();
//		System.err.println("cookie:"+cookies);
//		HttpURLConnection urlConn;
//		BufferedReader br = null;
//		urlConn = (HttpURLConnection)(new URL(newUrl)).openConnection();
//		urlConn.setDoOutput(true);
//		urlConn.setDoInput(true);
//		urlConn.setUseCaches(false);
//		urlConn.setAllowUserInteraction(false);
//		urlConn.setRequestProperty("Cookie", cookies);
//		urlConn.connect();
//		br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"gb2312"));
//		String str = br.readLine();
//		StringBuffer sb = new StringBuffer();
//		while(null != str){
//			sb.append(str+"\r\n");
//			str = br.readLine();
//		}
//		br.close();
//		urlConn.disconnect();
//		System.out.println(sb.toString());
		
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
				}
			}
			System.out.println("******************************登录成功******************************");
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
	
	
	/**
	 * 打开采集地址
	 * @param url 采集链接地址
	 * @return
	 */
	public WebSpec OpenURL(String url){
		
	try {
			if (this.gatherWeb == null) {
				gatherWeb = new WebSpec().ie();
			}
	
			//是否显示
		    if(!this.isDiaplay()&&gatherWeb.isVisible()){
		    	gatherWeb.hide();
		    }
		    // 打开地址
			gatherWeb.open(url);
			Thread.sleep(3000);
			int k = 0;
			while (!gatherWeb.ready()) {
				if (k > 8) {
					break;
				}
				Thread.sleep(3000);
				k++;
			}
			if (k > 8) {
				//gatherWeb.close();
				return null;
			}
			
			return gatherWeb;	
		} catch (Exception e) {

			e.printStackTrace();
			
			return null;
		}
		
	}
}
