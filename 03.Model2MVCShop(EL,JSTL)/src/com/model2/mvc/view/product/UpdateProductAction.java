package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class UpdateProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Product vo= new Product();
		int proNo = Integer.parseInt(request.getParameter("prodNo"));
		int price = Integer.parseInt(request.getParameter("price"));
		
		
		vo.setProdNo(proNo);
		vo.setProdName(request.getParameter("prodName"));
		vo.setProdDetail(request.getParameter("prodDetail"));
		vo.setManuDate(request.getParameter("manuDate"));
		vo.setPrice(price);
		vo.setFileName(request.getParameter("fileName"));
		
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(vo);
		System.out.println(vo);
		
		
		return "redirect:/getProduct.do?prodNo="+vo.getProdNo()+"&menu=up";
	}

}
