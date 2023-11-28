package com.my.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.my.department.entity.DepartmentEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity {
	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String position;

	@Column(nullable = false, unique = true)
	private String tel;

	@Column(nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "departmentId")
	private DepartmentEntity department;
	
	
}
