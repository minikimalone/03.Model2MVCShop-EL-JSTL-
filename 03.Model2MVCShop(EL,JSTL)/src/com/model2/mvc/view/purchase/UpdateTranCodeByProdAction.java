package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String s = request.getParameter("prodNo");
		int prodNo = Integer.parseInt(s);
		String tranCode = request.getParameter("tranCode");
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase vo = service.getPurchase2(prodNo);
		vo.setTranCode(tranCode);
		
		service.updateTranCode(vo);
		
		
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
