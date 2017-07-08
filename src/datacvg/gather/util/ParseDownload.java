﻿package datacvg.gather.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.watij.webspec.dsl.WebSpec;

import com.core.tag.util.SystemConstant;

import core.spider.entity.StaticVar;
import core.util.RegexValidate;
import core.util.WebConnection;

import datacvg.gather.embeddedDB.DBInstance;
import datacvg.gather.entity.GatherBean;
import datacvg.gather.entity.IterateDrillTag;
import datacvg.gather.entity.Link;

/**
 * 
 * 
 * 解析下载
 * 
 */
public class ParseDownload {
	private static final Logger gather = Logger.getLogger(ParseDownload.class);
	static int k = 0;
	public static int downpage = 0;
	public static String base = null;

	/**
	 * 迭代采集连接 2
	 * 
	 * @param websouce
	 *            页面源码
	 * @param listcontain
	 *            迭代标签合集
	 * @param siteurl
	 *            采集站点
	 * @return 链接合集
	 */
	public static List<Link> IterationTags2(String websouce,
			List<IterateDrillTag> listcontain, String siteurl) {

		return IterationTags2(websouce, listcontain, siteurl, null, null, null);
	}

	/**
	 * 迭代采集连接
	 * 
	 * @param websouce
	 *            页面源码
	 * @param listcontain
	 *            迭代标签合集
	 * @param siteurl
	 *            采集站点
	 * @param taskcode
	 *            任务编号
	 * @return 链接合集
	 */
	public static List<Link> IterationTags2(String websouce,
			List<IterateDrillTag> listcontain, String siteurl, String taskcode) {

		return IterationTags2(websouce, listcontain, siteurl, null, null,
				taskcode);
	}

	/**
	 * 迭代采集连接 1
	 * 
	 * @param websouce
	 *            html源码
	 * @param listcontain迭代标签合集
	 * @param siteurl
	 *            采集站点
	 * @param listlink
	 *            链接存储合集对象
	 * @return 链接合集
	 */
	public static List<Link> IterationTags2(String websouce,
			List<IterateDrillTag> listcontain, String siteurl,
			List<Link> listlink) {
		return IterationTags2(websouce, listcontain, siteurl, listlink, null,
				null);
	}

