package com.ams.app.dto;

import java.sql.Timestamp;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Studentdto {
  @Id
  private int id;
  private String username;
  private String name;
  private String device;
  private int rollnumber;
  private String classname;
  private Timestamp createdon;
  private Timestamp updatedon;
  public Studentdto() {
    super();
  }
}
