package com.my.car.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.entity.CarRentEntity;

public interface CarService {
	public void modifyCarStatus();
	
	public Page<CarDTO> findAllCar(Pageable pageable);
	
	public void createCarRent(CarRentDTO carRent);

	public Page<CarRentDTO> findAllMyCarRent(Pageable pageable, String memberId);
	
	public void removeByIdCarRent(Long id);
	
	public Page<CarDTO> findAllCarManage(Pageable pageable);
	
	public Page<CarRentDTO> findAllApprove(Pageable pageable);
	
	public void modifyCarRentStatus(Long id, Long status);
	
	public void saveCarRentReject(CarRentDTO carRent, Long status);

	public Page<CarRentDTO> findAllRentList(Pageable pageable);
	
	public Page<CarRentDTO> findAllNoReturnList(Pageable pageable);
}
