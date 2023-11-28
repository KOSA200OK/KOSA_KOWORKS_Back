package com.my.car.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.car.entity.CarEntity;
import com.my.car.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
	private CarRepository cr;
	
	@Autowired
	public CarServiceImpl(CarRepository cr, CarMapper cm) {
		this.cr = cr;
	}
	
	@Override
	public Page<CarDTO> findCarList(Pageable pageable) {
		Page<CarEntity> entityList = cr.findAllByOrderByStatusAscIdDesc(pageable);
		CarMapper cm = new CarMapper();
		return entityList.map(cm::entityToDto);
	}

}
