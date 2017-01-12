package com.greatfly.common.devutil.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Id;

import com.greatfly.common.annotation.Domain;
import com.greatfly.common.devutil.DevelopConstant;
import com.greatfly.common.devutil.vo.Model;
import com.greatfly.common.util.exception.AppException;
import com.greatfly.common.util.StringUtil;

/**
 * 实体类vo工具类，与DevelopUtil配合使用
 * @author wuwq
 * 2013-5-3 上午09:52:24
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class ModelUtil {
	/**
	 * 防止实例化使用
	 */
	private ModelUtil() {}
	
	/**
	 * 根据指定的包名，生成对应的实体类集合
	 * @param packageName 包名
	 * @return 实体类集合
	 */
	public static List<Model> initModelByPackage(String packageName) {
	    String tpackageName = packageName;
		if (StringUtil.isBlank(tpackageName)) {
			throw new AppException("包名为空");
		}
		
		//如果指定的包名只到模块级，将包名路径指定到实体层
		if (tpackageName.indexOf(".domain") < 0) {
		    tpackageName += ".domain";
		}
		
		String dir = DevelopConstant.CODE_BASE_DIR + getDir(tpackageName);
		
		File document = new File(dir); //文件夹
		String[] fileArr = document.list(); //该文件夹下的所有文件
		if (fileArr == null || fileArr.length == 0) {
			throw new AppException(tpackageName + "包目录下查无实体类");
		}
		List<Model> modelList = new ArrayList<Model>();
		for (String fileName : fileArr) {
			if (fileName.endsWith(DevelopConstant.POSTFIX_JAVA)) { //对于java文件
				int position = fileName.indexOf(DevelopConstant.POSTFIX_JAVA);
				Class cls = null;
				try {
					cls = Class.forName(tpackageName + "." + fileName.substring(0, position)); //去掉文件名后缀
					
				} catch (ClassNotFoundException e) {
					throw new AppException("通过反射获取Class异常", e);
				}
				Model model = initModel(cls);
				if (model != null) {
					modelList.add(model);
				} // end if model null
			}
		} // end for
			
		return modelList;
	}
	
	/**
	 * 通过类class初始化modelVo，用于配合velocity自动生成代码
	 * @param cls 类class
	 * @return modelVo
	 */
	public static Model initModel(Class cls) {
		if (cls == null) {
			throw new AppException("初始化ModelVo异常，Class不能为null");
		}
		
		if (!cls.isAnnotationPresent(Domain.class)) {
			System.out.println(cls + "没有设置Domain注解，不生成对应的代码");
			return null;
		}
		
		Model model = new Model();
		String basePackage = cls.getName().substring(0, cls.getName().lastIndexOf(".domain"));
		model.setBasePackage(basePackage);
		String baseDir = getDir(basePackage);
		model.setBaseDir(baseDir);
		
		// 实体类层
		String modelName = cls.getSimpleName();
		model.setModelName(modelName);
		model.setModelLowerName(getLowerName(cls.getSimpleName()));
		Domain iDomain = (Domain) cls.getAnnotation(Domain.class);
		if (iDomain == null) {
			System.out.println(modelName + "没有设置IDomain注解");
		} else {
			String domainName = iDomain.value();
			model.setDomainName(domainName);
		}
		String pkId = getPkId(cls);
		model.setPkId(pkId);
		model.setPkIdGetMethod(getGetMethodName(pkId));
		model.setPkIdSetMethod(getSetMethodName(pkId));
		
		model.setDaoName(modelName + "Dao");
		model.setDaoLowerName(getLowerName(model.getDaoName()));
		model.setDaoDir(baseDir + "/dao");
		
		model.setServiceName(modelName + "Service");
		model.setServiceLowerName(getLowerName(model.getServiceName()));
		model.setServiceDir(baseDir + "/service");
		
		String shortName = getShortName(model.getModelName());
		String shortLowerName = getLowerName(shortName);
		model.setVoName(shortName + "Vo");
		model.setVoLowerName(shortLowerName + "Vo");
		model.setVoDir(baseDir + "/vo");
		
		model.setUccName(shortName + "Service");
		model.setUccLowerName(shortLowerName + "Service");
		model.setUccDir(baseDir + "/ucc");
		
		model.setActionName(shortName + "Action");
		model.setActionLowerName(shortLowerName + "Action");
		model.setActionDir(baseDir + "/action");
		model.setActionStrutsName(shortLowerName);
		
		// struts 
		String nameSpace = getNameSpace(baseDir);
		model.setNameSpace(nameSpace);
		
		return model;
	}

	/**
	 * 将字符串转成大写字母开头的字符串
	 * @param name 字符串
	 * @return 小写字母开头的字符串
	 */
	public static String getLowerName(String name) {
		if (StringUtil.isBlank(name)) {
			throw new AppException("入参为空，请确认");
		} else if (name.length() <= 1) {
			throw new AppException("入参长度不能小于等于1");
		}
		
		return name.substring(0, 1).toLowerCase(Locale.CHINA) + name.substring(1);
	}
	
	/**
	 * 将字符串转成小写字母开头的字符串
	 * @param name 类名
	 * @return 小写字母开头的字符串
	 */
	public static String getUpperName(String name) {
		if (StringUtil.isBlank(name)) {
			throw new AppException("入参为空，请确认");
		} else if (name.length() <= 1) {
			throw new AppException("入参长度不能小于等于1");
		}
		
		return name.substring(0, 1).toUpperCase(Locale.CHINA) + name.substring(1);
	}
	
	/**
	 * 获取属性的get方法名
	 * @param fieldName 属性
	 * @return get方法名
	 */
	public static String getGetMethodName(String fieldName) {
		if (StringUtil.isBlank(fieldName)) {
			throw new AppException("属性名为空，请确认");
		}
		
		return "get" + fieldName.substring(0, 1).toUpperCase(Locale.CHINA) + fieldName.substring(1);
	}
	
	/**
	 * 获取属性的set方法名
	 * @param fieldName 属性
	 * @return get方法名
	 */
	public static String getSetMethodName(String fieldName) {
		if (StringUtil.isBlank(fieldName)) {
			throw new AppException("属性名为空，请确认");
		}
		
		return "set" + fieldName.substring(0, 1).toUpperCase(Locale.CHINA) + fieldName.substring(1);
	}
	
	/**
	 * 根据包名获取路径
	 * @param packageName 包名
	 * @return 路径
	 */
	public static String getDir(String packageName) {
		if (StringUtil.isBlank(packageName)) {
			throw new AppException("包名为空");
		}
		
		return packageName.replace(".", "/");
	}
	
	/**
	 * 根据实体类名获取无模块名的实体类名
	 * @param modelName 实体类名
	 * @return 无模块名的实体类名
	 */
	public static String getShortName(String modelName) {
		int position = -1; //第二个大写字母的位置
		char[] charArr = modelName.toCharArray();
		for (int i = 1; i < charArr.length; i++) { //从第2个字符开始循环，默认第1个字符是大写
			if (Character.isUpperCase(charArr[i])) {
				position = i;
				break;
			}
		}
		if (position == -1) {
			throw new AppException("在实体类名" + modelName + "中查无第二个大写字母，无法生成ucc/action类名");
		}
		
		return modelName.substring(position);
	}
	
	/**
	 * 获取实体类的主键字段名
	 * @param cls 实体类class
	 * @return 主键字段名
	 */
	public static String getPkId(Class cls) {
		Field[] fields = cls.getDeclaredFields();
		//遍历所有属性，获取主键
		for (Field field : fields) {
			if (field.getAnnotation(Id.class) != null) {
				return field.getName();
			}
		} // end for
		throw new AppException(cls + "没有用@Id指定主键字段");
	}
	
	/**
	 * 从基本包目录解析出Struts2的命名空间
	 * @param baseDir 基本包目录
	 * @return 命名空间
	 */
	public static String getNameSpace(String baseDir) {
	    String tbaseDir = baseDir;
		for (int i = 0; i < 3; i++) { //循环3次，因为解析类似com/公司名/项目名
		    tbaseDir = tbaseDir.substring(tbaseDir.indexOf('/') + 1);
		}
		return "/" + tbaseDir;
	}
}
