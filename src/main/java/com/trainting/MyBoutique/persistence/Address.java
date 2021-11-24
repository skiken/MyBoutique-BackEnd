package com.trainting.MyBoutique.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address {
	
	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;
	
	@Size(max = 10)
	
	@Column(name = "postcode", length = 10 )
	private String postcode;
	

	@Size(max = 15)
	@Column(name = "country", length = 15 )
	private String country;

}
