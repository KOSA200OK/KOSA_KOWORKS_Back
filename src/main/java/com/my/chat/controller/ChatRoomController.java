package com.my.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.chat.dto.ChatRoom;
import com.my.chat.repository.ChatRoomRepository;
import com.my.chat.service.ChatRoomService;
import com.my.exception.FindException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
@CrossOrigin(origins = "http://192.168.3.79:5173")
public class ChatRoomController {

	private final ChatRoomRepository chatRoomRepository;

	@Autowired
	private ChatRoomService chatRoomService;

	// 채팅 리스트 화면
	@GetMapping("/roomlist")
	public List<ChatRoom> rooms(Model model) throws FindException {
		System.out.println("rooms " + chatRoomService.findAll());
		return chatRoomService.findAll();
	}

	// 모든 채팅방 목록 반환
	@GetMapping("/rooms")
	@ResponseBody
	public List<ChatRoom> room() {
		System.out.println("room " + chatRoomRepository.findAllRoom());
		return chatRoomRepository.findAllRoom();
	}

	// 채팅방 생성
	@PostMapping("/room")
	@ResponseBody
	public ChatRoom createRoom(@RequestParam String name) {
		ChatRoom chatRoom = chatRoomRepository.createChatRoom(name);
//		System.out.println("createRoom " + chatRoom);
		return chatRoom;
	}

	// 특정 채팅방 조회 axios로 방 찾을 때의 uri. subscribe()의 uri랑 상관없음
	@GetMapping("/room/{roomId}")
	@ResponseBody
	public ChatRoom roomInfo(@PathVariable String roomId) {
//		System.out.println("roomInfo " + chatRoomRepository.findRoomById(roomId));
		return chatRoomRepository.findRoomById(roomId);
	}
}
