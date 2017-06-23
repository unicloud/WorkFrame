package com.greatfly.common.vo;

import com.greatfly.rams.basic.domain.StUser;

/**
 * 用户信息vo，用于在session保存当前登录用户的信息
 * @author wuwq
 * 2010-9-7 下午03:02:10
 */
public class UserVo extends StUser {
	private static final long serialVersionUID = -8944486234320381608L;
	/**
	 * 当前登录账套
	 */
	private String idCode;
	/**
	 * 用户登录IP地址
	 */
	private String clientIp;
	/**
	 * 公司二字码集合
	 */
	private String reCo2c;
	/**
	 * 公司三字码集合
	 */
	private String reCo3c;
	
    /**
     * 当前使用的公司二字码
     */
    private String co2c;
    /**
     * 当前使用的公司三字码
     */
    private String co3c;
    
    /**
     * 当前使用的国内国际
     */
    private String statCode;
    
    /**
     * 国内国际集合
     */
    private String reStatCode;   
	
    /**
     * 客货标志
     */
    private String pcType;   
    
	// getter && setter

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
    /**
     * 获取公司二字码集合
     * @return String 返回值
     */
	public String getReCo2c() {
		return reCo2c;
	}
	
    /**
     * 设置公司二字码集合
     * @param reCo2c 公司二字码集合
     */
	public void setReCo2c(String reCo2c) {
		this.reCo2c = reCo2c;
	}
	
    /**
     * 获取公司三字码集合
     * @return String 返回值
     */
	public String getReCo3c() {
		return reCo3c;
	}
	
    /**
     * 设置公司三字码集合
     * @param reCo3c 公司三字码集合
     */
	public void setReCo3c(String reCo3c) {
		this.reCo3c = reCo3c;
	}
	
	/**
	 * 获取当前使用的公司二字码
	 * @return String 返回值
	 */
    public String getCo2c() {
        return co2c;
    }

    /**
     * 设置当前使用的公司二字码
     * @param co2c 参数
     */
    public void setCo2c(String co2c) {
        this.co2c = co2c;
    }

    /**
     * 获取当前使用的公司三字码
     * @return String 返回值
     */
    public String getCo3c() {
        return co3c;
    }

    /**
     * 设置当前使用的公司三字码
     * @param co3c 参数
     */
    public void setCo3c(String co3c) {
        this.co3c = co3c;
    }

    /**
     * 获取当前使用的国内国际
     * @return String 返回值
     */
    public String getStatCode() {
        return statCode;
    }

    /**
     * 设置当前使用的国内国际
     * @param statCode 国内国际
     */
    public void setStatCode(String statCode) {
        this.statCode = statCode;
    }


    /**
     * 获取国内国际集合
     * @return String 返回值
     */
    public String getReStatCode() {
        return reStatCode;
    }

    /**
     * 设置国内国际集合
     * @param reStatCode 国内国际
     */
    public void setReStatCode(String reStatCode) {
        this.reStatCode = reStatCode;
    }
    
    /**
     * 获取客货标志
     * @return String 返回值
     */
	public String getPcType() {
		return pcType;
	}

    /**
     * 设置客货标志
     * @param pcType 客货标志
     */
	public void setPcType(String pcType) {
		this.pcType = pcType;
	}
}
