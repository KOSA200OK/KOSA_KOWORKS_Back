package com.my.login.service;

import com.my.exception.FindException;
import com.my.login.dto.LoginRequestDTO;
import com.my.member.entity.MemberEntity;

public interface LoginService {
	public MemberEntity findByMemberId(LoginRequestDTO loginRequestDTO) throws FindException;
}
