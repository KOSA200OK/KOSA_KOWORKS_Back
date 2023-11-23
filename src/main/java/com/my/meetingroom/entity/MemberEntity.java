package com.my.meetingroom.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="member")
public class MemberEntity{
	
	@Id
	private Long memberId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String position;

	@Column(nullable = false, unique = true)
	private String tel;

	@ManyToOne
	@JoinColumn(name = "departmentId")
	private DepartmentEntity department;
	
//	@OneToMany(
//			fetch = FetchType.EAGER
//			,
//			cascade = CascadeType.ALL
//			,
//			mappedBy="id.memberId"
//			)
//	@JoinColumn(name="memberId")
//	private ParticipantsEntity participants;
}
