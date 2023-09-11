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

@Table(name="subjects")
@Entity
@Data
public class Subject {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String name;
  
  private String code;
  
  @Column(name="created_on")
  @CreationTimestamp
  private Timestamp createdOn;
  
  @Column(name = "updated_on")
  @UpdateTimestamp
  private Timestamp updatedOn;
}