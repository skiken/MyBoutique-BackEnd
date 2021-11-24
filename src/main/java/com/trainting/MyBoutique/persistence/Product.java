package com.trainting.MyBoutique.persistence;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.trainting.MyBoutique.persistence.abstracts.AbstractEntity;
import com.trainting.MyBoutique.persistence.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor()
@ToString
@Entity
@Table(name="product")
public class Product extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name="name",nullable=false)
	private String name;
	
	@NotNull
	@Column(name="description", nullable = false)

	private String description;

	@NotNull
	@Column(name="imgPath", nullable = false)
	private String imgPath;
	@NotNull
	@Column(name="price",nullable = false, scale=2, precision = 10)
	private BigDecimal price;

	@NotNull
	@Column(name="quantity",nullable=false)
	private Integer quantity;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="status",nullable=false)
	private ProductStatus status;
	
	@Column(name = "sales_counter")
	private Integer salesCounter;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude private Category category;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private Set<Review> setReview= new HashSet<>();
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private Set<OrderItem> setOrderItems= new HashSet<>();

}
