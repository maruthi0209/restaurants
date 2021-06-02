package com.example.restaurants.commons.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.http.ResponseEntity;

import com.example.restaurants.commons.service.CommonsService;
import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.NotFoundException;
import com.example.restaurants.service.NewMenuService;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.StandardReleaseResponse;

@SpringBootTest
public class TestMenuController {

	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime dateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	@InjectMocks
	private MenuController menuController;
	
	@Mock
	private NewMenuService newMenuService;
	
	@Mock
	private CommonsService commonsService;
	
	@BeforeEach
	public void setMockSetup() {
		MockitoAnnotations.openMocks(this);
	}
	
///////////////////////////// testing get menu by id //////////////////////////////////		
	
	@Test
	@DisplayName("positive scenario for find a menu by given id")
	void testForGetMenuByIdPositive() {
		String menuId = "fef488b1-db97-45a5-89fe-3cdf090918c4";
		String menuDetails = "these are details about menu";
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201, menuDetails);
		Mockito.when(newMenuService.findById(menuId)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = menuController.commonsReleaseControllerMenuGet(menuId);
		
		Mockito.verify(newMenuService).findById(menuId);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
		Assertions.assertEquals(menuDetails, response.getBody().getResponseMessage());
	}
	
	@Test
	@DisplayName("Negative scenario for find a menu by given id")
	void testForGetMenuByIdvNegative() {
		String menuId = "fef488b1-db97-45a5-89fe-3cdf090918c4";
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(404, "Menu not found");
		Mockito.when(newMenuService.findById(menuId)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = menuController.commonsReleaseControllerMenuGet(menuId);
		
		Mockito.verify(newMenuService).findById(menuId);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
		Assertions.assertEquals(expectedResponse.getBody().getResponseMessage(), response.getBody().getResponseMessage());
	}

///////////////////////////// testing update menu //////////////////////////////////	
	
	@Test
	@DisplayName("positive scenario for successfuly updating menu object")
	void testForPositiveUpdateMenu() {
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
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201, "New Menu updated.");
		Mockito.when(commonsService.updateMenuEntities(menu, menu.getMenuId())).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = menuController.commonsReleaseControllerMenuUpdate(menu.getMenuId(), menu);
		
		Mockito.verify(commonsService).updateMenuEntities(menu, menu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
		Assertions.assertEquals(expectedResponse.getBody().getResponseMessage(), response.getBody().getResponseMessage());
	}
	
	@Test
	@DisplayName("Negative scenario for updating menu object")
	void testForNegativeUpdateMenu() {
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
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "SQl error exception");
		Mockito.when(commonsService.updateMenuEntities(menu, menu.getMenuId())).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> response = menuController.commonsReleaseControllerMenuUpdate(menu.getMenuId(), menu);
		
		Mockito.verify(commonsService).updateMenuEntities(menu, menu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), response.getStatusCodeValue());
		Assertions.assertEquals(expectedResponse.getBody().getResponseMessage(), response.getBody().getResponseMessage());
	}

///////////////////////////// testing delete menu //////////////////////////////////	
	
	@Test
	@DisplayName("positive scenario for deleting menu")
	void testForPositiveDeleteMenu() {
		String restaurantName = "Movie Magic";
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201, "success message");
		
		Mockito.when(newMenuService.deleteMenuByRestaurantName(restaurantName)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> actualResponse = menuController.commonsReleaseControllerMenuDelete(restaurantName);
		
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Mockito.verify(newMenuService).deleteMenuByRestaurantName(restaurantName);
	}
	
	@Test
	@DisplayName("Negative scenario for deleting menu")
	void testForNegativeDeleteMenu() {
		String restaurantName = "Movie Magic";
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "failure message");
		
		Mockito.when(newMenuService.deleteMenuByRestaurantName(restaurantName)).thenReturn(expectedResponse);
		
		ResponseEntity<StandardReleaseResponse> actualResponse = menuController.commonsReleaseControllerMenuDelete(restaurantName);
		
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Mockito.verify(newMenuService).deleteMenuByRestaurantName(restaurantName);
	}
	
