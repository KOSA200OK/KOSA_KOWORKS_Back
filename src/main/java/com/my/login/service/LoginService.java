package com.my.login.service;

import com.my.exception.FindException;
import com.my.login.dto.LoginRequestDTO;

public interface LoginService {
	public boolean findByMemberId(LoginRequestDTO loginRequestDTO) throws FindException;
}
