package com.jog.apps.wp.offerstore.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jog.apps.wp.offerstore.dao.OfferDAO;
import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.DAOException;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;
import com.jog.apps.wp.offerstore.exception.ServiceException;
import com.jog.apps.wp.offerstore.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	private Product product;

	@Mock
	private OfferDAO offerDao;

	@InjectMocks
	private ProductService productService = new ProductServiceImpl();

	@Before
	public void setUp() throws Exception {
		product = new Product();
		product.setName("Product Name");
	}

	//Create Product Tests
	@Test
	public final void verifyThatCreateProductCallsDOA() throws Exception {
		productService.createProductOffer(product);

		verify(offerDao).createProductOffer(product);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void shouldThrowIllegalExceptionWhenProductIsNull() throws Exception {
		productService.createProductOffer(null);
	}

	@Test(expected = ServiceException.class)
	public final void shouldThrowServiceExceptionWhenDataAccessExceptionIsCaught() throws Exception {
		doThrow(new DAOException()).when(offerDao).createProductOffer(product);

		productService.createProductOffer(product);
	}

	@Test(expected = RuntimeException.class)
	public final void shouldThrowOtherExceptionsThrownFromDAO() throws Exception {
		doThrow(new RuntimeException()).when(offerDao).createProductOffer(product);

		productService.createProductOffer(product);
	}
	
	//Get Product Tests	
	@Test
	public void shouldGetProduct() throws Exception{
		product.setId(100);
		
		when(offerDao.fetchProductById(100)).thenReturn(product);
		
		Product returnedProduct = productService.getProductOffer(100);
		
		assertThat(returnedProduct,	is(product));
		verify(offerDao).fetchProductById(100);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public final void shouldThrowINFExceptionWhenProductIsNotFound() throws Exception {
		doThrow(ItemNotFoundException.class).when(offerDao).fetchProductById(1);

		productService.getProductOffer(1);
	}
	
	@Test(expected = ServiceException.class)
	public final void shouldThrowServiceExceptionWhenDAOExceptionOccurs() throws Exception {
		doThrow(DAOException.class).when(offerDao).fetchProductById(1);

		productService.getProductOffer(1);
	}
	
	//Get All Products Tests	
	@Test
	public void shouldGetAllProducts() throws Exception{
		List<Product> products = Arrays.asList(new Product(), new Product());
		
		when(offerDao.fetchAllProducts()).thenReturn(products);
		
		List<Product> returnedProducts = productService.getProducts();
		
		assertThat(returnedProducts,	is(products));
		
		verify(offerDao).fetchAllProducts();
	}
	
	
	@Test(expected = ServiceException.class)
	public final void shouldThrowServiceExceptionForGetProducts() throws Exception {
		doThrow(DAOException.class).when(offerDao).fetchAllProducts();

		productService.getProducts();
	}

}
