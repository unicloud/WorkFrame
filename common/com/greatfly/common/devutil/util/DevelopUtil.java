package com.greatfly.common.devutil.util;

import static com.greatfly.common.devutil.DevelopConstant.CODE_BASE_DIR;
import static com.greatfly.common.devutil.DevelopConstant.POSTFIX_JAVA;

import java.util.List;

import org.apache.log4j.Logger;

import com.greatfly.common.devutil.DevelopConstant;
import com.greatfly.common.devutil.vo.Annotation;
import com.greatfly.common.devutil.vo.Model;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;

/**
 * 模型驱动开发util，用于生成后台代码</br>
 * 说明：
 * <ol>
 *   <li>util基于实体层生成上层各层代码；实体层建议使用MyEclipse的反射工具自动生成</li>
 *   <li>util只支持以Long为主键（序列主键）的实体类，不支持复合主键</li>
 * </ol>
 * @author wuwq
 * 2013-5-2 上午09:51:32
 */
@SuppressWarnings("rawtypes")
public final class DevelopUtil {
	private static Logger log = Logger.getLogger(DevelopUtil.class);
	private static CreateCode daoCreateCode = new CreateCode(DevelopConstant.TEMPLATE_VM_PATH_DAO);
	private static CreateCode serviceCreateCode = new CreateCode(DevelopConstant.TEMPLATE_VM_PATH_SERVICE);
	private static CreateCode voCreateCode = new CreateCode(DevelopConstant.TEMPLATE_VM_PATH_VO);
	private static CreateCode uccCreateCode = new CreateCode(DevelopConstant.TEMPLATE_VM_PATH_UCC);
	private static CreateCode actionCreateCode = new CreateCode(DevelopConstant.TEMPLATE_VM_PATH_ACTION);
	private static CreateCode packageInfoCreateCode = new CreateCode(DevelopConstant.TEMPLATE_VM_PATH_PACKAGE_INFO_CONFIG);
	
	/**
	 * 防止实例化使用
	 */
	private DevelopUtil() {}
	
	/**
	 * 使用说明：
	 * <ol>
	 *   <li>利用MyEclipse的反转引擎自动生成实体类</li>
	 *   <li>手工处理实体类，比如增加@Id标识主键字段、生成serialVersionUID、修改number型标志位的映射类型等</li>
	 *   <li>使用getDomainConfig方法生成实体类的spring注册配置，并粘贴到spring.xml和spring-test.xml中</li>
	 *   <li>使用createCode方法生成dao、service、vo、ucc、action的代码</li>
	 *   <li>手动增加struts2的配置</li>
	 * </ol>
	 * @param args 参数
	 */
	public static void main(String[] args) {
		//请修改
		String author = "huangqb"; //作者
		
		//生成代码方法1：指定Class
		Class cls = MdDatawindowFactor.class;
		getDomainConfig(cls);
		createCode(author, cls);
		
		//生成代码方法2：指定包
//		String packageName = "com.xmair.rams.cargo.tax.domain";
//		getDomainConfig(packageName);
//		createCode(author, packageName);
	}
	
	// private methods
	
	/**
	 * 根据指定类自动生成代码
	 * @param author 作者
	 * @param cls 指定类class
	 */
	private static void createCode(String author, Class cls) {
		//初始化
		Annotation annotation = new Annotation(author);
		Model model = ModelUtil.initModel(cls);
		
		//生成各层代码
		createCode(annotation, model);
	}
	
	/**
	 * 根据指定包名自动生成代码
	 * @param author 作者
	 * @param packageName 包名，可以是到模块名，如com.xmair.rams.blanktict，也可以是到实体层，如com.xmair.rams.blanktict.domain
	 */
	private static void createCode(String author, String packageName) {
		//初始化
		Annotation annotation = new Annotation(author);
		List<Model> modelList = ModelUtil.initModelByPackage(packageName);
		log.info("包" + packageName + "存在实体类 " + modelList.size() + " 个");
		
		//循环生成各层代码
		for (Model model : modelList) {
			createCode(annotation, model);
		}
	}
	
