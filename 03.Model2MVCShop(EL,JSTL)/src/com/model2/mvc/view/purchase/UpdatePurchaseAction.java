package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tmp = request.getParameter("tranNo");
		int tranNo = Integer.parseInt(tmp);
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase vo = service.getPurchase(tranNo);
		vo.setPaymentOption(request.getParameter("paymentOption"));
		vo.setReceiverName(request.getParameter("receiverName"));
		vo.setReceiverPhone(request.getParameter("receiverPhone"));
		vo.setDivyAddr(request.getParameter("receiverAddr"));
		vo.setDivyRequest(request.getParameter("receiverRequest"));
		vo.setDivyDate(request.getParameter("divyDate"));
		System.out.println("업데이트전 입력할 vo///////////"+vo);
		service.updatePurchase(vo);
		
		vo = service.getPurchase(tranNo);
		System.out.println("업데이트후 찾아온 vo///////////"+vo);
		
		
		return "redirect:getPurchase.do?tranNo="+tmp;
	}

}
