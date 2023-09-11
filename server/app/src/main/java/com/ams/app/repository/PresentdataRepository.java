package com.ams.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.PresentData;

@Repository
@Transactional
public interface PresentDataRepository extends JpaRepository<PresentData, Integer>{
    
    PresentData findById(Long Id);
    
    
    PresentData findByAttendRecordIdAndStudentId(Long attendRecordId, Long studentId);
    
    List<PresentData> findByAttendRecordId(Long attendRecordId);
}
