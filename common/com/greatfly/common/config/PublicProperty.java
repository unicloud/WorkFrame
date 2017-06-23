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
public class PublicProperty extends Properties {

    private static final long serialVersionUID = -184471175479906716L;
    
    /**
     * PublicProperty
     */
    public PublicProperty() {
        InputStream inStream = null;
        try {
            inStream = PublicProperty.class.getResourceAsStream("/public.properties");
            this.load(inStream);
            //
            // decode("oracle.username");
            
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
    public PublicProperty(String path) {
        InputStream inStream = null;
        try {
            inStream = PublicProperty.class.getResourceAsStream(path);
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
	 * 通过键key获取值value
	 * @param key 键值
	 * @return value 具体值
	 */
	public String getValue(String key) {
		String value = this.getProperty(key);
        return value;
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
