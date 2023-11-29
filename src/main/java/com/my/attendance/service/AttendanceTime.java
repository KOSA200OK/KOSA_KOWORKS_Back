package com.my.attendance.service;

import java.time.LocalTime;

public enum AttendanceTime {

	ON_TIME(LocalTime.of(9, 0, 0)), 
	OFF_TIME(LocalTime.of(17,0, 0)),
	LATE_TIME(LocalTime.of(12, 0, 0)),
	ABSENCE_TIME(LocalTime.of(12, 1, 0));

    LocalTime time; // 첫 번째 시간 (시작 시간)
//    LocalTime endTime; // 두 번째 시간 (종료 시간)

//    AttendanceTime(LocalTime startTime, LocalTime endTime) {
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
    
	 AttendanceTime(LocalTime time) {
	  this.time = time;

	}

    public LocalTime getTime() {
        return time;
    }

}
