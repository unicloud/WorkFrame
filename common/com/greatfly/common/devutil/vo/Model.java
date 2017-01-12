package com.greatfly.common.devutil.vo;


/**
 * 实体类Vo，包含模型驱动开发涉及的相关属性
 * @author wuwq
 * 2013-5-2 上午10:12:33
 */
public class Model {
	
	private String basePackage;
	private String baseDir;
	
	// domain
	private String modelName;
	private String modelLowerName;
	
	// dao
	private String daoName;
	private String daoLowerName;
	private String daoDir;
	
	// service
	private String serviceName;
	private String serviceLowerName;
	private String serviceDir;
	
	// vo
	private String voName;
	private String voLowerName;
	private String voDir;
	
	// ucc
	private String uccName;
	private String uccLowerName;
	private String uccDir;
	
	// action
	private String actionName;
	private String actionLowerName;
	private String actionDir;
	private String actionStrutsName;
	
	// fields
	private String domainName;
	private String pkId;
	private String pkIdGetMethod;
	private String pkIdSetMethod;
	
	// struts
	private String nameSpace;
	private String jspDir;
	
	// getter && setter
	
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelLowerName() {
		return modelLowerName;
	}
	public void setModelLowerName(String modelLowerName) {
		this.modelLowerName = modelLowerName;
	}
	public String getDaoName() {
		return daoName;
	}
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
	public String getDaoDir() {
		return daoDir;
	}
	public void setDaoDir(String daoDir) {
		this.daoDir = daoDir;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceLowerName() {
		return serviceLowerName;
	}
	public void setServiceLowerName(String serviceLowerName) {
		this.serviceLowerName = serviceLowerName;
	}
	public String getServiceDir() {
		return serviceDir;
	}
	public void setServiceDir(String serviceDir) {
		this.serviceDir = serviceDir;
	}
	public String getVoName() {
		return voName;
	}
	public void setVoName(String voName) {
		this.voName = voName;
	}
	public String getVoLowerName() {
		return voLowerName;
	}
	public void setVoLowerName(String voLowerName) {
		this.voLowerName = voLowerName;
	}
	public String getVoDir() {
		return voDir;
	}
	public void setVoDir(String voDir) {
		this.voDir = voDir;
	}
	public String getUccName() {
		return uccName;
	}
	public void setUccName(String uccName) {
		this.uccName = uccName;
	}
	public String getUccLowerName() {
		return uccLowerName;
	}
	public void setUccLowerName(String uccLowerName) {
		this.uccLowerName = uccLowerName;
	}
	public String getUccDir() {
		return uccDir;
	}
	public void setUccDir(String uccDir) {
		this.uccDir = uccDir;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionDir() {
		return actionDir;
	}
	public void setActionDir(String actionDir) {
		this.actionDir = actionDir;
	}
	public String getDaoLowerName() {
		return daoLowerName;
	}
	public void setDaoLowerName(String daoLowerName) {
		this.daoLowerName = daoLowerName;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getPkIdGetMethod() {
		return pkIdGetMethod;
	}
	public void setPkIdGetMethod(String pkIdGetMethod) {
		this.pkIdGetMethod = pkIdGetMethod;
	}
	public String getPkIdSetMethod() {
		return pkIdSetMethod;
	}
	public void setPkIdSetMethod(String pkIdSetMethod) {
		this.pkIdSetMethod = pkIdSetMethod;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getJspDir() {
		return jspDir;
	}
	public void setJspDir(String jspDir) {
		this.jspDir = jspDir;
	}
	public String getActionLowerName() {
		return actionLowerName;
	}
	public void setActionLowerName(String actionLowerName) {
		this.actionLowerName = actionLowerName;
	}
	public String getActionStrutsName() {
		return actionStrutsName;
	}
	public void setActionStrutsName(String actionStrutsName) {
		this.actionStrutsName = actionStrutsName;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	
}
