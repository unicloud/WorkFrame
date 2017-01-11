package com.greatfly.common.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

import com.greatfly.common.annotation.FieldDesc;
import com.greatfly.common.enums.LocaleEnum;
import com.greatfly.common.util.exception.AppException;


/**
 * 反射util，用于支持框架基础功能的实现
 * @author wuwq
 * 2013-7-1 上午10:59:05
 */
@SuppressWarnings("rawtypes")
public final class ReflectUtil {
	/**
	 * apache的对象转型util，声明为静态实例，单例使用
	 */
	public static final ConvertUtilsBean COVERT_UTIL_BEAN = new ConvertUtilsBean();
	/**
	 * 防止实例化使用
	 */
	private ReflectUtil() {}
	/**
	 * 获取一个类的某个属性，支持子类查无Field时，迭代在父类中尝试获得Field
	 * @param cls 类的cls
	 * @param name 属性str
	 * @return 指定的属性
	 * @throws NoSuchFieldException 查无指定的Field
	 */
	public static Field getFiled(Class cls, String name) throws NoSuchFieldException {
		Field field = null;
		try {
			field = cls.getDeclaredField(name);
			
		} catch (NoSuchFieldException e) {
			if (cls != Object.class) { //存在非Object的父类
				field = getFiled(cls.getSuperclass(), name);
			} else {
				throw e;
			}
		}
		
		return field;
	}
	
	/**
	 * 尝试将目标对象转换成指定的类型
	 * @param value 目标对象
	 * @param cls 指定类型
	 * @return 返回转换后的对象，实际类型即为指定的类型
	 */
	public static Object convert(Object value, Class cls) {
        Converter converter = COVERT_UTIL_BEAN.lookup(cls);
        if (converter != null) {
            return converter.convert(cls, value);
        } else {
            return value;
        }
    }
	
	/**
	 * 将输入的字符串转成对象，其中对象的每个属性要用@Filed进行定义
	 * @param <T> 泛型
	 * @param inputStr 输入字符串
	 * @param cls 指定对象
	 * @return 指定对象
	 */
	public static <T> T convertByAnnotation(String inputStr, Class<T> cls) {
		T t = null;
		try {
			t = cls.newInstance();
			
			Field[] fieldArr = cls.getDeclaredFields();
			String valStr = null;
			for (Field field : fieldArr) { //遍历所有属性
				FieldDesc anno = field.getAnnotation(FieldDesc.class);
				if (anno != null) { //使用了IField注解
					valStr = inputStr.substring(anno.begin(), anno.end()); //截取对应的字符串
					if (StringUtil.isNotBlank(valStr)) {
						field.setAccessible(true);
						setFiledValue(t, field, valStr);
					}
				} //end anno != null
			} //end for
			
		} catch (AppException e) {
			throw new AppException("解析异常", e);
		} catch (SecurityException e) {
			throw new AppException("解析异常", e);
		} catch (Exception e) {
			throw new AppException("解析异常", e);
		}
		
		return t;
	}
	
	// private methods
	
	/**
	 * 按照字段的类型将字符串型的参数设值
	 * @param obj 要修改字段值的对象
	 * @param field 字段
	 * @param valStr 字符串型参数
	 */
	private static void setFiledValue(Object obj, Field field, String valStr) {
		Class cls = field.getType();
		try {
			if (String.class == cls) {
				field.set(obj, valStr.trim());
				
			} else if (Integer.class == cls) {
				field.set(obj, Integer.valueOf(valStr.trim()));
				
			} else if (Long.class == cls) {
				field.set(obj, Long.valueOf(valStr.trim()));
				
			} else if (Double.class == cls) {
				field.set(obj, Double.valueOf(valStr.trim()));
				
			} else if (Date.class == cls) {
				FieldDesc anno = field.getAnnotation(FieldDesc.class);
				LocaleEnum localeEnum = anno.locale();
				SimpleDateFormat sdf = null;
				if (localeEnum == LocaleEnum.NULL) {
					sdf = new SimpleDateFormat(anno.dateFormat(), Locale.CHINA);
				} else {
					sdf = new SimpleDateFormat(anno.dateFormat(), localeEnum.getLocale());
				}
				field.set(obj, sdf.parse(valStr.trim()));
				
			} else {
				throw new AppException("类型无法解析：" + cls);
			}
			
		} catch (AppException e) {
			throw new AppException("设置字段类型异常：", e);
			
		} catch (Exception e) {
			throw new AppException("设置字段类型异常,参数Filed:" + field + ",valStr:" + valStr, e);
		}
	}
}
