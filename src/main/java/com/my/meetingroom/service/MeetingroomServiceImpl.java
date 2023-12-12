package com.my.meetingroom.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.exception.AddException;
import com.my.exception.DuplicateException;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.exception.UnavailableException;
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
	ReservationValidator validator;
	
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
	public void createMeetingReservation(MeetingReservationDTO msdto) 
			throws AddException, UnavailableException, DuplicateException, ParseException {
		//DTO->Vo
		MeetingroomMapper mapper = new MeetingroomMapper();
		MeetingReservationEntity entity = mapper.Reservation_DtoToVo(msdto);
		
		// 찬석(알림)
		MemberEntity memberEntity = entity.getMember();
		List<ParticipantsEntity> participantsEntity = entity.getParticipants();
		
		String memberName = entity.getMember().getName();
			
		//최종
		if (!validator.isAvailable(entity)) { //시간 가능 여부 false일 때
			throw new UnavailableException();
		} else if (!validator.idResDupChk(entity)) { //동일한 시간대, 같은 id 예약 가능 false일 때
			throw new DuplicateException();
		} else {
			MeetingReservationEntity savedEntity = reservation.save(entity);
			System.out.println("끝 : savedEntity" + savedEntity.getId());
			
			// 찬석(알림 - 예약시에는 예약자에게만 알림가게 하기)
			notify.send(memberEntity, NotificationEntity.NotificationType.MEETING, "회의실예약이 되었습니다.");
		}
			
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
	public void createParticipants(List<ParticipantsDTO> pdtoli) throws AddException {		
		//DTO->Vo
		MeetingroomMapper mapper = new MeetingroomMapper();
		for (ParticipantsDTO pdto : pdtoli) {
			ParticipantsEntity pentity = mapper.Participants_DtoToVo(pdto);			
			ParticipantsEntity savedEntity = participants.save(pentity);
		}
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
	        MeetingReservationEntity entity = mrentity.get();
			System.out.println(")))))))" + id);
			
			//영속성 제거 (자식 엔터티와의 연관관계를 해제한다)
			entity.getParticipants().clear();
			entityManager.clear();
			
//			reservation.delete(entity);		
			reservation.deleteById(id); //delete, deleteById 둘다 가능
			reservation.save(entity);
		} catch (Exception e) {
			throw new RemoveException();
		}	
	}

}
