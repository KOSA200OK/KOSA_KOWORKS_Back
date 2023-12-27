package com.my.attendance.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.my.attendance.entity.AttendanceEntity;
import com.my.member.entity.MemberEntity;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {
	
//	AttendanceEntity findByMemberId(MemberEntity memberId);
	
	// 조회
	@Query(value="SELECT *\r\n"
			+ "FROM attendance a\r\n"
			+ "JOIN member m ON a.member_id = m.id\r\n"
			+ "WHERE m.id = :memberId" , nativeQuery = true)
	Page<AttendanceEntity> findAllByMemberId(String memberId, Pageable pageable);

	// 출석
	Optional<AttendanceEntity> findByMemberIdAndAttendanceDate(MemberEntity memberId, String attendanceDate);
	
	// 월별 조회
	Page<AttendanceEntity> findByAttendanceDateStartingWithAndMemberId(String attendanceDate, MemberEntity memberEntity, Pageable pageable);

	// 출퇴근시간 조회
    @Query(value = "SELECT * "
    		+ "FROM attendance a JOIN member m ON a.member_id = m.id "
    		+ "WHERE m.id = :memberId AND a.attendance_date = :currentDate", nativeQuery = true)
    Optional<AttendanceEntity> findByMemberIdAndAttendanceDate(@Param("memberId") String memberId,
                                                               @Param("currentDate") String currentDate);
	
}
