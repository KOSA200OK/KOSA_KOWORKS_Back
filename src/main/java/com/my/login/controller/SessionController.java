package com.my.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

public class SessionController {
	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/session")
	public boolean findSession(HttpServletRequest request) {
		// 세션을 확인하여 로그인 상태 반환
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("memberId") != null;
	}
}
