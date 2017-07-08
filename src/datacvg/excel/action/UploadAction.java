package datacvg.excel.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
/**
 * 文件上传操作类
 * @author admin
 *
 */
public class UploadAction extends BasePagingQueryAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064261515962193585L;
	private File uploadify;
	private String uploadifyFileName;
	/**
	 * 上传文件到指定目录
	 * @return
	 * @throws IOException
	 */
	public String uploadFile() throws IOException{
		String rootPath;
		//保存文件的路径
//		String pageflag = MyURLDecoder.decode(request.getParameter("pageflag");
//		if(RegexValidate.StrNotNull(pageflag) && pageflag.equals("template")){
			rootPath =SystemConstant.get("templatePath")+getStringParameter("templatetype");
//		}else{
//			rootPath =SystemConstant.get("excelPath");
//		}
		
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