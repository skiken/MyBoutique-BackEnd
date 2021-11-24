package com.trainting.MyBoutique.web;

import java.util.List;

import com.trainting.MyBoutique.persistence.Order;
import com.trainting.MyBoutique.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.OrderDto;
import com.trainting.MyBoutique.services.OrderService;

import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.websocket.server.PathParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api"+"/orders")
public class OrderEndpoint {
	
	@Autowired
	private OrderService orderService;

	
	@PostMapping("/create")
	public OrderDto create(@RequestBody OrderDto orderDto) {
		return orderService.create(orderDto);
	}
	
	@GetMapping()
	public List<OrderDto> findAll(){
		return orderService.findAll();
	}

	@GetMapping("/confirmed")
	public List<OrderDto> findAllConfirmedOrders(){
		return orderService.findAllConfirmedOrders();
	}

	@GetMapping("/inDelivery")
	public List<OrderDto> findAllOrdersInDelivery(){
		return orderService.findAllOrdersInDelivery();
	}


	@GetMapping("{id}")
	public OrderDto findById(@PathVariable Long id) {
		return orderService.findOrderById(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		this.orderService.delete(id);
	}

	@PutMapping("/{id}")
	public void updateOrder(@PathVariable("id") Long idOrder, @RequestBody OrderDto orderDto){
		this.orderService.updateOrder(idOrder,orderDto);
	}
}
