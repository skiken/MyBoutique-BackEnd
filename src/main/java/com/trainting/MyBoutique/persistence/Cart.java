package com.trainting.MyBoutique.persistence;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import com.trainting.MyBoutique.persistence.abstracts.AbstractEntity;
import com.trainting.MyBoutique.persistence.enums.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) 
@Entity 
@Table(name = "cart") 
public class Cart extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private CartStatus cartStatus;
	
	@OneToOne
	@JoinColumn(unique = true)
	private Order order;
	
	@ManyToOne
	private Customer customer;

	public Cart(Customer customer) {
		this.customer = customer;
		this.cartStatus = CartStatus.NEW;
		}
 
}
