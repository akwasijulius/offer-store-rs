/**
 * 
 */
package com.jog.apps.wp.offerstore.service;

import java.util.List;

import com.jog.apps.wp.offerstore.common.exception.ServiceException;
import com.jog.apps.wp.offerstore.entity.Product;

/**
 * Service class for Product Offers
 * @author Julius Oduro
 */
public interface ProductService {

	/**
	 * Service method that validates and delegate the creation of a new product offer
	 * to the dao layer. 
	 * 
	 * @param product the product to be created. This should not be null 
	 * @return id of the product just created
	 * @throws ServiceException if an error occurs during this process
	 * @throw IllegalArgumentException if the specified product parameter is null
	 */
	public int createProductOffer(Product product) throws ServiceException;
	
	
	/**
	 * Finds and returns a Product for the specified id
	 * 
	 * @param id The unique product id
	 * @return Product if its exit or null if it doesn't exit
	 * @throws ServiceException if an error occurs during this process
	 */
	public Product getProductOffer(int id) throws ServiceException;
	
	
	/**
	 * Return list of all products
	 * @return list of products or an empty string
	 * @throws ServiceException if an error occurs during this process
	 */
	public List<Product> getAllProducts() throws ServiceException;

}