package com.trainting.MyBoutique.persistence;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("customer")
public class Customer extends UserEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Email
	@Column(name = "email")
	private String email;
	
	@Column(name = "telephone")
	private String telephone;
	
	
	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	private Set<Cart> setCart= new HashSet<>();
	
	@Column(name="enabled")
	private Boolean enabled;

	@Embedded
	private Address address;

	
	public Customer(String username, String password, String roles,String firstName, String lastName, String email, String telephone, Set<Cart> setCart,
			Boolean enabled ,Address address) {
		super(username, password, roles);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephone = telephone;
		this.setCart = setCart;
		this.enabled = enabled;
		this.address=address;
	}

	

	
	
}
