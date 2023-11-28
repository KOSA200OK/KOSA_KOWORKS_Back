package com.my.chat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.chat.dto.ChatRoom;
import com.my.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

//websocket 공부용으로 추가한 class
@RequiredArgsConstructor
@Controller
public class ChatController {
	private final ChatService chatService;

	@CrossOrigin(origins = "http://localhost:5173")
	@RequestMapping("/chat/list")
	public List<ChatRoom> chatList(Model model) {
		List<ChatRoom> roomList = chatService.findAllRoom();
		model.addAttribute("roomList", roomList);
		return roomList;
	}

	@CrossOrigin(origins = "http://localhost:5173")
	@PostMapping("/chat/createRoom") // 생성한 방으로 이동
	public ChatRoom createRoom(Model model, @RequestParam String name, String username) {
		ChatRoom room = chatService.createRoom(name);
		model.addAttribute("room", room);
		model.addAttribute("username", username);
		return room; // 만든사람이 첫번째로 입장
	}

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/chat/chatRoom")
	public ChatRoom chatRoom(Model model, @RequestParam String roomId) {
		ChatRoom room = chatService.findRoomById(roomId);
		model.addAttribute("room", room);
		return room;
	}
}
