package com.my.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.car.entity.CarEntity;
import com.my.car.entity.CarRentEntity;

public interface CarRentRepository extends JpaRepository<CarRentEntity, Long>{
	
}
