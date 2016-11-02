package com.jog.apps.wp.offerstore.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jog.apps.wp.offerstore.dao.ProductDAO;
import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;
import com.jog.apps.wp.offerstore.exception.ServiceException;

@Service
@Transactional
class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO productDao;

	@Override
	public int createProduct(Product product) throws ServiceException {
		if(product == null || StringUtils.isEmpty(product.getName())){
			throw new IllegalArgumentException("Product or product name should not be null");
		}	
		
		try {
			return productDao.createProduct(product);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}

	@Override
	public Product getProduct(int id) throws ItemNotFoundException, ServiceException {
		try {
			return productDao.fetchProductById(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}

	@Override
	public List<Product> getProducts() throws ServiceException {
		try{
			return productDao.fetchAllProducts();
		} catch(DAOException ex){
			throw new ServiceException(ex.getMessage(), ex.getCause());
		}
	}
	

}