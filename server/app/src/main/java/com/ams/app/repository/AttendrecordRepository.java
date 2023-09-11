package com.ams.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.AttendRecord;
import com.ams.app.model.Subject;
import com.ams.app.model.AttendRecord.AttendanceStatus;

import java.sql.Date;
import java.util.List;
import com.ams.app.model.Faculty;


@Repository
@Transactional
public interface AttendRecordRepository extends JpaRepository<AttendRecord, Integer>, JpaSpecificationExecutor<AttendRecord>{
    
    AttendRecord findById(Long Id);

    List<AttendRecord> findByDate(Date date);

    List<AttendRecord> findByDateAndClassName(Date date, String className);

    List<AttendRecord> findByClassName(String className);
    
    List<AttendRecord> findByClassNameAndStatus(String classname, AttendanceStatus status);
    
    List<AttendRecord> findByClassNameAndStatusAndSubject(String className, AttendanceStatus status, Subject subject);

    List<AttendRecord> findByFaculty(Faculty faculty);

}
