package com.example.restaurants.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.NotFoundException;
import com.example.restaurants.repos.InterfaceMenuRepository;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuFourthUtil;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.StandardReleaseResponse;

import net.bytebuddy.description.type.TypeDescription.Generic;

@SpringBootTest
public class TestNewMenuService {

	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime dateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	@InjectMocks
	private NewMenuService newMenuService;
	
	@Mock
	private MenuFourthUtil menuFourthUtil;
	
	@Mock
	private InterfaceMenuRepository interfaceMenuRepository;
	
	@BeforeEach
	public void setMockSetup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("when a new menu is given save it to repository")
	void checkNewMenuSaving() {
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
		
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.handleUserActions(menu);
		
		Assertions.assertEquals(201, actualResponse.getStatusCodeValue());	
		
		Mockito.verify(interfaceMenuRepository).insertIntoMenuTable(menu.getMenuId(), menu.getCreatedBy(), menu.getCreatedTime(), menu.getRestaurantCode(), menu.getUpdatedBy(), menu.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoMenuDivisionTable(menu.getMenuId(), indian.getDivisionId());
		Mockito.verify(interfaceMenuRepository).insertIntoDivisionTable(indian.getDivisionId(), indian.getCreatedBy(), indian.getCreatedTime(), indian.getDivisionName(), indian.getUpdatedBy(), indian.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoDivisionCategoriesTable(indian.getDivisionId(), breakfast.getCategoryId());
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryTable(breakfast.getCategoryId(), breakfast.getCreatedBy(), breakfast.getCreatedTime(), breakfast.getCategoryName(), breakfast.getUpdatedBy(), breakfast.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryMenuItemsTable(breakfast.getCategoryId(), idly.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).insertIntoMenuItemTable(idly.getMenuItemId(), idly.getCreatedBy(), idly.getCreatedTime(), idly.getMenuItemCost(), idly.getMenuItemName(), idly.getUpdatedBy(), idly.getUpdatedTime());
	}

	@Test
	@DisplayName("when a exception occurs in create menu method")
	void checkExceptionCatchingForCreateMenu() {
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", null, "Sethu", dateTime,"Sethu", dateTime);
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501, "An exception occured");
		Mockito.when(interfaceMenuRepository.insertIntoMenuTable(menu.getMenuId(), menu.getCreatedBy(), menu.getCreatedTime(), menu.getRestaurantCode(), menu.getUpdatedBy(), menu.getUpdatedTime())).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> newMenuService.handleUserActions(menu));
		//ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.handleUserActions(menu);
		//Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
	}

	
//////////////////////////////////// testing update menu /////////////////////////////////////	

	
	@Test
	@DisplayName("If user accidentally sends menu id which doesnt exist")
	void checkNonExistingMenuId () {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(404, "Menu not found.");
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.empty();
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
		
		Assertions.assertThrows(GenericException.class, () -> newMenuService.updateUserActions(menu, menu.getMenuId()));
		//ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(menu, menu.getMenuId());
		//Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		//Assertions.assertEquals(expectedResponse.getBody().getResponseMessage(), actualResponse.getBody().getResponseMessage());
		
	} 
			
	@Test
	@DisplayName("If user sends menu for updating with new division")
	void checkUpdationForExistingMenuIdWithNewDivision() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		MenuItem vegnoodles = new MenuItem("476cfb7b-f55b-4562-b9c8-4b39e6a58b09",  "veg noodles", 50, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		List<MenuItem> itemlist2 = new ArrayList<MenuItem>();
		itemlist2.add(vegnoodles);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		Category noodles = new Category("ead1b449-625b-45d2-b521-db18a32dd17b", "noodles", itemlist2, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		List<Category> categories2 = new ArrayList<Category>();
		categories2.add(noodles);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		Division chinese = new Division("af013a45-a627-4791-9c30-dce750364d94", "chinese", categories2, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		List<Division> divisions2 = new ArrayList<Division>();
		divisions.add(indian);
		divisions2.add(indian);
		divisions2.add(chinese);
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Menu newmenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions2, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);		 
		
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(newmenu, newmenu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		
		Mockito.verify(menuFourthUtil).setDivsionDetails(chinese);
		Mockito.verify(interfaceMenuRepository).insertIntoDivisionTable(chinese.getDivisionId(), chinese.getCreatedBy(), chinese.getCreatedTime(), chinese.getDivisionName(), chinese.getUpdatedBy(), chinese.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoDivisionCategoriesTable(chinese.getDivisionId(), noodles.getCategoryId());
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryTable(noodles.getCategoryId(), noodles.getCreatedBy(), noodles.getCreatedTime(), noodles.getCategoryName(), noodles.getUpdatedBy(), noodles.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryMenuItemsTable(noodles.getCategoryId(), vegnoodles.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).insertIntoMenuItemTable(vegnoodles.getMenuItemId(), vegnoodles.getCreatedBy(), vegnoodles.getCreatedTime(), vegnoodles.getMenuItemCost(), vegnoodles.getMenuItemName(), vegnoodles.getUpdatedBy(), vegnoodles.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoMenuDivisionTable(menu.getMenuId(), chinese.getDivisionId());
	}
		
	@Test
	@DisplayName("If user sends menu for updating with new category")
	void checkUpdationForExistingMenuIdWithNewCategory() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		MenuItem vegmeals = new MenuItem("476cfb7b-f55b-4562-b9c8-4b39e6a58b09",  "veg meals", 100, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		List<MenuItem> itemlist2 = new ArrayList<MenuItem>();
		itemlist2.add(vegmeals);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		Category meals = new Category("ead1b449-625b-45d2-b521-db18a32dd17b", "meals", itemlist2, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		List<Category> categories2 = new ArrayList<Category>();
		categories2.add(breakfast);
		categories2.add(meals);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		Division indian2 = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories2, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		List<Division> divisions2 = new ArrayList<Division>();
		divisions2.add(indian2);
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Menu newmenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions2, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
				
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(newmenu, newmenu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		
		Mockito.verify(menuFourthUtil).setCategoryDetails(meals, indian.getDivisionId());
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryTable(meals.getCategoryId(), meals.getCreatedBy(), meals.getCreatedTime(), meals.getCategoryName(), meals.getUpdatedBy(), meals.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryMenuItemsTable(meals.getCategoryId(), vegmeals.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).insertIntoMenuItemTable(vegmeals.getMenuItemId(), vegmeals.getCreatedBy(), vegmeals.getCreatedTime(), vegmeals.getMenuItemCost(), vegmeals.getMenuItemName(), vegmeals.getUpdatedBy(), vegmeals.getUpdatedTime());
		Mockito.verify(interfaceMenuRepository).insertIntoDivisionCategoriesTable(indian.getDivisionId(), meals.getCategoryId());
	}
	
	@Test
	@DisplayName("If user sends menu for updating with new item")
	void checkUpdationForExistingMenuIdWithNewItem() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		MenuItem dosa = new MenuItem("476cfb7b-f55b-4562-b9c8-4b39e6a58b09",  "dosa", 100, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		List<MenuItem> itemlist2 = new ArrayList<MenuItem>();
		itemlist2.add(idly);
		itemlist2.add(dosa);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		Category breakfast2 = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist2, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		List<Category> categories2 = new ArrayList<Category>();
		categories2.add(breakfast2);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Division indian2 = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories2, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions2 = new ArrayList<Division>();
		divisions2.add(indian2);
		
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Menu newmenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions2, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
				
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(newmenu, newmenu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		
		Mockito.verify(menuFourthUtil).setItemDetails(dosa);
		Mockito.verify(interfaceMenuRepository).insertIntoCategoryMenuItemsTable(breakfast.getCategoryId(), dosa.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).insertIntoMenuItemTable(dosa.getMenuItemId(), dosa.getCreatedBy(), dosa.getCreatedTime(), dosa.getMenuItemCost(), dosa.getMenuItemName(), dosa.getUpdatedBy(), dosa.getUpdatedTime());
	}
	
	@Test
	@DisplayName("if user updating a single item cost only")
	void checkItemCostOnlyForExistingMenuId() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 50, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
				
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(menu, menu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Mockito.verify(interfaceMenuRepository).updateMenuItem(idly.getMenuItemId(), idly.getMenuItemCost(), idly.getUpdatedBy(), idly.getUpdatedTime());
	}
	
	@Test
	@DisplayName("if user updating with less items")
	void checkWhenMenuItemisless() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		MenuItem dosa = new MenuItem("476cfb7b-f55b-4562-b9c8-4b39e6a58b09",  "dosa", 100, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		itemlist.add(dosa);
		List<MenuItem> itemlist2 = new ArrayList<MenuItem>();
		itemlist2.add(idly);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		Category breakfast2 = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist2, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		List<Category> categories2 = new ArrayList<Category>();
		categories2.add(breakfast2);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		Division indian2 = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories2, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		List<Division> divisions2 = new ArrayList<Division>();
		divisions2.add(indian2);
		
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Menu newmenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions2, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
				
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(newmenu, newmenu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryItemTable(dosa.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuItemTable(dosa.getMenuItemId());
	}
	
	@Test
	@DisplayName("if user updating with less categories")
	void checkWhenCategoriesisless() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		MenuItem vegmeals = new MenuItem("476cfb7b-f55b-4562-b9c8-4b39e6a58b09",  "veg meals", 100, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		List<MenuItem> itemlist2 = new ArrayList<MenuItem>();
		itemlist2.add(vegmeals);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		Category meals = new Category("af013a45-a627-4791-9c30-dce750364d94", "meals", itemlist2, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		categories.add(meals);
		List<Category> categories2 = new ArrayList<Category>();
		categories2.add(breakfast);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		Division indian2 = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories2, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		List<Division> divisions2 = new ArrayList<Division>();
		divisions2.add(indian2);
		
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Menu newmenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions2, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
		
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(newmenu, newmenu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Mockito.verify(interfaceMenuRepository).deleteFromDivisionCategories(meals.getCategoryId());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryTable(meals.getCategoryId());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryItemTable(vegmeals.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuItemTable(vegmeals.getMenuItemId());
	}
	
	@Test
	@DisplayName("if user updating with less divisions")
	void checkWhenDivisionisless() {
		MenuItem idly = new MenuItem("2db3b87c-a117-4d2c-970c-2b2685854ed1",  "idly", 30, "Sethu", dateTime,"Sethu", dateTime);
		MenuItem vegnoodles = new MenuItem("476cfb7b-f55b-4562-b9c8-4b39e6a58b09",  "veg noodles", 100, "Sethu", dateTime,"Sethu", dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		List<MenuItem> itemlist2 = new ArrayList<MenuItem>();
		itemlist2.add(vegnoodles);
		Category breakfast = new Category("5e0611e1-b797-49f7-8bfa-ff16b43490d3", "breakfast", itemlist, "Sethu", dateTime,"Sethu", dateTime);
		Category noodles = new Category("af013a45-a627-4791-9c30-dce750364d94", "noodles", itemlist2, "Sethu", dateTime,"Sethu", dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		categories.add(noodles);
		List<Category> categories2 = new ArrayList<Category>();
		categories2.add(noodles);
		Division indian = new Division("7595b53c-d604-43c7-aea2-afa6d436856c", "indian", categories, "Sethu", dateTime,"Sethu", dateTime);
		Division chinese = new Division("be4c314e-aaf9-44d8-8efc-1364bb296527", "chinese", categories2, "Sethu", dateTime,"Sethu", dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		divisions.add(chinese);
		List<Division> divisions2 = new ArrayList<Division>();
		divisions2.add(indian);
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions, "Sethu", dateTime,"Sethu", dateTime);
		Menu newmenu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", divisions2, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "New Menu updated.");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
				
		ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(newmenu, newmenu.getMenuId());
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuDivisionTable(menu.getMenuId(), chinese.getDivisionId());
		Mockito.verify(interfaceMenuRepository).deleteFromDivisionTable(chinese.getDivisionId());
		Mockito.verify(interfaceMenuRepository).deleteFromDivisionCategories(noodles.getCategoryId());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryTable(noodles.getCategoryId());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryItemTable(vegnoodles.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuItemTable(vegnoodles.getMenuItemId());
	}
	
	@Test
	@DisplayName("when a exception occurs in update menu method")
	void checkExceptionCatchingForUpdateMenu() {
		Menu menu = new Menu("8c8c07e7-3736-4870-aac8-53f947e4cf3a", "FD004NH", null, "Sethu", dateTime,"Sethu", dateTime);
		Optional<Menu> value = Optional.of(menu);
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(501,  "An exception occured");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenThrow(GenericException.class);
		//Mockito.when(interfaceMenuRepository.getDivisionNamesForGivenIds(menu.getMenuId())).thenThrow(GenericException.class);
		
		//ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.updateUserActions(menu, menu.getMenuId());
		Assertions.assertThrows(GenericException.class, () -> newMenuService.updateUserActions(menu, menu.getMenuId()));
		//Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
	}
	
/////////////////////// testing get menu method //////////////////////////////////
	
	@Test
	@DisplayName(" positive test case for get menu method")
	void checkGetMenuMethod() {
		MenuItem idly = new MenuItem();
		idly.setMenuItemId("2db3b87c-a117-4d2c-970c-2b2685854ed1");
		idly.setMenuItemName("idly");
		idly.setMenuItemCost(30);
		idly.setCreatedBy("Sethu");
		idly.setCreatedTime(dateTime);
		idly.setUpdatedBy("Sethu");
		idly.setUpdatedTime(dateTime);
		List<MenuItem> itemlist = new ArrayList<MenuItem>();
		itemlist.add(idly);
		Category breakfast = new Category();
		breakfast.setCategoryId("5e0611e1-b797-49f7-8bfa-ff16b43490d3");
		breakfast.setCategoryName("breakfast");
		breakfast.setItems(itemlist);
		breakfast.setCreatedBy("Sethu");
		breakfast.setCreatedTime(dateTime);
		breakfast.setUpdatedBy("Sethu");
		breakfast.setUpdatedTime(dateTime);
		List<Category> categories = new ArrayList<Category>();
		categories.add(breakfast);
		Division indian = new Division();
		indian.setDivisionId("7595b53c-d604-43c7-aea2-afa6d436856c");
		indian.setDivisionName("indian");
		indian.setCategories(categories);
		indian.setCreatedBy("Sethu");
		indian.setCreatedTime(dateTime);
		indian.setUpdatedBy("Sethu");
		indian.setUpdatedTime(dateTime);
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(indian);
		Menu menu = new Menu();
		menu.setMenuId("8c8c07e7-3736-4870-aac8-53f947e4cf3a");
		menu.setRestaurantCode("FD004NH");
		menu.setDivisions(divisions);
		menu.setCreatedBy("Sethu");
		menu.setCreatedTime(dateTime);
		menu.setUpdatedBy("Sethu");
		menu.setUpdatedTime(dateTime);
		Optional<Menu> value = Optional.of(menu);
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins(menu.getMenuId())).thenReturn(value);
		
		ResponseEntity<StandardReleaseResponse> response = newMenuService.findById(menu.getMenuId());
		Assertions.assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	@DisplayName(" negative test case for get menu method")
	void checkGetMenuMethodNegative() {
		Optional<Menu> menuElement = Optional.empty();
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(404,  "Menu not found");
		Mockito.when(interfaceMenuRepository.getFullMenuUsingJoins("8c8c07e7-3736-4870-aac8-53f947e4cf3a")).thenReturn(menuElement);
		Assertions.assertThrows(NotFoundException.class, () -> newMenuService.findById("8c8c07e7-3736-4870-aac8-53f947e4cf3a"));
		//ResponseEntity<StandardReleaseResponse> actualResponse = newMenuService.findById("8c8c07e7-3736-4870-aac8-53f947e4cf3a");
		//Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actualResponse.getStatusCodeValue());
	}

///////////////////////////////// testing delete menu method ////////////////////////
	
	@Test
	@DisplayName("positive scenario for delete restaurant based on name")
	void checkDeleteMenuPositiveScenario() {
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
		Optional<Menu> menuElement = Optional.of(menu);
		ResponseEntity<StandardReleaseResponse> expectedResponse = new GlobalResponse().createResponseEntity(201,  "success message");
		
		Mockito.when(interfaceMenuRepository.getMenuUsingJoins(restaurantName)).thenReturn(menuElement);
		
		ResponseEntity<StandardReleaseResponse> actual = newMenuService.deleteMenuByRestaurantName(restaurantName);
		Assertions.assertEquals(expectedResponse.getStatusCodeValue(), actual.getStatusCodeValue());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuTable(menu.getMenuId());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuDivisionTable(menu.getMenuId(), indian.getDivisionId());
		Mockito.verify(interfaceMenuRepository).deleteFromDivisionTable(indian.getDivisionId());
		Mockito.verify(interfaceMenuRepository).deleteFromDivisionCategories(breakfast.getCategoryId());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryTable(breakfast.getCategoryId());
		Mockito.verify(interfaceMenuRepository).deleteFromCategoryItemTable(idly.getMenuItemId());
		Mockito.verify(interfaceMenuRepository).deleteFromMenuItemTable(idly.getMenuItemId());
	}
	
	@Test
	@DisplayName("negtive scenario for delete restaurant based on name")
	void checkDeleteMenuNegativeScenario() {
		String restaurantName = "Movie Magic";
		Optional<Menu> menuElement = Optional.empty();
		
		Mockito.when(interfaceMenuRepository.getMenuUsingJoins(restaurantName)).thenReturn(menuElement);
		
		Assertions.assertThrows(GenericException.class, () -> newMenuService.deleteMenuByRestaurantName(restaurantName));
	}
	
/////////////////////////////  testing get restaurant names and item names //////////////////////////////////
	
	@Test
	@DisplayName("postivie scenario for get by item name")
	void checkGetByItemNamePositive() {
		String itemName = "veg loaded pizza";
		List<String> restaurantName = new ArrayList<String>();
		restaurantName.add("Domino's Pizza");
		restaurantName.add("Pizza Hut");
		
		HashMap<String, Integer> expectedResult = new HashMap<String, Integer>();
		expectedResult.put("Domino's Pizza", 150);
		expectedResult.put("Pizza Hut", 199);
		
		Mockito.when(interfaceMenuRepository.getRestaurantNameForMenuItem(itemName)).thenReturn(restaurantName);
		Mockito.when(interfaceMenuRepository.getItemCostForMenuItem(itemName, restaurantName.get(0))).thenReturn(150);
		Mockito.when(interfaceMenuRepository.getItemCostForMenuItem(itemName, restaurantName.get(1))).thenReturn(199);
		
		HashMap<String, Integer> actual = newMenuService.findByItemName(itemName);
		
		Assertions.assertTrue(actual.equals(expectedResult));		
	}
	
	@Test
	@DisplayName("negative scenario for get by item name")
	void checkGetByItemNameNegative() {
		String itemName = "veg loaded pizza";
		
		Mockito.when(interfaceMenuRepository.getRestaurantNameForMenuItem(itemName)).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> newMenuService.findByItemName(itemName));
	}
	
	@Test
	@DisplayName("postivie scenario for get by category name")
	void checkGetByCategoryNamePositive() {
		String categoryName = "Pizzas";
		List<String> restaurantName = new ArrayList<String>();
		restaurantName.add("Domino's Pizza");
		restaurantName.add("Pizza Hut");
		
		HashMap<String, Integer> expectedResult = new HashMap<String, Integer>();
		expectedResult.put("veg loaded Pizza", 140);
		
		List<String> items1 = new ArrayList<String>();
		items1.add("veg loaded Pizza");
		
		HashMap<String, Integer> expectedResult2 = new HashMap<String, Integer>();
		expectedResult2.put("veg pepperoni Pizza", 156);
		
		List<String> items2 = new ArrayList<String>();
		items2.add("veg pepperoni Pizza");
		
		HashMap<String, HashMap<String, Integer>> finalResult = new HashMap<String, HashMap<String,Integer>>();
		finalResult.put(restaurantName.get(0), expectedResult);
		finalResult.put(restaurantName.get(1), expectedResult2);
		
		Mockito.when(interfaceMenuRepository.getRestaurantNameForMenuCategory(categoryName)).thenReturn(restaurantName);
		Mockito.when(interfaceMenuRepository.getItemListForMenuCategory(categoryName, restaurantName.get(0))).thenReturn(items1);
		Mockito.when(interfaceMenuRepository.getItemListForMenuCategory(categoryName, restaurantName.get(1))).thenReturn(items2);
		Mockito.when(interfaceMenuRepository.getItemCostForMenuItem("veg loaded Pizza", restaurantName.get(0))).thenReturn(140);
		Mockito.when(interfaceMenuRepository.getItemCostForMenuItem("veg pepperoni Pizza", restaurantName.get(1))).thenReturn(156);
		
		HashMap<String, HashMap<String, Integer>> actual = newMenuService.findByCategory(categoryName);
		
		Assertions.assertTrue(actual.equals(finalResult));		
	}
	
	@Test
	@DisplayName("negative scenario for get by item name")
	void checkGetByCategoryNameNegative() {
		String categoryName = "Pizzas";
		
		Mockito.when(interfaceMenuRepository.getRestaurantNameForMenuCategory(categoryName)).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> newMenuService.findByCategory(categoryName));
	}
	
	@Test
	@DisplayName("postivie scenario for get by division name")
	void checkGetByDivisionNamePositive() {
		String divisionName = "Indian";
		List<String> restaurantName = new ArrayList<String>();
		restaurantName.add("Ananda Bhavan");
		restaurantName.add("Udipi Restaurant");
		
		HashMap<String, Integer> expectedResult = new HashMap<String, Integer>();
		expectedResult.put("veg spl meals", 140);
		
		List<String> items1 = new ArrayList<String>();
		items1.add("veg spl meals");
		
		HashMap<String, Integer> expectedResult2 = new HashMap<String, Integer>();
		expectedResult2.put("veg plate meals", 156);
		
		List<String> items2 = new ArrayList<String>();
		items2.add("veg plate meals");
		
		HashMap<String, HashMap<String, Integer>> finalResult = new HashMap<String, HashMap<String,Integer>>();
		finalResult.put(restaurantName.get(0), expectedResult);
		finalResult.put(restaurantName.get(1), expectedResult2);
		
		Mockito.when(interfaceMenuRepository.getRestaurantNameForMenuDivision(divisionName)).thenReturn(restaurantName);
		Mockito.when(interfaceMenuRepository.getItemListForMenuDivision(divisionName, restaurantName.get(0))).thenReturn(items1);
		Mockito.when(interfaceMenuRepository.getItemListForMenuDivision(divisionName, restaurantName.get(1))).thenReturn(items2);
		Mockito.when(interfaceMenuRepository.getItemCostForMenuItem("veg spl meals", restaurantName.get(0))).thenReturn(140);
		Mockito.when(interfaceMenuRepository.getItemCostForMenuItem("veg plate meals", restaurantName.get(1))).thenReturn(156);
		
		HashMap<String, HashMap<String, Integer>> actual = newMenuService.findByDivision(divisionName);
		
		Assertions.assertTrue(actual.equals(finalResult));		
	}
	
	@Test
	@DisplayName("negative scenario for get by item name")
	void checkGetByDivisionNameNegative() {
		String divisionName = "Indian";
		
		Mockito.when(interfaceMenuRepository.getRestaurantNameForMenuDivision(divisionName)).thenThrow(GenericException.class);
		
		Assertions.assertThrows(GenericException.class, () -> newMenuService.findByDivision(divisionName));
	}
}
