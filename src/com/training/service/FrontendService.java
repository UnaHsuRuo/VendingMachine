package com.training.service;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.dao.FrontendDao;
import com.training.model.Goods;
import com.training.model.Member;

public class FrontendService {

	private static FrontendService frontendService = new FrontendService();

	private FrontendService() {
	}

	public static FrontendService getInstance() {
		return frontendService;
	}

	private FrontendDao frontEndDao = FrontendDao.getInstance();
	
	public Member queryMemberByIdentificationNo(String identificationNo) {
		return frontEndDao.queryMemberByIdentificationNo(identificationNo);
	}

	public List<Goods> searchGoods(String searchKeyword,int start,int end) {
		return frontEndDao.searchGoods(searchKeyword,start,end);
	}

	public List<Goods> searchAllGoods(String searchKeyword) {
		return frontEndDao.searchAllGoods(searchKeyword);
	}
	
	public int getBuyAmount(Map<Goods,Integer> shoppingCartGoods) {
		int buyAmount =0;
		Set<Goods> goods = shoppingCartGoods.keySet();
		Iterator<Goods> iterator = goods.iterator();
		while(iterator.hasNext()){
			Goods good = iterator.next();
			buyAmount += good.getGoodsPrice() * shoppingCartGoods.get(good);
		}
		return buyAmount;
	}
	
	public Set<Goods> orderRenew(Map<Goods,Integer> shoppingCartGoods) {
		Set<Goods> orderRenew = new LinkedHashSet<>();
		Iterator<Goods> cartGoodsIterator = shoppingCartGoods.keySet().iterator();
		while(cartGoodsIterator.hasNext()){
			Goods goods = cartGoodsIterator.next();
			Integer dbQuantity = goods.getGoodsQuantity();
			Integer buyQuantity = shoppingCartGoods.get(goods);
			//新的庫存數=原庫存-購買數量
			int newQuantity = dbQuantity - buyQuantity ;
			//更新庫存數量
			goods.setGoodsQuantity(newQuantity);
			orderRenew.add(goods);
		}
		return orderRenew;
	}

}
