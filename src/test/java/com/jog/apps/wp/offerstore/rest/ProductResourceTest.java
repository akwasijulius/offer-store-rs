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
import com.jog.apps.wp.offerstore.rest.ProductOfferResource;
import com.jog.apps.wp.offerstore.service.ProductService;

import cucumber.runtime.NullSummaryPrinter;

@RunWith(MockitoJUnitRunner.class)
public class ProductOfferResourceTest {
	@Mock
	ProductService productService;
	
	@Mock
	ResponseBuilder responseBuilder;
	
	@InjectMocks
	ProductOfferResource productOfferResource = new ProductOfferResource();;


	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testCreateProductOffer() throws Exception {
		Product product = new Product("name", "decription", BigDecimal.ONE);			
		
		when(productService.createProductOffer(product)).thenReturn(101);
		
		Response response = Mockito.mock(Response.class, Mockito.CALLS_REAL_METHODS);	
		when(responseBuilder.entity(101)).thenReturn(responseBuilder);
		when(responseBuilder.build()).thenReturn(response);
		
		
		Response returnedResponse = productOfferResource.createProductOffer(product);
	
		assertThat(returnedResponse, is(notNullValue()));
		assertThat(returnedResponse.getStatus(), is(Response.Status.CREATED.getStatusCode()));
		assertThat(returnedResponse.getEntity(), is(101));		
	}
	
	
	@Test(expected=WebApplicationException.class)
	public void testCreateProductOfferWhenProductIsNull() throws Exception {	
		
		when(productService.createProductOffer(null)).thenThrow(new IllegalArgumentException());
						
		productOfferResource.createProductOffer(null);		
	}
	
	
	//// Get Product Tests ////
	@Test(expected=WebApplicationException.class)
	public void testCreateProductOfferWhenServiceExceptionIsThrown() throws Exception {	
		
		when(productService.createProductOffer(null)).thenThrow(new ServiceException("Exception Throw", new RuntimeException()));
						
		productOfferResource.createProductOffer(null);		
	}
	
	
	@Test
	public void testGetProductOfferShouldReturnProduct() throws Exception{
		int productId = 101; 
				
		when(productService.getProductOffer(productId)).thenReturn(new Product("name", "description", BigDecimal.ONE).setId(productId));
		
		Product product = productOfferResource.getProductOffer(productId);
		
		assertThat(product, is(notNullValue()));
		assertThat(product.getId(), is(productId));
		assertThat(product.getName(), is("name"));
		assertThat(product.getDescription(), is("description"));
		assertThat(product.getPrice(), is(BigDecimal.ONE));
		
	}
	
	
	@Test(expected=WebApplicationException.class)
	public void shouldExpectINFExceptionWhenProductDoesNotExist() throws Exception{
		when(productService.getProductOffer(100)).thenThrow(new ItemNotFoundException("Product not found"));
		
		productOfferResource.getProductOffer(100);		
	}
	

	@Test(expected=WebApplicationException.class)
	public void shouldExpectServiceExceptionWhenGetProductFails() throws Exception{
		int productId = 100; 
				
		when(productService.getProductOffer(productId)).thenThrow(new ServiceException("Fetching Product Offer failed.", new Exception()));
		
		productOfferResource.getProductOffer(productId);		
	}
	
	
	@Test(expected=WebApplicationException.class)
	public void shouldExpectExceptionWhenGetProductFails() throws Exception{				
		when(productService.getProductOffer(1)).thenThrow(new NullPointerException("Unexpected Error occured"));
		
		productOfferResource.getProductOffer(1);		
	}
	
	
	//// Get All Products Tests ////
	@Test
	public void getAllProducts() throws Exception{
		List<Product> products = Arrays.asList(new Product(), new Product(), new Product());
		
		when(productService.getProducts()).thenReturn(products);
		
		List<Product> returnedProducts = productOfferResource.getProducts();
		
		assertThat(returnedProducts, is(products));		
	}
	
	
	@Test
	public void getAllProductsShouldReturnEmptyList() throws Exception{
		List<Product> products = Collections.emptyList();
		
		when(productService.getProducts()).thenReturn(products);
		
		List<Product> returnedProducts = productOfferResource.getProducts();
		
		assertThat(returnedProducts, is(products));		
	}

	

}
