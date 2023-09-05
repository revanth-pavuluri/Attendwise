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

@Table(name="Subject")
@Entity
@Data
public class Subject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String code;
  @Column(name="createdon")
  @CreationTimestamp
  private Timestamp createdon;
  @Column(name = "updatedon")
  @UpdateTimestamp
  private Timestamp updatedon;
}