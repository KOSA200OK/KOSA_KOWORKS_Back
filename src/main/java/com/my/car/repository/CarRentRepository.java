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
	
	Page<CarRentEntity> findAllByStatusOrderByReqDate(Pageable pageable, Long status);
	
	@Query("SELECT cr FROM CarRentEntity cr LEFT JOIN cr.car c " +
	           " WHERE cr.status = 2 AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN cr.startDate AND cr.endDate"+
				" ORDER BY cr.reqDate DESC")
	Page<CarRentEntity> findAllRentList(Pageable pageable);
	
	@Query("SELECT cr FROM CarRentEntity cr LEFT JOIN cr.car c " +
	           " WHERE cr.status = 2 AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') > cr.endDate"+
				" ORDER BY cr.reqDate DESC")
	Page<CarRentEntity> findAllNoReturnList(Pageable pageable);
	
	Page<CarRentEntity> findAllByOrderByReqDateDesc(Pageable pageable);
}
