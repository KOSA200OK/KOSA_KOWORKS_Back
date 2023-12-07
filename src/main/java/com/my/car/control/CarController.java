package com.my.car.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/carrent")
@CrossOrigin(origins="http://localhost:5173")
public class CarController {
	@Autowired
	private CarService cs;
	

	//****************** 챠량 목록 ******************

	
	@GetMapping("/carlist/{currentPage}")
	public Page<CarDTO> findAllCarList(@PathVariable int currentPage, @RequestParam String startDate,  @RequestParam String endDate) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		System.out.println("**********startDate: "+startDate+", endDate: "+endDate);
		return cs.findAllCarList(pageable, startDate, endDate);
	}
	
//	@GetMapping("/dateselectcarlist/{currentPage}")
//	public Page<CarDTO> findAllCarByDateSelect(@PathVariable int currentPage) throws FindException{
//		System.out.println("currentPage: "+currentPage);
//		currentPage -=1;
//		Pageable pageable = PageRequest.of(currentPage, 10);
//		return cs.findAllCarList(pageable);
//	}
	
	@PostMapping("/reserve")
	public void createCarRent(@RequestBody CarRentDTO carRent) throws AddException{
		System.out.println(carRent.getCar().getId()+" "+carRent.getStartDate()+" "+carRent.getEndDate());
		cs.createCarRent(carRent);
	}
	

	//****************** 나의 차량 대여 목록 ******************

	@GetMapping("/myrentlist")
	public Page<CarRentDTO> findAllMyCarRent(String memberId, int currentPage) throws AddException{
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllMyCarRent(pageable, memberId);
	}
	
	@DeleteMapping("/cancelreserve/{id}")
	public ResponseEntity<?> removeByIdCarRent(@PathVariable Long id) {
		cs.removeByIdCarRent(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//******************* 차량 관리 메인 ***********************
	
	@GetMapping("/managelist/{currentPage}")
	public Page<CarDTO> findAllCarManage(@PathVariable int currentPage) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllCarManage(pageable);
	}
	
	//******************* 차량 관리 승인 ***********************
	
	@GetMapping("/waitinglist/{currentPage}")
	public Page<CarRentDTO> findAllApprove(@PathVariable int currentPage) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllApprove(pageable);
	}
	
	@GetMapping("/approve")
	public ResponseEntity<?> modifyCarRentStatusApprove(Long id, Long status){
//		HttpSession session = request.getSession();
//		Long memberId = (Long) session.getAttribute("memberId");
		cs.modifyCarRentStatus(id, (long)2);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/reject")
	public ResponseEntity<?> modifyCarRentStatusReject(@RequestBody CarRentDTO carRent){
		cs.saveCarRentReject(carRent,(long)1);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//******************* 차량 관리 대여중 ***********************
	
	@GetMapping("/rentlist/{currentPage}")
	public Page<CarRentDTO> findAllRentList(@PathVariable int currentPage){
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllRentList(pageable);
	}
	
	//******************* 차량 관리 미반납 ***********************
	
	@GetMapping("/noreturnlist/{currentPage}")
	public Page<CarRentDTO> findAllNoReturnList(@PathVariable int currentPage){
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllNoReturnList(pageable);
	}
	
	//******************* 차량 관리 모든 예약 내역 ***********************
	
	@GetMapping("/allrentlist/{currentPage}")
	public Page<CarRentDTO> findAllRentListAll(@PathVariable int currentPage){
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllRentListAll(pageable);
	}
	
	
	
//	@GetMapping("/test")
//	public void test() {
//		cs.modifyCarStatus();
//	}
}
