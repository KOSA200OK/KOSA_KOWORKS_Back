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
	
	@Scheduled(cron = "0 14 19 * * ?")
	@Transactional
	public void carStatusScheduler(){
//		LocalDate today = LocalDate.now();
//		System.out.println("*****today : "+today);
		cs.modifyCarStatus();
		System.out.println("실행됨");
	}
}
