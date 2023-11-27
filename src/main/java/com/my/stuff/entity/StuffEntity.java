package com.my.stuff.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "stuff")
class StuffEntity {
	@Id
	@Column(name = "stuffId")
	private int stuffId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "quantity")
	private int quantity;
	
}
