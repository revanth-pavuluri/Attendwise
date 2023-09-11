package com.ams.app.model;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

@Table(name="absent_data")
@Entity
@Data
public class AbsentData {

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
  private AttendRecord attendRecord;

  @Column(name="created_on")
  @CreationTimestamp
  private Timestamp createdOn;

  
  public AbsentData(Student student, AttendRecord attendRecord) {
    this.student = student;
    this.attendRecord = attendRecord;
  }
}
