package com.example.restaurants.commons.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurants.commons.service.CommonsService;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.OrderDetails;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.util.StandardReleaseResponse;

/**
 * This is a common controller for the application. All the incoming requests
 * from the user are received here.
 * 
 * @author schennapragada
 *
 */

@RestController
@RequestMapping("/v1/release")

public class CommonsReleaseController {

	@Autowired
	CommonsService commonsService;

	public static final Logger logger = LoggerFactory.getLogger(CommonsReleaseController.class);

	/**
	 * This method consumes the incoming restaurant details object to be stored in
	 * the database
	 * 
	 * @param restaurantdetails
	 * @return A response entity having status code and status message
	 */
	@RequestMapping(value="/restaurants", method=RequestMethod.POST,produces = { "application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerRestaurants(
			@RequestBody RestaurantDetails restaurantdetails) {
		logger.info("sending request to commons service");
		return commonsService.processRestaurantEntities(restaurantdetails);
	}

	@RequestMapping(value="/deleterestaurant/{restaurantName}/", method=RequestMethod.DELETE)
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerDeleteRestaurant(
			@PathVariable String restaurantName) {
		logger.info("sending request to commons service");
		return commonsService.deleteRestaurantEntities(restaurantName);
	}
	
	
	/**
	 * This method consumes the incoming menu details object to be stored in
	 * the database
	 * 
	 * @param menu
	 * @return A response entity having status code and status message
	 */
	@RequestMapping(value="/newmenu", method=RequestMethod.POST,produces = { "application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerMenu(@RequestBody Menu menu) {
		logger.info("sending request to commons service");
		return commonsService.processMenuEntities(menu);
	}

	
	
	/**
	 * This method consumes the incoming user details object to be stored in
	 * the database
	 * 
	 * @param userDetails
	 * @return A response entity having status code and status message
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST,produces = { "application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerUser(@RequestBody UserDetails userDetails) {
		logger.info("sending request to orders service");
		return commonsService.processUserEntities(userDetails);
	}
	  
		
}
