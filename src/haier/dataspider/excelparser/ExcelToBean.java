package haier.dataspider.excelparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import haier.dataspider.entity.ParserStructure;
import core.util.RegexValidate;

public class ExcelToBean {
	LinkedHashMap  paramrowMap = new LinkedHashMap();
	public String getPriceTrendString(int rownum,String[][] ExpriceStr){
	       StringBuffer  valuebuffer = new StringBuffer();
	        for (String[] x:ExpriceStr){
	        	if(x!= null && x.length>0){
        			 for(String e:x){
     	        		if(RegexValidate.StrNotNull(e)
     	        				&& !e.equals("null")){
     	        			 valuebuffer.append(e+"∴");
     	        		}
     	        	}
        		}
	        	
	        	valuebuffer.append("|");
	        }
	     return valuebuffer.toString(); 
   }
	
	/**
	 * 解析Excel 接口
	 * @param interfaces
	 * @param content
	 */
	public StringBuffer getExcelToBean(ParserStructure parser,Map<String, String> datamap){
		StringBuffer contentbuff=new StringBuffer();
		String interfaces=parser.getInterfaces();
		// 表格拆分解析
		String content = datamap.get("0").toString();
		if(interfaces.equals("0")){
			String datestr = datamap.get("1").toString();
			String[]  contentstr = getExcelToArray(content);
			String[]  dateStr    = getExcelToString(datestr);
			
			contentbuff  = getExcelToBuffer(dateStr, contentstr);
			return contentbuff;
		}
		else if(interfaces.equals("1")){
			return getExcelToBuffer(content);
		}else if(interfaces.equals("2")){
			return getExcelToBuffer(content);
		}
		else if(interfaces.equals("3")){
		}
		else if(interfaces.equals("4")){
		}
		else if(interfaces.equals("5")){
			return getExcelToBuff(content);
		}
		return null;
	}
	/**
	 * 解析Excel 接口
	 * @param interfaces
	 * @param content
	 */
	public List<String[]> getExcelToList(ParserStructure parser,Map<String, String> datamap){
		List<String[]> arrayBean=null;
		String interfaces = parser.getInterfaces();
		String content = datamap.get("0").toString();
		if(interfaces.equals("0")){
			String datestr = datamap.get("1").toString();
			String[]  dateStr    = getExcelToString(datestr);
			String[]  contentstr = getExcelToArray(content);
			arrayBean =  getExcelToArray(dateStr, contentstr);
		}
		else if(interfaces.equals("1")){
			
		}else if(interfaces.equals("2")){
		}
		else if(interfaces.equals("3")){
		}
		else if(interfaces.equals("4")){
		}
		else if(interfaces.equals("5")){
		}
		return arrayBean;
	}
	/**
	 * 解析Excel 接口
	 * @param interfaces
	 * @param content
	 */
	public String[] getExcelToBean(String interfaces,String content){
		// 表格拆分解析
		if(interfaces.equals("0")){
			return getExcelToString(content);
			
		}
		// 定义下标解析
		else if(interfaces.equals("1")){
			return getExcelToArray(content);
		}else if(interfaces.equals("2")){
		}
		else if(interfaces.equals("3")){
		}
		else if(interfaces.equals("4")){
		}
		// 行转列解析
		else if(interfaces.equals("5")){
			return getExcelToString(content);
		}
		return null;
	}
	
	/**
	 * 解析Excel 接口
	 * @param interfaces
	 * @param content
	 */
	public StringBuffer getExcelToBean(String interfaces,Map<String, String> datamap){
		StringBuffer contentbuff = new StringBuffer();
		// 表格拆分解析
		String content = datamap.get("0").toString();
		if(interfaces.equals("0")){
			String datestr = datamap.get("1").toString();
			// 获取内容值数组
			String[]  contentstr = getExcelToArray(content);
			// 获取需要行转
			String[]  dateStr    = getExcelToString(datestr);
			contentbuff  = getExcelToBuffer(dateStr, contentstr);
			return contentbuff;
		}
		// 定义下标解析
		else if(interfaces.equals("1")){
			return getExcelToBuffer(content);
		}else if(interfaces.equals("2")){
			return getExcelToBuffer(content);
		}
		else if(interfaces.equals("3")){
		}
		else if(interfaces.equals("4")){
		}
		// 行转列解析
		else if(interfaces.equals("5")){
			return getExcelToBuff(content);
		}
		return null;
	}
	
