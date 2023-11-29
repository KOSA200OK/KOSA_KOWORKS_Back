package com.my.car.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.service.CarService;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.member.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/carrent")
@CrossOrigin(origins="http://localhost:5173")
public class CarController {
	@Autowired
	private CarService cs;
	
	
	//****************** 챠량 목록 ******************
	
	@GetMapping("/carlist")
	public Page<CarDTO> findAllCar(int currentPage) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllCar(pageable);
	}
	
	@PostMapping("/reserve")
	public void createCarRent(@RequestBody CarRentDTO carRent) throws AddException{
		System.out.println(carRent.getCar().getId()+" "+carRent.getStartDate()+" "+carRent.getEndDate());
		cs.createCarRent(carRent);
	}
	
	
	//****************** 나의 차량 대여 목록 ******************
	
	@GetMapping("/myrentlist")
	public Page<CarRentDTO> findAllMyCarRent(String memberId, int currentPage) throws AddException{
		log.warn("1. Controller id : {}", memberId);
		
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllMyCarRent(pageable, memberId);
	}
}
