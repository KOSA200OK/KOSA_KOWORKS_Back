package com.my.meetingroom.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.ParticipantsEntity;
import com.my.meetingroom.repository.MeetingReservationRepository;
import com.my.meetingroom.repository.ParticipantsRepository;
import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;
import com.my.notification.service.NotificationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReservationScheduler {

    private final NotificationServiceImpl notify;
    private final MeetingReservationRepository reservation;
    
    @Autowired
    ParticipantsRepository participants;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public ReservationScheduler(NotificationServiceImpl notify, MeetingReservationRepository reservation) {
        this.notify = notify;
        this.reservation = reservation;
    }
			
	//스케쥴러로 12시 정각마다 시작하고, 30분 단위로 검사
	public void startScheduler() {
		scheduler.scheduleAtFixedRate(() -> {
			try {
				checkReservations();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, calculateInitialDelay(), 30, TimeUnit.MINUTES);
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
		
		// 알림을 보낼 대상을 저장할 리스트
	    Set<MemberEntity> membersToNotify = new HashSet<>(); //중복불가
	    List<ParticipantsEntity> participantsEntity = new ArrayList<>();
		
		for (MeetingReservationEntity mre : reservations) {
			try {
				String timestring = mre.getMeetingDate() + " " + mre.getStartTime();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		        LocalDateTime startTime = LocalDateTime.parse(timestring, formatter);
		        LocalDateTime currentTime = LocalDateTime.now();

				//현재 시간으로부터 30분 이내 일 때 알림 보내기
				if (startTime.isBefore(currentTime.plusMinutes(30)) && startTime.isAfter(currentTime)) {					
					MemberEntity memberEntity = mre.getMember();
					String memberName = mre.getMember().getName();
					log.warn("-----이름 {}---", memberName);
			        
					//알림 보낼 대상을 리스트에 추가하기
	                membersToNotify.add(memberEntity);
	                participantsEntity = participants.findByMeetingId(mre.getId());
				}
			} catch (Exception e) {
		        log.error("예약 확인 중 오류 발생: {}", e.getMessage());
			}
		}
		
		//예약자에게 알림 보내기
		for (MemberEntity member : membersToNotify) {
	        notify.send(member, NotificationEntity.NotificationType.MEETING, "30분 후 진행 예정인 회의가 있습니다");         
	    }
		
		//참여자들에게 알림 보내기
		if (participantsEntity.isEmpty()) {
			System.out.println("참여자가 없습니다.");
		} else {
			notify.sendToParticipants(participantsEntity, NotificationEntity.NotificationType.MEETING, "30분 후 진행 예정인 회의가 있습니다");			
		}
		
	    log.warn("------알림 완료-----");
	}
	
}
