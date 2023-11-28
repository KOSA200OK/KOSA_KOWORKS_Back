package com.my.address.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.address.dto.AddressMemberDTO;
import com.my.address.service.AddressService;

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
	public List<AddressMemberDTO> findAll() {
		return addressService.findAll();
	}
}
