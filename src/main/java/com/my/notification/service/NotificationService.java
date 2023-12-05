package com.my.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;

public interface NotificationService {

	public SseEmitter subscribe(String userName, String lastEventId);
	
	public void send(MemberEntity id, NotificationEntity.NotificationType notificationType, String Content);
	
}