	/**
	 * 迭代采集连接 1
	 * 
	 * @param websouce
	 *            html源码
	 * @param listcontain迭代标签合集
	 * @param siteurl
	 *            采集站点
	 * @param listlink
	 *            链接存储合集对象
	 * @param plink
	 *            链接父对象
	 * @return 链接合集
	 */
	public static List<Link> IterationTags2(String websouce,
			List<IterateDrillTag> listcontain, String siteurl,
			List<Link> listlink, Link plink, String taskcode) {
		boolean parserflag = true;
		// 初始化
		if (listlink == null) {
			listlink = new ArrayList<Link>();
			
		}
		if (plink == null) {
			plink = new Link();
			plink.setLinkurl("siteurl");
			// plink.setGather(false);
		}
		

		Document doc = Jsoup.parse(websouce);
		k++;
		// logger.info("运行:"+k);
		if (listcontain.size() > 0) {
			// eles:网页的html全部节点
//			Elements eles = doc.select("html");
			Elements eles=doc.select("body");
//			System.out.println(eles);
			for (int i = 0; i < listcontain.size(); i++) {
				IterateDrillTag dtag = listcontain.get(i);
				TagItem ti = dtag.getTag();
				// 判断类型 1 容器标签(存在子标签)
				if (IterateDrillTag.CONTAIN_TAG.equals(dtag.getType())) {
					eles = findContainTag(eles, ti);
					if (eles != null && eles.size() == 0) {
						gather.info("未找到指定容器标签" + dtag.getTag().getTagName());
						break;
					}
				} else {
					// 是否采集
					// !ti.getTargetAttr().equals("")
					if (!ti.isGather) {
						eles = getElements(eles, ti);
						if (eles != null && eles.size() == 0) {
							gather.info("未找到目标标签" + dtag.getTag().getTagName());
							break;
						}
						gather.info("link===" + eles.size());

						for (int j = 0; j < eles.size(); j++) {
							while (parserflag) {
								HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate
										.get(taskcode);
								if (threadstate != null
										&& threadstate.size() > 0) {
									Boolean isDead = threadstate.get("isDead");
									Boolean isStop = threadstate.get("isStop");
									if (!isDead) {
										if (!isStop) {
											Link link = new Link();
											// 设置采集状态
											link.setGather(dtag.isGather());
											// 获取路径
											String targetarrt = eles.get(j)
													.attr(ti.getTargetAttr());
											String filename=eles.get(j).text();//文件名
											

											// 特殊属性是否存在正则解析
											if (ti.getTargetRegexStr() != null
													&& !"".equals(ti
															.getTargetRegexStr())) {
												targetarrt = RegexHtmlUrl(
														targetarrt,
														ti.getTargetRegexStr());
											}
											// 解析连接合法性
											targetarrt = GetLink.getRealURL(
													siteurl, targetarrt);
											if (targetarrt != null
													&& !targetarrt.trim()
															.equals("")) {
												// 拼接完整的url
												gather.info(targetarrt+filename);
												//获取的link为url+文件名
												targetarrt=targetarrt+"+"+filename;
												link.setLinkurl(targetarrt);
												// 父级连接
												plink.add(link);
												link.setParentLink(plink);
												listlink.add(link);

												break;
											}

											else
												break;
										}
									} else {
										parserflag = false;
										i = eles.size();
										break;
									}
								} else {
									break;
								}
							}
						}
					}
					// 是否钻取
					if (dtag.isDrill()) {
						// 解析钻取标签
						List<IterateDrillTag> tagdrills = ChildTagList(
								listcontain, dtag, null);
						if (null != tagdrills && tagdrills.size() > 0) {
							for (int j = 0; j < listlink.size(); j++) {
								// 解析路径
								String url = listlink.get(j).getLinkurl();
								// 钻取源码
								String soucehtml = DrillSouceHtmlToTime(url);
								int n = 0;
								// 两次请求
								while (true) {
									if (n > 2) {
										break;
									}
									if (soucehtml == null) {
										soucehtml = DrillSouceHtmlToTime(url);
										if (soucehtml != null) {
											n = 2;
										}
									} else {
										break;
									}
									n++;
								}
								if (soucehtml != null) {
									if (null != tagdrills
											&& tagdrills.size() > 0) {
										// 递归
										List<Link> listl = IterationTags2(
												soucehtml, tagdrills, url,
												listlink.get(j).getChildLink(),
												listlink.get(j), taskcode);
										if (listl != null && listl.size() > 0) {
											listlink.get(j).setChildLink(listl);
											listlink.get(j).setChildlivel(1);
										}
									}
								} else {
									gather.info("钻取超时发生在标签："
											+ dtag.getTag().getTagName()
											+ ",级别：" + i);
								}
							}
						}
					}

					// 遇到钻取后面标签全部忽略
					 if (dtag.isDrill())
					 {
					 break;
					 }

				}
			}
		}
		return listlink;
	}
//public static HashMap<String, String> getLinkmap(WebDriver driver,GatherBean task,List<IterateDrillTag> listtag,String html,String beginurl){
//	HashMap<String, String> source_filename=new HashMap<String, String>();
//	Document doc=Jsoup.parse(html);
//	Elements eles=doc.select("html");
//	source_filename.put(beginurl, "首页");
//	//迭代
//	source_filename=getLinkmap(source_filename, listtag, task);
//	return source_filename;
//}
//	
//public static HashMap<String,String> getLinkmap(HashMap<String, String> source_filename,List<IterateDrillTag> listtag,GatherBean task){
//	ChromeDriver driver=new ChromeDriver();
//	for(String url:source_filename.keySet()){
//		driver.navigate().to(url);
//		Document doc=Jsoup.parse(driver.getPageSource());
//		Elements eles=doc.select("html");
//		for(IterateDrillTag tag:listtag){
//			TagItem ti=tag.getTag();
//			if(tag.getType().equals(IterateDrillTag.CONTAIN_TAG)){
//				eles = findContainTag(eles, ti);
//				if (eles != null && eles.size() == 0) {
//					gather.info("未找到指定容器标签" + tag.getTag().getTagName());
//					break;
//				}
//			}
//			else{
//				eles=getElements(eles, ti);
//				for(org.jsoup.nodes.Element ele:eles){
//					String filename=ele.text();
//					String sourceurl=ele.attr(ti.getTargetAttr());
//					if (ti.getTargetRegexStr() != null&& !"".equals(ti.getTargetRegexStr())) {
//						sourceurl = RegexHtmlUrl(sourceurl,ti.getTargetRegexStr());
//					}
//					// 解析连接合法性
//					sourceurl = GetLink.getRealURL(task.getTaskurl(), sourceurl);
//					if (sourceurl != null&& !sourceurl.trim().equals("")) {
//						// 拼接完整的url
//						gather.info(sourceurl);
//					}
//					source_filename.put(sourceurl, filename);
//				}
//			
//			}
//		}
//	}
//	return source_filename;
//}


