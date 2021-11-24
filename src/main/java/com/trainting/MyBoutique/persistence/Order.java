package com.trainting.MyBoutique.persistence;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.trainting.MyBoutique.persistence.abstracts.AbstractEntity;
import com.trainting.MyBoutique.persistence.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@NotNull
@Column(name = "total_price", precision = 10, scale = 2, nullable = false)
private BigDecimal totalPrice;

@NotNull
@Enumerated(EnumType.STRING)
@Column(name = "status", nullable = false)
private OrderStatus orderStatus;

@Column(name = "shipped")
@DateTimeFormat(pattern = "dd.MM.yyyy")
private Date shipped;


@Embedded
private Address shipmentAddress;


@OneToOne
@JoinColumn(unique = true)
private Payment payment;


@OneToOne(mappedBy = "order")
@JsonIgnore
@EqualsAndHashCode.Exclude  private Cart  cart;


@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private Set<OrderItem> orderItems;


}