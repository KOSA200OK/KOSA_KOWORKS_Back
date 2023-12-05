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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
	
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    // SSE 연결 지속 시간 설정

	@Autowired
	private EmitterRepository emitterRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
    public NotificationServiceImpl(EmitterRepository emitterRepository) {
        this.emitterRepository = emitterRepository;
    }
	
	@Override
	public SseEmitter subscribe(String id, String lastEventId) {
		
		log.warn("Notification ServiceImpl id: {}", id);
		log.warn("Notification ServiceImpl lastEventId: {}", id);
		
		String emitterId = makeTimeIncludId(id);
		// 클라이언트의 sse연결 요청에 응답하기 위해서는 SseEmitter 객체를 만들어 반환해주어야 한다.
		SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
		// key값을 emitterId, 기본 타임아웃으로 default_timeout을 설정한 emitter 객체 생성
		
		emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));	// emitter가 완료될 때 'emitterId'에 해당하는 데이터를 repository에서 삭제
		emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));		// emitter가 타임아웃될 때 'emitterId'에 해당하는 데이터를 repository에서 삭제
		
		// 에러를 방지하기 위한 더미 이벤트 전송
		String eventId = makeTimeIncludId(id);							 		
		sendNotification(emitter, eventId, emitterId, "EventStream Created. [userid = " + id + "]");
		
		// 클라이언트가 미수신한 event 목록이 존재할 경우 이벤트를 재전송하여 event 유실을 예방 -> hasLostDate함수 사용
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
 
	//  ===================== 리뷰어에게 알림 보내기 =======================
	
	@Override
	public void send(MemberEntity memberEntity, NotificationEntity.NotificationType notificationType, String content) {
		
		if(memberEntity == null) {
			log.warn("memberEntity가 null입니다");
			return;
		}
		
		// Notification 객체를 만들고, 현재 로그인 한 유저의 id값을 통해 SseEmitter를 모두 가져옴, 그 후 캐시에 저장해주고, 실제 데이터도 전송 해야한다
		 NotificationEntity notification = notificationRepository.save(createNotification(memberEntity, notificationType, content)); // (2-1)⠀
		
		 String receiver = memberEntity.getId();
		 String eventId = receiver + "_" + System.currentTimeMillis(); // (2-3)
		 
		 log.warn("NotificationServiceImpl send recevier : {}", receiver);
		 log.warn("NotificationServiceImpl send eventId : {}", eventId);
		 
		 // 로그인 한 유저의 SseEmitter 모두 가져오기
	     Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiver); // (2-4)
	     
	     log.warn("emitters : {}", emitters.getClass().getName());
	     
	     emitters.forEach( // (2-5)
	             (key, emitter) -> {
	            	 // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
	                 emitterRepository.saveEventCache(key, notification);
	                 // 데이터 전송
	                 sendNotification(emitter, eventId, key, NotificationDTO.Response.createResponse(notification));
	             }
	     );
		
	}
	
    private NotificationEntity createNotification(MemberEntity receiver, NotificationEntity.NotificationType notificationType, String content) { // (7)
        return NotificationEntity.builder()
                .memberEntity(receiver) // 수신자
                .notificationType(notificationType)
                .content(content)
                .build();
    }

}
