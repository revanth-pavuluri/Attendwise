package com.ams.app.dto;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class SubjectDTO {
  private Long id;
  
  private String name;
  
  private String code;
  
  private Timestamp createdOn;
  
  private Timestamp updatedOn;   
}
