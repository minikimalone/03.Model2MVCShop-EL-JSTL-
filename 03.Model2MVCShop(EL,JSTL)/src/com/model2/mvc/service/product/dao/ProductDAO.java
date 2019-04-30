package com.model2.mvc.service.product.dao;

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



public class ProductDAO {

	public ProductDAO() {
	}
		
	public Product findProduct(int prodNo)throws Exception{
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product vo = null;
		while (rs.next()) {
			vo = new Product();
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setFileName(rs.getString("IMAGE_FILE"));
			vo.setRegDate(rs.getDate("REG_DATE"));
			
		}
		
		con.close();

		return vo;
	}
	
	public Map<String,Object> getProductList(Search search)throws Exception{
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		String sql = "";
		// Original Query 구성
		
		if(search.getMenu().equals("manage")) {			
			sql = "SELECT p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day"
					+ ", p.price, p.image_file, p.reg_date,NVL(t.tran_status_code,'0') tran_status_code"
					+ " FROM product p, transaction t WHERE p.prod_no = t.prod_no(+)";
		}else {
			sql = "SELECT p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day"
					+ ", p.price, p.image_file, p.reg_date"
					+ " FROM product p WHERE p.prod_no IS NOT NULL";
		}
		
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " AND p.prod_no like '%" + search.getSearchKeyword()+"%'";
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " AND p.prod_name like '%" + search.getSearchKeyword()+"%'";
			} else if ( search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " AND p.price > '" + search.getSearchKeyword()+"'";
			}
		}
		
	
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			
			Product vo = new Product();
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setFileName("IMAGE_FILE");
			vo.setRegDate(rs.getDate("REG_DATE"));
			if(search.getMenu().equals("manage")) {
				vo.setProTranCode((rs.getString("tran_status_code")).trim());
			}
		
			
			list.add(vo);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
		
	}
	
	
	public void insertProduct(Product vo)throws Exception{
		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, vo.getProdName());
		stmt.setString(2, vo.getProdDetail());
		stmt.setString(3, vo.getManuDate().replace("-", ""));
		stmt.setInt(4, vo.getPrice());
		stmt.setString(5, vo.getFileName());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}
	
	public void updateProduct(Product vo)throws Exception{

		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_Name=?,PROD_detail=?,"
				+ "manufacture_day=?,price=?,image_file=? where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, vo.getProdName());
		stmt.setString(2, vo.getProdDetail());
		stmt.setString(3, vo.getManuDate());
		stmt.setInt(4, vo.getPrice());
		stmt.setString(5, vo.getFileName());
		
		stmt.setInt(6, vo.getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
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
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}