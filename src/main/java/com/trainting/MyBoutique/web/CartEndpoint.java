package com.trainting.MyBoutique.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.CartDto;
import com.trainting.MyBoutique.services.CartService;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api"+"/carts")
public class CartEndpoint {

	@Autowired
	private CartService cartService;
	
	@GetMapping()
	public List<CartDto> findAll() {
		return  cartService.findAll();
	}
	
	@GetMapping("/active")
    public List<CartDto> findAllActiveCarts(){
    	return cartService.findActiveCart();
    }

	@GetMapping("/customerId/StatusConfirmed/{id}")
	public List<CartDto> findByCustomerIdAndStatusConfirmed(@PathVariable("id") Long id){
		return cartService.findByCustomerIdAndStatusConfirmed(id);
	}
	@GetMapping("/customerId/StatusNew/{id}")
	public List<CartDto> findByCustomerIdAndStatusNew(@PathVariable("id") Long id){
		return cartService.findByCustomerIdAndStatusNew(id);
	}

	@GetMapping("/orderId/{id}")
	public CartDto findByOrderId(@PathVariable("id") Long id){
		return cartService.findByOrderId(id);
	}


	@GetMapping("/{id}")
	public CartDto findById(@PathVariable("id") Long  id) {
		return  cartService.findById(id);
	}
	
    @PostMapping()
	public CartDto create(@RequestBody Long customerId) {
		return cartService.create(customerId);
	}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id) {
    	this.cartService.delete(id);
    }

    @PutMapping()
    public void updateStatus(@RequestBody Long idCart){
		this.cartService.updateCartStatus(idCart);
	}
    
	
}
