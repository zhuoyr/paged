package com.zhuogg.paged.json;

import java.util.List;

public class JsonResult<T> {
	private boolean ok;
	private Integer pageNo ;
	private Integer pageCount;
	private Integer pageSize;
	private Integer rowsCount;
	private List<T> rows ;
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		if(rows == null){
			setRowsCount(0);
		}else{
			setRowsCount(rows.size());
		}
		this.rows = rows;
	}

	public boolean hasNext() {
		return getRowsCount() != 0;
	}

	public Integer getRowsCount() {
		return rowsCount == null?0:rowsCount;
	}

	public void setRowsCount(Integer rowsCount) {
		this.rowsCount = rowsCount;
	}
	
	
}
