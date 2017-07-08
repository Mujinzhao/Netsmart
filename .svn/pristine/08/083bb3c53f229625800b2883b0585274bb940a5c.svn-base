package datacvg.excel.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Element;

import core.util.RegexValidate;
import core.util.WDWUtil;

import datacvg.excel.entity.ExcelRule;
import datacvg.excel.entity.ExcelStruct;
import datacvg.excel.entity.ParserDB;
import datacvg.excel.service.ExcelPaserXml;
import datacvg.excel.util.DistoryCollection;
import datacvg.excel.util.ExcelDataSource;
import datacvg.excel.util.ExcelStaticVar;
import datacvg.excel.util.ExcelUtils;
import datacvg.excel.util.XmlParser;

/**
 * 根据传入的XML模板解析EXCEL数据入库
 * @author admin
 *
 */

public class ExcelParserXmlController {
	private static final Logger excel = Logger.getLogger(ExcelParserXmlController.class);
	ExcelPaserXml excelpaserxml;
	private XmlParser xmlparser ;
	private Element root ;
	private String filePath;
	
	ExcelDataSource dataSource =null;
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
	
	public String getFilePath() {
		//解析文件地址
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ExcelParserXmlController(){
	}
	public ExcelParserXmlController(String filePath){
    	this.filePath = filePath;
    	initTemplateXml();
	}
	/**
	 * 初始化解析结构数据
	 */
	 public void initTemplateXml(){
	    	excel.info("开始......初始化解析xml中的模板信息");
	    	
	    	ExcelStaticVar.parserrorList.add(this.getClass()+"开始......初始化解析xml中的模板信息");
									
		    dataSource = new ExcelDataSource(ParserDB.ds);
	    	/** 验证文件是否合法 */
			if (!WDWUtil.validateXML(ParserDB.parserxmlFile))
			{
				excel.error("解析XML文件类型有误!"+ParserDB.parserxmlFile);
				ExcelStaticVar.parserrorList.add(this.getClass()+"解析XML文件类型有误!"+ParserDB.parserxmlFile);
				return;
			}
	    	xmlparser = new XmlParser(ParserDB.parserxmlFile);
	    	root = xmlparser.getRootElement();
	    	excelpaserxml = new ExcelPaserXml(); 
	    	
	    	
	    	List<Element> templatepath = xmlparser.getChildElement(root,"TemplatePath");
	    	if(null != templatepath && templatepath.size()>0){
	    		 for(Element childement :templatepath){  
					 List<Element> mainpath  =  xmlparser.getChildElement(childement,"mainPath");
					 excelpaserxml.getTemplatePathToXml(mainpath);
	    		 }
	    		 ExcelStaticVar.parserrorList.add(this.getClass()+"加载模板解析文件集合完成");
					
	    		 excel.info("加载模板解析文件集合完成");
	    	}
	    	List<Element> structurements = xmlparser.getChildElement(root,"TemplateStructure");
			if(null != structurements && structurements.size()>0){
				 for(Element structure :structurements){  
					 List<Element> structures =  xmlparser.getChildElement(structure,"structure");
					 excelpaserxml.getExcelStructToXml(structures);
				 }
				 ExcelStaticVar.parserrorList.add(this.getClass()+":"+this.getClass().getModifiers()+"加载模板结构集合完成");
					
				 excel.info("加载模板结构集合完成");
			}
			List<Element> templatefileds = xmlparser.getChildElement(root,"TemplateFileds");
			if(null != templatefileds && templatefileds.size()>0){
				 for(Element fieldement :templatefileds){  
					 List<Element> field =  xmlparser.getChildElement(fieldement,"field");
					 excelpaserxml.getExcelStructNotNullFiledToXml(field);
				 }
				 ExcelStaticVar.parserrorList.add(this.getClass()+"加载模板结构字段集合完成");
					
				 excel.info("加载模板结构字段集合完成");
			}
			List<Element> templatetagrule = xmlparser.getChildElement(root,"TemplateTagRule");
			if(null != templatetagrule && templatetagrule.size()>0){
				 for(Element tagrulelement :templatetagrule){  
					 List<Element> tagrule =  xmlparser.getChildElement(tagrulelement,"tagrule");
					 excelpaserxml.getExcelStructRuleToXml(tagrule);
				 }
				 ExcelStaticVar.parserrorList.add(this.getClass()+"加载模板结构规则集合完成");
					
				 excel.info("加载模板结构规则集合完成");
			}
			 ExcelStaticVar.parserrorList.add(this.getClass()+"初始化解析xml中的模板信息......结束");
	    	 excel.info("初始化解析xml中的模板信息......结束");
	    }
	
	  /**
	   * 通过解析xml信息获取模板结构信息入库 
	   * @param optype 0 全量更新  1增量更新
	   * @throws Exception 解析异常信息
	   */
	    public void parserXmlToDb(String optype,String userpkid) throws Exception{
	    	ExcelUtils eutils = new ExcelUtils();
	    	List<ExcelStruct> excelStructList  = ExcelStaticVar.excelStructList;
			List<String> notNullFieldList = null;
			List<ExcelRule> excelruleList = null;
			HashMap<Object,String> relationMap = null;
			HashMap<Object,String> filedtypeMap = null;
			HashMap<Object,String> customfiledMap = null;
			if(excelStructList != null && excelStructList.size()>0){
				try{
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
							customfiledMap   = ExcelStaticVar.customFieldVar.get(structuredCode);
							//解析sheet名称
							eutils.sheetName = excelstruct.getSheetname();
							//数据库存储信息
							String owner     = excelstruct.getOwner();
							String tableName = excelstruct.getAsstable();
							if(relationMap != null && relationMap.size()>0){
								Workbook wb = eutils.readExcel(filePath);
								String savetable = "";
								if(RegexValidate.StrNotNull(owner)){
									savetable = owner+"."+tableName;
								}else{
									savetable = tableName;
								}
								//全量更新删除表数据
								if(RegexValidate.StrNotNull(optype) && optype.equals("0")){
									String trunsql = "delete from "+savetable;
									int opindex = dataSource.execUPdate(trunsql);
									excel.info("全量更新删除"+owner+"用户数据库中"+tableName+"表数据"+opindex+"条");
								}
								excel.info("开始解析 "+filePath+" 文件中的"+eutils.sheetName+" sheet名称 该模板解析结构数"+excelruleList.size()+"个");
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
										if (resultlist != null && resultlist.size()>0) {
											// 自定义数据合并析数据合并
											for(HashMap<String, String> m : resultlist){
												if(null != customfiledMap && customfiledMap.size()>0){
													for (Map.Entry<Object, String> sm1 : customfiledMap.entrySet()) {
														//自定义操作入库时间
														if(sm1.getValue().equals("sysdate") || sm1.getValue().equals("currentuser"))
														{
															//获取字段类型
															String dbfiledtype=filedtypeMap.get((String) sm1.getKey());
															//日期类型
															if(dbfiledtype.toLowerCase().equals("date")){
																m.put((String) sm1.getKey(), sm1.getValue()); 
															}
															//varchar类型
															if(dbfiledtype.toLowerCase().equals("varchar2")){
																m.put((String) sm1.getKey(),  "to_char"); 
															}
															//判断操作操作用户ID
															if(sm1.getValue().equals("currentuser"))
															{
																//日期类型
															    m.put((String) sm1.getKey(),userpkid); 
															}
														}
														else{
															//其他正常定义字段值
															m.put((String) sm1.getKey(), sm1.getValue()); 
														}
													}
													
												}
											}
											// 保存数据入库
											int opindex = 	dataSource.saveExcel(resultlist, savetable,notNullFieldList,filedtypeMap);
									        if(opindex==-1){
									           break;
									        }
									        else{
									        	excel.info("解析成功 操作影响条数: " + opindex);
									        }
										}
									}
									i++;
								}
							}else{
								excel.warn("该模板尚未配置解析字段信息！");
							}
				
						}
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw e;
				}
			}else{
				excel.warn("该模板尚未配置解析结构信息！");
			}
	    }
	    
	    /**
		   * 通过解析xml信息获取模板结构信息入库 
		   * @param  optype 0 全量更新  1增量更新
		   * @param  userpkid 当前操作用户
		   * @throws Exception 解析异常信息
		   */
		    public String parserXmlToDbStr(String optype,String userpkid) throws Exception{
		    	ExcelUtils eutils = new ExcelUtils();
		    	List<ExcelStruct> excelStructList  = ExcelStaticVar.excelStructList;
				List<String> notNullFieldList = null;
				List<ExcelRule> excelruleList = null;
				HashMap<Object,String> relationMap = null;
				HashMap<Object,String> filedtypeMap = null;
				HashMap<Object,String> customfiledMap = null;
				if(excelStructList != null && excelStructList.size()>0){
					try{
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
								customfiledMap   = ExcelStaticVar.customFieldVar.get(structuredCode);
								//解析sheet名称
								eutils.sheetName = excelstruct.getSheetname();
								//数据库存储信息
								String owner     = excelstruct.getOwner();
								String tableName = excelstruct.getAsstable();
								if(relationMap != null && relationMap.size()>0){
									Workbook wb = eutils.readExcel(filePath);
									String savetable = "";
									if(RegexValidate.StrNotNull(owner)){
										savetable = owner+"."+tableName;
									}else{
										savetable = tableName;
									}
									//全量更新删除表数据
									if(RegexValidate.StrNotNull(optype) && optype.equals("0")){
										String trunsql = "delete from "+savetable;
										int opindex = dataSource.execUPdate(trunsql);
										excel.info("全量更新删除"+owner+"用户数据库中"+tableName+"表数据"+opindex+"条");
										ExcelStaticVar.parserrorList.add(this.getClass()+"全量更新删除"+owner+"用户数据库中"+tableName+"表数据"+opindex+"条");
									}
									excel.info("开始解析 "+filePath+" 文件中的"+eutils.sheetName+" sheet名称 该模板解析结构数"+excelruleList.size()+"个");
									ExcelStaticVar.parserrorList.add(this.getClass()+"开始解析 "+filePath+" 文件中的"+eutils.sheetName+" sheet名称 该模板解析结构数"+excelruleList.size()+"个");
									
									int i=1;
									for(ExcelRule rule:excelruleList){
										eutils.srownums=rule.getParsesrownum();//开始行
										eutils.erownums=rule.getParseerownum();//结束行
										eutils.scellnums=rule.getParsescellnum();//开始列
										eutils.ecellnums=rule.getParseecellnum();//结束列
										eutils.celltorows=rule.getParserowtocell();//行转列标识
										excel.info("开始解析第"+i+"个模板结构");
										ExcelStaticVar.parserrorList.add(this.getClass()+"开始解析第"+i+"个模板结构");
										
										Map<String,Object> resultsMap  =  eutils.getExcelResultList(wb);
										if (resultsMap != null) {
											// 转换成字符串
											String contentstr = eutils.getExcelToBuffer(resultsMap);
											List<HashMap<String, String>> resultlist =  eutils.getExcelResultList(contentstr, relationMap);
											// 判断解析结果是否为空,存储表名称是否一致
											if (resultlist != null && resultlist.size()>0) {
												// 自定义数据合并析数据合并
												for(HashMap<String, String> m : resultlist){
													if(null != customfiledMap && customfiledMap.size()>0){
														for (Map.Entry<Object, String> sm1 : customfiledMap.entrySet()) {
															//自定义操作入库时间
															if(sm1.getValue().equals("sysdate") || sm1.getValue().equals("currentuser"))
															{
																//获取字段类型
																String dbfiledtype=filedtypeMap.get((String) sm1.getKey());
																//日期类型
																if(dbfiledtype.toLowerCase().equals("date")){
																	m.put((String) sm1.getKey(), sm1.getValue()); 
																}
																//varchar类型
																if(dbfiledtype.toLowerCase().equals("varchar2")){
																	m.put((String) sm1.getKey(),  "to_char"); 
																}
																//判断操作操作用户ID
																if(sm1.getValue().equals("currentuser"))
																{
																	//日期类型
																    m.put((String) sm1.getKey(),userpkid); 
																}
															}
															else{
																//其他正常定义字段值
																m.put((String) sm1.getKey(), sm1.getValue()); 
															}
														}
														
													}
												}
												// 保存数据入库
												int opindex = 	dataSource.saveExcel(resultlist, savetable,notNullFieldList,filedtypeMap);
										        if(opindex==-1){
										           break;
										        }
										        else{
										        	excel.info("解析成功 操作影响条数: " + opindex);
										        	ExcelStaticVar.parserrorList.add(this.getClass()+"解析成功 操作影响条数: " + opindex);
										        }
											}
										}
										i++;
									}
								}else{
									excel.warn(eutils.sheetName+"配置文件中尚未配置解析字段信息");
								 	ExcelStaticVar.parserrorList.add(this.getClass()+eutils.sheetName+"配置文件中尚未配置解析字段信息");
							        
								}
						 }
					
					}
					catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						DistoryCollection.distory();
						return eutils.errorInfo;
					}
				}else{
					excel.warn("该模板尚未配置解析结构信息！");
					ExcelStaticVar.parserrorList.add(this.getClass()+"该模板尚未配置解析结构信息！");
			        
				}
				return eutils.errorInfo;
		    }
}
