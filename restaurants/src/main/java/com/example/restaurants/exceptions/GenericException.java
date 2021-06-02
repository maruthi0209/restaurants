package com.example.restaurants.exceptions;

/**
 *  A class for defining a template for any runtime exception for this application.
 * @author schennapragada
 *
 */
public class GenericException extends RuntimeException {

	private int statusCode;
	private String responseMessage;
	
	private static final long serialVersionUID = -2352946924017164975L;

	public GenericException() {
		super();
	}

	public GenericException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericException(String message) {
		super(message);
	}

	public GenericException(Throwable cause) {
		super(cause);
	}
	
	public GenericException(int statusCode, String message) {
		super(message);
	}
	
}
