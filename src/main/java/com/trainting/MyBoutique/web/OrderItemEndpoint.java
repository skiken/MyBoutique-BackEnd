package com.trainting.MyBoutique.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.OrderItemDto;
import com.trainting.MyBoutique.services.OrderItemService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api"+"/orderItems")
public class OrderItemEndpoint {

	
	@Autowired
	private  OrderItemService itemService;
	
	@GetMapping
	public List<OrderItemDto> findAll() {
	return this.itemService.findAll();
	}

	@GetMapping("/{id}")
	public OrderItemDto findById(@PathVariable Long id) {
	return this.itemService.findById(id);
	}

	@PostMapping("/create")
	public OrderItemDto create(@RequestBody OrderItemDto orderItemDto) {
	return this.itemService.create(orderItemDto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	this.itemService.delete(id);
	}

	@GetMapping("/idOrder/{id}")
	public List<OrderItemDto> findByIdOrder(@PathVariable Long id) {
		return this.itemService.findByIdOrder(id);
	}

	@PutMapping("/{id}")
	public OrderItemDto update(@PathVariable  Long id, @RequestBody OrderItemDto orderItemDto) {
		return this.itemService.update(id,orderItemDto);
	}

	@DeleteMapping("/orderId/{id}")
	public void deleteByOrderId(@PathVariable Long id) {
		this.itemService.deleteByOrderId(id);
	}


}
