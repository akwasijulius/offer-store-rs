/**
 * 
 */
package com.jog.apps.wp.offerstore.rest;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jog.apps.wp.offerstore.entity.Product;
import com.jog.apps.wp.offerstore.exception.ItemNotFoundException;
import com.jog.apps.wp.offerstore.exception.ServiceException;
import com.jog.apps.wp.offerstore.service.ProductService;

/**
 * Rest Service for that handling all product offers
 * 
 * @author Julius Oduro <protocol>://<service-name>/<ResourceType>/<ResourceID>
 */
@Path("/offers")
@Component
public class ProductOfferResource {
	private static Logger logger = LogManager.getLogger(ProductOfferResource.class);

	@Autowired
	ProductService productService;

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProductOffer(Product product) {
		try {

			int productId = productService.createProductOffer(product);

			return Response.status(Response.Status.CREATED).entity(productId).build();

		} catch (ServiceException ex) {
			throw new WebApplicationException(ex.getMessage());
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			throw new WebApplicationException(exception.getMessage());
		}
	}
	

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProductOffer(@PathParam("id") int id) throws Exception {
		try {
			return productService.getProductOffer(id);
		} catch (ItemNotFoundException ex) {			
			throw new WebApplicationException(ex.getMessage(), NOT_FOUND);
		} catch (ServiceException ex) {			
			throw new WebApplicationException(ex.getMessage(), INTERNAL_SERVER_ERROR);
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			throw new WebApplicationException(exception.getMessage());
		}
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts() throws ServiceException {
		return productService.getProducts();
	}
	
	
}