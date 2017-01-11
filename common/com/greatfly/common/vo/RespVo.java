package com.greatfly.common.vo;


/**
 * 异步请求返回信息，如果有特殊需要，请继承本类
 * @author wuwq
 * 2012-12-14 上午10:00:57
 */
public class RespVo {
	/** 是否执行成功，默认成功 */
	protected Boolean success = true;
	/** 执行情况 */
	protected String info = "执行成功";
	/** 请求是否超时 */
	protected Boolean timeout = true;
	
	// constructor
	
	/**
	 * 构造函数
	 */
	public RespVo(){};
	
	/**
	 * 构造函数
	 * @param success 是否执行成功
	 */
	public RespVo(Boolean success) {
		this.success = success;
	}
	
	/**
	 * 构造函数
	 * @param success 是否执行成功
	 * @param info 执行情况
	 */
	public RespVo(Boolean success, String info) {
		this.success = success;
		this.info = info.replaceAll("\"", "\\\"");
	}
	
	// getter && setter
	
	public Boolean getTimeout() {
        return timeout;
    }

    public void setTimeout(Boolean timeout) {
        this.timeout = timeout;
    }

    public Boolean isSuccess() {
		return success;
	}
    
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	/**
	 * 获取值
	 * @return String 值
	 */
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
	    //防止其中如果带"，前台json解析异常
		this.info = info.replaceAll("\"", "\\\"");
	}
	
}
