/**
 * 
 */
package com.jog.apps.wp.offerstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;

/**
 * @author Julius Oduro
 *
 */
@Repository
class ProductDAOImpl implements ProductDAO {

	private static Logger logger = LogManager.getLogger(ProductDAOImpl.class);

	private static AtomicInteger sequenceIdGenetrator = new AtomicInteger(100);

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public int createProduct(Product product) throws DAOException {
		if (product == null) {
			throw new IllegalArgumentException("Product is null");
		}

		try {
			String sql = "insert into Products (id, name, description, price) values (:id, :name, :description, :price)";
			Map<String, Object> paramMap = new HashMap<>();
			product.setId(sequenceIdGenetrator.incrementAndGet());
			paramMap.put("id", product.getId());
			paramMap.put("name", product.getName());
			paramMap.put("description", product.getDescription());
			paramMap.put("price", product.getPrice());

			int insertedRows = jdbcTemplate.update(sql, paramMap);

			if (insertedRows == 1) {
				logger.info("Product {}, {} created.", product.getId(), product.getName());
				return product.getId();
			} else {
				String message = String.format(
						"Create Product Offer failed, should insert 1 row but %s row was inserted", insertedRows);
				logger.error( message);
				throw new DAOException(message);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException("Create Product Offer failed.", e);
		}

	}

	@Override
	public Product fetchProductById(int id) throws DAOException, ItemNotFoundException {
		String sql = "select id, name, description, price from Products where id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

		try {
			return this.jdbcTemplate.queryForObject(sql, namedParameters, new ProductMapper());
		}catch(EmptyResultDataAccessException ex){
			logger.error( "Nothing found for Product Id {}", id);
			throw new ItemNotFoundException("Product ID "+ id + " not found");
		}catch (DataAccessException e) {
			logger.error( e.getMessage(), e);
			throw new DAOException("Fetching Product Offer failed.", e);
		}
	}


	@Override
	public List<Product> fetchAllProducts() throws DAOException {
		try {
			return this.jdbcTemplate.query("select * from Products", new ProductMapper());
		} catch (DataAccessException e) {
			logger.error( e.getMessage(), e);
			throw new DAOException("Fetching Product Offer failed.", e);
		}
	}
	
	
	
	
	
	private static final class ProductMapper implements RowMapper<Product> {
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product()
					.setId(rs.getInt("id"))
					.setName(rs.getString("name"))
					.setDescription(rs.getString("description"))
					.setPrice(rs.getBigDecimal("price"));
			return product;
		}
	}

}