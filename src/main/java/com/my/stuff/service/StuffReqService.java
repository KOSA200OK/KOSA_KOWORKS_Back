package com.my.stuff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.stuff.repository.StuffReqRepository;

@Service
public class StuffReqService {
	@Autowired
	private StuffReqRepository stuffReqRepository;
	
	
	
}
