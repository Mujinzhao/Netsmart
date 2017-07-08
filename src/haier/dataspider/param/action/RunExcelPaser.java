package haier.dataspider.param.action;

import haier.dataspider.entity.ParserStructure;
import haier.dataspider.excelparser.ExcelConfig;
import haier.dataspider.excelparser.ExcelToBean;
import haier.dataspider.filemanger.service.FileMangerService;
import haier.dataspider.param.service.ParamService;
import haier.dataspider.param.service.SpringContextUtils;
import haier.dataspider.template.service.TemplateService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.core.tag.util.SystemConstant;

import core.util.WDWUtil;

/**
 * 解析excel模板信息入库
 * @author admin
 *
 */
public class RunExcelPaser{
	private static final Logger logger = Logger.getLogger(RunExcelPaser.class);
	ExcelToBean exceinterface = new ExcelToBean();
	private String rootPath;
	private String fileName;
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	ParamService paramService=null;
	public ParamService getParamService() {
		return paramService;
	}
	public void setParamService(ParamService paramService) {
		this.paramService = paramService;
	}
	TemplateService templateService;
	private FileMangerService fileService;
	public FileMangerService getFileService() {
		return fileService;
	}
	public void setFileService(FileMangerService fileService) {
		this.fileService = fileService;
	}
	Map paramMap = new HashMap();
  
    List<ParserStructure> datalist;
    String fileid;
	
	public RunExcelPaser(){
	}
	public RunExcelPaser(String rootPath,String fileName){
		 this.rootPath = rootPath;
		 this.fileName = fileName;
		 paramService = (ParamService) SpringContextUtils.getBean("paramService");
		 fileService = (FileMangerService) SpringContextUtils.getBean("fileService");
		 templateService =(TemplateService) SpringContextUtils.getBean("templateService");
	}
	
	public void parserRun(String name){
		    //获取数据库模板信息
            Map<String,Object> tempMap = templateService.getTemplateInfo();
			if(tempMap != null && tempMap.size()>0){
				  boolean parserflag=true;
				   // 迭代Map 匹对当前文件名称获取模板ID
				   for(Entry<String, Object> entry : tempMap.entrySet()){
					   String templateFname =  entry.getKey().toString();
					   if(name.contains(templateFname)){
	   					 String templateId=entry.getValue().toString();
	   					 sysRunExcelPaser(templateId,templateFname);
	   					 parserflag =false;
	   					 break;
   				       }
			       }
				   if(parserflag){
					   logger.warn("上传的文件尚未匹配数据库中的模板信息");
				   }
			}else{
				logger.info("获取数据库模板信息失败！");
			}
	}
	