	/**
	 * 循环定位容器标签
	 * 
	 * @param eles
	 * @param ti
	 * @return 容器标签eles
	 */
	public static Elements findContainTag(Elements eles, TagItem ti) {
//		System.out.println(eles);
		eles = getElements(eles, ti);
		// 当容器标签是iframe时，获取iframe
		if (ti.getTagName().equals("iframe") | eles == null) {
			Document iframedocument = Jsoup
					.parse(new GatherExe().getIframe(ti));
			// System.out.println(doc);
			eles = iframedocument.select("body");
		}
		return eles;
	}

	/**
	 * 获取标签节点对象
	 * 
	 * @param eles
	 *            源节点对象
	 * @param ti
	 *            标签属性对象
	 * @return 节点
	 */
	public static Elements getElements(Elements eles, TagItem ti) {
		Elements listele = new Elements();
		// 如果属性为空
		if (ti.getTagAttrName().equals("") || ti.getTagAttrValue().equals("")) {
			// 迭代层标签集合

			for (int i = 0; i < eles.size(); i++) {

				Elements tempEles = eles.get(i).select(ti.getTagName());
				if (tempEles.size() == 1) {
					listele.add(tempEles.get(0));
				} else {
					for (int j = 0; j < tempEles.size(); j++) {
						listele.add(tempEles.get(j));
					}
				}
			}
			return listele;

		} else {

			for (int i = 0; i < eles.size(); i++) {
				// 判断属性是否为innerText,innerHTML
				if (ti.getTagAttrName().indexOf("inner") >= 0) {
					Elements tempEles = eles.get(i)
							.getElementsContainingOwnText(ti.getTagAttrValue());
					if (tempEles.size() == 1) {
						listele.add(tempEles.get(0));
					} else {
						for (int j = 0; j < tempEles.size(); j++) {
							listele.add(tempEles.get(j));
						}
					}
					return listele;
				}
				// 一般属性解析
				String attr = ti.getTagAttrName().indexOf("class") >= 0 ? "class"
						: ti.getTagAttrName();
				// eles.get(i).
				// eles.get(i).getElementsByAttributeValue(key, value)
				Elements tempEles = eles.get(i).getElementsByAttributeValue(
						attr, ti.getTagAttrValue());
				if (tempEles.size() == 1) {
					listele.add(tempEles.get(0));
				} else {
					for (int j = 0; j < tempEles.size(); j++) {
						listele.add(tempEles.get(j));
					}
				}

				// logger.info(eles.get(i).getElementsByAttributeValue(attr,
				// ti.getTagAttrValue()).html());
			}
			return listele;
		}

	}

	/**
	 * 钻取链接
	 * 
	 * @param url
	 *            链接
	 * @return 链接源码
	 */
	public static String DrillSouceHtml(String url) {

		try {
			Document doc = Jsoup.connect(url).get();
			return doc.html();
		} catch (Exception e) {

			gather.info("钻取连接超时：" + url);

		}
		return null;
	}

	/**
	 * 钻取链接
	 * 
	 * @param url
	 *            链接
	 * @return 链接源码
	 */
	public static String DrillSouceHtmlToTime(String url) {

		try {
			Document doc = Jsoup.connect(url).timeout(20000).get();
			return doc.html();
		} catch (Exception e) {

			gather.info("钻取连接超时：" + url);

		}
		return null;
	}

