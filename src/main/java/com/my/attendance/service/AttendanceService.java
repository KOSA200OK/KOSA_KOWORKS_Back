package com.my.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.exception.FindException;

@Service
public interface AttendanceService {

	public List<AttendanceEntity> findAll() throws FindException;
	
	public AttendanceDTO findByMemberId(int memberId) throws FindException;
	
} // end class
