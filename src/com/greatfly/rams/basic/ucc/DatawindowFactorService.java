package com.greatfly.rams.basic.ucc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greatfly.common.util.exception.AppException;
import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.ServiceUtil;
import com.greatfly.common.util.StringUtil;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;
import com.greatfly.rams.basic.service.MdDatawindowFactorService;
import com.greatfly.rams.basic.vo.DatawindowFactorVo;

/**
 * 数据窗配置ucc
 * @author huangqb
 * 2017-01-12 15:47:18
 */
@SuppressWarnings("unchecked")
@Service
public class DatawindowFactorService {
	@Resource
	private MdDatawindowFactorService mdDatawindowFactorService;
	
	/**
	 * 分页查询
	 * @param ps 分页对象
	 * @param vo 查询条件
	 */
	public void list(PageSupport<DatawindowFactorVo> ps, DatawindowFactorVo vo) {
		StringBuffer sql = new StringBuffer();
		// TODO 定义查询Sql  
		
		// TODO 增加查询条件
		Map values = new HashMap();
		
		mdDatawindowFactorService.findBySql(sql.toString(), values, ps, DatawindowFactorVo.class);
	}
	
	/**
	 * 通过主键获取数据窗配置
	 * @param idLng 主键
	 * @return 数据窗配置
	 */
	public MdDatawindowFactor get(Long idLng) {
		return mdDatawindowFactorService.get(idLng);
	}
	
	/**
	 * 获取默认的数据窗配置
	 * @return 数据窗配置
	 */
	public MdDatawindowFactor getDefaultModel() {
		MdDatawindowFactor mdDatawindowFactor = new MdDatawindowFactor();
		mdDatawindowFactor.setPkid(0L);
		
		return mdDatawindowFactor;
	}
	
	/**
	 * 根据主键是否存在保存/更新数据窗配置
	 * @param mdDatawindowFactor 数据窗配置
	 */
	public void save(MdDatawindowFactor mdDatawindowFactor) {
		long id = mdDatawindowFactor.getPkid();
		
		if (id == 0L) { //新增
			mdDatawindowFactorService.save(mdDatawindowFactor);
		} else { //编辑
			mdDatawindowFactorService.update(mdDatawindowFactor);
		}
	}
	
	/**
	 * 删除数据窗配置（物理删除）
	 * @param ids 数据窗配置主键
	 */
	public void delete(String ids) {
		List<Long> idList = ServiceUtil.tran2LongList(ids);
		if (idList == null) {
			throw new AppException("查无要删除的数据窗配置");
		}
		
		for (Long id : idList) {
			mdDatawindowFactorService.delete(id);
		}
	}
	
}