package com.ams.app.dto;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class StudentDTO {
  private int id;
  private String username;
  private String name;
  private String device;
  private int rollNumber;
  private String className;
  private Timestamp createdOn;
  private Timestamp updatedOn;
}
