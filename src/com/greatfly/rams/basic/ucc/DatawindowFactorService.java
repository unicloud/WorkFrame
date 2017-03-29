package com.greatfly.rams.basic.ucc;

import java.util.ArrayList;
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

	//解析单个数据窗信息
	//考虑需要的功能中要用到数据窗的那些信息
	
	//列名、列的别名
	//获取表名
	//获取下拉列表信息
	//获取默认where条件
	//获取可更新列
	//获取主键列
	//获取显示列
	//获取隐藏列信息
	//获取数据窗中任意列的信息  别名、数据库列名、DT（CHAR\NUM\DATE）、WD（界面列宽）、
	//DL（长度校验）、ST(TXT\CMB--单选》####,****,多选》###*，***#) 、FM (日期格式、数值格式)
	//获取非空列和索引列（索引列中除了主键其他的尽可能放在查询的前列）
	//首先需要获取到Grid所需要的部分

	public List getGridColumnInfos() {
//		列信息包括：colName, dbName, dataType, disWidth, dataLength, showType, format,
//		isRequired, isIndex, isMutiSelect
		return new ArrayList<String>();
		//datafields\cloumns
	}

	public String constructQueryCond() {
		//拼接where条件  1、数据窗默认的where条件； 2、前台界面默认的查询条件  
		//3、前台界面输入的查询条件  4、后台其他条件处理之后得到的条件  
		//5、默认的group by \order by 语句  6、前台传过来的group by\order by语句
		// 注意一些涉及到用户信息的可以替换。可以在数据窗默认的查询条件中加入带变量参数的
		//条件语句，然后前台对对应的参数进行赋值。这样可以使得条件拼接代码更通用
		return "AAAA";
	}

	//获取下拉列表
	public List getDropDownList(String showType) {
		//showType为  _AAAA 类型，则从MD_CODE_LIST表中获取数据
		//showType为  AAAA类型，则通过showType 的判断，用不同的sql语句获取下拉里列表
		return new ArrayList<String>();
	}

	//查询数据集(不分页)
	public List getDataSet() {
		return new ArrayList<String>();
	}

	//Grid的数据查询  主要是拼接where条件以及分页信息 再则就是group by、order by语句需要注意
	public List queryPagingDataSet() {
		return new ArrayList<String>();
	}


	//通过sql语句获取数据集
	public List getDataSetBySql(String sql) {
		return new ArrayList<String>();
	}

	//查询关键是要获取到查询的sql语句，因此为了各种情况，需要有个拼接处SQL语句的功能，暂不考虑分页的SQL

}	