	/**
	 * 获取迭代子标签
	 * 
	 * @param listp
	 *            源标签合集ȫ����ǩ
	 * @param dt
	 *            当前标签
	 * @param childlist
	 *            子标签存储对象
	 * @return 子标签合集
	 */
	public static List<IterateDrillTag> ChildTagList(
			List<IterateDrillTag> listp, IterateDrillTag dt,
			List<IterateDrillTag> childlist) {

		if (childlist == null) {
			childlist = new ArrayList<IterateDrillTag>();
		}
		for (int i = 0; i < listp.size(); i++) {

			if (listp.get(i).getParentCode().equals(dt.getCode())) {
				childlist.add(listp.get(i));
				ChildTagList(listp, listp.get(i), childlist);
				break;
			}

		}
		return childlist;
	}

	/**
	 * 解析下载网页
	 * 
	 * @param links
	 *            解析网址集合
	 * @param lflagag
	 *            文件下载目录
	 * @param encode
	 *            文件编码
	 * @param curpage
	 *            当前页数
	 * @param downleves
	 *            保存目录层级
	 * @param totalpagenum
	 *            文件下载总数
	 */
	public static void parseChildLinksDownload(List<Link> links,
			String lflagag, String encode) {
		boolean downflag = true;
		String taskcode = null;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(lflagag);
		if (matcher.find()) {
			taskcode = matcher.group(1);
		}
		int downpagenum = downpage += links.size();// totalpagenum;
		// 线程终止状态
		for (int i = 0; i < links.size(); i++) {
			while (downflag) {
				// 线程状态判断
				HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate
						.get(taskcode);
				Boolean isDead = threadstate.get("isDead");
				Boolean isStop = threadstate.get("isStop");
				// 线程终止状态
				if (!isDead) {
					// 暂停线程状态
					if (!isStop) {
						Link link = links.get(i);
						// 如果是采集
						if (link.isGather()) {
							downpagenum--;
							gather.info("钻取采集目录:" + lflagag + "/" + downpagenum
									+ ".html" + "==" + link.getLinkurl());
							downLoads(lflagag, downpagenum + ".html",
									link.getLinkurl(), encode);
							// 存在子链接
							if (link.isExistChildLink()) {
								// downpagenum +=link.getChildLink().size();
								parseChildLinksDownload(link.getChildLink(),
										lflagag, encode);
							}
							break;
						}
					}
				} else {
					downpage = 0;
					i = links.size();
					downflag = false;
					break;
				}
			}
		}
	}

	/**
	 * 解析下载网页
	 * 
	 * @param links
	 *            链接对象
	 * @param lflagag
	 *            标识
	 * @param encode
	 *            链接内容页编码
	 */
	public static void parseLinksDownload(List<Link> links, String lflagag,
			String encode, int linksnum) {

		boolean downflag = true;
		String taskcode = null;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(lflagag);
		if (matcher.find()) {
			taskcode = matcher.group(1);
		}
		for (int i = 0; i < links.size(); i++) {
			while (downflag) {
				// 线程状态判断
				HashMap<String, Boolean> threadstate = StaticVar.taskandthreadstate
						.get(taskcode);
				Boolean isDead = threadstate.get("isDead");
				Boolean isStop = threadstate.get("isStop");
				// 线程终止状态
				if (!isDead) {
					// 暂停线程状态
					if (!isStop) {
						Link link = links.get(i);
						// 如果是采集
						if (link.isGather()) {
							if (linksnum == links.size()) {
								gather.debug("分页采集目录:" + lflagag + "/" + i
										+ "==" + link.getLinkurl());
								// 下载文件
								downLoads(lflagag, i + ".html",
										link.getLinkurl(), encode);
								// 存在子链接
								if (link.isExistChildLink()) {
									parseChildLinksDownload(
											link.getChildLink(), taskcode + "_"
													+ link.getChildlivel(),
											encode);
								}
							} else {
								linksnum--;
								gather.debug("分页采集目录:" + lflagag + "/"
										+ linksnum + "==" + link.getLinkurl());
								// 下载文件
								downLoads(lflagag, linksnum + ".html",
										link.getLinkurl(), encode);
								// 存在子链接
								if (link.isExistChildLink()) {
									parseChildLinksDownload(
											link.getChildLink(), taskcode + "_"
													+ link.getChildlivel(),
											encode);
								}
							}
							break;
						}
					}
				} else {
					downpage = 0;
					i = links.size();
					downflag = false;
					break;
				}
			}
		}
	}

