package com.greatfly.common;


import java.util.Locale;

/**
 * 框架级全局常量
 * @author xiaoxiang
 * 2013-5-5 上午08:55:42
 */
public final class CommonConstant {
	/**
	 * 默认编码
	 */
	public static final String CHARSET_NAME = "UTF-8";
	/**
	 * 描述：未知
	 */
	public static final String UNKNOWN = "未知";
	/**
	 * 默认Locale
	 */
	public static final Locale DEFAULT_LOCALE = Locale.CHINA;
	/** sql时间每日开始 */
	public static final String SQL_TIME_BEGIN = " 00:00:00";
	/** sql时间每日结束 */
	public static final String SQL_TIME_END = " 23:59:59";
	
	/**
	 * 默认分页大小
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/**
	 * 最大分页记录数
	 */
	public static final int PAGE_SPLIT_MAX_PAGE_SIZE = 1000;
	/**
	 * 系统，用于自动作业等用到的用户名、工号等参数
	 */
	public static final String SYSM = "SYSM";

	/**
	 * session中当前登陆用户的key
	 */
	public static final String CUR_USER = "CUR_USER";
	
	/**
	 * 系统中使用的默认分隔符,默认值: ,
	 */
	public static final String SPLITSEPARATOR = ",";
	
	/**
	 * 防止实例化使用
	 */
	private CommonConstant() { }
}
