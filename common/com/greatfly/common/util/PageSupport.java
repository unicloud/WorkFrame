package com.greatfly.common.util;

import java.util.List;
import java.util.Map;

/**
 * 分页查询支持类
 * 
 * @author wuwq
 * Jul 26, 2010   8:45:43 AM
 */
public final class PageSupport<T> {
	
	/**
	 * 分页数据集
	 */
	private List<Map<String, Object>> items;
    /**
     * 当前页码，默认第1页
     */
	private int pageIndex = 1;
    /**
     * 分页大小
     */
	private int pageSize = 20;
    /**
     * 总数据数，还未设置时为-1，表示还没有查询
     */
	private int totalRows = -1;
    /**
     * 最大页码，还未设置时为-1，表示还没有查询
     */
	private int maxPages = -1;
	/**
	 * 合计对象
	 */
	private ITotal total;
	

	/**
     * 无参数构造方法,主要用于不分页
     */
    public PageSupport() {
        
    }

    /**
     * 根据当前页创建分页对象
     * @param pageIndex 当前页
     */
    public PageSupport(int pageIndex) {
        if (pageIndex > 0) {
            this.pageIndex = pageIndex;
        } else {
            this.pageIndex = 1;
        }
    }

    /**
     * 根据当前页和分页的大小创建分页对象
     * @param pageIndex 当前页码
     * @param pageSize 分页大小
     */
    public PageSupport(int pageIndex, int pageSize) {
        this(pageIndex);
        if (pageSize > 0) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 1;
        }
    }

    /**
     * 根据当前页，分页大小，总记录数创建分页对象，如果当前页超过总页数，则当前页指向最后一页
     * @param pageIndex 当前页码
     * @param pageSize 分页大小
     * @param totalCounts 总记录数
     */
    public PageSupport(int pageIndex, int pageSize, int totalCounts) {
        this(pageIndex, pageSize);
        if (totalCounts >= 0) {
            this.totalRows = totalCounts;
        } else {
            this.totalRows = 0;
        }
        this.maxPages = getMaxPages();
        if (pageIndex > maxPages) {
            this.pageIndex = maxPages;
        }
    }
    
    public int getPageIndex() {
        return pageIndex;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 获取最大页数
     * @return 最大页数
     */
    public int getMaxPages() {
    	if (maxPages == -1) {
	        maxPages = PageUtil.getMaxPages(pageSize, totalRows);
    	}
        return maxPages;
    }
    
    public int getHibernateSeq() {
        return (pageIndex - 1) * pageSize;
    }
    
    public int getTotalRows() {
        return totalRows;
    }
    
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
    
    public List<Map<String, Object>> getItems() {
        return items;
    }
    
    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	public ITotal getTotal() {
		return total;
	}

	public void setTotal(ITotal total) {
		this.total = total;
	}
    
}
