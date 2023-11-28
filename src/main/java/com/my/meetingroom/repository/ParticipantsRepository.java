package com.my.meetingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.meetingroom.entity.ParticipantsEntity;

public interface ParticipantsRepository extends JpaRepository<ParticipantsEntity, Long>{

}
