package com.my.attendance.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.my.attendance.dao.AttendanceRepository;
import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.car.entity.CarRentEntity;
import com.my.car.repository.CarRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendanceMapper {
	
	private AttendanceRepository repository;
	
	private ModelMapper mapper;
	
	public AttendanceMapper() {
		this.mapper = new ModelMapper();
		this.mapper.getConfiguration()
				   .setMatchingStrategy(MatchingStrategies.STANDARD)
				   .setFieldAccessLevel(AccessLevel.PRIVATE)
				   .setFieldMatchingEnabled(true);
	}
	
	public AttendanceDTO VoToDTO(AttendanceEntity att) {

		log.warn("3. VoToDTO의 att =====> {}", att);
		
		Object source = att;
//		Class<AttendanceDTO> destinationType = AttendanceDTO.class;
//		Class<AttendanceDTO destinationType = AttendanceDTO.class;
		
		AttendanceDTO dto = mapper.map(source, AttendanceDTO.class);
		dto.setMemberId(dto.getMemberId());
		
		return dto;

	} // VoToDTO

	public AttendanceEntity DtoToVo(AttendanceDTO dto) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				   .setMatchingStrategy(MatchingStrategies.STANDARD)
				   .setFieldAccessLevel(AccessLevel.PRIVATE)
				   .setFieldMatchingEnabled(true);

		log.warn("DtoToVO inner dto ======> {}", dto.getMemberId());

		Object source = dto;

		Class<AttendanceEntity> destinationType=AttendanceEntity.class;

		AttendanceEntity entity = mapper.map(source, destinationType);
	
		log.warn("DtoToVO inner entity ======> {}", entity.getMemberId());
		
		return entity;
	} // DtoToVo


} // end class
