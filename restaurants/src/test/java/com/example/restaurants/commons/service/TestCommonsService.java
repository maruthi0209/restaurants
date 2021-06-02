package com.example.restaurants.commons.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.entities.UserDetails;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.ValidationException;
import com.example.restaurants.models.RestaurantDetails;
import com.example.restaurants.restaurantservice.RestaurantService;
import com.example.restaurants.service.NewMenuService;
import com.example.restaurants.service.UserService;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuFourthUtil;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.StandardReleaseResponse;

@SpringBootTest
public class TestCommonsService {

	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime dateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	@InjectMocks
	private CommonsService commonsService;
	
	@Mock
	private RestaurantService restaurantservice;
	
	@Mock
	private NewMenuService newMenuService;
	
	@Mock
	private UserService userService;

	@Mock
	private MenuFourthUtil menuFourthUtil;

	@Mock
	private CommonsValidateService commonsvalidateservice;
	
////////////////////////////// testing save new restaurant module ///////////////////////////	
	
	@Test
	@DisplayName("checking positive scenario for assertions in restaurant details")
	void testProcessRestaurantEntitiesPositiveScenario() {
		RestaurantDetails restaurantDetails = new RestaurantDetails();
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(201,
				"Operation Successful. Your restaurant code is FD001SS");
		Mockito.when(commonsvalidateservice.validateRestaurant(restaurantDetails)).thenReturn(expectedList);
		Mockito.when(restaurantservice.handleUserActions(restaurantDetails)).thenReturn(expectedresponse);
		
		ResponseEntity<StandardReleaseResponse> actualResponse = commonsService.processRestaurantEntities(restaurantDetails);

		Mockito.verify(commonsvalidateservice).validateRestaurant(restaurantDetails);
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
	}
	
	@Test
	@DisplayName("checking negative scenario for assertions in restaurant details")
	void testProcessRestaurantEntitiesNegativeScenario() {
		RestaurantDetails restaurantDetails = new RestaurantDetails();
		List<String> actualList = new ArrayList<String>();
		actualList.add("restaurant type cannot be empty");
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(450, actualList.toString());
		Mockito.when(commonsvalidateservice.validateRestaurant(restaurantDetails)).thenReturn(actualList);
		
		Assertions.assertThrows(ValidationException.class, () -> commonsService.processRestaurantEntities(restaurantDetails));
		//ResponseEntity<StandardReleaseResponse> actualResponse = commonsService.processRestaurantEntities(restaurantDetails);
		//Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		//Assertions.assertEquals(expectedresponse.getBody().getResponseMessage(), actualResponse.getBody().getResponseMessage());
	} 
	
	@Test
	@DisplayName("checking negative scenario for service in restaurant details")
	void testProcessRestaurantEntitiesNegativeService() {
		RestaurantDetails restaurantDetails = new RestaurantDetails();
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(501, "failed");
		Mockito.when(commonsvalidateservice.validateRestaurant(restaurantDetails)).thenReturn(expectedList);
		Mockito.when(restaurantservice.handleUserActions(restaurantDetails)).thenReturn(expectedresponse);
	
		ResponseEntity<StandardReleaseResponse> actualresponse = commonsService.processRestaurantEntities(restaurantDetails);
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualresponse.getStatusCodeValue());
	}
	
//////////////////////////////testing delete restaurant  ///////////////////////////	
	
	@Test
	@DisplayName("positive scenario for deleting restaurant by name")
	void testPositiveDeleteRestaurantByName() {
		String restaurantName = "Movie Magic";
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(201, "success message");
		
		Mockito.when(restaurantservice.deleteRestaurantByName(restaurantName)).thenReturn(expectedresponse);
		
		ResponseEntity<StandardReleaseResponse> actual = commonsService.deleteRestaurantEntities(restaurantName);
		
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actual.getStatusCodeValue());
		Mockito.verify(restaurantservice).deleteRestaurantByName(restaurantName);
	}
	
	@Test
	@DisplayName("negative scenario for deleting restaurant by name")
	void testNegativeDeleteRestaurantByName() {
		String restaurantName = "Movie Magic";
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(501, "failed");
		Mockito.when(restaurantservice.deleteRestaurantByName(restaurantName)).thenReturn(expectedresponse);
		
		ResponseEntity<StandardReleaseResponse> actual = commonsService.deleteRestaurantEntities(restaurantName);
		
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actual.getStatusCodeValue());
		Mockito.verify(restaurantservice).deleteRestaurantByName(restaurantName);
	}
	
