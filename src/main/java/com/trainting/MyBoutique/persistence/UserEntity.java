package com.trainting.MyBoutique.persistence;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainting.MyBoutique.persistence.abstracts.AbstractEntity;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "UserEntity")
@DiscriminatorColumn

public  class UserEntity extends AbstractEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "username")
	@JsonIgnore
	private String username;
	
	
	@Column(name="password")
	@JsonIgnore
	protected String password;
	
	
	@Column(name="roles")
	@JsonIgnore
	protected String roles;
	
	

	
	
	
	

}
