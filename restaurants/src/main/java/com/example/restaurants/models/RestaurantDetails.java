package com.example.restaurants.models;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
/*import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType; */

/**
 *  An entity to define the attributes of restaurant
 * @author schennapragada
 *
 */
@Entity
@Table(name = "restaurant_details")
//@Document(indexName="restaurants-application", shards=1)
public class RestaurantDetails {

	/**
	 *  Primary key for this entity
	 */
	@Id
	// @GeneratedValue
	// @NotNull(message="restaurant id cannot be empty")
	@org.springframework.data.annotation.Id
	@Column(name = "restaurant_id", nullable = false)
	private String restaurantId;

	@Length(min = 2, max = 50, message = "restaurant name should be between 2-50 characters")
	@NotBlank(message = "restaurant name cannot be empty")
	@Column(name = "restaurant_name", unique = true, nullable = false)
	private String restaurantName;

	/**
	 *  Foreign key for this entity
	 */
	@Valid
	@NotNull(message = "address cannot be empty")
	@OneToOne(cascade = { CascadeType.ALL }, optional = false)
	@JoinColumn(referencedColumnName = "address_id", nullable = false)
	private Address restaurantAddress;

	@Length(min = 2, max = 50, message = "restaurant type should be between 2-50 characters")
	@NotBlank(message = "restarant type cannot be empty")
	@Column(name = "restaurant_type", nullable = false)
	private String restaurantType;

	/**
	 *  Audit fields for this entity
	 */
	// @Length(min=2, max=50, message="restaurant created by should be between 2-50
	// characters")
	//@NotBlank(message = "restaurant created by cannot be empty")
	@Column(name = "created_by", nullable = false)
	private String restaurantCreatedBy;

//	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	@Column(name = "created_date_time", nullable = false)
	private LocalDateTime restaurantCreatedDateTime;

	// @Length(min=2, max=50, message="restaurant updated by should be between 2-50
	// characters")
	@Column(name = "updated_by")
	private String restaurantUpdatedBy;

	@Column(name = "updated_date_time")
	private LocalDateTime restaurantUpdatedDateTime;

	// @Length(min=6, max=6, message="restaurant code should be 6 characters long")
	@Column(name = "restaurant_code", nullable = false)
	private String restaurantCode;

	public RestaurantDetails() {
	}

	public RestaurantDetails(@NotBlank(message = "restrant id cannot be empty") String restaurantId,
			@NotBlank(message = "restaurant name cannot be empty") String restaurantName, Address restaurantAddress,
			@NotBlank(message = "restarant type cannot be empty") String restaurantType,
			@NotNull(message = "field cannot be empty") String restaurantCreatedBy,
			LocalDateTime restaurantCreatedDateTime,
			@NotNull(message = "field cannot be empty") String restaurantUpdatedBy,
			LocalDateTime restaurantUpdatedDateTime, String restaurantCode) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.restaurantAddress = restaurantAddress;
		this.restaurantType = restaurantType;
		this.restaurantCreatedBy = restaurantCreatedBy;
		this.restaurantCreatedDateTime = restaurantCreatedDateTime;
		this.restaurantUpdatedBy = restaurantUpdatedBy;
		this.restaurantUpdatedDateTime = restaurantUpdatedDateTime;
		this.restaurantCode = restaurantCode;
	}

	/**
	 *  Getters and Setters for this entity
	 * @return
	 */
	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Address getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(Address restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	public String getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(String restaurantType) {
		this.restaurantType = restaurantType;
	}

	public String getRestaurantCreatedBy() {
		return restaurantCreatedBy;
	}

	public void setRestaurantCreatedBy(String restaurantCreatedBy) {
		this.restaurantCreatedBy = restaurantCreatedBy;
	}

	public LocalDateTime getRestaurantCreatedDateTime() {
		return restaurantCreatedDateTime;
	}

	public void setRestaurantCreatedDateTime(LocalDateTime restaurantCreatedDateTime) {
		this.restaurantCreatedDateTime = restaurantCreatedDateTime;
	}

	public String getRestaurantUpdatedBy() {
		return restaurantUpdatedBy;
	}

	public void setRestaurantUpdatedBy(String restaurantUpdatedBy) {
		this.restaurantUpdatedBy = restaurantUpdatedBy;
	}

	public LocalDateTime getRestaurantUpdatedDateTime() {
		return restaurantUpdatedDateTime;
	}

	public void setRestaurantUpdatedDateTime(LocalDateTime restaurantUpdatedDateTime) {
		this.restaurantUpdatedDateTime = restaurantUpdatedDateTime;
	}

	public String getRestaurantCode() {
		return restaurantCode;
	}

	public void setRestaurantCode(String restaurantCode) {
		this.restaurantCode = restaurantCode;
	}

	@Override
	public String toString() {
		return "RestaurantDetails [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName
				+ ", restaurantAddress=" + restaurantAddress + ", restaurantType=" + restaurantType
				+ ", restaurantCreatedBy=" + restaurantCreatedBy + ", restaurantCreatedDateTime="
				+ restaurantCreatedDateTime + ", restaurantUpdatedBy=" + restaurantUpdatedBy
				+ ", restaurantUpdatedDateTime=" + restaurantUpdatedDateTime + ", restaurantCode=" + restaurantCode
				+ "]";
	}

}
