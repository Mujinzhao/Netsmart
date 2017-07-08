package haier.dataspider.template.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.core.tag.util.SystemConstant;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.util.RegexValidate;
import core.util.WDWUtil;

public class TemplateService {
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
	
	public  void uploadTemplateFile(File upload ,Map paramMap)throws Exception{
		 Session session = mySessionFactory.openSession();
		 Transaction tran = null ;
		 File filePath = null;
		try {
			tran = session.beginTransaction();
			tran.begin();
			// 获取模板保存的目录
			String templatePath = SystemConstant.get("templatePath");
			String savePath = templatePath+paramMap.get("templateType")+"\\\\";
			filePath = new File(savePath);
			//如果不存在则创建
			if(!filePath.exists()){
				filePath.mkdir();
			}
			paramMap.put("templatePath", filePath);
			long templateSize = WDWUtil.getFileSizes(upload);
			paramMap.put("templateSize", WDWUtil.formetFileSize(templateSize));
			
			paramMap.put("statusType","T");
			String inserTemplate = SystemConstant.get("inserTemplate");
			
			dao.exeSql(session, inserTemplate,paramMap);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(null !=filePath)
			   filePath.delete();
			
			tran.rollback();
			//异常抛出
			throw e;
		}finally{
			if(session != null)
				try {
					mySessionFactory.closeSession(session);
				} catch (Exception e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}	
	}
	public  void uploadTemplate(File filePath,Map paramMap)throws Exception{
		 Session session = mySessionFactory.openSession();
		 Transaction tran = null ;
		try {
			tran = session.beginTransaction();
			tran.begin();
			paramMap.put("statusType","T");
			String inserTemplate = SystemConstant.get("inserTemplate");
			dao.exeSql(session, inserTemplate,paramMap);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(null !=filePath)
				filePath.delete();
			tran.rollback();
			//异常抛出
			throw e;
		}finally{
			if(session != null)
				try {
					mySessionFactory.closeSession(session);
				} catch (Exception e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}	
	}
	/**
	 * 删除文件
	 * 
	 */
	public void delTemplate(String id,String filePath) throws Exception{
		String delsql= "delete from  parser_template t where t.template_id=:templateid";
		Map map = new HashMap();
		map.clear();
		map.put("templateid", id);
		try{
		   dao.exeSql(delsql,map);
		   File delFile = new File(filePath);
		   FileUtils.deleteDirectory(delFile);//删除目录 
//		   delFile.delete();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
    public boolean checkTemplateInfo(String templateType) throws Exception{
    	boolean flag = false;
    	String sql = "select * from parser_template t where t.template_type='"+templateType+"'";
    	
    	List<?> list = dao.selectSqlAuto(sql);
		if(list!=null && list.size()>0){
		  return true;
		}
    	return flag;
    }

    public Map<String,Object> getTemplateInfo(){
    	String sql = "select t.template_id,t.template_type from parser_template t where t.status='T'";
		Map<String,Object> templateMap = new HashMap<String, Object>();
		try {
			List list = dao.selectSqlAuto(sql);
			if(null != list && list.size()>0){
				templateMap.clear();
				for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[])list.get(i);
						templateMap.put(String.valueOf(obj[1] == null ? "" : obj[1]), String.valueOf(obj[0] == null ? "" : obj[0 ]));
//						templateMap.put("templateid", String.valueOf(obj[0] == null ? "" : obj[0]));
//						templateMap.put("templatetype", String.valueOf(obj[1] == null ? "" : obj[1]));
						
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateMap;
    }
    
    public List<Map<String,Object>> getParserMap(String templatid){
 		String sql = "select f.file_pkid, f.template_id,f.file_path,t.template_type,t.template_name," +
				"f.file_fname,t.template_filetype from parser_template t " +
				"where t.status='T'' ";
				if(!RegexValidate.StrisNull(templatid)){
					sql += "and f.template_id='"+templatid+"'";
				}
		List<Map<String,Object>>  filelist= null;
		try {
			filelist = dao.queryMapList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filelist;
	}

    public void modfiyTemplate(Map map){
    	String delsql= "update parser_template t set t.template_type=:type,t.template_path=:path where t.template_id=:templateid";
		try{
		   dao.exeSql(delsql,map);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
}
