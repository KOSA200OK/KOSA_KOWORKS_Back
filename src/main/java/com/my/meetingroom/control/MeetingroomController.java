package com.my.meetingroom.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.MeetingReservationRequest;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.service.MeetingroomServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/meetingroom")
@CrossOrigin(origins="http://localhost:5173")
@Slf4j
public class MeetingroomController {	
	
	@Autowired
	MeetingroomServiceImpl service;
	
	@GetMapping("")
	public List<MeetingReservationDTO> findAllMeetingRoom(@RequestParam String meetingDate) throws FindException {
		return service.findAllMeetingRoom(meetingDate);
	}
	
	@GetMapping("/{id}")
	public Optional<MeetingReservationDTO> findById(@PathVariable Long id) throws FindException {
		return service.findById(id);
	}
	
	@PostMapping(value="", produces="application/json;charset=UTF-8") //회의실 예약하기(저장)
	public ResponseEntity<?> createMeetingReservation(@RequestBody MeetingReservationDTO mrdto) throws AddException {
		try {
			service.createMeetingReservation(mrdto);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			return new ResponseEntity<>("저장되었습니다", headers, HttpStatus.OK);			
		} catch (AddException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
//	@PostMapping(value="", produces="application/json;charset=UTF-8") //회의실 예약하기(저장)
//	public ResponseEntity<?> createMeetingReservation(@RequestBody MeetingReservationRequest request) throws AddException {
//		MeetingReservationDTO meetingReservationDTO = request.getMeetingReservationDTO();
//		List<String> participantsMemberId = request.getParticipantsMemberId();
//		
//		try {
//			service.createMeetingReservation(meetingReservationDTO, participantsMemberId);
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Type", "text/html; charset=UTF-8");
//			return new ResponseEntity<>("저장되었습니다", headers, HttpStatus.OK);			
//		} catch (AddException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
}
