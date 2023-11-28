package com.my.car.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import com.my.car.dto.CarRentDTO;
import com.my.car.entity.CarRentEntity;
import com.my.car.repository.CarRepository;

public class CarRentMapper {
	public CarRentEntity dtoToEntity(CarRentDTO dto) {
		CarRepository cr;
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		    .setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<CarRentEntity> destinationType = CarRentEntity.class;
		
        
		CarRentEntity entity = mapper.map(source, destinationType); //DTO->VO
		
		CarRentEntity carEntity = cr.findById(dto.getCarId())
				.orElseThrow(() -> new EntityNotFoundException("Car not found with ID: " + dto.getCarId()));
		entity.setCar(carEntity);

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
