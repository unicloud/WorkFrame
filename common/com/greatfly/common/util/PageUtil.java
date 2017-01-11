package com.greatfly.common.util;

/**
 * 分页支持util
 * @author wuwq
 * 2014-8-5 上午08:19:46
 */
public final class PageUtil {
	/**
	 * 私有构造函数，防止实例化使用
	 */
	private PageUtil() {}
	
	/**
	 * 计算最大页数
	 * @param pageSize 分页大小
	 * @param totalRows 总记录数
	 * @return 最大页数
	 */
	public static int getMaxPages(int pageSize, int totalRows) {
		if (totalRows % pageSize == 0) {
        	return totalRows / pageSize;
        } else {
        	return totalRows / pageSize + 1;
        }
	}
}
