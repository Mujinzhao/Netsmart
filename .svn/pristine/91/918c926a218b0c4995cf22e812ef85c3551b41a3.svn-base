package datacvg.dbconfg.action;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import datacvg.dbconfg.util.ReadSQLFile;
import datacvg.parse.service.ParserTaskService;

public class DbConfigActrion  extends BasePagingQueryAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd"); 
	ParserTaskService parsertaskService;
	public ParserTaskService getParsertaskService() {
		return parsertaskService;
	}
	public void setParsertaskService(ParserTaskService parsertaskService) {
		this.parsertaskService = parsertaskService;
	}
	public String configPage(){
		return SUCCESS;
	}
	public String generalPage(){
		// 获取当前数据库中的所有用户
	    String usersql = SystemConstant.get("getDbausers");
	    String userselect = parsertaskService.GetOptionsSelect(usersql, null);
	    request.setAttribute("userselect", userselect);
	    
	    String spaceSql = SystemConstant.get("getDbausertablespace");
	    String spaceselect = parsertaskService.GetOptionsSelect(spaceSql, null);
	    request.setAttribute("spaceselect", spaceselect);
		return SUCCESS;
	}
	/**
	 * 创建数据库表
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 */
	public void dbConfigTable() throws UnsupportedEncodingException{
		// 获取创建表内容信息
		String tablecontent  = getStringParameter("tablecontent");
		try{
			ReadSQLFile.readSQLContent(tablecontent);
//			parsertaskService.readSQLContent(tablecontent);
			write("success");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write(e.getMessage());
		}
	}
}
