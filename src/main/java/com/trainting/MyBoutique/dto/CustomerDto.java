package com.trainting.MyBoutique.dto;



import com.trainting.MyBoutique.persistence.Address;
import com.trainting.MyBoutique.services.validations.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embedded;

@Data
@AllArgsConstructor
@ValidPassword(message ="passwords are not equal")
public class CustomerDto {

	private Long id;
	
	private String firstName;
	
	
	private String lastName;
	
	
	private String email;
	
	
	private String telephone;
	
	
	private String username;
	
	
	private String password;
	
	
	private  String matchingPassword;
	
	
	private String roles;

	private Address address;



	


	




	

	
	
	
	
}
