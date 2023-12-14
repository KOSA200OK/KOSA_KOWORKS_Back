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
	
	/**
	 * status값이 0 혹은 2이고, 설정한 대여시작날짜, 설정한 대여마감날짜가 기존에 겹치지 않는 행을 조회한다.
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @param start 설정한 대여시작날짜
	 * @param end 설정한 대여마감날짜
	 * @return 페이징된 신청 가능 차량 목록
	 */
	@Query("SELECT c "+ 
			" FROM CarEntity c "+
			" WHERE c.id NOT IN "+ " ( SELECT cr.car.id " + 
			" FROM CarRentEntity cr " +
			" WHERE ( cr.status = 2 OR cr.status = 0 ) AND ( ( :start BETWEEN cr.startDate AND cr.endDate) OR ( :end BETWEEN cr.startDate AND cr.endDate) OR ( cr.startDate BETWEEN :start AND :end ) OR ( cr.endDate BETWEEN :start AND :end ) ) ) "+
			" ORDER BY c.id DESC")
	Page<CarEntity> findAllCarList(Pageable pageable, String start, String end);
//	@Query("SELECT c "+ 
//			" FROM CarEntity c "+
//			" WHERE c.id NOT IN "+ " ( SELECT cr.car.id " + 
//			" FROM CarRentEntity cr " +
//			" WHERE cr.status = 2 OR cr.status = 0 ) "+
//			" ORDER BY c.id DESC")
//	Page<CarEntity> findAllCarList(Pageable pageable);

	
	@Transactional
	@Modifying
	@Query(value="UPDATE CAR"+ " SET status = 0" + 
					" WHERE id IN "+"(SELECT r.car_id "+
					" FROM CAR_RENT r "+
					"WHERE r.end_date < :today)", nativeQuery=true)
	void saveEndCarStatus(LocalDate today);
	
	/**
	 * 페이징된 차량 목록
	 * @author 나원희
	 * @pageable 페이징 요청객체
	 * @return 페이징된 차량 목록
	 */
	Page<CarEntity> findAll(Pageable pageble);
	
//	Page<CarEntity> findAllByOrderByStatusDescIdDesc(Pageable pageble);
	
//	@Query("SELECT c FROM CarEntity c LEFT JOIN c.CarEntity cr " +
//	           "ORDER BY cr.status DESC, cr.startDate DESC")
//	Page<CarEntity> findAllCarManage(Pageable pageble);
	
}