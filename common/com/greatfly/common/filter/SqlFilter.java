package com.greatfly.common.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * sql过滤器，用来防止sql注入
 * @author wuwq
 * 2011-2-22 上午12:03:07
 */
@SuppressWarnings("rawtypes")
public class SqlFilter implements Filter {
	private String injStr = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|join|create|drop|; |or|-|+|,";
	
	@Override
	public void destroy() {
		//do nothing
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Iterator values = request.getParameterMap().values().iterator();
		while (values.hasNext()) {
			String[] valueArr = (String[]) values.next();
			for (String str : valueArr) {
				if (sqlInjection(str)) {
					throw new ServletException("参数不合法，发现sql注入风险！");
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		if (config.getInitParameter("keywords") != null) {
			this.injStr = config.getInitParameter("keywords");
		}
	}
	
	// private methods
	
	/**
	 * 检测是否有sql注入漏洞
	 * @param str 待测试字符串
	 * @return 如果没有漏洞，返回false；否则返回true
	 */
	private boolean sqlInjection(String str) {
		String[] injArr = injStr.split("\\|");
		for (String word : injArr) {
			if (str.toLowerCase(Locale.CHINA).indexOf(" " + word + " ") >= 0) {
				return true;
			}
		}
		return false;
	}

}
