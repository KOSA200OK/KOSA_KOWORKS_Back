package com.my.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.my.chat.dto.ChatMessage;
import com.my.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

//https://github.com/gks930620/chatting3_redis_pubsub
@RequiredArgsConstructor
@Controller
public class ChatController {
	private final ChatService chatService;

	/**
	 * websocket에서 "/pub/chat/message"로 들어오는 메시징을 처리
	 *
	 */
	@MessageMapping("/chat/message")
	// ws.send()에서 JSON.stringfy를 통해 자동으로 ChatMessage 형태로 반환된다.
	public void message(ChatMessage chatMessage) {
		// 등록이 안되어있다면 등록
		if (!chatService.isRegisteredChannelTopic(chatMessage.getRoomId())) {
			chatService.registerChannelTopic(chatMessage.getRoomId());
		}
		if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
			chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
		}
		chatService.publish(chatMessage);
	}
}
