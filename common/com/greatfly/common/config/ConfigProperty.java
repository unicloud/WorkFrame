package com.greatfly.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.greatfly.common.util.cryptography.AESUtil;
import com.greatfly.common.util.exception.AppException;

/**
 * 属性文件配置类
 * @author wuwq
 * 2010-11-12 下午02:05:11
 */
public class ConfigProperty extends Properties {

	private static final long serialVersionUID = -184471175479906716L;
	
	/**
	 * 标准无参构造函数
	 */
	public ConfigProperty() {
		InputStream inStream = null;
		try {
			inStream = ConfigProperty.class.getResourceAsStream("/jdbc.properties");
			this.load(inStream);
			//结算数据库
			decode("oracle.username");
			decode("oracle.password");
			decode("dev.username");
			decode("dev.password");
			
		} catch (Exception e) {
			throw new AppException("属性设置失败", e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					throw new AppException("关闭jdbc.properties的InputStream异常", e);
				}
			}
		}
	}

	/**
	 * 带指定属性文件路径的构造函数
	 * @param path 属性文件路径
	 */
	public ConfigProperty(String path) {
		InputStream inStream = null;
		try {
			inStream = ConfigProperty.class.getResourceAsStream(path);
			this.load(inStream);
		} catch (IOException e) {
			throw new AppException("属性文件加载失败，路径：" + path, e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					throw new AppException("关闭InputStream异常，路径" + path, e);
				}
			}
		}
	}
	
	/**
	 * 解密并重置
	 * @param property 属性名
	 */
	private void decode(String property) {
		String value = this.getProperty(property);
		value = AESUtil.getInstance().getDesString(value);
		this.setProperty(property, value);
	}
}
