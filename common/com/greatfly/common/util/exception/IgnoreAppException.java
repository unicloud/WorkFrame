package com.greatfly.common.util.exception;

/**
 * 可忽略的业务异常
 * <br/>&nbsp;&nbsp;说明
 * <ol>
 * 	<li>本类与AppException同为框架定义的业务逻辑异常，用于在程序中硬编码检查对象的属性、状态是否符合业务逻辑的要求</li>
 * 	<li>本类与AppException的区别在于，本类不会导致事务的回滚，所以在选择抛出业务异常的类型时要特别注意使用</li>
 *  <li>本类要求在spring的transactionInterceptor的配置中将事务传播属性忽略本异常</li>
 * </ol>
 * @author wuwq
 * 2012-11-9 下午02:34:03
 */
public class IgnoreAppException extends RuntimeException {
private static final long serialVersionUID = 5530063727694715934L;
	
	//Constructors
	
	/**
	 * 默认构造函数
	 */
	public IgnoreAppException(){}
	
	/**
	 * 构造函数
	 * @param msg 出错信息
	 */
	public IgnoreAppException(String msg) {
		super(msg);
	}
	
	/**
	 * 构造函数
	 * @param cause 可抛出异常
	 */
	public IgnoreAppException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 * @param msg 出错信息
	 * @param cause 可抛出异常
	 */
	public IgnoreAppException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
