package com.example.restaurants.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.exceptions.ValidationException;

@Component
public class MenuFourthUtil {

	public static final String createdby = "Sethu";
	
	public static final String updatedby = "";
	
	String stringDate = "1900-01-01 00:00:00";
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime updatedDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
	
	public Menu setNewMenuDetails(Menu menu) {
		System.out.println(menu);
		List<String> menuviolations = new ArrayList<String>();
		if (menu.getMenuId()==null || menu.getMenuId().isBlank() || menu.getMenuId().isEmpty()) {
			menu.setMenuId(UUID.randomUUID().toString());
			menu.setCreatedBy(createdby);
			menu.setCreatedTime(LocalDateTime.now());
			menu.setUpdatedBy(updatedby);
			menu.setUpdatedTime(updatedDateTime);
		}
		for (Division division : menu.getDivisions()) {	
			List<String> divisionviolations = new ArrayList<String>();
			divisionviolations = setDivsionDetails(division);
			if (!divisionviolations.isEmpty()) {
				for (String divisionviolation : divisionviolations) {
					menuviolations.add(divisionviolation);
				}
			}
		}
		if (menuviolations.isEmpty()) {
			return menu;
		} else {
			throw new ValidationException(menuviolations.toString());
		}	
	}

	public List<String> setDivsionDetails(Division division) {
		List<String> divisionviolations = new ArrayList<String>();
		if(division.getDivisionId()==null || division.getDivisionId().isBlank() || division.getDivisionId().isEmpty()) {
			division.setDivisionId(UUID.randomUUID().toString());
			division.setCreatedBy(createdby);
			division.setCreatedTime(LocalDateTime.now());
			division.setUpdatedBy(updatedby);
			division.setUpdatedTime(updatedDateTime);
		}
		List<Category> categories = division.getCategories();
		List<String> categoryviolations = new ArrayList<String>();
		for (Category category : categories) {
			categoryviolations = setCategoryDetails(category, division.getDivisionId());
			if (!categoryviolations.isEmpty()) {
				for (String categoryviolation : categoryviolations) {
					divisionviolations.add(categoryviolation);
				}
			}
		}
		return divisionviolations;
	}
	

	public List<String> setCategoryDetails(Category category, String divisionId) {
		System.out.println(divisionId);
		List<String> categoryviolations = new ArrayList<String>();
		if (category.getCategoryId()==null || category.getCategoryId().isBlank() || category.getCategoryId().isEmpty()) {
			category.setCategoryId(UUID.randomUUID().toString());
			category.setCreatedBy(createdby);
			category.setCreatedTime(LocalDateTime.now());
			category.setUpdatedBy(updatedby);
			category.setUpdatedTime(updatedDateTime);
		} 
		List<MenuItem> menuItems = category.getItems();
		List<String> itemviolations = new ArrayList<String>();
		for (MenuItem item : menuItems) {
			itemviolations = setItemDetails(item);
			if (!itemviolations.isEmpty()) {
				for (String itemviolation : itemviolations) {
					categoryviolations.add(itemviolation);
				}
			}
		}
		return categoryviolations;
	}

	public List<String> setItemDetails(MenuItem item) {
		List<String> itemviolations = new ArrayList<String>();
		if (item.getMenuItemId()==null || item.getMenuItemId().isBlank() || item.getMenuItemId().isEmpty()) {
			item.setMenuItemId(UUID.randomUUID().toString());
			item.setCreatedBy(createdby);
			item.setCreatedTime(LocalDateTime.now());
			item.setUpdatedBy(updatedby);
			item.setUpdatedTime(updatedDateTime);
		}
		return itemviolations;
		
	}
}
