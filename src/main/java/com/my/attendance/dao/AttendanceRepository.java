package com.my.attendance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.attendance.entity.AttendanceEntity;
import com.my.member.entity.MemberEntity;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

	AttendanceEntity findByMemberId(MemberEntity memberId);

}
