package com.greatfly.rams.basic.domain;

import java.sql.Timestamp;

import javax.persistence.Id;

import com.greatfly.common.annotation.Domain;

/**
 * MdDatawindowFactor entity. @author MyEclipse Persistence Tools
 */

@Domain("数据窗配置")
public class MdDatawindowFactor implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6287145962435927287L;
	// Fields
	@Id
	private Long pkid;
	private String dwName;
	private String pcType;
	private String statCodeArray;
	private String idCode;
	private String applyRole;
	private String applyUser;
	private String sceneName;
	private String sqlStr;
	private String sqlStr1;
	private String updTable;
	private String updColumn;
	private String prmColumn;
	private String disColumn;
	private String hideColumn;
	private String columnInfo1;
	private String columnInfo2;
	private String columnInfo3;
	private String columnInfo4;
	private String columnInfo5;
	private String columnInfo6;
	private String columnInfo7;
	private String columnInfo8;
	private String columnInfo9;
	private String columnInfo10;
	private String dwInfo;
	private String remark;
	private String createUser;
	private Timestamp createTime;
	private String modifyUser;
	private Timestamp modifyTime;

	// Constructors

	/** default constructor */
	public MdDatawindowFactor() {
	}

	/** minimal constructor */
	public MdDatawindowFactor(String dwName, String pcType,
			String statCodeArray, String idCode, String applyRole,
			String applyUser, String sceneName) {
		this.dwName = dwName;
		this.pcType = pcType;
		this.statCodeArray = statCodeArray;
		this.idCode = idCode;
		this.applyRole = applyRole;
		this.applyUser = applyUser;
		this.sceneName = sceneName;
	}

	/** full constructor */
	public MdDatawindowFactor(String dwName, String pcType,
			String statCodeArray, String idCode, String applyRole,
			String applyUser, String sceneName, String sqlStr, String sqlStr1,
			String updTable, String updColumn, String prmColumn,
			String disColumn, String hideColumn, String columnInfo1,
			String columnInfo2, String columnInfo3, String columnInfo4,
			String columnInfo5, String columnInfo6, String columnInfo7,
			String columnInfo8, String columnInfo9, String columnInfo10,
			String dwInfo, String remark, String createUser,
			Timestamp createTime, String modifyUser, Timestamp modifyTime) {
		this.dwName = dwName;
		this.pcType = pcType;
		this.statCodeArray = statCodeArray;
		this.idCode = idCode;
		this.applyRole = applyRole;
		this.applyUser = applyUser;
		this.sceneName = sceneName;
		this.sqlStr = sqlStr;
		this.sqlStr1 = sqlStr1;
		this.updTable = updTable;
		this.updColumn = updColumn;
		this.prmColumn = prmColumn;
		this.disColumn = disColumn;
		this.hideColumn = hideColumn;
		this.columnInfo1 = columnInfo1;
		this.columnInfo2 = columnInfo2;
		this.columnInfo3 = columnInfo3;
		this.columnInfo4 = columnInfo4;
		this.columnInfo5 = columnInfo5;
		this.columnInfo6 = columnInfo6;
		this.columnInfo7 = columnInfo7;
		this.columnInfo8 = columnInfo8;
		this.columnInfo9 = columnInfo9;
		this.columnInfo10 = columnInfo10;
		this.dwInfo = dwInfo;
		this.remark = remark;
		this.createUser = createUser;
		this.createTime = createTime;
		this.modifyUser = modifyUser;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public Long getPkid() {
		return this.pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getDwName() {
		return this.dwName;
	}

	public void setDwName(String dwName) {
		this.dwName = dwName;
	}

	public String getPcType() {
		return this.pcType;
	}

	public void setPcType(String pcType) {
		this.pcType = pcType;
	}

	public String getStatCodeArray() {
		return this.statCodeArray;
	}

	public void setStatCodeArray(String statCodeArray) {
		this.statCodeArray = statCodeArray;
	}

	public String getIdCode() {
		return this.idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getApplyRole() {
		return this.applyRole;
	}

	public void setApplyRole(String applyRole) {
		this.applyRole = applyRole;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getSceneName() {
		return this.sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSqlStr() {
		return this.sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlStr1() {
		return this.sqlStr1;
	}

	public void setSqlStr1(String sqlStr1) {
		this.sqlStr1 = sqlStr1;
	}

	public String getUpdTable() {
		return this.updTable;
	}

	public void setUpdTable(String updTable) {
		this.updTable = updTable;
	}

	public String getUpdColumn() {
		return this.updColumn;
	}

	public void setUpdColumn(String updColumn) {
		this.updColumn = updColumn;
	}

	public String getPrmColumn() {
		return this.prmColumn;
	}

	public void setPrmColumn(String prmColumn) {
		this.prmColumn = prmColumn;
	}

	public String getDisColumn() {
		return this.disColumn;
	}

	public void setDisColumn(String disColumn) {
		this.disColumn = disColumn;
	}

	public String getHideColumn() {
		return this.hideColumn;
	}

	public void setHideColumn(String hideColumn) {
		this.hideColumn = hideColumn;
	}

	public String getColumnInfo1() {
		return this.columnInfo1;
	}

	public void setColumnInfo1(String columnInfo1) {
		this.columnInfo1 = columnInfo1;
	}

	public String getColumnInfo2() {
		return this.columnInfo2;
	}

	public void setColumnInfo2(String columnInfo2) {
		this.columnInfo2 = columnInfo2;
	}

	public String getColumnInfo3() {
		return this.columnInfo3;
	}

	public void setColumnInfo3(String columnInfo3) {
		this.columnInfo3 = columnInfo3;
	}

	public String getColumnInfo4() {
		return this.columnInfo4;
	}

	public void setColumnInfo4(String columnInfo4) {
		this.columnInfo4 = columnInfo4;
	}

	public String getColumnInfo5() {
		return this.columnInfo5;
	}

	public void setColumnInfo5(String columnInfo5) {
		this.columnInfo5 = columnInfo5;
	}

	public String getColumnInfo6() {
		return this.columnInfo6;
	}

	public void setColumnInfo6(String columnInfo6) {
		this.columnInfo6 = columnInfo6;
	}

	public String getColumnInfo7() {
		return this.columnInfo7;
	}

	public void setColumnInfo7(String columnInfo7) {
		this.columnInfo7 = columnInfo7;
	}

	public String getColumnInfo8() {
		return this.columnInfo8;
	}

	public void setColumnInfo8(String columnInfo8) {
		this.columnInfo8 = columnInfo8;
	}

	public String getColumnInfo9() {
		return this.columnInfo9;
	}

	public void setColumnInfo9(String columnInfo9) {
		this.columnInfo9 = columnInfo9;
	}

	public String getColumnInfo10() {
		return this.columnInfo10;
	}

	public void setColumnInfo10(String columnInfo10) {
		this.columnInfo10 = columnInfo10;
	}

	public String getDwInfo() {
		return this.dwInfo;
	}

	public void setDwInfo(String dwInfo) {
		this.dwInfo = dwInfo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}