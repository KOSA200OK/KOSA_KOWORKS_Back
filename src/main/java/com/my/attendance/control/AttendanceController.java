package com.my.attendance.control;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.service.AttendanceService;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins="http://localhost:5173")
@Slf4j
public class AttendanceController {

	@Autowired
	private AttendanceService service;

//	인사팀
//	@GetMapping()
//	public List<AttendanceEntity> findAll() throws FindException {
//		
//		return service.findAll();
//		
//	}
	
	@GetMapping()
	public Page<AttendanceDTO> findByMemberEntity(@RequestParam String memberId, int currentPage) throws FindException {
		
//		Long LongMemberId = (long)memberId;
		
	    log.warn("Controller memberId ==> {}", memberId);
	    
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
	    
	    return service.findAllByMemberId(memberId, pageable);
		
	} // findByMemberId


	@PostMapping()
	public ResponseEntity<?> createAttendance(@RequestBody AttendanceDTO dto) throws AddException {
		
//		log.warn("Controller dto ===> {}", dto.getMemberId());
		
		try {
			service.createAttendance(dto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	} // createAttendance
	
	@PutMapping()
	public ResponseEntity<?> modifyAttendance(@RequestBody AttendanceDTO dto) throws ModifyException {
		
//		log.warn("put dto ======> {}", dto.getMemberId());
		
		try {
			service.modifyAttendance(dto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ModifyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	} // updateAttendance
	
} // end class
