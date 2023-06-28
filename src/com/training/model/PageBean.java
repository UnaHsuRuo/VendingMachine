package com.training.model;

import java.util.Set;

public class PageBean {
	// 頁碼索引
	private int pageIndex;
	// 每頁的容量
	private int pageSize;
	// 總頁數
	private int pageCount;
	// 分頁後的資料集合
	private Set<Goods> dataList;
	// 資料總條數
	private int total;
	// 準備一個集合顯示分頁條數
	private int[] bar;
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public Set<Goods> getDataList() {
		return dataList;
	}
	public void setDataList(Set<Goods> dataList) {
		this.dataList = dataList;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int[] getBar() {
		return bar;
	}
	public void setBar(int[] bar) {
		this.bar = bar;
	}
	
}