	/**
	 *  数据转换少列补全
	 * @param args
	 */
	
	public String[] getExcelToArray(String content){
		String[] contents = content.split("[|]");
		return contents;
	}
	/**
	 * 表格拆分解析
	 * @param content1
	 * @param content2
	 * @return
	 */
	public List<String[]> getExcelToArray(String[] content1,String[] content2){
		List<String[]> listcontent = new ArrayList<String[]>();
		String[] values =null;
		for(int i = 0;i<content1.length;i++){
			String[] dates = content1[i].split("∴");
			values = new String[content1.length+dates.length];
//			values[0] = dates[0];//日期：
//			values[1] = dates[1];//均价:
			for(int j = 0;j<content2.length;j++){
				String[] content = content2[j].split("∴");
				if(content!= null && content.length>0){
					int k=dates.length;
					int m=0;
					for(String datestr :dates){
						values[m]=datestr;
						m++;
					}
					for(String value :content){
						//datestr = dates[m]!=null? dates[m]:"";
						values[k]=value;
						k++;
					}
					listcontent.add(values);
				}
			}
		}
		return listcontent;
	}
	
	/**
	 * 表格拆分解析
	 * @param content1
	 * @param content2
	 * @return
	 */
	public StringBuffer getExcelToBuffer(String contentstr){
		paramrowMap.clear();
		StringBuffer tcontentbuff = null;
		StringBuffer contentbuff = new StringBuffer();
		String trime="∴";
		int m=0;
		String[] contents = contentstr.split("[|]");
		
			for(int j = 0;j<contents.length;j++){
				int n=0;
				String[] content = contents[j].split("∴");
				tcontentbuff = new StringBuffer();
					if(paramrowMap.size()==0 && m==0){
						for(int i = 0;i<content.length;i++){
							tcontentbuff.append(content[i]+trime);
						    paramrowMap.put(i, content[i]);
						    m=1;
						}
						String contentStr = tcontentbuff.toString().substring(0,tcontentbuff.toString().length()-1);
						contentbuff.append(contentStr);
						contentbuff.append("|");
					}else{
						for(int k=0;k<(paramrowMap.size()-content.length);k++){
							tcontentbuff.append(paramrowMap.get(k).toString()+trime);
							 paramrowMap.put(k, paramrowMap.get(k).toString());
							 n++;
						}
					    for(int i = 0;i<content.length;i++){
					     tcontentbuff.append(content[i]+trime);
						 paramrowMap.put(n,content[i]);
						 n++;
				        }
					    // 截取最后的空格
						String contentStr = tcontentbuff.toString().substring(0,tcontentbuff.toString().length()-1);
						contentbuff.append(contentStr);
						contentbuff.append("|");
					}
					
				}
//			System.out.println(contentbuff.toString()); 
		return contentbuff;
//		return listcontent;
	}
	
	public StringBuffer getExcelToBuffer1(String[] content1,String[] content2){
		StringBuffer tcontentbuff = null;
		StringBuffer contentbuff = new StringBuffer();
		String trime="∴";
		for(int i = 0;i<content1.length;i++){
			tcontentbuff = new StringBuffer();
			String[] dates = content1[i].split("∴");
		
			for(int j = 0;j<content2.length;j++){
				String[] content = content2[j].split("∴");
				tcontentbuff.append(dates[0]+trime);
				tcontentbuff.append(dates[1]+trime);
//				for(String datestr :dates){
//					
//					tcontentbuff.append(datestr+trime);
//				}
				if(content!= null && content.length>0){
					for(String value :content){
						tcontentbuff.append(value+trime);
					}
					// 截取最后的空格
					String contentStr = tcontentbuff.toString().substring(0,tcontentbuff.toString().length()-1);
					contentbuff.append(contentStr);
					contentbuff.append("|");
				}
				
			}
		}
		return contentbuff;
	}

	
	public StringBuffer getExcelToBuffer(String[] content1,String[] content2){
		StringBuffer tcontentbuff = null;
		StringBuffer contentbuff = new StringBuffer();
		String trime="∴";
		
		for(int i = 0;i<content1.length;i++){
			int n=1;
			String[] dates = content1[i].split("∴");
			for(int k = 0;k<content2.length;k++){
				tcontentbuff = new StringBuffer();
				tcontentbuff.append(dates[0]+trime);
				for(int j = n;j<dates.length;j++){
					tcontentbuff.append(dates[n]+trime);
					n++;
					break;
				}
				String[] content = content2[k].split("∴");
				if(content!= null && content.length>0){
					for(String value :content){
						tcontentbuff.append(value.trim()+trime);
					}
				}
				// 截取最后的空格
				String contentStr = tcontentbuff.toString().substring(0,tcontentbuff.toString().length()-1);
				contentbuff.append(contentStr);
				contentbuff.append("|");
			}
		}
		return contentbuff;
	}


