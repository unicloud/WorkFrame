package com.greatfly.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet请求util
 * @author wuwq
 * 2014-10-9 下午02:18:33
 */
public final class ServletRequestUtil {
	/**
	 * 防止实例化使用
	 */
	private ServletRequestUtil() {}
	/**
	 * 获取客户端真实的IP
	 * @param request 客户端请求
	 * @return 真实IP
	 */
	public static String getClientIp(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) { //非代理
			return request.getRemoteAddr();
		}
		
		return request.getHeader("x-forwarded-for");
	}
}
