package com.my.chat.dto;

<<<<<<< HEAD
import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

//https://github.com/gks930620/chatting3_redis_pubsub
@Getter
@Setter
public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;

	private String roomId;
	private String name;

	public static ChatRoom create(String name) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.name = name;
		return chatRoom;
=======
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Builder;
import lombok.Getter;

@Getter
//websocket 공부용으로 추가한 class
public class ChatRoom {
	private String roomId;
	private String name;
	private Set<WebSocketSession> sessions = new HashSet<>();

	@Builder
	public ChatRoom(String roomId, String name) {
		this.roomId = roomId;
		this.name = name;
>>>>>>> 7e839bc75c2e09bbd49900471016fef47452a3f4
	}
}
