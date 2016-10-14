/**
 * 
 */
package com.jog.apps.worldpay.assignment.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.jog.apps.wp.offerstore.entity.Product;



/**
 * @author Julius Oduro
 *
 */
public class ProductOfferResourceIntegrationTest {

	Product product;
	
	@Before
	public void setUp(){
		product = new Product("Antique Clock", "Very Old Antique Clock", BigDecimal.TEN);
	}


	
	@Test
	public void testCreateProductOffer(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/worldpay/assignment/offers/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(product));
			
		assertThat(response.getStatus(), is( Status.OK.getStatusCode()));
		assertThat(response.readEntity(Integer.class), is(not(0)));
		
		response.close();
	}
	
	@Test
	public void testCreateProductOfferWhenProductIsNull(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/worldpay/assignment/offers/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(null));
			
		assertThat(response.getStatus(), is( Status.INTERNAL_SERVER_ERROR.getStatusCode()));		
		response.close();
	}
	
	@Test
	public void testCreateProductOfferWhenProductIsNotValid(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/worldpay/assignment/offers/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(new Product()));
			
		assertThat(response.getStatus(), is( Status.INTERNAL_SERVER_ERROR.getStatusCode()));		
		response.close();
	}
	
	
	@Test
	public void testRetrivingOfferJustCreated(){		
		Client client = ClientBuilder.newClient();	
		
		Response response = client.target("http://localhost:8080/worldpay/assignment/offers/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)				
				.post(Entity.json(product));
		
		assertThat(response.getStatus(), is( Status.OK.getStatusCode()));		
		
			
		// get the recently saved product	
		product.setId(response.readEntity(Integer.class));
		Product returnedProduct = client.target("http://localhost:8080/worldpay/assignment/offers/")
				.path(String.valueOf(product.getId()))				
				.request()
				.accept(MediaType.APPLICATION_JSON)			
				.get(Product.class);
		
		assertThat(returnedProduct, is( product));	
		
	}

}
