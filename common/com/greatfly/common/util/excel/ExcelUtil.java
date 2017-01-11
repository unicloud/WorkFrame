package com.greatfly.common.util.excel;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.greatfly.common.util.ReflectUtil;
import com.greatfly.common.util.exception.AppException;


/**
 * excel工具类，用于将数据库查询到的数据对象生成excel文件
 * <ol>
 * 	<li>默认首列为"序号"，不需要指定</li>
 *  <li>只支持属性一级属性集成，如A是B的父类、含有属性a，B是C的父类、含有属性b，如果指定的泛型是C，只能获取到b属性，解析不到a属性</li>
 * </ol>
 * @author wuwq
 * 2011-8-18 下午04:08:45
 */
@SuppressWarnings("rawtypes")
public final class ExcelUtil {
	/**
	 * excel最大导出数据量
	 */
	public static final int EXCEL_MAX_RECORDS = 65535;
	/**
	 * 防止实例化使用
	 */
	private ExcelUtil() {}
	/**
	 * 创建excel文件
	 * @param <T> 数据对象list的泛型
	 * @param header 表头
	 * @param voList 数据对象list
	 * @return excel文件
	 */
	public static <T> HSSFWorkbook createWorkbook(Map<String, String> header, List<T> voList) {
		return createWorkbook(header, voList, null);
	}
	
	/**
	 * 创建excel文件
	 * @param <T> 数据对象list的泛型
	 * @param iExcel 创建excel表头接口
	 * @param voList 数据对象list
	 * @return excel文件
	 */
	public static <T> HSSFWorkbook createWorkbook(ICreateExcelHeader iExcel, List<T> voList) {
		return createWorkbook(iExcel, voList, null);
	}
	
	/**
	 * 创建excel文件
	 * @param <T> 泛型
	 * @param header excel的表头
	 * @param voList 数据对象
	 * @param sdf 日期格式化
	 * @return excel文件
	 */
	public static <T> HSSFWorkbook createWorkbook(Map<String, String> header, List<T> voList, SimpleDateFormat sdf) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet outSheet = workbook.createSheet("sheet1");
		createHeader(outSheet, header);
		try {
			createContent(outSheet, header, voList, sdf);
		} catch (Exception e) {
			throw new AppException("创建excel内容异常", e);
		}
		return workbook;
	}
	
	/**
	 * 创建excel文件
	 * @param <T> 泛型
	 * @param iExcel 生成表头接口
	 * @param voList 数据对象
	 * @param sdf 日期格式化
	 * @return excel文件
	 */
	public static <T> HSSFWorkbook createWorkbook(ICreateExcelHeader iExcel, List<T> voList, SimpleDateFormat sdf) {
		return createWorkbook(iExcel.setHeader(), voList, sdf);
	}
	
	/**
	 * 获取单元格的有效性校验，即设置单元格有效值的下拉菜单</br>
	 * 通过sheet.addValidationData(validation)把该校验加到对应的sheet
	 * @param column 列序号，从0开始计算
	 * @param values 有效值数组
	 * @return 校验对象
	 */
	public static HSSFDataValidation getValidation(int column, String[] values) {
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(values);
		//起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(1, EXCEL_MAX_RECORDS, column, column); //首行是表头
		return new HSSFDataValidation(regions, constraint);
	}
	
	// private methods
	
	/**
	 * 创建表头
	 * @param sheet 工作簿
	 * @param header 表头
	 */
	private static void createHeader(HSSFSheet sheet, Map<String, String> header) {
		sheet.createRow(0);
		HSSFCell cell = sheet.getRow(0).createCell(0);
		cell.setCellValue("序号");
		int i = 1; //列
		for (Map.Entry<String, String> entry : header.entrySet()) {
			cell = sheet.getRow(0).createCell(i);
			cell.setCellValue(entry.getValue());
			i++;
		}
	}
	
	/**
	 * 创建内容
	 * @param <T> 数据List的泛型
	 * @param sheet 工作簿
	 * @param header 表头
	 * @param voList 数据List
	 * @param sdf 日期格式化，默认是yyyy-MM-dd HH:mm:ss
	 * @throws Exception 使用反射产生的异常
	 */
	private static <T> void createContent(HSSFSheet sheet, Map<String, String> header, List<T> voList, SimpleDateFormat sdf) throws Exception {
		if (voList == null || voList.isEmpty()) {
			return;
		}
		
		//初始化
		if (sdf == null) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		}
		
		//获取泛型
		Class cls = voList.get(0).getClass();
		Field[] fields = new Field[header.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : header.entrySet()) { //遍历要处理的属性列
			fields[i] = ReflectUtil.getFiled(cls, entry.getKey());
			fields[i].setAccessible(true);
			i++;
		}
		
		//生成内容字段
		String tmp = null;
		for (i = 1; i <= voList.size(); i++) { //行
			sheet.createRow(i);
			HSSFCell cell = sheet.getRow(i).createCell(0);
			cell.setCellValue(String.valueOf(i));
			
			int j = 1; //列
			T currentObj = voList.get(i - 1);
			for (Field field : fields) {
				cell = sheet.getRow(i).createCell(j);
				Object obj = field.get(currentObj);
				if (obj == null) {
					tmp = "";
				} else if (field.getType().equals(Date.class)) {
					tmp = sdf.format((Date) obj);
				} else {
					tmp = obj.toString();
				}
				cell.setCellValue(tmp);
				
				j++;
			}
		}
	}
	
	/**
	 * 依据后缀名判断读取的是否为Excel文件
	 * @param fileName 文件名
	 * @return boolean
	 */
	public static boolean isExcel(String fileName) {
		if (fileName == null) {
			return false;
		}
		if (fileName.matches("^.+\\.(?i)(xls)$")
				|| fileName.matches("^.+\\.(?i)(xlsx)$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 依据文件名判断是否为excel2007及以上
	 * @param fileName 文件名
	 * @return boolean
	 */
	public static boolean isExcel2007(String fileName) {
		return fileName.matches("^.+\\.(?i)(xlsx)$");
	}
	
}
