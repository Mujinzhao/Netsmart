package core.spider.fun;

/**
 * 
 */

import org.apache.log4j.Logger;

/**
 * 字符(串)/数字处理类。
 */
public class GetInteger {
	private static final Logger logger = Logger.getLogger(GetInteger.class);

	/**
	 * 合并参数字符串中的所有数字，并转化为整数返回。<br>
	 * 不考虑小数点和正负号
	 * @param value 参数字符串
	 * @return 整数,没数字或异常，返回-1
	 */
	public static int getInt(final String value) {
		
		if (null == value) {
			return -1;
		}
		try {
			String sint = "";
			for (int i = 0; i < value.length(); ++i) {
				switch (value.charAt(i))
				{
					case  '1': 
						sint += "1";
						continue;
					case  '2': 
						sint += "2";
						continue;
					case  '3': 
						sint += "3";
						continue;
					case  '4': 
						sint += "4";
						continue;
					case  '5': 
						sint += "5";
						continue;
					case  '6': 
						sint += "6";
						continue;
					case  '7': 
						sint += "7";
						continue;
					case  '8': 
						sint += "8";
						continue;
					case  '9': 
						sint += "9";
						continue;
					case  '0': 
						sint += "0";
						continue;
					case  '.':
//						sint += ".";
//						continue;
				}
			}
			if (sint.length() < 1) {
				return -1;
			}
			return Integer.parseInt(sint);
		} catch (NumberFormatException e) {
//			e.printStackTrace();
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 判断是否存在数字字符
	 * 
	 * @param value
	 * @return 包含数字字符返回true
	 */
	public static boolean hasNum(final String value) {
		
		if (null == value) {
			return false;
		}
		
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i))
			{
				case  '1': return true;
				case  '2': return true;
				case  '3': return true;
				case  '4': return true;
				case  '5': return true;
				case  '6': return true;
				case  '7': return true;
				case  '8': return true;
				case  '9': return true;
				case  '0': return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断字符是不是数字0-9
	 * 
	 * @param c
	 *            要判断的字符
	 * @return 是数字返回true
	 */
	public static boolean isNum(final char c) {
		switch (c) {
			case '0':
				return true;
			case '1':
				return true;
			case '2':
				return true;
			case '3':
				return true;
			case '4':
				return true;
			case '5':
				return true;
			case '6':
				return true;
			case '7':
				return true;
			case '8':
				return true;
			case '9':
				return true;
			default:
				return false;
		}
	}

}
