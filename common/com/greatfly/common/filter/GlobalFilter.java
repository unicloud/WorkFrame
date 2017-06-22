package com.greatfly.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.greatfly.common.CommonConstant;
import com.greatfly.common.util.GlobalUtil;
import com.greatfly.common.util.exception.AppException;
import com.greatfly.common.vo.UserVo;


/**
 * 全局过滤器，用于与GlobalUtil配合，在线程上设置当前登录人信息
 * @author wuwq
 * 2013-2-8 上午09:27:20
 */
public class GlobalFilter implements Filter {

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
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		Object userVo = servletRequest.getSession().getAttribute(CommonConstant.CUR_USER);
		if (userVo == null) {
			GlobalUtil.setUser(null);
		} else {
			GlobalUtil.setUser((UserVo) userVo);
		}
		//将过滤器的控制向下传递
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//do nothing
	}

}
