package com.jog.apps.wp.offerstore.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;

@RunWith(MockitoJUnitRunner.class) 
public class OfferDAOTest {
	
	@Mock
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Mock
	AtomicInteger sequenceIdGenetrator;
		
	
	@InjectMocks
	private OfferDAO offerDAO= new OfferDAOImpl();

	@Before
	public void setUp() throws Exception {
		
	}

	
	private Product setUpProduct(){
		return new Product()
				.setId(1)
				.setName("Product 1")
				.setDescription("Product 1 Description");
	}
	
	@Test
	public void verifyThatJdbcTemplateIsCalled() throws DAOException{		
		when(jdbcTemplate.update(anyString(), anyMapOf(String.class, Object.class))).thenReturn(1);
		
		offerDAO.createProductOffer(setUpProduct());		
		
		verify(jdbcTemplate).update(anyString(), anyMapOf(String.class, Object.class));
	}
	
	@Test(expected=DAOException.class)
	public void shouldThrowExceptionWhenNoInsertOccurs() throws DAOException{		
		when(jdbcTemplate.update(anyString(), anyMapOf(String.class, Object.class))).thenReturn(0);
		
		offerDAO.createProductOffer(setUpProduct());
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenProductIsNull() throws DAOException{
		offerDAO.createProductOffer(null);
	}
	
	
	@Test(expected=DAOException.class)
	public void shouldThrowDAOExceptionWhenInsertErrorsOccurs() throws DAOException{		
		doThrow(DataIntegrityViolationException.class).when(jdbcTemplate).update(anyString(), anyMapOf(String.class, Object.class));
		
		offerDAO.createProductOffer(setUpProduct());
	}
	
	
	// Tests for FetchAllProducts //

	@SuppressWarnings("unchecked")
	@Test
	public final void fetchAllProductsShouldReturnAllProducts() throws Exception {
		List<Product> products = Arrays.asList(new Product(), new Product());
		
		when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(products);
		
		List<Product> results = offerDAO.fetchAllProducts();
		
		Assert.assertThat(results.size(), is(products.size()));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public final void fetchAllProductsShouldReturnProductsWithCorrectValues() throws Exception {
		
		List<Product> products = Arrays.asList(setUpProduct());
		
		when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(products);
		
		List<Product> results = offerDAO.fetchAllProducts();
		
		Assert.assertThat(results, is(products));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=DAOException.class)
	public void shouldThrowDAOExceptionWhenDataAccessExceptionOccurs() throws DAOException{		
		doThrow(QueryTimeoutException.class).when(jdbcTemplate).query(anyString(), any(RowMapper.class));
		
		offerDAO.fetchAllProducts();
	}
	
	
	// Tests for FetchProductById //
	@SuppressWarnings("unchecked")
	@Test
	public final void fetchProductByIdShouldReturnCorrectProduct() throws Exception {
		
		Product expectedProduct = setUpProduct();
		
		when(jdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class))).thenReturn(expectedProduct);
		
		Product returnedProduct = offerDAO.fetchProductById(101);
		
		Assert.assertThat(returnedProduct, is(expectedProduct));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=ItemNotFoundException.class)
	public final void shouldThrowINFExceptionWhenProductIsNotFound() throws Exception {
		doThrow(EmptyResultDataAccessException.class).when(jdbcTemplate).queryForObject(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class));

		offerDAO.fetchProductById(1);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=DAOException.class)
	public final void shouldThrowDAOExceptionWhenDataAccessErrorOccursFetchingProduct() throws Exception {
		doThrow(IncorrectResultSizeDataAccessException.class).when(jdbcTemplate).queryForObject(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class));

		offerDAO.fetchProductById(1);
	}
}
