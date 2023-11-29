package com.my.attendance.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

public interface AttendanceService {

	public List<AttendanceEntity> findAll() throws FindException;
	
	public Page<AttendanceDTO> findAllByMemberId(String memberId, Pageable pageable) throws FindException;
	
	// 출석
	public void createAttendance(AttendanceDTO dto) throws AddException;
	
	// 퇴근
	public void modifyAttendance(AttendanceDTO dto) throws ModifyException;
	
} // end class
