package com.example.restaurants.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *  An entity to define the location attributes of a restaurant
 * @author schennapragada
 *
 */
@Entity
@Table(name = "location")
public class Location {

	/**
	 *  Primary key for this entity
	 */
	@Id
	// @GeneratedValue
	//@NotNull(message = "Location ID cannot be empty")
	@Column(name = "location_id", nullable = false)
	private String locationId;

	@NotNull(message = "Latitude cannot be empty")
	@Column(name = "latitude", nullable = false)
	private Double latitude;

	@NotNull(message = "longitude cannot be empty")
	@Column(name = "longitude", nullable = false)
	private Double longitude;

	/**
	 *  Audit fields for this entity
	 */
	// @Length(min=2, max=50, message="location created by field must be between
	// 2-50 characters")
	//@NotBlank(message = "field cannot be empty")
	@Column(name = "location_created_by", nullable = false)
	private String locationCreatedBy;

	@Column(name = "location_created_date_time", nullable = false)
	private LocalDateTime locationCreatedDateTime;

	// @Length(min=2, max=50, message="location created by field must be between
	// 2-50 characters")
	@Column(name = "location_updated_by")
	private String locationUpdatedBy;

	@Column(name = "location_updated_date_time")
	private LocalDateTime locationUpdatedDateTime;

	public Location() {
	}

	public Location(String locationId,
			@NotNull(message = "Latitude cannot be empty") Double latitude,
			@NotNull(message = "longitude cannot be empty") Double longitude,
			@NotNull(message = "field cannot be empty") String locationCreatedBy, LocalDateTime locationCreatedDateTime,
			@NotNull(message = "field cannot be empty") String locationUpdatedBy,
			LocalDateTime locationUpdatedDateTime) {
		super();
		this.locationId = locationId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.locationCreatedBy = locationCreatedBy;
		this.locationCreatedDateTime = locationCreatedDateTime;
		this.locationUpdatedBy = locationUpdatedBy;
		this.locationUpdatedDateTime = locationUpdatedDateTime;
	}

	/**
	 *  Getters and Setters for this entity
	 * @return
	 */
	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getLocationCreatedBy() {
		return locationCreatedBy;
	}

	public void setLocationCreatedBy(String locationCreatedBy) {
		this.locationCreatedBy = locationCreatedBy;
	}

	public LocalDateTime getLocationCreatedDateTime() {
		return locationCreatedDateTime;
	}

	public void setLocationCreatedDateTime(LocalDateTime locationCreatedDateTime) {
		this.locationCreatedDateTime = locationCreatedDateTime;
	}

	public String getLocationUpdatedBy() {
		return locationUpdatedBy;
	}

	public void setLocationUpdatedBy(String locationUpdatedBy) {
		this.locationUpdatedBy = locationUpdatedBy;
	}

	public LocalDateTime getLocationUpdatedDateTime() {
		return locationUpdatedDateTime;
	}

	public void setLocationUpdatedDateTime(LocalDateTime locationUpdatedDateTime) {
		this.locationUpdatedDateTime = locationUpdatedDateTime;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", locationCreatedBy=" + locationCreatedBy + ", locationCreatedDateTime=" + locationCreatedDateTime
				+ ", locationUpdatedBy=" + locationUpdatedBy + ", locationUpdatedDateTime=" + locationUpdatedDateTime
				+ "]";
	}

}
