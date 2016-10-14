/**
 * 
 */
package com.jog.apps.worldpay.assignment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jog.apps.worldpay.assignment.dao.OfferDAO;

/**
 * @author Julius Oduro
 */
@Path("/hello")
@Component
public class StarterResource {
	
	@Autowired
	OfferDAO offerDAO;


	public StarterResource() {}
	
	@GET
	@Produces(MediaType.TEXT_HTML)	
	public String getUserDeatils(){
		//System.out.println(offerDAO.getAllProducts());

		return "<html> " + "<title>" + "Hello Jersey 2.0" + "</title>"
			        + "<body><h1>" + "Hello Jersey 2.0" + "</body></h1>" + "</html> ";
	}

}
