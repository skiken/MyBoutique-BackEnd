package com.trainting.MyBoutique.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import com.trainting.MyBoutique.persistence.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

	private Long id;
	
	private BigDecimal totalPrice;

	
	private String status;


	private Date shipped;


	private Address shipmentAddress;


	private Long paymentId;

	
	private Set<OrderItemDto> orderItems;
}
