package com.ams.app.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class StudentDashboardRequestDTO {
  private Date date;
  private Long studentId;
}
