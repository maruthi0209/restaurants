package com.example.restaurants.commons.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.ValidationException;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.restaurantservice.RestaurantService;
import com.example.restaurants.service.NewMenuService;
import com.example.restaurants.service.UserService;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuFourthUtil;
import com.example.restaurants.util.StandardReleaseResponse;

/**
 * A common service class for the application to map incoming request from the
 * controller to respective services.
 * 
 * @author schennapragada
 *
 */
@Component
public class CommonsService {

	@Autowired
	private RestaurantService restaurantservice;

	@Autowired
	private CommonsValidateService commonsvalidateservice;

	@Autowired
	private NewMenuService newMenuService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuFourthUtil menuFourthUtil;

	public static final Logger logger = LoggerFactory.getLogger(CommonsService.class);
	/**
	 * This method takes incoming restaurant details object as parameter, validates
	 * it and if there are no violations, sends to restaurant service for further
	 * processing
	 * 
	 * @param restaurantdetails
	 * @return A response entity of type status code and status message
	 */
	public ResponseEntity<StandardReleaseResponse> processRestaurantEntities(RestaurantDetails restaurantdetails) {
		logger.info("sending restaurant details to get validated");
		List<String> violationsList = commonsvalidateservice.validateRestaurant(restaurantdetails);
		if (violationsList.isEmpty()) {
			logger.info("validation successful. sending to restaurant service.");
			return restaurantservice.handleUserActions(restaurantdetails);
		} else {
			throw new ValidationException(violationsList.toString());
			//return new GlobalResponse().createResponseEntity(450, violationsList.toString());
		}
	}

	public ResponseEntity<StandardReleaseResponse> processMenuEntities(Menu menu) {
		Menu filledmenu = menuFourthUtil.setNewMenuDetails(menu);
		logger.info("sending menu list object to get validated");
		List<String> violationsList = commonsvalidateservice.validateMenu(filledmenu);
		if (violationsList.isEmpty()) {
			logger.info("validation successful. sending to menu service");
			return newMenuService.handleUserActions(filledmenu);
		} else {
			throw new ValidationException(violationsList.toString());
			//return new GlobalResponse().createResponseEntity(450, violationsList.toString());
		}
	}
	
	public ResponseEntity<StandardReleaseResponse> processUserEntities(UserDetails userDetails) {
		List<String> violationsList = commonsvalidateservice.validateUser(userDetails);
		if (violationsList.isEmpty()) {
			return userService.handleUserActions(userDetails);
		} else {
			throw new ValidationException(violationsList.toString());
			//return new GlobalResponse().createResponseEntity(450, violationsList.toString());
		}
	} 
	
	public ResponseEntity<StandardReleaseResponse> updateMenuEntities(Menu menu, String menuId) {
		logger.info("sending menu list object to get validated");
		List<String> violationsList = commonsvalidateservice.validateMenu(menu);
		if (violationsList.isEmpty()) {
			return newMenuService.updateUserActions(menu, menuId);
		} else {
			throw new ValidationException(violationsList.toString());
			//return new GlobalResponse().createResponseEntity(450, violationsList.toString());
		}
	}

	public ResponseEntity<StandardReleaseResponse> deleteRestaurantEntities(String restaurantName) {
		return restaurantservice.deleteRestaurantByName(restaurantName);
	}
}


