package com.greatfly.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Resource;

/**
 * 实体类自定义注解，用于支持模型驱动开发
 * @author wuwq
 * 2013-5-6 下午04:01:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Resource
public @interface Domain {
	/**
	 * 实体对象名
	 * @return String 字符串
	 */
	String value() default "";
}
