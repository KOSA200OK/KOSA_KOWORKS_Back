package com.my.meetingroom.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.exception.DuplicateException;
import com.my.exception.UnavailableException;
import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;

@Component
public class ReservationValidator {
	
	@Autowired
	MeetingReservationRepository reservation;
	
	//Validation
	//예약 가능한 시간인지 검증
	boolean isAvailable(MeetingReservationEntity entity) throws UnavailableException, ParseException {
		String date = entity.getMeetingDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		
		List<MeetingReservationEntity> mrelist 
				= reservation.findAllByMeetingroomIdAndMeetingDate(entity.getMeetingroom().getId(), date);
		
		LocalTime newstarttime = LocalTime.parse(entity.getStartTime(), formatter);
		LocalTime newendtime = LocalTime.parse(entity.getEndTime(), formatter);

		for (MeetingReservationEntity mre : mrelist) {
			LocalTime starttime = LocalTime.parse(mre.getStartTime(), formatter);
			LocalTime endtime = LocalTime.parse(mre.getEndTime(), formatter);
			
			if (!(starttime.isAfter(newendtime) || endtime.isBefore(newstarttime) 
					|| starttime.equals(newendtime) || endtime.equals(newstarttime))) {
				throw new UnavailableException("예약이 모두 차있습니다");
			}
		}
		return true;
	}

	
	//같은 아이디의 동시간 예약 검증
	boolean idResDupChk(MeetingReservationEntity entity) throws DuplicateException, ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		
		List<MeetingReservationEntity> mselist = 
				reservation.findAllByMemberIdAndMeetingDate(entity.getMember().getId(), entity.getMeetingDate());
		
		for (MeetingReservationEntity mse : mselist) {
			LocalTime starttime = LocalTime.parse(mse.getStartTime(), formatter);
			LocalTime newendtime = LocalTime.parse(entity.getEndTime(), formatter);
			LocalTime endtime = LocalTime.parse(mse.getEndTime(), formatter);
			LocalTime newstarttime = LocalTime.parse(entity.getStartTime(), formatter);
			
			if (!(starttime.isAfter(newendtime) || endtime.isBefore(newstarttime) 
					|| starttime.equals(newendtime) || endtime.equals(newstarttime))) {
				throw new DuplicateException("같은 시간에 예약이 이미 되어있습니다");
			}
		}
		return true;
	}
}
