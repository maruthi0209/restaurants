package com.example.restaurants.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.exceptions.GenericException;
import com.example.restaurants.exceptions.NotFoundException;
import com.example.restaurants.repos.InterfaceMenuRepository;
import com.example.restaurants.util.GlobalResponse;
import com.example.restaurants.util.MenuFourthUtil;
import com.example.restaurants.util.MenuThirdUtil;
import com.example.restaurants.util.StandardReleaseResponse;

@Service
public class NewMenuService {

	@Autowired
	private InterfaceMenuRepository interfaceMenuRepository;
	
	@Autowired MenuFourthUtil menuFourthUtil;
	
	public static final String updatedby = "Sethu";
	
	public static final Logger logger = LoggerFactory.getLogger(NewMenuService.class);
	
	@Transactional
	public ResponseEntity<StandardReleaseResponse> handleUserActions(Menu menu) {
		try {
		logger.info("creating a menu item");
		return createMenu(menu);
		} catch (Exception exception) {
			throw new GenericException(exception);
			//return new GlobalResponse().createResponseEntity(501,  exception.getMessage());
		}
}
	@Transactional
	private ResponseEntity<StandardReleaseResponse> createMenu(Menu setmenu) {
		interfaceMenuRepository.insertIntoMenuTable(setmenu.getMenuId(), setmenu.getCreatedBy(), setmenu.getCreatedTime(), setmenu.getRestaurantCode(), setmenu.getUpdatedBy(), setmenu.updatedTime);
		List<Division> menuDivision = setmenu.getDivisions();
		for (Division division : menuDivision) {
			menuDivisionSave(division);
			interfaceMenuRepository.insertIntoMenuDivisionTable(setmenu.getMenuId(), division.getDivisionId());
		}
		return new GlobalResponse().createResponseEntity(201, "New Menu saved.");
	}

	@Transactional
	private void menuDivisionSave(Division division) {
		System.out.println(division);
		interfaceMenuRepository.insertIntoDivisionTable(division.getDivisionId(), division.getCreatedBy(), division.getCreatedTime(), division.getDivisionName(), division.getUpdatedBy(), division.getUpdatedTime());
		List<Category> menuCategories = division.getCategories();
		for (Category category : menuCategories) {
			menuCategorySave(category);				
			interfaceMenuRepository.insertIntoDivisionCategoriesTable(division.getDivisionId(), category.getCategoryId());
		}
	}

	@Transactional
	private void menuCategorySave(Category category) {
		System.out.println(category);
		interfaceMenuRepository.insertIntoCategoryTable(category.getCategoryId(), category.getCreatedBy(), category.getCreatedTime(), category.getCategoryName(), category.updatedBy, category.updatedTime);
		List<MenuItem> menuItems = category.getItems();
		for (MenuItem item : menuItems) {
			menuItemsSave(item);				
			interfaceMenuRepository.insertIntoCategoryMenuItemsTable(category.getCategoryId(), item.getMenuItemId());
		} 
	}

	@Transactional
	private void menuItemsSave(MenuItem item) {
		System.out.println(item);
		interfaceMenuRepository.insertIntoMenuItemTable(item.getMenuItemId(), item.getCreatedBy(), item.getCreatedTime(), item.getMenuItemCost(), item.getMenuItemName(), item.getUpdatedBy(), item.getUpdatedTime());
		}
	
	
//////////////////////// update menu process ///////////////////////////////////////////////////////////////	
	
	@Transactional
	public ResponseEntity<StandardReleaseResponse> updateUserActions(Menu menu, String menuId) {
		try {
			logger.info("updating a menu item");
			return updateMenu(menu, menuId);
		} catch (Exception exception) {
			throw new GenericException(exception);
			//return new GlobalResponse().createResponseEntity(501,  exception.getMessage());
		}
		
	}
	
	@Transactional
	private ResponseEntity<StandardReleaseResponse> updateMenu(Menu menu, String menuId) {
		System.out.println(menu);
		Optional<Menu> givenmenu = interfaceMenuRepository.getFullMenuUsingJoins(menuId); 
		if (givenmenu.isPresent()) {
			Menu existingmenu = givenmenu.get();
			existingmenu.setUpdatedBy(updatedby);
			existingmenu.setUpdatedTime(LocalDateTime.now());
			System.out.println(existingmenu);
			List<Division> oldDivisions = existingmenu.getDivisions();
			System.out.println(oldDivisions);
			List<String> oldDivisionName = new ArrayList<String>();
			for (Division id : oldDivisions) {
				oldDivisionName.add(id.getDivisionName());
			}
			System.out.println(oldDivisionName);
			checkDivisionUpdation(menu, menuId, oldDivisions, oldDivisionName);	
			return new GlobalResponse().createResponseEntity(201, "New Menu updated.");
		} else {
			throw new NotFoundException("Menu not found");
			//return new GlobalResponse().createResponseEntity(404, "Menu not found.");
			}
	}
	