	/**
	 *  将Excel数据转换成二维数组 列转行
	 * @param args
	 */
	public String[] getExcelToString(String content){
		// 定义一个二维数据 用于列转行
		String contentStr=null;
		String rowtoline[][] ;
		int rownum = 0;
		// 获取解析内容值 转换成数组
		String[] datastr = content.split("[|]");
			if(datastr != null && datastr.length >0){
			 rowtoline = new String[datastr.length][];
			 for(int i =0;i<datastr.length;i++){
				// 获取第一列值
				String[] rowi =null;
				rowi= datastr[i].split("[∴]");
				rowtoline[i]=new String[rowi.length]; 
				for(int j = 0;j<rowi.length;j++){
//					System.out.println(rowi[j]);
					 rowtoline[i][j] = rowi[j]; 
				}
				rownum=rowi.length+1;
			}
			rowtoline = setArray(rownum+rowtoline.length,rowtoline);
			//将数组行值转换成列值数组
			contentStr =  getPriceTrendString(rownum,rowtoline);
			contentStr =contentStr.substring(0,contentStr.length()-1);
		  }
		return getExcelToArray(contentStr);
	}
	
	
	/**
	 *  将Excel数据转换成二维数组 列转行
	 * @param args
	 */
	public StringBuffer getExcelToBuff(String content){
		
		// 定义一个二维数据 用于列转行
		String contentStr=null;
		String rowtoline[][] ;
		int rownum = 0;
		// 获取解析内容值 转换成数组
		String[] datastr = content.split("[|]");
			if(datastr != null && datastr.length >0){
			 rowtoline = new String[datastr.length][];
			 for(int i =0;i<datastr.length;i++){
				// 获取第一列值
				String[] rowi =null;
				rowi= datastr[i].split("[∴]");
				rowtoline[i]=new String[rowi.length]; 
				for(int j = 0;j<rowi.length;j++){
//					System.out.println(rowi[j]);
					 rowtoline[i][j] = rowi[j]; 
				}
				rownum=rowi.length+1;
			}
			rowtoline = setArray(rownum+rowtoline.length,rowtoline);
			//将数组行值转换成列值数组
			contentStr =  getPriceTrendString(rownum,rowtoline);
			contentStr =contentStr.substring(0,contentStr.length()-1);
		  }
		return getExcelToBuffer(contentStr);
	}
	
	
	//进行元素倒置
	private String[][] setArray (int arraylength,String[][] ExpriceStr){
		   String result_arr[][]=new String[arraylength][];//先实现第一维
			for(int i=0 ; i<arraylength;i++){ //再实现第二维
				    result_arr[i]=new String[arraylength];
			}
	        /*******进行元素倒置******/
	        for(int i=0 ; i<ExpriceStr.length;i++){
		        	for(int j=0; j<ExpriceStr[i].length;j++){
		        		      result_arr[j][i]=ExpriceStr[i][j]; //转置核心
//		        		      System.out.println(result_arr[j][i]);
		        	}	
	        }
	        
	       return result_arr;
	}
	
	//进行元素倒置
	private int[][] setArray (int arraylength,int[][] ExpriceStr){
		    int result_arr[][]=new int[arraylength][];//先实现第一维
			for(int i=0 ; i<arraylength;i++){ //再实现第二维
				    result_arr[i]=new int[arraylength];
			}
	        /*******进行元素倒置******/
	        for(int i=0 ; i<ExpriceStr.length;i++){
		        	for(int j=0; j<ExpriceStr[i].length;j++){
		        		      System.out.println(ExpriceStr[i][j]);
		        		       result_arr[j][i]=ExpriceStr[i][j]; //转置核心
		        		      System.out.println(result_arr[j][i]);
		        	}	
	        }
	        
	       return result_arr;
	}
	
