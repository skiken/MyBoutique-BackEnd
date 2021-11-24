package com.trainting.MyBoutique.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.trainting.MyBoutique.persistence.abstracts.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "quantity", nullable = false)
	private Long quantity;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude  private Product product;

	@ManyToOne
	@EqualsAndHashCode.Exclude  private Order order;

}
