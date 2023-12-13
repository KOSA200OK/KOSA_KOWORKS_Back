package com.my.meetingroom.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;
import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;
import com.my.notification.service.NotificationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReservationScheduler {
	
	@Autowired
	NotificationServiceImpl notify;
	
	@Autowired
	MeetingReservationRepository reservation;
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	//스케쥴러로 12시 정각마다 시작하고, 30분 단위로 검사
	public void startScheduler() {
		scheduler.scheduleAtFixedRate(() -> {
			try {
				checkReservations();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, calculateInitialDelay(), 30, TimeUnit.MINUTES);
	}
	
	private long calculateInitialDelay() {
		Calendar now = Calendar.getInstance();
		int minutesUntilNextMidnight = 24*60 - (now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE));
		log.warn("***스케줄러 시간 확인*** {}", minutesUntilNextMidnight);

		return minutesUntilNextMidnight;
	}
	
	private void checkReservations() throws Exception{
		Date currentTime = new Date();
		
		//예약내역 불러오기
		List<MeetingReservationEntity> reservations = reservation.findAll();
		for (MeetingReservationEntity mre : reservations) {
			try {
				String st = mre.getStartTime();
				SimpleDateFormat formatter = new SimpleDateFormat("HH시 mm분 ss초");
				Date startTime = formatter.parse(st);
				
				//현재 시간으로부터 30분 이내 일 때 알림 보내기
				if (startTime.getTime() - currentTime.getTime() <= 30) {
					MemberEntity memberEntity = mre.getMember();
					List<ParticipantsEntity> participantsEntity = mre.getParticipants();
					String memberName = mre.getMember().getName();
					
					notify.send(memberEntity, NotificationEntity.NotificationType.MEETING, "30분 이내에 진행 예정인 회의가 있습니다");
					notify.sendToParticipants(participantsEntity, NotificationEntity.NotificationType.MEETING, "30분 이내에 진행 예정인 회의가 있습니다");
				}				
			} catch (Exception e) {
				throw new Exception();
			}
		}
	}
}
