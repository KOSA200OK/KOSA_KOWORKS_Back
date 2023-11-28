package com.my.attendance.service;

import java.util.List;

import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

public interface AttendanceService {

	public List<AttendanceEntity> findAll() throws FindException;
	
	public AttendanceDTO findByMemberId(int memberId) throws FindException;
	
	// 출석
	public void createAttendance(AttendanceDTO dto) throws AddException;
	
	// 퇴근
	public void updateAttendance(AttendanceDTO dto) throws ModifyException;
	
} // end class
