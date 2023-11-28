package com.my.attendance.control;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<AttendanceDTO> findByMemberEntity(@RequestParam Long memberId) throws FindException {
		
//		Long LongMemberId = (long)memberId;
		
	    log.warn("Controller memberId ==> {}", memberId);
	    
	    return service.findAllByMemberId(memberId);
		
	} // findByMemberId


	@PostMapping()
	public ResponseEntity<?> createAttendance(@RequestBody AttendanceDTO dto) throws AddException {
		
		try {
			service.createAttendance(dto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	} // createAttendance
	
	@PutMapping()
	public ResponseEntity<?> updateAttendance(@RequestBody AttendanceDTO dto) throws ModifyException {
		
		try {
			service.updateAttendance(dto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ModifyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	} // updateAttendance
	
} // end class
