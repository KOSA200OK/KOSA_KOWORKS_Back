package com.my.meetingroom.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.meetingroom.entity.MeetingReservationEntity;

public interface MeetingReservationRepository extends JpaRepository<MeetingReservationEntity, Long> {
	
	//회의실 예약 상세보기
	public Optional<MeetingReservationEntity> findById(Long id);
	
	//내 예약 전체 조회
	public Page<MeetingReservationEntity> findAllByMemberId(Pageable pageable, String memberId);
	
}
