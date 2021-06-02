package com.example.restaurants.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "additional_charges")
public class AdditionalCharges {

	@Id
	@Column(name = "charge_id")
	private String chargeId;

	@Column(name = "charge_name")
	private String chargeName;

	@Column(name = "charge_amount")
	private float chargeAmount;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;

	public AdditionalCharges() {
	}

	public AdditionalCharges(String chargeId, String chargeName, float chargeAmount, String createdBy,
			LocalDateTime createdTime, String updatedBy, LocalDateTime updatedTime) {
		super();
		this.chargeId = chargeId;
		this.chargeName = chargeName;
		this.chargeAmount = chargeAmount;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "AdditionalCharges [chargeId=" + chargeId + ", chargeName=" + chargeName + ", chargeAmount="
				+ chargeAmount + ", createdBy=" + createdBy + ", createdTime=" + createdTime + ", updatedBy="
				+ updatedBy + ", updatedTime=" + updatedTime + "]";
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public float getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(float chargeAmount) {
		this.chargeAmount = chargeAmount;
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
