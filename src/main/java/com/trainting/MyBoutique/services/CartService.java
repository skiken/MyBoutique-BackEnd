package com.trainting.MyBoutique.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.trainting.MyBoutique.persistence.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainting.MyBoutique.dto.CartDto;
import com.trainting.MyBoutique.persistence.Cart;
import com.trainting.MyBoutique.persistence.Customer;
import com.trainting.MyBoutique.persistence.Order;
import com.trainting.MyBoutique.persistence.enums.CartStatus;
import com.trainting.MyBoutique.repository.CartRepository;
import com.trainting.MyBoutique.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

	@Resource
	private CartRepository cartRepository;
	@Resource
	private CustomerRepository customerRepository;
	@Resource
	private OrderService orderService;

	@Transactional(readOnly = true)
	public List<CartDto> findAll() {
		log.debug("request to get all carts");
		return this.cartRepository.findAll().stream().map(CartService::mapToDto).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	public List<CartDto> findActiveCart() {
		log.debug("request to find active cart");
		return this.cartRepository.findByCartStatus(CartStatus.NEW).stream().map(CartService::mapToDto)
				.collect(Collectors.toList());
	}

	public CartDto findById(Long id) {
		log.debug("request to find cart :{}", id);
		return this.cartRepository.findById(id).map(CartService::mapToDto).orElse(null);
	}

	public CartDto findByOrderId(Long id) {
		log.debug("request to find cart by orderId :{}", id);
		return this.cartRepository.findByOrderId(id).map(CartService::mapToDto).orElse(null);
	}

	public List<CartDto> findByCustomerIdAndStatusConfirmed(Long id) {
		log.debug("request to find cart by customer id :{}", id);
		return this.cartRepository.findByCartStatusAndCustomerId(CartStatus.CONFIRMED,id).stream().map(CartService::mapToDto)
				.collect(Collectors.toList());
	}
	public List<CartDto> findByCustomerIdAndStatusNew(Long id) {
		log.debug("request to find cart by customer id :{}", id);
		return this.cartRepository.findByCartStatusAndCustomerId(CartStatus.NEW,id).stream().map(CartService::mapToDto)
				.collect(Collectors.toList());
	}


	public CartDto create(Long customerId) {
		log.debug("request to create cart for customer:{}", customerId);
		if (this.getActiveCart(customerId) == null) {
			Customer customer = this.customerRepository.findById(customerId)
					.orElseThrow(() -> new IllegalStateException("The Customer does not exist!"));
			Cart cart = new Cart(CartStatus.NEW, null, customer);
			Order order = this.orderService.create(cart);
			cart.setOrder(order);

			return mapToDto(this.cartRepository.save(cart));
		} else {
			throw new IllegalStateException("There is already an active cart");
		}

	}

	public CartDto getActiveCart(Long customerId) {
		List<Cart> carts = this.cartRepository.findByCartStatusAndCustomerId(CartStatus.NEW, customerId);
		if (carts != null) {
			if (carts.size() == 1) {
				return mapToDto(carts.get(0));
			}
			if (carts.size() > 1) {
				throw new IllegalStateException("Many active carts detected !!!");
			}
		}
		return null;
	}

	public static CartDto mapToDto(Cart cart) {
		if (cart != null) {
			return new CartDto(cart.getId(), cart.getCartStatus().name(), cart.getOrder().getId(),
					CustomerService.mapToDto(cart.getCustomer()));
		}
		return null;
	}
	
	public void delete(Long id) {
		log.debug("Request to delete Cart : {}", id);
		Cart cart =this.cartRepository.findById(id).orElseThrow(()-> new IllegalStateException("cart not found"));
		cart.setCartStatus(CartStatus.CANCELED);
		this.cartRepository.delete(cart);
	}


	public void updateCartStatus(Long idCart){
		log.debug("Request to update Cart Status to Confirmed : {}", idCart);
		Cart newCart=this.cartRepository.findById(idCart).get();
		newCart.setCartStatus(CartStatus.CONFIRMED);
		this.cartRepository.save(newCart);
	}

}
