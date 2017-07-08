package datacvg.gather.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.watij.webspec.dsl.Child;
import org.watij.webspec.dsl.Property;
import org.watij.webspec.dsl.Tag;
import org.watij.webspec.dsl.WebSpec;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

import core.util.StringUtil;

import datacvg.gather.entity.EventEntity;



/**
 * 模拟事件校验
 * @author admin
 *
 */
public class SimulationEventCheck {
	
	
	/**
	 * 校验登录事件
	 * @return
	 */
	public String checkLoginEvent(){
		
		
		return "success";
	}

	/**
	 * 模拟打开URL 
	 * @param Url
	 * @param groupMap
	 * @return
	 */
	public WebSpec OpenUrl(String Url){
		
		    WebSpec spec = new WebSpec().ie(); 
			if(spec.ready())  
	        {     
			    spec.open(Url);  
	            spec.pauseUntilReady();
	        }
			return spec;
//	            //获取事件类型
//	            String optype= groupMap.get("eventype").toString();
//	            String withid="id";
//	            String withname="name";
//	            String withhref="a";
//	            if(optype.equals("set")){
//	            	for(Entry<String, List<String>> itentry :groupMap.entrySet()){
//						List<String> grouparray = itentry.getValue();
//						for(String value :grouparray){
//							// ID
//		            		if(withid.equals(value)){
//		            			 spec.find.input().with.id(id).set.value(value); 
//		            		}
//		            		//name
//		            		if(withname.equals(value)){
//		            			 spec.find.input().with.name(name).set.value(value); 
//		            		}
//		            		//href
//		            		if(withhref.equals(value)){
//		            			 spec.find.a().with.href(value);
//		            		}
//						}
//	            	}
//	            }if(optype.equals("click")){
//	            	for(String value :grouparray){
//						// ID
//	            		if(withid.equals(value)){
//	            			 spec.find.input().with.id(id).click(); 
//	            		}
//	            		//name
//	            		if(withname.equals(value)){
//	            			 spec.find.input().with.name(value).click(); 
//	            		}
//	            		//href
//	            		if(withhref.equals(value)){
//	            			 spec.find.input().with.className(classname).click();
//	            		}
//					}
//	                //spec.find.input().with.name("Btn_Login").click(); 
//	            }
//	            spec.find.a().with.href("http://price.sci99.com/register.aspx");
//	            spec.open("http://price.sci99.com/register.aspx");  
//	            spec.pauseUntilReady();  
//	            spec.find.input().with.name("SciName").set.value("qdhaier"); 
//	            spec.find.input().with.name("SciPwd").set.value("666666");  
//	            spec.find.input().with.name("Btn_Login").click(); 
//	                System.out.println(spec.source());  
//	        }  
//	        else{  
//	            System.out.println("not ready");  
//	        }  
//			return spec;
//	       // spec.close();  
//	
	}
	/**
	 * 模拟打开URL 
	 * @param Url
	 * @param groupMap
	 * @return
	 */
	public WebSpec OpenUrl(String Url,List<EventEntity>eventList){
		   
		    WebSpec spec = new WebSpec().ie();
			if(spec.ready())  
	        {     
			    spec.open(Url);
			    spec.hide();
	            spec.pauseUntilReady();
	            for(EventEntity evententity : eventList){
	            	  //获取事件类型
		            String optype= evententity.getEventype();
		            if(optype.equals("set")){
		            	if(evententity.getTagattr().equals("id")){
	            			 spec.find.input().with.id(evententity.getTagattrval()).set.value(evententity.getEventval()); 
	            		}
		            	if(evententity.getTagattr().equals("name")){
	            			 spec.find.input().with.name(evententity.getTagattrval()).set.value(evententity.getEventval()); 
	            		}
		            	if(evententity.getTagattr().equals("a")){
		            		 spec.find.a().with.href(evententity.getEventval());
	            		}
		            	
		            }if(optype.equals("click")){
		            	if(evententity.getTagattr().equals("id")){
		            		spec.find.input().with.id(evententity.getTagattrval()).click(); 
	            		}
		            	if(evententity.getTagattr().equals("name")){
		            		spec.find.input().with.name(evententity.getTagattrval()).click(); 
	            		}
		            	if(evententity.getTagattr().equals("a")){
		            		spec.find.a().with.href(evententity.getTagattrval()).click();
	            		}
		            }
	            }
	           
	        }
		    else{  
		        System.out.println("not ready");  
		    }  
			return spec;
//	            String withid="id";
//	            String withname="name";
//	            String withhref="a";
//	            if(optype.equals("set")){
//	            	for(Entry<String, List<String>> itentry :groupMap.entrySet()){
//						List<String> grouparray = itentry.getValue();
//						for(String value :grouparray){
//							// ID
//		            		if(withid.equals(value)){
//		            			 spec.find.input().with.id(id).set.value(value); 
//		            		}
//		            		//name
//		            		if(withname.equals(value)){
//		            			 spec.find.input().with.name(name).set.value(value); 
//		            		}
//		            		//href
//		            		if(withhref.equals(value)){
//		            			 spec.find.a().with.href(value);
//		            		}
//						}
//	            	}
//	            }if(optype.equals("click")){
//	            	for(String value :grouparray){
//						// ID
//	            		if(withid.equals(value)){
//	            			 spec.find.input().with.id(id).click(); 
//	            		}
//	            		//name
//	            		if(withname.equals(value)){
//	            			 spec.find.input().with.name(value).click(); 
//	            		}
//	            		//href
//	            		if(withhref.equals(value)){
//	            			 spec.find.input().with.className(classname).click();
//	            		}
//					}
//	                //spec.find.input().with.name("Btn_Login").click(); 
//	            }
//	            spec.find.a().with.href("http://price.sci99.com/register.aspx");
//	            spec.open("http://price.sci99.com/register.aspx");  
//	            spec.pauseUntilReady();  
//	            spec.find.input().with.name("SciName").set.value("qdhaier"); 
//	            spec.find.input().with.name("SciPwd").set.value("666666");  
//	            spec.find.input().with.name("Btn_Login").click(); 
//	                System.out.println(spec.source());  
	   
	       // spec.close();  
	}

	
	
