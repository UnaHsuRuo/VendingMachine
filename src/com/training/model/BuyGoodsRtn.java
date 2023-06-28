package com.training.model;

import java.util.Map;

public class BuyGoodsRtn {
	
	private int inputMoney;
	private int change;
	private int buyAmount;
	private Map<Goods, Integer> shoppingCartGoods;
	
	public BuyGoodsRtn(int inputMoney, int change, int buyAmount,
			Map<Goods, Integer> shoppingCartGoods) {
		super();
		this.inputMoney = inputMoney;
		this.change = change;
		this.buyAmount = buyAmount;
		this.shoppingCartGoods = shoppingCartGoods;
	}
	
	public int getInputMoney() {
		return inputMoney;
	}
	public void setInputMoney(int inputMoney) {
		this.inputMoney = inputMoney;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
	public int getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
	public Map<Goods, Integer> getShoppingCartGoods() {
		return shoppingCartGoods;
	}
	public void setShoppingCartGoods(Map<Goods, Integer> shoppingCartGoods) {
		this.shoppingCartGoods = shoppingCartGoods;
	}
	
	
	
	

}
