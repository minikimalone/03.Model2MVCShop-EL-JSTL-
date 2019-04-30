package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;



public class PurchaseDAO {
	
	public Purchase getPurchase(int prodNo) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "select * from TRANSACTION WHERE PROD_NO=?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		
		ResultSet rs = pstmt.executeQuery();
		
		Purchase vo = null;
		
		while(rs.next()) {
			vo = new Purchase();
			UserService uservice = new UserServiceImpl();
			ProductService pservice = new ProductServiceImpl();
			User buyer = uservice.getUser(rs.getString("BUYER_ID")); 
			Product purchaseProd = pservice.getProduct(rs.getInt("PROD_NO"));
			System.out.println(buyer+"//////"+purchaseProd);
			vo.setBuyer(buyer);
			vo.setPurchaseProd(purchaseProd);
			vo.setTranNo(rs.getInt("TRAN_NO"));
			vo.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
			vo.setDivyAddr(rs.getString("demailaddr"));
			vo.setDivyDate(rs.getString("DLVY_DATE"));
			vo.setOrderDate(rs.getDate("ORDER_DATA"));
			vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
			vo.setReceiverName(rs.getString("RECEIVER_NAME"));
			vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			
		}
		rs.close();
		pstmt.close();
		con.close();
		System.out.println(vo);

		return vo;
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		String sql = "select * from TRANSACTION WHERE TRAN_NO=?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, tranNo);
		
		ResultSet rs = pstmt.executeQuery();
		
		Purchase vo = null;
		UserService uservice = new UserServiceImpl();
		ProductService pservice = new ProductServiceImpl();
		while(rs.next()) {
			vo = new Purchase();
			User buyer = uservice.getUser(rs.getString("BUYER_ID")); 
			Product purchaseProd = pservice.getProduct(rs.getInt("PROD_NO"));
			System.out.println(buyer+"//////"+purchaseProd);
			vo.setBuyer(buyer);
			vo.setPurchaseProd(purchaseProd);
			vo.setTranNo(rs.getInt("TRAN_NO"));
			vo.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
			vo.setDivyAddr(rs.getString("demailaddr"));
			vo.setDivyDate(rs.getString("DLVY_DATE"));
			vo.setOrderDate(rs.getDate("ORDER_DATA"));
			vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
			vo.setReceiverName(rs.getString("RECEIVER_NAME"));
			vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			vo.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
			
		}
		rs.close();
		pstmt.close();
		con.close();
		
		System.out.println(vo);

		return vo;
		
	}
	
	public Map<String,Object> getPurchaseList(Search search,String userId)throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from TRANSACTION WHERE BUYER_ID = '"+userId+"'";
		
		sql += " order by tran_NO desc";
		System.out.println("PurDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
	
		System.out.println(search);

		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			
			UserService uservice = new UserServiceImpl();
			ProductService pservice = new ProductServiceImpl();
			Purchase vo = new Purchase();
			User buyer = uservice.getUser(rs.getString("BUYER_ID")); 
			Product purchaseProd = pservice.getProduct(rs.getInt("PROD_NO"));
			
			vo.setBuyer(buyer);
			vo.setPurchaseProd(purchaseProd);
			vo.setTranNo(rs.getInt("TRAN_NO"));
			vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			vo.setDivyAddr(rs.getString("demailaddr"));
			vo.setDivyDate(rs.getString("DLVY_DATE"));
			vo.setOrderDate(rs.getDate("ORDER_DATA"));
			vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
			vo.setReceiverName(rs.getString("RECEIVER_NAME"));
			vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			vo.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
			
			
			list.add(vo);
		}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pstmt.close();
		con.close();

		return map;
		
	}
		
		

	
	public Map<String, Object> getSaleList(Search search) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from TRANSACTION";
		
		sql += " order by tran_NO desc";
		System.out.println("PurDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql,search);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
	
		System.out.println(search);

		List<Purchase> list = new ArrayList<Purchase>();
		//UserService uservice = new UserServiceImpl();
		ProductService pservice = new ProductServiceImpl();
		while(rs.next()){
			
			Purchase vo = new Purchase();
			//User buyer = uservice.getUser(rs.getString("BUYER_ID")); 
			Product purchaseProd = pservice.getProduct(rs.getInt("PROD_NO"));
			
			//vo.setBuyer(buyer);
			vo.setPurchaseProd(purchaseProd);
			vo.setTranNo(rs.getInt("TRAN_NO"));
			vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			vo.setDivyAddr(rs.getString("demailaddr"));
			vo.setDivyDate(rs.getString("DLVY_DATE"));
			vo.setOrderDate(rs.getDate("ORDER_DATA"));
			vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
			vo.setReceiverName(rs.getString("RECEIVER_NAME"));
			vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			vo.setTranCode(rs.getString("TRAN_STATUS_CODE").substring(0,1));
			
			
			list.add(vo);
		}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pstmt.close();
		con.close();

		return map;
	}
	
	public void insertPurchase(Purchase vo) throws Exception{
		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values "
				+ "(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
				
		pstmt.setInt(1, vo.getPurchaseProd().getProdNo());
		pstmt.setString(2,vo.getBuyer().getUserId());
		pstmt.setString(3, vo.getPaymentOption());
		pstmt.setString(4, vo.getReceiverName());
		pstmt.setString(5, vo.getReceiverPhone());
		pstmt.setString(6, vo.getDivyAddr());
		pstmt.setString(7, vo.getDivyRequest());
		pstmt.setString(8, "1");
		pstmt.setString(9, vo.getDivyDate());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
	}
	
	public void updatePurchase(Purchase vo) throws Exception{
		
		System.out.println("////////////////////////////updateM//////////////");
		Connection con = DBUtil.getConnection();

		String sql = "update transaction set payment_option=?,receiver_name=?,receiver_phone=?,";
			   sql +="demailaddr=?,dlvy_request=?,DLVY_DATE=? where tran_no=?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, vo.getPaymentOption());
		pstmt.setString(2, vo.getReceiverName());
		pstmt.setString(3, vo.getReceiverPhone());
		pstmt.setString(4, vo.getDivyAddr());
		pstmt.setString(5, vo.getDivyRequest());
		pstmt.setString(6, vo.getDivyDate());
		pstmt.setInt(7, vo.getTranNo());
		
		int n =pstmt.executeUpdate();
		System.out.println("updatePurchase :: "+n);
		
		pstmt.close();
		con.close();
	}
	
	public void updateTranceCode(Purchase vo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		String tcode = vo.getTranCode();
		String sql = "update transaction set TRAN_STATUS_CODE=?";
			   sql+="WHERE tran_no = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, tcode);
		pstmt.setInt(2, vo.getTranNo());
		
		int i = pstmt.executeUpdate();
		System.out.println("trancode////////////////////////"+i);
	}
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pstmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
