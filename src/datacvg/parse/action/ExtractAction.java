package datacvg.parse.action;

import haier.dataspider.param.service.SpringContextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.core.tag.util.SystemConstant;

import core.spider.entity.RuleTemplate;
import core.spider.entity.StaticVar;
import core.spider.entity.TagruleTemplate;
import core.spider.fun.DeleteFile;
import core.spider.fun.HtmlReader;
import core.spider.pagedetail.RuleExtract;
import core.spider.pagedetail.TagExtract;
import core.util.RegexValidate;
import datacvg.parse.entity.ParserInfo;
import datacvg.parse.service.ParserTaskService;
import datacvg.parse.service.ParseroperateTask;

/**
 * 执行网络采集数据解析入口
 * 
 * @author admin
 * 
 */
public class ExtractAction
{

	private static final Logger parser = Logger.getLogger(ExtractAction.class);
	// 采集目录
	private String rootPath;
	// 文件名称
	private String fileName;

	public String getRootPath()
	{
		return rootPath;
	}

	public void setRootPath(String rootPath)
	{
		this.rootPath = rootPath;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	ParserTaskService parsertaskService;

	public ParserTaskService getParsertaskService()
	{
		return parsertaskService;
	}

	public void setParsertaskService(ParserTaskService parsertaskService)
	{
		this.parsertaskService = parsertaskService;
	}

	ParseroperateTask paseroptask;

	public ParseroperateTask getPaseroptask()
	{
		return paseroptask;
	}

	public void setPaseroptask(ParseroperateTask paseroptask)
	{
		this.paseroptask = paseroptask;
	}

	public ExtractAction()
	{

	}

	// 构造函数
	public ExtractAction(String rootPath, String fileName)
	{
		this.rootPath = rootPath;
		this.fileName = fileName;

		parsertaskService = (ParserTaskService) SpringContextUtils.getBean("parsertaskService");
		// paramService = (ParamService) SpringContextUtils.getBean("paramService");
		paseroptask = (ParseroperateTask) SpringContextUtils.getBean("paseroptask");
		// 初始化解析模板信息
		String cussql = SystemConstant.get("");
		paseroptask.getCusfiled(cussql);

		String rulesql = SystemConstant.get("");
		paseroptask.getSuffixrule(rulesql);

		String tagsql = SystemConstant.get("");
		paseroptask.getTagTemplate(tagsql);
	}

	// 初始化服务类操作信息
	public void initService()
	{
		parsertaskService = (ParserTaskService) SpringContextUtils.getBean("parsertaskService");
		paseroptask = (ParseroperateTask) SpringContextUtils.getBean("paseroptask");
		paseroptask.distory();
		parser.info("初始化服务类操作信息");
		// 所以任务非空字段信息
		String cussql = SystemConstant.get("");
		paseroptask.getCusfiled(cussql);

		// 所以任务规则信息
		String rulesql = SystemConstant.get("");
		paseroptask.getSuffixrule(rulesql);

		// 所有任务标签信息
		String tagsql = SystemConstant.get("");
		paseroptask.getTagTemplate(tagsql);

		parser.info("初始化服务类操作信息完成");
	}

	/**
	 * 解析所有采集任务
	 * 
	 * @param taskcode
	 */
	public void parserAlltaskcode()
	{
		File file = new File(SystemConstant.get("gather_root_dir"));
		// 是否为目录
		if (file.isDirectory())
		{
			// 获取目录下文件信息
			String[] fileList = file.list();
			if (fileList == null || fileList.length <= 0)
			{
				return;
			}
			File subFile = null;
			// Pattern pattern = null;
			// Matcher matcher = null;
			// 迭代文件解析
			for (String f : fileList)
			{
				subFile = new File(file, f);
				parserAllByDir(subFile.getAbsolutePath().replace(".\\", ""), subFile.getName());
				// String taskCode;
				// String temp[] = f.split("[\\\\]");
				// String fileName = temp[temp.length-2];
				// pattern = Pattern.compile("(^.*?)(_.*)?$");
				// matcher = pattern.matcher(fileName);
				// if (matcher.find()) {
				// //获取任务编号
				// taskCode = matcher.group(1);
				// parser.info("解析"+taskCode+"任务启动");
				// //执行解析
				// parserBytaskcode(taskCode,subFile.getAbsolutePath());
				// parser.info("解析"+taskCode+"任务完成");
				// }
			}
		}
	}

	/**
	 * 解析指定任务
	 * 
	 * @param taskcode
	 */
	public void parserAllByDir(String files, String filename)
	{
		// 验证解析文件路径是否为空
		if (RegexValidate.StrisNull(files))
		{
			return;
		}
		// 通过截取获取任务编号
		String temp[] = files.split("[\\\\]");
		String fileName = temp[temp.length - 1];
		Pattern pattern = null;
		Matcher matcher = null;
		String taskCode;
		pattern = Pattern.compile("(^.*?)(_.*)?$");
		matcher = pattern.matcher(fileName);
		if (matcher.find())
		{
			taskCode = matcher.group(1);
			parserBytaskcode(taskCode, files);
		}
	}

	/**
	 * 根据任务编号 解析任务
	 * 
	 * @param taskCode
	 * @param filePath
	 */
	public void parserBytaskcode(String taskCode, String filePath)
	{
		// 校验任务是否有效，配置解析模板
		String parsersql = SystemConstant.get("");
		StaticVar.parserlist.clear();
		// 根据任务ID 获取任务信息
		List<ParserInfo> parserState = paseroptask.getParserInfo(parsersql, taskCode);
		// 获取非空字段设置
		HashMap<String, List<String>> notNullField = StaticVar.notNullField.get(taskCode);
		// 获取规则模板信息
		HashMap<String, List<RuleTemplate>> gatherTemp = StaticVar.ruleMap.get(taskCode);
		// 获取标签模板信息
		HashMap<String, TagruleTemplate> tagTemp = StaticVar.tagruleMap.get(taskCode);
		if (null != parserState && parserState.size() > 0)
		{
//			parser.info("解析" + filePath +"启动");
			if (gatherTemp == null && tagTemp == null && notNullField == null)
			{
				parser.info("任务" + taskCode + "解析结构未配置");
			}
			else
			{
				dataExtract(taskCode, filePath, parserState, gatherTemp, tagTemp, notNullField);
			}
			parser.info("解析" + filePath +"完成");
		}
		else
		{
			parser.info("任务" + taskCode + "暂未配置解析模板");
		}
	}
//读取文件目录，若为根目录，获取所有html文件，	若为文件目录，解析文件
	public void dataExtract(String taskcode, String filePath, List<ParserInfo> parserInfo, HashMap<String, List<RuleTemplate>> gatherTemp,
			HashMap<String, TagruleTemplate> tagTemp, HashMap<String, List<String>> notNullField)
	{
		File subFile = new File(filePath);
		if (subFile.isDirectory())
		{
			String[] fileList = subFile.list();
			for (String name : fileList)
			{
				dataExtract(taskcode, new File(subFile, name).getAbsolutePath(), parserInfo, gatherTemp, tagTemp, notNullField);
			}
		}
		else
		{
			dataExtract(subFile.getAbsolutePath().replace(".\\", ""), parserInfo, gatherTemp, tagTemp, notNullField);
		}
	}

	/**
	 * 解析模板数据入库
	 * 
	 * @param taskCode
	 *            任务编号
	 * @param gatherTemp
	 *            解析规则任务模板
	 * @param tagTemp
	 *            标签任务模板
	 * @param notNullField
	 *            非空字段
	 */
	public void dataExtract(String filename, List<ParserInfo> parserInfo, HashMap<String, List<RuleTemplate>> gatherTemp,
			HashMap<String, TagruleTemplate> tagTemp, HashMap<String, List<String>> notNullField)
	{
		int resultTag = 0;
		List<String> notNullFieldList = null;
		TagExtract tagExtract = null;
		RuleExtract ruleExtract = null;
		String structuredCode = null;
		String parsertaskid = null;
		String encode = null;
		String parsertype = null;
		String rowtocell = null;
		String owner = null;
		for (ParserInfo parserinfo : parserInfo)
		{
			encode = parserinfo.getEncodetype();
			parsertype = parserinfo.getParsertype();
			parsertaskid = parserinfo.getStructuredid();
			rowtocell = parserinfo.getRowtocell();
			owner = parserinfo.getOwner();
			// 文件编号
			String filecode = parserinfo.getParserfilecode();
			// 获取文件目录中的文件编号
			String temp[] = filename.split("[\\\\]");
			String fileName = temp[temp.length - 2];
			// 为空 默认是当前任务
			if (!RegexValidate.StrNotNull(filecode))
			{
				filecode = parserinfo.getTaskcode();
			}
			// 判断对应文件编号 解析对应文件
			if (fileName.contains(filecode))
			{
				// 根据文件名称 获取源码
				String html = HtmlReader.readHtml(filename, encode);
				// 标签模板是否为空 解析处理方式为综合解析
				if (tagTemp != null && tagTemp.size() > 0 && parsertype.equals("2"))
				{
					tagExtract = new TagExtract(tagTemp.get(parsertaskid));
					// 执行标签解析
					structuredCode = tagExtract.getInfo(parsertaskid, html, encode, rowtocell);
					// 根据结构编号，获取非空字段ID
					if (structuredCode != null)
					{
						if (gatherTemp != null)
						{
							if (notNullField != null)
							{
								notNullFieldList = notNullField.get(structuredCode);
							}
							// 实例化前后缀规则
							ruleExtract = new RuleExtract(gatherTemp.get(structuredCode), notNullFieldList);
							// 前后缀解析
							ruleExtract.getInfo(html, parsertype);
							// 判断解析结果是否为空,存储表名称是否一致
							if (tagExtract.tagResultList != null && ruleExtract.ruleResult != null
									&& tagExtract.tableName.equals(ruleExtract.tableName))
							{
								// 前后缀、标签解析数据合并
								for (HashMap<String, String> m : tagExtract.tagResultList)
								{
									for (Map.Entry<String, String> sm : ruleExtract.ruleResult.entrySet())
									{
										m.put(sm.getKey(), sm.getValue());
									}
								}
								resultTag = paseroptask.save(tagExtract.tagResultList, owner + "." + tagExtract.tableName, notNullFieldList);
							}
						}
					}
				}
				// 标签解析
				if (tagTemp != null && tagTemp.size() > 0 && parsertype.equals("1"))
				{
					tagExtract = new TagExtract(tagTemp.get(parsertaskid));
					// 执行标签解析
					structuredCode = tagExtract.getInfo(parsertaskid, html, encode, rowtocell);
					// 根据结构编号，获取非空字段ID
					if (structuredCode != null)
					{
						if (notNullField != null)
						{
							notNullFieldList = notNullField.get(structuredCode);
						}
						// 判断解析结果是否为空,存储表名称是否一致
						if (tagExtract.tagResultList != null && RegexValidate.StrNotNull(tagExtract.tableName))
						{
							resultTag = paseroptask.save(tagExtract.tagResultList, owner + "." + tagExtract.tableName, notNullFieldList);
						}
					}
				}
				// 前后缀任务模板
				if (gatherTemp != null && parsertype.equals("0"))
				{
					// 保存前后缀方式的结果
					if (notNullField != null)
					{
						// 142483012244198380
						notNullFieldList = notNullField.get(parsertaskid);
					}
					ruleExtract = new RuleExtract(gatherTemp.get(parsertaskid), notNullFieldList);
					ruleExtract.getInfo(html, parsertype);
					try
					{
						resultTag = paseroptask.save(ruleExtract.ruleResult, owner + "." + ruleExtract.tableName, notNullFieldList);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				// 元搜索解析任务模板
				if (gatherTemp != null && parsertype.equals("4"))
				{
					// 保存前后缀方式的结果
					if (notNullField != null)
					{
						notNullFieldList = notNullField.get(parsertaskid);
					}
					ruleExtract = new RuleExtract(gatherTemp.get(parsertaskid), notNullFieldList);

					// 标签规则对象实例化
					tagExtract = new TagExtract(tagTemp.get(parsertaskid));

					// 执行元搜索解析 返回 HTML
					tagExtract.getInfo(html, encode, ruleExtract);
					// 解析前后缀对象实例化
					resultTag = paseroptask.save(tagExtract.tagResultList, owner + "." + tagExtract.tableName, notNullFieldList);
				}
			}
		}
		if (resultTag == 1)
		{
			// 解析完成后删除文件
			DeleteFile.deleteFile(filename);
			parser.info("解析完成,删除文件信息:" + filename);
			File tempfile = new File(filename);
//			if(tempfile.getParentFile().listFiles().length==0)
//			{
//				String directory = tempfile.getParentFile().getAbsolutePath();
//				DeleteFile.deleteFile(directory);
//				parser.info("删除空文件夹:" + directory);
//			}
		}
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
		StaticVar.parserlist.clear();
	}
}
