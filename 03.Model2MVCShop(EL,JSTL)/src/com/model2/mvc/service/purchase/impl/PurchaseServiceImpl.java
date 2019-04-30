package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;


public class PurchaseServiceImpl implements PurchaseService{
	
	private PurchaseDAO purchaseDao;
	
	public PurchaseServiceImpl() {
		
		purchaseDao = new PurchaseDAO();
	}
	
	@Override
	public void addPurchase(Purchase purchaseVO) throws Exception {
		purchaseDao.insertPurchase(purchaseVO);
		
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		
		return purchaseDao.findPurchase(tranNo);
	}

	
	@Override
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception {
		
		return purchaseDao.getPurchaseList(searchVO, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
		
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		purchaseDao.updatePurchase(purchaseVO);
		
	}

	@Override
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		purchaseDao.updateTranceCode(purchaseVO);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		
		
		return purchaseDao.getPurchase(prodNo);
	}

}
