package com.my.meetingroom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.meetingroom.dto.MeetingRoomDTO;
import com.my.meetingroom.entity.MeetingroomDetailEntity;

public interface MeetingRoomRepository extends JpaRepository<MeetingroomDetailEntity, Long>{
	@Query(value="SELECT *\r\n"
			+ "FROM meetingroom_detail md\r\n"
			+ "LEFT JOIN meeting_reservation mr ON md.id = mr.meetingroom_id AND mr.meeting_date = :meetingDate\r\n"
			+ "ORDER BY md.id", nativeQuery=true)
	public List<MeetingroomDetailEntity> findByMeetingRoom(String meetingDate);
}
