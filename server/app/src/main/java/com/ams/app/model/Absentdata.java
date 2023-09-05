package com.ams.app.model;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

@Table(name="Absentdata")
@Entity
@Data
public class Absentdata {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="sid")
  private Student student;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="aid")
  private Attendrecord attendrecord;
  @Column(name="createdon")
  @CreationTimestamp
  private Timestamp createdon;
  public Absentdata(Student student, Attendrecord attendrecord) {
    this.student = student;
    this.attendrecord = attendrecord;
  }
  public Absentdata(){
    
  }
}
