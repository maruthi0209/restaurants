package com.example.restaurants.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Menu {

	@Id
	@Column(name = "menu_id", nullable = false)
	private String menuId;

	@NotNull(message = "restaurant code cannot be null")
	@NotBlank(message = "restaurant code cannot be blank")
	@Column(name = "restaurant_code", nullable = false)
	private String restaurantCode;

	@Valid
	@OneToMany
//	@JoinTable(name="menu_menu_division", joinColumns= {@JoinColumn(name="menu_menu_id")}, inverseJoinColumns= {@JoinColumn(name="menu_division_menu_division_id")}) 
	@NotNull(message = "menu divisions cannot be null")
	@Size(min = 1)
	@NotEmpty(message = "divisions list cannot be empty")
	//@JoinColumn(name="division_id")
	private List<Division> divisions;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;


}
