/**
 * 
 */
package com.jog.apps.wp.offerstore.dao;

import java.util.List;

import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;

/**
 * DAO for product offers
 * 
 * @author Julius Oduro
 */
public interface OfferDAO {
	
	/**
	 * Creates a product or good for offers in a data store 
	 * 
	 * @param product The product or good to be offered for sale, should not be null
	 * @return id of the created product.
	 * @throws DAOException if an error occurs during this process
	 * @throws IllegalArgumentException if the specified product parameter is null
	 */
	public int createProductOffer(Product product) throws DAOException;
	
	
	
	/**
	 * Finds and returns the Product mapping to the specified id
	 * 
	 * @param id The unique id to be used for search
	 * @return Product if found or null
	 * @throws ItemNotFoundException if Product relation to the specified Id is not found
	 * @throws DAOException, if an error occurs during this process
	 */
	public Product fetchProductById(int id) throws ItemNotFoundException, DAOException;



	/**
	 * Fetch and return list of all products.
	 * 
	 * @return list of products
	 * @throws DAOException, if an error occurs during this process
	 */
	public List<Product> fetchAllProducts()throws DAOException;
	
	
	

}
