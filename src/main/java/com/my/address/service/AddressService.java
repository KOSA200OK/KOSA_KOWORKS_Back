package com.my.address.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.my.address.dto.AddressMemberDTO;

@Service
public interface AddressService {

	List<AddressMemberDTO> getAllMembers();
}
