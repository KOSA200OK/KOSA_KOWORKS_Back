package com.my.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberDTO {
	private Long id;
	private Long departmentId;
	private String name;
	private String password;
	private String position;
	private String tel;
}
