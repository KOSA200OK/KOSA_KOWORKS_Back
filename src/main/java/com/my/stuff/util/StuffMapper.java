package com.my.stuff.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import com.my.stuff.dto.StuffDTO;
import com.my.stuff.entity.StuffEntity;

public class StuffMapper {
	public static StuffEntity dtoToEntity(StuffDTO dto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STANDARD)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<StuffEntity> destinationType = StuffEntity.class;
		StuffEntity entity = mapper.map(source, destinationType); //DTO->VO
		return entity;
	}
	
	public static StuffDTO entityToDto(StuffEntity entity) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STANDARD)
			.setFieldAccessLevel(AccessLevel.PRIVATE) 
			.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<StuffDTO> destinationType = StuffDTO.class;
		StuffDTO dto = mapper.map(source, destinationType);
		
		return dto;
	}
}
