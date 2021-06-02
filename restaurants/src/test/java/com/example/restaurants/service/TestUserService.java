package com.example.restaurants.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.models.Address;
import com.example.restaurants.models.Location;
import com.example.restaurants.repos.InterfaceUserRepository;
import com.example.restaurants.repository.IRestaurantRepository;
import com.example.restaurants.util.StandardReleaseResponse;

@SpringBootTest
public class TestUserService {

	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime dateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	@InjectMocks
	private UserService userService;

	@Mock
	private InterfaceUserRepository interfaceUserRepository;
	
	@Mock
	private IRestaurantRepository iRestaurantRepository;

	@BeforeEach
	public void setMockSetup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("positive scenario for new saving user details")
	void testForPositiveScenarioNewUserDetails() {
		Location location = new Location("1002", 13.0827, 80.2707, "sethu", LocalDateTime.now(), "sethu",
				LocalDateTime.now());
		Address address = new Address("1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu",
				LocalDateTime.now(), "sethu", LocalDateTime.now());
		UserDetails userDetails = new UserDetails("fef488b1-db97-45a5-89fe-3cdf090918c4", "ramakrishna", address, null,
				"sethu", dateTime, "sethu",dateTime );

		ResponseEntity<StandardReleaseResponse> response = userService.handleUserActions(userDetails);

		Mockito.verify(iRestaurantRepository).insertIntoLocationTable(location.getLocationId(), location.getLatitude(), location.getLocationCreatedBy(), location.getLocationCreatedDateTime(), location.getLocationUpdatedBy(), location.getLocationUpdatedDateTime(), location.getLongitude());
		Mockito.verify(iRestaurantRepository).insertIntoAddressTable(address.getAddressID(), address.getCity(), address.getAddressCreatedBy(), address.getAddressCreatedDateTime(), address.getLocality(), address.getStreet(), address.getAddressUpdatedBy(), address.getAddressUpdatedDateTime(), address.getLocation().getLocationId());
		Mockito.verify(interfaceUserRepository).insertIntoUserDetailsTable(userDetails.getUserId(),
				userDetails.getCreatedBy(), userDetails.getCreatedTime(), userDetails.getUserName(),
				userDetails.getUserAddress().getAddressID(), userDetails.getUpdatedBy(), userDetails.getUpdatedTime());
		Assertions.assertEquals(201, response.getStatusCodeValue());
	}

	@Test
	@DisplayName("Negative scenario for new saving user details")
	void testForNegativeScenarioNewUserDetails() {
		Location location = new Location();
		location.setLocationId("1002");
		location.setLatitude(13.0827);
		location.setLongitude(80.2707);
		location.setLocationCreatedBy("sethu");
		location.setLocationCreatedDateTime(LocalDateTime.now());
		location.setLocationUpdatedBy("sethu");
		location.setLocationUpdatedDateTime(LocalDateTime.now());
		Address address = new Address();
		address.setAddressID("1002");
		address.setStreet("203 Swastik apts");
		address.setLocality("Mothinagar");
		address.setCity("hyderabad");
		address.setLocation(location);
		address.setAddressCreatedBy("sethu");
		address.setAddressCreatedDateTime(LocalDateTime.now());
		address.setAddressUpdatedBy("sethu");
		address.setAddressUpdatedDateTime(LocalDateTime.now());
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId("fef488b1-db97-45a5-89fe-3cdf090918c4");
		userDetails.setUserName("ramakrishna");
		userDetails.setUserAddress(address);
		userDetails.setCartDetails(null);
		userDetails.setCreatedBy("sethu");
		userDetails.setCreatedTime(LocalDateTime.now());
		userDetails.setUpdatedBy("sethu");
		userDetails.setUpdatedTime(LocalDateTime.now());
		
		Mockito.when(interfaceUserRepository.insertIntoUserDetailsTable(userDetails.getUserId(), userDetails.getCreatedBy(), userDetails.getCreatedTime(), userDetails.getUserName(), userDetails.getUserAddress().getAddressID(), userDetails.getUpdatedBy(), userDetails.getUpdatedTime())).thenThrow(GenericException.class);

		//ResponseEntity<StandardReleaseResponse> expected = userService.handleUserActions(userDetails);
		Assertions.assertThrows(GenericException.class, () -> userService.handleUserActions(userDetails));
		//Assertions.assertEquals(501, expected.getStatusCodeValue());
	}
}
