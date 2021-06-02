package com.example.restaurants.commons.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.OrderDetails;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.repository.IRestaurantRepository;
import com.example.restaurants.service.NewMenuService;
import com.example.restaurants.util.MenuFourthUtil;
import com.example.restaurants.util.MenuThirdUtil;

/**
 * A validation service class to validate the constraints for each field in the
 * incoming object for violations.
 * 
 * @author schennapragada
 *
 */
@Component
public class CommonsValidateService {

	@Autowired
	private IRestaurantRepository iRestaurantRepository;
	
	@Autowired
	private NewMenuService newMenuService;
	
	@Autowired
	private MenuThirdUtil menuThirdUtil;
	
	@Autowired
	private MenuFourthUtil menuFourthUtil;
	
	public static final Logger logger = LoggerFactory.getLogger(CommonsValidateService.class);
	/**
	 * A method to validate the object restaurant details as per the constraints
	 * mentioned in the pojo class
	 * 
	 * @param restaurantdetails
	 * @return A list of all the violations.
	 */
	public List<String> validateRestaurant(RestaurantDetails restaurantdetails) {
		List<String> validationlist = new ArrayList<>();
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<RestaurantDetails>> restaurantviolations = validator.validate(restaurantdetails);
			logger.info("searching for special character violations");
			String specialCharacters = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
			String restaurantNameString[] = restaurantdetails.getRestaurantName().split("");
			int specialCharacterCount = 0;
			for (int i = 0; i < restaurantNameString.length; i++) {
				if (specialCharacters.contains(restaurantNameString[i])) {
					specialCharacterCount++;
				}
			}
			if (restaurantviolations.isEmpty() && (specialCharacterCount == 0)) {
				try {
				logger.info("searching if the restaurant name is unique");
				if (iRestaurantRepository.findRestaurantName(restaurantdetails.getRestaurantName()).isEmpty()) {
					return validationlist;
				}
				{
					validationlist.add("Restaurant details already exists");
					return validationlist;
				}
				} catch (Exception exception) {
					throw new GenericException();
				}
			} else {
				logger.debug("returning list of violations");
				Iterator<ConstraintViolation<RestaurantDetails>> it = restaurantviolations.iterator();
				while (it.hasNext()) {
					validationlist.add(it.next().getMessage());
				}
				if (specialCharacterCount != 0) {
					validationlist.add("Name contains " + specialCharacterCount + " special characters");
				}
				return validationlist;
			}
		
	}


	/**
	 * A method to validate the object menu as per the constraints
	 * mentioned in the pojo class
	 * 
	 * @param menu
	 * @return A list of all the violations.
	 */
	public List<String> validateMenu(Menu menu) {
		List<String> validationlist = new ArrayList<>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Menu>> menuviolations = validator.validate(menu);
		if (!menuviolations.isEmpty()) {
			Iterator<ConstraintViolation<Menu>> it = menuviolations.iterator();
			while (it.hasNext()) {
				validationlist.add(it.next().getMessage());
			}
		}
		return validationlist;
	}

	/**
	 * A method to validate the object user details as per the constraints
	 * mentioned in the pojo class
	 * 
	 * @param userDetails
	 * @return A list of all the violations.
	 */
	public List<String> validateUser(UserDetails userDetails) {
		UserDetails filleduserDetails = menuThirdUtil.setUserDetails(userDetails);
		System.out.println(filleduserDetails);
		List<String> validationlist = new ArrayList<>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserDetails>> uesrviolations = validator.validate(filleduserDetails);
		if (!uesrviolations.isEmpty()) {
			Iterator<ConstraintViolation<UserDetails>> it = uesrviolations.iterator();
			while (it.hasNext()) {
				validationlist.add(it.next().getMessage());
			}
		}
		return validationlist;
	} 

}