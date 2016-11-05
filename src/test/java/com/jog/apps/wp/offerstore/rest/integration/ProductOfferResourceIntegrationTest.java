package com.jog.apps.wp.offerstore.rest.integration;

import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.jog.apps.wp.offerstore.entity.Product;



/**
 * Integration test class for Product Offer functionalities
 * 
 * @author Julius Oduro
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring/testContext.xml"})
public class ProductOfferResourceIntegrationTest {
	Logger logger = LogManager.getLogger(ProductOfferResourceIntegrationTest.class);
	
	private Product product;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp(){		
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "Products");
		
		product = new Product("Swag Shoe", "Very Old Swag Shoe", TEN);
		// load test data into the database. This currently inserts 3 products
		this.loadTestData();
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
		
		int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Products");
		logger.info("Total number in table: {}", count);
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
		//NOTE: This retrieves a product that been already created by the insert-products script
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
	public void testGetAllProducts() throws Exception{			
		Client client = ClientBuilder.newClient();		
		Response response = client.target("http://localhost:8080/offer-store/products/")				
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get();		
			
		// There should be only 3 pre-existing items in the database, as per the insert-products script
		List<Product> products = response.readEntity(new GenericType<List<Product>>() {	});
		logger.info("Products returned .......");
		products.forEach(p->logger.info(p.toString()));
		
		assertThat(products.size(), is(3));
	}
	
	
	private void loadTestData(){
		Connection connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());		
		ScriptUtils.executeSqlScript(connection, new ClassPathResource("/db/insert-products.sql"));
	}
	
	

}
