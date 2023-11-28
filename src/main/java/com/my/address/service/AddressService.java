package com.my.address.service;

import java.util.List;

import com.my.address.dto.AddressMemberDTO;

public interface AddressService {
	List<AddressMemberDTO> findAll();
}
