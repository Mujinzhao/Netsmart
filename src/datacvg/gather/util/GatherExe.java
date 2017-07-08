package datacvg.gather.util;

import haier.dataspider.param.service.SpringContextUtils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javassist.expr.NewArray;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.watij.webspec.dsl.Tag;
import org.watij.webspec.dsl.WebSpec;

import antlr.collections.impl.LList;

import com.gargoylesoftware.htmlunit.javascript.host.Element;

import cn.smy.dama2.Dama2;

import vcore.Verification;



import core.dbcontrol.BaseDao;
import core.spider.entity.StaticVar;
import core.util.RegexValidate;
import core.util.StringUtil;
import datacvg.dbconfg.util.DBUtil;
import datacvg.gather.entity.GatherBean;
import datacvg.gather.entity.IterateDrillTag;
import datacvg.gather.entity.KeywordBean;
import datacvg.gather.entity.Link;
import datacvg.gather.entity.SIMParam;
import datacvg.gather.entity.TagGroup;
import datacvg.parse.service.ParserTaskService;
import datacvg.parse.util.parserHTML;
import datacvg.taskmanage.action.TaskManageAction;
import datacvg.taskmanage.service.TaskManageService;

public class GatherExe extends BaseThread
{
	

	private static final Logger gather = Logger.getLogger(GatherExe.class);
	BaseThread baseThread = new BaseThread();
	// 采集容器
	private WebSpec gatherWeb = null;
	private boolean isDiaplay = false;
	// 容器是否关闭
	@SuppressWarnings("unused")
	private boolean isclose = false;
	
	private String username ="";
	private String password = "";
	private String vcode = "";//验证码识别结果
	private long vcodeID=0;
	private Point iframePoint= new Point(0, 0);   //iframe形式的验证码弹出页面的point



	public boolean isDiaplay()
	{
		return isDiaplay;
	}

	public void setDiaplay(boolean isDiaplay)
	{
		this.isDiaplay = isDiaplay;
	}

	private GatherBean gatherbean;

	public GatherBean getGatherbean()
	{
		return gatherbean;
	}

	public void setGatherbean(GatherBean gatherbean)
	{
		this.gatherbean = gatherbean;
	}

	public GatherExe()
	{
		super();

	}

	public GatherExe(int taskId)
	{
		super(taskId);
	}
	private WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public static WebDriver webdriver;

	/**
	 * Attempt to switch to the target window according to its title. If the switch failed to find the designated window, the current window will be focused.
	 * @param driver The current webdriver
	 * @param title The string contained in the target window's title
	 * @return boolean
	 */
	public static boolean nextWindow(WebDriver driver, String title)
	{
		
		String currentHandle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		try{
			for (String s : handles) {
				if (s.equals(currentHandle))
					continue;
				else
				{
					driver.switchTo().window(s);
					if(StringUtils.isBlank(title) || driver.getTitle().contains(title))
					{
//						driver.manage().window().setPosition(new Point(-2000, 0));
						return true;
					}
				}
			}
//			driver.manage().window().setPosition(new Point(-2000, 0));
			driver.switchTo().window(currentHandle);
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			driver.switchTo().window(currentHandle);
			return false;
		}
	}
	
