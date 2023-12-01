package com.my.notification.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.my.member.entity.MemberEntity;
import com.my.notification.dao.EmitterRepository;
import com.my.notification.dao.NotificationRepository;
import com.my.notification.dto.NotificationDTO;
import com.my.notification.entity.NotificationEntity;
import com.my.notification.entity.NotificationEntity.NotificationType;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    // SSE 연결 지속 시간 설정

	@Autowired
	private EmitterRepository emitterRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public SseEmitter subscribe(String id, String lastEventId) {
		
		String emitterId = makeTimeIncludId(id);
		SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
		
		emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
		emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
		
		// 에러를 방지하기 위한 더미 이벤트 전송
		String eventId = makeTimeIncludId(id);
		sendNotification(emitter, eventId, emitterId, "EventStream Created. [userid = " + id + "]");
		
		// 클라이언트가 미수신한 event 목록이 존재할 경우 전송하여 event 유실을 예방
		if(hasLostData(lastEventId)) {
			sendLostData(lastEventId, id, emitterId, emitter);
		}
		
		return emitter;
		
	} // subscribe

	private String makeTimeIncludId(String id) {
		
		return id + "_" + System.currentTimeMillis();
	}
	
	private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
		try {
			emitter.send(SseEmitter.event().id(eventId).name("sse").data(data));
		} catch(IOException exception) {
			emitterRepository.deleteById(emitterId);
		}
	}
	
	private boolean hasLostData(String lastEventId) {
		return !lastEventId.isEmpty();
	}
	
	private void sendLostData(String lastEventId, String id, String emitterId, SseEmitter emitter) {
		 Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(id));
	        eventCaches.entrySet().stream()
	                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
	                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
	}
 
	@Override
	public void send(MemberEntity memberEntity, NotificationType notificationType, String content) {
		 NotificationEntity notification = notificationRepository.save(createNotification(memberEntity, notificationType, content)); // (2-1)⠀
		
		 String recevier = memberEntity.getId();
		 String eventId = recevier + "_" + System.currentTimeMillis(); // (2-3)
	        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(recevier); // (2-4)
	        emitters.forEach( // (2-5)
	                (key, emitter) -> {
	                    emitterRepository.saveEventCache(key, notification);
	                    sendNotification(emitter, eventId, key, NotificationDTO.Response.createResponse(notification));
	                }
	        );
		
	}
	
//	@Override
//    public void sendToClient(SseEmitter emitter, String id, Object data) {
//        try {
//            emitter.send(SseEmitter.event()
//                                   .id(id)
//                                   .name("sse")
//                                   .data(data));
//        } catch (IOException exception) {
//            emitterRepository.deleteById(id);
//            throw new RuntimeException("연결 오류!");
//        }
//    }
	
    private NotificationEntity createNotification(MemberEntity id, NotificationEntity.NotificationType notificationType, String content) { // (7)
        return NotificationEntity.builder()
                .memberEntity(id)
                .notificationType(notificationType)
                .content(content)
                .build();
    }

}
