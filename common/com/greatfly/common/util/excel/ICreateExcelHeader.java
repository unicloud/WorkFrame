package com.greatfly.common.util.excel;

import java.util.Map;

/**
 * 创建excel表头接口，与ExcelUtil配合使用
 * @author wuwq
 * 2011-8-18 下午10:20:41
 */
public interface ICreateExcelHeader {
	/**
	 * 设置excel列头
	 * @return 返回一个map，其中key是类的属性名，value是列头字段
	 */
	public Map<String, String> setHeader();
}
