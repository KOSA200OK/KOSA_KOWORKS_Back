package com.my.schedule.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.schedule.dto.ScheduleDTO;
import com.my.schedule.entity.ScheduleEntity;
import com.my.schedule.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	private ScheduleRepository sr;
	
	@Autowired
	public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
		this.sr = sr;
	}
	
	@Override
	public List<ScheduleDTO> findAllSchedule(String memberId, Long month) {
		List<ScheduleEntity> entityList = sr.findAllSchedule(memberId, month);
		List<ScheduleDTO> dtoList = new ArrayList<>();
		for(ScheduleEntity entity : entityList) {			
			ScheduleMapper sm = new ScheduleMapper();
			ScheduleDTO dto = sm.entityToDto(entity);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
}