/////////////////////  testing saving menu module  /////////////////////////////////////	
	
	@Test
	@DisplayName("checking positive scenario for assertions in menu details")
	void testProcessMenuEntitiesPositiveScenario() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu filledMenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		
		MenuItem postmanitem = new MenuItem(null,"idly", 30, null, null,null, null);
		List<MenuItem> postmanitemlist = new ArrayList<MenuItem>();
		postmanitemlist.add(postmanitem);
		Category postmancategory = new Category(null, "breakfast", postmanitemlist, null, null,null, null);
		List<Category> listofcategories = new ArrayList<Category>();
		listofcategories.add(postmancategory);
		Division postmandivision = new Division(null, "indian", listofcategories, null, null,null, null);
		List<Division> listofdivisions = new ArrayList<Division>();
		listofdivisions.add(postmandivision);
		Menu menu = new Menu(null, "FD004NH", listofdivisions, null, null,null, null);
		
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(201, "New Menu saved.");
		Mockito.when(commonsvalidateservice.validateMenu(menu)).thenReturn(expectedList);
		Mockito.when(newMenuService.handleUserActions(filledMenu)).thenReturn(expectedresponse);
		Mockito.when(menuFourthUtil.setNewMenuDetails(menu)).thenReturn(filledMenu);
		
		ResponseEntity<StandardReleaseResponse> actualResponse = commonsService.processMenuEntities(menu);

		Mockito.verify(commonsvalidateservice).validateMenu(filledMenu);
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Assertions.assertEquals(expectedresponse.getBody().getResponseMessage(), actualResponse.getBody().getResponseMessage());
	}
	
	@Test
	@DisplayName("checking negative scenario for assertions in menu details")
	void testProcessMenuEntitiesNegativeScenario() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu filledMenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		
		MenuItem postmanitem = new MenuItem(null,"idly", 30, null, null,null, null);
		List<MenuItem> postmanitemlist = new ArrayList<MenuItem>();
		postmanitemlist.add(postmanitem);
		Category postmancategory = new Category(null, "breakfast", postmanitemlist, null, null,null, null);
		List<Category> listofcategories = new ArrayList<Category>();
		listofcategories.add(postmancategory);
		Division postmandivision = new Division(null, "indian", listofcategories, null, null,null, null);
		List<Division> listofdivisions = new ArrayList<Division>();
		listofdivisions.add(postmandivision);
		Menu menu = new Menu(null, "FD004NH", listofdivisions, null, null,null, null);
		
		List<String> actualList = new ArrayList<String>();
		actualList.add("Category cannot be null");
		actualList.add("ItemName cannot be blank");
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(450, actualList.toString());
		Mockito.when(commonsvalidateservice.validateMenu(filledMenu)).thenReturn(actualList);
		Mockito.when(menuFourthUtil.setNewMenuDetails(menu)).thenReturn(filledMenu);
		
		Assertions.assertThrows(ValidationException.class, () -> commonsService.processMenuEntities(menu));
		//ResponseEntity<StandardReleaseResponse> actualResponse = commonsService.processMenuEntities(menu);
		//Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		//Assertions.assertEquals(expectedresponse.getBody().getResponseMessage(), actualResponse.getBody().getResponseMessage());
	} 
	
	@Test
	@DisplayName("checking negative scenario for service in menu details")
	void testProcessMenuEntitiesNegativeService() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu filledMenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		
		MenuItem postmanitem = new MenuItem(null,"idly", 30, null, null,null, null);
		List<MenuItem> postmanitemlist = new ArrayList<MenuItem>();
		postmanitemlist.add(postmanitem);
		Category postmancategory = new Category(null, "breakfast", postmanitemlist, null, null,null, null);
		List<Category> listofcategories = new ArrayList<Category>();
		listofcategories.add(postmancategory);
		Division postmandivision = new Division(null, "indian", listofcategories, null, null,null, null);
		List<Division> listofdivisions = new ArrayList<Division>();
		listofdivisions.add(postmandivision);
		Menu menu = new Menu(null, "FD004NH", listofdivisions, null, null,null, null);
		
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(501, "failed");
		Mockito.when(commonsvalidateservice.validateMenu(menu)).thenReturn(expectedList);
		Mockito.when(newMenuService.handleUserActions(filledMenu)).thenReturn(expectedresponse);
		Mockito.when(menuFourthUtil.setNewMenuDetails(menu)).thenReturn(filledMenu);
		
		ResponseEntity<StandardReleaseResponse> actualresponse = commonsService.processMenuEntities(menu);
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualresponse.getStatusCodeValue());
	}

