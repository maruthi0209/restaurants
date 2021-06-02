package com.example.restaurants.repos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurants.entities.Menu;
import com.example.restaurants.entities.Category;
import com.example.restaurants.entities.Division;
import com.example.restaurants.entities.MenuItem;
import com.example.restaurants.models.RestaurantDetails;

public interface InterfaceMenuRepository extends JpaRepository<Menu, String> {

///////////////////////// insert operation in tables /////////////////////////////////////	
	
	@Modifying
	@Query(value = "INSERT INTO menu_item (menu_item_id, created_by, created_time, menu_item_cost, menu_item_name, updated_by, updated_time) VALUES(:menuItemId, :createdBy, :createdTime, :menuItemCost, :menuItemName, :updatedBy, :updatedTime);", nativeQuery = true)
	int insertIntoMenuItemTable(@Param(value = "menuItemId") String menuItemId,
			@Param(value = "createdBy") String createdBy, @Param(value = "createdTime") LocalDateTime createdTime,
			@Param(value = "menuItemCost") int menuItemCost, @Param(value = "menuItemName") String menuItemName, 
			@Param(value = "updatedBy") String updatedBy, @Param(value = "updatedTime") LocalDateTime updatedTime);

	@Modifying
	@Query(value = "INSERT INTO category (category_id, category_name, created_by, created_time, updated_by, updated_time) VALUES(:categoryId, :categoryName, :createdBy, :createdTime, :updatedBy, :updatedTime);", nativeQuery = true)
	int insertIntoCategoryTable(@Param(value = "categoryId") String categoryId,
			@Param(value = "createdBy") String createdBy, @Param(value = "createdTime") LocalDateTime createdTime,
			@Param(value = "categoryName") String categoryName, @Param(value = "updatedBy") String updatedBy, @Param(value = "updatedTime") LocalDateTime updatedTime);

	@Modifying
	@Query(value = "INSERT INTO category_items (category_category_id, items_menu_item_id) VALUES(:categoryId, :menuItemId);", nativeQuery = true)
	int insertIntoCategoryMenuItemsTable(@Param(value = "categoryId") String categoryId,
			@Param(value = "menuItemId") String menuItemId);

	@Modifying
	@Query(value = "INSERT INTO division (division_id, created_by, created_time, division_name, updated_by, updated_time) VALUES(:divisionId, :createdBy, :createdTime, :divisionName, :updatedBy, :updatedTime);", nativeQuery = true)
	int insertIntoDivisionTable(@Param(value = "divisionId") String divisionId,
			@Param(value = "createdBy") String createdBy, @Param(value = "createdTime") LocalDateTime createdTime,
			@Param(value = "divisionName") String divisionName, @Param(value = "updatedBy") String updatedBy, @Param(value = "updatedTime") LocalDateTime updatedTime);

	@Modifying
	@Query(value = "INSERT INTO division_categories (division_division_id, categories_category_id) VALUES(:divisionId, :categoryId);", nativeQuery = true)
	int insertIntoDivisionCategoriesTable(@Param(value = "divisionId") String divisionId,
			@Param(value = "categoryId") String categoryId);

	@Modifying
	@Query(value = "INSERT INTO menu (menu_id, created_by, created_time, restaurant_code, updated_by, updated_time) VALUES(:menuId, :createdBy, :createdTime, :restaurantCode, :updatedBy, :updatedTime);", nativeQuery = true)
	int insertIntoMenuTable(@Param(value = "menuId") String menuId, @Param(value = "createdBy") String createdBy,
			@Param(value = "createdTime") LocalDateTime createdTime, @Param(value = "restaurantCode") String restaurantCode, 
			@Param(value = "updatedBy") String updatedBy, @Param(value = "updatedTime") LocalDateTime updatedTime);
	
	@Modifying
	@Query(value="INSERT INTO menu_divisions (menu_menu_id, divisions_division_id) VALUES(:menuId, :divisionId);", nativeQuery = true)
	int insertIntoMenuDivisionTable(@Param(value = "menuId") String menuId, @Param(value = "divisionId") String divisionId);

	
///////////////////////// select operation in tables /////////////////////////////////////
	
