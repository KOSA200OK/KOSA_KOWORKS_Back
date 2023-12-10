package com.my.meetingroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.meetingroom.entity.ParticipantsEntity;

public interface ParticipantsRepository extends JpaRepository<ParticipantsEntity, Long>{

}
