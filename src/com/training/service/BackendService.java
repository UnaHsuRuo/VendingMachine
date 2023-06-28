package com.training.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import com.training.dao.BackendDao;
import com.training.model.Goods;
import com.training.model.UpdateResult;
import com.training.vo.SalesReport;

public class BackendService {
	public final static SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
	
	private static BackendService backendService = new BackendService();

	private BackendService(){ }	
	
	public static BackendService getInstance(){
		return backendService;
	}

	public Goods queryGoodsByID(int goodsID) {
		return backendDao.queryGoodsByID(goodsID);
	}

	private BackendDao backendDao = BackendDao.getInstance();
	
	public List<Goods> queryGoods() {
		
		return backendDao.queryGoods();
	}
	
	public UpdateResult modifyGoods(Goods goods) {
		UpdateResult result = null;
		//查出飲料現有庫存
		Goods dbGoods = backendDao.queryGoodsByID(goods.getGoodsID());
		int curretQuantity = dbGoods.getGoodsQuantity();
		//更新庫存 = 現有庫存 + 補貨數量
		int newQuantity = curretQuantity + goods.getGoodsQuantity();
		dbGoods.setGoodsQuantity(newQuantity);
		//更新售價
		dbGoods.setGoodsPrice(goods.getGoodsPrice());
		//更新商品狀態
		dbGoods.setStatus(goods.getStatus());
		//更新商品
		result = backendDao.updateGoods(dbGoods);
		
		return result;
	}
	
	public int createGoods(Goods goods) {
		return backendDao.createGoods(goods);
	}

	public Set<SalesReport> queryOrderBetweenDate(String queryStartDate,
			String queryEndDate) throws ParseException {
		return backendDao.queryOrderBetweenDate(queryStartDate, queryEndDate);
	}



}
