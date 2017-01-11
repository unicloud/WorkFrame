package com.greatfly.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 编码过滤器
 * @author wuwq
 * Jul 27, 2010   4:15:11 PM
 */
public class EncodingFilter implements Filter {
	protected String encoding = null;
	
	@Override
	public void destroy() {
		this.encoding = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(encoding);
		}
		if (response.getCharacterEncoding() == null || !response.getCharacterEncoding().equals(encoding)) {
			response.setCharacterEncoding(encoding);
		}
		//将过滤器的控制向下传递
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.encoding = config.getInitParameter("encoding");
	}

}
