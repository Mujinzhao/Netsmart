package core.spider.fun;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 返回全文信息的一个时间(发布时间)。<br>
 * 首先返回第一个包含时分的日期<br>
 * 如果没有则返回首部或尾部日期<br>
 * 最后返回全文中符合格式的日期<br>
 * 不存在则返回null<br>
 * <br>
 * <br>
 * <br>
 * 此次日期提取不包括一些评论特色时间（例如：1分钟前，1小时前）
 * 
 */
public class DatePattern {

	private static final Logger logger = Logger.getLogger(DatePattern.class);
	// 网页中的3种空格字符
	private static final String blank1 = String.valueOf((char) 12288);
	private static final String blank2 = String.valueOf((char) 160);
	private static final String blank3 = String.valueOf((char) 32);

	private static final String blank = blank1 + "|" + blank2 + "|" + blank3;
	
	/**
	 * 提取input中的日期
	 * 
	 * @param input
	 *            输入网页全文
	 * @return Date日期
	 */
	public Date getDate(String input) {

	    /*
		 * 选择日期方式：正则匹配全文，如果有具体时分的时间，则立刻返回。 
		 * 如果没有时分，则返回一个最近的日期 如果没有日期，返回采集该网页时刻的时间
		 */

		if (input == null) {
			return null;
		}

		input = input.replace("&nbsp;", " ");
		input = input.replace("　", " ");
		
		
		//加入英文时间格式处理
		input = getDateFromEng(input);
		
		
		// 日期正则 包含时间，年份3种分隔符（-，/，年月日），时间分隔符（:）
		String year  = "(19|20)?\\d{2}(-|/|年|\\.)";
		String month = "(((0)?[1-9])|(1[0-2]))(-|/|月|\\.)";
		String day   = "(([0-3]?[0-9])|(3[0-1]))(日|号|(\\s))?";
		String time  = "((([01]?[0-9])|(2[0-4]))(时|:)[0-5]?[0-9](分)?(:[0-5]?[0-9])?)?";
		String regex = year + month + day + "\\s{0,4}" + time;

		Date date = null; // 返回值

		// 去掉不合法空格
		input = input.replace("(" + blank + ")+", blank3);
		String s_date = null; // 存放返回日期的字符串
		String datetemp = null; // 存放后续得到的日期字符串，与s_date比较，选出最符合要求的，放入s_date中

		Date today = Calendar.getInstance().getTime();
		Date date_date = null;
		Date date_temp = null;

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {

			if (null == s_date) {
				s_date = matcher.group();

				if (s_date.contains(":")) {
					break;
				}
			} else { // 当前获得的日期与之前获取的日期比较，选取最合适的日期
				datetemp = matcher.group();

				if (datetemp.contains(":")) {
					s_date = datetemp;
					break;
				}

				// 选出最近时间作为返回值
				date_date = dateFormat(s_date);
				date_temp = dateFormat(datetemp);
				if (date_temp.after(date_date) && date_temp.before(today)) {
					s_date = datetemp;
				}
			}
		}

		date = dateFormat(s_date);

		return date;
	}

	/**
	 * 针对微信接口 提取日期
	 * @param now
	 * @param time
	 * @return
	 */
	public String vrTimeHandle552(long now,String time)
	{
	 try
	 {
	   Date  date = new  Date((Long.parseLong(time)*1000));
	   Date  todate = new  Date(now);
	   if (date.getYear() == todate.getYear() && date.getMonth() == todate.getMonth() && date.getDate() == todate.getDate()) {
	   return (date.getYear()+1900) + "年" + (date.getMonth() < 9 ? "0":"") + (date.getMonth() + 1) + "月" + (date.getDate() < 10 ? "0":"") + date.getDate() + "日 " + (date.getHours() < 10 ? "0":"") + date.getHours() + ":" + (date.getMinutes() < 10 ? "0":"") + date.getMinutes()+":00";
	   }else{
	   return (date.getYear()+1900) + "年" + (date.getMonth() < 9 ? "0":"") + (date.getMonth() + 1) + "月" + (date.getDate() < 10 ? "0":"") + date.getDate() + "日 00:00:00";
	   }
	 }
	 catch(Exception e){
	  e.printStackTrace();
	 }
	 return "";
	}
	
