package com.greatfly.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.greatfly.common.dao.IBaseDao;
import com.greatfly.common.service.IBaseService;
import com.greatfly.common.util.PageSupport;


/**
 * 业务逻辑处理基础服务类，建议一般服务类继承此类
 * 继承时需对baseDao进行注入
 * @param <T> 泛型的具体ORM bean类
 * @param <ID> ORM bean类的主键类型
 * @author wuwq
 * 2010-11-15 下午04:26:29
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseService<T, ID extends Serializable> implements IBaseService<T, Serializable> {
	
	protected IBaseDao baseDao;

	@Override
	public Object aggregate(String hql) {
		return baseDao.aggregate(hql);
	}

	@Override
	public Object aggregate(String hql, List<Object> values) {
		return baseDao.aggregate(hql, values);
	}

	@Override
	public Object aggregate(String hql, Map<String, Object> values) {
		return baseDao.aggregate(hql, values);
	}
	
	@Override
	public <TT> TT aggregateBySql(String sql, Class<TT> cls) {
		return (TT) baseDao.aggregateBySql(sql, cls);
	}
	
	@Override
	public <TT> TT aggregateBySql(String sql, List<Object> values, Class<TT> cls) {
		return (TT) baseDao.aggregateBySql(sql, values, cls);
	}
	
	@Override
	public <TT> TT aggregateBySql(String sql, Map<String, ?> values, Class<TT> cls) {
		return (TT) baseDao.aggregateBySql(sql, values, cls);
	}
	
	@Override
	public int[] batchUpdateBySql(String[] sql) {
		return baseDao.batchUpdateBySql(sql);
	}
	
	@Override
	public int[] batchUpdateBySql(String sql, List<Object[]> values) {
		return baseDao.batchUpdateBySql(sql, values);
	}
	
	@Override
	public int[] batchUpdateBySql(String sql, Map[] values) {
		return baseDao.batchUpdateBySql(sql, values);
	}
	
	@Override
	public int[] batchUpdateBySql(String sql, SqlParameterSource[] values) {
		return baseDao.batchUpdateBySql(sql, values);
	}

	@Override
	public void clear() {
		baseDao.clear();
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public void delete(T t) {
		baseDao.delete(t);
	}

	@Override
	public void delete(Collection<T> entities) {
		baseDao.delete(entities);
	}

	@Override
	public void evict() {
		baseDao.evict();
	}

	@Override
	public void evict(Class entityClass) {
		baseDao.evict(entityClass);
	}

	@Override
	public void evict(Serializable id) {
		baseDao.evict(id);
	}

	@Override
	public void evict(T t) {
		baseDao.evict(t);
	}
	
	@Override
	public Object execute(String callStr, CallableStatementCallback action) {
		return baseDao.execute(callStr, action);
	}
	
	@Override
	public int execute(String hql) {
		return baseDao.execute(hql);
	}

	@Override
	public int execute(String hql, List<Object> values) {
		return baseDao.execute(hql, values);
	}

	@Override
	public int execute(String hql, Map<String, Object> values) {
		return baseDao.execute(hql, values);
	}
	
	@Override
	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}

	@Override
	public int executeSql(String sql, List<Object> values) {
		return baseDao.executeSql(sql, values);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> values) {
		return baseDao.executeSql(sql, values);
	}

	@Override
	public List find(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public List find(String hql, List<Object> values) {
		return baseDao.find(hql, values);
	}

	@Override
	public List find(String hql, Map<String, Object> values) {
		return baseDao.find(hql, values);
	}

	@Override
	public void find(String hql, PageSupport ps) {
		baseDao.find(hql, ps);
	}

	@Override
	public void find(String hql, List<Object> values, PageSupport ps) {
		baseDao.find(hql, values, ps);
	}

	@Override
	public void find(String hql, Map<String, Object> values, PageSupport ps) {
		baseDao.find(hql, values, ps);
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public List findBySql(String sql, Class cls) {
		return baseDao.findBySql(sql, cls);
	}

	@Override
	public List findBySql(String sql, List<Object> values, Class cls) {
		return baseDao.findBySql(sql, values, cls);
	}

	@Override
	public List findBySql(String sql, Map<String, Object> values, Class cls) {
		return baseDao.findBySql(sql, values, cls);
	}

	@Override
	public void findBySql(String sql, PageSupport ps, Class cls) {
		baseDao.findBySql(sql, ps, cls);
	}

	@Override
	public void findBySql(String sql, List<Object> values, PageSupport ps, Class cls) {
		baseDao.findBySql(sql, values, ps, cls);
	}

	@Override
	public void findBySql(String sql, Map<String, Object> values, PageSupport ps, Class cls) {
		baseDao.findBySql(sql, values, ps, cls);
	}

	@Override
	public void flush() {
		baseDao.flush();
	}

	@Override
	public T get(Serializable id) {
		return (T) baseDao.get(id);
	}
	
	@Override
	public Session getCurrentSession() {
		return baseDao.getCurrentSession();
	}

	@Override
	public int getMaxResults() {
		return baseDao.getMaxResults();
	}

	@Override
	public Serializable insert(T t) {
		return (ID) baseDao.insert(t);
	}

	@Override
	public T load(Serializable id) {
		return (T) baseDao.load(id);
	}

	@Override
	public void save(T t) {
		baseDao.save(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		baseDao.saveOrUpdate(t);
	}

	@Override
	public void saveOrUpdate(Collection<T> entities) {
		baseDao.saveOrUpdate(entities);
	}

	@Override
	public void setMaxResults(int maxResults) {
		baseDao.setMaxResults(maxResults);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}
	
}
