package datacvg.excel.action;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Element;


import datacvg.dbconfg.util.DBUtil;
import datacvg.excel.entity.ExcelRule;
import datacvg.excel.entity.ExcelStruct;
import datacvg.excel.service.ExcelPaserXml;
import datacvg.excel.util.ExcelStaticVar;
import datacvg.excel.util.ExcelUtils;
import datacvg.excel.util.XmlParser;
/**
 * 生成XML模板接口
 * @author admin
 *
 */
public class ExcelControllerXml {
	private static final Logger excel = Logger.getLogger(ExcelControllerXml.class);
	/**
	 * 
	 */
	ExcelPaserXml excelpaserxml;
	DBUtil dbutil = new DBUtil();
	private XmlParser xmlparser ;
	private Element root ;
	public Connection conn;
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public XmlParser getXmlparser() {
		return xmlparser;
	}

	public void setXmlParser(XmlParser xmlparser) {
		this.xmlparser = xmlparser;
	}

	private String filename;
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private String filePath;
	public ExcelControllerXml(){
	}
	public ExcelControllerXml(String filePath){
    	this.filePath = filePath;
    	initTemplateXml();
	}
	
    public ExcelControllerXml(String filename,String filePath){
    	this.filename=filename;
    	this.filePath = filePath;
    	initTemplateXml();
	}
    public void initTemplateXml(){
    	excel.info("开始......初始化解析xml中的模板信息");
    	xmlparser = new XmlParser(filePath);
    	root = xmlparser.getRootElement();
    	excelpaserxml = new ExcelPaserXml(); 
    	
    	List<Element> templatepath = xmlparser.getChildElement(root,"TemplatePath");
    	if(null != templatepath && templatepath.size()>0){
    		 for(Element childement :templatepath){  
				 List<Element> mainpath  =  xmlparser.getChildElement(childement,"mainPath");
				 excelpaserxml.getTemplatePathToXml(mainpath);
    		 }
    		 excel.info("加载模板解析文件集合完成");
    	}
    	List<Element> structurements = xmlparser.getChildElement(root,"TemplateStructure");
		if(null != structurements && structurements.size()>0){
			 for(Element structure :structurements){  
				 List<Element> structures =  xmlparser.getChildElement(structure,"structure");
				 excelpaserxml.getExcelStructToXml(structures);
			 }
			 excel.info("加载模板结构集合完成");
		}
		List<Element> templatefileds = xmlparser.getChildElement(root,"TemplateFileds");
		if(null != templatefileds && templatefileds.size()>0){
			 for(Element fieldement :templatefileds){  
				 List<Element> field =  xmlparser.getChildElement(fieldement,"field");
				 excelpaserxml.getExcelStructNotNullFiledToXml(field);
			 }
			 excel.info("加载模板结构字段集合完成");
		}
		List<Element> templatetagrule = xmlparser.getChildElement(root,"TemplateTagRule");
		if(null != templatetagrule && templatetagrule.size()>0){
			 for(Element tagrulelement :templatetagrule){  
				 List<Element> tagrule =  xmlparser.getChildElement(tagrulelement,"tagrule");
				 excelpaserxml.getExcelStructRuleToXml(tagrule);
			 }
			 excel.info("加载模板结构规则集合完成");
		}
    	excel.info("初始化解析xml中的模板信息......结束");
    }
    //通过解析xml信息获取模板结构信息入库 
    public void parserXmlStructToDb(){
    	ExcelUtils eutils = new ExcelUtils();
    	//获取配置文件中解析文件集合
    	HashMap<String,String> filepathmap = ExcelStaticVar.filePath;
    	List<ExcelStruct> excelStructList  = ExcelStaticVar.excelStructList;
		List<String> notNullFieldList = null;
		List<ExcelRule> excelruleList = null;
		HashMap<Object,String> relationMap = null;
		HashMap<Object,String> filedtypeMap = null;
		HashMap<Object,String> customfiledMap = null;
		if(filepathmap != null && filepathmap.size()>0){
			try{
				for (Entry<String, String> objfilepath : filepathmap.entrySet()) {
						for (ExcelStruct excelstruct : excelStructList) {
						//结构编号
						String structuredCode = excelstruct.getStructcode();
						// 非空字段
						notNullFieldList = ExcelStaticVar.notNullField.get(structuredCode);
						// 结构规则对象
						excelruleList    = ExcelStaticVar.structparseRule.get(structuredCode);
						//字段下标 保证解析顺序
						relationMap      = ExcelStaticVar.filedIndex.get(structuredCode);
						//字段类型
						filedtypeMap     = ExcelStaticVar.filedType.get(structuredCode);
						//自定义值
						customfiledMap      = ExcelStaticVar.customFieldVar.get(structuredCode);
						//解析sheet名称
						eutils.sheetName = excelstruct.getSheetname();
						//数据库存储信息
						String owner     = excelstruct.getOwner();
						String tableName = excelstruct.getAsstable();
						excel.info("开始解析 "+objfilepath.getKey()+" 文件中的第"+eutils.sheetName+" sheet名称 该模板解析结构数"+excelruleList.size()+"个");
						Workbook wb = eutils.readExcel(objfilepath.getKey());
						int i=1;
						for(ExcelRule rule:excelruleList){
							eutils.srownums=rule.getParsesrownum();//开始行
							eutils.erownums=rule.getParseerownum();//结束行
							eutils.scellnums=rule.getParsescellnum();//开始列
							eutils.ecellnums=rule.getParseecellnum();//结束列
							eutils.celltorows=rule.getParserowtocell();//行转列标识
							excel.info("开始解析第"+i+"个模板结构");
							Map<String,Object> resultsMap  =  eutils.getExcelResultList(wb);
							if (resultsMap != null) {
								// 转换成字符串
								String contentstr = eutils.getExcelToBuffer(resultsMap);
								List<HashMap<String, String>> resultlist =  eutils.getExcelResultList(contentstr, relationMap);
								// 判断解析结果是否为空,存储表名称是否一致
								if (resultlist != null && resultlist != null) {
									excel.info("解析成功 获取数据行数: " + resultlist.size());
									// 自定义数据合并析数据合并
									for(HashMap<String, String> m : resultlist){
										if(null != customfiledMap && customfiledMap.size()>0){
											for (Map.Entry<Object, String> sm1 : customfiledMap.entrySet()) {
												m.put((String) sm1.getKey(), sm1.getValue()); 
											}
										}
									}
									// 保存数据入库
									dbutil.saveExcel(resultlist, owner+"."+tableName,notNullFieldList,filedtypeMap);
								}
							}
							i++;
						}
					}
			     }
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				excel.error("解析异常"+e.getMessage());
			}
			finally{
				try {
                    if (null != conn) {
                    	conn.close();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();
                }
			}
		}else{
			excel.info("该模板尚未配置解析文件路径信息！");
		}
    }
    
    //通过解析xml信息获取模板结构信息入库 
    public void parserXmlStructToDb(Connection conn){
    	ExcelUtils eutils = new ExcelUtils();
    	//获取配置文件中解析文件集合
    	HashMap<String,String> filepathmap = ExcelStaticVar.filePath;
    	List<ExcelStruct> excelStructList  = ExcelStaticVar.excelStructList;
		List<String> notNullFieldList = null;
		List<ExcelRule> excelruleList = null;
		HashMap<Object,String> relationMap = null;
		HashMap<Object,String> filedtypeMap = null;
		HashMap<Object,String> customfiledMap = null;
		if(filepathmap != null && filepathmap.size()>0){
			try{
					for (Entry<String, String> objfilepath : filepathmap.entrySet()) {
							for (ExcelStruct excelstruct : excelStructList) {
							//结构编号
							String structuredCode = excelstruct.getStructcode();
							// 非空字段
							notNullFieldList = ExcelStaticVar.notNullField.get(structuredCode);
							// 结构规则对象
							excelruleList    = ExcelStaticVar.structparseRule.get(structuredCode);
							//字段下标 保证解析顺序
							relationMap      = ExcelStaticVar.filedIndex.get(structuredCode);
							//字段类型
							filedtypeMap     = ExcelStaticVar.filedType.get(structuredCode);
							//自定义值
							customfiledMap      = ExcelStaticVar.customFieldVar.get(structuredCode);
							//解析sheet名称
							eutils.sheetName = excelstruct.getSheetname();
							//数据库存储信息
							String owner     = excelstruct.getOwner();
							String tableName = excelstruct.getAsstable();
							excel.info("开始解析 "+objfilepath.getKey()+" 文件中的第"+eutils.sheetName+" sheet名称 该模板解析结构数"+excelruleList.size()+"个");
							Workbook wb = eutils.readExcel(objfilepath.getKey());
							int i=1;
							for(ExcelRule rule:excelruleList){
								eutils.srownums=rule.getParsesrownum();//开始行
								eutils.erownums=rule.getParseerownum();//结束行
								eutils.scellnums=rule.getParsescellnum();//开始列
								eutils.ecellnums=rule.getParseecellnum();//结束列
								eutils.celltorows=rule.getParserowtocell();//行转列标识
								excel.info("开始解析第"+i+"个模板结构");
								Map<String,Object> resultsMap  =  eutils.getExcelResultList(wb);
								if (resultsMap != null) {
									// 转换成字符串
									String contentstr = eutils.getExcelToBuffer(resultsMap);
									List<HashMap<String, String>> resultlist =  eutils.getExcelResultList(contentstr, relationMap);
									// 判断解析结果是否为空,存储表名称是否一致
									if (resultlist != null && resultlist != null) {
										excel.info("解析成功 获取数据行数: " + resultlist.size());
										// 自定义数据合并析数据合并
										for(HashMap<String, String> m : resultlist){
											if(null != customfiledMap && customfiledMap.size()>0){
												for (Map.Entry<Object, String> sm1 : customfiledMap.entrySet()) {
													m.put((String) sm1.getKey(), sm1.getValue()); 
												}
											}
										}
										// 保存数据入库
										dbutil.saveExcel(resultlist, owner+"."+tableName,notNullFieldList,filedtypeMap);
									}
								}
								i++;
							}
						}
			     }
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				excel.error("解析异常"+e.getMessage());
			}
			finally{
				try {
                    if (null != conn) {
                    	conn.close();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();
                }
			}
		}else{
			excel.info("该模板尚未配置解析文件路径信息！");
		}
    }
}
