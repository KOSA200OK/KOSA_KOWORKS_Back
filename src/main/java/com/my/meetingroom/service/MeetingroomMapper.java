package com.my.meetingroom.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.MeetingRoomDTO;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.MeetingroomDetailEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingRoomRepository;
import com.my.member.dto.MemberDTO;
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
		
//		Class<MeetingReservationEntity> destinationType = MeetingReservationEntity.class;
//		for(ParticipantsDTO p:  dto.getParticipants()) {
//			p.setMeetingId(dto.getId());
//			p.setMember(dto.getMember());
//		}
//		dto.setParticipants(dto.getParticipants());
//		Object source = dto;
//		MeetingReservationEntity entity = mapper.map(source, destinationType);
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
		mrde.setName(dto.getMeetingroom().getName());
		mrde.setLocation(dto.getMeetingroom().getLocation());
		mrde.setMaxNum(dto.getMeetingroom().getMaxNum());
		mrde.setProjector(dto.getMeetingroom().getProjector());
		mrde.setSocket(dto.getMeetingroom().getSocket());
		mrde.setMonitor(dto.getMeetingroom().getMonitor());
		mrde.setMarker(dto.getMeetingroom().getMarker());
		e.setMeetingroom(mrde);
		
		MemberEntity me = new MemberEntity();
		me.setId(dto.getMember().getId());
		me.setName(dto.getMember().getName()); // 찬석
		e.setMember(me);
		
		List<ParticipantsEntity> listpe = new ArrayList<>();
		for(ParticipantsDTO p : dto.getParticipants()) {
			ParticipantsEntity pe = new ParticipantsEntity();
			pe.setId(p.getId());
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
		ObjectMapper omapper = new ObjectMapper();
		omapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		omapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
//		Object source = entity;
//		Class<MeetingReservationDTO> destinationType = MeetingReservationDTO.class;
//		MeetingReservationDTO dto = mapper.map(source, destinationType);
		
		MeetingReservationDTO mrd = new MeetingReservationDTO();
		mrd.setId(entity.getId());
		mrd.setStartTime(entity.getStartTime());
		mrd.setEndTime(entity.getEndTime());
		mrd.setMeetingDate(entity.getMeetingDate());
		mrd.setPurpose(entity.getPurpose());
		
		MeetingRoomDTO mr = new MeetingRoomDTO();
		mr.setId(entity.getMeetingroom().getId());
		mr.setName(entity.getMeetingroom().getName());
		mr.setLocation(entity.getMeetingroom().getLocation());
		mr.setMaxNum(entity.getMeetingroom().getMaxNum());
		mr.setProjector(entity.getMeetingroom().getProjector());
		mr.setSocket(entity.getMeetingroom().getSocket());
		mr.setMonitor(entity.getMeetingroom().getMonitor());
		mr.setMarker(entity.getMeetingroom().getMarker());
		mrd.setMeetingroom(mr);
		
		MemberDTO md = new MemberDTO();
		md.setId(entity.getMember().getId());
		md.setName(entity.getMember().getName());
		mrd.setMember(md);
		
		List<ParticipantsDTO> listpd = new ArrayList<>();
		for(ParticipantsEntity pe : entity.getParticipants()) {
			ParticipantsDTO pd = new ParticipantsDTO();
			pd.setId(pe.getId());
			
			MeetingReservationDTO mrdd = new MeetingReservationDTO();
			mrdd.setId(pe.getMeeting().getId());;
			pd.setMeetingId(mrdd);
			
			MemberDTO memd = new MemberDTO();
			memd.setId(pe.getMember().getId());
			memd.setName(pe.getMember().getName());		
			pd.setMember(memd);
			listpd.add(pd);
		}
		mrd.setParticipants(listpd);
		
		return mrd;
	}
	
	//VO->DTO 변환 (MeetingRoom)
	public MeetingRoomDTO Meetingroom_VoToDto(MeetingroomDetailEntity entity) {
		
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration()
//				.setMatchingStrategy(MatchingStrategies.STANDARD)
//				.setFieldAccessLevel(AccessLevel.PRIVATE)
//				.setFieldMatchingEnabled(true);
//		ObjectMapper omapper = new ObjectMapper();
//		omapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		omapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
//		Object source = entity;
//		Class<MeetingRoomDTO> destinationType = MeetingRoomDTO.class;
//		MeetingRoomDTO dto = mapper.map(source, destinationType);
		
		//vo->dto
		MeetingRoomDTO mrdto = new MeetingRoomDTO();
		mrdto.setId(entity.getId());
		mrdto.setName(entity.getName());
		mrdto.setLocation(entity.getLocation());
		mrdto.setMaxNum(entity.getMaxNum());
		mrdto.setMonitor(entity.getMonitor());
		mrdto.setProjector(entity.getProjector());
		mrdto.setSocket(entity.getSocket());
		mrdto.setMarker(entity.getSocket());
		System.out.println("******" + entity.getReservation().get(0).getMeetingDate());
		
		List<MeetingReservationDTO> listmr = new ArrayList<>();
		for(MeetingReservationEntity mre : entity.getReservation()) {
			System.out.println("-----" + mre.getMeetingDate());
			MeetingReservationDTO mrd = new MeetingReservationDTO();
			mrd.setId(mre.getId());
			mrd.setStartTime(mre.getStartTime());
			mrd.setEndTime(mre.getEndTime());
			mrd.setMeetingDate(mre.getMeetingDate());
			mrd.setPurpose(mre.getPurpose());
			listmr.add(mrd);
		}
		System.out.println(listmr.size());
		mrdto.setReservation(listmr);
		return mrdto;
	}
	
	
	//DTO->VO 변환 (Participants)
	public ParticipantsEntity Participants_DtoToVo(ParticipantsDTO pdto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		ParticipantsEntity pe = new ParticipantsEntity();
		pe.setId(pdto.getId());
		
		MeetingReservationEntity pmre = new MeetingReservationEntity();
		pmre.setId(pdto.getMeetingId().getId());
		pe.setMeeting(pmre);
		
		MemberEntity me = new MemberEntity();
		me.setId(pdto.getMember().getId());
		me.setName(pdto.getMember().getName());
		pe.setMember(me);
		
		return pe;
	}
	
}
