package com.greatfly.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.greatfly.common.util.exception.AppException;

/**
 * Json工具类
 * <ol>
 * <li>对于其他一般的Object使用fastjson实现，在json与obj互转时可以获得更快的转换速度</li>
 * <li>在使用fastjson时，请在实体类中使用@JSONField定义Date型数据的格式化</li>
 * </ol>
 * @author wuwq
 * 2010-9-14   上午09:35:33
 */	
public final class JsonUtil {
	/**
	 * 防止实例化使用
	 */
	private JsonUtil() {}
	
	//public methods
	
	/**
	 * json序列化
	 * @param <T> 泛型类型
	 * @param jsonStr json字符串
	 * @param cls 泛型类型
	 * @return 实体类
	 */
	public static <T> T getBean(String jsonStr, Class<T> cls) {
		return (T) JSON.parseObject(jsonStr, cls);
	}
	
	/**
	 * 将json转成List
	 * @param <T> List中元素的类型
	 * @param jsonStr 要转换的json字符串
	 * @param cls 元素的类型
	 * @return 返回list
	 */
	public static <T> List<T> getBeanList(String jsonStr, Class<T> cls) {
		return JSON.parseArray(jsonStr, cls);
	}
	
	/**
	 * 将obj转换成json字符串
	 * @param obj 要转换的对象
	 * @return json字符串
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return "{}";
		}
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 将obj转换成json字符串
	 * @param obj 要转换的对象
	 * @param dateFormat 转型的日期格式
	 * @return json字符串
	 */
	public static String toJson(Object obj, String dateFormat) {
		if (obj == null) {
			return "{}";
		}
		return JSON.toJSONStringWithDateFormat(obj, dateFormat);
	}
	
	   /**
     * 将obj转换成json字符串
     * @param obj 要转换的对象
     * @param sFeature 转型的序列化格式
     * @return String json字符串
     */
    public static String toJson(Object obj, SerializerFeature sFeature) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, sFeature);
    }
    
    /**
     * 将字符串格式化为Object
     * @param text  文本
     * @return Object 对象
     */
    public static Object parse(String text) {
        if (StringUtil.isBlank(text)) {
            throw new AppException("格式化json字符串为空");
        }
        return JSON.parse(text);
    }
	
}
