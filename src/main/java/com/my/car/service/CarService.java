package com.my.car.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.entity.CarRentEntity;

public interface CarService {
	public void modifyCarStatus();
	
	/**
	 * 차량 대여 페이지에 들어가면 바로 나오는 신청 가능 차량 목록을 조회한다.
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @param startDate 설정한 대여시작날짜
	 * @param endDate 설정한 대여마감날짜
	 * @return 페이징한 신청 가능한 목록
	 */
	public Page<CarDTO> findAllCarList(Pageable pageable, String startDate, String endDate);
	
//	public Page<CarDTO> findAllCarByDateSelect(Pageable pageable);
	
	/**
	 * 차량 대여신청을 예약한다
	 * @author 나원희
	 * @param carRent 신청서 내용
	 */
	public void createCarRent(CarRentDTO carRent);
	
	/**
	 * 나의 신청 내역을 조회한다
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @param memberId 사원 아이디(나)
	 * @return 나의 대여 신청 내역
	 */
	public Page<CarRentDTO> findAllMyCarRent(Pageable pageable, String memberId);
	
	/**
	 * 대여신청을 취소한다
	 * @author 나원희
	 * @param id 취소할 신청의 아이디
	 */
	public void removeByIdCarRent(Long id);
	
	
	/**
	 * 차량 관리 메인 페이지 지도에 표시할 차량 리스트, 승인대기/대여중/미반납 리스트를 조회한다.
	 * @author 나원희
	 * @return 차량리스트, 승인대기/대여중/미반납 리스트
	 */
	public Map findAllCarManage();
	
	/**
	 * 차량 관리 메인 페이지에 출력할 차량리스트를 페이징하여 조회한다.
	 * @author 나원희
	 * @param pageable
	 * @return 페이징된 차량리스트
	 */
	public Page<CarDTO> findAllCarManageList(Pageable pageable);
	
	/**
	 * 대여 승인 대기중인 차량을 조회한다.
	 * @author 나원희
	 * @param pageable
	 * @return 승인 대기중인 차량 리스트
	 */
	public Page<CarRentDTO> findAllWaiting(Pageable pageable);
	
	/**
	 * 대여신청 상태를 변경한다
	 * @author 나원희
	 * @param id 대여신청 아이디
	 * @param status 대여상태
	 */
	public void modifyCarRentStatus(Long id, Long status);
	
	/**
	 * 대여신청을 반려한다
	 * @author 나원희
	 * @param carRent 반려할 대여신청 내역
	 * @param status 대여상태
	 */
	public void saveCarRentReject(CarRentDTO carRent, Long status);
	
	/**
	 * 대여중인 신청내역을 조회한다
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 대여중인 신청내역
	 */
	public Page<CarRentDTO> findAllRentList(Pageable pageable);
	
	/**
	 * 미반납인 신청내역을 조회한다
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 미반납인 신청내역
	 */
	public Page<CarRentDTO> findAllNoReturnList(Pageable pageable);
	
	/**
	 * 역대 신청내역을 모두 조회한다
	 * @author 나원희
	 * @param pageable 페이징 요청 객체
	 * @return 역대 신청내역
	 */
	public Page<CarRentDTO> findAllRentListAll(Pageable pageable);
	
	/**
	 * 승인대기중/대여중/미반납 신청 개수를 조회한다
	 * @author 나원희
	 * @return 승인대기중/대여중/미반납 차량 수
	 */
	public Map<String, Object> countMainCarRentCount();
	
	/**
	 * 내 승인대기중/대여중/미반납 신청 개수를 조회한다
	 * @author 나원희
	 * @return 승인대기중/대여중/미반납 신청 개수
	 */
	public Map<String, Object> countMainMyCarRentCount(String memberId);
}
