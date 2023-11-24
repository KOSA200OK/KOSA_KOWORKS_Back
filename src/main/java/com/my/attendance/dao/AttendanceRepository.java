package com.my.attendance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.attendance.entity.AttendanceEntity;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

}
