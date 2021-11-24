package com.trainting.MyBoutique.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.PaymentDto;
import com.trainting.MyBoutique.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api"+"/payment")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentEndpoint {
	
	@Autowired
	private  PaymentService paymentService;
	
	@GetMapping
	public List<PaymentDto> findAll() {
	return this.paymentService.findAll();
	}
	@GetMapping("/{id}")
	public PaymentDto findById(@PathVariable Long id) {
	return this.paymentService.findById(id);
	}
	@PostMapping("/create")
	public PaymentDto create(@RequestBody PaymentDto orderItemDto) {
	return this.paymentService.create(orderItemDto);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	this.paymentService.delete(id);
	}

}
