package com.my.car.repository;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.car.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, String> {
	Page<CarEntity> findAllByOrderByStatusAscIdDesc(Pageable pageble);
	@Transactional
	@Modifying
	@Query(value="UPDATE CAR"+ " SET status = 0" + 
					" WHERE id IN "+"(SELECT r.car_id "+
					" FROM CAR_RENT r "+
					"WHERE r.end_date < :today)", nativeQuery=true)
//	void saveCarStatus(LocalDate today);
	void saveCarStatus(LocalDate today);
}