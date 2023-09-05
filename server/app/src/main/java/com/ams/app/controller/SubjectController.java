package com.ams.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.app.model.Subject;
import com.ams.app.service.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable int id){
        return subjectService.findById(id);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject){
        return subjectService.save(subject);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Subject> editSubject(@PathVariable int id, @RequestBody Subject subject){
        return subjectService.editSubject(id, subject);
    }   

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable int id){
        return subjectService.deleteSubject(id);
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> allSubjects(){
        return subjectService.allSubjects();
    }

}