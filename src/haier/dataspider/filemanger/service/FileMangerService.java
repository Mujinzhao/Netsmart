package haier.dataspider.filemanger.service;

import haier.dataspider.filemanger.entity.FilemangerBean;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.core.tag.util.SystemConstant;
import core.util.WDWUtil;
import core.util.RegexValidate;
import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;

public class FileMangerService {
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
	
	public  void uploadFile(File upload ,FilemangerBean filemangerbean)throws Exception{
		 Session session = mySessionFactory.openSession();
		 Transaction tran = null ;
		try {
			tran = session.beginTransaction();
			tran.begin();
			String savePath = SystemConstant.get("haierfilePath");
			File filePath = new File(savePath);
			//如果不存在则创建
			if(!filePath.exists()){
				filePath.mkdir();
			}
			long filesize = WDWUtil.getFileSizes(upload);
			filemangerbean.setFileSize(WDWUtil.formetFileSize(filesize));
			String sql = "insert into parser_file_list values(" +
					"'"+filemangerbean.getFilePkid()+"'," +
					"'"+filemangerbean.getFileFname()+"'," +
					"'"+filemangerbean.getFileSize()+"'," +
					"'"+RegexValidate.nulltoStr(filemangerbean.getDevolepUsername())+"'," +
				    "'"+filemangerbean.getFileType()+"'," +
				    "'"+RegexValidate.nulltoStr(filemangerbean.getFilePath())+"'," +
				    "'"+RegexValidate.nulltoStr(filemangerbean.getUploadUsername())+"'," +
				    "'"+RegexValidate.nulltoStr(filemangerbean.getFileDescription())+"'," +
				    "'"+RegexValidate.nulltoStr(filemangerbean.getCreateUser())+"'," +
				    "sysdate," +
				    "'"+RegexValidate.nulltoStr(filemangerbean.getUpdateUser())+"'," +
				    "sysdate," +
				    "'"+filemangerbean.getStatus()+"'" +
				  ")";
			
			String savepath = savePath+filemangerbean.getFileFname()+filemangerbean.getFileType();
		    File saveFile = new File(savepath);
		    Map paramMap = new HashMap();
		    paramMap.clear();
			dao.exeSql(session, sql, paramMap);
			paramMap.put("id", WDWUtil.getSeqNextval());
			paramMap.put("filePkid", filemangerbean.getFilePkid());
			paramMap.put("name", filemangerbean.getFileFname());
			paramMap.put("filepath", savepath);
			paramMap.put("status", "F");
		    // 中间表
			String psql = "insert into PARSER_STRUCTURE_SHEET_INFO values (:id,:name,sysdate,:filepath,:status,:filePkid,0)";
			dao.exeSql(session, psql, paramMap);
			// 将文件另存到指定目录
			try {
				FileUtils.copyFile(upload, saveFile);
			} catch (IOException e) {
				tran.rollback();
				e.printStackTrace();
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
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
	public  void uploadFile(File upload ,Map paramMap)throws Exception{
		 Session session = mySessionFactory.openSession();
		 Transaction tran = null ;
		try {
			tran = session.beginTransaction();
			tran.begin();
			String sql = "insert into parser_file_list values (:id,:templateId,:fileName," +
					":filesize,'',:fileType," +
					":filePath,'',''" +
					",'',sysdate,'',sysdate" +
					",:statusType,'T',:parsernum,'')";
			dao.exeSql(session, sql, paramMap);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
			if(null !=upload)
				upload.delete();
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
	public void delFile(String id,String fileName) throws Exception{
//		fileName =fileName.substring(0,fileName.lastIndexOf("."));
		String sql = "delete from parser_file_list l where l.FILE_PKID='"+id+"'";
		//删除中间表
//		String dsql = "delete from parser_structure_sheet_info s where s.excel_name='"+fileName+"'";
		
		 Session session = mySessionFactory.openSession();
		 Transaction tran = null ;
		try {
			tran = session.beginTransaction();
			tran.begin();
			dao.deleteSql(session, sql);
//			dao.deleteSql(session, dsql);
			String savepath = SystemConstant.get("haierfilePath")+fileName;
		    File delFile = new File(savepath);
		    // 判断目录或文件是否存在   
		    delFile.delete();
		    tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
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
    public boolean checkFileInfo(String filename,String templateid) throws Exception{
    	boolean flag = false;
        String sql = "select f.file_pkid,f.file_fname from parser_file_list f " +
    			" where f.template_id ='"+templateid+"'and  f.file_fname  ='"+WDWUtil.getExtentionName(filename)+"'" +
    			" and f.parserstatus='T' and f.parsernum > 0";
    	List<?> list = dao.selectSqlAuto(sql);
		if(list!=null && list.size()>0){
		  return true;
		}
    	return flag;
    }
}
