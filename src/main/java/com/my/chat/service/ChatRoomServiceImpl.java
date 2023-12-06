package com.my.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.chat.dto.ChatRoom;
import com.my.chat.repository.ChatRoomRepository;
import com.my.exception.FindException;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;

	public List<ChatRoom> findAll() throws FindException {
		List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
		if (chatRooms.isEmpty()) {
			throw new FindException("방이 없습니다");
		}
		return chatRooms;
	}
}
