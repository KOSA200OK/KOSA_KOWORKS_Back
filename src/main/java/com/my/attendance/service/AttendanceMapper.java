package com.my.attendance.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;

@Service
public class AttendanceMapper {
	
	public AttendanceDTO VoToDTO(Optional<AttendanceEntity> att) {
		
		ModelMapper mapper = new ModelMapper();
		
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD)
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		Object source = att;
		Class<AttendanceDTO> destinationType = AttendanceDTO.class;
		
		AttendanceDTO dto = mapper.map(source, destinationType);
		dto.setMemberId(dto.getMemberId());
		
		return dto;

	} // VoToDTO

	public AttendanceEntity DtoToVo(AttendanceDTO dto) {

		ModelMapper mapper=new ModelMapper();

		// 매퍼설정
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD)
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);

		Object source = dto;

		Class<AttendanceEntity> destinationType=AttendanceEntity.class;

		AttendanceEntity entity = mapper.map(source, destinationType);
	
		return entity;
	} // DtoToVo

} // end class