	/**
	 * 执行组标签
	 * @param ti 标签属性对象
	 * @param tag 标签执行对象
	 */
	public  void exeTag(EventEntity event, Tag tag) {

		if (tag.exists()) {
				if (!event.getEventype().equals("")) {
					// 点击
					if (event.getEventype().equals(TagItem.ACTION_CLICK)) {
						tag.click();
						// 设置
					} else if (TagItem.ACTION_SET.equals(event.getEventype())) {
						tag.set(event.getEventval());
						// 其他事件
					} else if (TagItem.ACTION_CHANGE.equals(event.getEventype())) {
						tag.trigger.change();
					}
				}
//				else {
//				// tag.pause(3000);
//				List<TagItem> list = ti.getChildItems();
//				for (int i = 0; i < list.size(); i++) {
//					TagItem it = list.get(i);
//					Tag t = tag.find(it.getTagName()).with(it.getTagAttrName(),
//							it.getTagAttrValue());
//					// 执行tag
//					exeTag(it, t);
//				}
//
//			}
		} else {
			
//			LogWritter.bizInfo("执行标签不存在" + ti.getTagName() + ","
//					+ ti.getTagAttrName() + "," + ti.getTagAttrValue());
		}

	}
	
	/**
	 * 执行标签
	 * @param list 标签合集
	 * @param web  采集容器
	 */
	public  void exeTag(List<EventEntity>eventList, WebSpec web) {

		for (int i = 0; i < eventList.size(); i++) {
			EventEntity ti = eventList.get(i);
			//获取一级标签
			System.out.println(ti.getTagname()+"--->"+ti.getTagattr()+"---"+ti.getTagattrval());
			// 根据标签名称、属性、属性值  获取Tag对象
			Tag rootTag= web.find(ti.getTagname()).with(ti.getTagattr(), ti.getTagattrval());
//			System.out.println(rootTag.exists());
			//执行标签
			exeTag(ti,rootTag);
		}
	}
	public String downUrlFile(String dir,String filename,String htmlsource,String encode){
            ParseDownload pardownload = new ParseDownload();
//            System.out.println(" 源码内容: " +htmlsource);
            pardownload.download(dir,filename,htmlsource,encode);
        	return null;
	}
	/**
	 * 执行标签
	 * @param ti 标签属性对象
	 * @param tag 标签执行对象
	 */
	public  void exeTag(TagItem ti, Tag tag) {

		if (tag.exists()) {
			System.out.println(ti.existchilds());
			// 如果有子标签
			if (!ti.existchilds()) {

				//如果存在dialog事件,暂时不实现
				if(ti.isAfterIsDialog()){
					
				} 
				if (!ti.getActionName().equals("")) {
					// 点击
					if (ti.getActionName().equals(TagItem.ACTION_CLICK)) {
						tag.click();
						// 设置
					} else if (TagItem.ACTION_SET.equals(ti.getActionName())) {
						System.out.println(ti.getActionValue());
						tag.set().value(ti.getActionValue());
						// 其他事件
					} else if (TagItem.ACTION_CHANGE.equals(ti.getActionName())) {
						tag.trigger.change();
					}
					else if (TagItem.ACTION_TIMEDUFALT.equals(ti.getActionName())) {
						    // 当前默认时间
							tag.set(StringUtil.formatDate(new Date(),"yyyy-MM-dd"));
							// 其他事件
					}
				}
			} else {
				// tag.pause(3000);
				List<TagItem> list = ti.getChildItems();
				for (int i = 0; i < list.size(); i++) {
					TagItem it = list.get(i);
					Tag t = tag.find(it.getTagName()).with(it.getTagAttrName(),
							it.getTagAttrValue());
					// 执行tag
					exeTag(it, t);
				}

			}
		} 
	}
	
