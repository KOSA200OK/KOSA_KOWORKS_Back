package com.my.notification.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.my.exception.RemoveException;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.member.entity.MemberEntity;
import com.my.notification.dto.NotificationDTO.Response;
import com.my.notification.entity.NotificationEntity;

public interface NotificationService {

	// 구독
	public SseEmitter subscribe(String userName, String lastEventId);
	
	// 수신자에게 메시지 보내기
	public void send(MemberEntity id, NotificationEntity.NotificationType notificationType, String Content);
	
	// 회의실 참여자들에게 메시지 보내기
	public void sendToParticipants(List<ParticipantsEntity> participants, NotificationEntity.NotificationType notificationType, String Content);
	
	// 알림 조회
	@Query("SELECT n FROM NotificationEntity n WHERE n.memberEntity.id = :receiverId")
	public List<Response> findAllByMemberId(String MemberId);
	
	// 미팅 참여자 알림 조회
	
	// 알림 삭제
	public void deleteNotification(int id) throws RemoveException;

} // end class