	public static void main2(String[] args) {
		String content = "上海#宝钢益昌#0.4*1000*C#SPCC-SD#5044#5060#-16|0.5*1000*C#SPCC-SD#4984#5000#-16|宝钢#0.8*1250*C#DC01#4856#4880#-24|0.9*1250*C#DC01#5670#4880#-24";
//				 int a[][] = {{1,2,4},{6,7,8,9},{10,11,12,13}} ;
//				 int b[][] = new  ExcelToBean().setArray(3,a);
//				System.out.println(b);
//			        for(int i=0 ; i <a.length ; i++) { 
//			            for(int j=0 ; j<a[i].length ; j++) { 
//			                System.out.println("a[" + i + "][" + j + "]=" + a[i][j]) ; 
//			            } 
//			        } 
	}
	
		     public static void main(String[] args) {
		    	 String s1="鞍钢∴武钢∴南钢∴济钢∴柳钢∴湘钢∴安钢∴包钢|" +
		    	 		"4305.6∴4212.0∴4010.0∴3640.0∴4400.0∴4400.0∴3732.3|" +
		    	 		"4481.1∴4340.7∴4160.0∴3790.0∴4550.0∴4550.0∴3907.8|" +
		    	 		"4446.0∴4738.5∴4110.0∴3820.0∴4600.0∴4600.0∴3896.1|" +
		    	 		"4574.7∴4832.1∴4210.0∴3860.0∴4600.0∴4650.0∴3989.7|" +
		    	 		"4750.2∴4726.8∴4060.0∴3830.0∴4550.0∴4600.0∴3884.4|" +
		    	 		"4995.9∴4960.8∴4260.0∴4030.0∴4740.0∴4800.0∴3931.2|" +
		    	 		"4820.4∴5101.2∴4410.0∴4060.0∴5150.0∴4700.0∴3966.3|" +
		    	 		"4691.7∴4481.1∴4410.0∴4120.0∴5100.0∴4700.0∴4188.6|" +
		    	 		"4739∴/∴4430∴4300∴4250∴/∴4493∴4170|";
		 		String s = "鞍钢∴ 武钢∴ 南钢∴ 济钢∴ 柳钢∴ 湘钢 ∴安钢∴ 包钢|" +
		 				"4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
		 				"4206∴4112∴4010∴3340∴4500∴4400∴∴832|"+
		 				"4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
	   				    "4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
		 				"4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
		 				"4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
		 				"4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
		 				"4306∴4212∴4010∴3640∴4400∴4400∴3732|"+
		 				"4739∴/∴4430∴4300∴4250∴/∴4493∴4170";
		 		String s3 ="规格∴河钢∴首钢∴国丰∴包钢∴日钢∴|5.5mm*1500∴3600.0∴3814.2∴/∴3540.0∴3243.0∴|3.0mm*1260∴3710.0∴3884.4∴3600.0∴3740.0∴3303.0∴|";
				String rowtoline[][] ;
				int rownum = 0;
				String contentStr =null;
				// 获取解析内容值 转换成数组
				String[] datastr = s.split("[|]");
					if(datastr != null && datastr.length >0){
					 rowtoline = new String[datastr.length][];
					 for(int i =0;i<datastr.length;i++){
						// 获取第一列值
						String[] rowi =null;
						rowi= datastr[i].split("[∴]");
						rowtoline[i]=new String[rowi.length]; 
						for(int j = 0;j<rowi.length;j++){
//							System.out.println(rowi[j]);
							 rowtoline[i][j] = rowi[j]; 
						}
						rownum=rowi.length+1;
					}
					  String c[][] = new  ExcelToBean().setArray(rownum+rowtoline.length,rowtoline);
					  
						contentStr =  new  ExcelToBean().getPriceTrendString(rownum,c);
						contentStr =contentStr.substring(0,contentStr.length()-1);
				  }
					System.out.println(contentStr);
		    }
}
