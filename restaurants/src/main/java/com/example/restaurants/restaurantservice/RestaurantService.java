package com.example.restaurants.restaurantservice;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.NotFoundException;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.repository.IRestaurantRepository;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.RestaurantCode;
import com.example.restaurants.util.StandardReleaseResponse;

/**
 * A service class for Data Access operations related to Restaurants
 * 
 * @author schennapragada
 *
 */
@Service
public class RestaurantService {

	@Autowired
	private IRestaurantRepository iRestaurantRepository;

	@Autowired
	private RestaurantCode restaurantCode;
	
	@Autowired
	private MenuThirdUtil menuThirdUtil;
	
	/**
	 * Method to create new restaurant details
	 * 
	 * @param restaurantdetails
	 * @return object of type restaurant details
	 */
	public ResponseEntity<StandardReleaseResponse> handleUserActions(RestaurantDetails restaurantdetails) {
		return createRestaurant(restaurantdetails);
	}

	/**
	 * Private method to create new restaurant details
	 * 
	 * @param restaurantdetails
	 * @return object of type menulist
	 */
	private ResponseEntity<StandardReleaseResponse> createRestaurant(RestaurantDetails restaurantdetails) {
		try {
			RestaurantDetails filledRestaurantDetails = menuThirdUtil.setRestaurantObjectDetails(restaurantdetails);
			System.out.println(restaurantdetails);
			if (iRestaurantRepository.findCount() == 0) {
				restaurantdetails.setRestaurantCode(restaurantCode.restaurantCode(filledRestaurantDetails));
				iRestaurantRepository.save(filledRestaurantDetails);
			} else {
				RestaurantDetails lastCreatedRestaurant = iRestaurantRepository.findLastCreatedRestaurant();
				restaurantdetails.setRestaurantCode(restaurantCode.restaurantCode(filledRestaurantDetails, lastCreatedRestaurant));
				iRestaurantRepository.save(filledRestaurantDetails);
			}
			return new GlobalResponse().createResponseEntity(201, "Operation Successful. Your restaurant code is " + restaurantdetails.getRestaurantCode());
		} catch (Exception exception) {
			throw new GenericException(exception);
			//return new GlobalResponse().createResponseEntity(501, exception.getMessage());
		}
	}
	
	@Transactional
	public ResponseEntity<StandardReleaseResponse> deleteRestaurantByName(String restaurantName) {
		try {
		Optional<RestaurantDetails> presentRestaurantDetails = iRestaurantRepository.getRestaurantDetailsForName(restaurantName);
		if (!presentRestaurantDetails.isEmpty()) {
			iRestaurantRepository.deleteRestaurant(presentRestaurantDetails.get().getRestaurantAddress().getAddressID());
			String locationId = presentRestaurantDetails.get().getRestaurantAddress().getLocation().getLocationId();
			iRestaurantRepository.deleteAddress(locationId);
			iRestaurantRepository.deleteLocation(locationId);
			return new GlobalResponse().createResponseEntity(201, "Successfully deleted restaurant with name: " + restaurantName);
		} else {
			throw new NotFoundException("Restaurant not found");
		}	
		} catch (Exception exception) {
			throw new GenericException(exception);
		}
	}

}
