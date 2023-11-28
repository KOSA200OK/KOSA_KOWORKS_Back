package com.my.login.service;

import com.my.login.dto.LoginRequestDTO;

public interface LoginService {
	boolean findByMemberId(LoginRequestDTO loginRequestDTO);
}
