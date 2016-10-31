package com.jog.apps.wp.offerstore.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jog.apps.wp.offerstore.dao.OfferDAO;
import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;
import com.jog.apps.wp.offerstore.exception.ServiceException;

@Service
class ProductServiceImpl implements ProductService {
	
	@Autowired
	private OfferDAO offerDao;

	@Override
	public int createProductOffer(Product product) throws ServiceException {
		if(product == null || StringUtils.isEmpty(product.getName())){
			throw new IllegalArgumentException("Product or product name should not be null");
		}	
		
		try {
			return offerDao.createProductOffer(product);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}

	@Override
	public Product getProductOffer(int id) throws ItemNotFoundException, ServiceException {
		try {
			return offerDao.fetchProductById(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}

	@Override
	public List<Product> getProducts() throws ServiceException {
		try{
			return offerDao.fetchAllProducts();
		} catch(DAOException ex){
			throw new ServiceException(ex.getMessage(), ex.getCause());
		}
	}
	

}