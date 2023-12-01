package com.my.attendance.dto;

import com.my.member.dto.MemberDTO;
import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttendanceDTO {	

	private Integer id; 		// 근태번호
	
	private String memberId;			// 회원아이디
	
	private String attendanceDate;		// 일자
	
	private String startTime;			// 출근시간
	
	private String endTime;				// 퇴근시간
	
	private Integer status;				// 상태 0: 출석, 1: 지각, 2: 결근, 3: 조퇴
	
	private MemberDTO member;			// 조회
	
	private MemberEntity memberEntity;
	
} // end class	
