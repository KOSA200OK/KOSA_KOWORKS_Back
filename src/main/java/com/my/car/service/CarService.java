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
	
	public Page<CarDTO> findAllCarList(Pageable pageable);
	
//	public Page<CarDTO> findAllCarByDateSelect(Pageable pageable);
	
	public void createCarRent(CarRentDTO carRent);

	public Page<CarRentDTO> findAllMyCarRent(Pageable pageable, String memberId);
	
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
	
	public void modifyCarRentStatus(Long id, Long status);
	
	public void saveCarRentReject(CarRentDTO carRent, Long status);

	public Page<CarRentDTO> findAllRentList(Pageable pageable);
	
	public Page<CarRentDTO> findAllNoReturnList(Pageable pageable);
	
	public Page<CarRentDTO> findAllRentListAll(Pageable pageable);
}
