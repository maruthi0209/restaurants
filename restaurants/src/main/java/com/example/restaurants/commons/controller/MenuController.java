package com.example.restaurants.commons.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurants.commons.service.CommonsService;
import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.service.NewMenuService;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.StandardReleaseResponse;

@RestController
@RequestMapping("/v1/release")
public class MenuController {

	@Autowired
	CommonsService commonsService;
	
	@Autowired
	private NewMenuService newMenuService;
	
	public static final Logger logger = LoggerFactory.getLogger(MenuController.class);
		
	/**
	 * This method consumes the incoming menu details object to be updated and stored in
	 * the database
	 * 
	 * @param menuId
	 * @param menu
	 * @return A response entity having status code and status message
	 */

	@RequestMapping(value="/updatemenu/{menuId}", method=RequestMethod.PUT,produces = { "application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerMenuUpdate(@PathVariable(value="menuId")String menuId, @RequestBody Menu menu) {
		return commonsService.updateMenuEntities(menu, menuId);
	}

	/**
	 * This method retrieved the menu details object as per menu id from
	 * the database
	 * @param menuId
	 * @return A response entity having status code and status message
	 */
	@RequestMapping(value="/getmenu/{menuId}", method=RequestMethod.GET,produces = { "application/JSON" })
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerMenuGet(@PathVariable String menuId) {	
			return newMenuService.findById(menuId);
	}
	
	@RequestMapping(value="/getmenubyrestaurantname/{restaurantName}/", method=RequestMethod.GET,produces = { "application/JSON" })
	public Menu commonsReleaseControllerMenuGetByRestaurantName(@PathVariable String restaurantName) {	
			return newMenuService.findByRestaurantName(restaurantName);
	}
	
	@RequestMapping(value="/getrestaurantsbyitemname/{itemName}/", method=RequestMethod.GET,produces = { "application/JSON" })
	public HashMap<String, Integer> commonsReleaseControllerMenuGetByItemName(@PathVariable String itemName) {	
			return newMenuService.findByItemName(itemName);
	}
	
	@RequestMapping(value="/getrestaurantsbydivision/{divisionName}/", method=RequestMethod.GET,produces = { "application/JSON" })
	public HashMap<String, HashMap<String, Integer>> commonsReleaseControllerMenuGetByDivisionName(@PathVariable String divisionName) {	
			return newMenuService.findByDivision(divisionName);
	}
	
	@RequestMapping(value="/getrestaurantsbycategory/{categoryName}/", method=RequestMethod.GET,produces = { "application/JSON" })
	public HashMap<String, HashMap<String, Integer>> commonsReleaseControllerMenuGetByCategoryName(@PathVariable String categoryName) {	
			return newMenuService.findByCategory(categoryName);
	}
	
	@RequestMapping(value="/deletemenu/{restaurantName}/", method=RequestMethod.DELETE)
	public ResponseEntity<StandardReleaseResponse> commonsReleaseControllerMenuDelete(@PathVariable String restaurantName) {	
			return newMenuService.deleteMenuByRestaurantName(restaurantName);
	}
}
