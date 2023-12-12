package com.my.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.schedule.entity.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
	
//	@Query("SELECT s FROM ScheduleEntity s " +
//	           " WHERE s.member.id = :memberId AND EXTRACT(MONTH FROM s.startTime) = :month"+
//				" ORDER BY s.startTime DESC")
//	public List<ScheduleEntity> findAllSchedule(String memberId, Long month);
	
	@Query("SELECT s FROM ScheduleEntity s " +
    " WHERE s.member.id = :memberId"+
		" ORDER BY s.startTime DESC")
	public List<ScheduleEntity> findAllSchedule(String memberId);
}
