package com.my.address.service;

import java.util.List;

import com.my.address.dto.AddressMemberDTO;
import com.my.exception.FindException;

public interface AddressService {
	public List<AddressMemberDTO> findAll() throws FindException;

	public List<AddressMemberDTO> findPagedMembers(int page, int size);
}
