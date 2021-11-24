package com.trainting.MyBoutique.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.Cart;
import com.trainting.MyBoutique.persistence.enums.CartStatus;




@Repository
public interface CartRepository  extends JpaRepository<Cart, Long>{

	List<Cart> findByCartStatus(CartStatus cartStatus);
	List<Cart> findByCustomerId(Long id);
	Optional<Cart> findByOrderId(Long id);
	List<Cart> findByCartStatusAndCustomerId(CartStatus cartStatus, Long customerId);
}
