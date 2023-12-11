package com.my.car.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Builder @Setter

@Entity
@DynamicInsert
@Table(name="car")
public class CarEntity {
	@Id
	@Column(length=30)
	private String id;
	
	@Column(nullable=false)
	private String carNo;
	
	@Column(nullable=false)
	private String carType;
	
	@Column
	private String maxNum; 
	
	@Column(precision=12, scale=6)
	private BigDecimal latitude;
	
	@Column(precision=12, scale=6)
	private BigDecimal longitude;
	
//	@Column(nullable=false, length=2)
//	@ColumnDefault("0") 
//	private Long status;
	
//	public void modifyCarStatus(Long status) {
//		this.status = status;
//	}	
}
