package com.my.schedule.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.my.car.dto.CarDTO;
import com.my.schedule.dto.ScheduleDTO;

@Service
public interface ScheduleService {
	public List<ScheduleDTO> findAllSchedule(String memberId);
}
