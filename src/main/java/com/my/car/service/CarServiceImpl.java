package com.my.car.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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
import com.my.member.dto.MemberDTO;
import com.my.member.entity.MemberEntity;
import com.my.member.service.MemberMapper;

@Service
public class CarServiceImpl implements CarService {
	private CarRepository cr;
	private CarRentRepository crr;
	
	@Autowired
	public CarServiceImpl(CarRepository cr, CarRentRepository crr) {
		this.cr = cr;
		this.crr = crr;
	}
	
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
		crr.save(entity);
		
		Optional<CarEntity> optC = cr.findById(carRent.getCar().getId());
		CarEntity carEntity = optC.get();
		carEntity.modifyCarStatus((long)1);
		cr.save(carEntity);
	}
	
	@Override
	public Page<CarRentDTO> findAllMyCarRent(Pageable pageable, String memberId) {
		System.out.println("memberId : "+memberId);
//		MemberMapper mm = new MemberMapper();
//		MemberEntity mEntity = mm.dtoToEntity(member);
//		MemberEntity mEntity = new MemberEntity();
//		mEntity.builder()
//				.id(memberId)
//				.build();
		Page<CarRentEntity> entityList = crr.findByMember(pageable, memberId);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
}
