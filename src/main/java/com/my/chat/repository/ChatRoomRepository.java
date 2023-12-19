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
	private final RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;
	// 레디스를 이용한 채팅이 있는 project를 실행할 때 실행됨
	@PostConstruct
	private void init() {
		System.out.println("ChatRoomRepository- init " + opsHashChatRoom);
		opsHashChatRoom = redisTemplate.opsForHash();
	}

	public List<ChatRoom> findAllRoom() {
		System.out.println("ChatRoomRepository- findAllRoom " + opsHashChatRoom.values(CHAT_ROOMS_KEY));
		return opsHashChatRoom.values(CHAT_ROOMS_KEY);
	}

	public ChatRoom findRoomById(String roomId) {
		System.out.println("ChatRoomRepository-findRoomById " + opsHashChatRoom.get(CHAT_ROOMS_KEY, roomId));
		return opsHashChatRoom.get(CHAT_ROOMS_KEY, roomId);
	}

	/**
	 * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
	 */
	public ChatRoom createChatRoom(String name) {
		ChatRoom chatRoom = ChatRoom.create(name);
		// redis에 저장
		opsHashChatRoom.put(CHAT_ROOMS_KEY, chatRoom.getRoomId(), chatRoom);
		System.out.println("ChatRoomRepository-createChatRoom " + chatRoom);
		return chatRoom;
	}
}
