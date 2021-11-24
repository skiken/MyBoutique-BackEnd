package com.trainting.MyBoutique.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ProductDto {

	private Long id;
	
	private String name;
	
	
	private String description;
	
	
	private BigDecimal price;
	
	
	private Integer quantity;
	
	
	private String status;
	
	
	private Integer salesCounter;

	private String imgPath;
	
	
	private Long categoryId;


	private Set<ReviewDto> reviews;
	
	private Set<OrderItemDto> orderItemsDto;
}
