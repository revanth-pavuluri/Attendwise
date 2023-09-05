package com.ams.app.dto;
import java.sql.Timestamp;
import javax.persistence.Id;
import lombok.Data;

@Data
public class Attendrecorddto {
    @Id
    private int id;
    private String facultyname;
    private int facultyid;
    private int subjectid;
    private String subjectname;
    private String Subjectcode;
    private int periods;
    private int status;
    private String type;
    private String data;
    private String classname;
    private String date;
    private Timestamp createdon;
    private Timestamp updatedon;
}