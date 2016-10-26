package com.jog.apps.wp.offerstore.dao;

import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.jog.apps.wp.offerstore.dao.OfferDAO;
import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;

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
	public void verifyThatJdbcTemplateWasCalled() throws DAOException{		
		when(jdbcTemplate.update(anyString(), anyMapOf(String.class, Object.class))).thenReturn(1);
		
		offerDAO.createProductOffer(setUpProduct());		
		
		verify(jdbcTemplate).update(anyString(), anyMapOf(String.class, Object.class));
	}
	
	@Test(expected=DAOException.class)
	public void shouldThrowDAOExceptionWhenNoInsert() throws DAOException{		
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
	
	
	// Tests for non spec - helper methods
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public final void getAllProductsShouldReturnAllProducts() {
//		List<Product> products = Arrays.asList(new Product(), new Product());
//		
//		when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(products);
//		
//		List<Product> results = offerDAO.getAllProducts();
//		
//		Assert.assertThat(results.size(), is(products.size()));
//	}
	
	
//	@SuppressWarnings("unchecked")
//	@Test
//	public final void getAllProductsShouldReturnProductsWithCorrectValues() {
//		
//		List<Product> products = Arrays.asList(setUpProduct());
//		
//		when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(products);
//		
//		List<Product> results = offerDAO.getAllProducts();
//		
//		Assert.assertThat(results, is(products));
//	}

}
