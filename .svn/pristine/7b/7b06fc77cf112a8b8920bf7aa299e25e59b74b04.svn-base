package haier.dataspider.param.action;

import haier.dataspider.excelparser.ExcelConfig;
import haier.dataspider.excelparser.ExcelToBean;
import haier.dataspider.param.entity.ResParams;
import haier.dataspider.param.service.ParamService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;

import core.BasePagingQueryAction;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.WDWUtil;


public class ResParamsAction  extends BasePagingQueryAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ParamService paramService;
	
	ExcelConfig excelParser = new ExcelConfig();

	public ParamService getParamService() {
		return paramService;
	}

	public void setParamService(ParamService paramService) {
		this.paramService = paramService;
	}

	ResParams param;
	
	public ResParams getParam() {
		return param;
	}

	public void setParam(ResParams param) {
		this.param = param;
	}
	
	public String parserPage() throws UnsupportedEncodingException{
		// 获取数据库中解析存储数据表信息
		String tableTree = paramService.GetOptionsTree("","HR_");
		request.setAttribute("tableTree", tableTree);
		String templateid = getStringParameter("templateid");
		request.setAttribute("templateid", templateid);
		request.setAttribute("templatename", getStringParameter("templatename"));
		return SUCCESS;
	}
	public void validationRule(){
		try{
			HttpSession session = request.getSession();
			StringBuffer sub = new StringBuffer();
			String filePath = MyURLDecoder.decode(request.getParameter("templatename"), "UTF-8");
			excelParser.setSheetName(getStringParameter("sheetname"));
			String interfaces = getStringParameter("parseinterface");
			Workbook workbook = excelParser.readExcel(filePath);
			 /** 循环Excel的行 */
			//设置解析开始行号
			excelParser.setSrownum(Integer.parseInt(RegexValidate.StrNotNull(getStringParameter("srownum"))?getStringParameter("srownum"):"0"));
			//设置解析结束行号
			excelParser.setErownum(Integer.parseInt(RegexValidate.StrNotNull(getStringParameter("erownum"))?getStringParameter("erownum"):"0"));
			/** 循环Excel的列 */
			excelParser.setScellnum(Integer.parseInt(RegexValidate.StrNotNull(getStringParameter("scellnum"))?getStringParameter("scellnum"):"0"));
			excelParser.setEcellnum(Integer.parseInt(RegexValidate.StrNotNull(getStringParameter("ecellnum"))?getStringParameter("ecellnum"):"0"));
			
			excelParser.setRowstr(RegexValidate.StrNotNull(getStringParameter("srowstr"))?getStringParameter("srowstr"):"");
			excelParser.setCellstr(RegexValidate.StrNotNull(getStringParameter("erowstr"))?getStringParameter("erowstr"):"");

			/** 循环Excel的列 */
			Map<String, String> datamap =  excelParser.tableBreakUp(workbook);
//			System.out.println(excelParser.getErrorInfo());
			if(!RegexValidate.StrNotNull(excelParser.getErrorInfo())){
				ExcelToBean  tobean = new ExcelToBean();
				sub = tobean.getExcelToBean(interfaces, datamap);
				if(RegexValidate.StrNotNull(sub.toString())){
					// 解析成功
//					String parscontent =excelParser.getParserstr().toString().trim();
//					parscontent = parscontent.substring(0,parscontent.length()-1);
					String parscontent = sub.toString().replace("|", "\n");
					parscontent = parscontent.replace("∴", " ");
					session.setAttribute("excelParser",sub.toString());
//					parserbuff.append(excelParser.getParserstr().toString().trim());
					write("0|"+parscontent);
				
				}else{
					// 出现错误
					write("1|"+excelParser.getErrorInfo());
				}
			}else{
				// 出现错误
				write("1|"+excelParser.getErrorInfo());
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write("-1|"+"解析异常"+e.getMessage());
			
		}
	}
		
	//保存规则信息
	public void saveExRule(){
		try{
			HttpSession session = request.getSession();
			Map map = paramService.getParamMap(request);
			map.put("id", WDWUtil.getSeqNextval());
			map.put("pasercontent", (String)session.getAttribute("excelParser"));
			Integer flag = paramService.insertStructure(map);
			if(flag>0){
				write("操作成功");
			}else{
				write("操作失败！");
			}
		}catch (Exception e) {
			// TODO: handle exception
			write("操作失败！");
		}
	}
	
	/**
	 * 删除规则信息
	 * @throws UnsupportedEncodingException 
	 */
	public void delExRule() throws UnsupportedEncodingException{
		String id = getStringParameter("id");
		try
		{
			Integer flag = paramService.del(id);
			if(flag>0){
				write("操作成功");
			}else{
				write("操作失败！");
			}
		}catch (Exception e) {
			// TODO: handle exception
			write("操作失败！");
		}
	}

	/**
	 * 修改规则信息
	 */
	public void modifyExRule(){
		try{
			HttpSession session = request.getSession();
			Map map = paramService.getParamMap(request);
			map.put("pasercontent", (String)session.getAttribute("excelParser"));
			Integer flag = paramService.update(map);
			if(flag>0){
				write("操作成功");
			}else{
				write("操作失败！");
			}
		}catch (Exception e) {
			// TODO: handle exception
			write("操作失败！");
		}
	}

	public void paserRule() throws UnsupportedEncodingException{
		String fileNme = getStringParameter("filename");
		String templateid = getStringParameter("templateid");
		String fileId = getStringParameter("fileid");
		RunExcelPaser config = new RunExcelPaser();
		config.setParamService(paramService);
		try{
			List<Map<String,Object>> parserMap = paramService.getParserMap(templateid,fileId);
			config.ParserStructureById(parserMap);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void parsefiledTree() throws UnsupportedEncodingException{
		
	        String dbname = getStringParameter("dbname");
			// 获取数据库中解析存储数据表信息
			String filedTree = paramService.GetOptionsTree(dbname,"");
			filedTree = filedTree.substring(0, filedTree.length()-1);
			write(filedTree);
	}

}

