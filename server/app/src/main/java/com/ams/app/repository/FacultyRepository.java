package com.ams.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.Faculty;

@Repository
@Transactional
public interface FacultyRepository extends JpaRepository<Faculty, Integer>, JpaSpecificationExecutor<Faculty>{
    Faculty findById(int Id);
    Faculty findByUsername(String username);
}
