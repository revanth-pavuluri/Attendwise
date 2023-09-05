package com.ams.app.dto;
import java.sql.Timestamp;
import javax.persistence.Id;
import lombok.Data;

@Data
public class Facultydto {
  @Id
  private int id;
  private String username;
  private String name;
  private String role;
  private Timestamp createdon;
  private Timestamp updatedon;
}
