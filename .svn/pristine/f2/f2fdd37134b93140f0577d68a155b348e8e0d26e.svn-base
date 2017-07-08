package core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

  
/**  
 
 * @classDescription:Levenshtein Distance 算法实现  
 * 可以使用的地方：DNA分析  
 
 */  
public class MyLevenshtein {   
  

	private static final String REMOVE_DIC="连云港,教学楼,楼,#,有限公司,公司,工程,项目";
	private static final String REPLACE_DIC="连云港市|连云港|教学楼|楼|开发有限责任公司|地产开发|开发公司|有限责任公司|有限公司|分公司|公司|工程|项目|#|、|-";
	//正则初始化
	private static Pattern pattern = Pattern.compile(REPLACE_DIC); 
	 
    public static void main(String[] args) {   
       //要比较的两个字符串   
        String str1 = "中国白电行业大宗原料采购综合周报（8.18-8.22） - 副本";   
        String str2 = "白电行业";   
        if(str1.contains(str2)){
        	 long longt=System.currentTimeMillis();
             System.out.println(levenshteinStr(str1, str2)); 
        }
       
        
      
        
       // System.out.println(checkStrPrev(str1));
        //替换第一个符合正则的数据 
        //System.out.println(ReplaceStr(str1)); 
       // String[] cutstrs=cutStrings(str1,str2);
       // System.out.println("source:"+cutstrs[0]+" target:"+cutstrs[1]);
        //System.out.println( levenshteinStr(str1,str2));   
//        System.out.println(getSimilarity(str1,str2));
        //System.out.println(System.currentTimeMillis()-longt);
        
       
    }   
    

