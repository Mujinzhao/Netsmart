package haier.dataspider.excelparser;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import core.util.RegexValidate;
import core.util.WDWUtil;


public class ExcelConfig
{
	
    private Workbook wb;
    private Sheet sheet;

	/** 总行数 */

	private static int totalRows = 0;

	/** 总列数 */

	private static int totalCells = 0;
	
	private  String sheetName;
	
	public  String getSheetName() {
		return sheetName;
	}

	public  void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}


	/**开始、结束解析的行*/
	private  int srownum = 1;
	private  int erownum = 0;
	//需要过滤的行及列
	private  int glrownum =0;
	private  int glcellnum=0;
	
	public String getRowstr() {
		return rowstr;
	}

	public int getGlrownum() {
		return glrownum;
	}

	public void setGlrownum(int glrownum) {
		this.glrownum = glrownum;
	}

	public int getGlcellnum() {
		return glcellnum;
	}

	public void setGlcellnum(int glcellnum) {
		this.glcellnum = glcellnum;
	}

	public void setRowstr(String rowstr) {
		this.rowstr = rowstr;
	}

	public String getCellstr() {
		return cellstr;
	}

	public void setCellstr(String cellstr) {
		this.cellstr = cellstr;
	}


	private String rowstr;
	private String cellstr;
	
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


	/**开始、解析解析列*/
	private  int scellnum=0;
	private  int ecellnum=0;
	
	
	public StringBuffer parserstr;
	public StringBuffer getParserstr() {
		return parserstr;
	}

	public void setParserstr(StringBuffer parserstr) {
		this.parserstr = parserstr;
	}


	/** 错误信息 */

	private String errorInfo;
	
	/** 构造方法 */

	public ExcelConfig()
	{

	}

	/**
	 * 
	 * @描述：得到总行数
	 * 
	 * @参数：@return
	 * 
	 * @返回值：int
	 */

	public int getTotalRows()
	{

		return totalRows;

	}

	/**
	 * 
	 * @描述：得到总列数
	 * 
	 * @参数：@return
	 * 
	 * @返回值：int
	 */

	public int getTotalCells()
	{

		return totalCells;

	}

	/**
	 * 
	 * @描述：得到错误信息
	 * 
	 * @参数：@return
	 * 
	 * @返回值：String
	 */

	public String getErrorInfo()
	{

		return errorInfo;

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
			return false;
		}
		/** 检查文件是否存在 */

		File file = new File(filePath);
		if (file == null || !file.exists())
		{
			errorInfo = "文件不存在";
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

	//public LinkedHashMap<Integer, String> read(String filePath)
	public StringBuffer read(String filePath)
	{
		StringBuffer subdata = new StringBuffer();
//		LinkedHashMap<Integer, String> dataLst = new LinkedHashMap<Integer, String>();

		InputStream is = null;

		try
		{

			/** 验证文件是否合法 */

			if (!validateExcel(filePath))
			{

				System.out.println(errorInfo);

				return null;

			}

			/** 判断文件的类型，是2003还是2007 */

			boolean isExcel2003 = true;

			if (WDWUtil.isExcel2007(filePath))
			{

				isExcel2003 = false;

			}

			/** 调用本类提供的根据流读取的方法 */

			File file = new File(filePath);

			is = new FileInputStream(file);

			subdata = readStream(is, isExcel2003);
			is.close();

		}
		catch (Exception ex)
		{

			ex.printStackTrace();

		}
		finally
		{

			if (is != null)
			{

				try
				{

					is.close();

				}
				catch (IOException e)
				{

					is = null;

					e.printStackTrace();

				}

			}

		}

		/** 返回最后读取的结果 */

		return subdata;

	}

	/**
	 * 
	 * @描述：根据流读取Excel文件
	 * 
	 * @参数：@param inputStream
	 * 
	 * @参数：@param isExcel2003
	 * 
	 * @参数：@return
	 * 
	 * @返回值：List
	 */

//	private LinkedHashMap<Integer, String> readStream(InputStream inputStream, boolean isExcel2003)
	private StringBuffer readStream(InputStream inputStream, boolean isExcel2003)
	
	{

//		LinkedHashMap<Integer, String> map = null;
		StringBuffer subdata = null;
		try
		{

			/** 根据版本选择创建Workbook的方式 */

			if (isExcel2003)
			{
				wb = new HSSFWorkbook(inputStream);
			}
			else
			{
				wb = new XSSFWorkbook(inputStream);
			}
			subdata = readExcelContent(wb);

		}
		catch (IOException e)
		{

			e.printStackTrace();

		}

		return subdata;

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
	
	public Workbook readExcel(String filePath){
		InputStream inputStream = null;
		try
		{
			/** 验证文件是否合法 */
			if (!validateExcel(filePath))
			{
				System.out.println(errorInfo);
				return null;
			}
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(filePath))
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

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
//		readExcelContent(wb);
		

	}
	
	/**
     * 读取Excel数据内容
     * 
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
//    private LinkedHashMap<Integer, String> readExcelContent(Workbook wb) {
     private StringBuffer readExcelContent(Workbook wb) {
    	StringBuffer exceldb = new StringBuffer();
    	parserstr = new StringBuffer();
//    	LinkedHashMap<Integer, String> content = new LinkedHashMap<Integer, String>();
    	try{
    		 sheet =  wb.getSheet(sheetName);
             // 得到总行数
//             int rowNum = sheet.getLastRowNum();
             /** 循环Excel的行 */
//             System.out.println("ssssssssss"+sheet.getLastRowNum()); 
//             for (int rowNum = 50; rowNum <= 51; rowNum++) { 
    		 if(erownum == 0 ){
    			 erownum=sheet.getLastRowNum();
			 }
             for (int rowNum = srownum; rowNum <= erownum; rowNum++) {
 				Row row = sheet.getRow(rowNum);
 				if (row == null)
 				{
 					continue;
 				}
 				String value = "\n";
 				String spestr = "|";
 				String savevalue="\n";
 				/** 循环Excel的列 */
 				// 循环列Cell
 				System.out.println("row " +row.getLastCellNum());
 				if(ecellnum == 0 ){
 					ecellnum=row.getLastCellNum();
 				}
// 				for (int cellNum = 4; cellNum <=8; cellNum++) {
 				for (int cellNum = scellnum; cellNum <=ecellnum; cellNum++) {
 					Cell cell =  row.getCell(cellNum);
 					if (cell == null) {
 						continue;
 					}
 					String tvalue = getStringCellValue(cell);
// 					savevalue +=tvalue.trim()+"#";
 					if(RegexValidate.StrNotNull(tvalue.trim())){
 						value +=tvalue.trim()+" ";
 					    savevalue +=tvalue.trim()+"∴";
 					}
 				}
 				
 				 if(RegexValidate.StrNotNull(value)){
 					 exceldb.append(value);
			         parserstr.append(savevalue.trim()+spestr);
 				 }
             }
//             if(exceldb != null && !exceldb.equals(""))
            	 
//		          content.put(rowNum, exceldb.toString());
            	 return exceldb;
    	}catch (Exception e) {
			// TODO: handle exception
    		errorInfo = "解析excel数据异常";
    		exceldb.append(errorInfo);
		}
    	return exceldb;
           
    }
	
     private StringBuffer readExcelContent(Sheet sheet,int srownum,int erownum,int scellnum,int ecellnum) {
     	StringBuffer exceldb = new StringBuffer();
     	try{
              /** 循环Excel的行 */
     		 if(erownum == 0 ){
     			 erownum=sheet.getLastRowNum();
 			 }
              for (int rowNum = srownum; rowNum <= erownum; rowNum++) {
  				Row row = sheet.getRow(rowNum);
  				if (row == null)
  				{
  					continue;
  				}
  				String value = "\n";
  				String spestr = "|";
  				String savevalue="\n";
  				/** 循环Excel的列 */
  				if(ecellnum == 0 ){
  					ecellnum=row.getLastCellNum();
  				}
//  				for (int cellNum = 4; cellNum <=8; cellNum++) {
  				for (int cellNum = scellnum; cellNum <=ecellnum; cellNum++) {
  					Cell cell =  row.getCell(cellNum);
  					if (cell == null) {
  						continue;
  					}
  					String tvalue = getStringCellValue(cell);
  					value +=tvalue+" ";
//  					savevalue +=tvalue.trim()+"#";
  					if(RegexValidate.StrNotNull(tvalue)){
  					    savevalue +=tvalue.trim()+"∴";
  					}
  				}
  				 if(RegexValidate.StrNotNull(value)){
  					 exceldb.append(savevalue.trim()+spestr);
  					 parserstr.append(value);
  				 }
              }
              return exceldb;
     	}catch (Exception e) {
 			// TODO: handle exception
     		errorInfo = "解析excel数据异常";
     		exceldb.append(errorInfo);
 		}
     	return exceldb;
            
     }
 	
     
     // table 表格拆分解析
     public LinkedHashMap<String, String> tableBreakUp(Workbook wb) {
     	StringBuffer exceldb = new StringBuffer();
     	parserstr = new StringBuffer();
     	LinkedHashMap<String, String> content = new LinkedHashMap<String, String>();
     	try{
     		sheet =  wb.getSheet(sheetName);
              // 得到总行数
              /** 循环Excel的行 */
     		 //6,10,3
     		 //6,6,3,8
     		 //解析行开始结束数组
     		 String[] rowarray = String.valueOf(rowstr).split(",");
     		 //解析行号开始结束数组
     		 String[] cellarray = String.valueOf(cellstr).split(",");
     		 if(rowarray != null && rowarray.length>1
     				 && cellarray != null && cellarray.length>1){
     			
				srownum  =  Integer.parseInt(rowarray[0]);
			    erownum=  Integer.parseInt(rowarray[1]);
			    
			    scellnum  =  Integer.parseInt(cellarray[0]);
       		    ecellnum  =  Integer.parseInt(cellarray[1]);
			    
			    glrownum = Integer.parseInt(rowarray[2]);
//			    glcellnum = Integer.parseInt(cellarray[2]);
				
			    for(int i=0;i<cellarray.length;i++){
	            	 if(i==0){
	            		 scellnum = 0;
	            		 ecellnum = Integer.parseInt(rowarray[2]);
	            	 }else{
	            		 // 取包含列头的值
	            		 srownum = (srownum-1);
	            		 scellnum  =  Integer.parseInt(cellarray[0]);
	            		 ecellnum  =  Integer.parseInt(cellarray[1]);
	            	 }
	            	 exceldb =  readExcelContent(sheet, srownum,erownum,scellnum, ecellnum);
	            	 content.put(i+"", exceldb.toString());
	     		 }
			    
     		 }else{
//     			exceldb = readExcelContent(wb);
     			exceldb =  readExcelContent(sheet, srownum,erownum,scellnum, ecellnum);
     			content.put("0", exceldb.toString());
     		 }
     	}catch (Exception e) {
 			// TODO: handle exception
//     		errorInfo = "解析excel数据异常";
     		exceldb.append(errorInfo);
     		content.put("0", errorInfo);
 		}
     	return content;
            
     }
 	
     
	/**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(Cell cell) { 
    	 
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);//这个1的意识是保存结果到小数点后几位
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
       String strCell = "";
       switch (cell.getCellType()) { 
       case HSSFCell.CELL_TYPE_STRING:
           strCell = cell.getStringCellValue();
           break;
       case HSSFCell.CELL_TYPE_BOOLEAN:
           strCell = String.valueOf(cell.getBooleanCellValue());
           break;
       case HSSFCell.CELL_TYPE_BLANK:
           strCell = "";
           break;
       case HSSFCell.CELL_TYPE_NUMERIC:
    	 //判断是否为日期类型
      		if(HSSFDateUtil.isCellDateFormatted(cell)){
      			 cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            		 strCell =  sdf.format(cell.getDateCellValue());//日期型
      		}else{
      			strCell = String.valueOf(cell.getNumericCellValue());
      		}
           break;
       case HSSFCell.CELL_TYPE_FORMULA:
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
       default:
           strCell = "";
           break;
       }
       return strCell;
    }
   
	
    /**
	 * 
	 * @描述：main测试方法
	 * 
	 * @参数：@param args
	 * 
	 * @参数：@throws Exception
	 * 
	 * @返回值：void
	 */

	public static void main(String[] args) throws Exception
	{

		StringBuffer buff = new StringBuffer();
		ExcelConfig poi = new ExcelConfig();
		String filePath ="E:/360云盘/项目文档/青岛海尔EXCEL解析文件/中联钢-月度对比表.xls";
		poi.sheetName="热轧(1)";
		poi.rowstr="18,19,2";
		poi.cellstr="3,0";
		Workbook workbook = poi.readExcel(filePath);
		Map<String, String> datamap =  poi.tableBreakUp(workbook);
		ExcelToBean  tobean = new ExcelToBean();
		//datamap.get("0").toString()
		String[]  contentstr = tobean.getExcelToArray(datamap.get("0").toString());
		//datamap.get("1").toString()
		String[]  dateStr = tobean.getExcelToString(datamap.get("1").toString());
		buff = tobean.getExcelToBuffer(dateStr,contentstr);
		System.out.println(buff.toString().replace("|", "\n")); 
//		List<String[]> listcontent = tobean.getExcelToArray(dateStr,contentstr);
//		for(String[] strarray:listcontent){
//			for(int i=0;i<strarray.length;i++){
//	    		String[] values = strarray;
//	    	}
//			System.out.println("\n");
//		}
		
		
//		LinkedHashMap<String, String[]> datamaps = tobean.getExcelToMap(datamap.get("1").toString()); 
//		for(Entry<String, String[]> it :datamaps.entrySet()){
//		System.out.println(datamap.get("1").toString());
//			String[] content = tobean.getExcelToString(datamap.get("1").toString()); 
//			System.out.println(content);
//			System.out.println(it.getKey()+"--------"+it.getValue());
			
//		}
//		poi.scellnum=1;
//		poi.ecellnum=10;
//		StringBuffer substr= poi.read(filePath);
//	
//		for(String[] str1 : listcontent){
			
//		}
//		for(int i = 0;i<dateStr.length;i++){
//			String[] dates = dateStr[i].split("#");
//			for(int j = 0;j<contentstr.length;j++){
//				String[] content = contentstr[j].split("#");
//				if(content!= null && content.length>0){
//					System.out.println("日期："+dates[0]+" "+"均价:"+dates[1]+" "+"市场:"+content[0]+" "+"品名:"+content[1]+" "+"材质:"+content[2]+"\n");
////					System.out.println("均价:"+dates[1]);
////					System.out.println("市场:"+content[0]);
////					System.out.println("品名:"+content[1]);
////					System.out.println("材质:"+content[2]);
//				}
//			}
//		}
	}
		
}
