package com.my.login.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.my.member.entity.MemberEntity;

@RestController
public class LoginController {

	@Autowired
	private LoginService service;

	@CrossOrigin(origins = "http://localhost:5173")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session)
			throws FindException {
		MemberEntity member = service.findByMemberId(loginRequestDTO);

		if (member != null) {
			session.setAttribute("memberId", member.getId());
			session.setAttribute("departmentId", member.getDepartment().getId());

			// session에 있는 id값 확인
			String memberId = (String) session.getAttribute("memberId");
			Long departmentId = (Long) session.getAttribute("departmentId");
			if (memberId != null) {
				System.out.println("로그인 상태입니다. memberId: " + memberId + " 부서 ID: " + departmentId);
			} else {
				System.out.println("사용자가 로그인하지 않은 상태입니다.");
			}
			Map<String, Object> response = new HashMap<>();
			response.put("memberId", memberId);
			response.put("departmentId", departmentId);

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.removeAttribute("memberId");
		session.removeAttribute("departmentId");
		return ResponseEntity.ok("로그아웃 성공");
	}
}
