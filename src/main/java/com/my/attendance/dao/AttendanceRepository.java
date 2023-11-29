package com.my.attendance.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.attendance.entity.AttendanceEntity;
import com.my.member.entity.MemberEntity;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {
	
	AttendanceEntity findByMemberId(MemberEntity memberId);

//	@Query("SELECT a FROM attendance a JOIN a.member m WHERE m.memberId.id = :memberId")
//	@Query("SELECT a FROM attendance a JOIN a.member m WHERE m.member.id = :memberId")
//	@Query(value="SELECT a FROM Attendance a JOIN a.member m WHERE m.id = :memberId", nativeQuery = true)
//	@Query(value="SELECT a.member_id, m.id\r\n"
	@Query(value="SELECT *\r\n"
			+ "FROM attendance a\r\n"
			+ "JOIN member m ON a.member_id = m.id\r\n"
			+ "WHERE m.id = :memberId" , nativeQuery = true)
	List<AttendanceEntity> findAllByMemberId(String memberId);

//	Optional<AttendanceEntity> findById(String memberId);

}
