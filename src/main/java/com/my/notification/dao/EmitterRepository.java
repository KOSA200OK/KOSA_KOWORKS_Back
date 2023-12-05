package com.my.notification.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {
	
	// emitter 저장
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    
    // event 저장
    void saveEventCache(String emitterId, Object event);
    
    //// memberId로 시작하는 키를 가진 emitters 맵의 값들을 memberId와 그에 해당하는 SseEMitter 값으로 이루어진 맵을 변환하여 반환
    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);
    
    // memberId로 시작하는 키를 가진 eventCache 맵의 값들을 memberId와 그에 해당하는 Object 값으로 이루어진 맵을 변환하여 반환
    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);
    
    // emitter 삭제
    void deleteById(String id);
    
    // 해당 회원과 관련된 모든 emitter 삭제
    void deleteAllEmitterStartWithId(String memberId);
    
    // 해당 회원과 관련된 모든 event 삭제
    void deleteAllEventCacheStartWithId(String memberId);
    
}	// end class
