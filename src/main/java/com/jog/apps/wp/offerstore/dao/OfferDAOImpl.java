/**
 * 
 */
package com.jog.apps.wp.offerstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;

/**
 * @author Julius Oduro
 *
 */
@Repository
class OfferDAOImpl implements OfferDAO {

	private static Logger logger = Logger.getLogger(OfferDAOImpl.class.getName());

	private static AtomicInteger sequenceIdGenetrator = new AtomicInteger(100);

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public int createProductOffer(Product product) throws DAOException {
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
				return product.getId();
			} else {
				String message = String.format(
						"Create Product Offer failed, should insert 1 row but %s row was inserted", insertedRows);
				logger.log(Level.SEVERE, message);
				throw new DAOException(message);
			}
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException("Create Product Offer failed.", e);
		}

	}

	@Override
	public Product getProductById(int id) throws DAOException {
		String sql = "select id, name, description, price from Products where id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

		try {
			return this.jdbcTemplate.queryForObject(sql, namedParameters, new ProductMapper());
		} catch (org.springframework.dao.DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
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