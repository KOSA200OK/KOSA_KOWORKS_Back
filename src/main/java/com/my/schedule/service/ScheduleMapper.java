package com.my.schedule.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import com.my.schedule.dto.ScheduleDTO;
import com.my.schedule.entity.ScheduleEntity;

public class ScheduleMapper {
	public ScheduleEntity dtoToEntity(ScheduleDTO dto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STANDARD)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<ScheduleEntity> destinationType = ScheduleEntity.class;
		ScheduleEntity entity = mapper.map(source, destinationType); //DTO->VO
		
		return entity;
	}
	
	public ScheduleDTO entityToDto(ScheduleEntity entity) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STANDARD)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<ScheduleDTO> destinationType = ScheduleDTO.class;
		ScheduleDTO dto = mapper.map(source, destinationType);
		
		return dto;
	}
}
