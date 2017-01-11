package com.greatfly.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.greatfly.common.http.XssHttpServletRequestWrapper;
import com.greatfly.common.util.exception.AppException;


/**
 * XSS漏洞过滤器
 * @author wuwq
 * 2014-2-14 下午03:48:34
 */
public class XssFilter implements Filter {

	@Override
	public void destroy() {
		//do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request == null) {
			throw new AppException("request为null");
		}
		if (!(request instanceof HttpServletRequest)) {
			throw new AppException("request非HttpServletRequest实例");
		}
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		//do nothing
	}

}
