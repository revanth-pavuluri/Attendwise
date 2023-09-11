package com.ams.app.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.dto.RequestDTO;
import com.ams.app.dto.StatsresponseDTO;
import com.ams.app.dto.StudentDashboardRequestDTO;
import com.ams.app.dto.StudentDashboardResponseDTO;
import com.ams.app.dto.StudentDTO;
import com.ams.app.dto.SubjectStatsResponseDTO;
import com.ams.app.mapper.StudentMapper;
import com.ams.app.model.Student;
import com.ams.app.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class StudentsController {

    private StudentService studentService;

    private StudentMapper studentMapper;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id){
        Student student = studentService.findById(id);
        return ResponseEntity.ok(studentMapper.map(student));
    }
    
    @PostMapping("/")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO){
        Student student = studentMapper.map(studentDTO);
        Student saved = studentService.save(student);
        return ResponseEntity.ok(studentMapper.map(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> editStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO){
        Student student = studentMapper.map(studentDTO);
        Student edited = studentService.editStudent(id, student);
        return ResponseEntity.ok(studentMapper.map(edited));
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.deleteStudent(id));
        
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentDTO>> allStudents(){
        List<Student> result = studentService.allStudents();
        return ResponseEntity.ok(studentMapper.map(result));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<StudentDTO>> searchStudents(@RequestBody RequestDTO request) {
        List<Student> result = studentService.searchStudents(request);
        return ResponseEntity.ok(studentMapper.map(result));
    }

    @PostMapping("/dashboard")
    public ResponseEntity<List<StudentDashboardResponseDTO>> studentdashboard(@RequestBody StudentDashboardRequestDTO data){
        SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = (User) securityContext.getAuthentication().getPrincipal();
		String role = user.getAuthorities().stream().findFirst().get().getAuthority();
		if(role.equals("STUDENT")){
            return ResponseEntity.ok(studentService.dashboard(data));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid attempt");
        }
    }
    @GetMapping("/stats/{sid}")
    public ResponseEntity<List<StatsresponseDTO>> studentstats(@PathVariable Long sid){
        return ResponseEntity.ok(studentService.stats(sid));
    }

    @GetMapping("/stats/{sid}/{subid}")
    public ResponseEntity<List<SubjectStatsResponseDTO>> studentSubjectstats(@PathVariable Long sid, @PathVariable Long subid){
        return ResponseEntity.ok(studentService.subjectStats(sid,subid));
    }
}
