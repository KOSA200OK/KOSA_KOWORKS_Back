package com.my.meetingroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;
import com.my.meetingroom.repository.MeetingRoomRepository;
import com.my.meetingroom.repository.ParticipantsRepository;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.driver.parser.util.Array;

@Service
@Slf4j
public class MeetingroomServiceImpl implements MeetingroomService {
	
	@Autowired
	MeetingRoomRepository meetingroom;
	
	@Autowired
	MeetingReservationRepository reservation;
	
	@Autowired
	ParticipantsRepository participants;
	
	@Autowired
	private EntityManager entityManager;
		
	@Override
	public List<MeetingReservationDTO> findAllMeetingRoom(String meetingDate) throws FindException {
//		meetingDate = "2023-11-30";
//		System.out.println(meetingDate);
		
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

	@Override
	public void createMeetingReservation(MeetingReservationDTO msdto) throws AddException {
		
		//DTO->Vo
		MeetingroomMapper mapper = new MeetingroomMapper();
		MeetingReservationEntity entity = mapper.Reservation_DtoToVo(msdto);
		reservation.save(entity);

		//participants 넣기!!
		ParticipantsDTO pdto = new ParticipantsDTO();
		ParticipantsEntity pentity = mapper.Participants_DtoToVo(pdto);		
		String[] arr = msdto.getParticipants().getMemberId();
		for (String stringid : arr) {
//			pentity.setMeeting(entity);
//			pentity.setMember(entity.getMember());
			System.out.println("memberid 배열!!!!!" + stringid);
//			System.out.println("member entity!!!!" + pentity.getMember().getId());
//			System.out.println("meeting entity!!!!" + pentity.getMeeting().getId());
			participants.save(pentity);
		}
		
	}
	
//	@Override
////	@Transactional
//	public void createMeetingReservation(MeetingReservationDTO meetingReservationDTO, List<String> participantsMemberId) throws AddException {
//		//DTO->Vo
//		MeetingroomMapper mapper = new MeetingroomMapper();
//		MeetingReservationEntity entity = mapper.Reservation_DtoToVo(meetingReservationDTO);
//		reservation.save(entity);
//        System.out.println("---------------resmeetingId" + entity.getMeetingroom().getId());
//        System.out.println("---------------resmemberId" + entity.getMember().getId());
//
//		
//		for (String memberId : participantsMemberId) {
//            ParticipantsDTO pdto = new ParticipantsDTO();
//            pdto.setMeetingId((long)1);
//            pdto.setMemberId(memberId);
//            ParticipantsEntity pentity = mapper.Participants_DtoToVo(pdto);
//
////            pentity = entityManager.merge(pentity);
//            System.out.println("---------------parmeetingId" + pentity.getMeeting().getId());
//            System.out.println("---------------parmemberId" + pentity.getMember().getId());
////            pentity = entityManager.merge(pentity);
////            entityManager.flush();
//            participants.save(pentity);
//        }
//	
//	}

}
