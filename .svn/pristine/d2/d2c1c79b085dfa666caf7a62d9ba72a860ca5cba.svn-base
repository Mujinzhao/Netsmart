package datacvg.excel.action;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.JsonGenerator;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.WDWUtil;
import datacvg.excel.service.FileMangerService;
import datacvg.parse.util.FileUitl;


public class FileMangerAction extends BasePagingQueryAction{

	/**
	 *  解析文件列表管理
	 */
	private static final long serialVersionUID = 1025269229162476459L;
	  /**
	   * 编码格式
	   */
	protected static final String CHARACTER_ENCODING = "utf-8";
	
    private FileMangerService fileService;
	public FileMangerService getFileService() {
		return fileService;
	}
	public void setFileService(FileMangerService fileService) {
		this.fileService = fileService;
	}
	public String filePage() throws UnsupportedEncodingException{
		String templateid = getStringParameter("templateid");
		request.setAttribute("templateId", templateid);
		String templatetype = getStringParameter("templatetype");
		request.setAttribute("templatetype", templatetype);
		return SUCCESS;
	}
	public String parserfilePage() throws UnsupportedEncodingException{
		String templateid = getStringParameter("templateid");
		request.setAttribute("templateId", templateid);
		String strutnum = getStringParameter("strutnum");
		request.setAttribute("strutnum", strutnum);
		return SUCCESS;
	}
	//上传解析文件
	public void uploadFiles() throws UnsupportedEncodingException{
		    Map<String, String> paramMap= getParamMap(request);
		try {
//			System.out.println(paramMap);
			String sql = SystemConstant.get("");
			sql="insert into excel_file_list values (:excelid,:templateId,:excelname,:excelsize,:exceltype,:excelPath,'','','','','','',sysdate,'',null,'')";
			int opindex = fileService.OparserSql(sql, paramMap);
			if(opindex>0){
				write("success");
			}
		} catch (Exception e) {
			write("error");
		}
		write("success");
	
	}
	//删除文件信息
	public void delFile() {
		try{
			Map<String, String> paramMap= getParamMap(request);
//			System.out.println(paramMap);
			String sql = SystemConstant.get("");
			sql="delete from  excel_file_list  e where e.excelid=:excelid";
			int opindex = fileService.OparserSql(sql, paramMap);
			if(opindex>0){
				FileUitl.delFiles(paramMap.get("excelPath").toString());
				write("success");
			}
		}catch (Exception e) {
			// TODO: handle exception
			write(JsonGenerator.getObjectJsonContent("error"));
		}
	}
	// 操作参数  MAP 集合
	public  Map<String, String> getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
			Map<String, String>  paramMap = new HashMap<String, String>();
			String excelid =MyURLDecoder.decode(request.getParameter("id"), "UTF-8");
			excelid = RegexValidate.StrNotNull(excelid)==true?excelid:WDWUtil.getSeqNextval();
			paramMap.put("excelid", excelid);
			
			
			String templateId =MyURLDecoder.decode(request.getParameter("templateid"), "UTF-8");
			paramMap.put("templateId", templateId);
			
			String excelname = getStringParameter("excelname");
			if(RegexValidate.StrNotNull(excelname)){
				paramMap.put("excelname", WDWUtil.getExtentionName(excelname));
			}
		
			String exceltype = getStringParameter("exceltype");
			paramMap.put("exceltype", exceltype);
			
			String excelsize =getStringParameter("excelsize");
			if(RegexValidate.StrNotNull(excelsize)){
				excelsize = WDWUtil.formetFileSize(Long.parseLong(excelsize));
				paramMap.put("excelsize", excelsize);
			}
			String templatetype = getStringParameter("templatetype");
			String excelPath = SystemConstant.get("templatePath")+templatetype+"\\"+excelname;
			paramMap.put("excelPath", excelPath);
			
			
			return paramMap;
		}
}