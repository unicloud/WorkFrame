package com.greatfly.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.greatfly.common.dao.IJdbcBaseDao;
import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.SqlUtil;


/**
 * jdbc基础dao实现类，主要是对jdbcTemplate进行封装，扩展分页查询功能</br> 使用时需对jdbcTemplate进行注入
 * 
 * @author wuwq 2011-9-3 下午12:53:03
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class JdbcBaseDao implements IJdbcBaseDao {
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int[] batchUpdate(String[] sql) {
		return getJdbcTemplate().batchUpdate(sql);
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> values) {
		return jdbcTemplate.batchUpdate(sql, values);
	}

	@Override
	public int[] batchUpdate(String sql, Map[] values) {
		return namedParameterJdbcTemplate.batchUpdate(sql, values);
	}

	@Override
	public Object execute(String callStr, CallableStatementCallback action) {
		return getJdbcTemplate().execute(callStr, action);
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public List query(String sql, Object[] values, RowMapper rowMapper) {
		return getJdbcTemplate().query(sql, values, rowMapper);
	}

	@Override
	public List query(String sql, Object[] values, RowMapper rowMapper, int pageIndex, int pageSize) {
		sql = SqlUtil.getPageSql(sql, pageIndex, pageSize);
		return getJdbcTemplate().query(sql, values, rowMapper);
	}

	@Override
	public List query(String sql, RowMapper rowMapper) {
		return getJdbcTemplate().query(sql, rowMapper);
	}

	@Override
	public List query(String sql, RowMapper rowMapper, int pageIndex, int pageSize) {
		sql = SqlUtil.getPageSql(sql, pageIndex, pageSize);
		return getJdbcTemplate().query(sql, rowMapper);
	}

	@Override
	public int queryForInt(String sql) {
		return getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	@Override
	public int queryForInt(String sql, Object[] values) {
		return getJdbcTemplate().queryForObject(sql, values, Integer.class);
	}

	@Override
	public long queryForLong(String sql) {
		return getJdbcTemplate().queryForObject(sql, Long.class);
	}

	@Override
	public long queryForLong(String sql, Object[] values) {
		return getJdbcTemplate().queryForObject(sql, values, Long.class);
	}

	@Override
	public Map queryForMap(String sql) {
		return getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public Map queryForMap(String sql, Object[] values) {
		return getJdbcTemplate().queryForMap(sql, values);
	}

	@Override
	public Object queryForObject(String sql, Class cls) {
		return getJdbcTemplate().queryForObject(sql, cls);
	}

	@Override
	public Object queryForObject(String sql, Object[] values, Class cls) {
		return getJdbcTemplate().queryForObject(sql, values, cls);
	}

	@Override
	public int update(String sql) {
		return getJdbcTemplate().update(sql);
	}

	@Override
	public int update(String sql, Object[] values) {
		return getJdbcTemplate().update(sql, values);
	}

	@Override
	public List query(String sql, List values, RowMapper rowMapper) {
		return jdbcTemplate.query(sql, values.toArray(), rowMapper);
	}

	@Override
	public List query(String sql, Map<String, ?> values, RowMapper rowMapper) {
		return namedParameterJdbcTemplate.query(sql, values, rowMapper);
	}

	@Override
	public List query(String sql, List values, RowMapper rowMapper, int pageIndex, int pageSize) {
		sql = SqlUtil.getPageSql(sql, pageIndex, pageSize);
		return jdbcTemplate.query(sql, values.toArray(), rowMapper);
	}

	@Override
	public List query(String sql, Map<String, ?> values, RowMapper rowMapper, int pageIndex, int pageSize) {
		sql = SqlUtil.getPageSql(sql, pageIndex, pageSize);
		return namedParameterJdbcTemplate.query(sql, values, rowMapper);
	}

	@Override
	public void query(String sql, RowMapper rowMapper, PageSupport ps) {
		int totalRows = jdbcTemplate.queryForObject(getCountSql(sql), Integer.class);
		ps.setTotalRows(totalRows);
		sql = SqlUtil.getPageSql(sql, ps.getPageIndex(), ps.getPageSize());
		List items = getJdbcTemplate().query(sql, rowMapper);
		ps.setItems(items);
	}

	@Override
	public void query(String sql, List values, RowMapper rowMapper, PageSupport ps) {
		int totalRows = queryForInt(getCountSql(sql), values);
		ps.setTotalRows(totalRows);
		sql = SqlUtil.getPageSql(sql, ps.getPageIndex(), ps.getPageSize());
		List items = jdbcTemplate.query(sql, values.toArray(), rowMapper);
		ps.setItems(items);
	}

	@Override
	public void query(String sql, Map<String, ?> values, RowMapper rowMapper, PageSupport ps) {
		int totalRows = queryForInt(getCountSql(sql), values);
		ps.setTotalRows(totalRows);
		sql = SqlUtil.getPageSql(sql, ps.getPageIndex(), ps.getPageSize());
		List items = namedParameterJdbcTemplate.query(sql, values, rowMapper);
		ps.setItems(items);
	}

	@Override
	public int queryForInt(String sql, List values) {
		return jdbcTemplate.queryForObject(sql, values.toArray(), Integer.class);
	}

	@Override
	public int queryForInt(String sql, Map<String, ?> values) {
		return namedParameterJdbcTemplate.queryForObject(sql, values, Integer.class);
	}

	@Override
	public long queryForLong(String sql, List values) {
		return jdbcTemplate.queryForObject(sql, values.toArray(), Long.class);
	}

	@Override
	public long queryForLong(String sql, Map<String, ?> values) {
		return namedParameterJdbcTemplate.queryForObject(sql, values, Long.class);
	}

	@Override
	public Map queryForMap(String sql, List values) {
		return jdbcTemplate.queryForMap(sql, values.toArray());
	}

	@Override
	public Map queryForMap(String sql, Map<String, ?> values) {
		return namedParameterJdbcTemplate.queryForMap(sql, values);
	}

	@Override
	public List queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List queryForList(String sql, List values) {
		return jdbcTemplate.queryForList(sql, values.toArray());
	}

	@Override
	public List queryForList(String sql, Map<String, ?> values) {
		return namedParameterJdbcTemplate.queryForList(sql, values);
	}

	@Override
	public void queryForList(String sql, Map<String, ?> values, PageSupport ps) {
		int totalRows = queryForInt(getCountSql(sql), values);
		ps.setTotalRows(totalRows);
		sql = SqlUtil.getPageSql(sql, ps.getPageIndex(), ps.getPageSize());
		List<Map<String, Object>> items = namedParameterJdbcTemplate.queryForList(sql, values);
		ps.setItems(items);
	}

	@Override
	public Object queryForObject(String sql, List values, Class cls) {
		return jdbcTemplate.queryForObject(sql, values.toArray(), cls);
	}

	@Override
	public Object queryForObject(String sql, Map<String, ?> values, Class cls) {
		return namedParameterJdbcTemplate.queryForObject(sql, values, cls);
	}

	@Override
	public int update(String sql, List values) {
		return jdbcTemplate.update(sql, values.toArray());
	}

	@Override
	public int update(String sql, Map<String, ?> values) {
		return namedParameterJdbcTemplate.update(sql, values);
	}

	// private methods

	/**
	 * 获取查询总记录的sql
	 * 
	 * @param sql
	 *            原始查询sql
	 * @return 查询总记录数sql
	 */
	private String getCountSql(String sql) {
		return "select count(1) from (" + sql + ")";
	}

	// setter

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// {{ raofh 添加方法 -- 20160107

	@Override
	public long getTableSequence(String tname) {
		// String sql = "select SEQ_" + tname + ".nextval FROM DUAL";
		String sql = "select SEQ_COMM_PKID.nextval  from dual";
		return  queryForLong(sql);
	}
	
 

	// }} raofh 添加方法 -- 20160107

	@Override
	public Object execute(String sql, PreparedStatementCallback action) {
		return getJdbcTemplate().execute(sql, action);
	}

	@Override
	public Object execute(CallableStatementCreator callableStatementCreator, CallableStatementCallback action) {
		return getJdbcTemplate().execute(callableStatementCreator, action);
	}

}