////////////////////// testing update menu module  ///////////////////////////////	
	
	@Test
	@DisplayName("checking positive scenario for assertions in update menu details")
	void testUpdateMenuEntitiesPositiveScenario() {
		MenuItem postmanitem = new MenuItem(null,"idly", 30, null, null,null, null);
		List<MenuItem> postmanitemlist = new ArrayList<MenuItem>();
		postmanitemlist.add(postmanitem);
		Category postmancategory = new Category(null, "breakfast", postmanitemlist, null, null,null, null);
		List<Category> listofcategories = new ArrayList<Category>();
		listofcategories.add(postmancategory);
		Division postmandivision = new Division(null, "indian", listofcategories, null, null,null, null);
		List<Division> listofdivisions = new ArrayList<Division>();
		listofdivisions.add(postmandivision);
		Menu menu = new Menu(null, "FD004NH", listofdivisions, null, null,null, null);
		
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(201, "New Menu saved.");
		Mockito.when(commonsvalidateservice.validateMenu(menu)).thenReturn(expectedList);
		Mockito.when(newMenuService.updateUserActions(menu, menu.getMenuId())).thenReturn(expectedresponse);
		
		ResponseEntity<StandardReleaseResponse> actualResponse = commonsService.updateMenuEntities(menu, menu.getMenuId());

		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Assertions.assertEquals(expectedresponse.getBody().getResponseMessage(), actualResponse.getBody().getResponseMessage());
	}
	
	@Test
	@DisplayName("checking negative scenario for assertions in update menu details")
	void testUpdateMenuEntitiesNegativeScenario() {
		MenuItem postmanitem = new MenuItem(null,"idly", 30, null, null,null, null);
		List<MenuItem> postmanitemlist = new ArrayList<MenuItem>();
		postmanitemlist.add(postmanitem);
		Category postmancategory = new Category(null, "breakfast", postmanitemlist, null, null,null, null);
		List<Category> listofcategories = new ArrayList<Category>();
		listofcategories.add(postmancategory);
		Division postmandivision = new Division(null, "indian", listofcategories, null, null,null, null);
		List<Division> listofdivisions = new ArrayList<Division>();
		listofdivisions.add(postmandivision);
		Menu menu = new Menu(null, "FD004NH", listofdivisions, null, null,null, null);
		
		List<String> actualList = new ArrayList<String>();
		actualList.add("Category cannot be null");
		actualList.add("ItemName cannot be blank");
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(450, actualList.toString());
		Mockito.when(commonsvalidateservice.validateMenu(menu)).thenReturn(actualList);
		
		Assertions.assertThrows(ValidationException.class, () -> commonsService.updateMenuEntities(menu, menu.getMenuId()));
		//ResponseEntity<StandardReleaseResponse> actualResponse = commonsService.updateMenuEntities(menu, menu.getMenuId());
		//Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		//Assertions.assertEquals(expectedresponse.getBody().getResponseMessage(), actualResponse.getBody().getResponseMessage());
	} 
	
	@Test
	@DisplayName("checking negative scenario for service in update menu details")
	void testUpdateMenuEntitiesNegativeService() {
		MenuItem postmanitem = new MenuItem(null,"idly", 30, null, null,null, null);
		List<MenuItem> postmanitemlist = new ArrayList<MenuItem>();
		postmanitemlist.add(postmanitem);
		Category postmancategory = new Category(null, "breakfast", postmanitemlist, null, null,null, null);
		List<Category> listofcategories = new ArrayList<Category>();
		listofcategories.add(postmancategory);
		Division postmandivision = new Division(null, "indian", listofcategories, null, null,null, null);
		List<Division> listofdivisions = new ArrayList<Division>();
		listofdivisions.add(postmandivision);
		Menu menu = new Menu(null, "FD004NH", listofdivisions, null, null,null, null);
		
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(501, "failed");
		Mockito.when(commonsvalidateservice.validateMenu(menu)).thenReturn(expectedList);
		Mockito.when(newMenuService.updateUserActions(menu, menu.getMenuId())).thenReturn(expectedresponse);
		
		ResponseEntity<StandardReleaseResponse> actualresponse = commonsService.updateMenuEntities(menu, menu.getMenuId());
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualresponse.getStatusCodeValue());
	}
	
