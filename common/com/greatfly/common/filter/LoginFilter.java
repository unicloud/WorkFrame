package com.greatfly.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.greatfly.common.CommonConstant;
import com.greatfly.common.util.exception.AppException;
import com.greatfly.common.util.JsonUtil;
import com.greatfly.common.vo.RespVo;

/**
 * 登陆过滤器，对session超时或者未登录的请求，全部跳转到登陆页面</br>
 * 对于ajax请求，在页面中需要判断logout是否为true，为true的时候要跳转到登陆页面
 * @author wuwq
 * 2010-11-9 下午04:26:39
 */
public class LoginFilter implements Filter {
    protected String defaultPage = null;

    @Override
    public void destroy() {
        this.defaultPage = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request == null) {
            throw new AppException("request为null");
        }
        if (!(request instanceof HttpServletRequest)) {
            throw new AppException("request非HttpServletRequest实例");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        
        if (response == null) {
            throw new AppException("response为null");
        }
        if (!(response instanceof HttpServletResponse)) {
            throw new AppException("response非HttpServletResponse实例");
        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //判断是否是登录状态，非登录状态跳转到登录页面
        if (notLogin(session) && isFilter(httpRequest)) {
            if (isAjaxRequest(httpRequest)) { //ajax请求
                RespVo vo = new RespVo();
                vo.setTimeout(true);
                vo.setSuccess(false);
                vo.setInfo("登录超时，请重新登录!");
                response.getWriter().print(JsonUtil.toJson(vo));
            } else { //普通请求
                httpResponse.sendRedirect(httpRequest.getContextPath() + defaultPage);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.defaultPage = config.getInitParameter("defaultPage");
    }
    
    /**
     * 判断是否登陆
     * @param session 当前session
     * @return 已经登陆返回false，session超时或者未登录的返回true
     */
    private boolean notLogin(HttpSession session) {
        if (null != session.getAttribute(CommonConstant.USER_NAME)) {
            return false;
        }
        return true;
    }
    
    /**
     * 判断是否是需要过滤的请求，对于login.jsp和login.do的请求，不进行登陆过滤
     * @param request 请求
     * @return 需要进行过滤的请求返回true，否则返回false
     */
    private boolean isFilter(HttpServletRequest request) {
        String urlStr = request.getRequestURL().toString(); 
        if (urlStr.indexOf("login.jsp") >= 0 || urlStr.indexOf("login.do") >= 0 || urlStr.indexOf("Login.jpg") >= 0) {
            return false;
        }
        if (urlStr.endsWith("layer.js") || urlStr.endsWith("layer.css") || urlStr.endsWith("finance_rams.ico")) {
        	return false;
        }
        //登录页面需要加载这个js,故不作过滤
        if (urlStr.indexOf("jquery-1.11.1.min") >= 0) {
        	return false;
        }
        return true;
    }
    
    /**
     * 是否是ajax请求
     * @param request 请求
     * @return 如果是ajax请求，返回true；否则返回false
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With"); 
        if (null == header || !header.equals("XMLHttpRequest")) {
            return false;
        }
        return true;
    }
    
}
