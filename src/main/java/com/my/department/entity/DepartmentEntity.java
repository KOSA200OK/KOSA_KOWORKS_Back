package com.my.department.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "department")
public class DepartmentEntity {
	
	@Id
	private Long id = Long.valueOf(0L);

	@Column(nullable = false)
	private String name;
}
