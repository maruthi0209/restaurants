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
@Table(name = "division")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Division {

	@Id
	@Column(name = "division_id", nullable = false)
	private String divisionId;

	@Length(min = 3, max = 50, message = "division name should be between 3-50 characters")
	@NotNull(message = "division name cannot be null")
	@NotBlank(message = "division name cannot be blank")
	@Column(name = "division_name", nullable = false)
	private String divisionName;

	@OneToMany
	@NotNull(message="list of categories cannot be null")
	@Valid
	@Size(min = 1)
	@NotEmpty(message = "category list cannot be empty")
	@Column(name = "category")
	private List<Category> categories;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;


}
