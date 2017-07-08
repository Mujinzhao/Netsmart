package haier.dataspider.param.service;


import haier.dataspider.entity.ParserStructure;
import haier.dataspider.excelparser.ExcelConfig;
import haier.dataspider.excelparser.ExcelToBean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;

import com.core.tag.util.SystemConstant;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.WDWUtil;


public class ParamService {
	private static final Logger haier = Logger.getLogger(ParamService.class);
	public ParamService(){
	}
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
	// 自动补全参数集合
	Map paramrowMap = new HashMap();
	Map dataMap = new HashMap();
	// 根据表名称  获取表字段信息  
	public  String GetOptionsTree(String dbname,String likeflag) 
	{
		String  temp = "";
		String sqlKey =null;
		boolean flag = false;
		Map paramMap = new HashMap();
		paramMap.clear();
		
		if(RegexValidate.StrNotNull(dbname)){
			// 根据表名称获取表字段
			paramMap.put("tabname", dbname);
			sqlKey =SystemConstant.get("getTableFiled");
			flag = true;
		}else{
			sqlKey =SystemConstant.get("getPaserSaveTable");
			sqlKey="select table_name ,comments as commdesc,owner" +
					" from all_tab_comments where table_name like '"+likeflag+"%' and table_type ='TABLE'";
			temp = "<option value=''>--请选择--</option>";
		}
		
		if(sqlKey != null){
			try {
				List list = dao.selectSqlAuto(sqlKey,paramMap);
				StringBuffer sb = new StringBuffer();	
				if (list != null) {
					for (int i=0; i<list.size(); i++) {
						Object[] obj = (Object[]) list.get(i);
						String row1 = String.valueOf(obj[0] == null ? "" : obj[0]);
						String commdesc = String.valueOf(obj[1] == null ? "" : obj[1]);
						String owner ="";
						if(obj.length == 3){
							owner = String.valueOf(obj[2] == null ? "" : obj[2]);
						}
//						sb.append("<option id=\"'"+owner+"'\" value=\'" + row1 + "\'>" + commdesc + "</option>\n");
						temp += "<option id=\""+owner+"\" value=\'" + row1 + "\'>" + commdesc + "</option>\n";
					}
					sb.append(temp);
					return sb.toString();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
		}
		
		return "";
	} 
	
	public Map getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
		String id = MyURLDecoder.decode(request.getParameter("id"), "UTF-8");
		String templateid = MyURLDecoder.decode(request.getParameter("templateid"), "UTF-8");
		String sheetname = MyURLDecoder.decode(request.getParameter("sheetname"), "UTF-8");
		String sheetable = MyURLDecoder.decode(request.getParameter("sheetable"), "UTF-8");
		// 获取解析接口方法
		String interfaces = MyURLDecoder.decode(request.getParameter("parseinterface"), "UTF-8");
		// 获取存储数据库表名
		String tableName = MyURLDecoder.decode(request.getParameter("dbtable"), "UTF-8");
		String srownum = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("srownum"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("srownum"), "UTF-8"):"";
		String erownum = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("erownum"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("erownum"), "UTF-8"):"";
		String scellnum = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("scellnum"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("scellnum"), "UTF-8"):"";
		String ecellnum = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("ecellnum"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("ecellnum"), "UTF-8"):"";
		
		//解析开始-解析结束范围
		String parserscope = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("srowstr"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("srowstr"), "UTF-8"):"";
		String parserecope = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("erowstr"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("erowstr"), "UTF-8"):"";
		String parserstauts = RegexValidate.StrNotNull(MyURLDecoder.decode(request.getParameter("parstauts"), "UTF-8"))?MyURLDecoder.decode(request.getParameter("parstauts"), "UTF-8"):"T";
		
//		System.out.println("----->"+(String)session.getAttribute("excelParser"));
		String parsfiled =  MyURLDecoder.decode(request.getParameter("parsefiled"), "UTF-8");
		String parserbrand = MyURLDecoder.decode(request.getParameter("parserbrand"), "UTF-8");
		String tableunit   = MyURLDecoder.decode(request.getParameter("tableunit"), "UTF-8");
		Map map = new HashMap();
		map.clear();
		map.put("id", id);
		map.put("templateid", templateid);
		map.put("sheetname", sheetname);
		map.put("tablename", sheetable);
		map.put("dbtablename",tableName);
		map.put("interfaces", interfaces);
		map.put("srownum", srownum);
		map.put("erownum", erownum);
		map.put("scellnum", scellnum);
		map.put("ecellnum", ecellnum);
		map.put("parsefiled",parsfiled);
		map.put("parsebrandname", parserbrand);
		map.put("tableunit",tableunit);
		map.put("parserscope",parserscope);
		map.put("parserecope", parserecope);
		map.put("parserstauts", parserstauts);
		
		return map;
	}
	
	/**
	 * 根据模板ID 获取解析模板信息
	 * @param id
	 * @return
	 */
	public List<ParserStructure> getListParams(String id){
		
		List<ParserStructure> parserList = new  ArrayList<ParserStructure>();
		ParserStructure parser = null;
		try{
			//getStructure
			String sql = SystemConstant.get("getStructure");
			Map map = new HashMap();
			map.clear();
			map.put("id", id);
			List list = dao.selectSqlAuto(sql, map);
			if(null != null && list.size()>0)
				parserList = new ArrayList<ParserStructure>();
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[])list.get(i);
					parser= new ParserStructure();
					parser.setStructreid(String.valueOf(obj[0] == null ? "" : obj[0]));
					parser.setTemplateid(String.valueOf(obj[1] == null ? "" : obj[1]));
//					parser.setFileid(String.valueOf(obj[1] == null ? "" : obj[1]));
					parser.setSheetname(String.valueOf(obj[2] == null ? "" : obj[2]));
					parser.setRowtabname(String.valueOf(obj[3] == null ? "" : obj[3]));
					parser.setDbtable(String.valueOf(obj[4] == null ? "" : obj[4]));
					parser.setInterfaces(String.valueOf(obj[5] == null ? "" : obj[5]));
					parser.setSrownum(String.valueOf(obj[6] == null ? "" : obj[6]));
					parser.setErownum(String.valueOf(obj[7] == null ? "" : obj[7]));
					parser.setScellnum(String.valueOf(obj[8] == null ? "" : obj[8]));
					parser.setEcellnum(String.valueOf(obj[9] == null ? "" : obj[9]));
//					System.out.println(obj[11]);
					String content = "";
					Object boj11 = obj[11] == null ? "" : obj[11];
					if(!RegexValidate.StrisNull(boj11+"")){
					    content =  WDWUtil.ClobToString((Clob)(obj[11] == null ? "" : obj[11]));
					}
					parser.setParsercontent(content);
					parser.setParsefiled(String.valueOf(obj[12] == null ? "" : obj[12]));
					parser.setParsebrandname(String.valueOf(obj[13] == null ? "" : obj[13]));
					parser.setTableunit(String.valueOf(obj[14] == null ? "" : obj[14]));
					parser.setSrowstr(String.valueOf(obj[15] == null ? "" : obj[15]));
					parser.setErowstr(String.valueOf(obj[16] == null ? "" : obj[16]));
					parser.setParserstauts(String.valueOf(obj[17] == null ? "" : obj[17]));
					
					parserList.add(parser);
			   }
			  
		}catch(Exception e){
			e.printStackTrace();
		}
		
