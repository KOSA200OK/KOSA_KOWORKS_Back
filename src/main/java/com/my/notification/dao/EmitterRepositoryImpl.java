package com.my.notification.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

	Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();		// 발신자 관리를 위한 emitter 객체 생성
	Map<String, Object> eventCache = new ConcurrentHashMap<>();			// 이벤트 캐시 관리를 위한 cache객체 생성
	
	// key : emitterId, value : sseEmitter를 맵에 저장
	@Override
	public SseEmitter save(String emitterId, SseEmitter sseEmitter) {  // emitter 저장
		emitters.put(emitterId, sseEmitter);	// emitters에 매개변수로 받음 emitterId와 sseEmiiter를 key와 value 값으로 저장
		return sseEmitter;						// 메서드가 호출될 때 sseEmitter를 반환하여 외부에서 사용할 수 있도록 return
	}

	// key : eventCacheId, value : event를 맵에 저장
	@Override
	public void saveEventCache(String eventCacheId, Object event ) {		// 이벤트를 저장
		eventCache.put(eventCacheId, event);
	}
	
	// memberId로 시작하는 키를 가진 emitters 맵의 값들을 memberId와 그에 해당하는 SseEMitter 값으로 이루어진 맵을 변환하여 반환
	@Override
	public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId) { // memberId에 관련된 모든 이벤트를 찾음
		
		return emitters.entrySet()				// entrySet을 사용해서 emitters의 key,value를 가져옴			
						.stream()				// 가져온 entry들을 Stream으로 변환함
						.filter(entry -> entry.getKey().startsWith(memberId))
						// filter연산을 사용해서 stream의 각 entry에 조건을 적용(emitters맵의 key들 중 memberId로 시작하는 것들을 필터링 )
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
						// collect 연산을 사용해서 필터링된 entry들을 다시 맵으로 변환한다, collectors.toMap을 이용하여 새로운 맵 생성
		// entrySet을 가져와서 그대로 사용할 수 있지만, 유지보수, 기능 확장을 위해 데이터를 변환하여 사용
		
	} // findAllEmitterStartWithByMemberId
	
	// memberId로 시작하는 키를 가진 eventCache 맵의 값들을 memberId와 그에 해당하는 Object 값으로 이루어진 맵을 변환하여 반환
	@Override
	public Map<String, Object> findAllEventCacheStartWithByMemberId(String memerId) {	
		
		return eventCache.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith(memerId))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
	} // findAllCacheStartWithByMemberId
	
	@Override
	public void deleteById(String id) { // emitter를 지움
		emitters.remove(id);
	}
	
	@Override
	public void deleteAllEmitterStartWithId(String memberId) { // 해당 회원과 관련된 모든 emitter를 지움
		emitters.forEach(
				(key, emitter) -> {
					if (key.startsWith(memberId)) {
						emitters.remove(key);
					} // if
				}
		); // forEach
	} // deleteAllEmitterStartWithId
	
	@Override
	public void deleteAllEventCacheStartWithId(String memberId) { // 해당 회원과 관련된 모든 cache를 지움
		emitters.forEach(
				(key, emitter) -> {
					if (key.startsWith(memberId)) {
						eventCache.remove(key);
					} // if
				}
		); // forEach
	}
	
	
}
