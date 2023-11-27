package com.my.attendance.service;

import java.time.LocalTime;

public enum AttendanceTime {

	ON_TIME(LocalTime.of(9, 0, 0), LocalTime.of(10, 0, 0)), 
    OFF_TIME(LocalTime.of(17,0, 0), LocalTime.of(18,0, 0));

    LocalTime startTime; // 첫 번째 시간 (시작 시간)
    LocalTime endTime; // 두 번째 시간 (종료 시간)

    AttendanceTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
	
}
