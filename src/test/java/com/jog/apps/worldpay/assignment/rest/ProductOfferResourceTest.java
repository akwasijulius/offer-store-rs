package com.jog.apps.worldpay.assignment.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
import com.jog.apps.wp.offerstore.rest.ProductOfferResource;
import com.jog.apps.wp.offerstore.service.ProductService;

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
		assertThat(returnedResponse.getStatus(), is(Response.Status.OK.getStatusCode()));
		assertThat(returnedResponse.getEntity(), is(101));		
	}

	@Test
	public void testGetProductOffer() {
		//fail("Not yet implemented"); // TODO
	}

}
