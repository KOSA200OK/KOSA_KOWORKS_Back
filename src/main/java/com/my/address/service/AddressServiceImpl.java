package com.my.address.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	public List<AddressMemberDTO> findAll() {
		List<MemberEntity> members = memberRepository.findAll();
		return members.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private AddressMemberDTO convertToDTO(MemberEntity memberEntity) {
		AddressMemberDTO dto = new AddressMemberDTO();
		dto.setId(memberEntity.getId());
		dto.setName(memberEntity.getName());
		dto.setPosition(memberEntity.getPosition());
		dto.setTel(memberEntity.getTel());
		dto.setDepartmentName(memberEntity.getDepartment().getName());
		return dto;
	}

	@Override
	public List<AddressMemberDTO> findPagedMembers(int page, int size) {
		// Pageable 객체를 생성하여 페이징 정보 설정
		Pageable pageable = PageRequest.of(page - 1, size); // 페이지는 0부터 시작하므로 1을 빼줍니다.

		// 페이징 처리된 주소록을 조회
		Page<MemberEntity> pagedMembers = memberRepository.findAll(pageable);

		// 조회된 페이징 데이터를 DTO로 변환
		List<AddressMemberDTO> pagedMembersDTO = pagedMembers.getContent().stream().map(this::convertToDTO)
				.collect(Collectors.toList());

		return pagedMembersDTO;
	}
}
