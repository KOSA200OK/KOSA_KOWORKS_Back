package com.my.schedule.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.schedule.dto.ScheduleDTO;

@Service
public interface ScheduleService {
	/**
	 * 개인의 일정을 모두 조회한다
	 * @param memberId 사원아이디
	 * @return 개인 일정
	 */
	public List<ScheduleDTO> findAllSchedule(String memberId);
	
	/**
	 * 일정을 추가한다
	 * @param schedule 추가할 일정
	 */
	public void createSchedule(ScheduleDTO schedule);
}
