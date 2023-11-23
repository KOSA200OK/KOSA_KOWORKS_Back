package com.my.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.my.login.dto.LoginRequest;

public class LoginController {
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		// 로그인 성공 시 세션에 사용자 ID 저장
		session.setAttribute("memberId", loginRequest.getMemberId());
		return ResponseEntity.ok("Login successful");
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		// 세션에서 사용자 ID 삭제
		session.removeAttribute("memberId");
		return ResponseEntity.ok("Logout successful");
	}
}
