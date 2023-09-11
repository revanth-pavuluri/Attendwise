package com.ams.app.dto;

import java.sql.Timestamp;

import com.ams.app.model.AttendRecord.AttendanceStatus;

import lombok.Data;

@Data
public class StudentDashboardResponseDTO {
  private int attendRecordId;
  private FacultyDTO faculty;
  private SubjectDTO subject;
  private int periods;
  private AttendanceStatus status;
  private String studentStatus;
  private Timestamp createdOn;
  private Timestamp updatedOn;
}
