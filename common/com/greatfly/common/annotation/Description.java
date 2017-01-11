package com.greatfly.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Resource;

/**
 * Action描述标签,用于配合Struts2拦截器实现操作日志记录
 * @see RequestLogInterceptor
 * @author wuwq
 * 2015-8-5 下午01:35:59
 */
@Target({ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Resource
public @interface Description {
	/**
	 * Action描述
	 * @return String 字符串
	 */
	String value() default "";
}
