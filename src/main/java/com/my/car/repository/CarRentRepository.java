package com.my.car.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.car.entity.CarEntity;
import com.my.car.entity.CarRentEntity;

public interface CarRentRepository extends JpaRepository<CarRentEntity, Long>{
//	@Query(value = "SELECT * FROM car_rent WHERE member_id = :memberId", nativeQuery=true)
	/**
	 * 사원아이디에 해당되는 행들을 신청날짜 순서대로 반환한다
	 * @param pageable 페이징 요청 객체
	 * @param memberId 사원아이디
	 * @return 사원아이디에 해당되는 신청 내역
	 */
	Page<CarRentEntity> findAllByMemberIdOrderByReqDateDesc(Pageable pageable, String memberId);
	
	/**
	 * status값에 따른 차량 대여 리스트를 신청날짜 순서대로 페이징하여 반환한다.
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @param status 신청상태
	 * @return status값에 따른 차량 대여 리스트
	 */
	Page<CarRentEntity> findAllByStatusOrderByReqDate(Pageable pageable, Long status);
	
	/**
	 * status값에 따른 차량 대여 리스트를 신청날짜 순서대로 페이징하여 반환한다.
	 * @author 나원희
	 * @param status 신청상태
	 * @return status값에 따른 차량 대여 리스트
	 */
	List<CarRentEntity> findAllByStatusOrderByReqDate(Long status);
	
	/**
	 * 대여기간에 속하고, status값이 2인 대여 리스트를 신청날짜 순서대로 페이징하여 조회한다.
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 대여기간에 속하고, status값이 2인 대여 리스트 (페이징)
	 */
	@Query("SELECT cr FROM CarRentEntity cr LEFT JOIN cr.car c " +
	           " WHERE cr.status = 2 AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN cr.startDate AND cr.endDate"+
				" ORDER BY cr.reqDate DESC")
	Page<CarRentEntity> findAllRentList(Pageable pageable);
	
	/**
	 * 대여기간에 속하고, status값이 2인 대여 리스트를 신청날짜 순서대로 조회한다.
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 대여기간에 속하고, status값이 2인 대여 리스트
	 */
	@Query("SELECT cr FROM CarRentEntity cr LEFT JOIN cr.car c " +
	           " WHERE cr.status = 2 AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN cr.startDate AND cr.endDate"+
				" ORDER BY cr.reqDate DESC")
	List<CarRentEntity> findAllRentList();
	
	/**
	 * 대여기간이 지났고, status값이 2인 대여 리스트를 신청날짜 순서대로 페이징하여 조회한다.
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 대여기간이 지났고, status값이 2인 대여 리스트 (페이징)
	 */
	@Query("SELECT cr FROM CarRentEntity cr LEFT JOIN cr.car c " +
	           " WHERE cr.status = 2 AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') > cr.endDate"+
				" ORDER BY cr.reqDate DESC")
	Page<CarRentEntity> findAllNoReturnList(Pageable pageable);
	
	/**
	 * 대여기간이 지났고, status값이 2인 대여 리스트를 신청날짜 순서대로 조회한다.
	 * @author 나원희
	 * @return 대여기간이 지났고, status값이 2인 대여 리스트
	 */
	@Query("SELECT cr FROM CarRentEntity cr LEFT JOIN cr.car c " +
	           " WHERE cr.status = 2 AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') > cr.endDate"+
				" ORDER BY cr.reqDate DESC")
	List<CarRentEntity> findAllNoReturnList();
	
	/**
	 * 신청내역을 신청날짜 순서대로 조회한다
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 페이징된 모든 신청내역
	 */
	Page<CarRentEntity> findAllByOrderByReqDateDesc(Pageable pageable);
}
