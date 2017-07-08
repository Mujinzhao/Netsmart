package haier.dataspider.filemanger.action;

import haier.dataspider.filemanger.service.FileMangerService;
import haier.dataspider.template.service.TemplateService;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.MyURLDecoder;

public class UploadAction extends BasePagingQueryAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064261515962193585L;
	private File uploadify;
	private String uploadifyFileName;
	TemplateService templateService;
	public TemplateService getTemplateService() {
		return templateService;
	}
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
    private FileMangerService fileService;
	public FileMangerService getFileService() {
		return fileService;
	}
	public void setFileService(FileMangerService fileService) {
		this.fileService = fileService;
	}
	public String uploadFile() throws IOException{
		String rootPath;
		//保存文件的路径
		if(MyURLDecoder.decode(request.getParameter("pageflag"), "UTF-8").equals("template")){
			rootPath =SystemConstant.get("templatePath")+getStringParameter("templateType");
		}else{
			rootPath =SystemConstant.get("haierfilePath");
		}
		//上传完后文件存放位置
		String savePath = rootPath;
		File filePath = new File(savePath);
		//如果不存在则创建
		if(!filePath.exists()){
			filePath.mkdir();
		}
		//保存文件路径名
		File saveFile = new File(filePath+"//"+uploadifyFileName);
		if(saveFile.exists()){
			write("wring");
		}else{
			// 将文件另存到指定目录
			try {
				FileUtils.copyFile(uploadify, saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public File getUploadify() {
		return uploadify;
	}
	public void setUploadify(File uploadify) {
		this.uploadify = uploadify;
	}
	public String getUploadifyFileName() {
		return uploadifyFileName;
	}
	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}
}