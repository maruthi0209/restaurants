package com.example.restaurants.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.NotFoundException;
import com.example.restaurants.exceptions.ValidationException;
import com.example.restaurants.models.Address;
import com.example.restaurants.models.Location;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.repos.InterfaceMenuRepository;

@Component
public class MenuThirdUtil {

	public static final String createdby = "Sethu";
	
	public static final String updatedby = "Sethu";
	
	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime updatedDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	public RestaurantDetails setRestaurantObjectDetails(RestaurantDetails restaurantdetails) {
		restaurantdetails.setRestaurantId(UUID.randomUUID().toString());
		restaurantdetails.setRestaurantCreatedBy(createdby);
		restaurantdetails.setRestaurantCreatedDateTime(LocalDateTime.now());
		restaurantdetails.setRestaurantUpdatedBy(updatedby);
		restaurantdetails.setRestaurantUpdatedDateTime(updatedDateTime);
		if (restaurantdetails.getRestaurantAddress()!=null) {
			restaurantdetails.setRestaurantAddress(setAddressDetails(restaurantdetails.getRestaurantAddress()));
		} else {
			throw new ValidationException("Address cannot be null");
		}
		return restaurantdetails;  
	}
	
	public Address setAddressDetails(Address address) {
		address.setAddressID(UUID.randomUUID().toString());
		address.setAddressCreatedBy(createdby);
		address.setAddressCreatedDateTime(LocalDateTime.now());
		address.setAddressUpdatedBy(updatedby);
		address.setAddressUpdatedDateTime(updatedDateTime);
		if (address.getLocation()!=null) {
			address.setLocation(setLocationDetails(address.getLocation()));
		} else {
			//Throwable throwable = new Throwable("Location cannot be null");
			//throw new ValidationException(throwable);
			throw new ValidationException("Location cannot be null");
		}
		return address;
	}

	public Location setLocationDetails(Location location) {
		location.setLocationId(UUID.randomUUID().toString());
		location.setLocationCreatedBy(createdby);
		location.setLocationCreatedDateTime(LocalDateTime.now());
		location.setLocationUpdatedBy(updatedby);
		location.setLocationUpdatedDateTime(updatedDateTime);
		return location;
	}

	public UserDetails setUserDetails(UserDetails userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setCreatedBy(createdby);
		userDetails.setCreatedTime(LocalDateTime.now());
		userDetails.setUpdatedBy(updatedby);
		userDetails.setUpdatedTime(updatedDateTime);
		userDetails.setUserAddress(setAddressDetails(userDetails.getUserAddress()));
		return userDetails;
		
	}
}
