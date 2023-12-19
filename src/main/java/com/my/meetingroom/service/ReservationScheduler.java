package com.my.meetingroom.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
		}, 0, 1, TimeUnit.MINUTES);
				//0, 1, TimeUnit.MINUTES);
				//calculateInitialDelay(), 30, TimeUnit.MINUTES);
	}
	
	private long calculateInitialDelay() {
		Calendar now = Calendar.getInstance();
		int minutesUntilNextMidnight = 24*60 - (now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE));
		log.warn("***스케줄러 시간 확인*** {}", minutesUntilNextMidnight);

		return minutesUntilNextMidnight;
	}
	
	
	private void checkReservations() throws Exception{
		System.out.println("*********scheduler test********");
		
		//예약내역 불러오기
		List<MeetingReservationEntity> reservations = reservation.findAll();
		System.out.println("찾았음" + reservations.get(0).getId());
		
		// 알림을 보낼 대상을 저장할 리스트
        List<MemberEntity> membersToNotify = new ArrayList<>();
//        List<ParticipantsEntity> participantsToNotify = new ArrayList<>();
        List<ParticipantsEntity> participantsEntity = null;
		
		for (MeetingReservationEntity mre : reservations) {
			try {
				String timestring = mre.getMeetingDate() + " " + mre.getStartTime();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		        LocalDateTime startTime = LocalDateTime.parse(timestring, formatter);

		        LocalDateTime currentTime = LocalDateTime.now();

				String st = mre.getStartTime();
				System.out.println("st " + st);

				//현재 시간으로부터 30분 이내 일 때 알림 보내기
				if (startTime.isBefore(currentTime.plusMinutes(30))) {
					System.out.println("시작시간  " + startTime);
					System.out.println("현재시간  " + currentTime);
					System.out.println("---여기까지 완료---");
					MemberEntity memberEntity = mre.getMember();
					participantsEntity = mre.getParticipants();
					String memberName = mre.getMember().getName();
					log.warn("-----이름 {}---", memberName);
					
					//알림 보낼 대상을 리스트에 추가하기
//					membersToNotify.add(memberEntity);
//                    participantsToNotify.addAll(participantsEntity);
				}
			} catch (Exception e) {
				throw new Exception();
			}
		}
		
		for (MemberEntity member : membersToNotify) {
			notify.send(member, NotificationEntity.NotificationType.MEETING, "30분 후 진행 예정인 회의가 있습니다");			
		}
		notify.sendToParticipants(participantsEntity, NotificationEntity.NotificationType.MEETING, "30분 이내에 진행 예정인 회의가 있습니다");
		log.warn("------알림 완료-----");
	}
	
}
