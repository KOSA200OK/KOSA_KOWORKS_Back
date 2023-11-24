package com.my.car.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;

public interface CarService {
	public List<CarDTO> findCarList(Long carId);
}