	/**
	 * 针对微信接口 提取日期
	 * @param now
	 * @param time
	 * @return
	 */
	public  String vrTimeHandle552(String time)
	{
	 try
	 {
	   Date  date = new  Date((Long.parseLong(time)*1000));
	   long now =  System.currentTimeMillis();
	   Date  todate = new  Date(now);
	   if (date.getYear() == todate.getYear() && date.getMonth() == todate.getMonth() && date.getDate() == todate.getDate()) {
	   return (date.getYear()+1900) + "年" + (date.getMonth() < 9 ? "0":"") + (date.getMonth() + 1) + "月" + (date.getDate() < 10 ? "0":"") + date.getDate() + "日 " + (date.getHours() < 10 ? "0":"") + date.getHours() + ":" + (date.getMinutes() < 10 ? "0":"") + date.getMinutes()+":00";
	   }else{
	   return (date.getYear()+1900) + "年" + (date.getMonth() < 9 ? "0":"") + (date.getMonth() + 1) + "月" + (date.getDate() < 10 ? "0":"") + date.getDate() + "日 00:00:00";
	   }
	 }
	 catch(Exception e){
	  e.printStackTrace();
	 }
	 return "";
	}
	/**
	 * 格式化日期，把字符串，格式化为Date类型
	 * 
	 * @param sdate
	 *            日期字符串格式(xx)xx(-/年)xx(-/月)xx(日)( xx:xx(:xx))。
	 *            本类getDate中正则得到的字符串
	 * @return Date 格式化后的日期，格式化失败则返回当前日期。
	 */
	private Date dateFormat(String sdate) {

		//-----
		if (null == sdate) {
//			return Calendar.getInstance().getTime();
			return null;//20110818修改如果没有日期则返回null；
		}

		//-----修改20110622
		if (sdate.endsWith("分")) {
			sdate = sdate.substring(0,sdate.length()-1);
		}

		// 统一格式化日期
		String day = ""; // 存放统一格式后的时间字符串 yy-MM-dd HH:mm:ss
		int len = sdate.length();
		int seplen = 0; // 记录时间分隔符个数，用于后续补充完整
		char c;
		for (int i = 0; i < len; i++) {
			c = sdate.charAt(i);
			if (GetInteger.isNum(c)) {
				day += c;
			} else {
				switch (c) {
					case '年':
					case '/':
					case '月':
					case '-':
					case '.':
						day += "-";
						break;
					case '日':
					case ' ':
						day += blank3;
						break;
					case '时':
					case '分':
					case ':':
					case '：':
						day += ":";
						seplen++;
						break;
				}
			}
		}

		// 把day中缺少的时分秒用:00补充完整，方便SimpleDateFormat操作
		switch (seplen) {
			case 0:
				day += " 00:00:00";
				break;
			case 1:
				day += ":00";
				break;
		}

		// 格式化日期转换
		SimpleDateFormat sdf = null;
		
		//日期和时间之间是否有空格
		if (day.contains(" ")) {
			sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yy-MM-ddHH:mm:ss");
		}

		// 返回值，异常则返回当前日期
		Date date = null;

		try {
			date = sdf.parse(day);
		} catch (ParseException e) {
			logger.error(e);
		}

		return date;
	}
	
