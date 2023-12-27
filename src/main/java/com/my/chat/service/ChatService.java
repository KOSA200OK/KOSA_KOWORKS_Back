package com.my.chat.service;

import com.my.chat.dto.ChatMessage;

public interface ChatService {
	public boolean isRegisteredChannelTopic(String roomId);

	public void registerChannelTopic(String roomId);

	public void publish(ChatMessage chatMessage);
}
