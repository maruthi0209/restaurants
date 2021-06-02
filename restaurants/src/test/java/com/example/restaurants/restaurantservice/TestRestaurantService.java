package com.example.restaurants.restaurantservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

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

import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.NotFoundException;
import com.example.restaurants.models.Address;
import com.example.restaurants.models.Location;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.repository.IRestaurantRepository;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.RestaurantCode;
import com.example.restaurants.util.StandardReleaseResponse;

@SpringBootTest
class TestRestaurantService {

	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime dateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	@InjectMocks
	private RestaurantService restaurantService;
	
	@Mock 
	private IRestaurantRepository iRestaurantRepository;
	
	@Mock 
	private RestaurantCode restaurantCode;
	
	@Mock
	private GlobalResponse globalResponse;
	
	@Mock
	private MenuThirdUtil menuThirdUtil;
	
	@BeforeEach
	public void setMockSetup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("when a new restaurant details are sent to service, call create restaurant private method")
	void testIfHandleUserActions() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		RestaurantDetails postmanObject = new RestaurantDetails(null,"Savi Sagar", address, "veg", null, null, null, null, null);
		RestaurantDetails withoutResCode = new RestaurantDetails("1","Savi Sagar", address, "veg", "sethu", dateTime, "sethu", dateTime, null);
		Mockito.when(menuThirdUtil.setRestaurantObjectDetails(postmanObject)).thenReturn(withoutResCode);
		Mockito.when(iRestaurantRepository.findCount()).thenReturn(0);
		Mockito.when(restaurantCode.restaurantCode(withoutResCode)).thenReturn("FD001SS");
		
		ResponseEntity<StandardReleaseResponse> expected = restaurantService.handleUserActions(withoutResCode);
		
		Assertions.assertEquals(201,expected.getStatusCodeValue());
		
	}
	
	@Test
	@DisplayName("when a new restaurant details are sent to service with existing details, call create restaurant private method")
	void testIfHandleUserActionsExisting() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		RestaurantDetails withoutResCode = new RestaurantDetails("1","Savi Sagar", address, "veg", "sethu", dateTime, "sethu", dateTime, null);
		RestaurantDetails lastCreatedRestaurant = new RestaurantDetails("187","NH4 Restaurant", address, "Chinese", "sethu", dateTime, "sethu", dateTime, "FD003NR");
		RestaurantDetails postmanObject = new RestaurantDetails(null,"Savi Sagar", address, "veg", null, null, null, null, null);
		Mockito.when(menuThirdUtil.setRestaurantObjectDetails(postmanObject)).thenReturn(withoutResCode);
		Mockito.when(iRestaurantRepository.findCount()).thenReturn(3);
		Mockito.when(iRestaurantRepository.findLastCreatedRestaurant()).thenReturn(lastCreatedRestaurant);
		Mockito.when(restaurantCode.restaurantCode(withoutResCode, lastCreatedRestaurant)).thenReturn("FD004SS");
		
		ResponseEntity<StandardReleaseResponse> expected = restaurantService.handleUserActions(withoutResCode);
		
		Assertions.assertEquals(201,expected.getStatusCodeValue());
	}
	
	@Test
	@DisplayName("exception occurs") 
	void testWhenExceptionOccurs() {
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
		RestaurantDetails withoutResCode = new RestaurantDetails();
		withoutResCode.setRestaurantId("1");
		withoutResCode.setRestaurantName("Savi Sagar");
		withoutResCode.setRestaurantAddress(address);
		withoutResCode.setRestaurantType("veg");
		withoutResCode.setRestaurantCreatedBy("sethu");
		withoutResCode.setRestaurantCreatedDateTime(LocalDateTime.now());
		withoutResCode.setRestaurantUpdatedBy(null);
		withoutResCode.setRestaurantUpdatedDateTime(null);
		withoutResCode.setRestaurantCode(null);
		withoutResCode.getRestaurantId();
		withoutResCode.getRestaurantType();
		withoutResCode.getRestaurantCreatedBy();
		withoutResCode.getRestaurantCreatedDateTime();
		withoutResCode.getRestaurantUpdatedBy();
		withoutResCode.getRestaurantUpdatedDateTime();
		Mockito.when(iRestaurantRepository.findCount()).thenThrow(NoSuchElementException.class);
		Assertions.assertThrows(GenericException.class, () -> restaurantService.handleUserActions(withoutResCode));
		//ResponseEntity<StandardReleaseResponse> expected = restaurantService.handleUserActions(withoutResCode);
		//Assertions.assertEquals(501, expected.getStatusCodeValue());
	}
	
	@Test
	@DisplayName("positive scenario for deletion")
	void testWhenDeleteRestaurant() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		RestaurantDetails restaurant = new RestaurantDetails("187","NH4 Restaurant", address, "Chinese", "sethu", dateTime, "sethu", dateTime, "FD003NR");
		Optional<RestaurantDetails> presentRestaurantDetails = Optional.of(restaurant);
		
		Mockito.when(iRestaurantRepository.getRestaurantDetailsForName(restaurant.getRestaurantName())).thenReturn(presentRestaurantDetails);
		ResponseEntity<StandardReleaseResponse> expected = new GlobalResponse().createResponseEntity(201, "responseMessage");
		
		ResponseEntity<StandardReleaseResponse> actual = restaurantService.deleteRestaurantByName(restaurant.getRestaurantName());
		
		Assertions.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
		Mockito.verify(iRestaurantRepository).deleteLocation(location.getLocationId());
		Mockito.verify(iRestaurantRepository).deleteAddress(location.getLocationId());
		Mockito.verify(iRestaurantRepository).deleteRestaurant(address.getAddressID());
	}
	
	@Test
	@DisplayName("not found exception scenario for deletion")
	void testWhenDeleteRestaurantNotFound() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		RestaurantDetails restaurant = new RestaurantDetails("187","NH4 Restaurant", address, "Chinese", "sethu", dateTime, "sethu", dateTime, "FD003NR");
		Optional<RestaurantDetails> presentRestaurantDetails = Optional.empty();
		
		Mockito.when(iRestaurantRepository.getRestaurantDetailsForName(restaurant.getRestaurantName())).thenReturn(presentRestaurantDetails);
		
		Assertions.assertThrows(GenericException.class, () -> restaurantService.deleteRestaurantByName(restaurant.getRestaurantName()));
	}
}
