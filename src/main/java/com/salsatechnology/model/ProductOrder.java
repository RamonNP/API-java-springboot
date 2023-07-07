package com.salsatechnology.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_order")
public class ProductOrder {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userName;
	
	@Enumerated(EnumType.STRING)	
	private ProductType productType;
	
	private Integer timeHour;
	private Long productValue;
	private Long productTotal;
	private Long userAmount;
	
	
	
}
