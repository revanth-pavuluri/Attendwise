package com.ams.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.app.dto.SubjectDTO;
import com.ams.app.mapper.SubjectMapper;
import com.ams.app.model.Subject;
import com.ams.app.service.SubjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SubjectsController {

    private SubjectService subjectService;

    private SubjectMapper subjectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubject(@PathVariable Long id){
        SubjectDTO subjectDTO = subjectMapper.map(subjectService.findById(id));
        return ResponseEntity.ok(subjectDTO);
    }
    
    @PostMapping("/")
    public ResponseEntity<SubjectDTO> addSubject(@RequestBody SubjectDTO subjectDTO){
        Subject subject = subjectMapper.map(subjectDTO);
        SubjectDTO saved = subjectMapper.map(subjectService.save(subject));
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> editSubject(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO){
        Subject subject = subjectMapper.map(subjectDTO);
        SubjectDTO edited = subjectMapper.map(subjectService.editSubject(id, subject));
        return ResponseEntity.ok(edited);
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id){
        return ResponseEntity.ok(subjectService.deleteSubject(id));
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> allSubjects(){
        return ResponseEntity.ok(subjectMapper.map(subjectService.allSubjects()));
    }

}