    /**   
      *   中文字符检验   
      * @param   s   String   
      * @return   字符全部中文返回true,否则返回false   
      * */   
        private static  boolean chineseValid(String s){   
            byte   []   b = s.getBytes();   
	        if((b[0]&0xff)>128)   
	            return   true;
            return   false;   

        }
    //方案记录 从字符串前缀开始搜索 忽略相同 直到出现不同的字符 记录个数 
    //去除后缀修饰词汇
    public static String[] cutStrings(String sourcestr,String targetstr){
    	
    	String[] strs = new String[2];
    	//去除不必要关键词
    	sourcestr=ReplaceStr(sourcestr);
    	targetstr=ReplaceStr(targetstr);
    	//去除后缀修饰，提高主语相似度
    	while(true){
    	    if(sourcestr.length()==0
    	    ||targetstr.length()==0
    	    ||(sourcestr.charAt(sourcestr.length()-1)!=targetstr.charAt(targetstr.length()-1))){
    	    	break;
    	    }
    	    sourcestr=sourcestr.substring(0,sourcestr.length()-1);
    	    targetstr=targetstr.substring(0,targetstr.length()-1) ;
    	}
    	//去除不必要关键词
    	strs[0]= sourcestr;
    	strs[1]=targetstr;
    	return strs;
    }
    //替换字符
    public static String ReplaceStr(String str){
    	
    	//for(String var :REMOVE_DIC.split("[,]")){
    	//	str=str.replace(var, "");
    	//}
    	return pattern.matcher(str).replaceAll("");
    }
    static  Pattern numpattern = Pattern.compile("[0-9]*"); 
    static  Pattern charpattern = Pattern.compile("[a-zA-Z]*"); 
    //检测字符串前缀是否合法
    public static boolean checkStrPrev(String sourcestr){
    	String firstchar =sourcestr.substring(0,1);
    	//首字母中文检测
    	if(chineseValid(firstchar)){
    		return true;
    	}
    	//如果是字母
    	if(charpattern.matcher(firstchar).matches()){
    		return false;
    	}
    	//首字母数字检测,必须前4位都是数字 前已‘20’打头
    	if(numpattern.matcher(firstchar).matches()&&
    		sourcestr.length()>=4&&
    		numpattern.matcher(sourcestr.substring(0, 4)).matches()&&
    		sourcestr.startsWith("20")
    		){
    			return true;
    		
    	}
    	return false;
     
    }
    /**  
     * 　　DNA分析
     */  
    public static void levenshtein(String str1,String str2) { 
    	
        //计算两个字符串的长度。   
        int len1 = str1.length();   
        int len2 = str2.length();   
       //建立上面说的数组，比字符长度大一个空间   
        int[][] dif = new int[len1 + 1][len2 + 1];   
        //赋初值，步骤B。   
        for (int a = 0; a <= len1; a++) {   
            dif[a][0] = a;   
        }   
        for (int a = 0; a <= len2; a++) {   
            dif[0][a] = a;   
        }   
        //计算两个字符是否一样，计算左上的值   
        int temp;   
        for (int i = 1; i <= len1; i++) {   
           for (int j = 1; j <= len2; j++) {   
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {   
                    temp = 0;   
                } else {   
                    temp = 1;   
                }   
                //取三个值中最小的   
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,   
                        dif[i - 1][j] + 1);   
            }   
        }   
       // System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");   
        //取数组右下角的值，同样不同位置代表不同字符串的比较   
       // System.out.println("差异步骤："+dif[len1][len2]);   
        //计算相似度   
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());   
        System.out.println("相似度："+similarity);   
   }   
    /**
     * 计算相似度
     * @param sourceStr
     * @param targetStr
     * @return 相似度
     */
    public static float levenshteinStr(String sourceStr,String targetStr) {  
   
    	//如果完全相同
    	if(sourceStr.equals(targetStr)){
    		return  (float)1.0;
    	}
    	//剪切不必要重复字符
    	String[] cutstrs=cutStrings(sourceStr,targetStr);
    	sourceStr=cutstrs[0];
    	targetStr=cutstrs[1];
        //计算两个字符串的长度。   
        int len1 = sourceStr.length();   
        int len2 = targetStr.length();   
       //建立上面说的数组，比字符长度大一个空间   
        int[][] dif = new int[len1 + 1][len2 + 1];   
        //赋初值，步骤B。   
        for (int a = 0; a <= len1; a++) {   
            dif[a][0] = a;   
        }   
        for (int a = 0; a <= len2; a++) {   
            dif[0][a] = a;   
        }   
        //计算两个字符是否一样，计算左上的值   
        int temp;   
        for (int i = 1; i <= len1; i++) {   
           for (int j = 1; j <= len2; j++) {   
                if (sourceStr.charAt(i - 1) == targetStr.charAt(j - 1)) {   
                    temp = 0;   
                } else {   
                    temp = 1;   
                }   
                //取三个值中最小的   
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,   
                        dif[i - 1][j] + 1);   
            }   
        }   
       // System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");   
        //取数组右下角的值，同样不同位置代表不同字符串的比较   
       // System.out.println("差异步骤："+dif[len1][len2]);   
        //计算相似度   
        float similarity =1 - (float) dif[len1][len2] / Math.max(sourceStr.length(), targetStr.length());   
        return similarity;
       // System.out.println("相似度："+similarity);   
   }   
  
    //得到最小值   
    private static int min(int... is) {   
        int min = Integer.MAX_VALUE;   
        for (int i : is) {   
            if (min > i) {   
                min = i;   
            }   
        }   
        return min;   
    }   
    
    //计算公共字符串
    public static List<String> getSameStr(String str1, String str2){
    	  char[] arrchar1 = str1.toCharArray();
    	  char[] arrchar2 = str2.toCharArray();
    	  int[][] arr = new int[arrchar1.length][arrchar2.length];
    	  int len = arrchar1.length < arrchar2.length ? arrchar1.length:arrchar2.length;
    	  int maxarr[] = new int[len];
    	  int maxindex[] = new int[len];
    	  for(int i = 0; i < arrchar1.length ; i++){
    	   for(int j = 0; j < arrchar2.length ; j++){
    	    if(arrchar2[j] == arrchar1[i]){
    	     if(i == 0 || j == 0){
    	      arr[i][j] = 1;
    	      if(maxarr[0] < 1){
    	       maxarr[0] = 1;
    	       maxindex[0] = i;
    	      }
    	     }else{
    	      arr[i][j] = arr[i-1][j-1] + 1;
    	      //如果当前求出的子串长度大于了maxarr中第一个数值 则清空maxarr数值 全部置0 同时替换第一个最大值
    	      if(maxarr[0] < arr[i][j]){
    	       maxarr[0] = arr[i][j];
    	       maxindex[0] = i;
    	       for(int num = 1; num < maxarr.length; num++){
    	        if(maxarr[num] == 0){
    	         break;
    	        }else{
    	         maxarr[num] = 0;
    	         maxindex[num] = 0;
    	        }
    	       }
    	      }else if (maxarr[0] == arr[i][j]){
    	       //如果当前求出的子串长度跟maxarr中第一个一致 则保留
    	       int num = 0;
    	       for(int max : maxarr){
    	        if(max == 0){
    	         maxarr[num] = arr[i][j];
    	         maxindex[num] = i;
    	         break;
    	        }
    	        num++;
    	       }
    	      }
    	     }
    	    }else{
    	     arr[i][j] = 0;
    	    }
    	   }
    	  }
    	  for(int i = 0;i < arr.length ; i++){
    	   for(int j = 0;j < arr[i].length;j++){
    	    System.out.print("   " + arr[i][j]);
    	   }
    	   System.out.println("");
    	  }
    	  
    	  List<String> list = new ArrayList<String>();
    	  
    	  
    	  for(int i = 0; i< maxarr.length ;i++){
    	   if(maxarr[i] == 0){
    	    break;
    	   }
    	   int num = maxindex[i] - (maxarr[i] -1);
    	   String str = "";
    	   for(int k = 0;k<maxarr[i];k++){
    	    char tempchar = arrchar1[num];
    	    str += String.valueOf(tempchar);
    	    num++;
    	   }
    	   System.out.println(str);
    	   list.add(str);
    	  }
    	    
    	  return list;
    	 }

    
    
}  