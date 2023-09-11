package com.ams.app.dto;

import lombok.Data;

@Data
public class AttendanceReportDTO {
    private int studentId;
    private String username;
    private String name;
    private int rollNumber;
    private String studentStatus;
}
