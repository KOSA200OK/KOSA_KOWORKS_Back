package com.my.chat.dto;

import lombok.Getter;
import lombok.Setter;

//https://github.com/gks930620/chatting3_redis_pubsub
@Getter
@Setter
public class ChatMessage {
	// 메시지 타입 : 입장, 채팅, 나감
	public enum MessageType {
		ENTER, TALK, QUIT,

	}

	private MessageType type; // 메시지 타입
	private String roomId; // 방번호
	private String sender; // 메시지 보낸사람
	private String message; // 메시지
}
