package com.my.meetingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.meetingroom.entity.MeetingReservationEntity;

public interface MeetingroomRepository extends JpaRepository<MeetingReservationEntity, Long>{

}