	@Transactional
	private void checkDivisionUpdation(Menu newMenu, String menuId, List<Division> oldDivisions, List<String> oldDivisionName) {
		System.out.println(oldDivisionName);
		List<Division> newDivisions = newMenu.getDivisions();
		for (Division division : newDivisions) {
			System.out.println(division);
			if (!oldDivisionName.contains(division.getDivisionName())) {
				menuFourthUtil.setDivsionDetails(division);
				menuDivisionSave(division);
				interfaceMenuRepository.insertIntoMenuDivisionTable(menuId, division.getDivisionId());
			} else {
				checkCategoryUpdations(division, oldDivisions, oldDivisionName, menuId);
				division.setDivisionId(oldDivisions.get(oldDivisionName.indexOf(division.getDivisionName())).getDivisionId());
				division.setUpdatedBy(updatedby);
				division.setUpdatedTime(LocalDateTime.now());
				interfaceMenuRepository.updateDivisionTable(division.getDivisionId(), division.getUpdatedBy(), division.getUpdatedTime());
			}
		}
		List<String> newDivisionNames = new ArrayList<String>();
		for (Division division : newDivisions) {
			newDivisionNames.add(division.getDivisionName());
		}
		checkDivisionDeletions(newDivisions, newDivisionNames, oldDivisions, oldDivisionName, menuId);
	}
	
	@Transactional
	private void checkDivisionDeletions(List<Division> newDivisions, List<String> newDivisionNames, List<Division> oldDivisions,
			List<String> oldDivisionName, String menuId) {
		System.out.println(oldDivisionName);
		for (String name : oldDivisionName) {
			if (!newDivisionNames.contains(name)) {
				Division divisionId = oldDivisions.get(oldDivisionName.indexOf(name));
				System.out.println(divisionId);
				List<Category> categories = divisionId.getCategories();
				for (Category category : categories) {
					deleteFromMenuDivisionCategories(category.getCategoryId());
					List<MenuItem> items = category.getItems();
					for (MenuItem item : items) {
						deleteFromMenuCategoryItemTable(item.getMenuItemId());
						deleteFromMenuItemTable(item.getMenuItemId());
					}
					deleteFromCategoryTable(category.getCategoryId());
				}
				deleteFromMenuMenuDivision(divisionId.getDivisionId(), menuId);
				deleteFromDivisionTable(divisionId.getDivisionId());
			}
		}
	}
	
	@Transactional
	private void checkCategoryUpdations(Division division, List<Division> oldDivisions, List<String> oldDivisionName, String menuId) {
		List<Category> newCategories = division.getCategories();
		System.out.println(newCategories);
		Division divisionId = oldDivisions.get(oldDivisionName.indexOf(division.getDivisionName()));
		System.out.println(divisionId);
		List<Category> oldCategories = divisionId.getCategories();
		List<String> oldCategoryNames = new ArrayList<String>();
		for (Category id : oldCategories) {
			oldCategoryNames.add(id.getCategoryName());
		}
		for (Category category : newCategories) {
			if (!oldCategoryNames.contains(category.getCategoryName())) {
				menuFourthUtil.setCategoryDetails(category, divisionId.getDivisionId());
				menuCategorySave(category);
				interfaceMenuRepository.insertIntoDivisionCategoriesTable(divisionId.getDivisionId(), category.getCategoryId());
			} else {
				checkItemUpdations(category, oldCategories, oldCategoryNames);
				category.setUpdatedBy(updatedby);
				category.setUpdatedTime(LocalDateTime.now());
				interfaceMenuRepository.updateCategoryDetails(oldCategories.get(oldCategoryNames.indexOf(category.getCategoryName())).getCategoryId(), category.getUpdatedBy(), category.getUpdatedTime());
			}
		}
		checkCategoryDeletions(newCategories, oldCategories, oldCategoryNames);
	}

