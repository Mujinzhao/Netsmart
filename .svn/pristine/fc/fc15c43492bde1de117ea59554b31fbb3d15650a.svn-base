package datacvg.excel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import core.util.RegexValidate;
import datacvg.excel.entity.ExcelRule;
import datacvg.excel.entity.ExcelStruct;
import datacvg.excel.util.ExcelStaticVar;
/**
 * 获取XML中的Excel模板数据
 * @author admin
 *
 */
public class ExcelPaserXml {
	private static final Logger excel = Logger.getLogger(ExcelPaserXml.class);
	
	/**
	 * 模板配置信息
	 * @param sql
	 */
	public void  getTemplatePathToXml(List<Element> excelstructlist){
		try{
			if(null != excelstructlist && excelstructlist.size()>0){
				for (int i = 0; i < excelstructlist.size(); i++) {
					Element obj = excelstructlist.get(i);
					//解析文件路径
					String pathdir = obj.attributeValue("dir");
					//解析文件名称
					String filedesc =   obj.attributeValue("desc");
					if(RegexValidate.StrNotNull(pathdir))
					       ExcelStaticVar.filePath.put(pathdir, filedesc);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			excel.error("初始化模板配置信息失败!"+e.getMessage());
		}
	}
	/**
	 * 获取解析模板任务
	 */
	
	public void  getExcelStructToXml(List<Element> excelstructlist){
		try{
			if(null != excelstructlist && excelstructlist.size()>0){
				ExcelStaticVar.excelStructMap.clear();
				for (int i = 0; i < excelstructlist.size(); i++) {
					Element obj = excelstructlist.get(i);
					addExcelStruct(obj);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			excel.error("初始化获取解析模板任务失败!"+e.getMessage());
		}
	}
	
	public void addExcelStruct(Element obj){
		if (obj == null) {
			return ;
		}
		ExcelStruct excelstruct = new ExcelStruct();
		//结构编号
		String structuredCode =  String.valueOf(obj.attributeValue("structcode")==null? "": obj.attributeValue("structcode"));
		excelstruct.setStructcode(structuredCode);
		//sheet名称
		String sheetname = String.valueOf(obj.attributeValue("sheetname")==null? "": obj.attributeValue("sheetname"));
		excelstruct.setSheetname(sheetname);
		//sheet列表名称
		String sheetable = String.valueOf(obj.attributeValue("sheetable")==null? "": obj.attributeValue("sheetable"));
		excelstruct.setSheetable(sheetable);
		String owner = String.valueOf(obj.attributeValue("owner")==null? "": obj.attributeValue("owner"));
		excelstruct.setOwner(owner);
		//存储表名称
		String savetable = String.valueOf(obj.attributeValue("savetable")==null? "": obj.attributeValue("savetable"));
		excelstruct.setAsstable(savetable);
		//存储表描述
		String tabledesc = String.valueOf(obj.attributeValue("tabledesc")==null? "": obj.attributeValue("tabledesc"));
		excelstruct.setAsstablename(tabledesc);
		ExcelStaticVar.excelStructList.add(excelstruct);
	}
	/**
	 * 获取非空字段
	 */
	
	public void  getExcelStructNotNullFiledToXml(List<Element> notnullfiled){
		List<String> fieldList = null;
		HashMap<Object, String> oneGather = null;
		HashMap<Object, String> filedType = null;
		String structuredCode = null;
		try{
			if(null != notnullfiled && notnullfiled.size()>0){
				for (int i = 0; i < notnullfiled.size(); i++) {
					Element obj = notnullfiled.get(i);
					//任务编号
//					String templateid = String.valueOf(obj[0] == null ? "": obj[0]);
					//结构编号
					structuredCode =  String.valueOf(obj.attributeValue("structcode")==null? "": obj.attributeValue("structcode"));
					String acqfield = String.valueOf(obj.attributeValue("acqfield")==null? "": obj.attributeValue("acqfield"));
					//是否为空
					String isNull = String.valueOf(obj.attributeValue("required")==null? "": obj.attributeValue("required"));
					// 表示该任务指定过非空字段
					if (isNull != null && isNull.equals("true")) {
						fieldList = ExcelStaticVar.notNullField.get(structuredCode);
						if(fieldList == null){
							fieldList = new ArrayList<String>();
						}
						fieldList.add(acqfield);
						ExcelStaticVar.notNullField.put(structuredCode, fieldList);
					}
					String filedindex = String.valueOf(obj.attributeValue("indexed")==null? "": obj.attributeValue("indexed"));
					String filedtype = String.valueOf(obj.attributeValue("dbtype")==null? "": obj.attributeValue("dbtype"));
					
					filedType = ExcelStaticVar.filedType.get(structuredCode);
					if(filedType == null){
						filedType = new HashMap<Object, String>();
					}
//					//字段下标
					if(RegexValidate.StrNotNull(filedindex)){
						oneGather = ExcelStaticVar.filedIndex.get(structuredCode);
						if(oneGather == null){
							oneGather = new HashMap<Object, String>();
						}
						if(Integer.parseInt(filedindex) > 0){
							oneGather.put(Integer.parseInt(filedindex), acqfield);
							ExcelStaticVar.filedIndex.put(structuredCode,oneGather);
							filedType.put(acqfield, filedtype);
							ExcelStaticVar.filedType.put(structuredCode,filedType);
//							addFiledInfo(obj, ExcelStaticVar.filedIndex);
						}
					}
					String acqfieldvar = String.valueOf(obj.attributeValue("customvalue")==null? "": obj.attributeValue("customvalue"));
//					//字段对应值
					if(RegexValidate.StrNotNull(acqfieldvar)){
						addFiledInfo(obj, ExcelStaticVar.customFieldVar);
						filedType.put(acqfield, filedtype);
						ExcelStaticVar.filedType.put(structuredCode,filedType);
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			excel.error("获取模板非空字段信息失败!"+e.getMessage());
		}
	}

	/**
	 * 获取字段信息
	 */
	
	public void  addFiledInfo(Element obj,HashMap<String,HashMap<Object,String>> map){
		HashMap<Object, String> oneGather = null;
		try{
			if (obj == null) {
				return ;
			}
			//结构编号
			String structuredCode =  String.valueOf(obj.attributeValue("structcode")==null? "": obj.attributeValue("structcode"));
			String acqfield = String.valueOf(obj.attributeValue("acqfield")==null? "": obj.attributeValue("acqfield"));
			String acqfieldvar = String.valueOf(obj.attributeValue("customvalue")==null? "": obj.attributeValue("customvalue"));
			//字段对应值
			oneGather = map.get(structuredCode);
			if(RegexValidate.StrNotNull(acqfieldvar)){
				if(oneGather == null){
					oneGather = new HashMap<Object, String>();
				}
				oneGather.put(String.valueOf(acqfield), acqfieldvar);
			}
			map.put(structuredCode,oneGather);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			excel.error("获取模板自定义、下标信息失败!"+e.getMessage());
		}
	}
	

	/**
	 * 模板规则获取
	 */
	public void  getExcelStructRuleToXml(List<Element> excelstructlist){
		try{
			if(null != excelstructlist && excelstructlist.size()>0){
				for (int i = 0; i < excelstructlist.size(); i++) {
					Element obj = excelstructlist.get(i);
					addExRule(obj, ExcelStaticVar.structparseRule);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			excel.error("模板规则信息失败!"+e.getMessage());
		}
	}

    public void addExRule(Element obj,HashMap<String,List<ExcelRule>> map){
    	List<ExcelRule> rulelist= null;
    	ExcelRule excelrule = new ExcelRule();
		String structuredCode =String.valueOf(obj.attributeValue("structcode")==null? "": obj.attributeValue("structcode"));
		excelrule.setStructcode(structuredCode);
		excelrule.setParsesrownum(obj.attributeValue("srownums")==null? "": obj.attributeValue("srownums"));
		excelrule.setParseerownum(obj.attributeValue("erownums")==null? "": obj.attributeValue("erownums"));
		excelrule.setParsescellnum(obj.attributeValue("scellnums")==null? "": obj.attributeValue("scellnums"));
		excelrule.setParseecellnum(obj.attributeValue("ecellnums")==null? "": obj.attributeValue("ecellnums"));
		excelrule.setParserowtocell(obj.attributeValue("celltorows")==null? "": obj.attributeValue("celltorows"));
		rulelist = map.get(structuredCode);
		if(rulelist == null){
			rulelist = new ArrayList<ExcelRule>();
			rulelist.add(excelrule);
		}
		map.put(structuredCode, rulelist);
    }
}
