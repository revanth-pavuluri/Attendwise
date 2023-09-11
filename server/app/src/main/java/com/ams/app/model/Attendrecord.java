package com.ams.app.model;
import java.sql.Date;
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
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Table(name="attend_records")
@Entity
@Data
public class AttendRecord {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "faculty_id")
  private Long facultyId;

  @ManyToOne
  @JoinColumn(name="faculty_id", referencedColumnName = "id", insertable = false, updatable = false)
  private Faculty faculty;
  
  @Column(name = "subject_id")
  private Long subjectId;

  @ManyToOne
  @JoinColumn(name="subject_id", referencedColumnName = "id", insertable = false, updatable = false)
  private Subject subject;
  
  private Integer periods;
  
  private AttendanceStatus status;
  
  @Column(name="class_name")
  private String className;
  
  private Date date;
  
  @Column(name="created_on")
  @CreationTimestamp
  private Timestamp createdOn;
  
  @Column(name = "updated_on")
  @UpdateTimestamp
  private Timestamp updatedOn;

  public enum AttendanceStatus {
    ACTIVE,
    EXPIRED,
    FINALIZED
  }

}
