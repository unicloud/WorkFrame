package com.greatfly.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.greatfly.common.util.PageSupport;

/**
 * jdbc基础dao的常用方法接口，支持jdbc操作，分页查询、存储过程等
 * 
 * @author wuwq 2011-9-3 下午12:01:19
 */
@SuppressWarnings("rawtypes")
public interface IJdbcBaseDao {
	/**
	 * 批量更新操作，可以批量执行insert/update/delete语句 在某些情况下，使用批量更新能获得更好的性能
	 * 
	 * @param sql
	 *            批量执行的sql，可以是insert/update/delete语句
	 * @return 受影响的行数
	 */
	public int[] batchUpdate(String[] sql);

	/**
	 * 使用sql进行批量更新
	 * 
	 * @param sql
	 *            批量更新的sql数组
	 * @param values
	 *            List查询条件
	 * @return 受影响的行数
	 */
	public int[] batchUpdate(String sql, List<Object[]> values);

	/**
	 * 使用sql进行批量更新
	 * 
	 * @param sql
	 *            批量更新的sql数组，更新条件采用命名参数
	 * @param values
	 *            Map数组的更新条件
	 * @return 受影响的行数
	 */
	public int[] batchUpdate(String sql, Map[] values);

	/**
	 * 执行存储过程
	 * 
	 * @param callStr
	 *            调用存储过程的语句
	 * @param action
	 *            设置存储过程的参数
	 * @return 执行结果
	 */
	public Object execute(String callStr, CallableStatementCallback action);

	/**
	 * 获取jdbcTemplate，在本接口中没用封装的jdbcTemplate的方法，可以通过直接获取jdbcTemplate来调用
	 * 
	 * @return jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate();

	/**
	 * 查询，无分页
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param rowMapper
	 *            结果集映射
	 * @return 查询List
	 */
	public List query(String sql, Object[] values, RowMapper rowMapper);

	/**
	 * 查询，无分页，使用?参数
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param rowMapper
	 *            结果集映射
	 * @return 查询List
	 */
	public List query(String sql, List values, RowMapper rowMapper);

	/**
	 * 查询，无分页，使用命名参数
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param rowMapper
	 *            结果集映射
	 * @return 查询List
	 */
	public List query(String sql, Map<String, ?> values, RowMapper rowMapper);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param rowMapper
	 *            结果集
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页行数
	 * @return 查询结果List
	 */
	public List query(String sql, Object[] values, RowMapper rowMapper, int pageIndex, int pageSize);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param rowMapper
	 *            结果集
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页行数
	 * @return 查询结果List
	 */
	public List query(String sql, List values, RowMapper rowMapper, int pageIndex, int pageSize);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param rowMapper
	 *            结果集
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页行数
	 * @return 查询结果List
	 */
	public List query(String sql, Map<String, ?> values, RowMapper rowMapper, int pageIndex, int pageSize);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param rowMapper
	 *            结果集
	 * @return 查询结果List
	 */
	public List query(String sql, RowMapper rowMapper);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param rowMapper
	 *            结果集
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页行数
	 * @return 查询结果List
	 */
	public List query(String sql, RowMapper rowMapper, int pageIndex, int pageSize);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param rowMapper
	 *            结果集
	 * @param ps
	 *            分页对象
	 */
	public void query(String sql, RowMapper rowMapper, PageSupport ps);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询条件
	 * @param rowMapper
	 *            结果集
	 * @param ps
	 *            分页对象
	 */
	public void query(String sql, List values, RowMapper rowMapper, PageSupport ps);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询条件
	 * @param rowMapper
	 *            结果集
	 * @param ps
	 *            分页对象
	 */
	public void query(String sql, Map<String, ?> values, RowMapper rowMapper, PageSupport ps);

	/**
	 * int数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @return 查询结果
	 */
	public int queryForInt(String sql);

	/**
	 * int数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public int queryForInt(String sql, Object[] values);

	/**
	 * int数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public int queryForInt(String sql, List values);

	/**
	 * int数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public int queryForInt(String sql, Map<String, ?> values);

	/**
	 * long数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @return 查询结果
	 */
	public long queryForLong(String sql);

