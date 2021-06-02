package com.example.restaurants.exceptions;

/**
 *  A class for defining a template for any runtime exception for this application.
 * @author schennapragada
 *
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -933129278958313510L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	
}
