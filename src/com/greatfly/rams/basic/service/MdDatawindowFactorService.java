package com.greatfly.rams.basic.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greatfly.common.service.impl.BaseService;
import com.greatfly.rams.basic.dao.MdDatawindowFactorDao;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;

/**
 * 数据窗配置service
 * @author huangqb
 * 2017-01-12 15:47:18
 */
@Service
public class MdDatawindowFactorService extends BaseService<MdDatawindowFactor, Long> {
	@Resource
	public void setMdDatawindowFactorDao(MdDatawindowFactorDao mdDatawindowFactorDao) {
		this.baseDao = mdDatawindowFactorDao;
	}
	
}