	///<summary>
    /// 获取指定url的请求内容
    ///</summary>
    ///<param name="Url"></param>
    ///<param name="encode"></param>
    ///<returns></returns>
//    public static String GetRemoteHtmlCodeByEncoding(String Url, String encode)
//    {
//        HttpWebResponse Result = null;
//
//        try
//        {
//            HttpWebRequest Req = (HttpWebRequest) System.Net.WebRequest.Create(Url);
//            Req.Method = "get";
//            Req.ContentType = "application/x-www-form-urlencoded";
//            Req.Credentials = CredentialCache.DefaultCredentials;
//            Result = (HttpWebResponse) Req.GetResponse();
//
//            StreamReader ReceiveStream = new StreamReader(Result.GetResponseStream(), Encoding.GetEncoding(encode));
//            string OutPutString;
//            try
//            {
//                OutPutString = ReceiveStream.ReadToEnd();
//            }
//            catch
//            {
//                OutPutString = string.Empty;
//            }
//            finally
//            {
//                ReceiveStream.Close();
//            }
//
//            return OutPutString;
//        }
//        catch
//        {
//            return string.Empty;
//        }
//        finally
//        {
//            if (Result != null)
//            {
//                Result.Close();
//            }
//        }
//    }
	public static void main(String[] args) {
		    WebSpec spec = new WebSpec().ie();
			if(spec.ready())  
	        {    
//				// 中铝网
	            spec.open("https://kyfw.12306.cn/otn/login/init");  
	            spec.pauseUntilReady();  
//	            spec.hide();
	            spec.find.input().with.id("username").set.value("suoxiangyun"); 
	            spec.find.input().with.id("password").set.value("sxy517");
	            // https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&Math.random()
	            spec.find.input().with.id("randCode").set.value(""); 
	            spec.find.input().with.id("loginSub").click();
//	            System.out.println("页面数"+spec.browserCount());
//		        System.out.println("数据源: " +  spec.browser(1).source());  
//		        //中国银行
//		        spec.open(" http://srh.bankofchina.com/search/whpj/search.jsp");  
//	            spec.pauseUntilReady();  
//	            //spec.hide();
//	            //spec.find.select().with.name("pjname").set.value("0"); 
//	          //  spec.find.select().with.name("page").set.value("1"); 
	           
//	            spec.find.select().with.id("pjname").set.value("1316"); 
//	            spec.find.input().with.name("erectDate").set("2015-01-26");
//	            
////	            spec.find.input().with.name("calendarClose").click();
//	            spec.find.input().with.name("nothing").set("2015-01-26");
//	            spec.find.input().with.className("search_btn").at(1).click();
	            
//	            Tag rootTag= 
				
//				  spec.open("http://price.sci99.com/");  
//		          spec.pauseUntilReady();  
//	            List<TagItem> list = new ArrayList<TagItem>() ;
//	            	TagItem tagitem = new TagItem();
//	 	            tagitem.setTagName("a");
//	 	            tagitem.setTagAttrName("id");
//	 	            tagitem.setTagAttrValue("hlLogin");
//	 	            tagitem.setActionName("click");
//	 	            list.add(tagitem);
//	 	            
//	 	            TagItem tagitem1 = new TagItem();
//	 	            tagitem1.setTagName("input");
//	 	            tagitem1.setTagAttrName("id");
//	 	            tagitem1.setTagAttrValue("SciName");
//	 	            tagitem1.setActionValue("qdhaier");
//	 	            tagitem1.setActionName("set");
//	 	            list.add(tagitem1);
//	 	            
//	 	            TagItem tagitem2 = new TagItem();
//	 	            tagitem2.setTagName("input");
//	 	            tagitem2.setTagAttrName("id");
//	 	            tagitem2.setTagAttrValue("SciPwd");
//	 	            tagitem2.setActionValue("666666");
//	 	            tagitem2.setActionName("set");
//	 	            list.add(tagitem2);
//	 	            
//	 	            
//	 	       	TagItem tagitem3 = new TagItem();
// 	            tagitem3.setTagName("input");
// 	            tagitem3.setTagAttrName("id");
// 	            tagitem3.setTagAttrValue("Btn_Login");
// 	            tagitem3.setActionName("click");
// 	            list.add(tagitem3);
// 	            
// 	          	TagItem tagitem4 = new TagItem();
// 	            tagitem4.setTagName("input");
// 	            tagitem4.setTagAttrName("id");
// 	            tagitem4.setTagAttrValue("txtSearch");
// 	           tagitem4.setActionValue("PP");
// 	            tagitem4.setActionName("set");
// 	            list.add(tagitem4);
// 	           TagItem tagitem5 = new TagItem();
// 	          tagitem5.setTagName("input");
// 	         tagitem5.setTagAttrName("id");
// 	        tagitem5.setTagAttrValue("btnDeepSearch");
// 	       tagitem5.setActionName("click");
//	            list.add(tagitem5);
// 	           
//	 	           for (int i = 0; i < list.size(); i++) {
//	 	  			
//	 	  			TagItem ti = list.get(i);
//	 	  			//获取一级标签
//	 	  			System.out.println("执行标签信息 "+ ti.getTagName() +"--->"+ti.getTagAttrName()+"------"+ti.getTagAttrValue());
//	 	  			Tag rootTag= spec.find(ti.getTagName()).with(ti.getTagAttrName(), ti.getTagAttrValue());
//	 	  			//执行标签 
//	 	  			new SimulationEventCheck().exeTag(ti,rootTag);
//	 	  		    spec.find.iframe().with("id","SearchList").set().src("popWin/deepSearch_Hubble.aspx?keyword=PP").click();
//	 	  			System.out.println("SSSSSSSSSSSSS" + spec.source());
//	 	  		    
//	 	  			
//	 	  		}
	           
//	            System.out.println("执行标签信息 "+ ti.getTagName() +"--->"+ti.getTagAttrName()+"------"+ti.getTagAttrValue());
//				Tag rootTag= web.find(ti.getTagName()).with(ti.getTagAttrName(), ti.getTagAttrValue());
				
	           
	            
//	            spec.find("input").with("name","erectDate").set("2015-01-26");
//	            spec.find("input").with("name","nothing").set("2015-01-26");
//	            spec.find("select").with("id","pjname").set().value("1316");
//	            spec.find("input").with("className","search_btn").set("search_btn").at(1).click();
	            
//	            spec.find.input().with.name("calendarClose").click();
	     
	            //spec.find.input().with.className("search_btn").click();
	           
//	            System.out.println("页面数"+spec.browserCount());
//		        System.out.println("数据源: " +  spec.source());
		        
//		        //交易数据
//		        
//		        spec.open("http://www.shfe.com.cn/statements/dataview.html?paramid=kx");  
//	            spec.pauseUntilReady();  
//	          
//	            spec.find.select().with.className("ui-datepicker-year").set.value("2015"); 
//	            spec.find.select().with.className("ui-datepicker-month").set.value("1"); 
//	            
//	            spec.find.a().with.className("ui-state-default").set("6").click();
//	            
//	           // spec.find.input().with.id("showtable").get().innerHTML();
//	           
//	            Tag tagtable =  spec.browser(spec.browserCount()-1).find.table().with.id("divtable");
//	            String source =tagtable.find.html().get.innerHTML(); 
//				ParseDownload.download(task.getId()+"_SIM", page+".html", source);
	            
//	            Tag taginput=  spec.find.input().with.id("showtable");
//	            Tag exceltag=  spec.find.p().with.id("excel_pic");
//	            System.out.println("asssss"+source);
	            
	            
//	            System.out.println(taginput.exists());
//	            if(taginput.exists()){
//	            	//taginput.
//	            	System.out.println("toString " +taginput.find().toString());
//	            	 System.out.println("id :"+ taginput.get.id()); 
//	            	 System.out.println("name :"+ taginput.get.name()); 
//	            	 System.out.println("value :"+ taginput.get.value()); 
//	            	 System.out.println("innerText :"+ taginput.get().innerText()); 
//	            }
	            
	            //Child tabledata = spec.find.div().with.className("conncent").child;
	            
//	            Tag tagtable = spec.find.table().with.id("divtable");
//	            System.out.println( spec.browser(spec.browserCount()-1).find.table().with.id("divtable").get().innerHTML());
//	            System.out.println(tagtable.exists());
//	            if(tagtable.exists()){
//	            	System.out.println("table  " + tagtable.get.id());
//	  	            System.out.println("table  " + tagtable.get.innerText());
//	  	            System.out.println("table  " + tagtable.get.innerHTML());
//	            }
	          
//	            tabledata.html().shouldHave().;
//	            System.out.println("table  " +tabledata.get().toString());
		           
	            //Tag calendardiv = spec.find.div().with.id("calendar");
//	            calendardiv.find.select().with.className("ui-datepicker-year").set.value("2013");
//	            calendardiv.find.select().with.className("ui-datepicker-month").set.value("6"); 
	            
//	            calendardiv.find.a().with.className("ui-state-default").trigger.click();
//	            calendardiv.find.a().with.className("ui-state-default").get("3")
	            
	           // calendardiv.find.a().with.className("ui-state-default").all().set("3").click();
	            //.call("3").click();
//	            System.out.println("sssssssssssss" +   calendardiv.find.a().with.className("ui-state-default").all().call("3").toString());
	            
	            //spec.find.a().with.className("ui-state-default ui-state-hover").click();
//	            System.out.println("页面数"+spec.browserCount());
//		        System.out.println("数据源: " +  spec.source());
		        
		       
	        }
		//	spec.closeAll();
			
	}
}
	
