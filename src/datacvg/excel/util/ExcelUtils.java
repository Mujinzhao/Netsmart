package datacvg.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import core.util.ContentUtil;
import core.util.RegexValidate;
import core.util.WDWUtil;
import datacvg.excel.entity.ExcelRule;

/**
 * Excel 操作工具类
 * @author sxy
 *
 */
public class ExcelUtils{

	 private static final Logger excel = Logger.getLogger(ExcelUtils.class);
	 private Workbook wb;//excel操作类
	 private Sheet sheet;//sheet页面
	 
	 public   String sheetName;//sheet页名称
	 
	 /**开始、结束解析的行*/
	 private  int srownum = 1;
	 private  int erownum = 0;
	 /**开始、解析解析列*/
	 private  int scellnum=0;
	 private  int ecellnum=0;
	 
	 /***开始、结束解析的行、列数组 ***/
	 public   String srownums;
	 public   String erownums;
	 
	 public   String scellnums;
	 public   String ecellnums;
	 public   String errorInfo;
	 /***开始、结束解析的行、列数组 ***/
	 public   String celltorows;
	 public List<HashMap<String, String>> excelResultList;// 最终的解析结果
     public String tableName;// 最终存储的数据库名称
	 public String getSheetName() {
			return sheetName;
		}

		public void setSheetName(String sheetName) {
			this.sheetName = sheetName;
		}

		public int getSrownum() {
			return srownum;
		}

		public void setSrownum(int srownum) {
			this.srownum = srownum;
		}

		public int getErownum() {
			return erownum;
		}

		public void setErownum(int erownum) {
			this.erownum = erownum;
		}

		public int getScellnum() {
			return scellnum;
		}

		public void setScellnum(int scellnum) {
			this.scellnum = scellnum;
		}

		public int getEcellnum() {
			return ecellnum;
		}

		public void setEcellnum(int ecellnum) {
			this.ecellnum = ecellnum;
		}

		public String getSrownums() {
			return srownums;
		}

		public void setSrownums(String srownums) {
			this.srownums = srownums;
		}

		public String getErownums() {
			return erownums;
		}

		public void setErownums(String erownums) {
			this.erownums = erownums;
		}

		public String getScellnums() {
			return scellnums;
		}

		public void setScellnums(String scellnums) {
			this.scellnums = scellnums;
		}

		public String getEcellnums() {
			return ecellnums;
		}

		public void setEcellnums(String ecellnums) {
			this.ecellnums = ecellnums;
		}

		public String getCelltorows() {
			return celltorows;
		}

		public void setCelltorows(String celltorows) {
			this.celltorows = celltorows;
		}
	   /**
		 * 
		 * @描述：验证excel文件
		 * 
		 * @参数：@param filePath　文件完整路径
		 * 
		 * @参数：@return
		 * 
		 * @返回值：boolean
		 */

