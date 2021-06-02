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

/**
 *  This is an entity class for defining details of restaurant address
 * @author schennapragada
 *
 */
@Entity
@Table(name = "address")
public class Address {

	/**
	 *  Primary key for this class
	 */
	@Id
	// @GeneratedValue
	//@NotNull(message = "adress id cannot be empty")
	@Column(name = "address_id", nullable = false)
	private String addressID;

	@Length(min = 2, max = 50, message = "street should be between 2-50 characters")
	@NotBlank(message = "street cannot be empty")
	@Column(name = "street", nullable = false)
	private String street;

	@Length(min = 2, max = 50, message = "locality should be between 2-50 characters")
	@NotBlank(message = "locality cannot be empty")
	@Column(name = "locality", nullable = false)
	private String locality;

	@Length(min = 2, max = 50, message = "city should be between 2-50 characters")
	@NotBlank(message = "city cannot be empty")
	@Column(name = "city", nullable = false)
	private String city;

	/**
	 *  Foreign key for this entity
	 */
	@Valid
	@NotNull(message = "Location cannot be empty")
	@OneToOne(cascade = { CascadeType.ALL }, optional = false)
	@JoinColumn(referencedColumnName = "location_id", nullable = false)
	private Location location;

	/**
	 *  Audit fields for this entity
	 */
	// @Length(min=2, max=50, message="address created by should be between 2-50
	// characters")
	//@NotBlank(message = "address created by field cannot be empty")
	@Column(name = "address_created_by", nullable = false)
	private String addressCreatedBy;

	@Column(name = "address_created_date_time", nullable = false)
	private LocalDateTime addressCreatedDateTime;

	@Length(min = 2, max = 50, message = "address updated by should be between 2-50 characters")
	@Column(name = "address_updated_by")
	private String addressUpdatedBy;

	@Column(name = "address_updated_date_time")
	private LocalDateTime addressUpdatedDateTime;

	public Address() {
	}

	public Address(String addressID,
			@NotBlank(message = "street cannot be empty") String street,
			@NotBlank(message = "locality cannot be empty") String locality,
			@NotBlank(message = "city cannot be empty") String city,
			@NotNull(message = "location cannot be empty") Location location,
			@NotNull(message = "field cannot be empty") String addressCreatedBy, LocalDateTime addressCreatedDateTime,
			@NotNull(message = "field cannot be empty") String addressUpdatedBy, LocalDateTime addressUpdatedDateTime) {
		super();
		this.addressID = addressID;
		this.street = street;
		this.locality = locality;
		this.city = city;
		this.location = location;
		this.addressCreatedBy = addressCreatedBy;
		this.addressCreatedDateTime = addressCreatedDateTime;
		this.addressUpdatedBy = addressUpdatedBy;
		this.addressUpdatedDateTime = addressUpdatedDateTime;
	}

	/**
	 *  Getters and setters for this class
	 * @return respective data type
	 */
	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAddressCreatedBy() {
		return addressCreatedBy;
	}

	public void setAddressCreatedBy(String addressCreatedBy) {
		this.addressCreatedBy = addressCreatedBy;
	}

	public LocalDateTime getAddressCreatedDateTime() {
		return addressCreatedDateTime;
	}

	public void setAddressCreatedDateTime(LocalDateTime addressCreatedDateTime) {
		this.addressCreatedDateTime = addressCreatedDateTime;
	}

	public String getAddressUpdatedBy() {
		return addressUpdatedBy;
	}

	public void setAddressUpdatedBy(String addressUpdatedBy) {
		this.addressUpdatedBy = addressUpdatedBy;
	}

	public LocalDateTime getAddressUpdatedDateTime() {
		return addressUpdatedDateTime;
	}

	public void setAddressUpdatedDateTime(LocalDateTime addressUpdatedDateTime) {
		this.addressUpdatedDateTime = addressUpdatedDateTime;
	}


	@Override
	public String toString() {
		return "Address [addressID=" + addressID + ", street=" + street + ", locality=" + locality + ", city=" + city
				+ ", location=" + location + ", addressCreatedBy=" + addressCreatedBy + ", addressCreatedDateTime="
				+ addressCreatedDateTime + ", addressUpdatedBy=" + addressUpdatedBy + ", addressUpdatedDateTime="
				+ addressUpdatedDateTime + "]";
	}

}