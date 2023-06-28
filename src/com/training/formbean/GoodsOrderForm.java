package com.training.formbean;

import org.apache.struts.action.ActionForm;

public class GoodsOrderForm extends ActionForm {
	
	private String searchKeyword;
	private String[] GoodsIDs;
	private String CustomerID;
	private int inputMoney;
	private String[] buyQuantitys;
	
	public String[] getBuyQuantitys() {
		return buyQuantitys;
	}
	public void setBuyQuantitys(String[] buyQuantitys) {
		this.buyQuantitys = buyQuantitys;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String[] getGoodsIDs() {
		return GoodsIDs;
	}
	public void setGoodsIDs(String[] goodsIDs) {
		this.GoodsIDs = goodsIDs;
	}
	public int getInputMoney() {
		return inputMoney;
	}
	public void setInputMoney(int inputMoney) {
		this.inputMoney = inputMoney;
	}
	
	

}
