package com.my.car.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;

public interface CarService {
	public Page<CarDTO> findCarList(Pageable pageable);
}
