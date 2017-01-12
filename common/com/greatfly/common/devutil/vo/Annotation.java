package com.greatfly.common.devutil.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.greatfly.common.CommonConstant;


/**
 * 类注解vo
 * @author wuwq
 * 2013-5-2 上午11:20:40
 */
public class Annotation {
	/** 作者 */
	private String author;
	/** 创建时间 */
	private String createDate;
	
	/**
	 * 构造函数
	 * @param author 作者
	 */
	public Annotation(String author) {
		this.author = author;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", CommonConstant.DEFAULT_LOCALE);
		this.createDate = sdf.format(new Date());
	}
	
	// private methods
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
