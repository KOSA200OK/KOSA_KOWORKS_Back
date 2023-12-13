package com.my.schedule.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.my.car.control.CarController;
import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.service.CarService;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.schedule.dto.ScheduleDTO;
import com.my.schedule.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/schedule")
@CrossOrigin(origins="http://localhost:5173")
public class ScheduleController {
	@Autowired
	private ScheduleService ss;
	
	@GetMapping("/calendar")
	public List<ScheduleDTO> findAllSchedule(String memberId) throws FindException{
		return ss.findAllSchedule(memberId);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> createCarRent(@RequestBody ScheduleDTO schedule) throws AddException{
		System.out.println("*****memberId: "+schedule.getMember().getId());
		ss.createSchedule(schedule);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
