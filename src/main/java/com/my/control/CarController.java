package com.my.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.service.CarService;
import com.my.exception.AddException;
import com.my.exception.FindException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/carrent")
@CrossOrigin(origins="http://localhost:5173")
public class CarController {
	@Autowired
	private CarService cs;
	
	@GetMapping("/carlist")
	public Page<CarDTO> carList(int currentPage) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findCarList(pageable);
	}
	
	@PostMapping("/reserve")
	public void reserve(@RequestBody CarRentDTO carRent) throws AddException{
		System.out.println(carRent.getCarId()+" "+carRent.getStartDate()+" "+carRent.getEndDate());
		cs.addCarRent(carRent);
	}
}
