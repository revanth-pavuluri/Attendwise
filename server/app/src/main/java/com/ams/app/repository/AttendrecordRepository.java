package com.ams.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.Attendrecord;
import com.ams.app.model.Subject;

import java.util.List;

@Repository
@Transactional
public interface AttendrecordRepository extends JpaRepository<Attendrecord, Integer>, JpaSpecificationExecutor<Attendrecord>{
    
    Attendrecord findById(int Id);
    List<Attendrecord> findByDate(String date);
    @Query(value = "SELECT * FROM Attendrecord pd WHERE pd.date = :date AND pd.classname = :classname", nativeQuery = true)
    List<Attendrecord> findByDateAndClassname(@Param(value = "date") String date,@Param(value = "classname") String classname);
    List<Attendrecord> findByClassname(String classname);
    List<Attendrecord> findByClassnameAndStatus(String classname, int status);
    @Query(value = "SELECT * FROM Attendrecord pd WHERE pd.status = :status AND pd.classname = :classname AND pd.subid = :subid", nativeQuery = true)
    List<Attendrecord> findByClassnameAndStatusAndSubject(@Param(value = "classname") String classname,@Param(value = "status") int status,@Param(value = "subid") int subid);
    @Query(value = "SELECT * FROM Attendrecord pd WHERE pd.fid = :fid", nativeQuery = true)
    List<Attendrecord> findByFaculty(@Param(value = "fid") int fid);


}
