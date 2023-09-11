package com.ams.app.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ams.app.model.Student;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student>{
    
    Student findById(Long Id);
    
    List<Student> findByClassName(String name);
    
    Student findByUsername(String username);
}

