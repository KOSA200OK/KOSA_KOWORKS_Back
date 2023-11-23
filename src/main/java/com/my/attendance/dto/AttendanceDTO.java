package com.my.attendance.dto;

import java.util.Date;

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

	private Integer attendanceId; 		// 근태번호
	
	private Integer memberId;			// 회원아이디
	
	private Date attendanceDate;		// 일자
	
	private Date startTime;				// 출근시간
	
	private Date endTime;				// 퇴근시간
	
	private Integer status;				// 상태 0: 출석, 1: 지각, 2: 결근, 3: 조퇴
	
} // end class	
