package com.greatfly.common.devutil;

/**
 * 开发util常量
 * @author wuwq
 * 2013-5-2 下午03:07:51
 */
public final class DevelopConstant {
	/** dao模板 */
	public static final String TEMPLATE_VM_PATH_DAO = "common/com/greatfly/common/devutil/vm/Dao.vm";
	/** service模板 */
	public static final String TEMPLATE_VM_PATH_SERVICE = "common/com/greatfly/common/devutil/vm/Service.vm";
	/** vo模板 */
	public static final String TEMPLATE_VM_PATH_VO = "common/com/greatfly/common/devutil/vm/Vo.vm";
	/** ucc模板 */
	public static final String TEMPLATE_VM_PATH_UCC = "common/com/greatfly/common/devutil/vm/Ucc.vm";
	/** ext_ucc模板 */
	public static final String TEMPLATE_VM_PATH_EXT_UCC = "common/com/greatfly/common/devutil/vm/ExtUcc.vm";
	/** action模板 */
	public static final String TEMPLATE_VM_PATH_ACTION = "common/com/greatfly/common/devutil/vm/Action.vm";
	/** ext_action模板 */
	public static final String TEMPLATE_VM_PATH_EXT_ACTION = "common/com/greatfly/common/devutil/vm/ExtAction.vm";
	/** struts配置文件模板 */
	public static final String TEMPLATE_VM_PATH_PACKAGE_INFO_CONFIG = "common/com/greatfly/common/devutil/vm/package-info.vm";
	/** 类名尾缀 */
	public static final String POSTFIX_JAVA = ".java";
	/** 程序代码所在的绝对路径主目录 */
	public static final String CODE_BASE_DIR = System.getProperty("user.dir") + "\\src\\";
	/** 单元测试所在的绝对路径主目录 */
	public static final String TEST_BASE_DIR = System.getProperty("user.dir") + "\\test\\";
	/** 默认包名 */
	public static final String BASE_PACKAGE_NAME = "com.greatfly";
	/** 包info文件名 */
	public static final String PACKAGE_INFO_FILE_NAME = "package-info.java";
	/** 模型生成的代码类型：JSP */
	public static final int TYPE_JSP = 1;
	/** 模型生成的代码类型：EXT */
	public static final int TYPE_EXT = 2;
	/**
	 * 防止实例化使用
	 */
	private DevelopConstant() {}
}
