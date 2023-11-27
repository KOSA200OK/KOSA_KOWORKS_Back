package com.my.control;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.my.car.dto.CarDTO;
import com.my.car.service.CarService;
import com.my.exception.FindException;

import lombok.extern.slf4j.Slf4j;

@Controller
public class CarController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CarService cs;
	
	@GetMapping("/carrent/carlist")
	public List<CarDTO> list() throws FindException{
		List<CarDTO> list = new ArrayList<>();
		list = cs.findCarList();
		for (CarDTO car : list) {
            System.out.print(car + " ");
        }
		return cs.findCarList();
	}
}
