package test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import javassist.Modifier;

 

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;


import core.util.RegexValidate;
import core.util.WDWUtil;
import datacvg.excel.util.ExcelStaticVar;

public class FileDownLoader {
	
	
	private static   HttpClient myclient=null;  // 唯一的链接
	
	public static String localhtml="";   //取到的最原始的html
	
	public void login(String loginurl)
	{
		    myclient = new HttpClient(); 
		  
	        PostMethod authpost = new PostMethod(loginurl); 
	         
	        NameValuePair userid  = new NameValuePair("username", "haier111"); 
	        NameValuePair password = new NameValuePair("password", "888888"); 
	        NameValuePair doLogin = new NameValuePair("doLogin", "true"); 
	        authpost.setRequestBody( new NameValuePair[] {userid, password,doLogin}); 
		 try {
			 int status = myclient.executeMethod(authpost); 
			 
		    System.out.println("status=="+status);  
	        authpost.releaseConnection(); 
			} catch (IOException ioe) {
				 ioe.printStackTrace();
			}
		
	}
	
	
	public String  downloadHtml(String url)
	{
		  String filePath=null;
		  String html="";
		  myclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		   
		  GetMethod getMethod=new GetMethod(url);	 
		 
		  getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			new DefaultHttpMethodRetryHandler());
		  
		  /*3.执行 HTTP GET 请求*/
		  try{ 
				  int statusCode = myclient.executeMethod(getMethod);
				  //判断访问的状态码
				  System.out.println("statusCode=="+statusCode);
				  html=getMethod.getResponseBodyAsString();
				  localhtml=html;
			 
				  byte[] responseBody = getMethod.getResponseBody();
				  //读取为字节数组
				 
	             filePath="d:\\sun\\"+getFileNameByUrl(url,
	             getMethod.getResponseHeader("Content-Type").getValue());
				 saveToLocal(responseBody,filePath);
		    }
		  catch (HttpException e) {
				   
				   e.printStackTrace();
				  }
		  catch (IOException e) {
				   // 发生网络异常
				   e.printStackTrace();
				  } 
		  finally {
				   // 释放连接
				   getMethod.releaseConnection();		   
				  }
				  
