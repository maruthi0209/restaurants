package com.example.restaurants.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.StandardReleaseResponse;

/**
 *  Global class for handling exceptions throughout the application.
 * @author schennapragada
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 *  Method to handle runtime exceptions throughout the application.
	 * @param exception
	 * @return A response entity having status code and status message
	 */
	@ExceptionHandler(value = GenericException.class)
	public ResponseEntity<StandardReleaseResponse> handleGenericExceptions(GenericException exception) {
		logger.info("Exception occured. Generating exception message");
		return new GlobalResponse().createResponseEntity(501, exception.getCause().getMessage());
	}
	
	/**
	 *  Method to handle violation during validation time.
	 * @param exception
	 * @return A response entity of type status code and status message
	 */
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<StandardReleaseResponse> handleValidationExceptions(ValidationException exception) {
		logger.info("generating validation exception message");
		return new GlobalResponse().createResponseEntity(400, exception.getMessage());
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<StandardReleaseResponse> handleNotFoundExceptions(NotFoundException exception) {
		logger.info("generating not found exception message");
		return new GlobalResponse().createResponseEntity(404, exception.getMessage());
	}
}
