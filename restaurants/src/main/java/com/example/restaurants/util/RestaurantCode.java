package com.example.restaurants.util;

import org.springframework.stereotype.Component;

import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.models.RestaurantDetails;

/**
 * An entity class to calculate the restaurant code based on given restaurant
 * name
 * 
 * @author schennapragada
 *
 */
//@Entity
//@Table(name="restaurant_code")
@Component
public class RestaurantCode {

	private static String prefix = "FD";
	private String finalsuffix;
	private String finalmiddle;
	private String total;

	/**
	 * A method to generate the suffix of the code
	 * 
	 * @param restaurantName
	 * @return A string having two characters
	 */
	private String suffixGenerator(String restaurantName) {
		String firstLetter;
		String secondLetter;
		String suffix;
		String space = " ";
		if (restaurantName.contains(space)) {
			firstLetter = restaurantName.substring(0, 1).toUpperCase();
			secondLetter = restaurantName
					.substring(restaurantName.indexOf(space) + 1, restaurantName.indexOf(space) + 2).toUpperCase();
			suffix = firstLetter + secondLetter;
			return suffix;
		}
		{
			suffix = restaurantName.substring(0, 2).toUpperCase();
			return suffix;
		}
	}

	/**
	 * A method to generate the middle part of the code
	 * 
	 * @param number
	 * @return String of numbers
	 */
	private String numGenerator(int number) {
		String middle;
		if (number < 9) {
			middle = "00" + String.valueOf(number + 1);
		} else if (number > 9 && number <= 99) {
			middle = "0" + String.valueOf(number + 1);
		} else if (number > 99 && number < 1000) {
			middle = String.valueOf(number);
		} else {
			throw new GenericException();
		}
		return middle;
	}

	/**
	 * A method to generate the whole code
	 * 
	 * @param restaurantDetails
	 * @param lastCreatedRestaurant
	 * @return String which is the restaurant code
	 */
	public String restaurantCode(RestaurantDetails restaurantDetails, RestaurantDetails lastCreatedRestaurant) {
		finalsuffix = suffixGenerator(restaurantDetails.getRestaurantName());
		finalmiddle = numGenerator(Integer.parseInt(lastCreatedRestaurant.getRestaurantCode().substring(2, 5)));
		total = prefix + finalmiddle + finalsuffix;
		return total;
	}

	/**
	 * A method to generate the whole code in case it is the first restaurant in the
	 * database
	 * 
	 * @param restaurantDetails
	 * @return String which is the restaurant code
	 */
	public String restaurantCode(RestaurantDetails restaurantDetails) {
		finalsuffix = suffixGenerator(restaurantDetails.getRestaurantName());
		finalmiddle = "001";
		total = prefix + finalmiddle + finalsuffix;
		return total;
	}

}
