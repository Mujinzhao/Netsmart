package haier.dataspider.param.action;

import haier.dataspider.param.service.WebSitService;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import core.BasePagingQueryAction;


/**
 * 网站模板操作类
 * @author admin
 *
 */
public class WebSiteTemplateAction extends BasePagingQueryAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	WebSitService websitService;
	public WebSitService getWebsitService() {
		return websitService;
	}

	public void setWebsitService(WebSitService websitService) {
		this.websitService = websitService;
	}

	/****************** 网站模板增、删、改、查***********************/
	public String websitePage(){
		return SUCCESS;
	}
	
	/**
	 * 添加网站配置模板信息 
	 * @throws UnsupportedEncodingException 
	 */
	public void addwebsitTemplate() throws UnsupportedEncodingException{
		Map paramMap = websitService.getParamMap(request);
		Integer insertsql = websitService.insertWebSite(paramMap);
		if(insertsql > 0){
		  write("success");
		}
		else if(insertsql==-1){
			write("error");
		}
	}
	/**
	 * 修改网站配置模板信息
	 * @throws UnsupportedEncodingException 
	 */
	
	public void modfiywebsitTemplate() throws UnsupportedEncodingException{
		Map paramMap = websitService.getParamMap(request);
		Integer modfiysql = websitService.update(paramMap);
		if(modfiysql > 0){
			  write("success");
		}
		else if(modfiysql==-1){
			write("error");
		}
	}
	/**
	 * 删除网站配置模板信息
	 * @throws UnsupportedEncodingException 
	 */
	public void delwebsiteTemplate() throws UnsupportedEncodingException{
		Map paramMap = websitService.getParamMap(request);
		Integer delsql = websitService.del(paramMap);
		if(delsql > 0){
			  write("success");
		}
		else if(delsql==-1){
			write("error");
		}
	}
}
