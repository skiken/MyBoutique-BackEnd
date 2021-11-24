package com.trainting.MyBoutique.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDto {

	private Long id;
	
	private String status;
	
	private Long orderId;

	
}
