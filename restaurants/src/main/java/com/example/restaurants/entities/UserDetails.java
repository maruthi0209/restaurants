package com.example.restaurants.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.restaurants.models.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetails {

	@Id
	private String userId;

	@NotBlank(message = "User name cannot be blank")
	@Size(min = 3, max = 30, message = "user name must be between 3 and 30 chars")
	@Pattern(regexp = "^[a-zA-Z\\s]*$")
	@Column(name = "user_name")
	private String userName;

	@NotNull(message = "User Address Cannot be null")
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(referencedColumnName = "address_id")
	private Address userAddress;

	@OneToMany
	@Column(name = "cart_details")
	private List<CartDetails> cartDetails;

	@Column(name = "created_by")
	public String createdBy;

	@Column(name = "created_time")
	public LocalDateTime createdTime;

	@Column(name = "updated_by")
	public String updatedBy;

	@Column(name = "updated_time")
	public LocalDateTime updatedTime;

}
