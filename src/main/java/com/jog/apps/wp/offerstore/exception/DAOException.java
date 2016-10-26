
package com.jog.apps.wp.offerstore.exception;

/**
 * DAOException are exceptions thrown from the data access layer. They are
 * used to wrap any DB or persistent framework exceptions and re-throw them
 * in other to avoid tight coupling framework exception to the service layer.
 * 
 * @author Julius Oduro
 */
public class DAOException extends Exception {

	
	private static final long serialVersionUID = 1L;

	
	public DAOException() {
	}

	
	public DAOException(String message) {
		super(message);
	}

	
	public DAOException(Throwable cause) {
		super(cause);
	}


	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	

}
