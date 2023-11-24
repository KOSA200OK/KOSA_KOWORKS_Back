package com.my.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.car.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, String> {
	
}
