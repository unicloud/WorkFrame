package com.greatfly.common.service;

import java.io.Serializable;

import com.greatfly.common.dao.IBaseDao;

/**
 * 基础service的常用方法接口
 * @param <T> 泛型的具体ORM bean类
 * @param <ID> ORM bean类的主键类型
 * @author wuwq
 * 2010-11-15 下午04:22:29
 */
public interface IBaseService<T, ID extends Serializable> extends IBaseDao<T, Serializable> {
	
}
