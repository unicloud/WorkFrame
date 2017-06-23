package com.greatfly.rams.basic.domain;

import java.util.Date;

import javax.persistence.Id;

import com.alibaba.fastjson.annotation.JSONField;
import com.greatfly.common.annotation.Domain;

/**
 * SysmUser entity. @author MyEclipse Persistence Tools
 */
@Domain("系统用户")
public class StUser implements java.io.Serializable {

	// Fields
	
	private static final long serialVersionUID = -5890837024129653246L;
	@Id
	private Long pkid;
	private String organCode;
	private String unitName;
	private String deptName;
	private String officeName;
	private String dutyName;
	private String pcode;
	private String defComany;
	private String userName;
	private String smplName;
	private String fullName;
	private String ekpMail;
	private String mobile;
	private Integer virtualFlag;
	private Integer loginWay;
	private String userPassword;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date pswdDate;
	private Integer status;
	private Integer lockFlag;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lockDate;
	private String createUser;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String modifyUser;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String dealIdcode;


	// Property accessors

	public Long getPkid() {
		return this.pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getDefComany() {
		return defComany;
	}

	public void setDefComany(String defComany) {
		this.defComany = defComany;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSmplName() {
		return smplName;
	}

	public void setSmplName(String smplName) {
		this.smplName = smplName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEkpMail() {
		return ekpMail;
	}

	public void setEkpMail(String ekpMail) {
		this.ekpMail = ekpMail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getVirtualFlag() {
		return virtualFlag;
	}

	public void setVirtualFlag(Integer virtualFlag) {
		this.virtualFlag = virtualFlag;
	}

	public Integer getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(Integer loginWay) {
		this.loginWay = loginWay;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getPswdDate() {
		return pswdDate;
	}

	public void setPswdDate(Date pswdDate) {
		this.pswdDate = pswdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDealIdcode() {
		return dealIdcode;
	}

	public void setDealIdcode(String dealIdcode) {
		this.dealIdcode = dealIdcode;
	}

}