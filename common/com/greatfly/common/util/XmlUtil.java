package com.greatfly.common.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.greatfly.common.util.exception.AppException;


/**
 * 基于Jaxb的XML工具类，提供对象和XML的相互转换操作
 * @author wuwq
 * 2014-8-6 下午03:19:27
 */
@SuppressWarnings("unchecked")
public final class XmlUtil {
	private static Logger log = Logger.getLogger(XmlUtil.class);
	/**
	 * 私有构造函数，防止实例化使用
	 */
	private XmlUtil() {}
	/**
	 * 将对象解析成XML
	 * @param obj 待解析对象
	 * @return xml
	 */
	public static String toXml(Object obj) {
		StringWriter writer = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(obj, writer);
			return writer.toString();
			
		} catch (Exception e) {
			log.error("解析对象失败", e);
			throw new AppException("解析对象失败：", e);
		}
	}
	/**
	 * 将XML解析成对象
	 * @param <T> 泛型
	 * @param xml xml字符串
	 * @param cls 指定对象的class
	 * @return 对象
	 */
	public static <T> T toBean(String xml, Class<T> cls) {
		try {
			JAXBContext context = JAXBContext.newInstance(cls);
			Unmarshaller marshaller = context.createUnmarshaller();
			return (T) marshaller.unmarshal(new StringReader(xml));
			
		} catch (Exception e) {
			log.error("解析对象失败", e);
			throw new AppException("解析对象失败：", e);
		}
	}
}
