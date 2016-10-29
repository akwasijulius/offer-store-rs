/**
 * 
 */
package com.jog.apps.wp.offerstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Julius Oduro
 *
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(ServiceException ex) {
		return Response.status(Status.INTERNAL_SERVER_ERROR )
				.entity(ex.getCause())
				.type(MediaType.APPLICATION_JSON).
				build();
	}

}
