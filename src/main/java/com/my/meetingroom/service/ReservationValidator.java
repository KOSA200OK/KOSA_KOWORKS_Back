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
//		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		
		List<MeetingReservationEntity> mrelist 
				= reservation.findAllByMeetingroomIdAndMeetingDate(entity.getMeetingroom().getId(), date);
		
		int[] arr = new int[mrelist.size()];
//		Date newstarttime = formatter.parse(entity.getStartTime()); //새로 하려는 예약
//		Date newendtime = formatter.parse(entity.getEndTime());
		LocalTime newstarttime = LocalTime.parse(entity.getStartTime(), formatter);
		LocalTime newendtime = LocalTime.parse(entity.getEndTime(), formatter);
		System.out.println("new시간---" + newstarttime);
		System.out.println("new시간---" + newendtime);

		for (MeetingReservationEntity mre : mrelist) {
//			Date starttime = formatter.parse(mre.getStartTime()); //기존 예약들
//			Date endtime = formatter.parse(mre.getEndTime());
			LocalTime starttime = LocalTime.parse(mre.getStartTime(), formatter);
			LocalTime endtime = LocalTime.parse(mre.getEndTime(), formatter);
			System.out.println("시간----" + starttime);
			System.out.println("시간----" + endtime);
			
			if (!(starttime.isAfter(newendtime) || endtime.isBefore(newstarttime))) {
				throw new UnavailableException("예약이 모두 차있습니다");
			}
			
//			if (newstarttime.isBefore(endtime) || newendtime.isAfter(starttime) 
//					|| (starttime.isAfter(newstarttime) && starttime.isBefore(newendtime))
//					|| (endtime.isAfter(newstarttime) && endtime.isBefore(newendtime))) {
//				throw new UnavailableException("예약이 모두 차있습니다");
//			}
		}
		
//		if (Arrays.stream(arr).boxed().allMatch(i -> i == 1)) { //리스트에 있는 모든 결과가 1일 때
//			return true;
//		} else {
//			return false;
//		}
		return true;
	}

	
	//같은 아이디의 동시간 예약 검증
	boolean idResDupChk(MeetingReservationEntity entity) throws DuplicateException, ParseException {
//		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

//		List<Object> list = new ArrayList<>();
		
		List<MeetingReservationEntity> mselist = 
				reservation.findAllByMemberIdAndMeetingDate(entity.getMember().getId(), entity.getMeetingDate());
		int[] arr = new int[mselist.size()];
		
		for (MeetingReservationEntity mse : mselist) {
			LocalTime starttime = LocalTime.parse(mse.getStartTime(), formatter);
			LocalTime newendtime = LocalTime.parse(entity.getEndTime(), formatter);
			LocalTime endtime = LocalTime.parse(mse.getEndTime(), formatter);
			LocalTime newstarttime = LocalTime.parse(entity.getStartTime(), formatter);
			
//			if ((starttime.isAfter(newendtime) || starttime.equals(newendtime)) 
//					&& (endtime.isBefore(newstarttime) || endtime.equals(newstarttime))) {
//				Arrays.fill(arr, 1); //조건을 만족하는 경우 배열에 1을 저장
//			}
			
			if (!(starttime.isAfter(newendtime) || endtime.isBefore(newstarttime))) {
				throw new DuplicateException("같은 시간에 예약이 이미 되어있습니다");
			}
		}
		
		return true;
//		if (Arrays.stream(arr).boxed().allMatch(i -> i == 1)) { //리스트에 있는 모든 결과가 true일 때
//			return true;
//		} else {
//			return false;
//		}
	}
}
