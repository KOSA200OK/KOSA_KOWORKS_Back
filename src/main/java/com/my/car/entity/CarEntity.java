package com.my.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter

@Entity
@DynamicInsert
@Table(name="car")
public class CarEntity {
	@Id
	@Column(length=30)
	private String id;
<<<<<<< HEAD
=======
	
	@Column(nullable=false)
	private String carNo;
>>>>>>> 0b20b505269e3940d1759f0c3c7c21e9bdb758ba
	
	@Column(nullable=false)
	private String carType;
	
	@Column(nullable=false, length=2)
	@ColumnDefault("0") 
	private Long status;
}