	/**
	 * 根据指定类自动生成实体类的spring配置文件段
	 * @param cls 类Class
	 */
	private static void getDomainConfig(Class cls) {
		Model model = ModelUtil.initModel(cls);
		System.out.println("实体类配置文件如下：");
		getDomainConfig(model);
	}
	
	/**
	 * 根据指定包自动生成实体类的spring配置文件段
	 * @param packageName 包名
	 */
	private static void getDomainConfig(String packageName) {
		List<Model> modelList = ModelUtil.initModelByPackage(packageName);
		log.info("包" + packageName + "存在实体类 " + modelList.size() + " 个");
		
		//循环生成各层代码
		System.out.println("实体类配置文件如下：");
		for (Model model : modelList) {
			getDomainConfig(model);
		}
	}
	
	/**
	 * 根据注解生成package-info.java
	 * @param annotation 注解
	 * @param model 实体层模型
	 */
	private static void createPackageInfo(Annotation annotation, Model model) {
		String documentPath = CODE_BASE_DIR +  model.getActionDir();
		packageInfoCreateCode.createFile(annotation, model, documentPath, DevelopConstant.PACKAGE_INFO_FILE_NAME);
	}
	
	/**
	 * 根据实体vo在控制台生成打印字段
	 * @param model 实体vo
	 */
	private static void getDomainConfig(Model model) {
		String cfg = "<value>" + model.getBaseDir() + "/domain/" + model.getModelName() + ".hbm.xml</value>";
		System.out.println(cfg);
	}
	
	/**
	 * 生成各层代码
	 * @param annotation 注解vo
	 * @param model 实体vo
	 */
	private static void createCode(Annotation annotation, Model model) {
		createDao(annotation, model);
		createService(annotation, model);
		createVo(annotation, model);
		createUcc(annotation, model);
		createAction(annotation, model);
		createPackageInfo(annotation, model);
	}
	
	/**
	 * 创建dao
	 * @param annotation 注解vo
	 * @param model 实体vo
	 */
	private static void createDao(Annotation annotation, Model model) {
		String documentPath = CODE_BASE_DIR +  model.getDaoDir();
		String fileName = model.getDaoName() + POSTFIX_JAVA;
		daoCreateCode.createFile(annotation, model, documentPath, fileName);
	}
	
	/**
	 * 创建service
	 * @param annotation 注解vo
	 * @param model 实体vo
	 */
	private static void createService(Annotation annotation, Model model) {
		String documentPath = CODE_BASE_DIR +  model.getServiceDir();
		String fileName = model.getServiceName() + POSTFIX_JAVA;
		serviceCreateCode.createFile(annotation, model, documentPath, fileName);
	}
	
	/**
	 * 创建ucc
	 * @param annotation 注解vo
	 * @param model 实体vo
	 */
	private static void createUcc(Annotation annotation, Model model) {
		String documentPath = CODE_BASE_DIR +  model.getUccDir();
		String fileName = model.getUccName() + POSTFIX_JAVA;
		uccCreateCode.createFile(annotation, model, documentPath, fileName);
	}
	
	/**
	 * 创建action
	 * @param annotation 注解vo
	 * @param model 实体vo
	 */
	private static void createAction(Annotation annotation, Model model) {
		String documentPath = CODE_BASE_DIR +  model.getActionDir();
		String fileName = model.getActionName() + POSTFIX_JAVA;
		actionCreateCode.createFile(annotation, model, documentPath, fileName);
	}
	
	/**
	 * 创建vo
	 * @param annotation 注解vo
	 * @param model 实体vo
	 */
	private static void createVo(Annotation annotation, Model model) {
		String documentPath = CODE_BASE_DIR +  model.getVoDir();
		String fileName = model.getVoName() + POSTFIX_JAVA;
		voCreateCode.createFile(annotation, model, documentPath, fileName);
	}
	
}
