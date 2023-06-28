package com.training.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.training.formbean.BackendActionForm;
import com.training.model.Goods;
import com.training.model.UpdateResult;
import com.training.service.BackendService;
import com.training.vo.SalesReport;

public class BackendAction extends DispatchAction {

	private BackendService backendService = BackendService.getInstance();

	public ActionForward queryGoods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 分頁
		String pageNo = request.getParameter("pageNo");
		request.setAttribute("pageNo", pageNo);

		List<Goods> goods = backendService.queryGoods();
		request.setAttribute("goods", goods);

		int numPages = goods.size();
		request.setAttribute("numPages", numPages);

		return mapping.findForward("goodsListView");
	}

	public ActionForward createGoods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 將表單資料綁定到 BackendActionForm
		BackendActionForm backendform = (BackendActionForm) form;

		// 創建 Goods 物件並從 BackendActionForm 複製屬性
		Goods goods = new Goods();
		BeanUtils.copyProperties(goods, backendform);

		// 獲取商品圖片並設置到 Goods 物件
		FormFile goodsImage = backendform.getGoodsImage();
		goods.setGoodsImageName(goodsImage.getFileName());

		// 呼叫 backendService 的 createGoods 方法進行商品新增上架操作
		int createResult = backendService.createGoods(goods);

		// 設置成功訊息到 HttpSession
		String msg = "商品編號: " + createResult;
		request.setAttribute("msg", msg);
		String message = createResult != 0 ? "上架新增成功！" : "上架新增失敗！";
		request.setAttribute("createMsg", message);

		// 上傳商品圖片
		uploadGoodsImgFile(goodsImage);

		// 構建 JSON 格式的響應數據
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("msg", msg);
		jsonResponse.put("createMsg", message);

		// 設置響應標頭並將 JSON 數據寫入響應
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse.toString());

		return null;
	}

	private void uploadGoodsImgFile(FormFile goodsImage) throws Exception {
		String fileName = goodsImage.getFileName();
		String contentType = goodsImage.getContentType();
		byte[] fileData = goodsImage.getFileData();
		String basePath = "/home/VendingMachine/DrinksImage/"; 
		File file = new File(basePath + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(fileData);
		fos.close();
	}

	public ActionForward createGoodsView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		return mapping.findForward("createGoodsView");
	}


	public ActionForward modifyGoodsView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 商品選單資料
	    List<Goods> goods = backendService.queryGoods();
	    request.setAttribute("goods", goods);

	    // 被選擇要修改的商品資料
	    String goodsID = request.getParameter("goodsID");
	    goodsID = (goodsID != null) ? goodsID : String.valueOf(request.getSession().getAttribute("modifyGoodsID"));

	    if (goodsID == null) {
	        goodsID = String.valueOf(request.getSession().getAttribute("modifyGoodsID"));
	    } else  {
	        try {
	            int id = Integer.parseInt(goodsID);
	            Goods good = backendService.queryGoodsByID(id);
	            request.setAttribute("modifyGood", good);
	            
	        } catch (NumberFormatException e) {
	        }
	    }

	    return mapping.findForward("modifyGoodsView");
	}
	
	 // for AJAX 使用
    public ActionForward getModifyGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String goodsID = request.getParameter("goodsID");
    	int id = Integer.parseInt(goodsID);
        Goods good = backendService.queryGoodsByID(id);
        request.setAttribute("modifyGood", good);
        
	    // 將結果封裝為JSON格式
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("modifyGood", good);
	    jsonObject.put("goodsPrice", good.getGoodsPrice()); 
	    jsonObject.put("goodsQuantity", good.getGoodsQuantity()); 

	    // 返回JSON數據
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonObject.toString());
	    out.flush();
	    out.close();
		
    	return null;
    }

	public ActionForward modifyGoods(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // 將表單資料使用 Struts ActionForm 方式自動綁定，省去多次由 request getParameter 取表單資料的工作
	    BackendActionForm backendform = (BackendActionForm) form;

	    // 將 Struts BackedActionForm 資料複製到 Goods
	    // 將表單資料轉換儲存資料物件(commons-beanutils-1.8.0.jar)
	    Goods goods = new Goods();
	    BeanUtils.copyProperties(goods, backendform);

	    UpdateResult modifyResult = backendService.modifyGoods(goods);
	    request.setAttribute("goodsName", goods.getGoodsName()); 
	    request.setAttribute("goodsPrice", goods.getGoodsPrice()); 
	    request.setAttribute("goodsQuantity", goods.getGoodsQuantity());
	    
	    String msg = "商品編號: ";
	    request.setAttribute("msg", msg);
	    String message = modifyResult != null ? " 更新作業成功！" : " 更新作業失敗！";
	    request.setAttribute("modifyMSG", message);
	    request.setAttribute("modifyGoodsID", goods.getGoodsID());

	    // 將結果封裝為JSON格式
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("msg", msg);
	    jsonObject.put("modifyMSG", message);
	    jsonObject.put("modifyGoodsID", goods.getGoodsID());
	    jsonObject.put("goodsName", goods.getGoodsName()); 
	    jsonObject.put("goodsPrice", goods.getGoodsPrice()); 
	    jsonObject.put("goodsQuantity", goods.getGoodsQuantity());

	    // 返回JSON數據
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonObject.toString());
	    out.flush();
	    out.close();

	    return null;
	}

	public ActionForward querySalesReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String queryStartDate = request.getParameter("queryStartDate");
		String queryEndDate = request.getParameter("queryEndDate");
		if (queryStartDate != null && queryEndDate != null) {
			Set<SalesReport> salesReport = backendService
					.queryOrderBetweenDate(queryStartDate, queryEndDate);
			request.setAttribute("salesReport", salesReport);
		}

		return mapping.findForward("saleReportView");
	}
}
