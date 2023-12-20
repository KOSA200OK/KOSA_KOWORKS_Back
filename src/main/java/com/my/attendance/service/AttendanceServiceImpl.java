package com.my.attendance.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.my.attendance.dao.AttendanceRepository;
import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.config.AttendanceConfig;
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
	
	@Autowired
	private AttendanceConfig config;

	@Override
	public List<AttendanceEntity> findAll() throws FindException {
		
		return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
	} // findAll

	// 근태 내역 조회
	@Override
	public Page<AttendanceDTO> findAllByMemberId(String memberId, Pageable pageable) throws FindException {
	    log.warn("1. findByMemberId의 memberid ===> {} ", memberId);
	    

	    // 내림차순 정렬 조건 추가
	    Pageable sortedPageable = PageRequest.of(
	        pageable.getPageNumber(),
	        pageable.getPageSize(),
	        Sort.by("id").descending()
	    );

	    Page<AttendanceEntity> entityList = repository.findAllByMemberId(memberId, sortedPageable);
	    model = new AttendanceMapper();
	    return entityList.map(model::VoToDTO);

	} // findAllByMemberId

	// 출근
	@Override
	public void createAttendance(AttendanceDTO dto) throws AddException {

		// dto 객체로 들어온 것을 entiiy로 변환
		AttendanceEntity entity = model.DtoToVo(dto);

		log.warn("Service entity ===> {}", entity.getMemberId());
		
		// currentTime에 현재시간 대입
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        
        log.warn("현재시간: " + currentTime);
        log.warn("아이디 2 : " + entity.getMemberId().getId() );
        
        List<Integer> statusList = config.getStatus();
        List<String> timeList = config.getTime();
        
        // 이미 출석한 경우를 확인하기 위해 해당 날짜의 출석 데이터를 조회
        Optional<AttendanceEntity> existingAttendance = repository.findByMemberIdAndAttendanceDate(entity.getMemberId(), currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Integer onStatus = statusList.get(0); // on 상태 -> 출근
        Integer offStatus = statusList.get(1); // off 상태 -> 퇴근	
        Integer lateStatus = statusList.get(2);	// late 상태 -> 지각
        Integer absenceStatus = statusList.get(3); // absence 상태 -> 결근
        
        // localTime으로 형변환 해주기 위해서 선언
        String on = timeList.get(0);
        String late = timeList.get(1);
        String absence = timeList.get(2);
        
        // 형변환
        LocalTime onTime = LocalTime.parse(on);  // on 타임 -> 09:00:00
        LocalTime lateTime = LocalTime.parse(late); // late 타임 -> 12:00:00
        LocalTime absenceTime = LocalTime.parse(absence);	// absenceTime -> 12:01:00
        
        
	      if (existingAttendance.isPresent()) {
	          log.warn("이미 출석했습니다");
	          throw new AddException("이미 출석된 아이디입니다");
	      } else {
	    	  if (currentTime.isBefore(onTime)) {	// 9시까지 출근
	    		  entity.setAttendanceDate(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    		  entity.setStartTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    		  entity.setStatus(onStatus);
	    		  log.error("time ${}", entity.getStartTime());
	    	  } else if (currentTime.isBefore(lateTime) && currentTime.isAfter(onTime.plusMinutes(1))) { // 9시부터 12시까지
	    		  entity.setAttendanceDate(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    		  entity.setStartTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    		  entity.setStatus(lateStatus);
	    	  } else if (currentTime.isAfter(absenceTime)) { // 12시 이후
	    		  entity.setAttendanceDate(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    		  entity.setStartTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    		  entity.setStatus(absenceStatus);
	    	  } // if-else
	    	  
	    	  repository.save(entity);
	    	  
	      }

	} // create

	// 퇴근
	@Override
	public void modifyAttendance(AttendanceDTO dto) throws ModifyException {

	    // dto 객체로 들어온 것을 entity로 변환
	    AttendanceEntity entity = model.DtoToVo(dto);

	    MemberEntity memberId = entity.getMemberId();
	    LocalDate currentDate = LocalDate.now();

	    // 이미 해당 날짜에 출근 기록이 있는지 확인
	    Optional<AttendanceEntity> existingAttendance = repository.findByMemberIdAndAttendanceDate(memberId, currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

//	    log.warn("status : ", existingAttendance.get().getStatus());
	    
	    List<Integer> statusList = config.getStatus();
	    List<String> timeList = config.getTime();

//	    Integer earlyStatus = statusList.get(6); // early 상태 -> 조퇴
	    Integer earlyStatus = statusList.get(5); // early 상태 -> 조퇴
//	    Integer offStatus = statusList.get(1); // off 상태 -> 퇴근
	    String off = timeList.get(3);
	    
	    if (!existingAttendance.isPresent()) {
	    	log.warn("출근 기록이 없습니다");
	    	throw new ModifyException("출근 기록이 없습니다. 먼저 출근을 기록해야 합니다.");
	    }
	    
	    AttendanceEntity existingEntity = existingAttendance.get();
	    
//	    if (existingEntity.getStatus() != null && existingEntity.getStatus().equals(offStatus) && existingEntity.getStatus().equals(earlyStatus)) {
	    if (existingEntity.getEndTime() != null) {
	    	log.warn("이미 퇴근한 사원입니다.");
	    	throw new ModifyException("이미 퇴근한 사원입니다.");
	    } else {
	    	
	    	LocalTime currentTime = LocalTime.now();
	    	LocalTime offTime = LocalTime.parse(off); // 퇴근 시간 설정
	    	
//	    	if (currentTime.isBefore(offTime)) { // 조퇴
//	    		existingEntity.setEndTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//	    		existingEntity.setStatus(earlyStatus);
//	    	} else { // 퇴근
//	    		existingEntity.setEndTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//	    		existingEntity.setStatus(offStatus);
//	    	}
	    	
	    	if (currentTime.isBefore(offTime)) { // 조퇴
	    		existingEntity.setEndTime(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    		existingEntity.setStatus(earlyStatus);
	    	}
	    	
	    	repository.save(existingEntity);
	    	
	    }

	    
	} // modifyAttendance

	// 수정 후 코드: 변경된 Repository의 메서드 호출
	@Override
	public Page<AttendanceDTO> findAllByAttendanceDate(String memberId, String attendanceDate, Pageable pageable) throws FindException {
	    log.warn("1. findAllByAttendanceDate의 memberid ===> {} ", memberId);
	    log.warn("2. findAllByAttendanceDate의 dt ===> {}", attendanceDate);
	    
	    // MemberEntity 객체 생성 (예시로 memberId가 "1"인 경우)
	    MemberEntity memberEntity = new MemberEntity();
	    memberEntity.setId(memberId);
	    
	    // 내림차순 정렬 조건 추가
	    Pageable sortedPageable = PageRequest.of(
	        pageable.getPageNumber(),
	        pageable.getPageSize(),
	        Sort.by("id").ascending()
	    );
	    
	    // 수정 후 코드: 변경된 Repository의 메서드 호출
//	    Page<AttendanceEntity> entityList = repository.findByAttendanceDateStartingWithAndMemberId(attendanceDate, memberEntity, pageable);
	    Page<AttendanceEntity> entityList = repository.findByAttendanceDateStartingWithAndMemberId(attendanceDate, memberEntity, sortedPageable);
	    
	    model = new AttendanceMapper();
	    return entityList.map(model::VoToDTO);
	    
	} // findAllByAttendanceDate 

	@Override
	public Optional<AttendanceDTO> findByMemberId(String memberId) throws FindException {
		
		log.warn("1. findByMemberId Service : {}", memberId);
		
		// 현재 날짜 구하기
		LocalDateTime currentDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		
		// 멤버 아이디가 0003인 경우
//		MemberEntity memberEntity = new MemberEntity();
//		memberEntity.setId(memberId);
		
		Optional<AttendanceEntity> entity = repository.findByMemberIdAndAttendanceDate(memberId, formattedDate);
		
		model = new AttendanceMapper();
		
		return entity.map(model::VoToDTO);
	
	} // findByMemberId

	
	
	
} // end class
