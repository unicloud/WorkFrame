package com.greatfly.common.dao.impl;

import static com.greatfly.common.CommonConstant.PAGE_SPLIT_MAX_PAGE_SIZE;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ObjectType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.greatfly.common.dao.IBaseDao;
import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.StringUtil;


/**
 * spring封装hibernateTemplate的基础dao类</br>
 * 不支持分页的查询时，默认返回的最大记录数maxResults(1000)。这个参数可以通过setMaxResults方法进行修改，但只对当前dao起作用
 * @param <T> 泛型的具体ORM bean类
 * @param <ID> ORM bean类的主键类型
 * @author wuwq
 * 2010-11-10 下午10:26:50
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseHibernateDao<T, ID extends Serializable> implements IBaseDao<T, Serializable> {
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	protected JdbcTemplate jdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * 不支持分页时，默认返回的最大记录数
	 */
	protected int maxResults = PAGE_SPLIT_MAX_PAGE_SIZE;
	/**
	 * 实体类class
	 */
	protected Class<T> entityClass;
	
	/**
	 * 用于存储java类型与Hibernate映射类型的对应关系
	 */
	protected static final Map<Class, Type> TYPE_MAP = new HashMap<Class, Type>();
	
	static {
		TYPE_MAP.put(Long.class, new LongType());
		TYPE_MAP.put(String.class, new StringType());
		TYPE_MAP.put(Boolean.class, new BooleanType());
		TYPE_MAP.put(Double.class, new DoubleType());
		TYPE_MAP.put(Float.class, new FloatType());
		TYPE_MAP.put(Date.class, new DateType());
		TYPE_MAP.put(Integer.class, new IntegerType());
		TYPE_MAP.put(Short.class, new ShortType());
		TYPE_MAP.put(Object.class, new ObjectType());
	}
	
	// public methods
	
	@Override
	public Object aggregate(String hql) {
		Query query = getCurrentSession().createQuery(hql);
		return query.uniqueResult();
	}
	
	@Override
	public Object aggregate(String hql, List<Object> values) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		return query.uniqueResult();
	}
	
	@Override
	public Object aggregate(String hql, Map<String, Object> values) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		return query.uniqueResult();
	}
	
	@Override
	public <TT> TT aggregateBySql(String sql, Class<TT> cls) {
		return jdbcTemplate.queryForObject(sql, cls);
	}
	
	@Override
	public <TT> TT aggregateBySql(String sql, List<Object> values, Class<TT> cls) {
		return jdbcTemplate.queryForObject(sql, values.toArray(), cls);
	}

	@Override
	public <TT> TT aggregateBySql(String sql, Map<String, ?> values, Class<TT> cls) {
		return namedParameterJdbcTemplate.queryForObject(sql, values, cls);
	}
	
	@Override
	public int[] batchUpdateBySql(String[] sql) {
		return jdbcTemplate.batchUpdate(sql);
	}
	
	@Override
	public int[] batchUpdateBySql(String sql, List<Object[]> values) {
		return jdbcTemplate.batchUpdate(sql, values);
	}
	
	@Override
	public int[] batchUpdateBySql(String sql, Map[] values) {
		return namedParameterJdbcTemplate.batchUpdate(sql, values);
	}
	
	@Override
	public int[] batchUpdateBySql(String sql, SqlParameterSource[] values) {
		return namedParameterJdbcTemplate.batchUpdate(sql, values);
	}
	
	@Override
	public void clear() {
		getCurrentSession().clear();
	}
	
	@Override
	public void delete(Serializable id) {
		T t = (T) get(id);
		if (t != null) {
			delete(t);
		}
	}
	
	@Override
	public void delete(T t) {
		getCurrentSession().delete(t);
	}
	
	@Override
	public void delete(Collection<T> entities) {
		for (T t : entities) {
			getCurrentSession().delete(t);
		}
	}
	
	@Override
	public void evict() {
		evict(getEntityClass());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void evict(Class entityClass) {
		sessionFactory.evict(entityClass);
	}
	
	@Override
	public void evict(Serializable id) {
		T t = (T) get(id);
		if (t != null) {
			evict(t);
		}
	}
	
	@Override
	public void evict(T t) {
		getCurrentSession().evict(t);
	}
	
	@Override
	public int execute(String hql) {
		Query query = getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}
	
	@Override
	public int execute(String hql, List<Object> values) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		return query.executeUpdate();
	}
	
	@Override
	public int execute(String hql, Map<String, Object> values) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		return query.executeUpdate();
	}
	
	@Override
	public Object execute(String callStr, CallableStatementCallback action) {
		return jdbcTemplate.execute(callStr, action);
	}
	
	@Override
	public int executeSql(String sql) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public int executeSql(String sql, List<Object> values) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		addConditions(query, values);
		return query.executeUpdate();
	}

	@Override
	public int executeSql(String sql, Map<String, Object> values) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		addConditions(query, values);
		return query.executeUpdate();
	}
	
	@Override
	public List find(String hql) {
		Query query = getCurrentSession().createQuery(hql);
		query.setMaxResults(maxResults);
		return query.list();
	}
	
	@Override
	public List find(String hql, List<Object> values) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		query.setMaxResults(maxResults);
		return query.list();
	}
	
	@Override
	public List find(String hql, Map<String, Object> values) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		query.setMaxResults(maxResults);
		return query.list();
	}
	
	@Override
	public void find(String hql, PageSupport ps) {
		Query query = getCurrentSession().createQuery(hql);
		query.setFirstResult(ps.getHibernateSeq());
		query.setMaxResults(ps.getPageSize());
		ps.setItems(query.list());
		
		//查询总记录数
		String sql = getCountSql(hql);
		SQLQuery countQuery = getCurrentSession().createSQLQuery(sql);
		ps.setTotalRows(((BigDecimal) countQuery.uniqueResult()).intValue());
	}
	
	@Override
	public void find(String hql, List<Object> values, PageSupport ps) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		query.setFirstResult(ps.getHibernateSeq());
		query.setMaxResults(ps.getPageSize());
		ps.setItems(query.list());
		
		//查询总记录数
		String sql = getCountSql(hql);
		SQLQuery countQuery = getCurrentSession().createSQLQuery(sql);
		addConditions(countQuery, values);
		ps.setTotalRows(((BigDecimal) countQuery.uniqueResult()).intValue());
	}
	
	@Override
	public void find(String hql, Map<String, Object> values, PageSupport ps) {
		Query query = getCurrentSession().createQuery(hql);
		addConditions(query, values);
		query.setFirstResult(ps.getHibernateSeq());
		query.setMaxResults(ps.getPageSize());
		ps.setItems(query.list());
		
		//将命名参数解析为占位符参数，然后查询总记录数
		SQLQuery countQuery = getCountSqlQuery(hql, values);
		ps.setTotalRows(((BigDecimal) countQuery.uniqueResult()).intValue());
	}
	
	@Override
	public List<T> findAll() {
		Query query = getCurrentSession().createQuery(" from " + getEntityClass().getSimpleName());
		query.setMaxResults(maxResults);
		return query.list();
	}
	
	@Override
	public List findBySql(String sql, Class cls) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		addScalar(sqlQuery, cls);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(cls));
		return sqlQuery.list();
	}
	
	@Override
	public List findBySql(String sql, List<Object> values, Class cls) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		addScalar(sqlQuery, cls);
		addConditions(sqlQuery, values);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(cls));
		return sqlQuery.list();
	}
	
	@Override
	public List findBySql(String sql, Map<String, Object> values, Class cls) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		addScalar(sqlQuery, cls);
		addConditions(sqlQuery, values);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(cls));
		return sqlQuery.list();
	}
	
	@Override
	public void findBySql(String sql, PageSupport ps, Class cls) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		addScalar(sqlQuery, cls);
		sqlQuery.setFirstResult(ps.getHibernateSeq());
		sqlQuery.setMaxResults(ps.getPageSize());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(cls));
		ps.setItems(sqlQuery.list());
		
		//查询总记录数
		sql = " select count(*) from (" + sql + " ) ";
		SQLQuery countQuery = getCurrentSession().createSQLQuery(sql);
		ps.setTotalRows(((BigDecimal) countQuery.uniqueResult()).intValue());
	}
	
	@Override
	public void findBySql(String sql, List<Object> values, PageSupport ps, Class cls) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		addScalar(sqlQuery, cls);
		addConditions(sqlQuery, values);
		sqlQuery.setFirstResult(ps.getHibernateSeq());
		sqlQuery.setMaxResults(ps.getPageSize());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(cls));
		ps.setItems(sqlQuery.list());
		
		//查询总记录数
		sql = " select count(*) from (" + sql + " ) ";
		SQLQuery countQuery = getCurrentSession().createSQLQuery(sql);
		addConditions(countQuery, values);
		ps.setTotalRows(((BigDecimal) countQuery.uniqueResult()).intValue());
	}
	
	@Override
	public void findBySql(String sql, Map<String, Object> values, PageSupport ps, Class cls) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		addScalar(sqlQuery, cls);
		addConditions(sqlQuery, values);
		sqlQuery.setFirstResult(ps.getHibernateSeq());
		sqlQuery.setMaxResults(ps.getPageSize());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(cls));
		ps.setItems(sqlQuery.list());
		
		//查询总记录数
		sql = " select count(*) from (" + sql + " ) ";
		SQLQuery countQuery = getCurrentSession().createSQLQuery(sql);
		addConditions(countQuery, values);
		ps.setTotalRows(((BigDecimal) countQuery.uniqueResult()).intValue());	// 仅获取第一条结果
	}
	
	@Override
	public void flush() {
		getCurrentSession().flush();
	}
	
	@Override
	public T get(Serializable id) {
		return (T) getCurrentSession().get(getEntityClass(), id);
	}
	
	@Override
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public int getMaxResults() {
		return maxResults;
	}
	
	@Override
	public Serializable insert(T t) {
		return (ID) getCurrentSession().save(t);
	}
	
	@Override
	public T load(Serializable id) {
		return (T) getCurrentSession().load(getEntityClass(), id);
	}
	
	@Override
	public void save(T t) {
		getCurrentSession().save(t);
	}
	
	@Override
	public void saveOrUpdate(T t) {
		getCurrentSession().saveOrUpdate(t);
	}
	
	@Override
	public void saveOrUpdate(Collection<T> entities) {
		for (T t : entities) {
			getCurrentSession().saveOrUpdate(t);
		}
	}
	
	@Override
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	@Override
	public void update(T t) {
		getCurrentSession().update(t);
	}
	
	// protected methods
	
	/**
	 * 取得泛型后的具体Dao类
	 * @return 泛型后的具体Dao类
	 */
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}
	
	// private methods
	
	/**
	 * 给查询hql增加查询条件,这里是?替代的参数
	 * @param query 查询hql
	 * @param values 查询条件集合
	 */
	private void addConditions(Query query, List<Object> values) {
		for (int i = 0; i < values.size(); i++) {
			Object obj = values.get(i);
			if (obj instanceof List) { //处理in子句的查询参数
				query.setParameter(i, (List) obj);
			} else {
				query.setParameter(i, obj);
			}
		}
	}
	
	/**
	 * 给查询hql增加查询条件,这里是命名参数
	 * @param query 查询hql
	 * @param values 查询条件集合
	 */
	private void addConditions(Query query, Map<String, Object> values) {
		for (Entry<String, Object> entry : values.entrySet()) {
			Object obj = entry.getValue();
			if (obj instanceof List) { //处理in子句的查询参数
				query.setParameterList(entry.getKey(), (List) obj);
			} else {
				query.setParameter(entry.getKey(), obj);
			}
		}
	}
	
	/**
	 * 添加声明标量查询
	 * @param sqlQuery SqlQuery
	 * @param cls 查询要转换的类
	 */
	private void addScalar(SQLQuery sqlQuery, Class cls) {
		Field[] fields = cls.getDeclaredFields();		// 返回Class中所有的字段，包括私有字段
		for (Field field : fields) {
			field.setAccessible(true);
			sqlQuery.addScalar(field.getName(), TYPE_MAP.get(field.getType()));
		}
		
		// 返回表示这个类所代表的实体（类，接口，基本数据类型或void）的超类
		if (cls.getSuperclass() != Object.class) { //迭代		
			addScalar(sqlQuery, cls.getSuperclass());
		}
	}
	
	/**
	 * 将查询hql解析为sql，解析后不论原Hql是带占位符参数还是命名参数、都会被解析为占位符参数的Sql</br>
	 * @param hql 查询hql
	 * @return 返回解析后的sql
	 */
	private String getCountSql(String hql) {
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP, (SessionFactoryImplementor) sessionFactory);
		queryTranslator.compile(Collections.EMPTY_MAP, false);
		return "select count(*) from (" + queryTranslator.getSQLString() + ") tmp_count_t";
	}
	
	/**
	 * 根据带命名参数的hql，解析成已经传入查询参数的SQLQuery
	 * @param hql 带命名参数的hql
	 * @param values 查询条件
	 * @return SQLQuery
	 */
	private SQLQuery getCountSqlQuery(String hql, Map values) {
		List aList = new ArrayList(); //查询占位符
		hql = dealHql(hql, aList, values);
		String countSql = getCountSql(hql);
		SQLQuery query = getCurrentSession().createSQLQuery(countSql);
		addConditions(query, aList);
		return query;
	}
	
	/**
	 * 迭代解析hql，将其中的命名参数解析为占位符参数，并增加查询参数
	 * @param hql hql
	 * @param aList 解析后的占位符式的查询参数
	 * @param values 命名参数的参数
	 * @return 解析后的hql
	 */
	private String dealHql(String hql, List aList, Map values) {
		String nameParam = getNameParam(hql);
		if (StringUtil.isBlank(nameParam)) {
			return hql;
		}
		int begin = hql.indexOf(':');
		int end = hql.indexOf(":" + nameParam) + nameParam.length() + 1;
		hql = hql.substring(0, begin) + "?" + hql.substring(end);
		aList.add(values.get(nameParam));
		//迭代
		return dealHql(hql, aList, values);
	}
	
	/**
	 * 获取hql中的命名参数，如果没有返回null
	 * @param hql hql
	 * @return 返回命名参数
	 */
	private String getNameParam(String hql) {
		int begin = hql.indexOf(':');
		if (begin == -1) { //不存在命名参数
			return null;
		}
		StringBuffer name = new StringBuffer();
		String tmp = hql.substring(begin + 1); //不包含命名参数的冒号
		char[] arr = tmp.toCharArray();
		for (char item : arr) {
			if (Character.isAlphabetic(item)) {
				name.append(item);
			} else {
				break;
			}
		}
		
		return name.toString();
	}
	
}
