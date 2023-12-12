package com.my.address.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.address.dto.AddressMemberDTO;
import com.my.address.service.AddressService;
import com.my.exception.FindException;

@RestController
@RequestMapping("/address")
public class AddressController {
	private final AddressService addressService;

	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/members") // /address/members에 대한 매핑 추가
	public List<AddressMemberDTO> findAll() throws FindException {
		return addressService.findAll();
	}

	//
	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/members/{paged}")
	public List<AddressMemberDTO> findPagedMembers(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) throws FindException {
		// page와 size를 이용하여 페이징된 주소록을 조회하는 서비스 메서드 호출
		return addressService.findPagedMembers(page, size);
	}
}
