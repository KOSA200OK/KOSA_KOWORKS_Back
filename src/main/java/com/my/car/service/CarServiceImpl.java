package com.my.car.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.entity.CarEntity;
import com.my.car.entity.CarRentEntity;
import com.my.car.repository.CarRentRepository;
import com.my.car.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
	private CarRepository cr;
	private CarRentRepository crr;
	
	@Autowired
	public CarServiceImpl(CarRepository cr, CarRentRepository crr) {
		this.cr = cr;
		this.crr = crr;
	}
	
	@Transactional
	public void modifyCarStatus() {
		LocalDate today = LocalDate.now();
		
//		String todaystring = "2023-12-01";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate today = LocalDate.parse(todaystring, formatter);
//		System.out.println("*************service: "+today);
		
		cr.saveCarStatus(today);
	}
	
	//*************** 차량 목록 **************************
	
	@Override
	public Page<CarDTO> findAllCar(Pageable pageable) {
		Page<CarEntity> entityList = cr.findAllByOrderByStatusAscIdDesc(pageable);
		CarMapper cm = new CarMapper();
		return entityList.map(cm::entityToDto);
	}
	
	@Override
	public void createCarRent(CarRentDTO carRent) {
		CarRentMapper crm = new CarRentMapper();
		CarRentEntity entity = crm.dtoToEntity(carRent);
		System.out.println("entity carId   "+entity.getCar().getId());
		crr.save(entity);
		
		Optional<CarEntity> optC = cr.findById(carRent.getCar().getId());
		CarEntity carEntity = optC.get();
		carEntity.modifyCarStatus((long)1);
		cr.save(carEntity);
	}
	
	//****************** 차량 대여 목록 **************************
	
	
	@Override
	public Page<CarRentDTO> findAllMyCarRent(Pageable pageable, String memberId) {
		Page<CarRentEntity> entityList = crr.findAllByMemberIdOrderByReqDateDesc(pageable, memberId);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
	
	@Override
	public void removeByIdCarRent(Long id) {
		crr.deleteById(id);
	}
	
	
	//***************** 차량 관리 메인 ************************
	
//	@Override
//	public Page<CarDTO> findAllCarManage(Pageable pageable){
//		Page<CarEntity> entityList = cr.findAllCarManage(pageable);
//		CarMapper cm = new CarMapper();
//		return entityList.map(cm::entityToDto);
//	}
}
