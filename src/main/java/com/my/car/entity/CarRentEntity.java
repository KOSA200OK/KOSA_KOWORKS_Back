package com.my.car.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder

@Entity
@Table(name="car_rent")
@DynamicInsert
@SequenceGenerator(name="car_rent_seq_generator", sequenceName="car_rent_seq", initialValue=1, allocationSize=1)
public class CarRentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_rent_seq_generator")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="memberId",nullable=false)
	private MemberEntity member;
	
	@ManyToOne
	@JoinColumn(name="carId", nullable =false)
	private CarEntity car;
	
	@Column(nullable=false)
	@ColumnDefault(value = "SYSDATE")
	private Date reqDate;
	
	@Column(nullable=false)
	private Date startDate;
	
	@Column(nullable=false)
	private Date endDate;
	
	@Column(length=200)
	private String purpose;
	
	@Column(nullable=false, length=2)
	@ColumnDefault("0") 
	private Long status;
	
	@Column(length=200)
	private String reject;
	
	@Column(precision=12, scale=6)
	private BigDecimal latitude;
	
	@Column(precision=12, scale=6)
	private BigDecimal longitude;
	
	public void modifyCarRentStatus(Long status) {
		this.status = status;
	}

}
