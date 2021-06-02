package com.example.restaurants.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.restaurants.commons.controller.CommonsReleaseController;

/**
 *  A class to create a response entity object with HTTP status codes based on status codes and message
 * @author schennapragada
 *
 */
public class GlobalResponse {
	
	public static final Logger logger = LoggerFactory.getLogger(GlobalResponse.class);

	/**
	 *  Method to create response entity from status code and response message
	 * @param statusCode
	 * @param responseMessage
	 * @return response entity of type Standard Release response
	 */
	public ResponseEntity<StandardReleaseResponse> createResponseEntity(int statusCode, String responseMessage) {
		logger.info("generating response code and response message");
		StandardReleaseResponse standardreleaseresponse = new StandardReleaseResponse(statusCode, responseMessage);
		return new ResponseEntity<>(
				standardreleaseresponse, returnStatusForCode(statusCode));
	}

	/**
	 *  A method to return HTTP status for given user code
	 * @param statusCode
	 * @return HttpStatus
	 */
	private HttpStatus returnStatusForCode(int statusCode) {
		HttpStatus response;
		switch (statusCode) {
		case 200:
			response = HttpStatus.valueOf("OK");
			break;
		case 201:
			response = HttpStatus.valueOf("CREATED");
			break;
		case 202:
			response = HttpStatus.valueOf("ACCEPTED");
			break;
		case 400:
			response = HttpStatus.valueOf("BAD_REQUEST");
			break;
		case 401:
			response = HttpStatus.valueOf("UNAUTHORIZED");
			break;
		case 403:
			response = HttpStatus.valueOf("FORBIDDEN");
			break;
		case 404:
			response = HttpStatus.valueOf("NOT_FOUND");
			break;
		case 409:
			response = HttpStatus.valueOf("CONFLICT");
			break;
		case 412:
			response = HttpStatus.valueOf("PRECONDITION_FAILED");
			break;
		case 422:
			response = HttpStatus.valueOf("UNPROCESSABLE_ENTITY");
			break;
		case 500:
			response = HttpStatus.valueOf("INTERNAL_SERVER_ERROR");
			break;
		case 501:
			response = HttpStatus.valueOf("NOT_IMPLEMENTED");
			break;
		case 504:
			response = HttpStatus.valueOf("GATEWAY_TIMEOUT");
			break;
		default:
			response = HttpStatus.valueOf("INTERNAL_SERVER_ERROR");
			break;
		}
		return response;
	}
}
