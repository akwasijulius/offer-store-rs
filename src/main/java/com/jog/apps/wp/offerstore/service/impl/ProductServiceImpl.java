package com.jog.apps.worldpay.assignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jog.apps.worldpay.assignment.common.exception.DAOException;
import com.jog.apps.worldpay.assignment.common.exception.ServiceException;
import com.jog.apps.worldpay.assignment.dao.OfferDAO;
import com.jog.apps.worldpay.assignment.entity.Product;
import com.jog.apps.worldpay.assignment.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private OfferDAO offerDao;

	@Override
	public int createProductOffer(Product product) throws ServiceException {
		if(product == null || product.getName() == null || product.getName().isEmpty()){
			throw new IllegalArgumentException("Product or product name should not be null");
		}		
		try {
			return offerDao.createProductOffer(product);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public Product getProductOffer(int id) throws ServiceException {
		try {
			return offerDao.getProductById(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}
	
	public List<Product> getAllProducts() throws ServiceException{
		try {
			return offerDao.getAllProducts();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
