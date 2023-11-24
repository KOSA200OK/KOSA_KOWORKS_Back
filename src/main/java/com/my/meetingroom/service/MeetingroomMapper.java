package com.my.meetingroom.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.my.meetingroom.dto.MeetingroomDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.repository.MeetingroomRepository;
import com.my.notice.dto.NoticeDTO;
import com.my.notice.entity.NoticeEntity;
import com.my.notice.repository.NoticeRepository;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class MeetingroomMapper {
	@Autowired
	MeetingroomRepository mr;
	
	//DTO->VO 변환 (Meetingroom)
	public MeetingReservationEntity DtoToVo_ModelMapper(MeetingroomDTO dto) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<MeetingReservationEntity> destinationType = MeetingReservationEntity.class;
		MeetingReservationEntity entity = mapper.map(source, destinationType);
		log.error("entity boardTitle:{}, boardContent:{}, boardId:{}, boardDt:{}"
//				,
//				entity.getBoardTitle(),
//				entity.getBoardContent(),
//				entity.getBoardId(),
//				entity.getBoardDt()
				);
		return entity;
	}
	
	
	//VO->DTO 변환
	public MeetingroomDTO VoToDto_ModelMapper(MeetingReservationEntity entity) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<MeetingroomDTO> destinationType = MeetingroomDTO.class;
		MeetingroomDTO dto = mapper.map(source, destinationType);
		
		log.error("entity boardTitle:{}, boardContent:{}, boardId:{}, boardDt:{}"
//				,
//				dto.getBoardTitle(),
//				dto.getBoardContent(),
//				dto.getBoardId(),
//				dto.getBoardDt()
				);
		return dto;
	}
}