		  return parserList;
	}
	
	/**
	 * 添加解析模板
	 * @param paramMap
	 * @return
	 */
	public Integer insertStructure(Map<String, String> paramMap){
		Integer insertsql=0;
		String sql="insert into PARSER_STRUCTURE(" +
				                "ID," +
				                "TEMPLATEID," +
				                "SHEETNAME," +
				                "TABLENAME," +
				                "DBTABLENAME," +
				                "PARSEINTERFACE," +
				                "PARSESROWNUM," +
				                "PARSEEROWNUM," +
				                "PARSESCELLNUM," +
				                "PARSEECELLNUM," +
				                "PARSEFLAG," +
				                "PARSECONTENT," +
				                "PARSEFILED," +
				                "PARSEBRANDNAME," +
				                "TABLEUNIT,"+
				                "CREATETIME," +
				                "UPDATETIME," +
				                "PARSERSCOPE," +
				                "PARSERESCOPE," +
				                "PARSERSTAUTS)" +
				"values(" +
								" :id," +
								":templateid," +
								":sheetname," +
								":tablename," +
								":dbtablename," +
								":interfaces," +
								":srownum," +
								":erownum," +
								":scellnum," +
								":ecellnum," +
								":parseflag," +
								":pasercontent," +
								":parsefiled," +
								":parsebrandname," +
								":tableunit," +
								"sysdate," +
								"sysdate," +
								":parserscope," +
								":parserecope," +
								":parserstauts)";
		try {
			insertsql=dao.exeSql(sql, paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			insertsql = -1;
		}
	    return insertsql;
	}
	
	/**
	 * 修改解析模板
	 * @param paramMap
	 * @return
	 */
	public Integer update(Map<String, String> paramMap){
		Integer editflag=0;
		try {
			String sql="update " +
					            "PARSER_STRUCTURE set " +
								"SHEETNAME=:sheetname," +
								"TABLENAME=:tablename," +
								"DBTABLENAME=:dbtablename," +
								"PARSEINTERFACE=:interfaces," +
								"PARSESROWNUM=:srownum," +
								"PARSEEROWNUM=:erownum," +
								"PARSESCELLNUM=:scellnum," +
								"PARSEECELLNUM=:ecellnum," +
								"PARSECONTENT=:pasercontent,"+
								"PARSEFILED=:parsefiled,"+
								"PARSEBRANDNAME=:parsebrandname,"+
								"TABLEUNIT=:tableunit,"+
								"CREATETIME=sysdate,"+
								"UPDATETIME=sysdate,"+
								"PARSERSCOPE=:parserscope,"+
								"PARSERESCOPE=:parserecope,"+
								"PARSERSTAUTS=:parserstauts"+
					    " where ID=:id";
			try {
				editflag=dao.exeSql(sql, paramMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				editflag = -1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return editflag;
	}
	
	public Integer updateParseFlag(String id){
		Integer editflag =0;
		String sql= "update PARSER_STRUCTURE set PARSEFLAG='T' where id=:id";
		Map map = new HashMap();
		map.clear();
		map.put("id", id);
		try {
			editflag=dao.exeSql(sql,map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			editflag = -1;
		}
		return editflag;
	}
	// 删除模板信息
	public Integer del(String pkid){
		Integer delflag=0;
		String sql="delete from PARSER_STRUCTURE r where r.ID=:pkid";
		Map map = new HashMap();
		map.clear();
		map.put("pkid", pkid);
		try {
			delflag = dao.exeSql(sql, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delflag;
	}
	
	// 执行解析模板信息入库
	public Integer RunExStructure(Workbook wb,
			ExcelConfig excelParser,
			List<ParserStructure> datalist,
			String id)throws Exception{
		 Integer executenum=0;
		 Session session = mySessionFactory.openSession();
		 Transaction   tran = null;
		 try{
				 tran = session.beginTransaction();
				 tran.begin();
				     dataMap.clear();
					 for(ParserStructure parsrule: datalist)
					 {
						 int parsernum=0;
						 if(parsrule.getParserstauts().equals("T")){
							 try{
								excelParser.setSheetName(parsrule.getSheetname());
								 /** 循环Excel的行 */
								//设置解析开始行号
								excelParser.setSrownum(Integer.parseInt(RegexValidate.StrNotNull(parsrule.getSrownum())?parsrule.getSrownum():"0"));
								//设置解析结束行号
								excelParser.setErownum(Integer.parseInt(RegexValidate.StrNotNull(parsrule.getErownum())?parsrule.getErownum():"0"));
								/** 循环Excel的列 */
								excelParser.setScellnum(Integer.parseInt(RegexValidate.StrNotNull(parsrule.getScellnum())?parsrule.getScellnum():"0"));
								excelParser.setEcellnum(Integer.parseInt(RegexValidate.StrNotNull(parsrule.getEcellnum())?parsrule.getEcellnum():"0"));
								/** 循环Excel的列 */
								
								excelParser.setRowstr(RegexValidate.StrNotNull(parsrule.getSrowstr())?parsrule.getSrowstr():"");
								excelParser.setCellstr(RegexValidate.StrNotNull(parsrule.getErowstr())?parsrule.getErowstr():"");
					
								// 开始解析
								Map<String, String> datamap =  excelParser.tableBreakUp(wb);
								if(datamap != null && datamap.size()>0){
									ExcelToBean  tobean = new ExcelToBean();
								    StringBuffer sub = tobean.getExcelToBean(parsrule, datamap);
									 if(sub != null && RegexValidate.StrNotNull(sub.toString())){
									    	// 转换成数组解析
	//									    if(parsrule.getRowtabname().equals("3.3 PS部分地区市场价格汇总")){
										    	String[] paramids = sub.toString().split("[|]");
										    	for(int i=0;i<paramids.length;i++){
										    		String[] values = paramids[i].split("∴");
										    		insertArrayDB(id,session,values,parsrule);
										    		executenum++;
										    		parsernum++;
										    	}
									}else{
										haier.info("解析 "+parsrule.getSheetname()+" 中 "+parsrule.getRowtabname()+" 模板结构失败 ！异常信息:"+sub);
									}
								}
	//							}
							 }catch (Exception e) {
									// TODO: handle exception
								    tran.rollback();
								    haier.info("解析 "+parsrule.getSheetname()+" 中 "+parsrule.getRowtabname()+" 模板结构失败 ！异常信息:"+e.getMessage());
									throw e;
							 }
							 haier.info("解析 "+parsrule.getSheetname()+" 中 "+parsrule.getRowtabname()+" 模板结构成功，插入 "+parsrule.getDbtable()+" 表中数据为:"+parsernum);
						 }
						  // 记录插入数据的表格
						 dataMap.put(parsrule.getDbtable(), parsernum);
			}
			tran.commit();
		  }catch (Exception e) {
				// TODO: handle exception
			    tran.rollback();
			    haier.info("解析数据库异常！"+e.getMessage());
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
	  return executenum;
	}
	// 执行解析模板系想你入库
	private void insertArrayDB(String fileid,
			Session session,
			String[] paramids,
			ParserStructure parser) throws Exception{
		
		StringBuffer filedsql = new StringBuffer();
		String tablefiled = parser.getParsefiled();
		// 添加字段
		String insfiled = tablefiled.replace(",", ", ");
		// 添加字段参数名称
		String paramfiled = insfiled.replace(" ", ":");
		String valuefiled = paramfiled.replaceFirst("ID", ":id");
		Map paramMap = new HashMap();
	    try{
				//以为为固定字段参数值
			    String[] filedArray = insfiled.split(",");
			    if(null != filedArray && filedArray.length>0){
			    	paramMap.clear();
			    	paramMap.put("id",WDWUtil.getSeqNextval());
			    	paramMap.put("excel_id",fileid);
			    	paramMap.put("hr_structreids",parser.getStructreid());
			  	    paramMap.put("hr_sheetname",parser.getSheetname());
			  	    paramMap.put("hr_subittitle",parser.getRowtabname());
			  	    paramMap.put("hr_sheetpdname",parser.getParsebrandname());
			  	    paramMap.put("hr_tableunit",parser.getTableunit());
		  	    for (int i=0;i<paramids.length;i++) {
//		  	    	System.out.println(filedArray[6+i].toLowerCase());
		  		     paramMap.put(filedArray[7+i].toLowerCase().trim(),paramids[i]);
		  	    }
			  	filedsql.append("insert into "+parser.getDbtable()+"("+insfiled+")");
				filedsql.append("values ("+valuefiled.toLowerCase()+")");
//				System.out.println(filedsql.toString());
				// 执行sql 语句
				dao.exeSql(session,filedsql.toString(), paramMap);
	    }
	  }catch (Exception e) {
		  // TODO: handle exception
		  e.fillInStackTrace();
		  throw e;
	  }
	}
	
	/**
	 * 解析数据信息入库
	 * @param paramids
	 * @param parser
	 * @throws Exception
	 */
	
	private void insertArrayDB(String[] paramids,
			ParserStructure parser) throws Exception{
	    Session session = mySessionFactory.openSession();
	    Transaction tran = null ;
		StringBuffer filedsql = new StringBuffer();
		String tablefiled = parser.getParsefiled();
		// 添加字段
		String insfiled = tablefiled.replace(",", ", ");
		// 添加字段参数名称
		String paramfiled = insfiled.replace(" ", ":");
		String valuefiled = paramfiled.replaceFirst("ID", ":id");
		Map paramMap = new HashMap();
	    try{
	    	 tran = session.beginTransaction();
			 tran.begin();
				//以为为固定字段参数值
			    String[] filedArray = insfiled.split(",");
			    if(null != filedArray && filedArray.length>0){
			    	paramMap.clear();
			    	paramMap.put("id",WDWUtil.getSeqNextval());
			    	paramMap.put("hr_structreids",parser.getStructreid());
			  	    paramMap.put("hr_sheetname",parser.getSheetname());
			  	    paramMap.put("hr_subittitle",parser.getRowtabname());
			  	    paramMap.put("hr_sheetpdname",parser.getParsebrandname());
			  	    paramMap.put("hr_tableunit",parser.getTableunit());
		  	    for (int i=0;i<paramids.length;i++) {
//		  	    	System.out.println(filedArray[6+i].toLowerCase());
		  		     paramMap.put(filedArray[6+i].toLowerCase().trim(),paramids[i]);
		  	    }
			  	filedsql.append("insert into "+parser.getDbtable()+"("+insfiled+")");
				filedsql.append("values ("+valuefiled.toLowerCase()+")");
				// 执行sql 语句
				dao.exeSql(session,filedsql.toString(), paramMap);
				tran.commit();
	    }
	  }catch (Exception e) {
		  // TODO: handle exception
		  e.fillInStackTrace();
//		  tran.rollback();
		  haier.info("解析 "+parser.getSheetname()+"中的"+parser.getRowtabname()+"异常"+e.getMessage()+"！");
	  }
	}
	/**
	 * 
	 * @param paramids 字段名称
	 * @param parser 解析结构对象
	 */
	// 设置固定字段值
	
	
	public void insertObj(String[] paramids,ParserStructure parser){
		
		 Session session = mySessionFactory.openSession();
		 Transaction tran = null ;
		 StringBuffer filedsql =null;
		 String rows =null;
		 String values =null;
		 StringBuffer filedStr = new StringBuffer();
		 StringBuffer filedparam  =null;
	
		 Map paramMap = new HashMap();
		 Map lastparaMap = new HashMap();
		 paramMap.clear();
		 paramMap.put("tabname",parser.getDbtable());
		 try {
			 tran = session.beginTransaction();
			 tran.begin();
			 
			 rows = parser.getParsefiled();
			 //需要存储数据列字段
			 filedStr.append(rows);
			 String[] filedArray = rows.split("[,]");
			 // 查询该表字段
//			 List<String> list = (List<String>)dao.selectSqlAuto(filedsql, paramMap);
			 filedparam = new StringBuffer();
			 if(null != filedArray && filedArray.length>0){
				 paramMap.clear();
				 
				 // 结构ID
				 paramMap.put("id",parser.getId());
				 paramMap.put("hr_sheetname",parser.getSheetname());
				 paramMap.put("hr_subittitle",parser.getRowtabname());
				 paramMap.put("hr_tableunit",parser.getTableunit());
				 paramMap.put("hr_productname",parser.getParsebrandname());
				 
				 //存储参数字段
				 values =rows.replaceAll(",", ":");
				 filedparam.append(values.toLowerCase());
				 int i=0;
				for (String filed :filedArray) {
					if(filed.toString().toLowerCase().equals("id")){
						// 结构ID
						paramMap.put("id",parser.getId());
						i=0;
						continue;
					}if(filed.toString().toLowerCase().equals("hr_sheetname")){
						paramMap.put("hr_sheetname",parser.getSheetname());
						i=0;
						continue;
					}
				    if(filed.toString().toLowerCase().equals("hr_subittitle")){
						paramMap.put("hr_subittitle",parser.getRowtabname());
						i=0;
						continue;
					}else{
//						System.out.println(paramids.length);
						if(i<paramids.length){
							  String filedvalue =String.valueOf(paramids[i] == null ? "" : paramids[i]);
							  if(!RegexValidate.StrNotNull(filedvalue)){
								  filedvalue=paramrowMap.get(filed.toString().toLowerCase()).toString();
							  }
//							  System.out.println(i+"--- " + filed.toString().toLowerCase() +" ----- "+String.valueOf(paramids[i] == null ? "" : paramids[i]));
							  paramMap.put(filed.toString().toLowerCase(), filedvalue);
							  paramrowMap.put(filed.toString().toLowerCase(), filedvalue);
						}
					}
					i++;
				}
				filedsql = new StringBuffer();
				filedsql.append("insert into "+parser.getDbtable()+"("+filedStr.toString()+")");
				filedsql.append("values ("+filedparam.toString()+")");
				// 执行sql 语句
				dao.exeSql(session,filedsql.toString(), paramMap);
				tran.commit();
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			paramrowMap.clear();
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
	
	/**
	 * 获取所有的模板信息
	 * @param sql
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getListMap(){
		String sql = "select FILE_FNAME,FILE_PKID from parser_file_list";
		List<Map<String,Object>>  filelist= null;
		try {
			filelist = dao.queryMapList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filelist;
	}
	
	public List<Map<String,Object>> getParserMap(String templatid,String fileId){
 		String sql = "select f.file_pkid,f.file_type,f.template_id,f.file_path,t.template_type,t.template_name," +
				"f.file_fname,t.template_filetype from parser_file_list f ,parser_template t " +
				"where f.template_id = t.template_id " +
				"and t.status='T' and f.parserstatus='F' ";
				if(!RegexValidate.StrisNull(fileId)){
					sql += "and f.file_pkid='"+fileId+"'";
				}else if(!RegexValidate.StrisNull(templatid)){
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
	/**
	 * 获取模板ID信息
	 * @param sql
	 * @param map
	 * @return
	 */
	public String[] getByID(String fileName){
		String[] structures = new String[2];
		String sql = "select s.excel_id,s.file_pkid from parser_structure_sheet_info s where s.excel_name = '"+fileName+"' and EXCEL_STAUTS='F'";
		try {
			List list = dao.selectSqlAuto(sql);
			if(null != list && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[])list.get(i);
					      structures[0]= String.valueOf(obj[0] == null ? "" : obj[0]);
					      structures[1]= String.valueOf(obj[1] == null ? "" : obj[1]);
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return structures;
	}
	
	
	/**
	 * 获取模板ID信息
	 * @param sql
	 * @param map
	 * @return
	 */
	public String[] getByFileName(String fileId){
		String[] structures = new String[2];
		String sql = "select TEMPLATE_ID,FILE_FNAME,FILE_TYPE from parser_file_list s where s.FILE_PKID = '"+fileId+"' and s.PARSERSTATUS='F'";
		try {
			List list = dao.selectSqlAuto(sql);
			if(null != list && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[])list.get(i);
						structures[0] = String.valueOf(obj[0] == null ? "" : obj[0]);
						structures[1]= String.valueOf(obj[1] == null ? "" : obj[1]+""+obj[2] == null ? "" : obj[2]);
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return structures;
	}
	/**
	 * 获取所有的模板信息
	 * @param sql
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getParserStructerMap(Map paramMap){
		String sql = "select EXCEL_NAME,EXCEL_ID,EXCEL_STAUTS from PARSER_STRUCTURE_SHEET_INFO p where p.EXCEL_NAME=:name";
		List<Map<String,Object>>  filelist= null;
		try {
			filelist = dao.queryMapList(sql,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filelist;
	}

	
	
	/**
	 * 获取模板结构条数
	 * @param sql
	 * @param map
	 * @return
	 */
	public Integer getByCount(String fileName){
		String sql = "select count (*) from  parser_file_list l,parser_structure s where l.file_pkid =s.fileid  and l.file_fname = '"+fileName+"'";
		Integer  countnum= 0;
		try {
			List list = dao.selectSqlAuto(sql);
			if(null != list && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
						countnum = Integer.parseInt(list.get(i).toString());
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countnum;
	}
	
	/**
	 * 添加文件与解析模板中间表数据
	 * @return
	 */
	public Integer setParserInfo(Map paramMap){
		String sql = "insert into PARSER_STRUCTURE_SHEET_INFO values (:id,:name,sysdate,:filepath,:status)";
		Integer  insflag= 0;
		try {
			insflag = dao.exeSql(sql, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insflag;
	}
	
	/**
	 * 添加文件与解析模板中间表数据
	 * @return
	 */
	public Integer modifyParserInfo(Map paramMap){
//		String sql = "update PARSER_STRUCTURE_SHEET_INFO s set s.EXCEL_STAUTS='T',s.FILE_PKID=:filepkid,s.PARSERNUM=:parsernum where s.FILE_PKID=:filepkid";
		String sql = "update parser_file_list s set s.parserstatus='T',s.parsernum=:parsernum where s.file_pkid=:filepkid";
		
		Integer  modfiyflag= 0;
		try {
			modfiyflag = dao.exeSql(sql, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modfiyflag;
	}
	
	/**
	 * 添加文件与解析模板中间表数据
	 * @return
	 */
	public Integer modifyParserInfo(String fileName){
		String sql = "update PARSER_STRUCTURE_SHEET_INFO s set s.EXCEL_STAUTS='T',set where s.excel_name='"+fileName+"'";
//		String sql = "update parser_file_list s set s.parserstatus='T',s.parsernum=:parsernum where s.file_pkid=:filepkid";
		
		Integer  modfiyflag= 0;
		try {
			modfiyflag = dao.exeSql(sql, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modfiyflag;
	}
	
	public String getColumnName(SessionFactory factory,Class clazz, String propertyName) {
		   List<String> resultList = new ArrayList<String>();
		 
		   AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory
		     .getClassMetadata(clazz);
		 
		   System.out.println(classMetadata.getTableName());
		   boolean isCollection = classMetadata.getClassMetadata()
		     .getPropertyType(propertyName).isCollectionType();
		   if (!isCollection) {
		    for (String columnName : classMetadata
		      .getPropertyColumnNames(propertyName)) {
		     resultList.add(columnName);
		    }
		   }
		   if (resultList != null && resultList.size() >= 1) {
		    return resultList.get(0);
		   }
		   return null;
	}
	
	public   static void main(String[] args) throws IOException{
		
		haier.info("ssssssssssssssss");
//		SessionFactory	mySessionFactory=(SessionFactory)SpringContextUtils.getBean("sessionFactory");
//		 System.out.println(new ParamService().getColumnName(mySessionFactory, ResParams.class, "tendValInitRule"));
//		System.exit(0);   
	} 
	
}
