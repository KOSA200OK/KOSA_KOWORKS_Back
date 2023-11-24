package com.my.address.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.address.dto.AddressMemberDTO;
import com.my.address.repository.AddressRepository;
import com.my.member.entity.MemberEntity;

@RestController
@RequestMapping("/address")
public class AddressController {
	private final AddressRepository addressRepository;

	@Autowired
	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@GetMapping
	public List<AddressMemberDTO> getAllEmployees() {
		// DB에서 데이터를 조회하고 DTO로 변환하여 반환
		List<MemberEntity> membersFromDB = addressRepository.findAll();
		List<AddressMemberDTO> membersDTO = convertToDTOList(membersFromDB);
		return membersDTO;
	}

	private List<AddressMemberDTO> convertToDTOList(List<MemberEntity> members) {
		// MemberEntity를 AddressMemberDTO로 변환하는 메서드
		// 원하는 필드만 선택해서 DTO로 만들면 됩니다.
		return members.stream().map(member -> {
			AddressMemberDTO dto = new AddressMemberDTO();
			dto.setMemberId(member.getMemberId());
			dto.setName(member.getName());
			dto.setDepartment(member.getDepartment().getName());
			return dto;
		}).collect(Collectors.toList());
	}
}
