package com.trainting.MyBoutique.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainting.MyBoutique.dto.OrderItemDto;
import com.trainting.MyBoutique.persistence.Order;
import com.trainting.MyBoutique.persistence.OrderItem;
import com.trainting.MyBoutique.persistence.Product;
import com.trainting.MyBoutique.repository.OrderItemRepository;
import com.trainting.MyBoutique.repository.OrderRepository;
import com.trainting.MyBoutique.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderItemService {
	
	@Resource
	private OrderItemRepository orderItemRepository;
	@Resource
	private OrderRepository orderRepository;
	@Resource
	private ProductRepository productRepository;
	
	public List<OrderItemDto> findAll() {
		log.debug("request to get all orderItems");
		return orderItemRepository.findAll().stream().map(OrderItemService::mapToDto).collect(Collectors.toList());
		
	}
	
	public OrderItemDto findById(Long id) {
		log.debug("request to get orderItem : {}",id);
		return orderItemRepository.findById(id).map(OrderItemService::mapToDto).orElse(null);
		
	}

	public List<OrderItemDto> findByIdOrder(Long id) {
		log.debug("request to get orderItem by order: {}",id);
		return orderItemRepository.findByOrderId(id).stream().map(OrderItemService::mapToDto).collect(Collectors.toList());

	}
	
	public OrderItemDto create(OrderItemDto orderItemDto) {
		log.debug("request to create orderItem:{}",orderItemDto);
		Order order= this.orderRepository.findById(orderItemDto.getOrderId()).orElseThrow(()->new IllegalStateException("order not found"));
		Product product= this.productRepository.findById(orderItemDto.getProductId()).orElseThrow(()->new IllegalStateException("product not found"));
		return mapToDto(this.orderItemRepository.save(
				new OrderItem(
				orderItemDto.getQuantity(),
				product,
				order
				)
				));	
		}
		

	public void delete(Long id) {
		log.debug("request to delete orderItem: {}",id);
		orderItemRepository.deleteById(id);
	}

	public void deleteByOrderId(Long id) {
		log.debug("request to delete orderItem by orderId: {}",id);
		List<OrderItem> listOrderItem=orderItemRepository.findByOrderId(id).stream().collect(Collectors.toList());
		listOrderItem.forEach(orderItem -> {
			orderItemRepository.deleteById(orderItem.getId());
		});

	}

	public OrderItemDto update(Long id,OrderItemDto orderItemDto){
		OrderItem orderItem=this.orderItemRepository.findById(id).get();
		orderItem.setQuantity(orderItemDto.getQuantity());
		return mapToDto(orderItemRepository.save(orderItem));

	}

	public static OrderItemDto mapToDto(OrderItem orderItem) {
		if(orderItem!=null) {
			return new OrderItemDto(orderItem.getId(),
					orderItem.getQuantity(),
					orderItem.getProduct().getId(),
					orderItem.getOrder().getId());
		}
		return null;
	}
}
