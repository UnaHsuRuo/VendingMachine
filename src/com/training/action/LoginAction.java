package com.training.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.FrontendDao;
import com.training.model.Member;

public class LoginAction extends DispatchAction {
	
	private FrontendDao frontendDao = FrontendDao.getInstance();
	
	/**
	 * info:這是負責"登入"的action method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward login(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 登入請求
    	ActionForward actFwd = null;
    	HttpSession session = request.getSession();
    	String inputID = request.getParameter("username");
        String inputPwd = request.getParameter("password");
        // Step2:依使用者所輸入的帳戶名稱取得 Member
        Member member = frontendDao.queryMemberByIdentificationNo(inputID);
        String loginMsg = null;
    	if(member != null) {
    		// Step3:取得帳戶後進行帳號、密碼比對
    		String id = member.getIdentificationNo();
    		String pwd = member.getPassword();
    		if(id.equals(inputID) && pwd.equals(inputPwd)) {
    			loginMsg = member.getCustomerName() + " 先生/小姐您好!";
    			// 將member存入session scope 以供LoginCheckFilter之後使用!
    			session.setAttribute("member", member);
    			actFwd = mapping.findForward("success");        			
    		} else {
                // Step4:帳號、密碼錯誤,轉向到 "/BankLogin.html" 要求重新登入.
    			loginMsg = "帳號或密碼錯誤";
    			actFwd = mapping.findForward("fail");
    		}
    	} else {
            // Step5:無此帳戶名稱,轉向到 "/MemberLogin.jsp" 要求重新登入.
    		loginMsg = "無此帳戶名稱,請重新輸入!";	
    		actFwd = mapping.findForward("fail");
    	}
    	request.setAttribute("loginMsg", loginMsg);
    	
    	return actFwd;
    }
    
    /**
     * info:這是負責"登出"的action method
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward logout(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 登出請求
    	HttpSession session = request.getSession();
		session.removeAttribute("member");
		request.setAttribute("loginMsg", "謝謝您的光臨!");
		session.removeAttribute("shoppingCartGoods");
    	return mapping.findForward("fail");
    }
}
