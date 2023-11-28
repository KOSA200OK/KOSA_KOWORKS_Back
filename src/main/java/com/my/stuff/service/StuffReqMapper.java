package com.my.stuff.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.entity.StuffReqEntity;

public class StuffReqMapper {
	public static StuffReqEntity dtoToEntity(StuffReqDTO dto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STANDARD)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<StuffReqEntity> destinationType = StuffReqEntity.class;
		StuffReqEntity entity = mapper.map(source, destinationType); //DTO->VO
//		StuffReqEntity entity = StuffReqEntity.builder()
//		                                      .id(dto.getId())
//		                                      .stuffEntity(dto.)
//		                                      .name(dto.getName())
//		                                      .quantity(dto.getQuantity())
//		                                      .build();
		
		return entity;
	}
	
	public StuffReqDTO entityToDto(StuffReqEntity entity) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STANDARD)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<StuffReqDTO> destinationType = StuffReqDTO.class;
		StuffReqDTO dto = mapper.map(source, destinationType);
		
		return dto;
	}
}
