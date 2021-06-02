package com.example.restaurants.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuItem {

	@Id
	@Column(name = "menu_item_id", nullable = false)
	private String menuItemId;

	@Length(min = 3, max = 50, message = "item name should be between 3-50 characters")
	@NotBlank(message = "item name cannot be blank")
	@NotNull(message = "item name cannot be null")
	@Column(name = "menu_item_name", nullable = false)
	private String menuItemName;

	@NotNull
	@PositiveOrZero(message = "item cost cannot be negative")
	@Column(name = "menu_item_cost", nullable = false)
	private int menuItemCost;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;


}
