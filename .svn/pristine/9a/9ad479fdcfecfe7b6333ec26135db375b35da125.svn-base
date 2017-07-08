package datacvg.excel.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Element;

import haier.dataspider.param.service.SpringContextUtils;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import datacvg.excel.entity.ExcelRule;
import datacvg.excel.entity.ExcelStruct;
import datacvg.excel.service.ExcelPaserService;
import datacvg.excel.util.DistoryCollection;
import datacvg.excel.util.ExcelStaticVar;
import datacvg.excel.util.ExcelUtils;
import datacvg.excel.util.XmlCreater;
import datacvg.parse.service.ParseroperateTask;

/**
 * 解析Excel模板数据入库
 * 
 * @author admin
 * 
 */
public class ExcelController extends BasePagingQueryAction
{
	private static final Logger excel = Logger.getLogger(ExcelController.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ExcelPaserService excelpaserService;
	ParseroperateTask paseroptask;

	private XmlCreater xmlcreate;
	private Element root;

	public Element getRoot()
	{
		return root;
	}

	public void setRoot(Element root)
	{
		this.root = root;
	}

	public XmlCreater getXmlcreate()
	{
		return xmlcreate;
	}

	public void setXmlcreate(XmlCreater xmlcreate)
	{
		this.xmlcreate = xmlcreate;
	}

	private String filename;

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	private String filePath;

	public ExcelPaserService getExcelpaserService()
	{
		return excelpaserService;
	}

	public void setExcelpaserService(ExcelPaserService excelpaserService)
	{
		this.excelpaserService = excelpaserService;
	}

	public ParseroperateTask getPaseroptask()
	{
		return paseroptask;
	}

	public void setPaseroptask(ParseroperateTask paseroptask)
	{
		this.paseroptask = paseroptask;
	}

	public ExcelController()
	{

	}

	public ExcelController(String filename, String filePath)
	{
		this.filename = filename;
		this.filePath = filePath;
		initTemplate();
	}

	/**
	 * 初始化数据库中模板信息
	 */
	public void initTemplate()
	{
		excel.info("开始......初始化数据库中Excel解析模板信息");
		paseroptask = (ParseroperateTask) SpringContextUtils.getBean("paseroptask");
		excelpaserService = (ExcelPaserService) SpringContextUtils.getBean("excelpaserService");

		// 初始化解析文件信息
		String filesql = SystemConstant.get("getTemplateExcelFile");
		excelpaserService.getTemplateExcelFileBean(filesql);

		String tempsql = SystemConstant.get("getTemplateInfo");
		excelpaserService.getTemplatebean(tempsql);

		String epsql = SystemConstant.get("getExcelStruct");
		excelpaserService.getExcelStruct(epsql);
		String filedsql = SystemConstant.get("getFiledInfo");
		excelpaserService.getExcelStructNotNullFiled(filedsql);
		String rulesql = SystemConstant.get("getExcelStructRule");
		excelpaserService.getExcelStructRule(rulesql);
		excel.info("初始化数据库中Excel解析模板信息.....结束");
	}

	/**
	 * 根据模板ID获取该模板下所有结构、字段、字段类型、自定义字段下标、字段值等信息
	 * 
	 * @param templateid
	 * @param filePath
	 */
	public void ExcelPaserByTemplateId(String templateid, String filePath)
	{
		ExcelUtils eutils = new ExcelUtils();
		HashMap<String, ExcelStruct> excelStructList = ExcelStaticVar.excelStructMap.get(templateid);
		List<String> notNullFieldList = null;
		List<ExcelRule> excelruleList = null;
		HashMap<Object, String> relationMap = null;
		HashMap<Object, String> filedtypeMap = null;
		HashMap<Object, String> customfiledMap = null;

		ExcelStruct excelstruct = null;
		for (Map.Entry<String, ExcelStruct> sm : excelStructList.entrySet())
		{
			// 结构编号
			String structuredCode = sm.getKey();
			// 非空字段
			notNullFieldList = ExcelStaticVar.notNullField.get(structuredCode);
			// 结构规则对象
			excelruleList = ExcelStaticVar.structparseRule.get(structuredCode);
			// 字段下标 保证解析数据顺序
			relationMap = ExcelStaticVar.filedIndex.get(structuredCode);
			// 字段类型
			filedtypeMap = ExcelStaticVar.filedType.get(structuredCode);
			// 自定义值
			customfiledMap = ExcelStaticVar.customFieldVar.get(structuredCode);
			// 结构对象
			excelstruct = sm.getValue();
			// 解析sheet名称
			eutils.sheetName = excelstruct.getSheetname();
			// 数据库存储信息
			String owner = excelstruct.getOwner();
			String tableName = excelstruct.getAsstable();
			Workbook wb = null;
			// filePath+"//"+filename
			try
			{
				wb = eutils.readExcel(filePath);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			excel.info("解析 " + filePath + " 文件中的第" + eutils.sheetName + " sheet名称 该模板解析结构数" + excelruleList.size() + "个");
			int i = 1;
			try
			{
				for (ExcelRule rule : excelruleList)
				{

					eutils.srownums = rule.getParseerownum();// 开始行
					eutils.erownums = rule.getParsescellnum();// 结束行

					eutils.scellnums = rule.getParsescellnum();// 开始列
					eutils.ecellnums = rule.getParseecellnum();// 结束列
					eutils.celltorows = rule.getParserowtocell();// 行转列标识
					excel.info("开始解析第" + i + "个模板结构");
					Map<String, Object> resultsMap = eutils.getExcelResultList(wb);
					if (resultsMap != null)
					{
						// 转换成字符串
						String contentstr = eutils.getExcelToBuffer(resultsMap);
						List<HashMap<String, String>> resultlist = eutils.getExcelResultList(contentstr, relationMap);

						// 判断解析结果是否为空,存储表名称是否一致
						if (resultlist != null && resultlist != null)
						{
							excel.info("解析成功 获取数据行数: " + resultlist.size());
							// 自定义数据合并析数据合并
							for (HashMap<String, String> m : resultlist)
							{
								// 校验是否存在自定义字段值
								if (customfiledMap != null && customfiledMap.size() > 0)
								{
									for (Map.Entry<Object, String> sm1 : customfiledMap.entrySet())
									{
										m.put((String) sm1.getKey(), sm1.getValue());
									}
								}
							}
							// 保存数据入库
							paseroptask.saveExcel(resultlist, owner + "." + tableName, notNullFieldList, filedtypeMap);
						}
					}
					i++;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据模板传入的模板名称 获取该模板下所有结构、字段、字段类型、自定义字段下标、字段值等信息
	 * 
	 * @param templateName
	 */
	public void ExcelPaserByExceName(String templateName)
	{
		ExcelUtils eutils = new ExcelUtils();
		String templateid = ExcelStaticVar.templatebean.get(templateName);
		HashMap<String, ExcelStruct> excelStructList = ExcelStaticVar.excelStructMap.get(templateid);
		List<String> notNullFieldList = null;
		List<ExcelRule> excelruleList = null;
		HashMap<Object, String> relationMap = null;
		HashMap<Object, String> filedtypeMap = null;
		HashMap<Object, String> customfiledMap = null;

		ExcelStruct excelstruct = null;
		for (Map.Entry<String, ExcelStruct> sm : excelStructList.entrySet())
		{
			// 结构编号
			String structuredCode = sm.getKey();
			// 非空字段
			notNullFieldList = ExcelStaticVar.notNullField.get(structuredCode);
			// 结构规则对象
			excelruleList = ExcelStaticVar.structparseRule.get(structuredCode);
			// 字段下标 保证解析顺序
			relationMap = ExcelStaticVar.filedIndex.get(structuredCode);
			// 字段类型
			filedtypeMap = ExcelStaticVar.filedType.get(structuredCode);
			// 自定义值
			customfiledMap = ExcelStaticVar.customFieldVar.get(structuredCode);
			// 结构对象
			excelstruct = sm.getValue();
			// 解析sheet名称
			eutils.sheetName = excelstruct.getSheetname();
			// 数据库存储信息
			String owner = excelstruct.getOwner();
			String tableName = excelstruct.getAsstable();
			excel.info("解析 " + filePath + " 文件中的第" + eutils.sheetName + " sheet名称 该模板解析结构数" + excelruleList.size() + "个");
			Workbook wb = null;
			try
			{
				wb = eutils.readExcel(filePath);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			int i = 1;
			try
			{
				for (ExcelRule rule : excelruleList)
				{
					eutils.srownums = rule.getParsesrownum();// 开始行
					eutils.erownums = rule.getParseerownum();// 结束行
					eutils.scellnums = rule.getParsescellnum();// 开始列
					eutils.ecellnums = rule.getParseecellnum();// 结束列
					eutils.celltorows = rule.getParserowtocell();// 行转列标识
					excel.info("开始解析第" + i + "个模板结构");
					Map<String, Object> resultsMap = eutils.getExcelResultList(wb);
					if (resultsMap != null)
					{
						// 转换成字符串
						String contentstr = eutils.getExcelToBuffer(resultsMap);
						List<HashMap<String, String>> resultlist = eutils.getExcelResultList(contentstr, relationMap);
						// 判断解析结果是否为空,存储表名称是否一致
						if (resultlist != null && resultlist != null)
						{
							excel.info("解析成功 获取数据行数: " + resultlist.size());
							// 自定义数据合并析数据合并
							for (HashMap<String, String> m : resultlist)
							{
								if (null != customfiledMap && customfiledMap.size() > 0)
								{
									for (Map.Entry<Object, String> sm1 : customfiledMap.entrySet())
									{
										m.put((String) sm1.getKey(), sm1.getValue());
									}
								}
							}
							// 保存数据入库
							paseroptask.saveExcel(resultlist, owner + "." + tableName, notNullFieldList, filedtypeMap);
						}
					}
					i++;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * 初始化解析模板根节点信息
	 * 
	 * @param templateName
	 * @return
	 */
	private Element CreateXmlRoot(String templateId, String templateName)
	{
		xmlcreate = new XmlCreater("D:\\UDFILE\\UPLOAD\\TEMPLATE\\" + templateName + ".xml");
		root = xmlcreate.createRootElement("ExcelTemplate");
		xmlcreate.createAttribute(root, "id", templateId);
		xmlcreate.createAttribute(root, "version", "1.1");
		return root;
	}

	/**
	 * 创建Excel模板为xml信息
	 * 
	 * @param templateId
	 * @param templateName
	 */
	public Integer CreateTemplateToXml(String templateId, String templateName)
	{
		Integer temp = 0;
		// 获取解析文件集合
		HashMap<Object, String> filelist = ExcelStaticVar.templatePath.get(templateId);
		// 创建XML根节点信息
		root = CreateXmlRoot(templateId, templateName);
		Element temppath = xmlcreate.createElement(root, "TemplatePath");
		String commonpath = "解析文件路径 dir 文件路径,desc 文件描述";
		xmlcreate.addComment(temppath, commonpath);
		if (null != filelist && filelist.size() > 0)
		{
			for (Map.Entry<Object, String> objfilepath : filelist.entrySet())
			{
				Element mainPath = xmlcreate.createElement(temppath, "mainPath");
				xmlcreate.createAttribute(mainPath, "dir", objfilepath.getValue());
				xmlcreate.createAttribute(mainPath, "desc", objfilepath.getKey().toString());
			}
		}
		else
		{
			Element mainPath = xmlcreate.createElement(temppath, "mainPath");
			xmlcreate.createAttribute(mainPath, "dir", "");
			xmlcreate.createAttribute(mainPath, "desc", "");
			excel.info("模板 " + templateName + " 尚未上传解析文件！");
		}
		HashMap<String, ExcelStruct> excelStructList = ExcelStaticVar.excelStructMap.get(templateId);
		if (null != excelStructList && excelStructList.size() > 0)
		{
			ExcelStruct excelstruct = null;
			String commonstr = "模板结构信息 structcode 结构ID,sheetname SHEET名称,owner 存储用户,savetable 存储表,tabledesc 存储表描述";
			Element tempstructure = xmlcreate.createElement(root, "TemplateStructure");
			xmlcreate.addComment(tempstructure, commonstr);

			String filedcommon = "模板字段信息 structcode 结构ID, acqfield 字段名称, dbtype 字段类型, customvalue 自定义值,indexed 字段下标,required 非空字段";
			Element tempfiled = xmlcreate.createElement(root, "TemplateFileds");
			xmlcreate.addComment(tempfiled, filedcommon);

			String rulecommon = "模板结构规则信息  structcode 结构ID, srownums 开始行号, erownums 结束行号, scellnums开始列号, ecellnums结束列号,celltorows 列转行标识 ";
			Element temprule = xmlcreate.createElement(root, "TemplateTagRule");
			xmlcreate.addComment(temprule, rulecommon);

			for (Map.Entry<String, ExcelStruct> excelentry : excelStructList.entrySet())
			{
				Element structure = xmlcreate.createElement(tempstructure, "structure");
				// 结构编号
				String structuredCode = excelentry.getKey();
				// 结构对象
				excelstruct = excelentry.getValue();
				// 解析sheet名称
				String sheetName = excelstruct.getSheetname();
				// 数据库存储信息
				String owner = excelstruct.getOwner();
				String tableName = excelstruct.getAsstable();

				xmlcreate.createAttribute(structure, "structcode", structuredCode);
				xmlcreate.createAttribute(structure, "sheetname", sheetName);
				xmlcreate.createAttribute(structure, "owner", owner);
				xmlcreate.createAttribute(structure, "savetable", tableName);
				xmlcreate.createAttribute(structure, "tabledesc", excelstruct.getAsstablename());
				// xmlcreate.createAttribute(structure, "pinterface", excelstruct.getParsertype());
				// xmlcreate.createAttribute(structure, "isactive", excelstruct.getIsactive());
				String filedstr = structuredCode + " 结构 字段信息如下";
				xmlcreate.addComment(tempfiled, filedstr);

				CreateStructurFiled(xmlcreate, tempfiled, excelstruct);

				CreateStructurRule(xmlcreate, temprule, excelstruct);
			}
			temp = xmlcreate.buildXmlFile();
		}
		else
		{
			excel.info("模板 " + templateName + " 数据库中尚未配置解析模板！");
		}
		return temp;
	}

	/**
	 * 创建解析模板结构部分
	 * 
	 * @param xmlcreate
	 * @param tempfiled
	 * @param excelstruct
	 */
	private void CreateStructurFiled(XmlCreater xmlcreate, Element tempfiled, ExcelStruct excelstruct)
	{
		List<String> notNullFieldList = null;
		HashMap<Object, String> relationMap = null;
		HashMap<Object, String> customfiledMap = null;
		HashMap<Object, String> fileddescMap = ExcelStaticVar.filedInfo.get(excelstruct.getStructcode());
		;
		HashMap<Object, String> filedtypeMap = ExcelStaticVar.filedType.get(excelstruct.getStructcode());
		// 非空字段
		notNullFieldList = ExcelStaticVar.notNullField.get(excelstruct.getStructcode());
		// 字段下标 保证解析顺序
		relationMap = ExcelStaticVar.filedIndex.get(excelstruct.getStructcode());
		// 字段类型
		filedtypeMap = ExcelStaticVar.filedType.get(excelstruct.getStructcode());
		// 自定义值
		customfiledMap = ExcelStaticVar.customFieldVar.get(excelstruct.getStructcode());
		try
		{
			for (Map.Entry<Object, String> filedtype : filedtypeMap.entrySet())
			{
				Element field = xmlcreate.createElement(tempfiled, "field");
				String filedval = filedtype.getKey().toString();
				String dbtype = filedtype.getValue();
				// 组装字段信息
				xmlcreate.createAttribute(field, "structcode", excelstruct.getStructcode());
				xmlcreate.createAttribute(field, "acqfield", filedval);
				xmlcreate.createAttribute(field, "filedesc", fileddescMap.get(filedval));
				xmlcreate.createAttribute(field, "dbtype", dbtype);
				String customval = "";
				if (customfiledMap != null && customfiledMap.size() > 0)
				{
					customval = customfiledMap.get(filedval);
				}
				xmlcreate.createAttribute(field, "customvalue", customval);
				String index = "0";
				if (relationMap != null && relationMap.size() > 0)
				{
					for (Map.Entry<Object, String> relation : relationMap.entrySet())
					{
						String filed = relation.getValue();
						if (filed.equals(filedval))
						{
							index = relation.getKey().toString();
							break;
						}
					}
				}
				xmlcreate.createAttribute(field, "indexed", index);
				if (null != notNullFieldList && notNullFieldList.size() > 0)
				{
					for (String filedstr : notNullFieldList)
					{
						if (filedstr.equals(filedval))
						{
							xmlcreate.createAttribute(field, "required", "true");
							break;
						}
						else
						{
							xmlcreate.createAttribute(field, "required", "false");
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DistoryCollection.distory();
		}

	}

	/**
	 * 创建解析模板结构规则部分
	 * 
	 * @param xmlcreate
	 * @param temprule
	 * @param excelstruct
	 */
	private void CreateStructurRule(XmlCreater xmlcreate, Element temprule, ExcelStruct excelstruct)
	{
		// 结构规则对象
		List<ExcelRule> excelruleList = ExcelStaticVar.structparseRule.get(excelstruct.getStructcode());
		if (null != excelruleList && excelruleList.size() > 0)
		{
			try
			{
				for (ExcelRule excelrule : excelruleList)
				{
					Element rule = xmlcreate.createElement(temprule, "tagrule");
					xmlcreate.createAttribute(rule, "structcode", excelrule.getStructcode());
					xmlcreate.createAttribute(rule, "srownums", excelrule.getParsesrownum());
					xmlcreate.createAttribute(rule, "erownums", excelrule.getParseerownum());
					xmlcreate.createAttribute(rule, "scellnums", excelrule.getParsescellnum());
					xmlcreate.createAttribute(rule, "ecellnums", excelrule.getParseecellnum());
					xmlcreate.createAttribute(rule, "celltorows", excelrule.getParserowtocell());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				DistoryCollection.distory();
			}
		}
	}
}
