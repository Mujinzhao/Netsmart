package core.util;

import java.net.URLDecoder;

public class MyURLDecoder
{
	public static String decode(String s, String enc)
	{
		if(null != s && null != enc)
		{
			try
			{
				return URLDecoder.decode(s, enc);
			}
			catch (Exception e)
			{
				return s;
			}
		}
		else
		{
			return null;
		}
	}
}
