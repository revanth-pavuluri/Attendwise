package com.ams.app.dto;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class FacultyDTO {
  private int id;
  private String username;
  private String name;
  private String role;
  private Timestamp createdOn;
  private Timestamp updatedOn;
}
