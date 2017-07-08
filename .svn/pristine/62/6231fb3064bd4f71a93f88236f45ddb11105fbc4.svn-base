package core;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import core.util.MyURLDecoder;
import core.util.RegexValidate;
/**
 * 
 * action基类，提供action公共属性。
 * 
 * @author DATACVG
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware
{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1094248453662922388L;
	
	private static final String USER="USER";
	
	private static final String USERID_BOEKEY="USERID_BOEKEY";
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(BaseAction.class);

	/**
	 * 编码格式
	 */
	protected static final String CHARACTER_ENCODING = "utf-8";

	/**
	 * 内容类型
	 */
	protected static final String CONTENT_TYPE = "text/xml";
	
	/**
	 * request
	 */
	protected HttpServletRequest request;

	/**
	 * session
	 */
	protected Map<String, Object> session;

	/**
	 * response
	 */
	protected HttpServletResponse response;

	/**
	 * 默认构造方法
	 */
	public BaseAction()
	{
	}

	/**
	 * 
	 * 获取字符串参数值
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public String getStringParameter(String name) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("ISO-8859-1");
//		String ss = URLDecoder.decode(request.getParameter(name), "UTF-8");
		return MyURLDecoder.decode(request.getParameter(name), "UTF-8");
	}

	/**
	 * 
	 * 获取参数值数组
	 */
	public String[] getParameterValues(String name)
	{
		return request.getParameterValues(name);
	}

	/**
	 * 
	 * 获取长整型参数值
	 * @throws UnsupportedEncodingException 
	 */
	public Long getLongParameter(String name) throws UnsupportedEncodingException
	{
		// 获取参数String字串
		String parameter = getStringParameter(name);

		Long result = 0L;

		if (!RegexValidate.StrisNull(parameter))
		{
			try
			{
				// 将String子串参数转换成Long类型
				result = Long.parseLong(parameter);

			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 
	 * 获取整型参数值
	 * @throws UnsupportedEncodingException 
	 */
	public Integer getIntegerParameter(String name) throws UnsupportedEncodingException
	{
		// 获取参数String子串
		String parameter = getStringParameter(name);

		Integer result = 0;

		if (!RegexValidate.StrisNull(parameter))
		{
			try
			{
				// 将参数String子串转换成Integer类型
				result = Integer.parseInt(parameter);

				return result;
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * 向客户端写入内容
	 * @param data 需要写入的数据
	 */
	public void write(String data)
	{
		if (RegexValidate.StrisNull(data))
		{
			
			//data = "";
			return;
		}

		Writer writer = null;
		
		try
		{
			writer = response.getWriter();
			writer.write(data);
			writer.flush();
			//2013-12-27
			//由于打印信息消耗服务器性能比较大 因屏蔽此打印信息
//			logger.info("Write data to client successfully. The data is: " + data + "");
			
		} catch (IOException e)
		{
			logger.error("Write data error.", e);
		}
		finally
		{
			try
			{
				writer.close();
			} 
			catch (IOException e)
			{
				logger.error("Close writer error", e);
			}
		}
	}
	
	/**
	 * 获取response的Writer对象
	 * @return
	 */
	public Writer getWriter()
	{
		if (null != response)
		{
			try
			{
				return response.getWriter();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * 实现SessionAware的方法，容器调用注入Session对象
	 */
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	/**
	 * 
	 * 实现ServletRequestAware的方法，容器调用注入HttpServletResponse对象
	 */
	public void setServletRequest(HttpServletRequest request)
	{
		// 设置编码格式
		try
		{
			request.setCharacterEncoding(CHARACTER_ENCODING);

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		this.request = request;
	}

	

	/**
	 * 
	 * 实现ServletResponseAware的方法，容器调用注入Response对象
	 */
	public void setServletResponse(HttpServletResponse response)
	{
		// 设置编码格式
		response.setCharacterEncoding(CHARACTER_ENCODING);
//		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");

		this.response = response;
	}

	
}