	@Query(value = "SELECT menu FROM Menu menu WHERE restaurantCode = (SELECT restaurantCode FROM RestaurantDetails WHERE restaurantName = ?1 )")
	Menu getMenuForRestaurantName(String restaurantName);
	
	@Query(value="SELECT * FROM menu INNER JOIN restaurant_details ON restaurant_details.restaurant_code = menu.restaurant_code WHERE restaurant_name = ?1 ", nativeQuery = true)
	Optional<Menu> getMenuUsingJoins(String restaurantName);
	
	@Query(value="SELECT * FROM menu INNER JOIN menu_divisions ON menu.menu_id = menu_divisions.menu_menu_id WHERE menu.menu_id = ?1 ", nativeQuery = true)
	Optional<Menu> getFullMenuUsingJoins(String menuId);
	
	
///////////////////////// update operation in tables /////////////////////////////////////	
	
	@Modifying
	@Query(value="UPDATE division SET updated_by= ?2, updated_time= ?3 WHERE division_id= ?1 ", nativeQuery = true)
	int updateDivisionTable(String divisionId, String updatedBy, LocalDateTime updatedTime);
	
	@Modifying
	@Query(value="UPDATE category SET updated_by= ?2, updated_time=?3 WHERE category_id= ?1 ;", nativeQuery = true)
	int updateCategoryDetails(String categoryId, String updatedBy, LocalDateTime updatedTime);
	
	@Modifying
	@Query(value = "UPDATE menu_item SET menu_item_cost= ?2 , updated_by=?3 , updated_time=?4  WHERE menu_item_id= ?1 ;", nativeQuery = true)
	int updateMenuItem(String itemId, int itemCost, String updatedBy, LocalDateTime updatedTime);
	
	
///////////////////////// delete operation in tables /////////////////////////////////////		
	
	@Modifying
	@Query(value="DELETE FROM menu_divisions WHERE menu_menu_id= ?1 AND divisions_division_id= ?2 ", nativeQuery = true)
	int deleteFromMenuDivisionTable(String menuId, String divisionId);

	@Modifying
	@Query(value="DELETE FROM division WHERE division_id= ?1 ;", nativeQuery = true)
	int deleteFromDivisionTable(String divisionId);
	
	@Modifying
	@Query(value="DELETE FROM division_categories WHERE categories_category_id= ?1 ;", nativeQuery = true)
	int deleteFromDivisionCategories(String categoryId);
	
	@Modifying
	@Query(value="DELETE FROM category WHERE category_id= ?1 ;", nativeQuery = true)
	int deleteFromCategoryTable(String categoryId);
	
	@Modifying
	@Query(value="DELETE FROM category_items WHERE items_menu_item_id= ?1 ;", nativeQuery = true)
	int deleteFromCategoryItemTable(String itemId);
	
	@Modifying
	@Query(value="DELETE FROM menu_item WHERE menu_item_id= ?1 ;", nativeQuery = true)
	int deleteFromMenuItemTable(String itemId);
	
	@Modifying
	@Query(value = "DELETE FROM menu WHERE menu_id= ?1 ;", nativeQuery = true)
	int deleteFromMenuTable(String menuId);
	
///////////////////////// other operation in tables /////////////////////////////////////	

	@Query(value="SELECT restaurant_details.restaurant_name FROM menu_item INNER JOIN category_items ON category_items.items_menu_item_id = menu_item.menu_item_id INNER JOIN category ON category.category_id = category_items.category_category_id INNER JOIN division_categories ON category.category_id = division_categories.categories_category_id INNER JOIN division ON division_categories.division_division_id = division.division_id INNER JOIN menu_divisions ON division.division_id = menu_divisions.divisions_division_id INNER JOIN menu ON menu_divisions.menu_menu_id = menu.menu_id INNER JOIN restaurant_details ON menu.restaurant_code = restaurant_details.restaurant_code WHERE menu_item_name = ?1 ", nativeQuery = true)
	List<String> getRestaurantNameForMenuItem(String menuItemName);
	
