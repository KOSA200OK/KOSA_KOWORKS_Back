package com.my.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.FindException;
import com.my.login.dto.LoginRequestDTO;
import com.my.login.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService service;

	@CrossOrigin(origins = "http://localhost:5173")
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) throws FindException {
		if (service.findByMemberId(loginRequestDTO)) {
			session.setAttribute("memberId", loginRequestDTO.getId());
			return ResponseEntity.ok("로그인 성공");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
		}
	}

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.removeAttribute("memberId");
		return ResponseEntity.ok("로그아웃 성공");
	}
}
