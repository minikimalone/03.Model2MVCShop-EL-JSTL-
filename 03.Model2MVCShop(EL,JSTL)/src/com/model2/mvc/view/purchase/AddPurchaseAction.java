package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class AddPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			HttpSession session = request.getSession(true);
			User buyer =(User) session.getAttribute("user");
			Purchase purvo = new Purchase();
			
			
			
			String prodNo = request.getParameter("prodNo");
			String paymentOption = request.getParameter("paymentOption");
			String receiverName = request.getParameter("receiverName");
			String receiverPhone = request.getParameter("receiverPhone");
			String divyAddr = request.getParameter("receiverAddr");
			String divyRequest = request.getParameter("receiverRequest");
			String divyDate = request.getParameter("receiverDate");
		
			
			ProductService service=new ProductServiceImpl();
			Product proVO = service.getProduct(Integer.parseInt(prodNo));			
			
			Map<String,Object> prod = new HashMap<String,Object>();
	
			prod.put("prodNo", Integer.parseInt(prodNo));
			
		
			purvo.setBuyer(buyer);
			purvo.setDivyAddr(divyAddr);
			purvo.setDivyDate(divyDate);
			purvo.setDivyRequest(divyRequest);
			purvo.setPaymentOption(paymentOption);
			purvo.setPurchaseProd(proVO);
			purvo.setReceiverName(receiverName);
			purvo.setReceiverPhone(receiverPhone);
		
			System.out.println(purvo);
			
			
			
			PurchaseService servicePur = new PurchaseServiceImpl();
			
			servicePur.addPurchase(purvo);
			request.setAttribute("purchase", purvo);
			
			
			
			
			return "forward:/purchase/addPurchase.jsp";
	}

}
