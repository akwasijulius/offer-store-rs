/**
 * 
 */
package com.jog.apps.wp.offerstore.exception;

/**
 * Exception to be used for indication that an item been requested or been search for can not be 
 * found.
 * @author Julius Oduro
 */
@SuppressWarnings("serial")
public class ItemNotFoundException extends Exception {


	public ItemNotFoundException(String message) {
		super(message);
	}

}
