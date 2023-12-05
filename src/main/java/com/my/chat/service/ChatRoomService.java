package com.my.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.my.chat.dto.ChatRoom;
import com.my.exception.FindException;

public interface ChatRoomService {
	public List<ChatRoom> findAll() throws FindException;
}
