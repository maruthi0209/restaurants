package com.example.restaurants.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.restaurants.models.RestaurantDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_details")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class CartDetails {

	@Id
	@Column(name = "cart_item_id")
	private String cartItemId;

	@ManyToOne
	@JoinColumn(name = "menu_item")
	private MenuItem item;

	@Column(name = "quantity")
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "user_details")
	private UserDetails userDetails;

	@ManyToOne
	@JoinColumn(name = "restaurant_details")
	private RestaurantDetails restaurantDetails;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menuId;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;

	public CartDetails() {
	}

	public CartDetails(String cartItemId, MenuItem item, int quantity, UserDetails userDetails,
			RestaurantDetails restaurantDetails, Menu menuId, String createdBy, LocalDateTime createdTime,
			String updatedBy, LocalDateTime updatedTime) {
		super();
		this.cartItemId = cartItemId;
		this.item = item;
		this.quantity = quantity;
		this.userDetails = userDetails;
		this.restaurantDetails = restaurantDetails;
		this.menuId = menuId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "CartDetails [cartItemId=" + cartItemId + ", item=" + item + ", quantity=" + quantity + ", userDetails="
				+ userDetails + ", restaurantDetails=" + restaurantDetails + ", menuId=" + menuId + ", createdBy="
				+ createdBy + ", createdTime=" + createdTime + ", updatedBy=" + updatedBy + ", updatedTime="
				+ updatedTime + "]";
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public RestaurantDetails getRestaurantDetails() {
		return restaurantDetails;
	}

	public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
		this.restaurantDetails = restaurantDetails;
	}

	public Menu getMenuId() {
		return menuId;
	}

	public void setMenuId(Menu menuId) {
		this.menuId = menuId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

}