	public static boolean downloadHtml(WebDriver driver, File file)
	{
		try{
			if (!file.exists())
			{
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(driver.getPageSource());
			bw.close();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	// 重写执行方法
	public void execute()
	{
		gather.info("执行任务开始:" + this.gatherbean.getTaskcode());
		exeGatherBean();
		// 执行完成，改状态为初始化状态
		StaticVar.taskandthreadstate.remove(this.gatherbean.getTaskcode());
		gather.info("执行任务完成:" + this.gatherbean.getTaskcode());
		// 取出新的任务
		GatherBean nextgather = TaskDistributor.NextGatherTask();
		if (nextgather != null)
		{
			this.setGatherbean(nextgather);
			execute();
		}
		SessionFactory mySessionFactory = (SessionFactory) SpringContextUtils.getBean("sessionFactory");
		ParserTaskService parsertaskService = (ParserTaskService) SpringContextUtils.getBean("parsertaskService");
		String sql = "insert into  gather_Parser_code values(:taskcode,:parsercode) ";
		Session session = null;
		try
		{
			session = mySessionFactory.openSession();
			parsertaskService.OparserSql(sql, StaticVar.taskflie, session);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			gather.info("保存解析结构对已解析目录信息失败!");
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
		StaticVar.taskflie.clear();
		if(this.gatherWeb != null)
		{
			this.gatherWeb.close();
		}
	}


	/**
	 * 执行采集( 新的采集任务)
	 */
	public String exeGatherBean()
	{
		// 获取有效采集站点信息
		if (this.gatherbean != null && this.getGatherbean().isIsvalid())
		{
			gather.info("采集任务" + this.getGatherbean().getTaskcode() + "被创建");
			if (GatherBean.TASK_SIMULATION.equals(this.gatherbean.getTasktype()))// 模拟采集 1
			{
				WebDriverInit();//initialize the webdriver
				driver.manage().window().maximize();
				driver.navigate().to(this.getGatherbean().getTaskurl());//navigate to task url
				webdriver=driver;
				
				String retval = OpenSIMTagsBeanNew(driver, this.gatherbean);
				
				driver.quit();//quit driver to prevent memory leak
				return retval;
			}
			else if (OpenURL(this.getGatherbean().getTaskurl()) == null)// 打开采集链接
			{
				gather.warn("采集链接打开失败！");
				return "采集链接打开失败！";
			}
			if (GatherBean.TASK_LIST.equals(this.gatherbean.getTasktype()))// 列表采集(tasktype==0)
			{
				return OpenListBean(this.getGatherWeb(), this.getGatherbean(), true);
			}
//			else if (GatherBean.TASK_SIMULATION.equals(this.gatherbean.getTasktype()))// 模拟采集 1
//			{
//				return OpenSIMTagsBean(this.getGatherWeb(), this.gatherbean, true);
//			}
			else if (GatherBean.TASK_SOURCE.equals(this.gatherbean.getTasktype()))// 元搜索采集(tasktype==2)
			{
				// return OpenListBean(this.getGatherWeb(),this.getGatherbean(),true);
				return OpenMetaSearchBean(this.getGatherWeb(), this.getGatherbean(), true);
			}
			else if (GatherBean.TASK_CUSOURCE.equals(this.gatherbean.getTasktype()))// 当前源采集(tasktype==3)
			{
				return OpenListCuBean(this.getGatherWeb(), this.getGatherbean(), true);
			}
			StaticVar.taskandthreadstate.remove(this.getGatherbean().getTaskcode());
		}
		else
		{
			gather.warn("任务无效，无法验证！");
			return "任务无效，无法验证！";
		}
		return null;
	}

	/**
	 * 执行采集任务
	 * @param gtask 采集任务
	 */
	public void exeGatherBean(GatherBean gtask) throws Exception
	{
		if (gtask != null)
		{
			this.gatherbean = gtask;
			exeGatherBean();
		}
	}

	/**
	 * 打开采集地址
	 * @param url 采集链接地址
	 * @return
	 */
	public WebSpec OpenURL(String url)
	{
		try
		{
			if (this.gatherWeb == null)
			{
				gatherWeb = new WebSpec().ie();
				// gatherWeb = new WebSpec().mozilla();
			}
			if (!this.isDiaplay() && gatherWeb.isVisible())
			{
				gatherWeb.hide();//隐藏浏览器框
			}
			// 打开地址
			gatherWeb.open(url);
			Thread.sleep(5000);
			int k = 0;
			while (!gatherWeb.ready())
			{
				if (k > 8)
				{
					break;
				}
				Thread.sleep(5000);
				gather.info("等待:" + k);
				k++;
			}
			if (k > 8)
			{
				gather.info("请求超时:" + url);
				gatherWeb.close();
				return null;
			}
			return gatherWeb;
		}
		catch (Exception e)
		{
			gather.error("打开连接" + this.getGatherbean().getTaskurl() + "发生异常 " + e.getMessage());
			e.printStackTrace();
			gatherWeb.close();
			return null;
		}

	}

	/**
	 * 执行标签
	 * @param list 标签合集
	 * @param web 采集容器
	 */
	public void exeTag(List<TagItem> list, WebSpec web)
	{
		for (int i = 0; i < list.size(); i++)
		{
			TagItem ti = list.get(i);
			// 获取一级标签
			// System.out.println("执行标签信息 "+ ti.getTagName() +"--->"+ti.getTagAttrName()+"------"+ti.getTagAttrValue());
			Tag rootTag = web.find(ti.getTagName()).with(ti.getTagAttrName(), ti.getTagAttrValue());
//			String source = web.source();
//			System.out.println(source);
			// 执行标签
			exeTag(ti, rootTag);
		}
	}
	

	/**
	 * 执行标签
	 * @param ti
	 *            标签属性对象
	 * @param tag
	 *            标签执行对象
	 */
	public void exeTag(TagItem ti, Tag tag)
	{
		if (tag.exists())
		{
			// System.out.println(ti.existchilds());
			// 如果有子标签
			if (!ti.existchilds())
			{
				// 如果存在dialog事件,暂时不实现
				if (ti.isAfterIsDialog())
				{
				}
				if (!ti.getActionName().equals(""))
				{// 点击
					if (ti.getActionName().equals(TagItem.ACTION_CLICK))
					{
						tag.click();// 设置
					}
					else if (TagItem.ACTION_SET.equals(ti.getActionName()))
					{
						tag.set().value(ti.getActionValue());// 其他事件
					}
					else if (TagItem.ACTION_CHANGE.equals(ti.getActionName()))
					{
						tag.trigger.change();
					}
					else if (TagItem.ACTION_TIMEDUFALT.equals(ti.getActionName()))
					{// 当前默认时间
						tag.set(StringUtil.formatDate(new Date(), "yyyy-MM-dd"));// 其他事件
					}
				}
			}
			else
			{
				List<TagItem> list = ti.getChildItems();
				for (int i = 0; i < list.size(); i++)
				{
					TagItem it = list.get(i);
					Tag t = tag.find(it.getTagName()).with(it.getTagAttrName(), it.getTagAttrValue());
					// 执行tag
					exeTag(it, t);
				}
			}
		}
		else
		{
			gather.info("执行标签不存在" + ti.getTagName() + "," + ti.getTagAttrName() + "," + ti.getTagAttrValue());
		}
	}
	//TODO
/**
 * 
 * @param task 采集任务
 * @param list 执行标签列表
 * @param simparamlist 数据来源列表
 * @throws Exception
 */
	public void exeTagNext(GatherBean task,List<TagItem> list,List<SIMParam> simparamlist) throws Exception{
		if(TagItem.ACTION_GETDATA.equals(list.get(0).getActionName())){
//			List<SIMParam> simparam=taskmanageService.getParam();
		List<TagItem> setlist=new ArrayList<TagItem>();
		List<Integer> array=new ArrayList<Integer>();
		for(int j=0;j<list.size();j++){
			if(list.get(j).getActionName().equals(TagItem.ACTION_SET)){
				array.add(j);
			}
			else continue;
		}
		//传递的参数j，下载的文件名
		for(int i=0,j=0;i<simparamlist.size();i++){
			list.get(array.get(0)).setActionValue(simparamlist.get(i).getInvoiceCode());
			list.get(array.get(1)).setActionValue(simparamlist.get(i).getInvoiceNumber());
			list.get(array.get(2)).setActionValue(simparamlist.get(i).getSellerRegNumber());
			//循环获取数据
			exeTagNew(list,task,j);
			j++;
			
		}
		}
		else{
			//不循环获取数据
			exeTagNew(list);
		}
		}

	
	
	/**
	 * 执行标签
	 * @param list
	 *            标签合集
	 * @param driver
	 *            采集驱动器
	 */
	public void exeTagNew(List<TagItem> list)
	{
		boolean iframe = false;
		int id=1;//数据库中的invoiceid
		for (int i = 0; i < list.size(); i++)
		{
			TagItem ti = list.get(i);
			//获取数据源的标签可以存任何的标签值，不执行
			if(ti.actionName.equals(TagItem.ACTION_GETDATA)){
				continue;
			}
			String condition = CreateContidion(ti);
			WebElement element = null;
			String tagname = ti.getTagName();
			
			try{
				element = driver.findElement(By.xpath("//" + ti.getTagName() + condition));
				if(ti.getTagName().equals("iframe"))
				{
					iframePoint = element.getLocation();
					driver.switchTo().frame(element);
					iframe = true;
				}
				else
				{
					iframe = false;
				}
				exeTagNext(ti, element, iframe);
				//driver.switchTo().defaultContent();                  从iframe返回默认页面
			}
			catch(Exception e)
			{
				gather.info("执行标签不存在" + ti.getTagName() + "," + ti.getTagAttrName() + "," + ti.getTagAttrValue());
			}
		}
	}
	
	/**
	 * 
	 * @param list 标签列表
	 * @param task 
	 * @param n 保存的html文件名
	 */
	public void exeTagNew(List<TagItem> list,GatherBean task,int n)
	{
		boolean iframe = false;
		for (int i = 0; i < list.size(); i++)
		{
			TagItem ti = list.get(i);
			//不执行getdata动作
			if(ti.actionName.equals(TagItem.ACTION_GETDATA)){
				continue;
			}
			String condition = CreateContidion(ti);
			WebElement element = null;
			String tagname = ti.getTagName();
			
			try{
				element = driver.findElement(By.xpath("//" + ti.getTagName() + condition));
				if(ti.getTagName().equals("iframe"))
				{
					iframePoint = element.getLocation();
					driver.switchTo().frame(element);
					iframe = true;
				}
				else
				{
					iframe = false;
				}
				//在点击返回之前，将网页保存，文件名为n
				if(i==list.size()-1){
					ParseDownload.download(task.getTaskcode() , n + ".html", driver, task.getEncodeurl());
					exeTagNext(ti, element, iframe);
					break;
				}
				exeTagNext(ti, element, iframe);
				//有弹出警告框时，后退三个标签继续执行，当出现警告框时，获取vcodeID，想打码兔报告打码错误
				if(ti.getActionName().equals(TagItem.ACTION_CLICK)){
				Alert alert=driver.switchTo().alert();
				if(alert!=null){
					alert.accept();
					int result=new Dama2().reportResult(vcodeID,false);
					System.out.println("序列号为"+vcodeID+"：识别错误!");
					i=i-3;//后退三步
				}
				//driver.switchTo().defaultContent();                  从iframe返回默认页面
			}
			}
			//无警告框弹出异常，不处理
			catch(NoAlertPresentException e)
			{
//				throw e;
			}
			catch(Exception e){
				gather.info("执行标签不存在" + ti.getTagName() + "," + ti.getTagAttrName() + "," + ti.getTagAttrValue()
						+"或者验证码识别错误");
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	/**
	 * 
	 * @param ti  执行标签
	 * @param element 页面元素
	 * @param iframe 是否存在iframe
	 * @return 执行结果
	 */
	public boolean exeTagNext(TagItem ti, WebElement element, boolean iframe)
	{
		boolean bool=true;
		// 如果有子标签
		if (!ti.existchilds())
		{
			try{
				// 如果存在dialog事件,暂时不实现
				if (ti.isAfterIsDialog())
				{
				}
				if (!ti.getActionName().equals(""))
				{
					if (TagItem.ACTION_CLICK.equals(ti.getActionName()))
					{// 点击
						bool=bool&&tagClick(element);
						
					}
					else if (TagItem.ACTION_SET.equals(ti.getActionName()))
					{// 设置
						bool=bool&&tagSet(ti, element);
					}
					else if (TagItem.ACTION_CHANGE.equals(ti.getActionName()))
					{// 改变选项
						bool=bool&&tagChange(element);
					}
					else if (TagItem.ACTION_TIMEDUFALT.equals(ti.getActionName()))
					{// 当前默认时间
						bool=bool&&tagTimeDefault(element);
					}
					else if(TagItem.ACTION_VALIDATE.equals(ti.getActionName()))
					{//识别验证码
						//验证码的位置出错
						bool=bool&&tagValidate(element);
					}
					else if(TagItem.ACTION_FILLVALIDATE.equals(ti.getActionName()))
					{//填写验证码
						bool=bool&&tagFillValidate(element);
					}
					else if(TagItem.ACTION_SETDATE.equals(ti.getActionName())){
						bool=bool&tagSetDate(element,ti);
					}
				}
				//driver.switchTo().defaultContent();//TODO
				return bool;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
			
		}
		else
		{
			List<TagItem> list = ti.getChildItems();
			for (int i = 0; i < list.size(); i++)
			{
				TagItem it = list.get(i);
				String condition = CreateContidion(it);
				try{
					if(iframe)
					{
						element = driver.findElement(By.xpath("//" + it.getTagName() + condition));
					}
					else
					{
						element = element.findElement(By.xpath("//" + it.getTagName() + condition));
					}
					if(it.getTagName().equals("iframe"))
					{
						driver.switchTo().frame(element);
						iframe = true;
					}
					else
					{
						iframe = false;
					}
					boolean suc = false;
					for(int trycount =0; trycount<2; trycount++)
					{
						suc = exeTagNext(it, element, iframe);
						if(suc)
							break;
					}
					if(!suc)
					{
						return false;
					}
				}catch(NoSuchElementException e)
				{
					gather.info("执行标签不存在" + it.getTagName() + "," + it.getTagAttrName() + "," + it.getTagAttrValue());
					return false;
				}
				catch(Exception e)
				{
					
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}
	}
	/**
	 * 点击
	 * @param element
	 * @return
	 */
	public boolean tagClick(WebElement element){
		for(int count = 0; count <5; count++)
		{
			if(tryClick(element)){
				break;
			}
		}
		return true;
	}
	/**
	 * 设值
	 * @param ti
	 * @param element
	 * @return
	 */
	public boolean tagSet(TagItem ti,WebElement element){
		element.clear();//清空数值
		element.sendKeys(ti.getActionValue());
		return true;
	}
	/**
	 * 改变选项
	 * @param element
	 * @return
	 */
	public boolean tagChange(WebElement element){
		element.submit();
		return true;
	}
	/**
	 * 识别验证码
	 * @param element
	 * @return
	 * @throws WebDriverException
	 * @throws IOException
	 */
	public boolean tagValidate(WebElement element) throws WebDriverException, IOException{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element );//滚动到element所在处。
		Point point = element.getLocation();//获取element在当前frame所处位置
		Dimension dim = element.getSize();//获取element大小
		BufferedImage originalImage = 
				ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));//获取当前视窗截屏
		BufferedImage croppedImage = originalImage.getSubimage(point.getX()+iframePoint.getX(), 0, dim.width, dim.height);//截取验证码图片
//		BufferedImage croppedImage = originalImage.getSubimage(point.getX()+iframePoint.getX(), point.getY()+iframePoint.getY(), dim.width, dim.height+10);//截取验证码图片

		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		
//		ImageIO.write(croppedImage, "jpg", new File("abc.jpg"));//将图片写出
		
		ImageIO.write(croppedImage, "jpg", os);
		byte[] data = os.toByteArray();//验证码图片字节流
		int type =42;//此处的验证码为四位汉字或者汉字的加减法，默认200
		//4位数字英文42，4位汉字62
		
		//type = Integer.parseInt(ti.getValidateCodeType());
		//try{ type = Integer.parseInt(ti.getValidateCodeType()); }catch(Exception e){}
		Verification verification = new Verification();
//		vcode=verification.Verificate(username, password, data, type);
//		vcode="0";
		vcode =verification.Verificate(username, password, data, type);
		vcodeID=verification.getVcodeID();
		if(vcode!="ERROR")
			return true;
		else
			return false;
	}
	/**
	 * 填写验证码
	 * @param element
	 * @return
	 */
	public boolean tagFillValidate(WebElement element){
		if(StringUtils.isBlank(vcode))
		{
			return false;
		}
		element.click();
		element.clear();
		element.sendKeys(vcode);
		return true;
	}
	/**
	 * 默认时间
	 * @param element
	 * @return
	 */
	public boolean tagTimeDefault(WebElement element){
		element.sendKeys(StringUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return true;
	}
	/**
	 * 点击
	 * @param element
	 * @return
	 */
	public boolean tryClick(WebElement element)
	{
		try{
			element.click();
			Thread.sleep(5000);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
public boolean tagSetDate(WebElement element,TagItem ti){
	//date为空则将其设置为今天
	String date=ti.getActionValue();
	if (date.equals("")|date==null){
		Date today=new Date();
		SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
		date=sdf1.format(today);
	}
		String js="document.getElementById('"+
			element.getAttribute("id")+
			"').removeAttribute('readonly');" +
			"document.getElementById('"+
			element.getAttribute("id")
			+"').setAttribute('value','"+
			date+"');";
	((JavascriptExecutor) driver).executeScript(js);
	return true;
	
}
	/**
	 * 当前源采集任务执行
	 * @param web 模拟容器
	 * @param task 任务信息
	 * @param isthread 线程标识
	 * @return
	 */
	public String OpenListCuBean(WebSpec web, GatherBean task, boolean isthread)
	{
		int page = 0;
		int count = 0;
		String prev_outerHMTL = null;
		// 分页标签
		TagItem tpage = task.GetPageTagItem();
		int totalpage = 0;
		if (RegexValidate.StrNotNull(task.getTotalpage()))
		{
			// 通过正则匹对源码中分页总数
			totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), web.source());
		}
		// 是否需要采集
		if (task.isIsspider())
		{
			while (true)
			{
				// 采集当前页面
				ParseDownload.download(task.getTaskcode(), page + ".html", web, task.getEncodeurl());
				// ParseDownload.download(task.getTaskcode(), page+".html", web.source(),task.getEncodeurl());
				page++;
				gather.info("pageindex:" + page + "     counnt:" + count);
				if (tpage != null)
				{
					// 获取下一页标签
					Tag pageTag = getPageTag(web, tpage);
					if (pageTag.exists())
					{
						if (totalpage > 0)
						{
							if (totalpage == page)
							{
								gather.info("outerHtml已到最后一页!");
								break;
							}
						}
						else
						{
							if (pageTag.get("outerHTML") != null && pageTag.get("outerHTML").equals(prev_outerHMTL))
							{
								gather.info("outerHtml已到最后一页!");
								break;
							}
						}
						// 获取下一页连接
						prev_outerHMTL = pageTag.get("outerHTML");
						// 点击下一页
						pageTag.click();
						count = page;
					}
					else
					{
						gather.info("下一页标签不存在或者已到尾页！");
						break;
					}
				}
				else
				{
					gather.info("下一页标签不存在或者已到尾页！");
					break;
				}
			}
		}
		if (count > 0)
		{
			gather.info("task:" + task.getTaskcode() + " 下载完成!");
		}
		else if (web.source() == null)
		{
			gather.info("页面超时没有任何内容！");
		}
//		else
//		{
//			gather.info("页面超时或连接未配置");
//		}
		// 下载
		return web.source();
	}

	/**
	 * 元搜索采集 关键词
	 * 
	 * @param web
	 * @param task
	 * @param isthread
	 * @return
	 */
	public String OpenMetaSearchBean(WebSpec web, GatherBean task, boolean isthread)
	{
		try
		{
			// 获取组信息
			int page = 0;
			List<KeywordBean> keywordList = task.getKeyWords();
			if (keywordList != null && keywordList.size() > 0)
			{
				for (int i = 0; i < keywordList.size(); i++)
				{
					String keywords = keywordList.get(i).getKeywords();
					gather.info("元搜索 " + keywords + " 采集");
					String cjurl = task.getTaskurl();// + keywords;
					// 当前URl需要采集 并打开该链接
					if (keywordList.get(i).isIsspider() && this.OpenURL(cjurl) != null)
					{
						// 采集
						System.out.println("haha111"+cjurl);
						gather.info("元搜索链接 " + cjurl);
						page = ExeMeteListIframPageTags(web, task, keywordList.get(i), page);
						page++;
					}
					System.out.println("haha222"+cjurl);
				}
			}
			else
			{
				gather.info("元搜索 未配置搜索关键词");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			gather.info("元搜索采集异常！");
		}
		return null;
	}

	/**
	 * 元搜索任务采集接口
	 * 
	 * @param web
	 * @param task
	 * @param keywordbean
	 * @param pages
	 * @return
	 */
	public int ExeMeteListIframPageTags(WebSpec web, GatherBean task, KeywordBean keywordbean, int pages)
	{
		web.hide();
		String prev_outerHMTL = null;
		// 分页标签
		TagItem tpage = task.GetPageTagItem();

		String pageerror = "";
		int page = 0;
		int totalpage = 1;
		if (RegexValidate.isINTEGER_NEGATIVE(task.getTotalpage()))
		{
			totalpage = Integer.parseInt(task.getTotalpage());
		}
		else
		{
			// 通过正则匹对源码中分页总数
			totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), web.source());
		}
		// 解析容器标签
		while (true)
		{
			// 线程状态判断
			HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate.get(task.getTaskcode());
			Boolean isDead = threadstate.get("isDead");
			Boolean isStop = threadstate.get("isStop");
			// 线程终止状态
			if (!isDead)
			{
				// 暂停线程状态
				if (!isStop)
				{
					if (keywordbean.isIsspider())
					{
						// 下载
						// String sourcehtml = web.source();
						// String downurl= web.url();
						String taskcode = task.getTaskcode();
						String encode = task.getEncodeurl();
						gather.info("元搜索" + task.getTaskname() + "采集:" + taskcode + "_META" + "/" + pages + ".html");
						ParseDownload.download(taskcode + "_META", pages + ".html", web, encode);
						// ParseDownload.download(taskcode+"_META", pages+".html",sourcehtml,encode,downurl);
					}
					page++;
					gather.info("pageindex:" + page + "     counnt:" + pages);
					if (tpage != null)
					{
						// 获取下一页标签
						Tag pageTag = getPageTag(web, tpage);
						if (pageTag.exists())
						{
							// 当前任务配置采集页面数
							if (totalpage > 0)
							{
								if (totalpage == page)
								{
									pageerror = "outerHtml已到最后一页!";
									gather.info(pageerror);
									break;
								}
							}
							else
							{
								if (pageTag.get("outerHTML") != null && pageTag.get("outerHTML").equals(prev_outerHMTL))
								{
									pageerror = "outerHtml已到最后一页!";
									gather.info(pageerror);
									break;
								}
							}
							// 获取下一页连接
							prev_outerHMTL = pageTag.get("outerHTML");
							// 点击下一页
							pageTag.click();
						}
						else
						{
							pageerror = "下一页标签不存在或者已到尾页！";
							gather.info(pageerror);
							break;
						}
						String error1 = "";
						if (web.source() == null)
						{
							error1 = "页面超时没有任何内容！";
							gather.info(error1);
						}
					}
				}
			}
			else
			{
				break;
			}
		}
		return pages;
	}

	// 多线程采集列表页
	public String OpenListBean(WebSpec web, GatherBean task, boolean isthread)
	{
		int count = 0;
		int page = 0;
		int pagenum = 0;
		String prev_outerHMTL = null;
		// 分页标签
		TagItem tpage = task.GetPageTagItem();
		// 迭代标签合集
		List<IterateDrillTag> listcontain2 = task.getDrillTags();
		String con = "";
		String pageerror = "";
		int totalpage = 1;
		if (RegexValidate.isINTEGER_NEGATIVE(task.getTotalpage()))
		{
			totalpage = Integer.parseInt(task.getTotalpage());
		}
		else
		{
			// 通过正则匹对源码中分页总数
			totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), web.source());
		}
		// 解析容器标签
		while (true)
		{
			// 线程状态判断
			HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate.get(task.getTaskcode());
			Boolean isDead = threadstate.get("isDead");
			Boolean isStop = threadstate.get("isStop");
			// 线程终止状态
			if (!isDead)
			{
				// 暂停线程状态
				if (!isStop)
				{
					// 循环迭代列表标签
					List<Link> listurl = ParseDownload.IterationTags2(web.source(), listcontain2, task.getTaskurl(), task.getTaskcode());
					if (listurl.size() <= 0)
					{
						con = "未采集到任何连接数据或者已到尾页";
						gather.warn(con);
						break;
					}
					else
					{
						// 解析测试
						final List<Link> listurl2 = ParseDownload.parseGatherLinks(listurl);
						final int pagenum2 = pagenum += listurl.size();
						final String encode = task.getEncodeurl();
						final String taskcode = task.getTaskcode().toString();
						// //解析采集连接,创建新的线程
//						Thread threadlist = new Thread(new Runnable()
//						{
//							public void run()
//							{
//								ParseDownload.parseLinksDownload(listurl2, taskcode, encode, pagenum2);
//							}
//						});
//						threadlist.start();
						ParseDownload.parseLinksDownload(listurl2, taskcode, encode, pagenum2);
					}
					count += listurl.size();
					page++;
					gather.info("pageindex:" + page + "     counnt:" + count);
					if (tpage != null)
					{
						// 获取下一页标签
						Tag pageTag = getPageTag(web, tpage);
						if (pageTag.exists())
						{
							// 当前任务配置采集页面数
							if (totalpage > 0 && totalpage <= page)
							{
								pageerror = "outerHtml已到最后一页!";
								gather.info(pageerror);
								break;
							}
							else
							{
								if (pageTag.get("outerHTML") != null && pageTag.get("outerHTML").equals(prev_outerHMTL))
								{
									pageerror = "outerHtml已到最后一页!";
									gather.info(pageerror);
									break;
								}
							}
							// 获取下一页连接
							prev_outerHMTL = pageTag.get("outerHTML");
							// 点击下一页
							pageTag.click();
						}
						else
						{
							pageerror = "下一页标签不存在或者已到尾页！";
							gather.info(pageerror);
							break;
						}
						String error1 = "";
						if (web.source() == null)
						{
							error1 = "页面超时没有任何内容！";
							gather.info(error1);
						}
					}
					else
					{
						break;
					}
				}
			}
			else
			{
				break;
			}
		}
		ParseDownload.downpage = 0;
		return null;
	}

	/**
	 * 执行模拟标签组
	 * 
	 * @param web
	 *            模拟容器
	 * @param task
	 *            任务信息
	 * @param isthread
	 *            线程状态
	 * @return
	 */
	public String OpenSIMTagsBean(WebSpec web, GatherBean task, boolean isthread)
	{
		try
		{
			// 获取组信息
			List<TagGroup> grouplist = task.getSimGroupTags();
			if (grouplist != null && grouplist.size() > 0)
			{
				for (int i = 0; i < grouplist.size(); i++)
				{
					// 初始化标签组数据,必需
					grouplist.get(i).InitTagSortOut(grouplist.get(i).isIsspider());
					// 执行标签事件
					exeTag(grouplist.get(i).getGroupTags(), web);
					// 执行完成休眠等待
					web.pauseUntilReady();
					web.browser((web.browserCount() - 1)).hide();
					// 列表迭代标签
					List<IterateDrillTag> listcontain2 = grouplist.get(i).getDrillTags();
					// 需要采集，或者迭代标签大于0
					// System.out.println("ssss " + grouplist.get(i).isSIMGather());
					if (grouplist.get(i).isSIMGather() || (listcontain2 != null && listcontain2.size() > 0))
					{
						// 执行模拟事件标签
						ExeSIMListPageTags(web.browser((web.browserCount() - 1)), task, listcontain2, grouplist.get(i));
					}
				}
				return null;
			}
			else
			{
				String con = "模拟采集中没有配置任务标签组,任务编号:" + task.getTaskcode();
				gather.info(con);
				return con;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 初始化 webdriver
	 */
	public void WebDriverInit()
	{
		Properties p = new Properties();
		try
		{
			p.load(DBUtil.class.getResourceAsStream("/config/dbconf.properties"));//加载properties文件
			System.setProperty("webdriver.ie.driver",p.getProperty("webdriver.ie.driver"));//IE驱动库
//			System.setProperty("webdriver.chrome.driver", p.getProperty("webdriver.chrome.driver"));//Chrome驱动库
			username = p.getProperty("verificate.username");
			password = p.getProperty("verificate.password");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		driver = new InternetExplorerDriver();
//		driver.manage().window().setPosition(new Point(-2000, 0));//alternatively minimized the browser since there's no "window().minimize()" method
//		driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//set timeout
	}
	//执行模拟采集
	public String OpenSIMTagsBeanNew(WebDriver driver, GatherBean task)
	{
		try
		{
			List<TagGroup> grouplist = task.getSimGroupTags();// 获取组信息
			//paramlist只适用于循环采集数据时用来输入网页上文本框中的数据；比例如发票查询：发票代码，发票号码，公司注册号等列表化的数据库中数据信息
			List<SIMParam> paramlist=task.getSimparamlist();
			
			
			String actionValue=grouplist.get(0).getGroupTags().get(0).getActionValue();
			if (grouplist != null && grouplist.size() > 0)
			{
				for (int i = 0; i < grouplist.size(); i++)
				{
					// 初始化标签组数据,必需
					grouplist.get(i).InitTagSortOut(grouplist.get(i).isIsspider());
					//数据来源数据库中取出的paramlist不为空，则执行
					if(null!=paramlist&&paramlist.size()!=0){
					exeTagNext(task,grouplist.get(i).getGroupTags(),paramlist);
					break;
					}
					else{
					// 执行标签事件
					exeTagNew(grouplist.get(i).getGroupTags());}
//					companyurl=driver.getCurrentUrl();//http://www.cninfo.com.cn/information/companyinfo_n.html?fulltext?szmb000001
					// 执行完成休眠等待
					// web.browser((web.browserCount() - 1)).hide();
					// 列表迭代标签
					List<IterateDrillTag> listcontain2 = grouplist.get(i).getDrillTags();
					// 需要采集，或者迭代标签大于0
					if (grouplist.get(i).isSIMGather() || (listcontain2 != null && listcontain2.size() > 0))
					{
						ExeSIMListPageTagsNew(driver, task, listcontain2, grouplist.get(i));// 执行模拟事件标签
					}
					//无列表迭代标签就采集当前页，文件名为0.html
					else if(null==listcontain2||listcontain2.size()==0){
						ParseDownload.download(task.getTaskcode() , "0" + ".html", driver, task.getEncodeurl());
					}
					
				}
				//财报采集
				if(task.getTaskcode().equals("637")){
				task.getSimGroupTags().get(0).getGroupTags().get(0).setActionValue(String.valueOf(Integer.parseInt(actionValue)+1));//财报采集的循环
				if(Integer.parseInt(actionValue)==604000){
					return null;
				}
				else{
				return OpenSIMTagsBeanNew(driver, task);//财报采集的循环
				}}
			}
			else
			{
				String con = "模拟采集中没有配置任务标签组,任务编号:" + task.getTaskcode();
				gather.info(con);
				return con;
			}
		}
		catch(NoAlertPresentException e){
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行模拟任务中的列表页面、分页采集
	 * @param web 模拟容器
	 * @param task 执行任务信息
	 * @param listcontain2 页面标签
	 * @param group 标签组信息
	 * @return 任务执行状态
	 * @throws Exception
	 */
	public String ExeSIMListPageTagsNew(WebDriver driver, final GatherBean task, List<IterateDrillTag> listcontain2, TagGroup group)throws Exception
	{
		List<Link> allLinklist=new ArrayList<Link>();
//		WebDriver gatherlinkdriver=new ChromeDriver();
		int j=0;
		WebElement iframeelement=null;
		int count = 0;
		int page = 0;
		String prev_outerURL = null;
		TagItem tpage = task.GetPageTagItem();// 分页标签
		boolean isgatherlist = (listcontain2 != null && listcontain2.size() > 0);
		JSONArray parserArray = new JSONArray();
		int totalpage = 0;
		if (RegexValidate.isINTEGER_NEGATIVE(task.getTotalpage()))// 获取页面配置采集页数
		{
			totalpage = Integer.parseInt(task.getTotalpage());
		}
		else// 通过正则匹对源码中分页总数
		{
			totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), driver.getPageSource());
		}
		
		while (true)// 解析容器标签
		{
			String url = "";
			HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate.get(task.getTaskcode());
			Boolean isDead = threadstate.get("isDead");
			Boolean isStop = threadstate.get("isStop");
			if (!isDead)// 线程终止状态
			{
				if (!isStop)// 暂停线程状态
				{
					if (group.isSIMGather())// 组采集
					{
						ExeSIMPageTagsNew(driver, task, group, page);
					}
					if (isgatherlist)// 循环迭代列表标签
					{
						List<Link> listurl = ParseDownload.IterationTags2(driver.getPageSource(), listcontain2, task.getTaskurl(), task.getTaskcode());
						//重写的方法还没写完
//						HashMap<String,String> source_filename = ParseDownload.getLinkmap(driver,task,listcontain2,driver.getPageSource(),driver.getCurrentUrl());
						
						
						
						
						
						if (listurl.size() <= 0)
						{
							gather.info("未采集到任何详细页面连接");
							break;
						}
						else// 解析测试
						{
							url = driver.getCurrentUrl();
							final List<Link> listurl2 = ParseDownload.parseGatherLinks(listurl);
							if(allLinklist.containsAll(listurl2)){
								break;
							}
							allLinklist.addAll(listurl2);
//							ParseDownload.parseLinksDownload(listurl2, taskcode, encode, pagenum2);
							
							for (int i=0 ; i < listurl2.size(); i++)
							{
								String[] url_name=listurl2.get(i).getLinkurl().split("\\+");
								
								String nexturl=url_name[0];
								String name=url_name[1];
								j++;//本来用作文件名，当前废弃
								if (page == 1)
								{
									parserArray.add(nexturl);
								}
								if (listurl2.get(i).isGather())// 当前URl需要采集 并打开该链接
								{
									
									if(nexturl.endsWith(".PDF")|nexturl.endsWith(".pdf")){
										if(i==listurl2.size()-1){
											ParseDownload.downloadPDF(nexturl,task.getTaskcode(),name, j);
											break;
										}
										ParseDownload.downloadPDF(nexturl,task.getTaskcode(),name,j);
										
										continue;
									}
									
//									gatherlinkdriver.navigate().to(listurl2.get(i).getLinkurl());
									driver.navigate().to(listurl2.get(i).getLinkurl());
//									System.out.println(driver.getPageSource());
									//新加的
//									WebElement element=gatherlinkdriver.findElement(By.xpath("//iframe"));
//									String nexturl=element.getAttribute("src");
//									driver.navigate().to(nexturl);
									
									
									ExeSIMListIframPageTagsNew(driver, task, group, i);//TODO 
								}
							}
							
						}
						count += listurl.size();
					}
					page++;
					gather.info("pageindex:" + page + "     counnt:" + count);
					
					if (tpage != null && StringUtils.isNotBlank(url))
					{// 获取下一页标签
//						driver.navigate().to(url);
//						driver.navigate().to(companyurl);//回到000006主页
						
						
//						String currentpageurl=driver.getCurrentUrl();//获取当前的iframe的src
						
//						boolean pageTag=getPageTagNewIframe(driver, tpage, page);//点击下一页
						
						
//						Document doc=Jsoup.parse(driver.getPageSource());
//						
//						org.jsoup.nodes.Element iframe=doc.select("iframe[id]").first();
//						String nextpageurl= iframe.attr("src");
						
						
						boolean pageTag = getPageTagNew(driver, tpage);//后一页在iframe中，找到后一页并点击
						if(pageTag==false){
							iframeelement= driver.findElement(By.xpath("//iframe"));//找到iframe
							driver.switchTo().frame(iframeelement);boolean result=(driver.equals(webdriver));//跳转到iframe
							pageTag=getPageTagNew(driver, tpage);
							driver.switchTo().defaultContent();//从iframe回到主页
							webdriver=driver;
						}

						if (pageTag)
						{
							if (totalpage > 0 && totalpage <= page)
							{
									gather.info("outerHtml已到最后一页!");
									break;
							}
//							else if (driver.getCurrentUrl().equals(prev_outerURL))
//							{
//								gather.info("outerHtml已到最后一页!");
//								break;
//							}
							prev_outerURL = driver.getCurrentUrl();// 获取下一页连接
						}
						else
						{
							gather.info("下一页标签不存在或者已到尾页！");
							break;
						}
					}
					else
					{
						break;
					}
				}
			}
			else// 清空当任务
			{
				StaticVar.taskandthreadstate.remove(task.getTaskcode());
				break;
			}
		}
		if (count > 0)
		{
			gather.info("任务:" + task.getTaskcode() + " 下载完成!");
		}
//		gatherlinkdriver.quit();
		
		return (parserArray.toString());
	}
	
	
	public String CreateContidion(TagItem ti)//通过tagitem来生成xpath的条件语句
	{
		String condition = "";
		if(StringUtils.isNotEmpty(ti.getTagAttrName()))
		{
			Boolean isnum = false;
			int num = 0;
			try{ num = Integer.parseInt(ti.getTagAttrValue()); isnum=true; }
			catch(Exception e){}
			if(ti.getTagAttrName().equals("className"))
			{
				ti.setTagAttrName("class");
			}else if(ti.getTagAttrName().equals("innerText") && StringUtils.isNotEmpty(ti.getTagAttrValue()))
			{
				return "[contains(text(), '" + ti.getTagAttrValue().replaceAll("/", "") + "')]";
				
			}
			if(isnum)
			{
				condition = "[" + num + "]";
			}
			else if(StringUtils.isNotEmpty(ti.getTagAttrValue()))
			{
				condition ="[@" + ti.getTagAttrName() + "='" + ti.getTagAttrValue() + "']";
			}
			else if(StringUtils.isNotEmpty(ti.getTagAttrName()))
			{
				condition ="[@" + ti.getTagAttrName() + "]";
			}
		}
		return condition;
	}
	
	
	public void ExeSIMPageTagsNew(WebDriver driver, GatherBean task, TagGroup tgroup, int page)// 模拟标签分页采集
	{
		if (tgroup.getIframeTags() != null && tgroup.getIframeTags().size() > 0)
		{
			List<TagItem> iframetags = tgroup.getIframeTags();
			for (int i = 0; i < iframetags.size(); i++)
			{
				TagItem ti = iframetags.get(i);
				String condition = CreateContidion(ti);
				WebElement iframe = null;
				try{
					iframe = driver.findElement(By.xpath("//" + ti.getTagName() + condition));
					driver.switchTo().frame(iframe);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					gather.info("执行iframe标签不存在" + ti.getTagName() + "," + ti.getTagAttrName() + "," + ti.getTagAttrValue());
				}
			}
			gather.info("模拟采集目录:存在iframe 直接下载数据 " + task.getTaskcode() + "_SIM" + "/" + page + "==" + driver.getCurrentUrl());
			ParseDownload.download(task.getTaskcode() + "_SIM", page + ".html", driver.getPageSource(), task.getEncodeurl());
			driver.switchTo().defaultContent();
		}
//		else
//		{
//			gather.info("模拟采集目录:无iframe 直接下载数据 " + task.getTaskcode() + "_SIM" + "/" + page + "==" + driver.getCurrentUrl());
//			ParseDownload.download(task.getTaskcode() + "_SIM", page + ".html", driver, task.getEncodeurl());
//		}
	}
		
	/**
	 * 模拟采集
	 * @param web
	 * @param task
	 * @param tgroup
	 */
	public void ExeSIMListIframPageTagsNew(WebDriver driver, GatherBean task, TagGroup tgroup, int page)
	{
		page *=10;
//		int totalpage = 0;
//		String prev_outerURL = null;
		TagItem tpage = task.GetPageTagItem();// 分页标签
		
		if (tpage != null)// 下载
		{
//			if (RegexValidate.isINTEGER_NEGATIVE(task.getTotalpage()))
//			{
//				totalpage = Integer.parseInt(task.getTotalpage());
//			}
//			else
//			{
//				totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), driver.getPageSource());// 通过正则匹对源码中分页总数
//			}
			while (true)
			{
				HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate.get(task.getTaskcode());
				Boolean isDead = threadstate.get("isDead");
				Boolean isStop = threadstate.get("isStop");
				if (!isDead)// 线程终止状态
				{
					if (!isStop)// 暂停线程状态
					{
						ExeSIMPageTagsNew(driver, task, tgroup, page);// 下载
						break;
//						driver.close();
//						boolean pageTag = getPageTagNew(driver, tpage);// 获取页面标签
//						if (pageTag)
//						{
//							if (totalpage > 0 && totalpage == page)
//							{
//									gather.info("outerHtml已到最后一页!");
//									break;
//							}
//							else
//							{
//								if (driver.getCurrentUrl().equals(prev_outerURL))
//								{
//									gather.info("outerHtml已到最后一页!");
//									break;
//								}
//							}
//							prev_outerURL = driver.getCurrentUrl();// 获取下一页连接
//						}
//						else
//						{
//							gather.info("下一页标签不存在或者已到尾页！");
//							break;
//						}
//						page++;
					}
				}
				else
				{
					break;
				}
			}
		}
	}
	public boolean getPageTagNewIframe(WebDriver driver, TagItem tpage,int page)
	{
		String condition = CreateContidion(tpage);
		WebElement element = null;
		boolean result=false;
		try
		{
			element = driver.findElement(By.xpath("//" + tpage.getTagName() + condition));
		}
		catch(Exception e)
		{
			return false;
		}
		if (tpage.existchilds())
		{
			return exeTagNew(element, tpage);
		}
		for(int i=0;i<page;i++){
		result= tryClick(element);//点击下一页
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return result;
	}

	/**
	 * 获取容器标签对象
	 * @param web 采集容器
	 * @param tpage 分页标签属性对象
	 * @return Tag 容器标签对象
	 */
	public boolean getPageTagNew(WebDriver driver, TagItem tpage)
	{
		String condition = CreateContidion(tpage);
		WebElement element = null;
		try
		{
			element = driver.findElement(By.xpath("//" + tpage.getTagName() + condition));
		}
		catch(Exception e)
		{
			return false;
		}
		if (tpage.existchilds())
		{
			return exeTagNew(element, tpage);
		}
		return tryClick(element);//点击下一页
	}
	
	/**
	 * @param tag 采集容器标签对象
	 * @param ti 标签属性对象
	 * @return 采集容器标签对象
	 */
	public boolean exeTagNew(WebElement element, TagItem ti)
	{
			List<TagItem> list = ti.getChildItems();
			if(list.size()>0)
			{
				TagItem it = list.get(0);
				String condition = CreateContidion(it);
				try
				{
					element = element.findElement(By.xpath("//" + it.getTagName() + condition));
				}
				catch(Exception e)
				{
					return false;
				}
				return exeTagNew(element, ti);// 执行tag
			}
			else
			{
				return tryClick(element);//点击下一页
			}
	}
		
	/**
	 * 执行模拟任务中的列表页面、分页采集
	 * @param web 模拟容器
	 * @param task 执行任务信息
	 * @param listcontain2 页面标签
	 * @param group 标签组信息
	 * @return 任务执行状态
	 * @throws Exception
	 */
	public String ExeSIMListPageTags(WebSpec web, final GatherBean task, List<IterateDrillTag> listcontain2, TagGroup group)throws Exception
	{
		web.pauseUntilReady();
		web.hide();
		int count = 0;
		int page = 0;
		String prev_outerHMTL = null;
		TagItem tpage = task.GetPageTagItem();// 分页标签
		boolean isgatherlist = (listcontain2 != null && listcontain2.size() > 0);
		JSONArray parserArray = new JSONArray();
		int totalpage = 0;
		if (RegexValidate.isINTEGER_NEGATIVE(task.getTotalpage()))// 获取页面配置采集页数
		{
			totalpage = Integer.parseInt(task.getTotalpage());
		}
		else// 通过正则匹对源码中分页总数
		{
			totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), web.source());
		}
		// 解析容器标签
		while (true)
		{// 线程状态判断
			HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate.get(task.getTaskcode());
			Boolean isDead = threadstate.get("isDead");
			Boolean isStop = threadstate.get("isStop");
			if (!isDead)// 线程终止状态
			{
				if (!isStop)// 暂停线程状态
				{
					if (group.isSIMGather())// 组采集
					{
						ExeSIMPageTags(web, task, group, page);
					}
					if (isgatherlist)// 循环迭代列表标签
					{
						List<Link> listurl = ParseDownload.IterationTags2(web.source(), listcontain2, task.getTaskurl(), task.getTaskcode());
						if (listurl.size() <= 0)
						{
							gather.info("未采集到任何详细页面连接");
							break;
						}
						else// 解析测试
						{
							final List<Link> listurl2 = ParseDownload.parseGatherLinks(listurl);
							for (int i = 0; i < listurl2.size(); i++)
							{
								if (page == 1)
								{
									parserArray.add(listurl2.get(i).getLinkurl());
								}
								if (listurl2.get(i).isGather() && this.OpenURL(listurl2.get(i).getLinkurl()) != null)// 当前URl需要采集 并打开该链接
								{
									ExeSIMListIframPageTags(web, listurl2.get(i).getLinkurl().toString(), task, group);
								}
							}
						}
						count += listurl.size();
					}
					page++;
					gather.info("pageindex:" + page + "     counnt:" + count);
					if (tpage != null)
					{// 获取下一页标签
						Tag pageTag = getPageTag(web, tpage);
						if (pageTag.exists())
						{
							if (totalpage > 0)
							{
								if (totalpage == page)
								{
									gather.info("outerHtml已到最后一页!");
									break;
								}
							}
							else
							{
								if (pageTag.get("outerHTML") != null && pageTag.get("outerHTML").equals(prev_outerHMTL))
								{
									gather.info("outerHtml已到最后一页!");
									break;
								}
							}
							prev_outerHMTL = pageTag.get("outerHTML");// 获取下一页连接
							pageTag.click();// 点击下一页
						}
						else
						{
							gather.info("下一页标签不存在或者已到尾页！");
							break;
						}
					}
					else
					{
						break;
					}
				}
			}
			else// 清空当任务
			{
				StaticVar.taskandthreadstate.remove(task.getTaskcode());
				break;
			}
		}
		if (count > 0)
		{
			gather.info("任务:" + task.getTaskcode() + " 下载完成!");
		}
		return (parserArray.toString());
	}

	/**
	 * 模拟采集
	 * @param web
	 * @param links
	 * @param task
	 * @param tgroup
	 */
	public void ExeSIMListIframPageTags(WebSpec web, String links, GatherBean task, TagGroup tgroup)
	{
		int page = 0;
		int totalpage = 0;
		String prev_outerHMTL = null;
		TagItem tpage = task.GetPageTagItem();// 分页标签
		if (tpage != null)// 下载
		{
			if (RegexValidate.isINTEGER_NEGATIVE(task.getTotalpage()))
			{
				totalpage = Integer.parseInt(task.getTotalpage());
			}
			else
			{
				totalpage = new parserHTML().getPageToHtml(task.getTotalpage(), web.source());// 通过正则匹对源码中分页总数
			}
			while (true)
			{
				HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate.get(task.getTaskcode());
				Boolean isDead = threadstate.get("isDead");
				Boolean isStop = threadstate.get("isStop");
				if (!isDead)// 线程终止状态
				{
					if (!isStop)// 暂停线程状态
					{
						ExeSIMPageTags(web, task, tgroup, page);// 下载
						Tag pageTag = getPageTag(web, tpage);// 获取页面标签
						if (pageTag.exists())
						{
							if (totalpage > 0)
							{
								if (totalpage == page)
								{
									gather.info("outerHtml已到最后一页!");
									break;
								}
							}
							else
							{
								if (pageTag.get("outerHTML") != null && pageTag.get("outerHTML").equals(prev_outerHMTL))
								{
									gather.info("outerHtml已到最后一页!");
									break;
								}
							}
							prev_outerHMTL = pageTag.get("outerHTML");// 获取下一页连接
							pageTag.click();// 点击下一页
						}
						else
						{
							gather.info("下一页标签不存在或者已到尾页！");
							break;
						}
						page++;
					}
				}
				else
				{
					break;
				}
			}
		}
	}

	public void ExeSIMPageTags(WebSpec web, GatherBean task, TagGroup tgroup, int page)// 模拟标签分页采集
	{
		if (tgroup.getIframeTags() != null && tgroup.getIframeTags().size() > 0)
		{
			Tag tag = parseIframeTag(web, tgroup.getIframeTags());
			if (tag != null && tag.exists())
			{
				String source = tag.find.html().get.innerHTML();
				gather.info("模拟采集目录:存在iframe 直接下载数据 " + task.getTaskcode() + "_SIM" + "/" + page + "==" + web.url());
				ParseDownload.download(task.getTaskcode() + "_SIM", page + ".html", source, task.getEncodeurl());
			}
		}
		else
		{
			gather.info("模拟采集目录:无iframe 直接下载数据 " + task.getTaskcode() + "_SIM" + "/" + page + "==" + web.url());
			ParseDownload.download(task.getTaskcode() + "_SIM", page + ".html", web, task.getEncodeurl());
		}
	}

	/**
	 * 获取容器标签对象
	 * @param web 采集容器
	 * @param tpage 分页标签属性对象
	 * @return Tag 容器标签对象
	 */
	public Tag getPageTag(WebSpec web, TagItem tpage)
	{
		Tag tag = web.find(tpage.getTagName()).with(tpage.getTagAttrName(), tpage.getTagAttrValue());
		if (tpage.existchilds())
		{
			return exeTag(tag, tpage);
		}
		return tag;
	}

	/**
	 * @param tag 采集容器标签对象
	 * @param ti 标签属性对象
	 * @return 采集容器标签对象
	 */
	public Tag exeTag(Tag tag, TagItem ti)
	{
		if (tag.exists())
		{
			List<TagItem> list = ti.getChildItems();
			if(list.size()>0)
			{
				TagItem it = list.get(0);
				Tag t = tag.find(it.getTagName()).with(it.getTagAttrName(), it.getTagAttrValue());
				return exeTag(t, ti);// 执行tag
			}
		}
		return tag;
	}
	
	public Tag parseIframeTag(WebSpec web, List<TagItem> iframetags)// 解析多级iframe标签
	{
		Tag tag = web.find(iframetags.get(0).getTagName()).with(iframetags.get(0).getTagAttrName(), iframetags.get(0).getTagAttrValue());
		if (iframetags.size() > 1)
		{
			for (int i = 1; i < iframetags.size(); i++)
			{
				tag = tag.find(iframetags.get(i).getTagName()).with(iframetags.get(i).getTagAttrName(),iframetags.get(i).getTagAttrValue());
			}
		}
		return tag;
	}

	public WebSpec getGatherWeb()
	{
		return gatherWeb;
	}

	/**
	 * 释放
	 */
	public void distory()
	{
		StaticVar.ruleMap.clear();
		StaticVar.tagruleMap.clear();
		StaticVar.relateMap.clear();
		StaticVar.tagMap.clear();
		StaticVar.notNullField.clear();
		StaticVar.taskheavy.clear();
	}
	public String getIframe(TagItem ti){
		String condition = CreateContidion(ti);
		WebElement element=null;
		String tagname = ti.getTagName();
		String html=webdriver.getPageSource();
//		System.out.println(html);
			element = webdriver.findElement(By.xpath("//" + ti.getTagName() + condition));
			if(element==null){
				gather.info("未找到指定容器标签" + ti.getTagName());
			}
			webdriver.switchTo().frame(element);
			String iframehtml=webdriver.getPageSource();
			webdriver.switchTo().defaultContent();
			
			return iframehtml;
	}
	
	public static void main(String[] args) throws IOException
	{
		System.setProperty("webdriver.chrome.driver", "C:/Users/Administrator/Workspaces/MyEclipse 10/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
//		driver.manage().window().setPosition(new Point(-2000, 0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try{
			driver.navigate().to("http://shixin.court.gov.cn/");
			driver.findElement(By.id("pName")).sendKeys("宁波五福机电有限公司");
			WebElement vcode = driver.findElement(By.id("pCode"));
			vcode.click();
			WebElement img = driver.findElement(By.id("captchaImg"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", img );
			Point point = img.getLocation();
			Dimension dim = img.getSize();
			BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
			BufferedImage croppedImage = originalImage.getSubimage(point.getX(), 0, dim.width, dim.height);
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			ImageIO.write(croppedImage, "bmp", os);
			byte[] data = os.toByteArray();
			String code = new Verification().Verificate("talonlei", "JiaYi921109", data, 200);
			vcode.sendKeys(code);
			driver.findElement(By.className("login_button")).click();
			
			System.out.println("finished! Press any key to exit.");
			Scanner sc = new Scanner(System.in);
			sc.next();
			driver.quit();
		}catch(Exception e)
		{
			e.printStackTrace();
			driver.quit();
		}
	
	}
}