		public boolean validateExcel(String filePath)
		{

			/** 检查文件名是否为空或者是否是Excel格式的文件 */

			if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath)))
			{
				errorInfo = "文件名不是excel格式";
				excel.error(errorInfo);
				return false;
			}
			/** 检查文件是否存在 */

			File file = new File(filePath);
			if (file == null || !file.exists())
			{
				errorInfo = "文件不存在";
				excel.error(errorInfo);
				return false;
			}
			return true;

		}

	 /**
	 * 
	 * @描述：根据文件名读取excel文件
	 * 
	 * @参数：@param filePath 文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：List
	 */
	public Workbook readExcel(String filePath) throws Exception{
		InputStream inputStream = null;
		try
		{
			/** 验证文件是否合法 */
			if (!validateExcel(filePath))
			{
//				System.out.println(errorInfo);
				return null;
			}
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (!WDWUtil.isExcel2003(filePath))
			{
				isExcel2003 = false;
			}
			/** 调用本类提供的根据流读取的方法 */

			File file = new File(filePath);
			inputStream = new FileInputStream(file);
			/** 根据版本选择创建Workbook的方式 */
			if (isExcel2003)
			{
				wb = new HSSFWorkbook(inputStream);
			}
			else
			{
				wb = new XSSFWorkbook(inputStream);
			}
			inputStream.close();
		}catch (Exception ex){
			ex.printStackTrace();
			excel.error("创建Workbook 对象异常" + ex.getMessage() +"请检验是否上传正确的模板文件");
			errorInfo = "创建Workbook 对象异常" + ex.getMessage() +"请检验是否上传正确的模板文件";
			ExcelStaticVar.parserrorList.add(errorInfo);
			throw ex;
		}
		finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					inputStream = null;
					e.printStackTrace();
				}
			}
		}
		return wb;
	}
    /**
     * 根据wb 获取所有SheetName
     * @param wb
     * @return SheetName集合
     */
    public List<String> GetSheetNames(Workbook wb)
    {
    	List<String> sheetNames = new ArrayList<String>();
    	if(wb != null){
    		for(int i=0;i<wb.getNumberOfSheets();i++){
        		String sheetName = wb.getSheetName(i);
                if (RegexValidate.StrNotNull(sheetName))
                {
                    sheetNames.add(sheetName);
                }
    		}
    	}
        return sheetNames;
    }
    /**
     * 解析EXCEL模板规则
     * @param wb excel 操作对象
     * @param relationMap  配置下标字段集合
     * @return 解析数据
     */
    public String readExcelToCentent(Workbook wb) throws Exception{

    	    StringBuffer exceldb = new StringBuffer();
    		try{
    			 sheet =  wb.getSheet(sheetName);
    		}catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
        		excel.error("Workbook 获取 Sheet信息有误!"+e.getMessage());
        		errorInfo = "Workbook 获取 Sheet信息有误!"+e.getMessage();
        		ExcelStaticVar.parserrorList.add(errorInfo);
        		return exceldb.toString();
			}
	    	try{
	    		//Sheet 信息不为空
	    		if(sheet != null){
	    			if(erownum == 0 ){
		    			 // 获取当前sheet 页面中的最后一行数
		    			 erownum=sheet.getLastRowNum();
					 }
		    		 /** 循环Excel的行 */
		    		 excel.info("从第"+srownum+"行开始到"+erownum+"行结束");
		             for (int rowNum = srownum; rowNum <= erownum; rowNum++) {
		            	// 循环得到Excel中Row对象
		 				Row row = sheet.getRow(rowNum);
		 				if (row == null){
		 					continue;
		 				}
		 				String value = "\n";
		 				String spestr = "|";
		 				String savevalue="\n";
		// 				System.out.println("cellnum " +row.getLastCellNum());
		 				if(ecellnum == 0 ){
		 					// 获取excel 中列数
		 					ecellnum=row.getLastCellNum();
		 				}
		 				/** 循环Excel的列 */
		 				for (int cellNum = scellnum; cellNum <=ecellnum; cellNum++) {
		// 					excel.info("开始解析第"+rowNum+"行第"+cellNum+"列");
		 					Cell cell =  row.getCell(cellNum);
		 					if (cell == null) {
		 						savevalue+=" "+"∴";
		 						continue;
		 					}
		 					String tvalue = getStringCellValue(cell);
		 					if(RegexValidate.StrNotNull(tvalue.trim())){
		 						value +=tvalue.trim()+" ";
		 					    savevalue +=tvalue.trim()+"∴";
		 					}
		 					else{
		 						savevalue+="∴";
		 					}
		 				}
						if(RegexValidate.StrNotNull(value)){
		 					 exceldb.append(savevalue.trim()+spestr);
		 				 }
		             }
		            
	    		}else{
	    			excel.error("上传的模板文件未找到"+sheetName+" Sheet名称");
	    			errorInfo = "上传的模板文件未找到"+sheetName+" Sheet名称";
	    			ExcelStaticVar.parserrorList.add(errorInfo);
	    			return exceldb.toString();
	    		}
	    	}catch (Exception e) {
				// TODO: handle exception
	    		e.printStackTrace();
	    		errorInfo = "解析excel数据异常 "+e.getMessage();
	    		ExcelStaticVar.parserrorList.add(errorInfo);
	    		excel.error(errorInfo);
	    		throw e;
		    }
	    	return exceldb.toString();
    }
    
    /**
     * 拆分sheet页面中table表格解析
     * @param wb 解析excel 接口类
     * @return
     */
    public Map<String,Object> getExcelResultList(Workbook wb) throws Exception{
    	Map<String, Object>resultMap = new HashMap<String,Object>();
    	String contentstr = "";
    	if(RegexValidate.StrNotNull(srownums)
    			&& RegexValidate.StrNotNull(scellnums)
    			&& RegexValidate.StrNotNull(ecellnums)){
    		String[] srownumarray = srownums.split("[,]");// 开始行集合
    		String[] erownumarray = erownums.split("[,]");// 开始行集合
    		String[] scellnumarray = scellnums.split("[,]");//开始列集合
    		String[] ecellnumarray = ecellnums.split("[,]"); //结束列集合
    		String[] celltorowarray = celltorows.split("[,]"); //是否行转列标识
    		try{
	    		for(int i=0;i<srownumarray.length;i++){
	    		
	    			//开始行
	    			srownum  = Integer.parseInt(srownumarray[i].toString());
	    			//结束列
	    			erownum  = Integer.parseInt(erownumarray[i].toString());
	    			//开始列
	    			scellnum = Integer.parseInt(scellnumarray[i].toString());
	    			//结束列
	    			ecellnum = Integer.parseInt(ecellnumarray[i].toString());
	    			
	    			celltorows  = celltorowarray[i].toString();
	    			//获取行数据
	    		    contentstr = readExcelToCentent(wb);
	    			//行数据转换列
	    			if(RegexValidate.StrNotNull(celltorows) && celltorows.equals("true")){ 
	    				contentstr = new ContentUtil().getExcelToBuff(contentstr);
	    			}
	    			resultMap.put(celltorows+"_"+i, Arrays.asList(contentstr.split("[|]")));
	    		}
    		}catch (Exception e) {
				// TODO: handle exception
    			throw e;
			}
    	}
    	return resultMap;
    }
   
    /**
     * 拆分sheet页面中table表格解析
     * @param wb解析excel 接口类
     * @param excelrule excel解析规则
     * @return
     */
    public Map<String,Object> getExcelResultList(Workbook wb,ExcelRule excelrule){
    	Map<String, Object>resultMap = new HashMap<String,Object>();
    	String contentstr = "";
    	
    	if(RegexValidate.StrNotNull(excelrule.getParsesrownum()) && RegexValidate.StrNotNull(excelrule.getParsescellnum())
    			&& RegexValidate.StrNotNull(excelrule.getParseecellnum())){
    		
    		String[] srownumarray = srownums.split("[,]");// 开始行集合
    		String[] erownumarray = erownums.split("[,]");// 开始行集合
    		String[] scellnumarray = scellnums.split("[,]");//开始列集合
    		String[] ecellnumarray = ecellnums.split("[,]"); //结束列集合
    		String[] celltorowarray = celltorows.split("[,]"); //是否行转列标识
    		try{
	    		for(int i=0;i<srownumarray.length;i++){
	    		
	    			//开始行
	    			srownum  = Integer.parseInt(srownumarray[i].toString());
	    			//结束列
	    			erownum  = Integer.parseInt(erownumarray[i].toString());
	    			//开始列
	    			scellnum = Integer.parseInt(scellnumarray[i].toString());
	    			//结束列
	    			ecellnum = Integer.parseInt(ecellnumarray[i].toString());
	    			
	    			celltorows  = celltorowarray[i].toString();
	    			//获取行数据
	    		    contentstr = readExcelToCentent(wb);
	    			//行数据转换列
	    			if(RegexValidate.StrNotNull(celltorows)
	    					&& celltorows.equals("true")){ 
	    				contentstr = new ContentUtil().getExcelToBuff(contentstr);
	    			}
	    			resultMap.put(celltorows+"_"+i, Arrays.asList(contentstr.split("[|]")));
	    		}
    		}catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	return resultMap;
    }

    /**
     * 处理行数据转列方法
     * @param resultMap 数据结果集
     * @return 解析结果数据信息
     */
    public String getExcelToBuffer(Map<String, Object>resultMap){
		StringBuffer contentbuff = new StringBuffer();
		List<String> resultALL = new ArrayList<String>();
		List<String> resultList =null;
		List<String> resultList2 =null;
		if(resultMap != null && resultMap.size()>0){
			resultList = new ArrayList<String>();
			resultList2 = new ArrayList<String>();
			for(Map.Entry<String,Object> entry:resultMap.entrySet()){
					String key = entry.getKey();
					// 表示列转行处理
					if(key.contains("true")){
					    List<String> tmpresultList = (List<String>)entry.getValue();
						if(tmpresultList!= null && tmpresultList.size()>0){
							for(String str :tmpresultList){
								if(RegexValidate.StrNotNull(str)){
									resultList2.add(str);
								}
							}
						}
					}
					else if(key.contains("false")){
						resultList.addAll((List<String>) entry.getValue());
					}
					if(resultList!= null && resultList2!= null  && resultList.size()>0 && resultList2.size()>0){
						//列转行解析数据合并
						contentbuff = parserExceData(resultList,resultList2,contentbuff);
					}
			}
			if(resultList != null && resultList.size()>0 
					&& resultList2!= null && resultList2.size()>0){
			}else{
				resultALL.addAll(resultList2);
				resultALL.addAll(resultList);
				contentbuff = parserExceData(resultALL,contentbuff);
			}
			
		}
		return contentbuff.toString();
	}
    /**
     * 处理行转列数据
     * @param resultList 非行转列数据
     * @param resultList2 需要行转列数据
     * @param contentbuff 
     * @return
     */
    public StringBuffer parserExceData(List<String> resultList,List<String> resultList2,StringBuffer contentbuff){
    	String strtemp="";
    	int k=1;
		// 迭代处理拼装解析数据
		for(String str :resultList){
			int n=1;
			strtemp = str;
			for(int i =0;i<resultList2.size();i++){
				
				String[] arraystr1 = resultList2.get(i).split("[∴]");
				contentbuff.append(arraystr1[0]+"∴");
				for(int j =n;i<arraystr1.length;j++){
				    contentbuff.append(arraystr1[k]+"∴");	
					n++;
				    break;
				}
				contentbuff.append(strtemp);
				contentbuff.append("|\n");
			}
			k++;
		}
		return contentbuff;
    }
    
    public StringBuffer parserExceData(List<String> resultList,StringBuffer contentbuff ){
		for(String str :resultList){
			contentbuff.append(str);
			contentbuff.append("|\n");
		}
		return contentbuff;
    }
    /**
     *  读取行数据中
     * @param cell
     * @return
     */
    private static String getStringCellValue(Cell cell) { 
   	 
       NumberFormat nf = NumberFormat.getPercentInstance();
       nf.setMaximumFractionDigits(2);//这个2表示保存结果到小数点后几位
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
       String strCell = "";
       // 判断当前Cell的Type 
       switch (cell.getCellType()) { 
	       // 如果当前Cell的Type为STRIN 
	       case Cell.CELL_TYPE_STRING: 
	          // 取得当前的Cell字符串 
	   	      strCell = cell.getRichStringCellValue().getString(); 
              break; 
	       // 如果当前Cell的Type为NUMERIC 
	       case Cell.CELL_TYPE_NUMERIC: 
	    	   //判断是否为日期类型
	      		if(HSSFDateUtil.isCellDateFormatted(cell)){
	      			     cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            		 strCell =  sdf.format(cell.getDateCellValue());//日期型
	      		}else{
	      			     BigDecimal big = new BigDecimal(cell.getNumericCellValue());
	      			   
	      			     strCell=big.toString();
	      			     //正整数类型
	      			     if(RegexValidate.isInteger(strCell)){
//	      			    	 System.out.println(big.toString() + " 格式 ："+strCell);
	      			    	 break;
	      			     }
	      			     // double类型
	      			     if(RegexValidate.isDouble(strCell)){
	                    	 strCell = String.valueOf(cell.getNumericCellValue());
	                     }
	      		}
		        break; 
	       case Cell.CELL_TYPE_BOOLEAN:
	           strCell = String.valueOf(cell.getBooleanCellValue());
	           break;
	       case Cell.CELL_TYPE_BLANK:
	           strCell = "";
	           break;
	       case Cell.CELL_TYPE_ERROR:  
	    	   strCell = "";
	    	 break; 
	       // 公式
	       case Cell.CELL_TYPE_FORMULA:
	    	   try{
	           		//判断是否为日期类型
	           		if(HSSFDateUtil.isCellDateFormatted(cell)){
	           			 cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                     strCell =  sdf.format(cell.getDateCellValue());//日期型
	           		}else{
	           		 try{
	            		 cell.setCellType(Cell.CELL_TYPE_STRING);
	                     String temp = cell.getStringCellValue().toString();
	                     // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
	                     if (!temp.equals("0") && RegexValidate.isDouble(temp)) {
	                    	 strCell = nf.format(new Double(temp)).trim();
	                     }else if(temp.equals("0")){
	                    	 strCell = "0";
	                     }else{
	                    	 strCell = temp;
	                     }
	            	 }catch (Exception e1) {
	    				// TODO: handle exception
	            		 strCell = "0";
	    			}
	            	
	           		}
	            }catch (Exception e) {
	   			// TODO: handle exception
	           	//e.printStackTrace();
	           	 try{
	           		 
	           		    cell.setCellType(Cell.CELL_TYPE_STRING);
	                    String temp = cell.getStringCellValue().toString();
	                    // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
	                    if (!temp.equals("0") && RegexValidate.isDouble(temp)) {
	                   	 strCell = nf.format(new Double(temp)).trim();
	                    }else if(temp.equals("0")){
	                   	 strCell = "";
	                    }else{
	                   	 strCell = temp;
	                    }
	           	 }catch (Exception e1) {
	   				// TODO: handle exception
	           		 strCell = "0";
	   			}
	         }
	    	break;
	       // 默认的Cell值 
	       default: 
    	       strCell = " ";
    	       break;
      } 
      return strCell;
     }

    /**
	 * 拼装解析数据
	 * @param contentstr 解析的数据字符串
	 * @return
	 */
	public List<HashMap<String, String>>  getExcelResultList(String contentstr) throws Exception{
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>() ;
		LinkedHashMap<Integer, String>  paramrowMap = new LinkedHashMap<Integer, String>();
		HashMap<String, String> rowDataList = null;
		excelResultList = new ArrayList<HashMap<String, String>>();

		int m=0;
		// 截取解析内容
		String[] contents = contentstr.split("[|]");
			for(int j = 0;j<contents.length;j++){
				rowDataList = new HashMap<String, String>();
				int index =1;
				//某行数据
				String[] content = contents[j].split("∴");
				if(content.length>1){
					for(int i = 0;i<content.length;i++){
						//判断数组非空列数自动补全
					    String rowstr = content[i];
					    if(RegexValidate.StrisNull(rowstr)){
					    	 rowstr = paramrowMap.get(index);
					    }
					    rowDataList.put(index+"",rowstr);
					    //替换数据
						paramrowMap.put(index, rowstr);
				        index++;
			    }
					resultList.add(rowDataList);
			}
		}
//		System.out.println(resultList.size());
		return resultList;
	}
    
    
	/**
	 * 根据解析数据还原最终数据
	 * @param contentstr 解析的数据字符串
	 * @return
	 */
	public List<HashMap<String, String>>  getExcelResultList(String contentstr,HashMap<Object, String> relationMap){
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>() ;
		LinkedHashMap<Integer, String>  paramrowMap = new LinkedHashMap<Integer, String>();
		HashMap<String, String> rowDataList = null;
		excelResultList = new ArrayList<HashMap<String, String>>();

		if(relationMap == null && relationMap.size()<0){
			return resultList;
		}
		int m=0;
		// 截取解析内容
		String[] contents = contentstr.split("[|]");
			for(int j = 0;j<contents.length;j++){
				rowDataList = new HashMap<String, String>();
				int index =1;
				//某行数据
				String[] content = contents[j].split("∴");
				if(content.length>1){
					for(int i = 0;i<content.length;i++){
						 if(RegexValidate.StrNotNull(relationMap.get(index))){
							//判断数组非空列数自动补全
							    String rowstr = content[i];
							    if(RegexValidate.StrisNull(rowstr)){
							    	 rowstr = paramrowMap.get(index);
							    }
							    rowDataList.put(relationMap.get(index),rowstr);
							    //替换数据
								paramrowMap.put(index, rowstr);
						        index++;
						 }
					}
				}
				// 解析字段值，跟非空字段设置值相等
				if (rowDataList.size() == relationMap.size())
				{
					resultList.add(rowDataList);
				}
		}
		return resultList;
	}
    
	public static void main(String[] args) {
		ExcelUtils excel = new ExcelUtils();
		HashMap<Object, String> relationMap = new HashMap<Object, String>();
		
//		relationMap.put(1, "品名");
//		relationMap.put(2, "规格");
//		relationMap.put(3, "材质");
//		relationMap.put(4, "产地厂家");
//		relationMap.put(5, "报价地");
//		relationMap.put(6, "价格");
//		relationMap.put(7, "涨跌");
//		relationMap.put(8, "日期");
//		String filePath = "D:\\EXCEL\\TEPLATE\\中联钢\\中联钢价格走势.xls";
//		excel.setSheetName("sheet");
//		excel.setSrownum(1);//开始行
//		excel.setErownum(0);//结束行
//		excel.setScellnum(0);//开始列
//		excel.setEcellnum(-1);//结束列
//		List<HashMap<String, String>> resultlist =  excel.getExcelResultList(contentstr, relationMap);
		
//		relationMap.put(1, "日期");
//		relationMap.put(2, "价格");
//		relationMap.put(3, "市场");
//		relationMap.put(4, "品名");
//		relationMap.put(5, "材质");
//		String filePath = "D:\\EXCEL\\TEPLATE\\白电行业\\中国白电行业大宗原料采购综合周报（8.18-8.22） - 副本.xlsx";
		excel.sheetName="六.稀土";
		excel.srownums="6,5";//开始行
		excel.erownums="11,11";//结束行
		excel.scellnums="0,5";//开始列
		excel.ecellnums="3,-1";//结束列
		excel.celltorows="false,true";//行转列标识
		
		
//		relationMap.put(1, "品名");
//		relationMap.put(2, "价格");
//		relationMap.put(3, "日期");
		
//		excel.sheetName="四.有色";
//		excel.srownums="7,6";//开始行
//		excel.erownums="11,11";//结束行
//		excel.scellnums="0,1";//开始列
//		excel.ecellnums="0,-1";//结束列
//		excel.celltorows="false,true";//行转列标识
		
		
		String filePath = "E:\\excel\\售前成本中心负责人信息.xls";
//		relationMap.put(1, "品种");
//		relationMap.put(2, "序号");
//		relationMap.put(3, "牌号");
//		relationMap.put(4, "钢种基价");
//		excel.sheetName="宝钢";
//		excel.srownums="16,16,31,30,44,52";//开始行
//		excel.erownums="30,29,60,43,51,61";//结束行
//		excel.scellnums="0,4,0,4,4,4";//开始列
//		excel.ecellnums="3,-1,3,-1,-1,-1";//结束列
//		excel.celltorows="false,false,false,false,false,false";//行转列标识
		Workbook wb  = null;
		try{
		 wb = excel.readExcel(filePath);
		
		Map<String,Object> resultsMap  = excel.getExcelResultList(wb);
		
		
//		String tmp="";
//		for(String str:ExcelStaticVar.parserrorList){
//			tmp += str+ "<br/>";
//		}
//		 System.out.println(tmp);
//			
		if (resultsMap != null) {
			String contentstr = excel.getExcelToBuffer(resultsMap);
			List<HashMap<String, String>> resultlist =  excel.getExcelResultList(contentstr, relationMap);
			System.out.println(resultlist);
//					String parsucc = "";
//			    	String tmpsucc = "";
//			    	int i =0;
//			    	int j =1;
//					if(null != resultlist)
//				    {
//						// 迭代 标签规则值
//				    	for(HashMap<String, String> tagdata :resultlist)
//						{
//				    		// 迭代规则字段信息
//							for(Entry<Integer, String> entry : relationMap.entrySet())
//							{
//								parsucc +=tagdata.get(entry.getValue())+" "; // 迭代出每个规则字段解析的值
//								i++;
//								if(i == (relationMap.size()*j))// 判断 当前解析规则字段列数，进行换行
//								{
//									tmpsucc += parsucc+"\n";
//									parsucc ="";
//									j++;
//								}
//							}
//						}
//				    }
//				    if(RegexValidate.StrNotNull(tmpsucc)) 
//					{
//				    	 if(i <= 3) //当解析字段值 为三列  已三列作为依据换行
//				    	 {
////				    		System.out.println(tmpsucc+"\n");
//				    	 }
//				    	 else
//				    	 {
//				    	    System.out.println(tmpsucc+"\n");
//				    	 }
//					 }
	    }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		relationMap.put(1, "市场");
//		relationMap.put(2, "钢厂");
//		relationMap.put(3, "规格");
////		relationMap.put(4, "材质");
//		relationMap.put(4, "本周均价");
//		relationMap.put(5, "上周均价");
//		relationMap.put(6, "涨跌");
//		String filePath = "D:\\EXCEL\\TEPLATE\\白电行业\\中国白电行业大宗原料采购综合周报（8.18-8.22） - 副本.xlsx";

//		excel.srownums="6,5";//开始行
//		excel.scellnums="0,5";//开始列
//		excel.ecellnums="3,0";//结束列
//		excel.erownum=10;
		
		
//		excel.sheetName="四.有色";
//		excel.srownums="7,6,12";//开始行
//		excel.erownums="11,11,16";//结束行
//		
//		excel.scellnums="0,1,1";//开始列
//		excel.ecellnums="0,-1,-1";//结束列
		
		
//		excel.celltorows="false,true,true";//行转列标识
		
//		Workbook wb = excel.readExcel(filePath);
//		Map<String,  Object> resultsMap  = excel.getExcelResultList(wb);
//		if (resultsMap != null) {
//			System.out.println(resultsMap);
//			StringBuffer contentbuff = excel.getExcelToBuffer(resultsMap);
//			System.out.println(contentbuff);
//			for(Map.Entry<Integer,String> sm : resultsMap.entrySet()){
//				    Integer index = sm.getKey();
//				    System.out.println(index);
//				    String parsertbean = sm.getValue();
//				    // 获取内容值数组
//					String[]  contentstr = getExcelToArray(parsertbean);
//					// 表格拆分解析
//					String content = resultsMap.get("0").toString();
//					String datestr = resultsMap.get("1").toString();
//					// 获取需要行转
//					String[]  dateStr    = getExcelToString(datestr);
//					}
//				}
//			}
//		}
//	}
//		excel.getExcelResultList(contentstr,relationMap);
	}
   
}
