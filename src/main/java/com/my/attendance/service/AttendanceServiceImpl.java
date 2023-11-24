package com.my.attendance.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.attendance.dao.AttendanceRepository;
import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceRepository repository;
	
	@Autowired
	private AttendanceMapper model;

	@Override
	public List<AttendanceEntity> findAll() throws FindException {
		
		return repository.findAll();
		
	} // findAll

	@Override
	public AttendanceDTO findByMemberId(int memberId) throws FindException {
		
		Optional<AttendanceEntity> att = repository.findById(memberId);
		
		return model.VoToDTO(att);
		
	} // findByMemberId

	@Override
	public void createAttendance(AttendanceDTO dto) throws AddException {
		
		AttendanceEntity entity = model.DtoToVo(dto);
		
		dto.getStartTime();
		
        LocalTime currentTime = LocalTime.now();
        LocalTime onTimeStart = AttendanceTime.ON_TIME.getStartTime();
        LocalTime onTimeEnd = AttendanceTime.OFF_TIME.getEndTime();

        if (currentTime.isAfter(onTimeStart) && currentTime.isBefore(onTimeEnd)) {
            entity.setStartTime(currentTime);
            entity.setStatus(AttendanceStatus.ON.getStatus());
        } else {
            entity.setStartTime(currentTime);
            entity.setStatus(AttendanceStatus.LATE.getStatus());
        }
		
		repository.save(entity);
		
	} // create

	
	
} // end class
