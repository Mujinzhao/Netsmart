package datacvg.excel.action;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.StringUtil;
import core.util.WDWUtil;
import datacvg.excel.service.TemplateService;
import datacvg.excel.util.DistoryCollection;
import datacvg.excel.util.ExcelUtils;

public class TemplateAction extends BasePagingQueryAction{

	/**
	 *模板信息操作类
	 */
	private static final long serialVersionUID = 1L;
	 /**
	   * 编码格式
	   */
	protected static final String CHARACTER_ENCODING = "utf-8";
	
	TemplateService templateService;
	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	private File uploadtemplate;

	public File getUploadtemplate() {
		return uploadtemplate;
	}

	public void setUploadtemplate(File uploadtemplate) {
		this.uploadtemplate = uploadtemplate;
	}

	private String uploadifyFileName;
	public String getUploadifyFileName() {
		return uploadifyFileName;
	}

	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}

    /****************************************模板录入操作 Start**************************************/
	/**
	 * 模板信息界面
	 * @return
	 */
	public String templatePage(){
		return SUCCESS;
	}
	// 上传模板
	public void uploadTemplate(){
		try {
			Map<String, String> paramMap= getParamMap(request);
//			System.out.println(paramMap);
			//获取添加模板SQL
			String inserTemplate = SystemConstant.get("inserTemplate");
			templateService.OparserSql(inserTemplate, paramMap);
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			write("error");
			return;
		}
		write("success");
	}
	// 删除模板
	public void  delTemplate(){
		try{
			Map<String, String>  paramMap = new HashMap<String, String>();
			String structid =MyURLDecoder.decode(request.getParameter("structid"), "UTF-8");
			structid = RegexValidate.StrNotNull(structid)==true?structid:WDWUtil.getSeqNextval();
			paramMap.put("id", structid);
			//获取添加模板SQL
			String delTemplate = SystemConstant.get("delTemplate");
			delTemplate = "delete from excel_template_list e where e.id=:id";
			String customsql ="delete from  excel_parser_cusfield c where c.templatecode=:id";
			String rulesql ="delete from  excel_parser_structure s where s.templateid=:id";
			Integer opindex = templateService.OparserSqlBySession(delTemplate, paramMap);
			opindex = templateService.OparserSqlBySession(customsql, paramMap);
			opindex = templateService.OparserSqlBySession(rulesql, paramMap);
			if(opindex >0){
				//删除该模板下目录及文件信息
				String delpath = SystemConstant.get("templatePath")+uploadifyFileName;
			    File delFile = new File(delpath);
			    // 判断目录或文件是否存在   
			    delFile.delete();
				write("success");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write("error");
		}
		
	}
	//修改模板
	public void modfiyTemplate() throws UnsupportedEncodingException{
			Map<String, String> paramMap= getParamMap(request);
			try{
				String sql = "select * from excel_template_list t where t.template_type=:templateType";
				List<Map<String,Object>> list =templateService.getListMap(sql, paramMap);
				if(list != null && list.size()>0){
					write("wring");
				}else{
					String upsql = "update  excel_template_list t set t.template_type=:templateType,t.status=:statusType,t.update_time=sysdate where t.id=:templateId";
					templateService.OparserSqlBySession(upsql,paramMap);
					File file = new File(SystemConstant.get("templatePath")+getStringParameter("otemplatetype"));
//					System.out.println(SystemConstant.get("templatePath")+type);
					file.renameTo(new File(SystemConstant.get("templatePath")+getStringParameter("templatetype")));
					write("success");
				}
			}catch (Exception e) {
				// TODO: handle exception
				write("error");
		}
	}
	
	//模板结构界面
	public String templateStruct() throws UnsupportedEncodingException{
		// 获取所有sheet页面信息集合
		ExcelUtils excelutil = new ExcelUtils();
		String templatePath = MyURLDecoder.decode(request.getParameter("templatePath"), "UTF-8");
		String templateId = MyURLDecoder.decode(request.getParameter("templateid"), "UTF-8");
		try{
			Workbook wb = excelutil.readExcel(templatePath);
			List<String> sheetnameList = excelutil.GetSheetNames(wb);
			String optionControll = "";
			if(null != sheetnameList  && sheetnameList.size()>0){
				//组装option集合
				optionControll= templateService.SelectOptionControll(sheetnameList);
			}
			request.setAttribute("sheetOption", optionControll);
			// 获取当前数据库中的所有用户
		    String usersql = SystemConstant.get("getDbausers");
		    String userselect = templateService.GetOptionsSelect(usersql, null);
		    request.setAttribute("userselect", userselect);
		
			request.setAttribute("templateId", templateId);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
	// 生成XML模板
	public void createTemplateXml() throws UnsupportedEncodingException{
		String templateId = MyURLDecoder.decode(request.getParameter("templateid"), "UTF-8");
		String templatetype = MyURLDecoder.decode(request.getParameter("templatetype"), "UTF-8");
		ExcelController controller = new ExcelController();
		controller.initTemplate();
		Integer index = controller.CreateTemplateToXml(templateId,templatetype);
		DistoryCollection.distory();
		write(index+"");
	
		
	}
	/****************************************模板录入操作 End**************************************/
    
	/****************************************模板解析跳转界面 Start
	 * @throws UnsupportedEncodingException **************************************/
	
	public String templateConfig() throws UnsupportedEncodingException{
		String sheetname =MyURLDecoder.decode(request.getParameter("sheetname"), "UTF-8");
		request.setAttribute("sheetname", sheetname);
		
		String structcode =MyURLDecoder.decode(request.getParameter("structuredid"), "UTF-8");
		request.setAttribute("structuredid", structcode);
		
		String asstable =MyURLDecoder.decode(request.getParameter("asstable"), "UTF-8");
		request.setAttribute("asstable", asstable);
		//数据存储对应用户
		String dbuser = MyURLDecoder.decode(request.getParameter("owner"), "UTF-8");
		request.setAttribute("owner", dbuser);
		
		String templateId = MyURLDecoder.decode(request.getParameter("templateid"), "UTF-8");
		request.setAttribute("templateId", templateId);
		
		String jumpage = MyURLDecoder.decode(request.getParameter("jumpage"), "UTF-8");
		return jumpage;
	
	}
	/****************************************模板解析跳转界面 End**************************************/
	
	/****************************************模板结构录入操作 Start
	 * @throws UnsupportedEncodingException **************************************/
	
	//操作配置模板
	public void saveTemplateStructure() throws UnsupportedEncodingException{
			Map<String, String> paraMap = getParamMap(request);
			//添加sql
			String addsql = SystemConstant.get("SaveTemplatePasers");
			try{
				Integer executenum  = templateService.addCustomeFiled(addsql, paraMap);
				if(executenum > 0){
					write("操作成功");
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				write("操作失败！");
			}
	}
	
	public void updateTemplateStructure() throws UnsupportedEncodingException{
		Map<String, String> paraMap = getParamMap(request);
		
		// 添加SQL语句
		String addsql=SystemConstant.get("SaveTemplatePasers");
		
		//删除SQL语句
        String delsql=SystemConstant.get("delTemplateField");
        //查询SQL语句
        String selectsql =  SystemConstant.get("getCustomFiled");
        //修改SQL语句
        String upsql = SystemConstant.get("UpdateTemplatePasers");
        
        String[] filedanddescarray = paraMap.get("filedarray").split("[∴]");
		String[] asstablearray = paraMap.get("asstabledesc").split("[|]");
		
		List<String> addlist = null;
		List<String> dellist = null;
		String delfiled = null;
		Transaction tran = null;
		Session session = templateService.getMySessionFactory().openSession();
		try {
				tran = session.beginTransaction();
				tran.begin();
			    paraMap.put("asstable", asstablearray[0]);// 表名
				// 删除移除部分字段信息
			    delsql  = "delete from  excel_parser_cusfield c where c.asstable=:asstable and c.structcode=:structuredid";
    			// 前台传入
    			List arraylist = Arrays.asList(paraMap.get("modfiyarray").split("[∴]"));
    			// 获取数据库部分
    			List filedlist = templateService.getFiledList(selectsql, paraMap);
    			
    			
    			if(filedlist !=null && filedlist.size()>0){
    				// 比较出需要添加部分数据
        			addlist= StringUtil.list1ComparisonList2(arraylist,filedlist);
        			// 比较出需要删除部分数据
        			dellist= StringUtil.list1ComparisonList2(filedlist,arraylist);
        			//格式化 去掉最后特殊字符
        		    delfiled = Arrays.toString(dellist.toArray());
        			delfiled =delfiled.substring(1, delfiled.length()-1);
    			}
    			// 移除字段信息
    			if(dellist !=null && dellist.size()>0 && RegexValidate.StrNotNull(delfiled)){
    				String filedparam= delfiled.trim().replace(", ", "','");
        			delsql += 	" and c.acqfield in ('"+filedparam+"')";
        			templateService.OparserSql(delsql, paraMap,session);
    			}
				if(addlist !=null && addlist.size()>0 &&RegexValidate.StrNotNull(paraMap.get("filedarray"))){
					// 将字段名称、字段描述存入Map集合中
					Map<String, String> filedanddescMap = new HashMap<String, String>();
					for (String filedstr : filedanddescarray) {
						String[] filedstrArray = filedstr.split("[|]");
						filedanddescMap.put(filedstrArray[0],filedstrArray[1]);
					}
					// 添加字段
					for (String filedstr : addlist) {
						paraMap.put("id", WDWUtil.getSeqNextval());// 存储字段ID
						paraMap.put("asstable", asstablearray[0]);// 表名
						paraMap.put("asstablename", asstablearray[1]);// 表名描述
						paraMap.put("acqfield", filedstr);// 字段
						paraMap.put("acqfieldesc", filedanddescMap.get(filedstr).toString());// 字段描述
						templateService.OparserSql(addsql, paraMap,session);
					}
				}else{
					templateService.OparserSql(upsql, paraMap,session);
				}
				tran.commit();
				write("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
			write("操作失败");
		}finally {
			if (session != null) {
				try {
					templateService.getMySessionFactory().closeSession(session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void delTemplateStructure() throws UnsupportedEncodingException{
		Map<String, String> paraMap = getParamMap(request);
		//删除sql
		String delsql = SystemConstant.get("delTemplateField");
		String delrsql = SystemConstant.get("delTemplateRule");
		Session session = templateService.getMySessionFactory().openSession();
		Transaction tran = null;
		try{
			tran = session.beginTransaction();
			tran.begin();
			// 删除Excel模板结构及模板信息
			templateService.OparserSql(delsql, paraMap,session);
			templateService.OparserSql(delrsql, paraMap,session);
			tran.commit();
			write("操作成功");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write("操作失败！");
		}finally{
			if(session != null){
				try {
					templateService.getMySessionFactory().closeSession(session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	// 填充下一条
	public void tanchongStructure() throws UnsupportedEncodingException{
		Map<String, String> paraMap = getParamMap(request);
		// 获取结构信息getTemplateStruct
		String sql = SystemConstant.get("getTemplateFiled");
		sql = sql.replaceAll("#", "");
//		Integer executenum = templateService.Optancongthong(sql, paraMap);
		templateService.Optanchongthong(sql, paraMap);
	}
	/****************************************模板结构录入操作 End**************************************/
	
	/****************************************模板结构字段操作 Start
	 * @throws UnsupportedEncodingException **************************************/
	
	
	public void updatecustomFiledVal() throws UnsupportedEncodingException{
		Map<String, String>  paramMap = getParamMap(request);
		String customop = MyURLDecoder.decode(request.getParameter("customop"), "UTF-8");
		String sql = "";
		if(RegexValidate.StrNotNull(customop) && customop.equals("0")){
		    sql = SystemConstant.get("updateCustomFieldVar");
		}else{
			sql = SystemConstant.get("updateCustomFieldIndex");
		}
		try {
			templateService.OparserSql(sql,paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/****************************************模板结构字段操作 End**************************************/
	
	/****************************************模板解析操作 Start
	 * @throws UnsupportedEncodingException **************************************/
	//校验EXCEL模板解析
	public void checkexcelParser() throws UnsupportedEncodingException{
		ExcelUtils excel = new ExcelUtils();
		Map<String, String>  paramMap = getParamMap(request);
		// 数据库获取该模板信息
		//获取该模板配置下标字段字段信息
	    String sql ="select t.filedindex,t.acqfield from excel_parser_cusfield t where t.templatecode=:templateId and t.structcode=:structuredid and t.filedindex <> 0 ";
	    try{
	    HashMap<Object, String> relationMap   =templateService.getTagindex(sql, paramMap);
	    //获取解析模板路径
		String filePath = "D:\\EXCEL\\TEPLATE\\白电\\中国白电行业大宗原料采购综合周报（8.18-8.22）.xlsx";
//		String filePath =  ExcelStaticVar.filePath.get(paramMap.get("structuredid"));
		Workbook wb = excel.readExcel(filePath);
		
		
		excel.setSheetName(paramMap.get("sheetname"));
		excel.srownums=paramMap.get("parsesrownum");//开始行
		excel.erownums=paramMap.get("parseerownum");//结束行
		excel.scellnums=paramMap.get("parsescellnum");//开始列
		excel.ecellnums=paramMap.get("parseecellnum");//结束列
		excel.celltorows=paramMap.get("parserowtocell");;//行转列标识
		
		Map<String,Object> resultsMap  = excel.getExcelResultList(wb);
		if (resultsMap != null) {
			String contentstr = excel.getExcelToBuffer(resultsMap);
			List<HashMap<String, String>> resultlist =  excel.getExcelResultList(contentstr, relationMap);
			String parsucc = "";
	    	String tmpsucc = "";
	    	int i =0;
	    	int j =1;
			if(null != resultlist)
		    {
				// 迭代 标签规则值
		    	for(HashMap<String, String> tagdata :resultlist)
				{
		    		// 迭代规则字段信息
					for(Entry<Object, String> entry : relationMap.entrySet())
					{
						parsucc +=tagdata.get(entry.getValue())+" "; // 迭代出每个规则字段解析的值
						i++;
						// 判断 当前解析规则字段列数，进行换行 已第一条数据为基础进行换行
						if(i == (relationMap.size()*j)) 
						{
							parsucc = parsucc.replaceAll("\n", "");
							tmpsucc += parsucc+"\n";
							parsucc ="";
							j++;
						}
					}
				}
		    }
		    if(RegexValidate.StrNotNull(tmpsucc)) 
			 {
		    	 write(tmpsucc);
			 }
		     else
		     {
		    	write("标签规则没有解析出值");// 标签规则没有解析出值
		     }
		}
	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
//		String contentstr = excel.readExcelToCentent(wb);
//		List<HashMap<String, String>> resultlist =  excel.getExcelResultList(contentstr, relationMap);
		
	}
	
	//添加解析EXCEL模板规则信息
	public void saveExcelParser() throws UnsupportedEncodingException{
		Map<String, String>  paramMap = getParamMap(request);
		String sql =  SystemConstant.get("saveExcelParser");
		try {
			templateService.OparserSql(sql,paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//添加解析EXCEL模板规则信息
	public void updateExcelParser() throws UnsupportedEncodingException{
		Map<String, String>  paramMap = getParamMap(request);
		String sql =  SystemConstant.get("updateExcelParser");
		try {
			templateService.OparserSql(sql,paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//添加解析EXCEL模板规则信息
	public void delExcelParser() throws UnsupportedEncodingException{
		Map<String, String>  paramMap = getParamMap(request);
		String sql =  SystemConstant.get("delExcelParser");
		try {
			templateService.OparserSql(sql,paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	/****************************************模板解析操作 End
	 * @throws UnsupportedEncodingException **************************************/
	
	// 操作参数  MAP 集合
	public  Map<String, String> getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
		
		Map<String, String>  paramMap = new HashMap<String, String>();
		
		//字段ID
		String structid =MyURLDecoder.decode(request.getParameter("structid"), "UTF-8");
		structid = RegexValidate.StrNotNull(structid)==true?structid:WDWUtil.getSeqNextval();
		paramMap.put("id", structid);
		
		//模板ID
		String templateId =MyURLDecoder.decode(request.getParameter("templateid"), "UTF-8");
		templateId = RegexValidate.StrNotNull(templateId)==true?templateId:WDWUtil.getSeqNextval();
		paramMap.put("templateId", templateId);
		
		String structcode =MyURLDecoder.decode(request.getParameter("structuredid"), "UTF-8");
		structcode = RegexValidate.StrNotNull(structcode)==true?structcode:WDWUtil.getSeqNextval();
		paramMap.put("structuredid", structcode);
		
		String templateName = getStringParameter("templatename");
		if(RegexValidate.StrNotNull(templateName)){
			paramMap.put("templateName", WDWUtil.getExtentionName(templateName));
		}
	
		String templateType = getStringParameter("templatetype");
		paramMap.put("templateType", templateType);
		
		String templatefileType =getStringParameter("templatefiletype");
		paramMap.put("templatefileType", templatefileType);
		
		
		String templatePath = SystemConstant.get("templatePath")+templateType;
		paramMap.put("templatePath", templatePath);
		
		String templateSize =getStringParameter("templatesize");
		if(RegexValidate.StrNotNull(templateSize)){
			templateSize = WDWUtil.formetFileSize(Long.parseLong(templateSize));
			paramMap.put("templateSize", templateSize);
		}
		
		String stauts =getStringParameter("stauts");
		paramMap.put("statusType", stauts);
		
		//添加模板解析信息
		String sheetname = MyURLDecoder.decode(request.getParameter("sheetname"), "UTF-8");
		paramMap.put("sheetname",sheetname);
		
		String sheetable = MyURLDecoder.decode(request.getParameter("sheetable"), "UTF-8");
		paramMap.put("sheetable",sheetable);
		// 获取解析类型 0 表示前后缀解析 1表示标签解析  2 表示综合解析
		String parsertype = MyURLDecoder.decode(request.getParameter("parsertype"), "UTF-8");
		paramMap.put("parsertype",parsertype);
		
		//表字段数组
		String filedarray = MyURLDecoder.decode(request.getParameter("filedarray"), "UTF-8");
		paramMap.put("filedarray", filedarray);
		
		// 修改表字段数组
		String modfiyarray = MyURLDecoder.decode(request.getParameter("modfiyarray"), "UTF-8");
		paramMap.put("modfiyarray", modfiyarray);
		
		// 表名称描述
		String asstabledesc = MyURLDecoder.decode(request.getParameter("tablearray"), "UTF-8");
		paramMap.put("asstabledesc", asstabledesc);
		
		//数据存储对应用户
		String dbuser = MyURLDecoder.decode(request.getParameter("owner"), "UTF-8");
		paramMap.put("owner", dbuser);
		
		// 是否列转行解析标识
		String rowtocell = MyURLDecoder.decode(request.getParameter("rowtocell"), "UTF-8");
		rowtocell = RegexValidate.StrNotNull(rowtocell)==true?rowtocell:"false";
		paramMap.put("celltorow", rowtocell);
		
		String isactive = MyURLDecoder.decode(request.getParameter("isactive"), "UTF-8");
		isactive = RegexValidate.StrNotNull(isactive)==true?isactive:"F";
		paramMap.put("isactive", isactive);
		
		String acqfieldval = MyURLDecoder.decode(request.getParameter("acqfieldval"), "UTF-8");
		paramMap.put("acqfieldval", acqfieldval);
		
		String emptyacqfield = MyURLDecoder.decode(request.getParameter("emptyacqfield"), "UTF-8");
		acqfieldval = RegexValidate.StrNotNull(acqfieldval)==true?acqfieldval:"0";
		paramMap.put("emptyacqfield", emptyacqfield);
		
		String asstable = MyURLDecoder.decode(request.getParameter("asstable"), "UTF-8");
		paramMap.put("asstable", asstable);
		
		String parsefiled = MyURLDecoder.decode(request.getParameter("parsefiled"), "UTF-8");
		paramMap.put("acqfield", parsefiled);
		
		paramMap.put("filedindex", acqfieldval);
		
		
		
		// 解析开始行
		String parsesrownum = MyURLDecoder.decode(request.getParameter("parsesrownum"), "UTF-8");
		paramMap.put("parsesrownum", parsesrownum);
		
		// 解析结束行
		String parseerownum = MyURLDecoder.decode(request.getParameter("parseerownum"), "UTF-8");
		paramMap.put("parseerownum", parseerownum);
		
		// 解析开始列
		String parsescellnum = MyURLDecoder.decode(request.getParameter("parsescellnum"), "UTF-8");
		paramMap.put("parsescellnum", parsescellnum);
		
		// 解析结束列
		String parseecellnum = MyURLDecoder.decode(request.getParameter("parseecellnum"), "UTF-8");
		paramMap.put("parseecellnum", parseecellnum);
		
		// 解析列转行标识
		String parserowtocell = MyURLDecoder.decode(request.getParameter("parserowtocell"), "UTF-8");
		paramMap.put("parserowtocell",parserowtocell);
		
		return paramMap;
	}
}
