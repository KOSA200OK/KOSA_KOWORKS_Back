package com.my.meetingroom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.meetingroom.entity.MeetingReservationEntity;

public interface MeetingReservationRepository extends JpaRepository<MeetingReservationEntity, Long> {
	@Query(value="SELECT *\r\n"
			+ "FROM meetingroom_detail md LEFT OUTER JOIN meeting_reservation mr ON md.id = mr.meetingroom_id\r\n"
			+ "WHERE mr.meeting_date = :meetingDate OR mr.meeting_date IS NULL\r\n"
			+ "ORDER BY md.id",
			nativeQuery=true)
	public List<MeetingReservationEntity> findAllMeetingRoom(String meetingDate);
}