	/**
	 * 解析采集链接
	 * 
	 * @param links
	 * @return 链接合集
	 */
	public static List<Link> parseGatherLinks(List<Link> links) {

		return parseGatherLinks(links, null);
	}

	/**
	 * 解析剔除无效链接
	 * 
	 * @param links
	 *            原链接合集
	 * @param newlinks
	 *            新链接合集
	 * @return 需要采集链接合集
	 */
	public static List<Link> parseGatherLinks(List<Link> links,
			List<Link> newlinks) {
		if (newlinks == null) {
			newlinks = new ArrayList<Link>();
		}
		for (int i = 0; i < links.size(); i++) {
			Link link = links.get(i);

			if (link.isGather()) {
				Link newlink = new Link();
				newlink.setGather(true);
				newlink.setLinkurl(link.getLinkurl());
				// newlink.setExistChildLink(link.isExistChildLink());
				if (link.isExistChildLink()) {
					List<Link> templist = parseGatherLinks(link.getChildLink(),
							null);// TODO 有待了解

					newlink.setChildLink(templist);
				}
				newlinks.add(newlink);
			} else {
				if (link.isExistChildLink()) {
					List<Link> templist = link.getChildLink();
					for (int j = 0; j < templist.size(); j++) {
						newlinks.add(templist.get(j));
					}

				}
			}

		}
		return newlinks;
	}

