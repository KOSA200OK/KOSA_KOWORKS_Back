package com.my.meetingroom.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.FindException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;
import com.my.meetingroom.repository.MeetingRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MeetingroomServiceImpl implements MeetingroomService {
	
	@Autowired
	MeetingRoomRepository meetingroom;
	
	@Autowired
	MeetingReservationRepository reservation;
	
	@Override
	public List<MeetingReservationDTO> findAllMeetingRoom(String meetingDate) throws FindException {
		
//		meetingDate = "2023-11-30";
//		System.out.println(meetingDate);
		
		List<MeetingReservationEntity> entity = reservation.findAllMeetingRoom(meetingDate);
		List<MeetingReservationDTO> list = new ArrayList();
		MeetingroomMapper mm = new MeetingroomMapper();
		
		//Vo->DTO
		for (MeetingReservationEntity mre : entity) {
			MeetingReservationDTO dto = mm.Reservation_VoToDto(mre);
			list.add(dto);
		}
		return list;
	}

}
