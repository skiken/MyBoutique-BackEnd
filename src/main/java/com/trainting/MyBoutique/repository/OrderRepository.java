package com.trainting.MyBoutique.repository;

import com.trainting.MyBoutique.persistence.Cart;
import com.trainting.MyBoutique.persistence.enums.CartStatus;
import com.trainting.MyBoutique.persistence.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByOrderStatusOrderByShipped(OrderStatus orderstatus);

}
