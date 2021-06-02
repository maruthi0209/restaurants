package com.example.restaurants.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.models.Address;
import com.example.restaurants.models.Location;
import com.example.restaurants.repos.InterfaceUserRepository;
import com.example.restaurants.repository.IRestaurantRepository;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.StandardReleaseResponse;

@Service
public class UserService {

	@Autowired
	private InterfaceUserRepository interfaceUserRepository;
	
	@Autowired
	private IRestaurantRepository iRestaurantRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(NewMenuService.class);
	
	public static final String createdBy = "Sethu";
	public static final String updatedBy = "Sethu";
	
	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime updatedDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);

	@Transactional
	public ResponseEntity<StandardReleaseResponse> handleUserActions(UserDetails userDetails) {
		try {
		logger.info("creating a new user");
		return createUserDetails(userDetails);
		}  catch (Exception exception) {
			throw new GenericException(exception);
			//return new GlobalResponse().createResponseEntity(501, exception.getMessage());
		}
	}

	@Transactional
	private ResponseEntity<StandardReleaseResponse> createUserDetails(UserDetails userDetails) {
		
			System.out.println(userDetails);
			Address userAddress = userDetails.getUserAddress();
			Location userLocation = userAddress.getLocation();
			iRestaurantRepository.insertIntoLocationTable(userLocation.getLocationId(), userLocation.getLatitude(), userLocation.getLocationCreatedBy(), userLocation.getLocationCreatedDateTime(), userLocation.getLocationUpdatedBy(), userLocation.getLocationUpdatedDateTime(), userLocation.getLongitude());
			iRestaurantRepository.insertIntoAddressTable(userAddress.getAddressID(), userAddress.getCity(), userAddress.getAddressCreatedBy(), userAddress.getAddressCreatedDateTime(), userAddress.getLocality(), userAddress.getStreet(), userAddress.getAddressUpdatedBy(), userAddress.getAddressUpdatedDateTime(), userAddress.getLocation().getLocationId());
			interfaceUserRepository.insertIntoUserDetailsTable(userDetails.getUserId(), userDetails.getCreatedBy(), userDetails.getCreatedTime(), userDetails.getUserName(), userDetails.getUserAddress().getAddressID(), userDetails.getUpdatedBy(), userDetails.getUpdatedTime());
			return new GlobalResponse().createResponseEntity(201, "successfully created new user");
	}
}
