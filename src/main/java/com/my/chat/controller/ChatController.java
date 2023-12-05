package com.my.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.my.chat.dto.ChatMessage;
import com.my.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {
	private final ChatService chatService;

	/**
<<<<<<< HEAD
	 * websocket에서 "/pub/chat/message"로 들어오는 메시징을 처리
	 *
=======
	 * websocket에서 "/pub/chat/message"로 들어오는 메시징을 처리 해당 
	 * 메시지를 다시 발행하여 채팅방의 다른 사용자들에게 전달
>>>>>>> 27d13b7ec9921f895d8eb00725662cbbf3f2c60f
	 */
	@CrossOrigin(origins = "http://localhost:5173")
	@MessageMapping("/chat/message")
	// ws.send()에서 JSON.stringfy를 통해 자동으로 ChatMessage 형태로 반환된다.
	public void message(ChatMessage chatMessage) {
		// 등록이 안되어있다면 등록
		if (!chatService.isRegisteredChannelTopic(chatMessage.getRoomId())) {
			chatService.registerChannelTopic(chatMessage.getRoomId());
		}
		// 만약 메시지의 타입이 입장 메시지라면, 메시지 내용을 변경
		if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
			chatMessage.setMessage(chatMessage.getSender() + "님이입장하셨습니다.");
		}
		// 변경된 메시지를 ChatService를 통해 발행.
		// 발행된 메시지는 Redis를 통해 다른 서버 및 클라이언트에게 전달, 채팅내역을 저장
		chatService.publish(chatMessage);
	}
}
