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
		
		// dto 객체로 들어온 것을 entiiy로 변환
		AttendanceEntity entity = model.DtoToVo(dto);
		
		// currentTime에 현재시간 대입
        LocalTime currentTime = LocalTime.now();
        
        System.out.println("현재시간: " + currentTime);
        LocalTime onTimeStart = AttendanceTime.ON_TIME.getStartTime();		// enum타입의 ON_TIME값에 해당하는 startTime값 가져와서 대입
        LocalTime onTimeEnd = AttendanceTime.ON_TIME.getEndTime();			// emnm타입의 OFF_TIME에 해당하는 endTime값 가져와서 대입

        if (currentTime.isAfter(onTimeStart) && currentTime.isBefore(onTimeEnd)) {	// 
            entity.setStartTime(currentTime);
            entity.setStatus(AttendanceStatus.ON.getStatus());
        } else {
            entity.setStartTime(currentTime);
            entity.setStatus(AttendanceStatus.LATE.getStatus());
        }
		
		repository.save(entity);
		
	} // create

	
	
} // end class
