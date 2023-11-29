package com.my.meetingroom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.meetingroom.dto.MeetingRoomDTO;
import com.my.meetingroom.entity.MeetingroomDetailEntity;

public interface MeetingRoomRepository extends JpaRepository<MeetingroomDetailEntity, Long>{

}