	/**
	 * long数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public long queryForLong(String sql, Object[] values);

	/**
	 * long数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public long queryForLong(String sql, List values);

	/**
	 * long数据查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public long queryForLong(String sql, Map<String, ?> values);

	/**
	 * 查询，结果以map返回，其key为列名
	 * 
	 * @param sql
	 *            查询sql
	 * @return 查询结果
	 */
	public Map queryForMap(String sql);

	/**
	 * 查询，结果以map返回，其key为列名
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public Map queryForMap(String sql, Object[] values);

	/**
	 * 查询，结果以map返回，其key为列名
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public Map queryForMap(String sql, List values);

	/**
	 * 查询，结果以map返回，其key为列名
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public Map queryForMap(String sql, Map<String, ?> values);

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            查询sql
	 * @return 查询结果
	 */
	public List queryForList(String sql);

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public List queryForList(String sql, List values);

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
	public List queryForList(String sql, Map<String, ?> values);

	/**
	 * raofh 添加 - 20160107 分页查询,获取结果及总行数
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param ps
	 *            分页对象
	 */
	public void queryForList(String sql, Map<String, ?> values, PageSupport ps);

	/**
	 * object查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param cls
	 *            object类型
	 * @return 查询结果
	 */
	public Object queryForObject(String sql, Class cls);

	/**
	 * object查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param cls
	 *            object类型
	 * @return 查询结果
	 */
	public Object queryForObject(String sql, Object[] values, Class cls);

	/**
	 * object查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param cls
	 *            object类型
	 * @return 查询结果
	 */
	public Object queryForObject(String sql, List values, Class cls);

	/**
	 * object查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param values
	 *            查询参数
	 * @param cls
	 *            object类型
	 * @return 查询结果
	 */
	public Object queryForObject(String sql, Map<String, ?> values, Class cls);

	/**
	 * 更新操作，可以批量执行insert/update/delete语句
	 * 
	 * @param sql
	 *            执行的sql，可以是insert/update/delete语句
	 * @return 受影响的行数
	 */
	public int update(String sql);

	/**
	 * 更新操作，可以批量执行insert/update/delete语句
	 * 
	 * @param sql
	 *            执行的sql，可以是insert/update/delete语句
	 * @param values
	 *            查询参数
	 * @return 受影响的行数
	 */
	public int update(String sql, Object[] values);

	/**
	 * 更新操作，可以批量执行insert/update/delete语句
	 * 
	 * @param sql
	 *            执行的sql，可以是insert/update/delete语句
	 * @param values
	 *            查询参数
	 * @return 受影响的行数
	 */
	public int update(String sql, List values);

	/**
	 * 更新操作，可以批量执行insert/update/delete语句
	 * 
	 * @param sql
	 *            执行的sql，可以是insert/update/delete语句
	 * @param values
	 *            查询参数
	 * @return 受影响的行数
	 */
	public int update(String sql, Map<String, ?> values);

	// {{ raofh 添加方法 -- 20160107

	/**
	 * @Description: 获取序列号,SEQUENCE
	 * @param tableName
	 *            表名
	 * @return String
	 * @throws
	 * @author raofh
	 * @date 2016-1-16
	 */
	public long getTableSequence(String tableName);

//	public long getTableSequence(String tname);
	
	
//	public List getCodeListForValue();
 
	
	// }} raofh 添加方法

	/**
	 * 执行blob，clob
	 * 
	 * @param sql
	 *            執行的sql语句
	 * @param action
	 *            设置操作blob等参数
	 * @return 执行结果
	 */
	public Object execute(String sql, PreparedStatementCallback action);

	/**
	 * 存储过程
	 * 
	 * @param callableStatementCreator
	 *            執行参数
	 * @param action
	 *            執行参数
	 * @return 執行結果
	 */
	public Object execute(CallableStatementCreator callableStatementCreator, CallableStatementCallback action);

}
