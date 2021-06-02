package com.example.restaurants.commons.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.ValidationException;
import com.example.restaurants.models.Address;
import com.example.restaurants.models.Location;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.repository.IRestaurantRepository;
import com.example.restaurants.util.MenuThirdUtil;

@SpringBootTest
public class TestCommonsValidateService {

	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime dateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	@InjectMocks
	private CommonsValidateService commonsValidateService;
	
	@Mock
	private MenuThirdUtil menuThirdUtil;
	
	@Mock
	private IRestaurantRepository iRestaurantRepository;
	
	@BeforeEach
	public void setMockSetup() {
		MockitoAnnotations.openMocks(this);
	} 
	
	@Test
	@DisplayName("positive scenario when there are no violations for restaurantDetails")
	void testForNoRestaurantViolations() {
		Location location = new Location( null,13.0827, 80.2707, null, null, null, null);
		Address address = new Address( null, "203 Swastik apts", "Mothinagar", "hyderabad", location, null, null, null, null);
		RestaurantDetails postmanObject = new RestaurantDetails(null,"Savi Sagar", address, "veg", null, null,null,null, null);
		ArrayList<String> restaurantList = new ArrayList<String>();
		Mockito.when(iRestaurantRepository.findRestaurantName(postmanObject.getRestaurantName())).thenReturn(restaurantList);
		
		List<String> expected = commonsValidateService.validateRestaurant(postmanObject);
		
		Assertions.assertTrue(expected.isEmpty());
	}
	
	@Test
	@DisplayName("negative scenario when there are existing restaurant name for restaurantDetails")
	void testForExistingRestaurantNameViolations() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		RestaurantDetails postmanObject = new RestaurantDetails(null,"Savi Sagar", address, "veg", null, null,null,null, null);
		RestaurantDetails withoutResCode = new RestaurantDetails("1","Savi Sagar", address, "veg", "sethu", dateTime, "sethu", dateTime, null);
		ArrayList<String> restaurantList = new ArrayList<String>();
		restaurantList.add("Savi Sagar");
		Mockito.when(iRestaurantRepository.findRestaurantName(withoutResCode.getRestaurantName())).thenReturn(restaurantList);
		
		List<String> expected = commonsValidateService.validateRestaurant(postmanObject);
		
		Assertions.assertFalse(expected.isEmpty());
	}
	
	@Test
	@DisplayName("negative scenario when there is exception for finding restaurantDetails name")
	void testForExistingRestaurantNameViolationsException() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		RestaurantDetails postmanObject = new RestaurantDetails(null,"Savi Sagar", address, "veg", null, null,null,null, null);
		RestaurantDetails withoutResCode = new RestaurantDetails("1","Savi Sagar", address, "veg", "sethu", dateTime, "sethu", dateTime, null);
		Mockito.when(iRestaurantRepository.findRestaurantName(withoutResCode.getRestaurantName())).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> commonsValidateService.validateRestaurant(postmanObject));
	}
	
	@Test
	@DisplayName("negative scenario when there are validations errors for restaurantDetails")
	void testForValidationViolations() {
		RestaurantDetails postmanObject = new RestaurantDetails(null,"%&^ (*", null, "veg", null, null,null,null, null);
			
		List<String> expected = commonsValidateService.validateRestaurant(postmanObject);
		
		Assertions.assertFalse(expected.isEmpty());
	}
	
/////////////////////  testing menu module  /////////////////////////////////////	
	
	@Test
	@DisplayName("positive scenario when there are no violations for menu")
	void testForNoMenuviolations() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
				
		List<String> expected = commonsValidateService.validateMenu(menu);
		
		Assertions.assertTrue(expected.isEmpty());
	}
	
	@Test
	@DisplayName("Negative scenario for menu violations in validator factory")
	void testForMenuViolationsInValidatorFactory( ) {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "i", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", null, divisions, "Sethu", dateTime,"Sethu", dateTime);
		
		List<String> expected = commonsValidateService.validateMenu(menu);
		
		Assertions.assertFalse(expected.isEmpty());
	}
	
/////////////////////  testing user module  /////////////////////////////////////	
	
	@Test
	@DisplayName("Positive Scenario for Validate user details")
	void testForValidateUserDetailsPositive() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		UserDetails postmanObject = new UserDetails(null,"Savi Sagar", address, null, null, null,null,null);
		UserDetails filleduser = new UserDetails("1","Savi Sagar", address, null, "sethu", dateTime, "sethu", dateTime);
		Mockito.when(menuThirdUtil.setUserDetails(postmanObject)).thenReturn(filleduser);
		
		List<String> expected = commonsValidateService.validateUser(postmanObject);
		
		Assertions.assertTrue(expected.isEmpty());
	}
	
	@Test
	@DisplayName("Positive Scenario for Validate user details")
	void testForValidateUserDetailsNegative() {
		Location location = new Location( "1002",13.0827, 80.2707, "sethu", dateTime, "sethu", dateTime);
		Address address = new Address( "1002", "203 Swastik apts", "Mothinagar", "hyderabad", location, "sethu", dateTime, "sethu", dateTime);
		UserDetails postmanObject = new UserDetails(null,"Savi Sagar", address, null, null, null,null,null);
		UserDetails filleduser = new UserDetails("1","S", address, null, "sethu", dateTime, "sethu", dateTime);
		Mockito.when(menuThirdUtil.setUserDetails(postmanObject)).thenReturn(filleduser);
		
		List<String> expected = commonsValidateService.validateUser(postmanObject);
		
		Assertions.assertFalse(expected.isEmpty());
	}
	
}
