package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class GetPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String tmp = request.getParameter("tranNo");
			System.out.println("tmp///////"+tmp);
			int tranNo = Integer.parseInt(tmp);
			
			PurchaseService service = new PurchaseServiceImpl();
			Purchase vo = service.getPurchase(tranNo);
			System.out.println("ACTION//////"+vo);		
			request.setAttribute("purchase", vo);
			
		return "forward:/purchase/getPurchaseView.jsp";
	}

}
