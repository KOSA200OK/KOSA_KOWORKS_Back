package com.my.meetingroom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.my.meetingroom.entity.MeetingReservationEntity;

public interface MeetingReservationRepository extends JpaRepository<MeetingReservationEntity, Long> {
	
	// 회의실 예약 상세보기
	public Optional<MeetingReservationEntity> findById(Long id);
	
	// 내 예약 전체 조회
	public Page<MeetingReservationEntity> findAllByMemberId(Pageable pageable, String memberId);
	
	
	// Validator
	// 1) 예약 가능한 시간인지 검증 - 각 미팅룸id와 날짜에 해당하는 모든 회의 내역 뽑기
	List<MeetingReservationEntity> findAllByMeetingroomIdAndMeetingDate(Long meetingroomId, String meetingDate);
	
	// 2) 같은 아이디의 동시간 예약 검증 - 각 멤버id와 날짜에 해당하는 회의 내역 뽑기
	List<MeetingReservationEntity> findAllByMemberIdAndMeetingDate(String memberId, String meetingDate);
}
