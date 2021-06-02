package com.example.restaurants.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coupon")
public class Coupon {

	@Id
	@Column(name = "coupon_id")
	private String couponId;

	@Column(name = "coupon_code")
	private String couponCode;

	@Column(name = "discount_percentage")
	private int discountPercentage;

	@Column(name = "valid_upto")
	private LocalDateTime validUpto;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;

	public Coupon() {
	}

	public Coupon(String couponId, String couponCode, int discountPercentage, LocalDateTime validUpto, String createdBy,
			LocalDateTime createdTime, String updatedBy, LocalDateTime updatedTime) {
		super();
		this.couponId = couponId;
		this.couponCode = couponCode;
		this.discountPercentage = discountPercentage;
		this.validUpto = validUpto;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", couponCode=" + couponCode + ", discountPercentage="
				+ discountPercentage + ", validUpto=" + validUpto + ", createdBy=" + createdBy + ", createdTime="
				+ createdTime + ", updatedBy=" + updatedBy + ", updatedTime=" + updatedTime + "]";
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public LocalDateTime getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(LocalDateTime validUpto) {
		this.validUpto = validUpto;
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
