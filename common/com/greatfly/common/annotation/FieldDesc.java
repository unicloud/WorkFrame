package com.greatfly.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Resource;

import com.greatfly.common.enums.LocaleEnum;


/**
 * 属性描述注解，用于配合ReflectUtil解析文本文件
 * @see ReflectUtil
 * @author wuwq
 * 2014-3-27 下午03:41:56
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Resource
public @interface FieldDesc {
	/**
	 * 属性开始位置
	 * @return int 开始值
	 */
	int begin();
	/**
	 * 属性结束位置
	 * @return int 结束值
	 */
	int end();
	/**
	 * 日期格式，对于Date型的数据要设置，默认yyyyMMdd
	 * @return String 字符串
	 */
	String dateFormat() default "yyyyMMdd";
	/**
	 * 指定日期转型使用的Locale枚举类值
	 * @return LocaleEnum 枚举类型
	 */
	LocaleEnum locale() default LocaleEnum.NULL;
}
