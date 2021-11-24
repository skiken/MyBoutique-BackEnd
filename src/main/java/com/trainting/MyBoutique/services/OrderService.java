package com.trainting.MyBoutique.services;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import com.trainting.MyBoutique.persistence.Payment;
import com.trainting.MyBoutique.persistence.enums.PaymentStatus;
import com.trainting.MyBoutique.persistence.enums.ProductStatus;
import com.trainting.MyBoutique.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainting.MyBoutique.dto.OrderDto;
import com.trainting.MyBoutique.persistence.Address;
import com.trainting.MyBoutique.persistence.Cart;
import com.trainting.MyBoutique.persistence.Order;
import com.trainting.MyBoutique.persistence.enums.OrderStatus;

import com.trainting.MyBoutique.repository.OrderRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderService {
	
	@Resource
	private OrderRepository orderRepository;

	@Resource
   private PaymentRepository payementReposiyory;

	
	public List<OrderDto> findAll() {
		log.debug("request to find all orders");
		return this.orderRepository.findAll().stream().map(OrderService::mapToDto).collect(Collectors.toList());
	}
	public List<OrderDto> findAllConfirmedOrders() {
		log.debug("request to find all orders");
		return this.orderRepository.findByOrderStatusOrderByShipped(OrderStatus.CONFIRMED).stream().map(OrderService::mapToDto).collect(Collectors.toList());
	}

	public List<OrderDto> findAllOrdersInDelivery() {
		log.debug("request to find all orders");
		return this.orderRepository.findByOrderStatusOrderByShipped(OrderStatus.IN_DELIVERING).stream().map(OrderService::mapToDto).collect(Collectors.toList());
	}

	
	public OrderDto findOrderById(Long id) {
		log.debug("request to find order :{}",id);
		return this.orderRepository.findById(id).map(OrderService::mapToDto).orElse(null);
		}
	
	public OrderDto create(OrderDto orderDto) {
		log.debug("request to create order:{}",orderDto);
		return mapToDto(this.orderRepository.save(
				new Order(
				BigDecimal.ZERO,
				OrderStatus.CREATION,				
				null,
				new Address(orderDto.getShipmentAddress().getAddress(),
                orderDto.getShipmentAddress().getCity(), orderDto.getShipmentAddress().getPostcode(),orderDto.getShipmentAddress().getCountry()),
				null,
				null,
				Collections.emptySet()
			
				)
				));
		
	}
	public Order create(Cart cart) {
		log.debug("Request to create Order with a Cart : {}", cart);
		Payment payment=new Payment(PaymentStatus.CREATION,null);
		return this.orderRepository.save(
		new Order(
		BigDecimal.ZERO,
		OrderStatus.CREATION,
		null,
		null,
				null,
		cart,
		Collections.emptySet()
		));




		}
	
	public void delete (Long id) {
		log.debug("request to delete order {}",id);
		orderRepository.deleteById(id);
	}

	public static OrderDto mapToDto(Order order) {
		if(order!=null) {
			return new OrderDto(order.getId(),
					order.getTotalPrice(),
					order.getOrderStatus().name(),
					order.getShipped(),
					order.getShipmentAddress(),
					null,
					order.getOrderItems().stream().map(OrderItemService::mapToDto).collect(Collectors.toSet()));
		}return null;
	}

	public void updateOrder(Long idOrder, OrderDto orderDto){
		Optional<Order> order =this.orderRepository.findById(idOrder);
		Order newOrder= order.get();
		newOrder.setTotalPrice(orderDto.getTotalPrice()!=null?orderDto.getTotalPrice():newOrder.getTotalPrice());
		newOrder.setShipped(orderDto.getShipped()!=null?orderDto.getShipped():newOrder.getShipped());
		newOrder.setPayment(orderDto.getPaymentId()!=null?payementReposiyory.findById(orderDto.getPaymentId()).get():newOrder.getPayment());
		newOrder.setOrderStatus(orderDto.getStatus()!=null?OrderStatus.valueOf(orderDto.getStatus()):newOrder.getOrderStatus());
		orderRepository.save(newOrder);

	}
}