	/**
	 * 解析新增目录下的文件信息
	 * @param rootPath
	 * @param fileName
	 */
	public void sysRunExcelPaser(String templateId,String templateFname){
		try{
		   boolean fileflag =  fileService.checkFileInfo(fileName,templateId);
	       if(!fileflag){
				paramMap.clear();
				paramMap.put("id", WDWUtil.getSeqNextval());
				paramMap.put("fileName", WDWUtil.getExtentionName(fileName));
				paramMap.put("template_name",templateFname);
				paramMap.put("fileType", WDWUtil.getExtentionType(fileName));
				paramMap.put("filePath", rootPath+fileName);
				paramMap.put("templateId", templateId);
//				Integer insnum =  
				ParserStructureByMap(paramMap);
	       }else{
	    	   logger.warn("数据库中检测到 "+templateFname+" 模板下已解析 "+fileName+" 文件信息！");
	       }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("操作数据库异常! "+e.getMessage());
		}
    }
	public Integer ParserStructureByMap(Map<String,Object> parserMap) throws Exception{
		Integer insnum = 0;
		if(null != parserMap && parserMap.size()>0){
			    // 文件ID
				String fileid = parserMap.get("id").toString();
				// 模板ID
				String templateid=parserMap.get("templateId").toString();
				//模板别名
				String templatename = parserMap.get("template_name").toString();
				// 文件路径
				String filePath = parserMap.get("filePath").toString();
				List<ParserStructure> parserlist = paramService.getListParams(templateid);
				if(null != parserlist && parserlist.size()>0){
					logger.info("调用数据库中 "+templatename+" 模板信息");
					ExcelConfig excelParser = new ExcelConfig();
					logger.info("开始解析读取 "+filePath+" 文件");
					Workbook wb = excelParser.readExcel(filePath);
					try {
						    // 解析入库
				    	    insnum=  paramService.RunExStructure(wb,excelParser,parserlist,fileid);
				    	    if(insnum>0){
					    			// 修改中间表中的模板状态
					    			paramMap.put("parsernum", insnum);
					    			File savepath= new File(rootPath+fileName);
					    			Long fileSize;
									fileSize = WDWUtil.getFileSizes(savepath);
									String fileSizeStr= WDWUtil.formetFileSize(fileSize);
									paramMap.put("filesize", fileSizeStr);
									try {
					    				paramMap.put("statusType", "T");
										fileService.uploadFile(savepath, parserMap);
										//paramService.modifyParserInfo(paramMap);
						        	    logger.info("解析 "+filePath+" 文件完成受影响总行数:"+insnum);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										logger.info("保存解析文件信息失败"+e.getMessage());
										throw e;
									}
						  } 
			    	   }catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logger.info("文件大小转换失败 "+e.getMessage());
							throw e;
					  }
				}else{
					 logger.info(templatename+" 模板解析结构未配置！");
				}
			}
		return insnum;
	}
	
	/**
	 * 根据文件名称获取解析模板信息
	 * @param fileName
	 * @return
	 */
	public Integer ParserStructureById(List<Map<String,Object>> parserMap) throws Exception{
		Integer insnum = 0;
		if(null != parserMap && parserMap.size()>0){
			for(Map<String,Object> entry:parserMap){
				String fileid = entry.get("file_pkid").toString();
				String templateid=entry.get("template_id").toString();
				String templatename = entry.get("template_name").toString();
				String filePath = entry.get("file_path").toString();
				String fileType= entry.get("file_type").toString();
				String fileName= entry.get("file_fname").toString();
			    String parsePath = filePath+fileName+fileType;
				List<ParserStructure> parserlist = paramService.getListParams(templateid);
				if(null != parserlist && parserlist.size()>0){
					logger.info("调用数据库中 "+templatename+" 模板信息");
					ExcelConfig excelParser = new ExcelConfig();
					logger.info("开始解析读取 "+parsePath+" 文件");
					Workbook wb = excelParser.readExcel(parsePath);
					// 解析入库
		    	    insnum=  paramService.RunExStructure(wb,excelParser,parserlist,fileid);
		    	    if(insnum>0){
		    			// 修改中间表中的模板状态
		    			paramMap.put("parsernum", insnum);
		    			paramMap.put("filepkid", fileid);
		        		paramService.modifyParserInfo(paramMap);
		        	    logger.info("解析 "+filePath+" 文件完成受影响总行数:"+insnum);
		    		}
				}else{
					 logger.info("解析 "+templatename+" 模板解析结构未配置！");
				}
			}
     	}
		return insnum;
	}
	
	/**
	 * 根据文件id,文件名称解析
	 * @param fileName
	 * @return
	 */
	public Integer ParserStructureById(String id,String fileName)throws Exception{
		Integer insnum = 0;
		String delName =fileName.substring(0,fileName.lastIndexOf("."));
		// 获取模板信息
		//分别获取 文件ID、解析模板任务ID
		List<ParserStructure> parserlist = paramService.getListParams(id);
		paramMap.clear();
		paramMap.put("filepkid", id);
		paramMap.put("name", delName);
		if(null != parserlist && parserlist.size()>0){
     		logger.info("匹对数据库中 "+fileName+"的模板信息");
     		ExcelConfig excelParser = new ExcelConfig();
     		logger.info("开始解析读取"+SystemConstant.get("haierfilePath")+fileName+"文件");
     		rootPath = SystemConstant.get("haierfilePath");
    		Workbook wb = excelParser.readExcel(rootPath+fileName);
    		// 解析入库
    	    insnum=  paramService.RunExStructure(wb,excelParser,parserlist,fileid);
    		if(insnum>0){
    			// 修改中间表中的模板状态
    			paramMap.put("parsernum", insnum);
        		paramService.modifyParserInfo(paramMap);
        	    logger.info("解析"+rootPath+fileName+"文件完成受影响总行数:"+insnum);
    		}
     	}
		return insnum;
	}
	/**
	 * 根据文件名称，查询数据库中相似度大于等于0.6以上的模板信息 返回解析模板ID
	 * @return
	 */
//	public String  getParserNameXSD(){
//		String name = "";
//		MyLevenshtein levenshtein = new MyLevenshtein();
//		try{
//		// 获取所有中间表中的模板信息
//		List<Map<String, Object>> filelist= paramService.getListMap();
//		for(Map<String, Object> map:filelist){
//				//计算相似度,调用解析对应的模板信息
//				float xsnum=levenshtein.levenshteinStr(fileName,map.get("file_fname").toString());
//				//计算相似度大于等于0.6 表示模板已存在数据库中
//				if(xsnum>=0.6){
//					// 检测是否配置模板信息
//					Integer mbcount =  paramService.getByCount(name);
//					if(mbcount >0){
//						name = xsnum+"|"+map.get("file_pkid").toString();
//						return name;
//					}else{
//						name ="|"+map.get("file_pkid").toString();
//					}
//					return name;
//				}
//	     }
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return "0";
//		
//    }
}
