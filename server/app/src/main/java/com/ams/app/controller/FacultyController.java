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

import com.ams.app.dto.Facultydto;
import com.ams.app.model.Faculty;
import com.ams.app.service.FacultyService;

@RestController
@RequestMapping("/faculty")

public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Facultydto> getFaculty(@PathVariable int id){
        return facultyService.findById(id);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty){
        return facultyService.save(faculty);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Faculty> editFaculty(@PathVariable int id, @RequestBody Faculty faculty){
        return facultyService.editFaculty(id, faculty);
    }   

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable int id){
        return facultyService.deleteFaculty(id);
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<Facultydto>> allFaculties(){
        return facultyService.allFaculties();
    }

}