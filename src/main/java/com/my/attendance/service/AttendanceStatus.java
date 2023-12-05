package com.my.attendance.service;

public enum AttendanceStatus {
	
    ON(0),						// 출근
    OFF(1),						// 퇴근
    LATE(2),					// 지각
    ABSENCE(3),					// 결근
    SICK(4),					// 병가
    VACATION(5);				// 휴가
    
    private final int status;

    AttendanceStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    
}
