package com.jog.apps.wp.offerstore.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jog.apps.wp.offerstore.common.exception.DAOException;
import com.jog.apps.wp.offerstore.common.exception.ServiceException;
import com.jog.apps.wp.offerstore.dao.OfferDAO;
import com.jog.apps.wp.offerstore.entity.Product;
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
	
	@Test
	public void shouldGetProduct() throws Exception{
		product.setId(100);
		
		when(offerDao.getProductById(100)).thenReturn(product);
		
		Product returnedProduct = productService.getProductOffer(100);
		
		assertThat(returnedProduct,	is(product));
		verify(offerDao).getProductById(100);
	}

}
