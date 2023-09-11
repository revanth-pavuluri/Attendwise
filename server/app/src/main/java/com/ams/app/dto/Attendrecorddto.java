package com.ams.app.dto;
import java.sql.Date;
import java.sql.Timestamp;

import com.ams.app.model.AttendRecord.AttendanceStatus;

import lombok.Data;

@Data
public class AttendrecordDTO {
    private int id;
    private FacultyDTO faculty;
    private SubjectDTO subject;
    private int periods;
    private AttendanceStatus status;
    private String className;
    private Date date;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}