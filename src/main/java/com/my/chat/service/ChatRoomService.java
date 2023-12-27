package com.my.chat.service;

import java.util.List;

import com.my.chat.dto.ChatRoom;
import com.my.exception.FindException;

public interface ChatRoomService {
	public List<ChatRoom> findAll() throws FindException;
}
