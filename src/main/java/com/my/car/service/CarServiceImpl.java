package com.my.car.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<CarDTO> findCarList(Long carId) {
		List<CarEntity> entityList = cr.findAll();
		List<CarDTO> list = new ArrayList<>();
		CarMapper cm = new CarMapper();
		
		for(CarEntity entity : entityList) {
			CarDTO dto = new CarDTO();
			dto = cm.entityToDto(entity);
			list.add(dto);
		}
		
		return list;
	}
}
