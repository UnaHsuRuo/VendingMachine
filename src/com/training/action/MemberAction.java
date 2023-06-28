package com.training.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.BackendDao;
import com.training.model.Goods;
import com.training.service.BackendService;
import com.training.vo.ShoppingCartGoods;

@MultipartConfig
public class MemberAction extends DispatchAction {
	
	private BackendDao backendDao = BackendDao.getInstance();
	
	public ActionForward addCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // 創建一個JSONObject對象
	    JSONObject jsonObject = new JSONObject();
	    
	    // 設置響應的字符編碼和內容類型
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    
	    // 獲取當前會話的HttpSession對象
	    HttpSession session = request.getSession();
	    
	    // 從請求參數中獲取商品ID和購買數量，並查詢數據庫獲取商品信息
	    String goodsIDs = request.getParameter("goodsID");
	    Integer buyQuantitys = Integer.parseInt(request.getParameter("buyQuantity"));
	    Goods goodsInDB = backendDao.queryGoodsByID(Integer.parseInt(goodsIDs));
	    
	    // 根據購買數量和庫存判斷是否存在錯誤信息
	    String message = null;
	    if (buyQuantitys.equals(0))
	        message = "購買數量 0 請重新輸入！";
	    if (Integer.valueOf(goodsInDB.getGoodsQuantity()).equals(0) && message == null)
	        message = "庫存不足，請購買其他商品！";
	    if (buyQuantitys > goodsInDB.getGoodsQuantity() && message == null)
	        message = "購買數量已超過上限，請重新輸入！";
	    
	    // 如果存在錯誤信息，將錯誤信息以JSON格式輸出到響應中並返回
	    if (message != null) {
	        jsonObject.put("message", message);
	        out.println(JSONObject.fromObject(jsonObject));
	        out.flush();
	        out.close();
	        return null;
	    }

	    Map<Goods,Integer> shoppingCartGoods = null;
	    // 如果session中不存在"shoppingCartGoods"屬性，創建一個新的LinkedHashMap，並將商品和購買數量添加到其中
	    if (session.getAttribute("shoppingCartGoods") == null) {
	        shoppingCartGoods = new LinkedHashMap<>();
	        shoppingCartGoods.put(goodsInDB, buyQuantitys);
	        session.setAttribute("shoppingCartGoods", shoppingCartGoods);
	    } else {
	        // 如果session中存在"shoppingCartGoods"屬性，從session中獲取購物車商品的信息
	        shoppingCartGoods = (Map<Goods,Integer>)session.getAttribute("shoppingCartGoods");
	        if (shoppingCartGoods.containsKey(goodsInDB)) {
	            // 如果購物車中已存在該商品，更新該商品的購買數量
	            Goods goodsInCart = null;
	            for (Goods goods : shoppingCartGoods.keySet()) {
	                if (Integer.valueOf(goods.getGoodsID()).equals(goodsInDB.getGoodsID())) {
	                    goodsInCart = goods;
	                }
	                break;
	            }
	            Integer newBuyQuantity = buyQuantitys + shoppingCartGoods.get(goodsInCart);
	            shoppingCartGoods.put(goodsInCart, newBuyQuantity);
	            session.setAttribute("shoppingCartGoods", shoppingCartGoods);
	        } else {
	            // 如果購物車中不存在該商品，將商品和購買數量添加到購物車中
	            shoppingCartGoods.put(goodsInDB, buyQuantitys);
	            session.setAttribute("shoppingCartGoods", shoppingCartGoods);
	        }
	    }

	    // 將商品信息以JSON格式輸出到響應中
	    jsonObject.put("goodsName", goodsInDB.getGoodsName());
	    jsonObject.put("buyQuantity", buyQuantitys);
	    jsonObject.put("goodsPrice", goodsInDB.getGoodsPrice());
	    out.println(JSONObject.fromObject(jsonObject));
	    out.flush();
	    out.close();

	    return null;
	}

	// 清空購物車
	public ActionForward clearCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
	    HttpSession session = request.getSession();
	    session.removeAttribute("shoppingCartGoods");

	    return null;
	}
}
