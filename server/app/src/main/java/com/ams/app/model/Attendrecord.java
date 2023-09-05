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
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Table(name="Attendrecord")
@Entity
@Data
public class Attendrecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="fid")
  private Faculty faculty;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="subid")
  private Subject subject;
  private int periods;
  private int status;
  private String type;
  private String data;
  private String classname;
  private String date;
  @Column(name="createdon")
  @CreationTimestamp
  private Timestamp createdon;
  @Column(name = "updatedon")
  @UpdateTimestamp
  private Timestamp updatedon;
}