	@Transactional
	private void checkCategoryDeletions(List<Category> newCategories, List<Category> oldCategories,
			List<String> oldCategoryNames) {
		List<String> newCategoryNames = new ArrayList<String>();
		for (Category category : newCategories) {
			newCategoryNames.add(category.getCategoryName());
		}
		for (String name : oldCategoryNames) {
			if (!newCategoryNames.contains(name)) {
				Category categoryId = oldCategories.get(oldCategoryNames.indexOf(name));
				List<MenuItem> oldItems = categoryId.getItems();
				for (MenuItem item : oldItems) {
					deleteFromMenuCategoryItemTable(item.getMenuItemId());
					deleteFromMenuItemTable(item.getMenuItemId());
					deleteFromMenuDivisionCategories(categoryId.getCategoryId());
					deleteFromCategoryTable(categoryId.getCategoryId());
				}
			}
		}
	}
	
	@Transactional
	private void checkItemUpdations(Category category, List<Category> oldCategories, List<String> oldCategoryNames) {
		Category categoryId = oldCategories.get(oldCategoryNames.indexOf(category.getCategoryName()));
		System.out.println(categoryId);
		List<MenuItem> oldItems = categoryId.getItems();
		System.out.println(oldItems);
		List<String> oldItemNames = new ArrayList<String>();
		for (MenuItem id : oldItems) {
			oldItemNames.add(id.getMenuItemName());
		}
		System.out.println(oldItemNames);
		List<MenuItem> newItems = category.getItems();
		for (MenuItem item : newItems) {
			if (!oldItemNames.contains(item.getMenuItemName())) {
				menuFourthUtil.setItemDetails(item);
				menuItemsSave(item);
				interfaceMenuRepository.insertIntoCategoryMenuItemsTable(categoryId.getCategoryId(), item.getMenuItemId());
			} else {
				updateTheItem(item, oldItems, oldItemNames);
			}
		}
		checkItemDeletions(newItems, oldItems, oldItemNames);
	}
	
	private void updateTheItem(MenuItem item, List<MenuItem> oldItems, List<String> oldItemNames) {
		System.out.println(item);
		MenuItem itemId = oldItems.get(oldItemNames.indexOf(item.getMenuItemName()));
		item.setMenuItemId(itemId.getMenuItemId());
		item.setUpdatedBy(updatedby);
		item.setUpdatedTime(LocalDateTime.now());	
		interfaceMenuRepository.updateMenuItem(item.getMenuItemId(), item.getMenuItemCost(), item.getUpdatedBy(), item.getUpdatedTime());
	}
	
	@Transactional
	private void checkItemDeletions(List<MenuItem> newItems, List<MenuItem> oldItems, List<String> oldItemNames) {
		List<String> newItemNames = new ArrayList<String>();
		for (MenuItem item : newItems) {
			newItemNames.add(item.getMenuItemName());
		}
		for (String name : oldItemNames) {
			if (!newItemNames.contains(name)) {
				MenuItem id = oldItems.get(oldItemNames.indexOf(name));
				deleteFromMenuCategoryItemTable(id.getMenuItemId());
				deleteFromMenuItemTable(id.getMenuItemId());
			}
		}
	}
	
///////////////////// delete from tables /////////////////////////////////////////////	
	
	@Transactional
	private int deleteFromMenuMenuDivision(String divisionId, String menuId) {
		interfaceMenuRepository.deleteFromMenuDivisionTable(menuId, divisionId);
		return 1;	
	}
	
	@Transactional
	private int deleteFromMenuDivisionCategories(String menuCategoryId) {
		interfaceMenuRepository.deleteFromDivisionCategories(menuCategoryId);
		return 1;
	}
	
	@Transactional
	private int deleteFromMenuCategoryItemTable(String itemId) {
		interfaceMenuRepository.deleteFromCategoryItemTable(itemId);
		return 1;	
	}
	
	@Transactional
	private int deleteFromMenuItemTable(String itemId) {
		interfaceMenuRepository.deleteFromMenuItemTable(itemId);
		return 1;
	}
	
	@Transactional
	private int deleteFromCategoryTable(String categoryId) {
		interfaceMenuRepository.deleteFromCategoryTable(categoryId);
		return 1;
	}
	
	@Transactional
	private int deleteFromDivisionTable(String divisionId) {
		interfaceMenuRepository.deleteFromDivisionTable(divisionId);
		return 1;
	}
	
	@Transactional
	private int deleteFromMenuTable(String menuId) {
		interfaceMenuRepository.deleteFromMenuTable(menuId);
		return 1;
	}
/////////////////////// find by id, restaurant name, item name, category, division  /////////////////////////////////////////////
	
