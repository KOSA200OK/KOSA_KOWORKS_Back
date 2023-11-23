package com.my.department.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "department")
public class DepartmentEntity {
	@Id
	private Long departmentId;

	@Column(nullable = false)
	private String name;
}
