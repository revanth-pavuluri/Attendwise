package com.ams.app.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Studentdashresponsedto {
  private int aid;
  private String facultyname;
  private String subjectname;
  private String subjectcode;
  private int periods;
  private int status;
  private String type;
  private String Studentstatus;
  private Timestamp createdon;
  private Timestamp updatedon;
}
