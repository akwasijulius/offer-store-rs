package com.jog.apps.wp.offerstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jog.apps.wp.offerstore.common.exception.DAOException;
import com.jog.apps.wp.offerstore.common.exception.ServiceException;
import com.jog.apps.wp.offerstore.dao.OfferDAO;
import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.service.ProductService;

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
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}

	@Override
	public Product getProductOffer(int id) throws ServiceException {
		try {
			return offerDao.getProductById(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}
	

}