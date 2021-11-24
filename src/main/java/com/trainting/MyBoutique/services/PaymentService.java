package com.trainting.MyBoutique.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainting.MyBoutique.dto.PaymentDto;
import com.trainting.MyBoutique.persistence.Order;
import com.trainting.MyBoutique.persistence.Payment;
import com.trainting.MyBoutique.persistence.enums.PaymentStatus;
import com.trainting.MyBoutique.repository.OrderRepository;
import com.trainting.MyBoutique.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {
	
	@Resource
	private OrderRepository orderRepository;
	@Resource
	private PaymentRepository paymentRepository;
	
	public List<PaymentDto> findAll() {
		log.debug("Request to get all Payments");
		return this.paymentRepository.findAll()
		.stream()
		.map(PaymentService::mapToDto)
		.collect(Collectors.toList());
		
	}
	
	@Transactional(readOnly = true)
	public PaymentDto findById(Long id) {
	log.debug("Request to get Payment : {}", id);
	return this.paymentRepository.findById(id).map(PaymentService::mapToDto).orElse(null);
	}
	
	public PaymentDto create(PaymentDto paymentDto) {
		Order order = this.orderRepository.findById(paymentDto.getOrderId()).orElse(null);
		return mapToDto(paymentRepository.save(new
				Payment(
						PaymentStatus.valueOf(paymentDto.getStatus()),
						order
						)))
				;
		
	}
	public void delete(Long id) {
		log.debug("Request to delete Payment : {}", id);
		this.paymentRepository.deleteById(id);
		}

	public static PaymentDto mapToDto(Payment payment) {
		
		if (payment!=null) {
			return new PaymentDto(payment.getId(),

					payment.getStatus().name(),
					payment.getOrder().getId());
			
		} return null;
	}
}
