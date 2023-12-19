package com.my.meetingroom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.my.meetingroom.entity.MeetingReservationEntity;
import com.my.meetingroom.entity.MeetingroomDetailEntity;


public interface MeetingRoomRepository extends JpaRepository<MeetingroomDetailEntity, Long>{
	
	//회의실 전체 목록과 회의실별 예약 내역
	@Query(value="SELECT md.*, mr.id, mr.meeting_date, mr.start_time, mr.end_time, mr.purpose, mr.meetingroom_id, mr.member_id \r\n"
			+ "FROM meetingroom_detail md\r\n"
			+ "LEFT JOIN meeting_reservation mr ON md.id = mr.meetingroom_id AND mr.meeting_date = :meetingDate\r\n"
			+ "WHERE mr.meeting_date IS NULL OR mr.meeting_date = :meetingDate\r\n"
			+ "ORDER BY md.name", nativeQuery=true)
	public List<MeetingroomDetailEntity> findAllByMeetingRoom(@Param("meetingDate") String meetingDate);
	
	//회의실 상세보기
	public Optional<MeetingroomDetailEntity> findById(Long id);

}
