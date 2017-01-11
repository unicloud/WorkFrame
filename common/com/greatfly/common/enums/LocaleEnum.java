package com.greatfly.common.enums;

import java.util.Locale;

/**
 * 日期转型枚举类，用于配合ReflectUtil进行字符串转型为对象</br>
 * 由于注解中无法直接使用java.util.Locale对象，改用该枚举类作为中转关联
 * @see ReflectUtil, FieldDesc
 * @author wuwq
 * 2014-4-3 下午01:58:31
 */
public enum LocaleEnum {
	NULL(null),
	ENGLISH(Locale.ENGLISH);
	
	/**
	 * 构造函数
	 * @param locale 本地化
	 */
	private LocaleEnum(Locale locale) {
		this.locale = locale;
	}
	
	private Locale locale;
	
	public Locale getLocale() {
		return locale;
	}
	
}
