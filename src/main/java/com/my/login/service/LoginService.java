package com.my.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.login.dto.LoginRequestDTO;

@Service
public class LoginService {

	@Autowired
	private MemberRepository memberRepository;

	public boolean authenticateMember(LoginRequestDTO loginRequest) {
		Member member = memberRepository.findByMemberId(loginRequest.getMemberId());

		if (member != null && member.getPassword().equals(loginRequest.getPassword())) {
			return true;
		}
		return false;
	}
}
