package com.my.chat.dto;

import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
//https://github.com/gks930620/chatting3_redis_pubsub
@Getter
@Setter
public class ChatMessage {
	// 메시지 타입 : 입장, 채팅, 나감
	public enum MessageType {
		ENTER, TALK, QUIT,
=======
@Getter
@Setter
//websocket 공부용으로 추가한 class
public class ChatMessage {
	// 메시지 타입 : 입장, 채팅, 나감
	public enum MessageType {
		ENTER, TALK, QUIT
>>>>>>> 7e839bc75c2e09bbd49900471016fef47452a3f4
	}

	private MessageType type; // 메시지 타입
	private String roomId; // 방번호
	private String sender; // 메시지 보낸사람
	private String message; // 메시지
}
