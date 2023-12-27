package com.my.member.dto;

import com.my.department.dto.DepartmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberDTO {
	private String id;
	private DepartmentDTO department;
	private String name;
	private String password;
	private String position;
	private String tel;
}
