package com.my.schedule.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.schedule.dto.ScheduleDTO;

@Service
public interface ScheduleService {
	/**
	 * 개인의 일정을 모두 조회한다
	 * @author 나원희
	 * @param memberId 사원아이디
	 * @return 개인 일정
	 */
	public List<ScheduleDTO> findAllSchedule(String memberId);
	
	/**
	 * 일정을 추가한다
	 * @author 나원희
	 * @param schedule 추가할 일정
	 */
	public void createSchedule(ScheduleDTO schedule);
	
	/**
	 * 일정을 수정한다
	 * @author 나원희
	 * @param schedule 수정할 일정 내용
	 */
	public void modifySchedule(ScheduleDTO schedule);
	
	/**
	 * 일정을 삭제한다
	 * @author 나원희
	 * @param id 삭제할 일정 아이디
	 */
	public void removeByIdSchedule(Long id);
	
	/**
	 * 개인의 오늘 일정을 모두 조회한다
	 * @authour 나원희
	 * @param membeId 사원아이디
	 * @return 오늘의 개인 일정
	 */
	public List<ScheduleDTO> findAllTodaySchedule(String membeId);
}
