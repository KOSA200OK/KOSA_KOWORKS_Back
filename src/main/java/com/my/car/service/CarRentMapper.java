package com.my.car.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;

import com.my.car.dto.CarRentDTO;
import com.my.car.entity.CarRentEntity;
import com.my.car.repository.CarRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarRentMapper {
	public CarRentEntity dtoToEntity(CarRentDTO dto) {
		CarRepository cr;
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);

		Object source = dto;
		
		// 필드명이 일치하지 않는 경우 명시적으로 매핑
//        mapper.addMappings(new PropertyMap<CarRentDTO, CarRentEntity>() {
//            @Override
//            protected void configure() {
//                map().getCar().setId(source.getCar().getId());
//                map().getMember().setId(source.getMember().getId());
//            }
//        });
		
		Class<CarRentEntity> destinationType = CarRentEntity.class;
		CarRentEntity entity = mapper.map(source, destinationType); //DTO->VO

		return entity;
	}
	
	public CarRentDTO entityToDto(CarRentEntity entity) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<CarRentDTO> destinationType = CarRentDTO.class;
		CarRentDTO dto = mapper.map(source, destinationType);
		
		return dto;
	}
}
