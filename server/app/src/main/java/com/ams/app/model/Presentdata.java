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

@Table(name="present_data")
@Entity
@Data
public class PresentData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "student_id")
  private Long studentId;

  @ManyToOne
  @JoinColumn(name="student_id", referencedColumnName = "id", insertable = false, updatable = false)
  private Student student;
  
  @Column(name = "attend_record_id")
  private Long attendRecordId;
  
  @ManyToOne
  @JoinColumn(name="attend_record_id", referencedColumnName = "id", insertable = false, updatable = false)
  private AttendRecord attendrecord;
  
  @Column(name="created_on")
  @CreationTimestamp
  private Timestamp createdOn;
  
  public PresentData(Student student, AttendRecord attendrecord) {
    this.student = student;
    this.attendrecord = attendrecord;
  }
  
}
