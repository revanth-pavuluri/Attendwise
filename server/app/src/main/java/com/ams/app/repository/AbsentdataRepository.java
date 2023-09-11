package com.ams.app.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.AbsentData;
import com.ams.app.model.Student;
import com.ams.app.model.AttendRecord;


@Repository
@Transactional
public interface AbsentDataRepository extends JpaRepository<AbsentData, Integer>{
    
    AbsentData findById(Long Id);
    

    AbsentData findByAttendRecordIdAndStudentId(Long attendRecordId, Long studentId);
    
    @Query(value = "SELECT COUNT(*) FROM Absentdata a WHERE a.aid IN (:values) AND a.sid = :sid", nativeQuery = true)
    Long countByAttendrecordContainsAndStudent(@Param(value = "values") List<Long> attendrecord, @Param(value = "sid") Student student);

    List<AbsentData> findByAttendRecordId(Long attendRecordId);
    
    @Query(value = "SELECT aid FROM Absentdata a WHERE a.aid IN (:values) AND a.sid = :sid", nativeQuery = true)
    List<Long> findAllbyAttendrecordContainsAndStudent(@Param(value = "values") List<Long> attendrecord, @Param(value = "sid") Student student);

}
