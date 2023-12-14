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
	/**
	 * 사원아이디와 일치하는 일정을 모두 조회한다
	 * @param memberId 사원아이디
	 * @return 사원아이디와 일치하는 일정
	 */
	public List<ScheduleEntity> findAllByMemberIdOrderByStartTimeDesc(String memberId);
	
	/**
	 * 사원아이디와 일치하는 일정들 중에서 오늘 날짜가 startTime보다 크거나 같고, endTime보다 작거나 같은 일정들을 조회한다
	 * @param memberId 사원아이디
	 * @return 오늘의 개인 일정
	 */
	@Query("SELECT s FROM ScheduleEntity s "+
			" WHERE s.member.id = :memberId AND ( TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN TO_CHAR(s.startTime, 'YYYY-MM-DD') AND TO_CHAR(s.endTime, 'YYYY-MM-DD') ) "+
			" ORDER BY s.startTime ASC")
	public List<ScheduleEntity> findAllTodaySchedule(String memberId);

}
