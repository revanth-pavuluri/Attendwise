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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.dto.Requestdto;
import com.ams.app.dto.Statsresponsedto;
import com.ams.app.dto.Studentdashrequestdto;
import com.ams.app.dto.Studentdashresponsedto;
import com.ams.app.dto.Studentdto;
import com.ams.app.dto.SubjectStatsresponsedto;
import com.ams.app.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    StudentService studentService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Studentdto> getStudent(@PathVariable int id){
        return studentService.findById(id);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Studentdto> addStudent(@RequestBody Studentdto student){
        return studentService.save(student);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Studentdto> editStudent(@PathVariable int id, @RequestBody Studentdto student){
        return studentService.editStudent(id, student);
    }   

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id){
        return studentService.deleteStudent(id);
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<Studentdto>> allStudents(){
        return studentService.allStudents();
    }

    @GetMapping("/all/filter")
    public ResponseEntity<List<Studentdto>> searchStudents(@RequestBody Requestdto request) {
        return studentService.searchStudents(request);
    }

    @PostMapping("/dashboard")
    public ResponseEntity<List<Studentdashresponsedto>> studentdashboard(@RequestBody Studentdashrequestdto data){
        SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = (User) securityContext.getAuthentication().getPrincipal();
		String role = user.getAuthorities().stream().findFirst().get().getAuthority();
		if(role.equals("STUDENT")){
            return studentService.dashboard(data);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid attempt");
        }
    }
    @GetMapping("/stats/{sid}")
    public ResponseEntity<List<Statsresponsedto>> studentstats(@PathVariable int sid){
        return studentService.stats(sid);
    }

    @GetMapping("/stats/{sid}/{subid}")
    public ResponseEntity<List<SubjectStatsresponsedto>> studentSubjectstats(@PathVariable int sid, @PathVariable int subid){
        return studentService.subjectStats(sid,subid);
    }
}