				  return html;
	}
	
	/**根据 url 和网页类型生成需要保存的网页的文件名
	 *去除掉 url 中非文件名字符 
	 */
	public  String getFileNameByUrl(String url,String contentType)
	{
		url=url.substring(7);//remove http://
		if(contentType.indexOf("html")!=-1)//text/html
		{
			url= url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
			return url;
		}
		else
		{
            return url.replaceAll("[\\?/:*|<>\"]", "_")+"."+contentType.substring(contentType.lastIndexOf("/")+1);
		}	
	}

	/**保存网页字节数组到本地文件
	 * filePath 为要保存的文件的相对地址
	 */
	private void saveToLocal(byte[] data,String filePath)
	{
		try {
			 
			DataOutputStream out=new DataOutputStream(
            new FileOutputStream(new File(filePath)));
			for(int i=0;i<data.length;i++)
			out.write(data[i]);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	  
	//td 标签
	public void readtdhtml(String inputhtml)
	{
     Parser parser;
        
        parser = Parser.createParser(inputhtml, "GBK");        
        
        String filterStr = "td";
        NodeFilter filter = new TagNameFilter(filterStr);
        try {
        	NodeList tables = parser.extractAllNodesThatMatch(filter); 
         for(int i=0;i<tables.size();i=i+1)
         {
        	 TableColumn tdColumn = (TableColumn)tables.elementAt(i);        
             if(tdColumn.hashCode()>0)
             System.out.println(  i+"-->"+tdColumn.childAt(0).getText());
         }
               
        } catch (Exception e) {
			e.printStackTrace();
		}
         
    }

	
	
	
	
	
	public void readinputhtml(String inputhtml)
	{
		Parser parser;
		  NodeList nodelist;
		  try {	
		
		    parser = Parser.createParser(inputhtml,"GB2312");
		    String filterStr = "form";
	        NodeFilter filter = new TagNameFilter(filterStr);
	        NodeList formList = parser.extractAllNodesThatMatch(filter);  
	        FormTag ft = (FormTag)formList.elementAt(0); 
	        NodeList  inputList=ft.getFormInputs();
	        for(int i=0;i<inputList.size();i=i+1)
	         {
	        	InputTag input = (InputTag)inputList.elementAt(i);    
	        	System.out.println(i+"--"+input.getText());
	         }
	          
		    } 
		  catch (Exception e) {
				e.printStackTrace();
			}
         
    }
	
	// 读纯文本 
	 
	 public   String readText (String result)  
	 {
		  String line ="";
		  Parser parser;
		  NodeList nodelist;
		  try {	
				  parser = Parser.createParser(result,"GB2312");
				  NodeFilter textFilter = new NodeClassFilter(TextNode.class);
				  OrFilter lastFilter = new OrFilter();
				  lastFilter.setPredicates(new NodeFilter[] { textFilter });
				  nodelist = parser.parse(lastFilter);
				  Node[] nodes = nodelist.toNodeArray();				 
				  for(int i=0;i<nodes.length;i++)
				  {
				    Node node = nodes[i];			   
				    TextNode textnode = (TextNode) node;
				    line += "文本"+i+"=="+textnode.getText();
				    if (isTrimEmpty(line))
				           continue;
				    System.out.println(line);
			     }
		      } 
		  catch (Exception e) {
				e.printStackTrace();
			  }
	  return line;
	 }
	 //  读链接
	 public   String readLink(String result)  
	 {
		 String line ="";
	  Parser parser;
	  NodeList nodelist;
	  try { 
	  parser = Parser.createParser(result,"GB2312"); 
	  NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
	  OrFilter lastFilter = new OrFilter();
	  lastFilter.setPredicates(new NodeFilter[] { linkFilter });
	  nodelist = parser.parse(lastFilter);
	  Node[] nodes = nodelist.toNodeArray();
	  
	  for(int i=0;i<nodes.length;i++)
	  {
	   Node node = nodes[i];	    
	    LinkTag link = (LinkTag)node;
	    line += link.getText()+"#$#$";	   
	   if (isTrimEmpty(line))
	                continue;
	      System.out.println(line);
	   }
	  } catch (Exception e) {
			e.printStackTrace();
		}
	  return line;
	 }

	 
	 public void downloadfujian()
	 {
		 String link=this.readLink(this.localhtml);		 
		 String links[]=link.split("#$#$");		 
		 String dizhi=links[0].toString();		 
		 dizhi=dizhi.substring(dizhi.indexOf("open_win('")+10,dizhi.indexOf("&FileType"));
		   String url="http://127.0.0.1:8080/eclzloa7/SYSTEMFILES/Email/"+dizhi;
		
		  System.out.println(url);
		  String filePath=null;		 
		  myclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		   
		  GetMethod getMethod=new GetMethod(url);		 
		  getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			new DefaultHttpMethodRetryHandler());		  
		  /*3.执行 HTTP GET 请求*/
		  try{ 
				  int statusCode = myclient.executeMethod(getMethod);
				  //判断访问的状态码
				  System.out.println("statusCode=="+statusCode);
				  
				  byte[] responseBody = getMethod.getResponseBody();
				  //读取为字节数组
				 
	             filePath="d:\\sun\\"+getFileNameByUrl(url,
	             getMethod.getResponseHeader("Content-Type").getValue());
				 saveToLocal(responseBody,filePath);
		    }
		  catch (HttpException e) {
				   
				   e.printStackTrace();
				  }
		  catch (IOException e) {
				   // 发生网络异常
				   e.printStackTrace();
				  } 
		  finally {
				   // 释放连接
				   getMethod.releaseConnection();		   
				  }
		 
		  
	 }	 
	 

	  //读取所有的内容节点
	 public   void readAll(String result)  
	 {
		 try {
			
		
	  Parser parser;
	  NodeList nodelist; 
	  parser = Parser.createParser(result,"GB2312");
	  
	  NodeFilter textFilter = new NodeClassFilter(TextNode.class);
	  OrFilter lastFilter = new OrFilter();
	  lastFilter.setPredicates(new NodeFilter[] { textFilter });
	  nodelist = parser.parse(lastFilter);
	  Node[] nodes = nodelist.toNodeArray();	 
	  for (int i = 0; i < nodes.length; i++)
	        {
	            TextNode textnode = (TextNode) nodes[i];
	            String line = textnode.toPlainTextString().trim();
	            if (line.equals(""))
	                continue;
	            System.out.println(i+"=="+line);
	        }
		 } catch (Exception e) {
				e.printStackTrace();
			}
	 }


	  /**
	     * 去掉左右空格后字符串是否为空
	     */
	    public static boolean isTrimEmpty(String astr)
	    {
	        if ((null == astr) || (astr.length() == 0))
	        {
	            return true;
	        }
	        if (isBlank(astr.trim()))
	        {
	            return true;
	        }
	        return false;
	    }
	    /**
	     * 字符串是否为空:null或者长度为0.
	     */
	    public static boolean isBlank(String astr)
	    {
	        if ((null == astr) || (astr.length() == 0))
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }


	 
	public static void main(String[]args)
	{
		
		String temp="sss";
		String temp1="SSS";
		System.out.println(temp.toLowerCase());
		
		FileDownLoader c = new FileDownLoader();
	     System.out.println(c.getClass().getName()+":"+c.getClass().getEnumConstants()+"加载模板结构集合完成");
			

	     // returns the Java language modifiers for this class

				
//		FileDownLoader downLoader = new FileDownLoader();
//		String loginurl="http://www.custeel.com/gb2312/membercenter/login_new.jsp";
//		
//		String url="http://www.custeel.com/kanwu/kanwu_more.jsp?group=1001&cat=1050006";
//		downLoader.login(loginurl);  //登陆初始化，必须的
//	    String aa=downLoader.downloadHtml(url);
//		
////		downLoader.html(aa);
//		System.out.println("*************************");
//		//downLoader.inputhtml(aa); 
//		//downLoader.html(aa);  
////		 downLoader.readText(aa);  //文本 
//			System.out.println("******************************************");
//		  downLoader.readLink(aa);  // 连接
//		 // System.out.println(aa);
	 
		// downLoader.downloadfujian();
	}
}