	@Query(value="SELECT restaurant_details.restaurant_name FROM menu_item INNER JOIN category_items ON category_items.items_menu_item_id = menu_item.menu_item_id INNER JOIN category ON category.category_id = category_items.category_category_id INNER JOIN division_categories ON category.category_id = division_categories.categories_category_id INNER JOIN division ON division_categories.division_division_id = division.division_id INNER JOIN menu_divisions ON division.division_id = menu_divisions.divisions_division_id INNER JOIN menu ON menu_divisions.menu_menu_id = menu.menu_id INNER JOIN restaurant_details ON menu.restaurant_code = restaurant_details.restaurant_code WHERE category_name = ?1 ", nativeQuery = true)
	List<String> getRestaurantNameForMenuCategory(String menucategoryName);
	
	@Query(value="SELECT restaurant_details.restaurant_name FROM menu_item INNER JOIN category_items ON category_items.items_menu_item_id = menu_item.menu_item_id INNER JOIN category ON category.category_id = category_items.category_category_id INNER JOIN division_categories ON category.category_id = division_categories.categories_category_id INNER JOIN division ON division_categories.division_division_id = division.division_id INNER JOIN menu_divisions ON division.division_id = menu_divisions.divisions_division_id INNER JOIN menu ON menu_divisions.menu_menu_id = menu.menu_id INNER JOIN restaurant_details ON menu.restaurant_code = restaurant_details.restaurant_code WHERE division_name = ?1 ", nativeQuery = true)
	List<String> getRestaurantNameForMenuDivision(String menuDivisionName);
	
	@Query(value="SELECT menu_item.menu_item_cost FROM menu_item INNER JOIN category_items ON category_items.items_menu_item_id = menu_item.menu_item_id INNER JOIN category ON category.category_id = category_items.category_category_id INNER JOIN division_categories ON category.category_id = division_categories.categories_category_id INNER JOIN division ON division_categories.division_division_id = division.division_id INNER JOIN menu_divisions ON division.division_id = menu_divisions.divisions_division_id INNER JOIN menu ON menu_divisions.menu_menu_id = menu.menu_id INNER JOIN restaurant_details ON menu.restaurant_code = restaurant_details.restaurant_code WHERE menu_item_name = ?1 AND restaurant_name = ?2 ", nativeQuery = true)
	int getItemCostForMenuItem(String menuItemName, String restaurantName); 
	
	@Query(value="SELECT menu_item.menu_item_name FROM menu_item INNER JOIN category_items ON category_items.items_menu_item_id = menu_item.menu_item_id INNER JOIN category ON category.category_id = category_items.category_category_id INNER JOIN division_categories ON category.category_id = division_categories.categories_category_id INNER JOIN division ON division_categories.division_division_id = division.division_id INNER JOIN menu_divisions ON division.division_id = menu_divisions.divisions_division_id INNER JOIN menu ON menu_divisions.menu_menu_id = menu.menu_id INNER JOIN restaurant_details ON menu.restaurant_code = restaurant_details.restaurant_code WHERE division_name = ?1 AND restaurant_name = ?2 ", nativeQuery = true)
	List<String> getItemListForMenuDivision(String menuDivisionName, String restaurantName);
	
	@Query(value="SELECT menu_item.menu_item_name FROM menu_item INNER JOIN category_items ON category_items.items_menu_item_id = menu_item.menu_item_id INNER JOIN category ON category.category_id = category_items.category_category_id INNER JOIN division_categories ON category.category_id = division_categories.categories_category_id INNER JOIN division ON division_categories.division_division_id = division.division_id INNER JOIN menu_divisions ON division.division_id = menu_divisions.divisions_division_id INNER JOIN menu ON menu_divisions.menu_menu_id = menu.menu_id INNER JOIN restaurant_details ON menu.restaurant_code = restaurant_details.restaurant_code WHERE category_name = ?1 AND restaurant_name = ?2 ", nativeQuery = true)
	List<String> getItemListForMenuCategory(String menucategoryName, String restaurantName);
}
