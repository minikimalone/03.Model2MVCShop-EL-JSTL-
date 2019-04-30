package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		Product proVO = new Product();
		proVO.setProdName(request.getParameter("prodName"));
		proVO.setProdDetail(request.getParameter("prodDetail"));
		proVO.setManuDate(request.getParameter("manuDate"));
		proVO.setPrice(Integer.parseInt(request.getParameter("price")));
		proVO.setFileName(request.getParameter("fileName"));
		
		System.out.println(proVO);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(proVO);
		
		request.setAttribute("pvo", proVO);
		
		return "forward:/product/getProduct.jsp";
	}

}
