package com.my.chat.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.my.chat.dto.ChatRoom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
	private static final String CHAT_ROOMS_KEY = "CHAT_ROOM";
	// Redis와 통신하기 위한 템플릿
	private final RedisTemplate<String, Object> redisTemplate;
	// Redis의 해시 데이터 구조를 조작하기 위한 인터페이스를 제공
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;

	@PostConstruct
	// @PostConstruct이 있는 init 메서드는 빈이 생성된 후 초기화 작업을 수행, 여기서는 opsHashChatRoom을 초기화
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
	}

	public List<ChatRoom> findAllRoom() {
		return opsHashChatRoom.values(CHAT_ROOMS_KEY);
	}

	public ChatRoom findRoomById(String roomId) {
		return opsHashChatRoom.get(CHAT_ROOMS_KEY, roomId);
	}

	/**
	 * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
	 */
	public ChatRoom createChatRoom(String name) {
		ChatRoom chatRoom = ChatRoom.create(name);
		// redis에 저장
		opsHashChatRoom.put(CHAT_ROOMS_KEY, chatRoom.getRoomId(), chatRoom);
		return chatRoom;
	}
}
