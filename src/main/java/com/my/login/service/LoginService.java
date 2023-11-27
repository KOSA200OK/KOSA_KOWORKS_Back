package com.my.login.service;

import org.springframework.stereotype.Service;

import com.my.login.dto.LoginRequestDTO;

@Service
public interface LoginService {

	boolean authenticateMember(LoginRequestDTO loginRequestDTO);
}
