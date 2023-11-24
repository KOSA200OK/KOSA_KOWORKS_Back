package com.my.notification.dto;

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
public class NotificationDTO {

	private Integer attendanceId; 		// 알림번호
	
	private Integer memberId;			// 알림받는 사원 번호
	
	private String type;				// 알림타입
	
	private String content;				// 내용
	
	private Date createdAt;				// 알림생성일시
	
	private Integer status;				// 상태 0: 확인, 1: 미확인
	
} // end class
