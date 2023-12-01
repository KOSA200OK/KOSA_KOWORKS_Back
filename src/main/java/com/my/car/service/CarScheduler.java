package com.my.car.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.my.car.entity.CarEntity;

@Component
public class CarScheduler {
	@Autowired
	private CarService cs;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void carStatusScheduler(){
		cs.modifyCarStatus();
	}
}
