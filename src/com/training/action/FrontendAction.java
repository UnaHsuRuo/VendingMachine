package com.training.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.FrontendDao;
import com.training.model.BuyGoodsRtn;
import com.training.model.Goods;
import com.training.model.Member;
import com.training.service.FrontendService;

public class FrontendAction extends DispatchAction {

	private FrontendService frontendService = FrontendService.getInstance();
	private FrontendDao frontEndDao = FrontendDao.getInstance();

	public ActionForward searchGoods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String searchKeyword = request.getParameter("searchKeyword");
		String pageNo = request.getParameter("pageNo");
		System.out.print(pageNo);
		if (pageNo == null || pageNo.equals("")) {
			pageNo = "1";
		}

		request.setAttribute("pageNo", pageNo);
		request.setAttribute("searchKeyword", searchKeyword);
		
		//有搜尋關鍵字
		if (searchKeyword != null && searchKeyword != "") {
			int end = Integer.parseInt(pageNo) * 6;
			int start = end - 5;
			//透過關鍵字，找出所有商品
			List<Goods> totalGoods = frontendService.searchAllGoods(searchKeyword);
			//透過關鍵字，找出指定頁數的六筆商品
			List<Goods> goods = frontendService.searchGoods(searchKeyword,start,end);
			request.setAttribute("goods", goods);
			//此關鍵字共有多少商品總數
			int numPages = totalGoods.size();
			request.setAttribute("numPages", numPages);
		//無關鍵字，直接瀏覽資料庫中所有商品
		} else {
			int end = Integer.parseInt(pageNo) * 6;
			int start = end - 5;
			//無關鍵字，找出所有商品
			List<Goods> goods = frontendService.searchAllGoods(searchKeyword);
			//無關鍵字，找出指定頁數的六筆商品
			List<Goods> pageGoods = frontendService.searchGoods(searchKeyword,start,end);
			request.setAttribute("goods", pageGoods);
			//共有多少商品總數
			int numPages = goods.size();
			request.setAttribute("numPages", numPages);
		}

		return mapping.findForward("VendingMachineCartView");
	}

	public ActionForward buyGoods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse responses)
			throws Exception {
		HttpSession session = request.getSession();
		
		int inputMoney = Integer.parseInt(request.getParameter("inputMoney"));
	    Member member = (Member) session.getAttribute("member");
	    String memberNo = member.getIdentificationNo();
	    
	    Map<Goods,Integer> shoppingCartGoods = (Map<Goods,Integer>)session.getAttribute("shoppingCartGoods");
	    
	    if(shoppingCartGoods==null) return mapping.findForward("buyGoods");
	    
	    //計算購物車中，所有商品的總金額
	    int buyAmount = frontendService.getBuyAmount(shoppingCartGoods);
	    
	    //投入金額>購買金額
	    if (inputMoney >= buyAmount) {
	    	//找零金額=投入金額-購買金額
	    	int change = inputMoney - buyAmount;
	    	//新增訂單
	    	boolean insertSuccess = frontEndDao.batchCreateGoodsOrder(shoppingCartGoods, memberNo);
	    	//如果訂單新增成功
	    	if(insertSuccess){
	    		Set<Goods> set = frontendService.orderRenew(shoppingCartGoods);
	    		//就進行庫存更新
	    		boolean updateSuccess = frontEndDao.batchUpdateGoodsQuantity(set);
	    		if(updateSuccess) session.removeAttribute("shoppingCartGoods");
	    	}
	    BuyGoodsRtn buyGoodsRtn = new BuyGoodsRtn(inputMoney,change,buyAmount,shoppingCartGoods);
	    session.setAttribute("buyGoodsRtn",buyGoodsRtn);
	    }

		return mapping.findForward("searchGoods");
	}

}
