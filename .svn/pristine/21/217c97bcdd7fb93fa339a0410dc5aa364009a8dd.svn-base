package datacvg.excel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.util.RegexValidate;
import datacvg.excel.entity.ExcelRule;
import datacvg.excel.entity.ExcelStruct;
import datacvg.excel.util.ExcelStaticVar;

public class ExcelPaserService {
	private static final Logger excel = Logger.getLogger(ExcelPaserService.class);
	
	public BaseDao dao = null;
	private MySessionFactory mySessionFactory;
	public MySessionFactory getMySessionFactory() {
		return mySessionFactory;
	}
	public void setMySessionFactory(MySessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}
	public BaseDao getDao() {
		return dao;
	}
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
	
	/**
	 * 模板配置信息
	 * @param sql
	 */
	public void  getTemplateExcelFileBean(String sql){
		try{
			ExcelStaticVar.templatePath.clear();
			List<?>  excelstructlist = (List<?> )dao.selectSqlAuto(sql);
			if(null != excelstructlist && excelstructlist.size()>0){
				HashMap<Object, String> pathmap = null;
				for (int i = 0; i < excelstructlist.size(); i++) {
						Object[] obj = (Object[]) excelstructlist.get(i);
						String templateid = String.valueOf(obj[0] == null ? "": obj[0]);
						//文件名称
						String excelname = String.valueOf(obj[1] == null ? "": obj[1]);
						//文件路径
						String excelpath =  String.valueOf(obj[2] == null ? "": obj[2]);
					    pathmap = ExcelStaticVar.templatePath.get(templateid);
					    //解析模板路径
						ExcelStaticVar.filePath.put(templateid, excelpath);
						if(pathmap == null){
							pathmap = new HashMap<Object, String>();
						}
						pathmap.put(excelname, excelpath);
						ExcelStaticVar.templatePath.put(templateid, pathmap);
					}
		 }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ExcelStaticVar.templatePath.clear();
			excel.error("初始化模板配置信息失败!"+e.getMessage());
		}
	}
	/**
	 * 模板配置信息
	 * @param sql
	 */
	public void  getTemplatebean(String sql){
		try{
			List<?>  excelstructlist = (List<?> )dao.selectSqlAuto(sql);
			if(null != excelstructlist && excelstructlist.size()>0){
				for (int i = 0; i < excelstructlist.size(); i++) {
					Object[] obj = (Object[]) excelstructlist.get(i);
					//模板编号
					String templateid = String.valueOf(obj[0] == null ? "": obj[0]);
					//模板名称
					String templateName =  String.valueOf(obj[1] == null ? "": obj[1]);
					ExcelStaticVar.templatebean.put(templateName, templateid);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ExcelStaticVar.templatebean.clear();
			excel.error("初始化模板配置信息失败!"+e.getMessage());
		}
	}
	/**
	 * 获取解析模板任务
	 */
	
	public void  getExcelStruct(String sql){
		try{
			List<?>  excelstructlist = (List<?> )dao.selectSqlAuto(sql);
			if(null != excelstructlist && excelstructlist.size()>0){
				ExcelStaticVar.excelStructMap.clear();
				for (int i = 0; i < excelstructlist.size(); i++) {
					Object[] obj = (Object[]) excelstructlist.get(i);
					addExcelStruct(obj, ExcelStaticVar.excelStructMap);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ExcelStaticVar.excelStructMap.clear();
			excel.error("初始化获取解析模板任务失败!"+e.getMessage());
		}
	}
	
	public void addExcelStruct(Object[] obj,HashMap<String,HashMap<String,ExcelStruct>> map){
		if (obj == null) {
			return ;
		}
		ExcelStaticVar.excelStructList.clear();
		HashMap<String,ExcelStruct> excelstructMap = null;
		ExcelStruct excelstruct = new ExcelStruct();
		//模板编号
		String templateid = String.valueOf(obj[0] == null ? "": obj[0]);
		excelstruct.setTemplateid(templateid);
		//结构编号
		String structuredCode =  String.valueOf(obj[1] == null ? "": obj[1]);
		excelstruct.setStructcode(structuredCode);
		//sheet名称
		String sheetname = String.valueOf(obj[2] == null?"":obj[2]);
		excelstruct.setSheetname(sheetname);
		//sheet列表名称
		String sheetable = String.valueOf(obj[3] == null?"":obj[3]);
		excelstruct.setSheetable(sheetable);
		String owner = String.valueOf(obj[4]==null?"":obj[4]);
		excelstruct.setOwner(owner);
		//存储表名称
		String savetable = String.valueOf(obj[5]==null?"":obj[5]);
		excelstruct.setAsstable(savetable);
		//存储表描述
		String tabledesc = String.valueOf(obj[6]==null?"":obj[6]);
		excelstruct.setAsstablename(tabledesc);
		excelstructMap = map.get(templateid);
		if (excelstructMap == null) {
			excelstructMap = new HashMap<String, ExcelStruct>();
			ExcelStaticVar.excelStructList.add(excelstruct);
		}
		excelstructMap.put(structuredCode,excelstruct);
		map.put(templateid, excelstructMap);
	}
	/**
	 * 获取非空字段
	 */
	
	public void  getExcelStructNotNullFiled(String sql){
		List<String> fieldList = null;
		HashMap<Object, String> oneGather = null;
		HashMap<Object, String> filedType = null;
		HashMap<Object, String> filedInfo = null;
		String structuredCode = null;
		try{
			List<?>  excelstructlist = (List<?> )dao.selectSqlAuto(sql);
			if(null != excelstructlist && excelstructlist.size()>0){
				for (int i = 0; i < excelstructlist.size(); i++) {
					Object[] obj = (Object[]) excelstructlist.get(i);
					//任务编号
//					String templateid = String.valueOf(obj[0] == null ? "": obj[0]);
					//结构编号
					structuredCode =  String.valueOf(obj[2] == null ? "": obj[2]);
					String acqfield = String.valueOf(obj[8] == null?"":obj[8]);
					String fielddesc = String.valueOf(obj[9] == null?"":obj[9]);
					//是否为空
					String isNull = String.valueOf(obj[10] == null ? "": obj[10]);
					// 表示该任务指定过非空字段
					if (isNull != null && isNull.equals("1")) {
						fieldList = ExcelStaticVar.notNullField.get(structuredCode);
						if(fieldList == null){
							fieldList = new ArrayList<String>();
						}
						fieldList.add(acqfield);
						ExcelStaticVar.notNullField.put(structuredCode, fieldList);
					}
					String filedindex = String.valueOf(obj[17] == null?"":obj[17]);
					String filedtype = String.valueOf(obj[18] == null?"":obj[18]);
					
					filedType = ExcelStaticVar.filedType.get(structuredCode);
					filedInfo = ExcelStaticVar.filedInfo.get(structuredCode);
					if(filedType == null &&   filedInfo == null){
						filedType = new HashMap<Object, String>();
						filedInfo = new HashMap<Object, String>();
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
							filedInfo.put(acqfield, fielddesc);//字段、字段描述
							ExcelStaticVar.filedInfo.put(structuredCode,filedInfo);
//							addFiledInfo(obj, ExcelStaticVar.filedIndex);
						}
					}
					String acqfieldvar = String.valueOf(obj[16] == null?"":obj[16]);
//					//字段对应值
					if(RegexValidate.StrNotNull(acqfieldvar)){
						addFiledInfo(obj, ExcelStaticVar.customFieldVar);
						filedType.put(acqfield, filedtype);
						ExcelStaticVar.filedType.put(structuredCode,filedType);
						filedInfo.put(acqfield, fielddesc);//字段、字段描述
						ExcelStaticVar.filedInfo.put(structuredCode,filedInfo);
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
	
	public void  addFiledInfo(Object[] obj,HashMap<String,HashMap<Object,String>> map){
		HashMap<Object, String> oneGather = null;
		try{
			if (obj == null) {
				return ;
			}
			//模板编号
//			String templateid = String.valueOf(obj[0] == null ? "": obj[0]);
			//结构编号
			String structuredCode =  String.valueOf(obj[2] == null ? "": obj[2]);
			String acqfield = String.valueOf(obj[8] == null?"":obj[8]);
			String acqfieldvar = String.valueOf(obj[16] == null?"":obj[16]);
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
	public void  getExcelStructRule(String sql){
		try{
			List<?>  excelstructlist = (List<?> )dao.selectSqlAuto(sql);
			if(null != excelstructlist && excelstructlist.size()>0){
				for (int i = 0; i < excelstructlist.size(); i++) {
					Object[] obj = (Object[]) excelstructlist.get(i);
					addExRule(obj, ExcelStaticVar.structparseRule);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ExcelStaticVar.structparseRule.clear();
			excel.error("模板规则信息失败!"+e.getMessage());
		}
	}

    public void addExRule(Object[] obj,HashMap<String,List<ExcelRule>> map){
    	List<ExcelRule> rulelist= null;
    	ExcelRule excelrule = new ExcelRule();
    	excelrule.setTemplateid(String.valueOf(obj[1] == null?"":obj[1]));
		String structuredCode =String.valueOf(obj[2] == null?"":obj[2]);
		excelrule.setStructcode(structuredCode);
		excelrule.setParsesrownum(String.valueOf(obj[3] == null?"":obj[3]));
		excelrule.setParseerownum(String.valueOf(obj[4] == null?"":obj[4]));
		excelrule.setParsescellnum(String.valueOf(obj[5] == null?"":obj[5]));
		excelrule.setParseecellnum(String.valueOf(obj[6] == null?"":obj[6]));
		excelrule.setParserowtocell(String.valueOf(obj[7] == null?"":obj[7]));
		rulelist = map.get(structuredCode);
		if(rulelist == null){
			rulelist = new ArrayList<ExcelRule>();
			rulelist.add(excelrule);
		}
		map.put(structuredCode, rulelist);
    }
}
