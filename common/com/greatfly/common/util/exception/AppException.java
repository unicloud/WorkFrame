package com.greatfly.common.util.exception;

/**
 * 业务异常
 * @author wuwq
 * 2011-7-8 下午02:07:26
 */
public class AppException extends RuntimeException {
	private static final long serialVersionUID = 8906652988748380471L;
	
	// Constructors
	
	/**
	 * 默认构造函数
	 */
	public AppException(){}
	
	/**
	 * 构造函数
	 * @param msg 出错信息
	 */
	public AppException(String msg) {
		super(msg);
	}
	
	/**
	 * 构造函数
	 * @param cause 可抛出异常
	 */
	public AppException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 * @param msg 出错信息
	 * @param cause 可抛出异常
	 */
	public AppException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
