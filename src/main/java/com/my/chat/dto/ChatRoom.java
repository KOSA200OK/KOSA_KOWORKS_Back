package com.my.chat.dto;

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
	}
}
