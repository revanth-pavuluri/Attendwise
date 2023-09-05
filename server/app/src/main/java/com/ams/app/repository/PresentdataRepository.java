package com.ams.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.Presentdata;

@Repository
@Transactional
public interface PresentdataRepository extends JpaRepository<Presentdata, Integer>{
    Presentdata findById(int Id);
    @Query(value = "SELECT * FROM Temprecord pd WHERE pd.aid = :aid AND pd.sid = :sid", nativeQuery = true)
    Presentdata findByAidAndSid(@Param(value = "aid") int aid, @Param(value = "sid") int sid);
    @Query(value = "SELECT * FROM Temprecord pd WHERE pd.aid = :aid", nativeQuery = true)
    List<Presentdata> findByAid(@Param(value = "aid") int aid);
}
