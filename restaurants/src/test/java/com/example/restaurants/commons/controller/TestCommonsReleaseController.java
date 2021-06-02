package com.example.restaurants.commons.controller;

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

import com.example.restaurants.commons.service.CommonsService;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.models.Address;
import com.example.restaurants.models.Location;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.StandardReleaseResponse;

@SpringBootTest
public class TestCommonsReleaseController {

	@InjectMocks
	private CommonsReleaseController commonsReleaseController;
	
	@Mock
	private CommonsService commonsService;
	
	@BeforeEach
	public void setMockSetup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("positive scenario for saving restaurant details")
	void testPositiveScenarioForSaveRestaurantDetails() {
		RestaurantDetails restaurant = new RestaurantDetails();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201, "Operation Successful. Your restaurant code is FD004NR");
		Mockito.when(commonsService.processRestaurantEntities(restaurant)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerRestaurants(restaurant);
		
		Mockito.verify(commonsService).processRestaurantEntities(restaurant);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
	}
	
	@Test
	@DisplayName("Negative scenario for saving restaurant details")
	void testNegativeScenarioForSaveRestaurantDetails() {
		RestaurantDetails restaurant = new RestaurantDetails();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "an exception occured");
		Mockito.when(commonsService.processRestaurantEntities(restaurant)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerRestaurants(restaurant);
		
		Mockito.verify(commonsService).processRestaurantEntities(restaurant);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
	}
	
///////////////////////////testing delete restaurant ////////////////////////	
	
	@Test
	@DisplayName("positive scenario for deleting restaurant by name")
	void testPositiveScenarioForDeleteRestaurant() {
		String restaurantName = "Movie Magic";
		ResponseEntity<StandardReleaseResponse> expected = new GlobalResponse().createResponseEntity(201, "responseMessage");
		Mockito.when(commonsService.deleteRestaurantEntities(restaurantName)).thenReturn(expected);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerDeleteRestaurant(restaurantName);
		
		Assertions.assertEquals(expected.getStatusCodeValue(), response.getStatusCodeValue());
		Mockito.verify(commonsService).deleteRestaurantEntities(restaurantName);
	}
	
	@Test
	@DisplayName("Negative scenario for deleting restaurant by name")
	void testNegativeScenarioForDeleteRestaurant() {
		String restaurantName = "Movie Magic";
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "an exception occured");
		Mockito.when(commonsService.deleteRestaurantEntities(restaurantName)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerDeleteRestaurant(restaurantName);

		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
		Mockito.verify(commonsService).deleteRestaurantEntities(restaurantName);
	}
	
	
	
/////////////////////  testing menu module  /////////////////////////////////////	
	
	@Test
	@DisplayName("positive scenario for saving menu details")
	void testPositiveScenarioForSaveMenuDetails() {
		Menu menu = new Menu();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201, "New Menu saved.");
		Mockito.when(commonsService.processMenuEntities(menu)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerMenu(menu);
		
		Mockito.verify(commonsService).processMenuEntities(menu);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
	}
	
	@Test
	@DisplayName("Negative scenario for saving menu details")
	void testNegativeScenarioForSaveMenuDetails() {
		Menu menu = new Menu();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "an exception occured");
		Mockito.when(commonsService.processMenuEntities(menu)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerMenu(menu);
		
		Mockito.verify(commonsService).processMenuEntities(menu);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
	}

/////////////////////  testing user module  /////////////////////////////////////	
	
	@Test
	@DisplayName("positive scenario for saving user details")
	void testPositiveScenarioForSaveUserDetails() {
		UserDetails userDetails = new UserDetails();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201, "New Menu saved.");
		Mockito.when(commonsService.processUserEntities(userDetails)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerUser(userDetails);
		
		Mockito.verify(commonsService).processUserEntities(userDetails);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
	}
	
	@Test
	@DisplayName("Negative scenario for saving user details")
	void testNegativeScenarioForSaveUserDetails() {
		UserDetails userDetails = new UserDetails();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "an exception occured");
		Mockito.when(commonsService.processUserEntities(userDetails)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = commonsReleaseController.commonsReleaseControllerUser(userDetails);
		
		Mockito.verify(commonsService).processUserEntities(userDetails);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
	}
	
	
}
