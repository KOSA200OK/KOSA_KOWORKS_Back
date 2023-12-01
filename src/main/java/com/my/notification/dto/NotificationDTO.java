package com.my.notification.dto;

import java.util.Date;

import com.my.notification.entity.NotificationEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotificationDTO {

	private Integer id; 		// 알림번호
	
	private String memberId;			// 알림받는 사원 번호
	
	private String type;				// 알림타입
	
	private String content;				// 내용
	
	private Date createdAt;				// 알림생성일시
	
	private Integer status;				// 상태 0: 확인, 1: 미확인
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public static class Response {
		String id;
		String memberId;
		String content;
		String type;
		String createdAt;
		public static Response createResponse(NotificationEntity notify) {
			return Response.builder()
					.content(notify.getContent())
					.id(notify.getId().toString())
					.memberId(notify.getMemberEntity().getName())
					.createdAt(notify.getCreatedAt().toString())
					.build();
		}
	}
	
} // end class
