package com.my.attendance.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.my.attendance.dao.AttendanceRepository;
import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.member.entity.MemberEntity;
import com.my.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceRepository repository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AttendanceMapper model;

	@Override
	public List<AttendanceEntity> findAll() throws FindException {
		
		return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
	} // findAll

	@Override
	public List<AttendanceDTO> findAllByMemberId(String memberId) throws FindException {
	    log.warn("1. findByMemberId의 memberid ===> {} ", memberId);
	    
	    List<AttendanceEntity> entityList= repository.findAllByMemberId(memberId);
	    List<AttendanceDTO> list = new ArrayList<>();
	    
	    for(AttendanceEntity entity : entityList) {
	    	AttendanceDTO dto = model.VoToDTO(entity);
	    	list.add(dto);
	    }
	 
	    return list;

	} // findAllByMemberId

	@Override
	public void createAttendance(AttendanceDTO dto) throws AddException {
		
		log.warn("Service dto ===> {}", dto.getMemberId());
		
		// dto 객체로 들어온 것을 entiiy로 변환
		AttendanceEntity entity = model.DtoToVo(dto);
//		entity.setMemberId(dto.getMemberId());
		
		log.warn("Service entity ===> {}", entity.getMemberId());
		
		// currentTime에 현재시간 대입
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        
        System.out.println("현재시간: " + currentTime);
        System.out.println("아이디 : " + dto.getMemberId() );
        
        // DB에서 해당 memberId의 출석 정보 가져오기
        Optional<AttendanceEntity> existingAttendance = repository.findById(dto.getMemberId());
        
        if (existingAttendance.isPresent()) {
            System.out.println("이미 출석했습니다");
            return; // 이미 출석한 경우 메서드 종료
        }
        
        LocalTime onTimeStart = AttendanceTime.ON_TIME.getTime();
        LocalTime lateTimeEnd = AttendanceTime.LATE_TIME.getTime();
        LocalTime absenceTimeEnd = AttendanceTime.ABSENCE_TIME.getTime(); // 12시 이후

        if (currentTime.isBefore(onTimeStart)) {	// 9시까지 출근
        	entity.setAttendanceDate(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        	entity.setStartTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        	entity.setStatus(AttendanceStatus.ON.getStatus());
        	log.error("time ${}", entity.getStartTime());
        } else if (currentTime.isBefore(lateTimeEnd)) { // 9시부터 12시까지
        	entity.setAttendanceDate(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        	entity.setStartTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        	entity.setStatus(AttendanceStatus.LATE.getStatus());
        } else if (currentTime.isAfter(absenceTimeEnd)) { // 12시 이후
        	entity.setAttendanceDate(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        	entity.setStartTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        	entity.setStatus(AttendanceStatus.ABSENCE.getStatus());
        }

	    repository.save(entity);
		
	} // create


	@Override		// modify로 수정
	public void updateAttendance(AttendanceDTO dto) throws ModifyException {
		
		log.warn("Service update dto ===> {}", dto.getMemberId());
		
	    // dto 객체로 들어온 것을 entity로 변환
	    AttendanceEntity entity = model.DtoToVo(dto);

	    MemberEntity memberId = entity.getMemberId();

	    // repository에서 memberId를 사용하여 해당 엔터티를 찾음
	    AttendanceEntity existingEntity = repository.findByMemberId(memberId);

	    if (existingEntity != null) {
	    	
	        LocalTime currentTime = LocalTime.now();

	        existingEntity.setEndTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	        existingEntity.setStatus(AttendanceStatus.OFF.getStatus());

	        repository.save(existingEntity);
	        
	    } else {	
	        // 예외 처리 - 해당 memberId를 찾을 수 없는 경우
	        throw new ModifyException("Attendance for memberId " + memberId + " not found.");
	    }
	    
	} // end class

	
} // end class
