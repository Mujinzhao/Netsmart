package haier.dataspider.filemanger.action;

import haier.dataspider.filemanger.service.FileMangerService;
import haier.dataspider.param.service.ParamService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.JsonGenerator;
import core.util.MyURLDecoder;
import core.util.WDWUtil;


public class FileMangerAction extends BasePagingQueryAction{

	/**
	 *  文件列表管理
	 */
	private static final long serialVersionUID = 1025269229162476459L;
	  /**
	   * 编码格式
	   */
	protected static final String CHARACTER_ENCODING = "utf-8";

	File upload;
	ParamService paramService;
	public ParamService getParamService() {
		return paramService;
	}

	public void setParamService(ParamService paramService) {
		this.paramService = paramService;
	}
    private FileMangerService fileService;
	public FileMangerService getFileService() {
		return fileService;
	}
	public void setFileService(FileMangerService fileService) {
		this.fileService = fileService;
	}

	public String filePage(){
		
		return SUCCESS;
	}
	public String parserfilePage() throws UnsupportedEncodingException{
		String templateid = getStringParameter("templateid");
		request.setAttribute("templateid", templateid);
		String strutnum = getStringParameter("strutnum");
		request.setAttribute("strutnum", strutnum);
		return SUCCESS;
	}
	public void uploadFiles(){

		Map paramMap= new HashMap(); 
		try {
			String fileid = WDWUtil.getSeqNextval();
			String templateid = getStringParameter("templateid");
			String fileName = getStringParameter("filename");
			String filetype= getStringParameter("fileOperType");
			String filesize = getStringParameter("filesize");
			String filePath= SystemConstant.get("haierfilePath");
			paramMap.put("id", fileid);
			paramMap.put("fileName", WDWUtil.getExtentionName(fileName));
			paramMap.put("fileType", filetype);
			paramMap.put("filePath", SystemConstant.get("haierfilePath"));
			paramMap.put("templateId", templateid);
			paramMap.put("filesize", WDWUtil.formetFileSize(Long.parseLong(filesize)));
			paramMap.put("statusType", "F");
			paramMap.put("parsernum", "0");
			
			upload=new File(filePath+fileName);
			fileService.uploadFile(upload,paramMap);
		} catch (Exception e) {
			write("error");
		}
		write("success");
	
	}
	public void delFile() {
		try{
			String filename = MyURLDecoder.decode(request.getParameter("filename"), "UTF-8");
//			String delName =filename.substring(0,filename.lastIndexOf("."));
			String id = MyURLDecoder.decode(request.getParameter("filepkid"), "UTF-8");
			fileService.delFile(id,filename);
			write("success");
		}catch (Exception e) {
			// TODO: handle exception
			write(JsonGenerator.getObjectJsonContent("error"));
		}
	}
}