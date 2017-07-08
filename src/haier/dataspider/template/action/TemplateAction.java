package haier.dataspider.template.action;

import haier.dataspider.template.service.TemplateService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.WDWUtil;

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
	
	File upload;
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	/**
	 * 模板配置界面
	 * @return
	 */
	public String templatePage(){
		return SUCCESS;
	}
	// 上传模板
	public void uploadTemplate(){
		try {
			Map paramMap= new HashMap(); 
			String templateId = WDWUtil.getSeqNextval();
			String templateName = getStringParameter("templatename");
			String templateType = getStringParameter("templatetype");
			String templatefileType =getStringParameter("templatefiletype");
			String templateSize =getStringParameter("templatesize");
			
			String templatePath = SystemConstant.get("templatePath")+templateType;
			templateSize = WDWUtil.formetFileSize(Long.parseLong(templateSize));
			paramMap.put("templateId", templateId);
			paramMap.put("templateName", WDWUtil.getExtentionName(templateName));
			paramMap.put("templateType", templateType);
			paramMap.put("templatefileType", templatefileType);
			paramMap.put("templateSize", templateSize);
			paramMap.put("templatePath", templatePath);
			File saveFile = new File(templatePath+templateName);
			templateService.uploadTemplate(saveFile,paramMap);
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
			String templateId =getStringParameter("templateid");
			String templatePath = getStringParameter("templatepath");
			templateService.delTemplate(templateId,templatePath);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write("error");
		}
		write("success");
	}
//	//上传时检验文件是否存在
//	public void checkTemplate(){
//		boolean checkflag = false;
//		String templateName =getStringParameter("templatename");
//		try{
//			checkflag =templateService.checkTemplateInfo(templateName);
//		}catch (Exception e) {
//			// TODO: handle exception
//			write("error");
//		}
//		write(checkflag+"");
//	}

	public void modfiyTemplate() throws UnsupportedEncodingException{
		boolean checkflag = false;
		String id =getStringParameter("templateid");
		String type =getStringParameter("templateType");
		String oldtype =getStringParameter("oldtemplateType");
		
		try{
			checkflag =templateService.checkTemplateInfo(type);
			if(checkflag){
				write("wring");
			}else{
				
				Map map = new HashMap();
				map.clear();
				map.put("templateid", id);
				map.put("type", type);
				map.put("path", SystemConstant.get("templatePath")+type);
				templateService.modfiyTemplate(map);
				File file = new File(SystemConstant.get("templatePath")+oldtype);
				System.out.println(SystemConstant.get("templatePath")+type);
				boolean flag = file.renameTo(new File(SystemConstant.get("templatePath")+type));
				write("success");
			}
		}catch (Exception e) {
			// TODO: handle exception
			write("error");
		}
	}
}
