package com.greatfly.common.action;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.ReflectUtil;
import com.greatfly.common.util.StringUtil;
import com.greatfly.common.util.exception.AppException;


/**
 * 基础action类，建议一般的action继承本类
 * @author wuwq
 * 2010-11-15 下午03:33:22
 */
@SuppressWarnings("unchecked")
public abstract class BaseAction implements ServletRequestAware, ServletResponseAware {
    
    private static Logger log = Logger.getLogger(BaseAction.class);
    /**
     * 使用Ioc方式获取request
     */
    protected HttpServletRequest request;
    /**
     * 使用Ioc方式获取response
     */
    protected HttpServletResponse response;
    /**
     * 获取当前请求的session
     * @return http session
     */
    protected HttpSession getSession() {
        return request.getSession();
    }
    
    /**
     * 初始化分页对象
     * @param <T> 分页对象泛型
     * @return 初始化的分页对象
     */
    protected <T> PageSupport<T> initPs() {
    	String param = request.getParameter("pagenum");
		int pageIndex = StringUtil.isBlank(param) ? 1 : Integer.valueOf(param) + 1;
		param = request.getParameter("pagesize");
		int pageSize = StringUtil.isBlank(param) ? 20 : Integer.valueOf(param);
		
		return new PageSupport<T>(pageIndex, pageSize);
    }
      
    /**
     * 初始化分页对象
     * @param pageIndex 请求页
     * @param pagesize  请求行数
     * @return PageSupport<T> 初始化的分页对象
     */
    protected <T> PageSupport<T> initPs(String pageIndex, String pagesize) {
        int pageIndexInt = StringUtil.isBlank(pageIndex) ? 1 : Integer.valueOf(pageIndex) + 1;
        int pageSizeInt = StringUtil.isBlank(pagesize) ? 1 : Integer.valueOf(pagesize);
        return new PageSupport<T>(pageIndexInt, pageSizeInt);
    }
    
    /**
     * 从request中获取传入参数，并封装到指定类型的对象中
     * @param <T> 泛型
     * @param cls 指定对象的class
     * @return 包含request传入参数的对象
     */
    protected <T> T getRequestVo(Class<T> cls) {
        T vo = null;
        try {
            vo = cls.newInstance();
        } catch (Exception e) {
            throw new AppException("反射获取实例异常" + cls, e);
        }
        setRequestParam(vo);
        
        return vo;
    }
    
    /**
     * 输出返回信息
     * @param csq 返回信息
     * @param log 日志
     */
    protected void output(CharSequence csq, Logger log) {
        try {
            response.setContentType("application/json");
            response.getWriter().append(csq);
        } catch (Exception e) {
            log.error("输出异常", e);
        }
    }
    
    /**
     * @Description: 输出返回信息,设置返回编码 - UTF-8
     * @param msg 返回信息
     * @param log 日志 
     * @throws
     * @author raofh
     * @date 2016-1-16
     */
    protected void outputWeb(String msg, Logger log) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(msg);
        } catch (Exception e) {
            log.error("输出异常", e);
        }
    }
    
    /**
     * 去除json中的脏字符，否则前台可能无法解析
     * @param message 待转字符串
     * @return 转码后的字符串
     */
    protected String decode4Json(String message) {
        return message.replaceAll("\"", "\\\"").replaceAll("'", "").replaceAll("\n", "");
    }
    
    // private methods
    
    /**
     * 将request请求中的参数封装到指定的对象中
     * @param vo 承接request参数的对象
     */
    private void setRequestParam(Object vo) {
        Map<String, String[]> values = request.getParameterMap();
        for (Entry<String, String[]> entry : values.entrySet()) { //遍历所有传入参数的方法名
            try {
                Field field = ReflectUtil.getFiled(vo.getClass(), entry.getKey());
                if (field != null) {
                    field.setAccessible(true);
                    field.set(vo, ReflectUtil.convert(values.get(entry.getKey())[0], field.getType()));
                }
            } catch (NoSuchFieldException e) {
                if (log.isDebugEnabled()) {
                    log.error("request参数查无实体类对应Field:" + entry.getKey(), e);
                }
            } catch (IllegalArgumentException e) {
                if (log.isDebugEnabled()) {
                    log.error("反射调用方法传参错误", e);
                }
            } catch (IllegalAccessException e) {
                if (log.isDebugEnabled()) {
                    log.error("反射设值异常", e);
                }
            }
        }
    }
    
    //getter && setter
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
  
    public void setRequest(HttpServletRequest request) {  
       this.request = request;  
    }

          
}
