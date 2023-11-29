package com.my.meetingroom.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.MeetingRoomDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.repository.MeetingRoomRepository;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class MeetingroomMapper {
	@Autowired
	MeetingRoomRepository mr;
	
	//DTO->VO 변환 (MeetingReservation)
	public MeetingReservationEntity Reservation_DtoToVo(MeetingReservationDTO dto) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<MeetingReservationEntity> destinationType = MeetingReservationEntity.class;
		MeetingReservationEntity entity = mapper.map(source, destinationType);
		return entity;
	}
	
	
	//VO->DTO 변환 (MeetingReservation)
	public MeetingReservationDTO Reservation_VoToDto(MeetingReservationEntity entity) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<MeetingReservationDTO> destinationType = MeetingReservationDTO.class;
		MeetingReservationDTO dto = mapper.map(source, destinationType);
		return dto;
	}
}
