package com.ams.app.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.Absentdata;
import com.ams.app.model.Student;

@Repository
@Transactional
public interface AbsentdataRepository extends JpaRepository<Absentdata, Integer>{
    Absentdata findById(int Id);
    
    @Query(value = "SELECT * FROM Absentdata pd WHERE pd.aid = :aid AND pd.sid = :sid", nativeQuery = true)
    Absentdata findByAidAndSid(@Param(value = "aid") int aid, @Param(value = "sid") int sid);
    
    @Query(value = "SELECT COUNT(*) FROM Absentdata a WHERE a.aid IN (:values) AND a.sid = :sid", nativeQuery = true)
    int countByAttendrecordContainsAndStudent(@Param(value = "values") List<Integer> attendrecord, @Param(value = "sid") Student student);

    @Query(value = "SELECT * FROM Absentdata pd WHERE pd.aid = :aid", nativeQuery = true)
    List<Absentdata> findByAid(@Param(value = "aid") int aid);
    
    @Query(value = "SELECT aid FROM Absentdata a WHERE a.aid IN (:values) AND a.sid = :sid", nativeQuery = true)
    List<Integer> findAllbyAttendrecordContainsAndStudent(@Param(value = "values") List<Integer> attendrecord, @Param(value = "sid") Student student);

}
