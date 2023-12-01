package com.my.meetingroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;
import com.my.meetingroom.repository.MeetingRoomRepository;
import com.my.meetingroom.repository.ParticipantsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MeetingroomServiceImpl implements MeetingroomService {
	
	@Autowired
	MeetingRoomRepository meetingroom;
	
	@Autowired
	MeetingReservationRepository reservation;
	
	@Autowired
	ParticipantsRepository participants;
		
	
	@Override
	public List<MeetingReservationDTO> findAllMeetingRoom(String meetingDate) throws FindException {
		
		List<MeetingReservationEntity> entity = reservation.findAllMeetingRoom(meetingDate);
		List<MeetingReservationDTO> list = new ArrayList();
		MeetingroomMapper mapper = new MeetingroomMapper();
		
		//Vo->DTO
		for (MeetingReservationEntity mre : entity) {
			MeetingReservationDTO dto = mapper.Reservation_VoToDto(mre);
			list.add(dto);
		}
		return list;
	}

	@Override
	public Optional<MeetingReservationDTO> findById(Long id) throws FindException {
		Optional<MeetingReservationEntity> entity = reservation.findById(id);
		MeetingroomMapper mapper = new MeetingroomMapper();
		return entity.map(mapper::Reservation_VoToDto);
	}
	
	@Transactional
	@Override
	public void createMeetingReservation(MeetingReservationDTO msdto) throws AddException {
		
		//DTO->Vo
		MeetingroomMapper mapper = new MeetingroomMapper();
		MeetingReservationEntity entity = mapper.Reservation_DtoToVo(msdto);
		
		//최종
		MeetingReservationEntity savedEntity = reservation.save(entity);
		System.out.println("끝 : savedEntity" + savedEntity.getId());
		
//		//2차시도(단방향)
//		Long meetingId = savedEntity.getId();
//		for(ParticipantsEntity p : entity.getParticipants()) {
//			p.setMeeting(savedEntity);
////			p.setMeetingId(meetingId);
//			participants.save(p);
//		}
		
//		reservation.deleteById(159L);
		
	}

	@Override
	public Page<MeetingReservationDTO> findAllByMemberId(Pageable pageable, String memberId) throws FindException {
		Page<MeetingReservationEntity> entity = reservation.findAllByMemberId(pageable, memberId);
		MeetingroomMapper mapper = new MeetingroomMapper();
		return entity.map(mapper::Reservation_VoToDto);
	}

	@Override
	public void createParticipants(ParticipantsDTO pdto) throws AddException {		
		//DTO->Vo
		MeetingroomMapper mapper = new MeetingroomMapper();
		ParticipantsEntity pentity = mapper.Participants_DtoToVo(pdto);
		participants.save(pentity);
	}

	@Override
	public void removeParticipants(Long id) throws RemoveException {
		try {
			participants.deleteById(id);
		} catch (Exception e) {
			throw new RemoveException();
		}
	}

	@Override
	public void removeMeeting(Long id) throws RemoveException {
		try {
			reservation.deleteById(id);
		} catch (Exception e) {
			throw new RemoveException();
		}
	}

}
