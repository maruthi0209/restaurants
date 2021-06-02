package com.example.restaurants.util;

import org.springframework.http.HttpStatus;

/**
 *  An entity class for standard release response type object 
 * @author schennapragada
 *
 */
public class StandardReleaseResponse {

	private int responseCode;

	private String responseMessage;

	/**
	 *  Constructors and getters and setters for this class
	 * @param responseCode
	 * @param responseMessage
	 */
	public StandardReleaseResponse(int responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "ResponseEntityReturn [responseCode=" + responseCode + ", responseMessage=" + responseMessage + "]";
	}

}
