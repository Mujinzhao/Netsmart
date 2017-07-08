package core.util;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




/**
 * 针对URL地址的捕获惊醒过滤操作,以及用户组的权限,页面访问资源等
 * 
 * @author msq
 * 
 */
public class UrlFilter implements Filter {
	String date = Calendar.getInstance().getTime().toLocaleString();
	// 代表是首次运行监控
	boolean beginflag=false;
	public void destroy() {
//		LogWritter.sysDebug(date + "URLFile 过滤器关闭...");
	}
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse,
			FilterChain filterchain) throws IOException, ServletException {
//		String date = Calendar.getInstance().getTime().toLocaleString();
//		HttpServletRequest request = (HttpServletRequest) servletrequest;
//		HttpServletResponse response = (HttpServletResponse) servletresponse;
//		String path = request.getContextPath();
//		String ip = request.getRemoteAddr();
//		String url = request.getRequestURI();// 取得URL路径名
//	
//		String tempurl = url.substring(url.lastIndexOf("/") + 1, url.length());
////		HttpSession httpsession = ((HttpServletRequest) servletrequest).getSession();
//		
//		if ("".equals(tempurl)) {
//			filterchain.doFilter(servletrequest, servletresponse);
//			return;
//		}
//		// 不过滤的地址列表
//		if ("filelist.jsp".equals(tempurl)
//				|| tempurl.endsWith(".action")// 代表是移动设备，则直接给予通过
//				|| tempurl.endsWith(".css")// 代表是css样式文件，则直接给予通过
//				|| tempurl.endsWith(".js")// 代表是js文件，则直接给予通过
//				|| tempurl.endsWith(".jpg")// 代表是jpg图片，则直接给予通过
//				|| tempurl.endsWith(".swf")// 代表是swf文件，则直接给予通过
//				|| tempurl.endsWith(".xlf")// 代表是xlf文件，则直接给予通过
//				|| tempurl.endsWith(".png")// 代表是png图片，则直接给予通过
//				|| tempurl.endsWith(".gif")// 代表是gif图片，则直接给予通过
//				|| tempurl.endsWith(".bmp")// 代表是bmp图片，则直接给予通过
//				|| tempurl.endsWith(".exe")// 代表是bmp图片，则直接给予通过
//				|| tempurl.endsWith(".jsp")// 代表是jsp，则直接给予通过
//				|| tempurl.endsWith(".html")// 代表是html，则直接给予通过
//				|| "testsea.jsp".equals(tempurl) // 临时
//		) {
			filterchain.doFilter(servletrequest, servletresponse);
//		} else {
//			// 获取是否是ajax请求
//			if (request.getHeader("x-requested-with") != null
//					&& request.getHeader("x-requested-with")
//							.equalsIgnoreCase("XMLHttpRequest")) {
//				// 判断session是否丢失
//					// 通过运行
//					filterchain.doFilter(servletrequest, servletresponse);
//			} else {
//					// 错误地址重定向
//					response.sendRedirect("");
//				}
//		}
//			// add by sea as 20120928 end
//
//	
			}
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
