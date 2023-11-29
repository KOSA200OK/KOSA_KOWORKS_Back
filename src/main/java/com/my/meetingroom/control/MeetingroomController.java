package com.my.meetingroom.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.FindException;
import com.my.meetingroom.dto.MeetingReservationDTO;
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
}
