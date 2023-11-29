package com.my.member.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import com.my.member.dto.MemberDTO;
import com.my.member.entity.MemberEntity;
import com.my.member.repository.MemberRepository;

public class MemberMapper {
	public MemberEntity dtoToEntity(MemberDTO dto) {
		MemberRepository mr;
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);

		Object source = dto;

		Class<MemberEntity> destinationType = MemberEntity.class;
		MemberEntity entity = mapper.map(source, destinationType); //DTO->VO

		return entity;
	}
	
	public MemberDTO entityToDto(MemberEntity entity) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<MemberDTO> destinationType = MemberDTO.class;
		MemberDTO dto = mapper.map(source, destinationType);
		
		return dto;
	}
}
