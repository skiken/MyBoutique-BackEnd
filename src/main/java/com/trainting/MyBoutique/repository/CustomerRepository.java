package com.trainting.MyBoutique.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
	
	List<Customer> findAllByEnabled(Boolean enabled);
	List<Customer> findAllByRoles(String role);
	Customer findByEmail(String email);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String username);

	@Query("SELECT a.customer FROM Cart a where a.order.id=:id")
    Optional<Customer> findCustomerByOrderId(Long id);

	Optional<Customer> findByUsername(String username);
}
