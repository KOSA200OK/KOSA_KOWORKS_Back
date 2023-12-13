package com.my.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "attendance")
public class AttendanceConfig {

//	@Value("#{'${attendance.status}'.split(',')}")
	private List<Integer> status;
	
//	 @Value("#{'${attendance.time}'.split(',')}")
	private List<String> time;
	
	
}