	public ResponseEntity<StandardReleaseResponse> findById(String menuId) {
		Optional<Menu> menu = interfaceMenuRepository.getFullMenuUsingJoins(menuId);
		if (menu.isPresent()) {
			return new GlobalResponse().createResponseEntity(201, menu.get().toString());
		} else {
			throw new NotFoundException("Menu not found");
			//return new GlobalResponse().createResponseEntity(404, "Menu not found");
		}
	}
	
	public Menu findByRestaurantName(String restaurantName) {
		Optional<Menu> menu = interfaceMenuRepository.getMenuUsingJoins(restaurantName);
		if (menu.isPresent()) {
			return menu.get();
		} else {
			throw new NotFoundException("Menu not found");
		}
	}
	
	public HashMap<String, Integer> findByItemName(String menuItemName) {
		try {
			HashMap<String, Integer> resultForItemName = new HashMap<String, Integer>();
			List<String> restaurantNames = interfaceMenuRepository.getRestaurantNameForMenuItem(menuItemName);
			for (String name : restaurantNames) {
				resultForItemName.put(name, interfaceMenuRepository.getItemCostForMenuItem(menuItemName, name));
			}
			System.out.println(resultForItemName);
			return resultForItemName;
		} catch (Exception exception) {
			throw new GenericException(exception);
		}
	}
	
	public HashMap<String, HashMap<String, Integer>> findByDivision(String divisionName) {
		try {
			HashMap<String, HashMap<String, Integer>> resultForDivisionName = new HashMap<String, HashMap<String, Integer>>();
			List<String> restaurantNames = interfaceMenuRepository.getRestaurantNameForMenuDivision(divisionName);
			System.out.println(restaurantNames);
			for (String name : restaurantNames) {
				List<String> menuitems = interfaceMenuRepository.getItemListForMenuDivision(divisionName, name);
				HashMap<String, Integer> mapOfItemNameAndCost = new HashMap<String, Integer>();
				for (String item : menuitems) {
					mapOfItemNameAndCost.put(item, interfaceMenuRepository.getItemCostForMenuItem(item, name));
				}
				System.out.println(menuitems);
				resultForDivisionName.put(name, mapOfItemNameAndCost);
			}
			return resultForDivisionName;
		} catch (Exception exception) {
			throw new GenericException(exception);
		}
	}
	
	public HashMap<String, HashMap<String, Integer>> findByCategory(String categoryName) {
		try {
			HashMap<String, HashMap<String, Integer>> resultForCategoryName = new HashMap<String, HashMap<String, Integer>>();
			List<String> restaurantNames = interfaceMenuRepository.getRestaurantNameForMenuCategory(categoryName);
			System.out.println(restaurantNames);
			for (String name : restaurantNames) {
				List<String> menuitems = interfaceMenuRepository.getItemListForMenuCategory(categoryName, name);
				HashMap<String, Integer> mapOfItemNameAndCost = new HashMap<String, Integer>();
				for (String item : menuitems) {
					mapOfItemNameAndCost.put(item, interfaceMenuRepository.getItemCostForMenuItem(item, name));
				}
				System.out.println(menuitems);
				resultForCategoryName.put(name, mapOfItemNameAndCost);
			}
			return resultForCategoryName;
		} catch (Exception exception) {
			throw new GenericException(exception);
		}
	}
	
/////////////////////////////// delete menu by restaurant name //////////////////////////////	
	
	@Transactional
	public ResponseEntity<StandardReleaseResponse> deleteMenuByRestaurantName(String restaurantName) {
		try {
			Menu existingMenu = findByRestaurantName(restaurantName);
			System.out.println(existingMenu);
			for (Division division : existingMenu.getDivisions()) {
				List<Category> categories = division.getCategories();
				for (Category category : categories) {
					List<MenuItem> items = category.getItems();
					for (MenuItem item : items) {
						deleteFromMenuCategoryItemTable(item.getMenuItemId());
						deleteFromMenuItemTable(item.getMenuItemId());
					}
					deleteFromMenuDivisionCategories(category.getCategoryId());
					deleteFromCategoryTable(category.getCategoryId());
				}
				deleteFromMenuMenuDivision(division.getDivisionId(), existingMenu.getMenuId());
				deleteFromDivisionTable(division.getDivisionId());
			}
			deleteFromMenuTable(existingMenu.getMenuId());
			return new GlobalResponse().createResponseEntity(201, "menu successfully deleted");
		} catch (Exception exception) {
			throw new GenericException(exception);
		}
	}
}