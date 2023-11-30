package com.my.meetingroom.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.MeetingroomDetailEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingRoomRepository;
import com.my.member.entity.MemberEntity;

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
		
		//2차시도
//		Object source = dto;
//		Class<MeetingReservationEntity> destinationType = MeetingReservationEntity.class;
//		MeetingReservationEntity entity = mapper.map(source, destinationType);
//		for(ParticipantsEntity p:  entity.getParticipants()) {
//			MemberEntity pm = p.getMember();
//			MeetingReservationEntity pmr = p.getMeeting(); 
//			p.setMeeting(pmr);
//			p.setMember(pm);
//		}
//		return entity;
		
		//최종
		MeetingReservationEntity e = new MeetingReservationEntity();
		e.setId(dto.getId());
		e.setMeetingDate(dto.getMeetingDate());
		e.setStartTime(dto.getStartTime());
		e.setEndTime(dto.getEndTime());
		e.setPurpose(dto.getPurpose());
		MeetingroomDetailEntity mrde = new MeetingroomDetailEntity();
		mrde.setId(dto.getMeetingroom().getId());
		e.setMeetingroom(mrde);
		MemberEntity me = new MemberEntity();
		me.setId(dto.getMember().getId());
		e.setMember(me);
		List<ParticipantsEntity> listpe = new ArrayList<>();
		for(ParticipantsDTO p : dto.getParticipants()) {
			ParticipantsEntity pe = new ParticipantsEntity();
			pe.setId(p.getId());
			//pe.setMeetingId(e.getId());//~~~
			pe.setMeeting(e);
			
			MemberEntity pme = new MemberEntity();
			pme.setId(p.getMember().getId());
			pe.setMember(pme);
			listpe.add(pe);
		}
		e.setParticipants(listpe);
		return e;
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
	
	
	//DTO->VO 변환 (Participants)
	public ParticipantsEntity Participants_DtoToVo(ParticipantsDTO pdto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = pdto;
		Class<ParticipantsEntity> destinationType = ParticipantsEntity.class;
		ParticipantsEntity pentity = mapper.map(source, destinationType);
		return pentity;
	}
}
