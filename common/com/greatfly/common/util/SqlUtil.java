package com.greatfly.common.util;


/**
 * SQL工具类，用于辅助生成分页查询SQL
 * @author wuwq
 * 2011-12-26 下午06:15:56
 */
public final class SqlUtil {
	/**
	 * 防止实例化使用
	 */
	private SqlUtil() {}
	/**
	 * 将普通的查询sql封装成分页查询的sql
	 * @param sql 查询sql
	 * @param pageIndex 当前页码
	 * @param pageSize 每页行数
	 * @return 分页sql
	 */
	public static String getPageSql(String sql, int pageIndex, int pageSize) {
		int min = pageSize * (pageIndex - 1) + 1;
		int max = pageSize * pageIndex;
		sql = dealSql(sql, min, max);
		return sql;
	}
	
	// private methods
	
	/**
	 * 获取分页sql
	 * @param sql sql语句
	 * @param min 分页数据起始
	 * @param max 分页数据结束
	 * @return 分页sql
	 */
	private static String dealSql(String sql, int min, int max) {
		sql = "select * from (select rownum rn, t.* from (" + sql 
				+ ") t where rownum <= " + max + ")  where rn >= " + min;
		return sql;
	}
	
}
