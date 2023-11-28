package com.my.attendance.service;

public enum AttendanceStatus {
	
    ON(0),
    OFF(1),
    LATE(2),
    ABSENCE(3),
    SICK(4),
    VACATION(5);
    
    private final int status;

    AttendanceStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    
}
