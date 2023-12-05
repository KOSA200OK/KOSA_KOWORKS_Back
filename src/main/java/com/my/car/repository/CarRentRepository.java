package com.my.car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.car.entity.CarEntity;
import com.my.car.entity.CarRentEntity;

public interface CarRentRepository extends JpaRepository<CarRentEntity, Long>{
//	@Query(value = "SELECT * FROM car_rent WHERE member_id = :memberId", nativeQuery=true)
	Page<CarRentEntity> findAllByMemberIdOrderByReqDateDesc(Pageable pageable, String memberId);
}
