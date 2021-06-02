package com.example.restaurants.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_details")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class OrderDetails {

	@Id
	@Column(name="order_id")
	private String orderId;
	
	@OneToOne
	@JoinColumn(name="user_details")
	private UserDetails userDetails;
	
	@OneToMany
	@Column(name="cart_details")
	private List<CartDetails> cartDetails;
	
	@Column(name="order_status")
	private String orderStatus;
	
	@OneToMany
	@Column(name="coupon")
	private List<Coupon> couponsApplied;
	
	@OneToMany
	@Column(name="additional_charges")
	private List<AdditionalCharges> additionalCharges;
	
	@Column(name="grand_total")
	private float grandTotal;

/*	@OneToOne
	@JoinColumn(name="payment_details")
	private PaymentDetails paymentDetails; */
	
	@Column(name="created_by")
	public String createdBy;
	
	@Column(name="created_time")
	public LocalDateTime createdTime;
	
	@Column(name="updated_by")
	public String updatedBy;
	
	@Column(name="updated_time")
	public LocalDateTime updatedTime;

	public OrderDetails() {

	}

	public OrderDetails(String orderId, UserDetails userDetails, List<CartDetails> cartDetails, String orderStatus,
			List<Coupon> couponsApplied, List<AdditionalCharges> additionalCharges, float grandTotal, String createdBy,
			LocalDateTime createdTime, String updatedBy, LocalDateTime updatedTime) {
		super();
		this.orderId = orderId;
		this.userDetails = userDetails;
		this.cartDetails = cartDetails;
		this.orderStatus = orderStatus;
		this.couponsApplied = couponsApplied;
		this.additionalCharges = additionalCharges;
		this.grandTotal = grandTotal;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", userDetails=" + userDetails + ", cartDetails=" + cartDetails
				+ ", orderStatus=" + orderStatus + ", couponsApplied=" + couponsApplied + ", additionalCharges="
				+ additionalCharges + ", grandTotal=" + grandTotal + ", createdBy=" + createdBy + ", createdTime="
				+ createdTime + ", updatedBy=" + updatedBy + ", updatedTime=" + updatedTime + "]";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public List<CartDetails> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<CartDetails> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Coupon> getCouponsApplied() {
		return couponsApplied;
	}

	public void setCouponsApplied(List<Coupon> couponsApplied) {
		this.couponsApplied = couponsApplied;
	}

	public float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
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

	public List<AdditionalCharges> getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(List<AdditionalCharges> additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	
	
	
}
