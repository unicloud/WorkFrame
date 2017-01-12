package com.greatfly.common.devutil.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.greatfly.common.CommonConstant;
import com.greatfly.common.devutil.vo.Annotation;
import com.greatfly.common.devutil.vo.Model;
import com.greatfly.common.util.exception.AppException;

/**
 * 创建代码
 * @author wuwq
 * 2013-5-3 下午04:02:31
 */
public class CreateCode {
	private static Logger log = Logger.getLogger(CreateCode.class);
	/** 模板 */
	private String templatePath;
	
	/**
	 * 构造函数
	 * @param templatePath 模板路径
	 */
	public CreateCode(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * 创建类文件，如果文件存在，则跳过
	 * @param annotation 注解vo
	 * @param model 实体vo
	 * @param documentPath 文件路径
	 * @param fileName 文件名
	 */
	public void createFile(Annotation annotation, Model model, String documentPath, String fileName) {
		//创建目录
		File documentFile = new File(documentPath);
		createFilePath(documentFile);
		
		//创建文件
		String filePath = documentPath + "/" + fileName;
		File file = new File(filePath);
		if (file.exists()) {
			log.info(fileName + "文件已存在，跳过");
			return;
		}
		
		FileOutputStream os = null;
		OutputStreamWriter osw = null;
		try {
			os = new FileOutputStream(file);
			osw = new OutputStreamWriter(os, CommonConstant.CHARSET_NAME);
			String content = createCode(annotation, model);
			osw.write(content);
			osw.flush();
	        
	        log.info("创建文件：" + fileName);
			
		} catch (IOException e) {
			log.error("创建文件异常", e);
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				log.error("关闭OutputStreamWriter异常", e);
			}
			
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				log.error("关闭FileOutputStream异常", e);
			}
		}
	}
	
	// private methods
	
	/**
	 * 如果不存在，则创建文件夹；否则，跳过
	 * @param document 文件夹
	 */
	private void createFilePath(File document) {
		if (!document.exists()) {
			boolean isSuccess = document.mkdirs();
			if (!isSuccess) {
				throw new AppException("创建目录失败：" + document);
			}
		}
	} 
	
	/**
	 * 根据指定模板生成对应的代码内容
	 * @param annotation 注解vo
	 * @param model 实体vo
	 * @return 代码内容
	 */
	private String createCode(Annotation annotation, Model model) {
		VelocityContext context = new VelocityContext();
		context.put("annotation", annotation);
		context.put("model", model);
		
		//使用单例的Velocity
		Velocity.init();
		StringWriter writer = new StringWriter();
		//指定按UTF-8编码
		Velocity.mergeTemplate(this.templatePath, CommonConstant.CHARSET_NAME, context, writer);
		
		return writer.toString();
	}
	
	// getter && setter

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	
}