	/**
	 * 网页下载
	 * 
	 * @param dir
	 *            目录
	 * @param filename
	 *            文件名
	 * @param downurl
	 *            网页链接
	 * @param encode
	 *            网页编码
	 */
	public static void downLoads(String dir, String filename, String downurl,
			String encode) {

		// 获取采集跟目录
		if (base == null) {
			Object objbase = SystemConstant.get("gather_root_dir");
			// ApplicationContext.singleton().getValue("gather_root_dir");
			if (objbase == null) {
				base = "d://gather//";
			} else {
				base = objbase.toString();
			}
		}
		String path = base + "/" + dir + "/" + filename;
		if (DBInstance.mydb.contains(downurl)) {
			gather.debug(downurl + "已访问，过滤。。。");
			return;
		}
		int sleeptime = (int) (Math.random() * 5000);// 每2个采集页间隔5秒内随机时间
		// System.out.println("任务:"+ downurl.split("/")[2] + "等待" +
		// ((double)sleeptime)/1000);
		try {
			Thread.sleep(sleeptime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 默认不存在创建目录
		createFile(path);
		// 下载数据
		String taskcode = null;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(dir);
		if (matcher.find()) {
			taskcode = matcher.group(1);
		}
		savedata(path, downurl, encode);
		StaticVar.taskflie.put(dir, taskcode);
		gather.debug(downurl + "存入缓存数据库");
		DBInstance.mydb.put(downurl, filename);
	}

	/**
	 * 下载网页 到本地
	 * 
	 * @param dir
	 *            保存目录
	 * @param filename
	 *            保存文件名称
	 * @param web
	 *            webspec 容器
	 * @param encode
	 *            网站编码
	 */
	public static void download(String dir, String filename, WebSpec web,
			String encode) {
		// 获取采集跟目录
		String taskcode = null;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(dir);
		if (matcher.find()) {
			taskcode = matcher.group(1);
		}
		if (base == null) {
			Object objbase = SystemConstant.get("gather_root_dir");// ApplicationContext.singleton().getValue("gather_root_dir");
			if (objbase == null) {
				base = "d://gather//";
			} else {
				base = objbase.toString();
			}
		}
		String downurl = web.url();
		String soucehtml = web.source();
		String taskheavy = StaticVar.taskheavy.get("taskcode");
		// 排重状态位不为空，并且taskheavy 为T
		if (RegexValidate.StrNotNull(taskheavy) && taskheavy.equals("T")) {
			if (DBInstance.mydb.contains(downurl)) {
				gather.debug(downurl + "已访问，过滤。。。");
				return;
			}
		}
		String path = base + "/" + dir + "/" + filename;
		try {
			// 默认不存在创建目录
			File file = createFile(path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encode));
			bw.write(soucehtml);
			bw.close();
			StaticVar.taskflie.put(dir, taskcode);
			gather.debug(downurl + "存入缓存数据库");
			DBInstance.mydb.put(downurl, taskcode);
		} catch (Exception e) {
			gather.info("采集文件保存错误!!");
			e.printStackTrace();
		}
	}
/**
 * 
 * @param url资源地址
 * @param taskcode 任务编号
 * @param i    截取网页的text：文件全名
 */
	public static void downloadPDF(String url, String taskcode, String name,int  i) {
		String[] groupname=name.split(":");
		String uppername=groupname[0];
		String finalname=groupname[1];
		if (base == null) {
			Object objbase = SystemConstant.get("gather_root_dir");// ApplicationContext.singleton().getValue("gather_root_dir");
			if (objbase == null) {
				base = "d://gather//";
			} else {
				base = objbase.toString();
			}
		}
		
		File taskFile=new File(base+"/"+taskcode);
		if(!taskFile.exists()){
			taskFile.mkdir();
		}
		File upperFile=new File(base+"/"+taskcode+"/"+uppername);
		if(!upperFile.exists()){
			upperFile.mkdir();
		}
		HttpClient httpclient = new HttpClient();
		httpclient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		byte[] responseBody = null;
		try {
			int statusCode = httpclient.executeMethod(getMethod);
			// if(statusCode!=200);
			// System.err.println("Method failed:"+getMethod.getStatusLine());
			String filePath = base + "/" + taskcode + "/" + uppername+"/"+ finalname+i+ ".pdf";
			responseBody = getMethod.getResponseBody();

			File file = new File(filePath);

			FileOutputStream fo = new FileOutputStream(file);
			fo.write(responseBody);
			fo.flush();
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载网页 到本地
	 * 
	 * @param dir
	 *            保存目录
	 * @param filename
	 *            保存文件名称
	 * @param web
	 *            webspec 容器
	 * @param encode
	 *            网站编码
	 */
	public static void download(String dir, String filename, WebDriver driver,
			String encode) {
		// 获取采集跟目录
		String taskcode = null;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(dir);
		if (matcher.find()) {
			taskcode = matcher.group(1);
		}
		if (base == null) {
			Object objbase = SystemConstant.get("gather_root_dir");// ApplicationContext.singleton().getValue("gather_root_dir");
			if (objbase == null) {
				base = "d://gather//";
			} else {
				base = objbase.toString();
			}
		}
		String downurl = driver.getCurrentUrl();
		String soucehtml = driver.getPageSource();
		String taskheavy = StaticVar.taskheavy.get("taskcode");
		// 排重状态位不为空，并且taskheavy 为T
		if (RegexValidate.StrNotNull(taskheavy) && taskheavy.equals("T")) {
			if (DBInstance.mydb.contains(downurl)) {
				gather.debug(downurl + "已访问，过滤。。。");
				return;
			}
		}
		String path = base + "/" + dir + "/" + filename;
		try {
			File file = createFile(path);// 默认不存在创建目录
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encode));
			bw.write(soucehtml);
			bw.close();
			StaticVar.taskflie.put(dir, taskcode);
			gather.debug(downurl + "存入缓存数据库");
			DBInstance.mydb.put(downurl, taskcode);
		} catch (Exception e) {
			gather.info("采集文件保存错误!!");
			e.printStackTrace();
		}
	}

	/**
	 * 下载网页 重载1
	 * 
	 * @param dir
	 *            二级目录名
	 * @param filename
	 *            文件名
	 * @param soucehtml
	 *            源码字符串
	 */
	public static void download(String dir, String filename, String soucehtml,
			String encode) {
		// 获取采集跟目录
		String taskcode = null;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(dir);
		if (matcher.find()) {
			taskcode = matcher.group(1);
		}
		if (base == null) {
			Object objbase = SystemConstant.get("gather_root_dir");// ApplicationContext.singleton().getValue("gather_root_dir");
			if (objbase == null) {
				base = "d://gather//";
			} else {
				base = objbase.toString();
			}
		}

		String path = base + "/" + dir + "/" + filename;
		try {
			// 默认不存在创建目录
			File file = createFile(path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encode));
			// BufferedWriter bw = new BufferedWriter(new
			// FileWriter(file+".html"));
			bw.write(soucehtml);
			bw.close();
			StaticVar.taskflie.put(dir, taskcode);
		} catch (Exception e) {

			gather.info("采集文件保存错误!!");
			e.printStackTrace();
		}

	}

