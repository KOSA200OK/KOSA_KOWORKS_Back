package com.my.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.login.dto.LoginRequestDTO;
import com.my.member.entity.MemberEntity;
import com.my.member.repository.MemberRepository;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private MemberRepository memberRepository;

	public boolean authenticateMember(LoginRequestDTO loginRequest) {
		MemberEntity member = memberRepository.findByMemberId(loginRequest.getId());

		if (member != null && member.getPassword().equals(loginRequest.getPassword())) {
			return true;
		}
		return false;
	}
}
