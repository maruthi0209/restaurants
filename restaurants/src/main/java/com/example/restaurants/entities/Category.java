package com.example.restaurants.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

	@Id
	@Column(name = "category_id", nullable = false)
	private String categoryId;

	@Length(min = 3, max = 50, message = "category type should be between 3-50 characters")
	@NotNull(message = "category name cannot be null")
	@Column(name = "category_name", nullable = false)
	private String categoryName;

	@OneToMany
	@NotNull(message = "list of items cannot be null")
	@Valid
	@Size(min = 1)
	@NotEmpty(message = "menu items list cannot be empty")
	@Column(name = "menu_item")
	private List<MenuItem> items;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;


}