	/**
	 * 下载网页
	 * 
	 * @param soucehtml
	 *            源码字符串
	 */
	public static void download(String filename, String soucehtml) {
		// 获取采集跟目录
		if (base == null) {
			Object objbase = SystemConstant.get("gather_root_dir");// ApplicationContext.singleton().getValue("gather_root_dir");
			if (objbase == null) {
				base = "d://gather//";
			} else {
				base = objbase.toString();
			}
		}
		String path = base + "/" + filename;
		// 默认不存在创建目录
		createFile(path);

		try {
			File file = new File(filename);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(soucehtml);
			bw.close();
		} catch (Exception e) {

			gather.info("采集文件保存错误!!");
			e.printStackTrace();
		}

	}

	/**
	 * 创建任意深度的文件所在文件夹,可以用来替代直接new File(path)。
	 * 
	 * @param path
	 * @return File对象
	 */
	public static File createFile(String path) {
		File file = new File(path);
		// 寻找父目录是否存在
		File parent = new File(file.getAbsolutePath().substring(0,
				file.getAbsolutePath().lastIndexOf(File.separator)));
		// 如果父目录不存在，则递归寻找更上一层目录
		if (!parent.exists()) {
			createFile(parent.getPath());
			// 创建父目录
			parent.mkdirs();
		}
		return file;
	}

	/**
	 * 保存采集文件
	 * 
	 * @param filename
	 *            保存地址
	 * @param url
	 *            网页地址
	 * @param encode
	 *            网页编码
	 */
	public static void savedata(String filename, String url, String encode) {
		try {
			// encode = "UTF-8";
			String soucehtml = RequestHTML(url, encode);
			int n = 0;
			// 两次请求
			while (true) {

				if (n > 1) {
					break;
				}
				if (soucehtml == null) {
					soucehtml = RequestHTML(url, encode);
				} else {
					break;
				}
				n++;
			}
			if (soucehtml == null) {
				gather.info("采集网页失败" + url);
				return;
			}
			File file = new File(filename);
			// BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encode));

			bw.write(soucehtml);
			bw.close();
		} catch (Exception e) {

			gather.info("采集文件保存错误!!");
			e.printStackTrace();
		}

	}

	/**
	 * 请求下载html源码
	 * 
	 * @param url
	 *            下载链接
	 * @return 源码字符
	 */
	public static String RequestHTML(String url, String encode) {

		try {
			return WebConnection.getHtmlSource(url, encode);
		} catch (Exception e) {
			// LogWritter.bizInfo("采集网页超时"+url);
		}
		return null;
	}

	// 匹配htmlurl
	public static String RegexHtmlUrl(String sourceStr, String regexstr) {
		try {
			// 如果怕出现默认分割符号|,则用前后缀取值
			if (regexstr.indexOf("|") >= 0) {
				String[] pre_suff = regexstr.split("[|]");

				return StringUtils.substringBetween(sourceStr, pre_suff[0],
						pre_suff[1]);
			} else {
				// 正则取值
				Pattern pattern = Pattern.compile(regexstr);
				Matcher matcher = pattern.matcher(sourceStr);
				if (matcher.find()) {
					return matcher.group(1);
				}
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static void main(String[] args) throws IOException{
		Document doc=Jsoup.parse(new File("D:\\h5\\11.html"), "UTF-8");
		Elements eles=doc.select("div[class=sse_list_1 list ]");
		System.out.println(eles);
	}

}