	private static String getDateStr(String dateStr){
		String day = dateStr;
		Pattern pattern = null;
		Matcher matcher = null;
		if (day.length() == 9) {
			pattern = Pattern.compile("\\d{4}\\-\\d{4}");
			matcher = pattern.matcher(day);
			if (matcher.find()) {
				day = day.substring(0,7)+"-"+day.substring(7);
			}else {
				pattern = Pattern.compile("\\d{6}\\-\\d{2}");
				matcher = pattern.matcher(day);
				if (matcher.find()) {
					day = day.substring(0,4)+"-"+day.substring(4);
				}
			}
		}else if (day.length() == 7) {
			pattern = Pattern.compile("\\d{2}\\-\\d{4}");
			matcher = pattern.matcher(day);
			if (matcher.find()) {
				day = day.substring(0,5)+"-"+day.substring(5);
			}
		}
		return day;
	}
	/**
	 * 格式化日期，把字符串，格式化为Date类型
	 * 
	 * @param sdate
	 *            日期字符串格式(xx)xx(-/年)xx(-/月)xx(日)( xx:xx(:xx))。
	 *            本类getDate中正则得到的字符串
	 * @return Date 格式化后的日期，格式化失败则返回当前日期。
	 */
	private static Date dateFormat1(String sdate) {
		if (null == sdate) {
			return null;//20110818修改如果没有日期则返回null；
		}
		// 统一格式化日期
		String day = ""; // 存放统一格式后的时间字符串 yy-MM-dd
		int len = sdate.length();
		char c;
		for (int i = 0; i < len; i++) {
			c = sdate.charAt(i);
			if (GetInteger.isNum(c)) {
				day += c;
			} else {
				switch (c) {
					case '年':
					case '/':
					case '月':
					case '-':
					case '_':
					case '.':
						day += "-";
						break;
					case '日':
					case ' ':
						day += blank3;
						break;
				}
			}
		}
		day = getDateStr(day);
		// 格式化日期转换
		SimpleDateFormat sdf = null;
		
		//日期和时间之间是否有空格
		if (day.contains("-") && day.length()==10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else if(day.contains("-") && day.length()==8){
			sdf = new SimpleDateFormat("yy-MM-dd");
		}else if (!day.contains("-") && day.length()==8) {
			sdf = new SimpleDateFormat("yyyyMMdd");
		}else if(!day.contains("-")&&day.length()==6){
			sdf = new SimpleDateFormat("yyMMdd");
		}else {
			return null;
		}

		// 返回值，异常则返回当前日期
		Date date = null;

		try {
			date = sdf.parse(day);
			if (date.after(new Date())) {
				date = null;
			}
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}

	public static Date getDateFromUrl(String url){
		if (url == null || url.equals("")) {
			return null;
		}
		String dateStr = null;
		String temp = null;
		Date date = null;
		// 日期正则 包含时间，年份3种分隔符（-，/，年月日），时间分隔符（:）
		String year  = "(19|20)?\\d{2}(-|/|年|\\.)";
		String month = "(((0)?[1-9])|(1[0-2]))(-|/|月|\\.)";
		String day   = "(([0-3]?[0-9])|(3[0-1]))(日|号|(\\s)|/|_)";
		String regex = year+month+day;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		while (matcher.find()) {
			if (dateStr == null) {
				dateStr = matcher.group().trim();
			}else {
				temp = matcher.group().trim();
				if (dateStr.length() < temp.length()) {
					dateStr = temp;
				}
			}
			
		}
		if (dateStr != null) {
			if (dateStr.endsWith("/")|dateStr.endsWith("_")) {
				dateStr = dateStr.substring(0,dateStr.length()-1);
			}
			date = dateFormat1(dateStr);
		}
		return date;
	}
	
	public Date getDate1(String str){
		Date date = new Date();
		String chazhi = "";
		String danwei = "";
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Pattern pattern = Pattern.compile("(\\d+)(.*?)前");
			Matcher matcher = pattern.matcher(str);
			Calendar cal = Calendar.getInstance();
			if (matcher.find()) {
				chazhi = matcher.group(1);
				danwei = matcher.group(2);
				if (danwei.equals("秒") || danwei.equals("分钟") || danwei.equals("小时")) {
					if (danwei.equals("秒")) {//10秒前
						cal.add(Calendar.SECOND, -Integer.parseInt(chazhi));
					}else if (danwei.equals("分钟")) {//10分钟前
						cal.add(Calendar.MINUTE, -Integer.parseInt(chazhi));
					}else if (danwei.equals("小时")) {//1小时前
						cal.add(Calendar.HOUR, -Integer.parseInt(chazhi));
					}
					date = cal.getTime();
				}else {
					String time = "00:00:00";//天 月 年
					int dateNum = Integer.parseInt(chazhi);
					if (danwei.equals("天")) {
						cal.add(Calendar.DAY_OF_WEEK, -dateNum);
					}else if(danwei.equals("月")||danwei.equals("个月")){
						cal.add(Calendar.MONTH, -dateNum);
					}else if(danwei.equals("年")){
						cal.add(Calendar.YEAR, -dateNum);
					}
					str = sdf.format(cal.getTime());
					date = sdf1.parse(str+" "+time);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static void main(String[] args) {
		String str = "2014年5月10日 - 经常在上网的时候看到别的网站有自己喜欢的flash或者视频,但是又不能下载怎么办呢?一般我们浏览网页的时候系统会自动将浏览的网页缓存到我们电脑中。那么";//Jamshedpur, 7/6/2012
		Date date = new DatePattern().getDate(str);
		System.out.println(date.toString());
//		BufferedReader buf = null;
//		try {
//			buf = new BufferedReader(new InputStreamReader(new FileInputStream("D:/url.txt")));
//			String line;
//			while ((line = buf.readLine()) != null) {
//				System.out.println(line);
//				System.out.println(DatePattern.getDateFromUrl(line));
//			}
//			buf.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	public String getDateFromEng(String string){
		
		String[] fullName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		String[] simpleName = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		String[] number = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		HashMap <String,String> month = new HashMap<String, String>();
		String monthStr = "";
		for(int i = 0; i < 12; i++){
			month.put(fullName[i].toLowerCase(), number[i]);
			month.put(simpleName[i].toLowerCase(), number[i]);
			monthStr = monthStr+fullName[i]+"|"+simpleName[i]+"|";
		}
		
		if (monthStr.endsWith("|")) {
			monthStr = monthStr.substring(0,monthStr.length()-1);
		}
		string = string.replaceAll("\\s+", " ");
		
		String regex = "("+monthStr+")[\\.|\\s](\\d{1,2}),[\\s]?(\\d{4})";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			string = matcher.group(3)+"-"+month.get(matcher.group(1).toLowerCase())+"-"+matcher.group(2);
		}else {
			regex = "(\\d{1,2})\\s"+"("+monthStr+")\\s(\\d{4})";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(string);
			if (matcher.find()) {
				string = matcher.group(3)+"-"+month.get(matcher.group(2).toLowerCase())+"-"+matcher.group(1);
			}
		}
		return string;
	}
}
