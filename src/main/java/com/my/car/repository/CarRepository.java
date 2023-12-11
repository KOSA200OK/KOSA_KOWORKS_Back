package com.my.car.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

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
//	Page<CarEntity> findAllByOrderByStatusAscIdDesc(Pageable pageble);
	@Query("SELECT c "+ 
			" FROM CarEntity c "+
			" WHERE c.id NOT IN "+ " ( SELECT cr.car.id " + 
			" FROM CarRentEntity cr " +
			" WHERE cr.status = 2 AND ( ( :start BETWEEN cr.startDate AND cr.endDate) OR ( :end BETWEEN cr.startDate AND cr.endDate) OR ( cr.startDate BETWEEN :start AND :end ) OR ( cr.endDate BETWEEN :start AND :end ) ) ) "+
			" ORDER BY c.id DESC")
	Page<CarEntity> findAllCarList(Pageable pageable, String start, String end);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE CAR"+ " SET status = 0" + 
					" WHERE id IN "+"(SELECT r.car_id "+
					" FROM CAR_RENT r "+
					"WHERE r.end_date < :today)", nativeQuery=true)
	void saveEndCarStatus(LocalDate today);
	
	Page<CarEntity> findAll(Pageable pageble);
	
//	Page<CarEntity> findAllByOrderByStatusDescIdDesc(Pageable pageble);
	
//	@Query("SELECT c FROM CarEntity c LEFT JOIN c.CarEntity cr " +
//	           "ORDER BY cr.status DESC, cr.startDate DESC")
//	Page<CarEntity> findAllCarManage(Pageable pageble);
	
}