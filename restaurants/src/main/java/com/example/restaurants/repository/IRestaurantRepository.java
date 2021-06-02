package com.example.restaurants.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.restaurants.models.Address;
import com.example.restaurants.models.RestaurantDetails;

/**
 *  A repository interface to insert data into restaurant details table
 * @author schennapragada
 *
 */
public interface IRestaurantRepository extends JpaRepository<RestaurantDetails, String> {

	/**
	 *  Method to get list of restaurants based on name
	 * @param restaurantName
	 * @return list of restaurants 
	 */
	@Query("SELECT restaurantName FROM RestaurantDetails WHERE restaurantName = ?1")
	ArrayList<String> findRestaurantName(String restaurantName);

	/**
	 *  Method to get last created restaurant details 
	 * @return last created restaurant details 
	 */
	@Query(value = "SELECT * FROM restaurant_details  ORDER BY created_date_time DESC LIMIT 1 ", nativeQuery = true)
	RestaurantDetails findLastCreatedRestaurant();

	/**
	 *  Method to count the number of rows in restaurant details table
	 * @return count of rows in integer form
	 */
	@Query("select count(r) from RestaurantDetails r ")
	int findCount();

	/**
	 *  An experimental method to insert data into restaurant details table
	 * @param restaurantAddress
	 * @param restaurantCode
	 * @param restaurantCreatedBy
	 * @param restaurantCreatedDateTime
	 * @param restaurantName
	 * @param restaurantType
	 * @param restaurantUpdatedBy
	 * @param restaurantUpdatedDateTime
	 * @param uuid
	 * @return object of type restaurant details
	 */
	@Modifying
	@Query(value = "INSERT INTO restaurant_details (restaurant_id, restaurant_code, restaurant_name, restaurant_type, restaurant_address_address_id, created_by, created_date_time, updated_by, updated_date_time) VALUES (:id, :code, :name, :type, :address, :createdby, :createddatetime, :updatedby, :updatedtime)", nativeQuery = true)
	RestaurantDetails insertIntoDatabase(@Param("address") String restaurantAddress,
			@Param("code") String restaurantCode, @Param("createdby") String restaurantCreatedBy,
			@Param("createddatetime") LocalDateTime restaurantCreatedDateTime, @Param("name") String restaurantName,
			@Param("type") String restaurantType, @Param("updatedby") String restaurantUpdatedBy,
			@Param("updatedtime") LocalDateTime restaurantUpdatedDateTime, @Param("id") String uuid);

	
	@Modifying
	@Query(value="INSERT INTO address (address_id, address_created_by, address_created_date_time, address_updated_by, address_updated_date_time, city, locality, street, location_location_id) VALUES(:addressId, :createdby, :createddatetime, :updatedby, :updateddatetime, :city, :locality, :street, :locationId); ", nativeQuery = true)
	int insertIntoAddressTable(@Param("addressId") String addressId,
			@Param("city") String city, @Param("createdby") String createdby,
			@Param("createddatetime") LocalDateTime createddatetime, @Param("locality") String locality,
			@Param("street") String street, @Param("updatedby") String updatedby,
			@Param("updateddatetime") LocalDateTime updateddatetime, @Param("locationId") String locationId);
	
	@Modifying
	@Query(value="INSERT INTO \"location\" (location_id, latitude, location_created_by, location_created_date_time, location_updated_by, location_updated_date_time, longitude) VALUES(:locationId, :latitude, :createdby, :createddatetime, :updatedby, :updateddatetime, :longitude);", nativeQuery = true)
	int insertIntoLocationTable(@Param("locationId") String locationId, @Param("latitude") Double latitude ,@Param("createdby") String createdby,
			@Param("createddatetime") LocalDateTime createddatetime,  @Param("updatedby") String updatedby,
			@Param("updateddatetime") LocalDateTime updateddatetime, @Param("longitude") Double longitude);
	
	@Query(value = "SELECT restaurant_code FROM restaurant_details WHERE restaurant_name = ?1 ;", nativeQuery = true)
	String getRestaurantCodeForRestaurantName(String restaurantName);
	
	@Modifying
	@Query(value = "DELETE FROM \"location\" WHERE location_id= ?1 ;", nativeQuery = true)
	int deleteLocation(String locationId);
	
	@Modifying
	@Query(value = "DELETE FROM address WHERE location_location_id= ?1 ;", nativeQuery = true)
	int deleteAddress(String locationId);
	
	@Modifying
	@Query(value = "DELETE FROM restaurant_details WHERE restaurant_address_address_id= ?1 ;", nativeQuery = true)
	int deleteRestaurant(String addressId);
	
	@Query(value = "SELECT restaurant_id, restaurant_code, created_by, created_date_time, restaurant_name, restaurant_type, updated_by, updated_date_time, restaurant_address_address_id FROM restaurant_details WHERE restaurant_name = ?1 ;", nativeQuery = true)
	Optional<RestaurantDetails> getRestaurantDetailsForName(String restaurantName);
}
