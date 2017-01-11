package com.greatfly.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.greatfly.common.util.exception.AppException;


/**
 * Http请求方法拦截器，用于防止开启不安全Http方法
 * @author wuwq
 * 2014-2-14 下午03:09:54
 */
public class HttpMethodFilter implements Filter {
    
    /**
     * 允许的http请求方法字符串，以|隔开
     */
    private final static String[] ALLOW_ARR = new String[]{"POST", "GET"};
    
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
        String method = servletRequest.getMethod();
        if (!isAllowMethod(method)) {
            throw new ServletException("发现不允许的Http请求方法" + method);
        }
        //将过滤器的控制向下传递
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }
    
    // private methods
    
    /**
     * 判断是否是允许的Http请求方法
     * @param method 请求方法
     * @return 是否是允许的Http请求方法
     */
    private boolean isAllowMethod(String method) {
        for (String allowMethod : ALLOW_ARR) {
            if (allowMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
    
}
