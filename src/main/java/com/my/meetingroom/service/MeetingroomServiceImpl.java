package com.my.meetingroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.MeetingRoomDTO;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.MeetingroomDetailEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;
import com.my.meetingroom.repository.MeetingRoomRepository;
import com.my.meetingroom.repository.ParticipantsRepository;
import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;
import com.my.notification.service.NotificationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MeetingroomServiceImpl implements MeetingroomService {
	
	// 찬석
	@Autowired
	NotificationServiceImpl notify;
	
	@Autowired
	MeetingRoomRepository meetingroom;
	
	@Autowired
	MeetingReservationRepository reservation;
	
	@Autowired
	ParticipantsRepository participants;
	
	@Autowired
	EntityManager entityManager;
		
	
	@Override
	public List<MeetingRoomDTO> findByMeetingRoom(String meetingDate) throws FindException {
		List<MeetingroomDetailEntity> entity = meetingroom.findAllByMeetingRoom(meetingDate);
		List<MeetingRoomDTO> list = new ArrayList();
		MeetingroomMapper mapper = new MeetingroomMapper();
		
		//Vo->DTO
		for (MeetingroomDetailEntity mre : entity) {
			MeetingRoomDTO dto = mapper.Meetingroom_VoToDto(mre);
			System.out.println("++++" + dto.getReservation().get(0).getMeetingDate());
			list.add(dto);
		}
		return list;
	}

	@Override
	public Optional<MeetingRoomDTO> findByMeetingroomId(Long id) throws FindException {
		Optional<MeetingroomDetailEntity> entity = meetingroom.findById(id);
		MeetingroomMapper mapper = new MeetingroomMapper();
		return entity.map(mapper::Meetingroom_VoToDto);
	}

	@Override
	public Optional<MeetingReservationDTO> findByResId(Long id) throws FindException {
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
		
		// 찬석
		MemberEntity memberEntity = entity.getMember();
		log.warn("회의실예약 id : {}", memberEntity.getId());
		
		List<ParticipantsEntity> participantsEntity = entity.getParticipants();
		log.warn("회의실 참여 멤버 : {} ", entity.getParticipants());
		
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
		
		// 찬석
	    notify.send(memberEntity, NotificationEntity.NotificationType.MEETING, "회의실예약이 되었습니다.");
	    notify.sendToParticipants(participantsEntity, NotificationEntity.NotificationType.MEETING, "회의실예약이 되었습니다.");
		
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
		ParticipantsEntity savedEntity = participants.save(pentity);
	}

	@Override
	public void removeParticipants(Long id) throws RemoveException {
		try {
			participants.deleteById(id);
		} catch (Exception e) {
			throw new RemoveException();
		}
	}

	@Transactional
	@Override
	public void removeMeeting(Long id) throws RemoveException {
		try {
			Optional<MeetingReservationEntity> mrentity = reservation.findById(id);
//			MeetingReservationEntity entity = mrentity.orElse(null);
	        MeetingReservationEntity entity = mrentity.get();
			System.out.println(")))))))" + id);
			
			//영속성 제거 (자식 엔터티와의 연관관계를 해제한다)
			entity.getParticipants().clear();
			entityManager.clear();
			
			reservation.delete(entity);		
//			reservation.deleteById(id); //delete, deleteById 둘다 가능
			reservation.save(entity);
		} catch (Exception e) {
			throw new RemoveException();
		}
		
	}

}
