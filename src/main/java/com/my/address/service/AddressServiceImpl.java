package com.my.address.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.address.dto.AddressMemberDTO;
import com.my.member.entity.MemberEntity;
import com.my.member.repository.MemberRepository;

@Service
public class AddressServiceImpl implements AddressService {
	private final MemberRepository memberRepository;

	@Autowired
	public AddressServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public List<AddressMemberDTO> getAllMembers() {
		List<MemberEntity> members = memberRepository.findAll();
		return members.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private AddressMemberDTO convertToDTO(MemberEntity memberEntity) {
		AddressMemberDTO dto = new AddressMemberDTO();
		dto.setId(memberEntity.getId());
		dto.setName(memberEntity.getName());
		dto.setPosition(memberEntity.getPosition());
		dto.setTel(memberEntity.getTel());
		return dto;
	}
}
