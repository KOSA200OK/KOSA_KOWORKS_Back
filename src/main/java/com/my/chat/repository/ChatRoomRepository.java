package com.my.chat.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.my.chat.dto.ChatRoom;

import lombok.RequiredArgsConstructor;

//https://github.com/gks930620/chatting3_redis_pubsub
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
	private static final String CHAT_ROOMS_KEY = "CHAT_ROOM";
	private final RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;

	@PostConstruct
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
		opsHashChatRoom.put(CHAT_ROOMS_KEY, chatRoom.getRoomId(), chatRoom); // redis에 저장 .
		return chatRoom;
	}
}
