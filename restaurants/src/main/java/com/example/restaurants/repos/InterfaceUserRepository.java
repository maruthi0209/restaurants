package com.example.restaurants.repos;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurants.entities.UserDetails;

public interface InterfaceUserRepository extends JpaRepository<UserDetails, String> {

	@Modifying
	@Query(value="INSERT INTO user_details (user_id, created_by, created_time, updated_by, updated_time, user_name, user_address_address_id) VALUES(:userId, :createdBy, :createdTime, :updatedBy, :updatedTime, :userName, :userAddress); ", nativeQuery = true)
	int insertIntoUserDetailsTable(@Param(value = "userId") String userId,
			@Param(value = "createdBy") String createdBy, @Param(value = "createdTime") LocalDateTime createdTime,
			@Param(value = "userName") String userName, @Param(value = "userAddress") String userAddress, 
			@Param(value = "updatedBy") String updatedBy, @Param(value = "updatedTime") LocalDateTime updatedTime);
}
