package com.greatfly.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 业务逻辑层util
 * @author wuwq
 * 2014-1-13 下午04:00:03
 */
public final class ServiceUtil {
	/**
	 * 防止实例化使用
	 */
	private ServiceUtil() {}
	/**
	 * 将以,隔开的字符串转成泛型为Long的List，一般用于转换前台传入的主键字符串
	 * @param ids 待转换的字符串
	 * @return Long集合
	 */
	public static List<Long> tran2LongList(String ids) {
		if (StringUtil.isBlank(ids)) {
			return null;
		}
		
		//如果以,结尾，则去除结尾的,
		int lastPosition = ids.length() - 1;
		if (ids.lastIndexOf(',') == lastPosition) {
			ids = ids.substring(0, lastPosition);
		}
		
		//防止方法参数为","时，直接split会返回size为1、元素为""的id数组
		if (StringUtil.isBlank(ids)) {
			return null;
		}
		
		String[] idArr = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (String idStr : idArr) {
			idList.add(Long.valueOf(idStr));
		}
		return idList;
	}
	
	/**
	 * 将以,隔开的字符串转成泛型为String的List
	 * @param ids 待转换的字符串
	 * @return String集合
	 */
	public static List<String> tran2StrList(String ids) {
		if (StringUtil.isBlank(ids)) {
			return null;
		}
		
		//如果以,结尾，则去除结尾的,
		int lastPosition = ids.length() - 1;
		if (ids.lastIndexOf(',') == lastPosition) {
			ids = ids.substring(0, lastPosition);
		}
		
		//防止方法参数为","时，直接split会返回size为1、元素为""的id数组
		if (StringUtil.isBlank(ids)) {
			return null;
		}
		
		String[] idArr = ids.split(",");
		return Arrays.asList(idArr);
	}
	
	/**
	 * 根据符号拆分字符串
	 * @param str 待拆分字符串
	 * @param symbol 用于拆分的符号
	 * @return 字符串List
	 */
	public static List<String> spliteStrToList(String str, String symbol) {
		String[] values = str.split(symbol);
		return Arrays.asList(values);
	}
	
	/**
	 * 把part拼接到hql中
	 * @param part hql段
	 * @param hql hql
	 */
	public static void setHql(String part, StringBuffer... hql) {
		for (StringBuffer strBuffer : hql) {
			strBuffer.append(part);
		}
	}
}
