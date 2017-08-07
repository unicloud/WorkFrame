package com.greatfly.rams.basic.vo;

import com.greatfly.rams.basic.domain.MdDatawindowFactor;

/**
 * 数据窗配置vo
 * @author huangqb
 * 2017-01-12 15:47:18
 */
public class DatawindowFactorVo extends MdDatawindowFactor {
	
	 
	/**
	 * @Fields serialVersionUID : 默认值
	 */
	private static final long serialVersionUID = 4514996253434845792L;
	
	private String whereJson ;
	private int recordstartindex;
	private int recordendindex;
	private String sortdatafield;
	private String sortorder;
	
	private int filterscount;
	private int groupscount;
	private int pagenum;
	private int pagesize;

	public String getWhereJson() {
		return whereJson;
	}

	public void setWhereJson(String whereJson) {
		this.whereJson = whereJson;
	}

	public int getRecordstartindex() {
		return recordstartindex;
	}

	public void setRecordstartindex(int recordstartindex) {
		this.recordstartindex = recordstartindex;
	}

	public int getRecordendindex() {
		return recordendindex;
	}

	public void setRecordendindex(int recordendindex) {
		this.recordendindex = recordendindex;
	}

	public String getSortdatafield() {
		return sortdatafield;
	}

	public void setSortdatafield(String sortdatafield) {
		this.sortdatafield = sortdatafield;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}


	public int getFilterscount() {
		return filterscount;
	}

	public void setFilterscount(int filterscount) {
		this.filterscount = filterscount;
	}

	public int getGroupscount() {
		return groupscount;
	}

	public void setGroupscount(int groupscount) {
		this.groupscount = groupscount;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
}