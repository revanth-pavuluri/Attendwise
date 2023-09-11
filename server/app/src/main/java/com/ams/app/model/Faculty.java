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

@Table(name="faculties")
@Entity
@Data
public class Faculty {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String username;
  
  private String name;
  
  private String password;
  
  private String role;
  
  @Column(name="created_on")
  @CreationTimestamp
  private Timestamp createdOn;
  
  @Column(name = "updated_on")
  @UpdateTimestamp
  private Timestamp updatedOn;
  
}
