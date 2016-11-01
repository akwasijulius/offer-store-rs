package com.jog.apps.wp.offerstore.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;
import com.jog.apps.wp.offerstore.exception.ServiceException;
import com.jog.apps.wp.offerstore.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductResourceTest {
	@Mock
	ProductService productService;
	
	@Mock
	ResponseBuilder responseBuilder;
	
	@InjectMocks
	ProductResource productResource = new ProductResource();;


	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testCreateProductOffer() throws Exception {
		Product product = new Product("name", "decription", BigDecimal.ONE);			
		
		when(productService.createProduct(product)).thenReturn(101);
		
		Response response = Mockito.mock(Response.class, Mockito.CALLS_REAL_METHODS);	
		when(responseBuilder.entity(101)).thenReturn(responseBuilder);
		when(responseBuilder.build()).thenReturn(response);
		
		
		Response returnedResponse = productResource.createProduct(product);
	
		assertThat(returnedResponse, is(notNullValue()));
		assertThat(returnedResponse.getStatus(), is(Response.Status.CREATED.getStatusCode()));
		assertThat(returnedResponse.getEntity(), is(101));		
	}
	
	
	@Test(expected=WebApplicationException.class)
	public void testCreateProductOfferWhenProductIsNull() throws Exception {	
		
		when(productService.createProduct(null)).thenThrow(new IllegalArgumentException());
						
		productResource.createProduct(null);		
	}
	
	
	//// Get Product Tests ////
	@Test(expected=WebApplicationException.class)
	public void testCreateProductOfferWhenServiceExceptionIsThrown() throws Exception {	
		
		when(productService.createProduct(null)).thenThrow(new ServiceException("Exception Throw", new RuntimeException()));
						
		productResource.createProduct(null);		
	}
	
	
	@Test
	public void testGetProductOfferShouldReturnProduct() throws Exception{
		int productId = 101; 
				
		when(productService.getProduct(productId)).thenReturn(new Product("name", "description", BigDecimal.ONE).setId(productId));
		
		Product product = productResource.getProduct(productId);
		
		assertThat(product, is(notNullValue()));
		assertThat(product.getId(), is(productId));
		assertThat(product.getName(), is("name"));
		assertThat(product.getDescription(), is("description"));
		assertThat(product.getPrice(), is(BigDecimal.ONE));
		
	}
	
	
	@Test(expected=WebApplicationException.class)
	public void shouldExpectINFExceptionWhenProductDoesNotExist() throws Exception{
		when(productService.getProduct(100)).thenThrow(new ItemNotFoundException("Product not found"));
		
		productResource.getProduct(100);		
	}
	

	@Test(expected=WebApplicationException.class)
	public void shouldExpectServiceExceptionWhenGetProductFails() throws Exception{
		int productId = 100; 
				
		when(productService.getProduct(productId)).thenThrow(new ServiceException("Fetching Product Offer failed.", new Exception()));
		
		productResource.getProduct(productId);		
	}
	
	
	@Test(expected=WebApplicationException.class)
	public void shouldExpectExceptionWhenGetProductFails() throws Exception{				
		when(productService.getProduct(1)).thenThrow(new NullPointerException("Unexpected Error occured"));
		
		productResource.getProduct(1);		
	}
	
	
	//// Get All Products Tests ////
	@Test
	public void getAllProducts() throws Exception{
		List<Product> products = Arrays.asList(new Product(), new Product(), new Product());
		
		when(productService.getProducts()).thenReturn(products);
		
		List<Product> returnedProducts = productResource.getProducts();
		
		assertThat(returnedProducts, is(products));		
	}
	
	
	@Test
	public void getAllProductsShouldReturnEmptyList() throws Exception{
		List<Product> products = Collections.emptyList();
		
		when(productService.getProducts()).thenReturn(products);
		
		List<Product> returnedProducts = productResource.getProducts();
		
		assertThat(returnedProducts, is(products));		
	}

	

}
