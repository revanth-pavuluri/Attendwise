package com.ams.app.model;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Table(name="Student")
@Entity
@Data
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String name;
  private String password;
  private String device;
  private int rollnumber;
  private String classname;
  @Column(name="createdon")
  @CreationTimestamp
  private Timestamp createdon;
  @Column(name = "updatedon")
  @UpdateTimestamp
  private Timestamp updatedon;
}
