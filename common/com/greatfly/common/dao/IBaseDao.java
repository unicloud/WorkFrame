package com.greatfly.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.greatfly.common.util.PageSupport;


/**
 * 基础dao的常用方法接口，使用hibernate实现数据库操作
 * @param <T> 泛型的具体ORM bean类
 * @param <ID> ORM bean类的主键类型
 * @author wuwq
 * 2010-11-10 下午08:28:29
 */
@SuppressWarnings("rawtypes")
public interface IBaseDao<T, ID extends Serializable> {
	/**
	 * 支持sum、count等聚集函数的hql
	 * @param hql 统计的hql语句
	 * @return 返回统计数值
	 */
	public Object aggregate(String hql);
	/**
	 * 支持sum、count等聚集函数的hql
	 * @param hql 统计的hql语句
	 * @param values 查询条件
	 * @return 返回统计数值
	 */
	public Object aggregate(String hql, List<Object> values);
	/**
	 * 支持sum、count等聚集函数的hql
	 * @param hql 统计的hql语句
	 * @param values 查询条件
	 * @return 返回统计数值
	 */
	public Object aggregate(String hql, Map<String, Object> values);
	/**
	 * 支持sum、count等聚集函数的sql
	 * @param <TT> 返回类型
	 * @param sql 查询sql
	 * @param cls 返回类型class
	 * @return 返回执行结果
	 */
	public <TT> TT aggregateBySql(String sql, Class<TT> cls);
	/**
	 * 支持sum、count等聚集函数的sql
	 * @param <TT> 返回类型
	 * @param sql 查询sql
	 * @param values 查询条件
	 * @param cls 返回类型class
	 * @return 返回执行结果
	 */
	public <TT> TT aggregateBySql(String sql, List<Object> values, Class<TT> cls);
	/**
	 * 支持sum、count等聚集函数的sql
	 * @param <TT> 返回类型
	 * @param sql 查询sql
	 * @param values 查询条件
	 * @param cls 返回类型class
	 * @return 返回执行结果
	 */
	public <TT> TT aggregateBySql(String sql, Map<String, ?> values, Class<TT> cls);
	/**
	 * 使用sql进行批量更新
	 * @param sql 批量更新的sql数组
	 * @return 受影响的行数
	 */
	public int[] batchUpdateBySql(String[] sql);
	/**
	 * 使用sql进行批量更新
	 * @param sql 批量更新的sql数组
	 * @param values List查询条件
	 * @return 受影响的行数
	 */
	public int[] batchUpdateBySql(String sql, List<Object[]> values);
	/**
	 * 使用sql进行批量更新
	 * @param sql 批量更新的sql数组，更新条件采用命名参数
	 * @param values Map数组的更新条件
	 * @return 受影响的行数
	 */
	public int[] batchUpdateBySql(String sql, Map[] values);
	/**
	 * 使用sql进行批量更新
	 * @param sql 批量更新的sql数组，更新条件采用命名参数
	 * @param values SqlParameterSource数组的更新条件，相较于Map数组而言，优点在于不需要对参数进行一一赋值，
	 * 要求sql中使用的命名参数与对象属性名称一致
	 * @return 受影响的行数
	 */
	public int[] batchUpdateBySql(String sql, SqlParameterSource[] values);
	/**
	 * 清理本线程一级缓存的所有持久化对象
	 */
	public void clear();
	/**
	 * 根据指定Id删除一个ORM bean实例
	 * @param id 实体类的主键
	 */
	public void delete(ID id);
	/**
	 * 删除一个ORM bean实例
	 * @param t ORM bean实例
	 */
	public void delete(T t);
	/**
	 * 批量删除ORM bean实例集合
	 * @param entities 要删除的ORM bean集合
	 */
	public void delete(Collection<T> entities);
	/**
	 * 清理此泛型类的所有二级缓存
	 */
	public void evict();
	/**
	 * 清理指定类的所有二级缓存
	 * @param entityClass 要清理的类对象的Class
	 */
	public void evict(Class entityClass);
	/**
	 * 根据主键从二级缓存中清理一个实例
	 * @param id 实例主键
	 */
	public void evict(ID id);
	/**
	 * 从二级缓存中清理一个实例
	 * @param t 要清理的实例
	 */
	public void evict(T t);
	/**
	 * 执行数据操纵hql（DML），如insert、update、delete等
	 * @param hql hql
	 * @return 受影响的行数
	 */
	public int execute(String hql);
	/**
	 * 执行数据操纵hql（DML），如insert、update、delete等
	 * @param hql hql
	 * @param values 参数List，对应hql中用？代替的参数
	 * @return 受影响的行数
	 */
	public int execute(String hql, List<Object> values);
	/**
	 * 执行数据操纵hql（DML），如insert、update、delete等
	 * @param hql hql
	 * @param values 参数Map，key为命名参数，value为参数值
	 * @return 受影响的行数
	 */
	public int execute(String hql, Map<String, Object> values);
	/**
	 * 执行存储过程
	 * @param callStr 调用存储过程的语句
	 * @param action 设置存储过程的参数
	 * @return 执行结果
	 */
	public Object execute(String callStr, CallableStatementCallback action);
	/**
	 * 执行数据操纵sql（DML），如insert、update、delete等
	 * @param sql sql
	 * @return 受影响的行数
	 */
	public int executeSql(String sql);
	/**
	 * 执行数据操纵sql（DML），如insert、update、delete等
	 * @param sql sql
	 * @param values 参数List，对应hql中用？代替的参数
	 * @return 受影响的行数
	 */
	public int executeSql(String sql, List<Object> values);
	/**
	 * 执行数据操纵sql（DML），如insert、update、delete等
	 * @param sql sql
	 * @param values 参数Map，key为命名参数，value为参数值
	 * @return 受影响的行数
	 */
	public int executeSql(String sql, Map<String, Object> values);
	/**
	 * （不分页）根据hql查询数据对象集合
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>如果包含查询参数，请使用同名包含查询条件的方法，否则可能存在sql注入的漏洞</li>
	 *   <li>默认返回的最大记录数为MAX_RESULTS的值</li>
	 *   <li>如果待查询数据预估超过该最大记录数时，建议使用分页查询</li>
	 *   <li>如果查询的是实体类，则hql可类似于如下：from 实体类 where 条件</li>
	 *   <li>如果查询的是dto，则hql可类似如下：select new 完整包.类名（查询的字段按照构造函数的属性顺序依次排出） from 对象名 where 条件</li>
	 * </ol>
	 * @param hql hql语句
	 * @return 符合条件的实例集合
	 */
	public List find(String hql);
	/**
	 * （不分页）根据hql查询实例集合，参数使用?代替
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>默认返回的最大记录数为MAX_RESULTS的值</li>
	 *   <li>如果待查询数据预估超过该最大记录数时，建议使用分页查询</li>
	 *   <li>如果查询的是dto，则hql可类似如下：select new 完整包.类名（查询的字段按照构造函数的属性顺序依次排出） from 对象名 where 条件</li>
	 * </ol>
	 * @param hql hql语句
	 * @param values 参数List，对应hql中用？代替的参数
	 * @return 符合条件的实例集合
	 */
	public List find(String hql, List<Object> values);
	/**
	 * （不分页）根据hql查询实例集合，参数使用:name代替
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>默认返回的最大记录数为MAX_RESULTS的值</li>
	 *   <li>如果待查询数据预估超过该最大记录数时，建议使用分页查询</li>
	 *   <li>如果查询的是dto，则hql可类似如下：select new 完整包.类名（查询的字段按照构造函数的属性顺序依次排出） from 对象名 where 条件</li>
	 * </ol>
	 * @param hql hql语句
	 * @param values 参数Map，key为命名参数，value为参数值
	 * @return 符合条件的实例集合
	 */
	public List find(String hql, Map<String, Object> values);
	/**
	 * 根据hql进行分页查询，执行后会将数据集、总条数等信息填充到PageSupport对象中</br>
	 * 如果包含查询参数，请使用同名包含查询条件的方法，否则可能存在sql注入的漏洞
	 * @param hql hql语句
	 * @param ps 分页对象，一般只有当前页码等基本参数，没有分页数据集
	 */
	public void find(String hql, PageSupport ps);
	/**
	 * 根据hql进行分页查询，执行后会将数据集、总条数等信息填充到PageSupport对象中，参数使用？代替
	 * @param hql hql语句
	 * @param ps 分页对象，一般只有当前页码等基本参数，没有分页数据集
	 * @param values 参数List，对应hql中用？代替的参数
	 */
	public void find(String hql, List<Object> values, PageSupport ps);
	/**
	 * 根据hql进行分页查询，执行后会将数据集、总条数等信息填充到PageSupport对象中，参数使用：name代替
	 * @param hql hql语句
	 * @param ps 分页对象，一般只有当前页码等基本参数，没有分页数据集
	 * @param values 参数Map，key为命名参数，value为参数值
	 */
	public void find(String hql, Map<String, Object> values, PageSupport ps);
	/**
	 * （不分页）列出整张表的ORM bean，默认返回最大记录数是getNoSplitPageMaxRows()的值
	 * @return ORM bean List
	 */
	public List<T> findAll();
	/**
	 * （不分页）根据sql查询数据对象集合
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>一般地，建议使用find方法通过hql进行查询</li>
	 *   <li>如果包含查询参数，请使用同名包含查询条件的方法，否则可能存在sql注入的漏洞</li>
	 *   <li>默认返回的最大记录数为MAX_RESULTS的值</li>
	 *   <li>如果待查询数据预估超过该最大记录数时，建议使用分页查询</li>
	 *   <li>要求给查询的每个字段加上别名，并且别名与指定的类的属性名一致</li>
	 * </ol>
	 * @param sql sql语句
	 * @param cls 映射对象class
	 * @return 符合条件的实例集合
	 */
	public List findBySql(String sql, Class cls);
	/**
	 * （不分页）根据sql查询数据对象集合，参数使用？代替
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>一般地，建议使用find方法通过hql进行查询</li>
	 *   <li>默认返回的最大记录数为MAX_RESULTS的值</li>
	 *   <li>如果待查询数据预估超过该最大记录数时，建议使用分页查询</li>
	 *   <li>要求给查询的每个字段加上别名，并且别名与指定的类的属性名一致</li>
	 * </ol>
	 * @param sql sql语句
	 * @param values 查询条件集合
	 * @param cls 映射对象class
	 * @return 符合条件的实例集合
	 */
	public List findBySql(String sql, List<Object> values, Class cls);
	/**
	 * （不分页）根据sql查询数据对象集合，参数使用:name代替
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>一般地，建议使用find方法通过hql进行查询</li>
	 *   <li>默认返回的最大记录数为MAX_RESULTS的值</li>
	 *   <li>如果待查询数据预估超过该最大记录数时，建议使用分页查询</li>
	 *   <li>要求给查询的每个字段加上别名，并且别名与指定的类的属性名一致</li>
	 * </ol>
	 * @param sql sql语句
	 * @param values 查询条件集合
	 * @param cls 映射对象class
	 * @return 符合条件的实例集合
	 */
	public List findBySql(String sql, Map<String, Object> values, Class cls);
	/**
	 * 根据hql进行分页查询，执行后会将数据集、总条数等信息填充到PageSupport对象中
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>一般地，建议使用find方法通过hql进行查询</li>
	 *   <li>如果包含查询参数，请使用同名包含查询条件的方法，否则可能存在sql注入的漏洞</li>
	 *   <li>要求给查询的每个字段加上别名，并且别名与指定的类的属性名一致</li>
	 * </ol>
	 * @param sql sql语句
	 * @param ps 分页对象
	 * @param cls 映射对象class
	 */
	public void findBySql(String sql, PageSupport ps, Class cls);
	/**
	 * 根据hql进行分页查询，参数使用？代替，执行后会将数据集、总条数等信息填充到PageSupport对象中
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>一般地，建议使用find方法通过hql进行查询</li>
	 *   <li>要求给查询的每个字段加上别名，并且别名与指定的类的属性名一致</li>
	 * </ol>
	 * @param sql sql语句
	 * @param values 查询条件集合
	 * @param ps 分页对象
	 * @param cls 映射对象class
	 */
	public void findBySql(String sql, List<Object> values, PageSupport ps, Class cls);
	/**
	 * 根据hql进行分页查询，参数使用:name代替，执行后会将数据集、总条数等信息填充到PageSupport对象中
	 * </br>&nbsp;&nbsp;说明：
	 * <ul>
	 *   <li>一般地，建议使用find方法通过hql进行查询</li>
	 *   <li>要求给查询的每个字段加上别名，并且别名与指定的类的属性名一致</li>
	 * </ol>
	 * @param sql sql语句
	 * @param values 查询条件集合
	 * @param ps 分页对象
	 * @param cls 映射对象class
	 */
	public void findBySql(String sql, Map<String, Object> values, PageSupport ps, Class cls);
	/**
	 * 刷新一级缓存的内容，使之与数据库数据保持同步
	 */
	public void flush();
	/**
	 * 加载一个实例，不会查找二级缓存，如果数据库中不存在,返回null</br></br>
	 * load()和get()的区别：
	 * <ol>
	 * <li>如果找不到符合条件的纪录，get()方法将返回null．而load()将会报出ObjectNotFoundEcception</li>
	 * <li>load()方法可以返回实体的代理类实例，而get()永远只返回实体类</li>
	 * <li>load()方法可以充分利用二级缓存和内部缓存的现有数据，而get()方法只在内部缓存中进行查找，如没有发现对应数据将跳过二级缓存，直接调用sql进行查找</li>
	 * </ol>
	 * @param id 实体类主键
	 * @return 实体类实例
	 */
	public T get(ID id);
	/**
     * 取得当前线程的Hibernate Session
     * @return 当前线程的Hibernate Session
     */
    public Session getCurrentSession();
	/**
	 * 获取不分页查询的最大查询记录数
	 * @return 最大查询记录数
	 */
	public int getMaxResults();
	/**
	 * 保存实例对象，但不会把实例对象添加到二级缓存</br>
	 * insert和save都是保存实例对象的方法，只是insert会返回该实例对象的主键，save无返回值
	 * @param t 要保存的实例对象
	 * @return 实例对象的ID
	 */
	public ID insert(T t);
	/**
	 * 加载一个实例，会从二级缓存查找，如果数据库中不存在,抛出ObjectNotFoundException</br></br>
	 * load()和get()的区别：
	 * <ol>
	 *  <li><s>如果找不到符合条件的纪录，get()方法将返回null．而load()将会报出ObjectNotFoundEcception</s>——单元测试显示并不会抛出该异常，慎用</li>
	 *  <li>load()方法可以返回实体的代理类实例，而get()永远只返回实体类</li>
	 *  <li>load()方法可以充分利用二级缓存和内部缓存的现有数据，而get()方法只在内部缓存中进行查找，如没有发现对应数据将跳过二级缓存，直接调用sql进行查找</li>
	 * </ol>
	 * @param id 实体类主键
	 * @return 实体对象
	 */
	public T load(ID id);
	/**
	 * 保存实例对象，但不会把实例对象添加到二级缓存</br>
	 * insert和save都是保存实例对象的方法，只是insert会返回该实例对象的主键，save无返回值
	 * @param t 要保存的实例对象
	 */
	public void save(T t);
	/**
	 * 自动选择保存或更新实例对象</br>
	 * 如果缓存中存在该实例对象，则update，否则save。建议明确使用save和update。
	 * @param t 要保存或更新的实例对象
	 */
	public void saveOrUpdate(T t);
	/**
	 * 批量自动选择保存或更新实例对象</br>
	 * 如果缓存中存在该实例对象，则update，否则save。建议明确使用save和update。
	 * @param entities 要保存或更新的实例对象集合
	 */
	public void saveOrUpdate(Collection<T> entities);
	/**
	 * 设置默认查询记录条数
	 * @param maxResults 分页大小
	 */
	public void setMaxResults(int maxResults);
	/**
	 * 更新实例对象
	 * @param t 要更新的实例对象
	 */
	public void update(T t);

}
