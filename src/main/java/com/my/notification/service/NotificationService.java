package com.my.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;

public interface NotificationService {

	// 구독
	public SseEmitter subscribe(String userName, String lastEventId);
	
	// 수신자에게 메시지 보내기
	public void send(MemberEntity id, NotificationEntity.NotificationType notificationType, String Content);
	
}
