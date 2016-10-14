/**
 * 
 */
package com.jog.apps.wp.offerstore.common.exception;

/**
 * ServiceException are exceptions thrown from the service layer
 * 
 * @author Julius Oduro
 */
public class ServiceException extends Exception{


	private static final long serialVersionUID = 1L;

	
	public ServiceException(String message, Exception e) {
		super(message);
	}

}