///////////////////////////// testing getting by methods //////////////////////////////	
	
	@Test
	@DisplayName("positive scenario for get menu by restaurant name")
	void testForPositiveGetMenuForRestaurantName() {
		String restaurantName = "Movie Magic";
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004MM", divisions, "Sethu", dateTime,"Sethu", dateTime);
		
		Mockito.when(newMenuService.findByRestaurantName(restaurantName)).thenReturn(menu);
		
		Menu actualmenu = menuController.commonsReleaseControllerMenuGetByRestaurantName(restaurantName);
		
		Assertions.assertEquals(menu.getMenuId(), actualmenu.getMenuId());
		Mockito.verify(newMenuService).findByRestaurantName(restaurantName);
	}
	
	@Test
	@DisplayName("Negative scenario for get menu by restaurant name")
	void testForNegativeGetMenuForRestaurantName() {
		String restaurantName = "Movie Magic";
		
		Mockito.when(newMenuService.findByRestaurantName(restaurantName)).thenThrow(NotFoundException.class);
		
		Assertions.assertThrows(NotFoundException.class, () -> menuController.commonsReleaseControllerMenuGetByRestaurantName(restaurantName));
	}
	
	@Test
	@DisplayName("positive scenario for get restaurant name from item name")
	void testForPositiveGetRestaurantNameForItemName() {
		String itemName = "pista icecream";
		HashMap<String, Integer> expectedResult = new HashMap<String, Integer>();
		expectedResult.put("CreamStone", 200);
		
		Mockito.when(newMenuService.findByItemName(itemName)).thenReturn(expectedResult);
		
		HashMap<String, Integer> actual = menuController.commonsReleaseControllerMenuGetByItemName(itemName);
		
		Assertions.assertTrue(actual.equals(expectedResult));
	}
	
	@Test
	@DisplayName("Negative scenario for get restaurant name from item name")
	void testForNegativeGetRestaurantNameForItemName() {
		String itemName = "Ice cream";
		
		Mockito.when(newMenuService.findByItemName(itemName)).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> menuController.commonsReleaseControllerMenuGetByItemName(itemName));
	}
	
	@Test
	@DisplayName("positive scenario for get restaurant name from category name")
	void testForPositiveGetRestaurantNameForCategoryName() {
		String categoryName = "Juices";
		HashMap<String, Integer> expecteditem = new HashMap<String, Integer>();
		expecteditem.put("Apple juice", 20);
		expecteditem.put("Orange juice", 25);
		
		HashMap<String, HashMap<String, Integer>> expectedResult = new HashMap<String, HashMap<String, Integer>>();
		expectedResult.put("Juice corner", expecteditem);
		
		Mockito.when(newMenuService.findByCategory(categoryName)).thenReturn(expectedResult);
		
		HashMap<String, HashMap<String, Integer>> actual = menuController.commonsReleaseControllerMenuGetByCategoryName(categoryName);
		
		Assertions.assertTrue(actual.equals(expectedResult));
	}
	
	@Test
	@DisplayName("Negative scenario for get restaurant name from category name")
	void testForNegativeGetRestaurantNameForCategoryName() {
		String categoryName = "Juices";
		
		Mockito.when(newMenuService.findByCategory(categoryName)).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> menuController.commonsReleaseControllerMenuGetByCategoryName(categoryName));
	}
	
	@Test
	@DisplayName("positive scenario for get restaurant name from division name")
	void testForPositiveGetRestaurantNameForDivisionName() {
		String divisionName = "Beverages";
		HashMap<String, Integer> expecteditem = new HashMap<String, Integer>();
		expecteditem.put("Apple juice", 20);
		expecteditem.put("Orange juice", 25);
		
		HashMap<String, Integer> expecteditem2 = new HashMap<String, Integer>();
		expecteditem2.put("Tea", 10);
		expecteditem2.put("Coffee", 10);
		
		HashMap<String, HashMap<String, Integer>> expectedResult = new HashMap<String, HashMap<String, Integer>>();
		expectedResult.put("Juice corner", expecteditem);
		expectedResult.put("Tata tea", expecteditem2);
		
		Mockito.when(newMenuService.findByDivision(divisionName)).thenReturn(expectedResult);
		
		HashMap<String, HashMap<String, Integer>> actual = menuController.commonsReleaseControllerMenuGetByDivisionName(divisionName);
		
		Assertions.assertTrue(actual.equals(expectedResult));
	}
	
	@Test
	@DisplayName("Negative scenario for get restaurant name from division name")
	void testForNegativeGetRestaurantNameForDivisionName() {
		String divisionName = "Beverages";
		
		Mockito.when(newMenuService.findByDivision(divisionName)).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> menuController.commonsReleaseControllerMenuGetByDivisionName(divisionName));
	}
}
