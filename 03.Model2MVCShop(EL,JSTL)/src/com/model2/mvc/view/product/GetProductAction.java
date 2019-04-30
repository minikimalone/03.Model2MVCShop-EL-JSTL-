package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class GetProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		String result = "forward:/product/getProductDetail.jsp";
		
		ProductService service=new ProductServiceImpl();
		Product vo= service.getProduct(prodNo);
		System.out.println(vo);
		request.setAttribute("vo", vo);
		
		String history = null;
		Cookie[] cookies = request.getCookies();
		if(cookies==null) {
			history = request.getParameter("prodNo");
		}else {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
				}
			}
			history += ","+request.getParameter("prodNo");
		}
		Cookie cookie = new Cookie("history",history);
		response.addCookie(cookie);
		
		if(menu.equals("manage")){
			result = "forward:/product/updateProductView.jsp";
		}else if(menu.equals("up")) {
			result = "forward:/product/updateProduct.jsp";
		}
		
		return result;
	}

}