/////////////////////  testing user module  /////////////////////////////////////	
	
	@Test
	@DisplayName("checking positive scenario for assertions in user details")
	void testProcessUserEntitiesPositiveScenario() {
		UserDetails userDetails = new UserDetails();
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(201, "successfully created new user");
		Mockito.when(commonsvalidateservice.validateUser(userDetails)).thenReturn(expectedList);
		Mockito.when(userService.handleUserActions(userDetails)).thenReturn(expectedresponse);
		
		ResponseEntity<StandardReleaseResponse> actualresponse = commonsService.processUserEntities(userDetails);

		Mockito.verify(commonsvalidateservice).validateUser(userDetails);
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualresponse.getStatusCodeValue());
	}
		
	@Test
	@DisplayName("checking negative scenario for assertions in user details")
	void testProcessUserEntitiesNegativeScenario() {
		UserDetails userDetails = new UserDetails();
		List<String> actualList = new ArrayList<String>();
		actualList.add("UserAddress cannot be null");
		actualList.add("UserName cannot be blank");
		assertFalse(actualList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(450, actualList.toString());
		Mockito.when(commonsvalidateservice.validateUser(userDetails)).thenReturn(actualList);
		
		Assertions.assertThrows(ValidationException.class, () -> commonsService.processUserEntities(userDetails));
		//ResponseEntity<StandardReleaseResponse> actualresponse = commonsService.processUserEntities(userDetails);
		//Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualresponse.getStatusCodeValue());
		//Assertions.assertEquals(expectedresponse.getBody().getResponseMessage(), actualresponse.getBody().getResponseMessage());
	} 
	
	@Test
	@DisplayName("checking negative scenario for service in user details")
	void testProcessUserEntitiesNegativeService() {
		UserDetails userDetails = new UserDetails();
		List<String> expectedList = new ArrayList<String>();
		Assertions.assertTrue(expectedList.isEmpty());
		ResponseEntity<StandardReleaseResponse> expectedresponse = new GlobalResponse().createResponseEntity(501, "failed");
		Mockito.when(commonsvalidateservice.validateUser(userDetails)).thenReturn(expectedList);
		Mockito.when(userService.handleUserActions(userDetails)).thenReturn(expectedresponse);

		ResponseEntity<StandardReleaseResponse> actualresponse = commonsService.processUserEntities(userDetails);
		Assertions.assertEquals(expectedresponse.getStatusCodeValue(), actualresponse.getStatusCodeValue());
	}
}
