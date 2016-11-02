package com.jog.apps.wp.offerstore.rest.integration;

import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jog.apps.wp.offerstore.entity.Product;



/**
 * Integration test class for Product Offer functionalities
 * 
 * @author Julius Oduro
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@Transactional
@Rollback
public class ProductOfferResourceIntegrationTest {
	Logger logger = LogManager.getLogger(ProductOfferResourceIntegrationTest.class);
	private Product product;
	
	@Before
	public void setUp(){
		product = new Product("Swag Shoe", "Very Old Swag Shoe", TEN);
	}
	
	// Create Product Tests

	@Test
	public void shouldCreateProductOfferSuccessfully(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/offer-store/products/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(product));
			
		assertThat(response.getStatus(), is( Status.CREATED.getStatusCode()));
		assertThat(response.readEntity(Integer.class), is(not(0)));		
	}
	
	
	@Test
	public void testCreateProductOfferWhenProductIsNull(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/offer-store/products/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(null));
			
		assertThat(response.getStatus(), is( Status.INTERNAL_SERVER_ERROR.getStatusCode()));	
	}
	
	
	@Test
	public void testCreateProductOfferWhenProductIsNotValid(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/offer-store/products/")				
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(new Product()));
			
		assertThat(response.getStatus(), is( Status.INTERNAL_SERVER_ERROR.getStatusCode()));	
	}
	
	
	//////// Get Product Tests //////	
	@Test
	public void shouldBeAbleToRetriveProductCreated(){		
		// Retrieves a product created by the insert-products script
		Product existingProduct = new Product().setId(901).setName("Digital Clock");
		Client client = ClientBuilder.newClient();					
		Product returnedProduct = client.target("http://localhost:8080/offer-store/products/")
				.path(String.valueOf(901))				
				.request()
				.accept(MediaType.APPLICATION_JSON)			
				.get(Product.class);
		
		assertThat(returnedProduct, is( existingProduct));			
	}
	
	
	@Test(expected=NotFoundException.class)
	public void testRetrivingOfferThatDoesNotExist(){		
		Client client = ClientBuilder.newClient();		
		product.setId(101);
		client.target("http://localhost:8080/offer-store/products/")
				.path(String.valueOf(product.getId()))				
				.request()
				.accept(MediaType.APPLICATION_JSON)		
				.get(Product.class);			
	}
	
	
	///// Get All Products Tests //////
	@Test
	public void testGetAllProducts(){		
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/offer-store/products/")				
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get();		
			
		// There should be only 3 pre-existing items in the database, as per the insert-products script
		List<Product> products = response.readEntity(new GenericType<List<Product>>() {	});
		assertThat(products.size(), is(3));
		response.close();
	}

}
