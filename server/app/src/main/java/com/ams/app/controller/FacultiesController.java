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

import com.ams.app.dto.FacultyDTO;
import com.ams.app.mapper.FacultyMapper;
import com.ams.app.model.Faculty;
import com.ams.app.service.FacultyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/faculty")
public class FacultiesController {

    private FacultyService facultyService;

    private FacultyMapper facultyMapper;

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFaculty(@PathVariable Long id){
        Faculty faculty = facultyService.findById(id);
        return ResponseEntity.ok(facultyMapper.map(faculty));
    }
    
    @PostMapping("/")
    public ResponseEntity<FacultyDTO> addFaculty(@RequestBody Faculty faculty){
        Faculty result = facultyService.save(faculty);
        return ResponseEntity.ok(facultyMapper.map(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyDTO> editFaculty(@PathVariable Long id, @RequestBody Faculty faculty){
        Faculty result = facultyService.editFaculty(id, faculty);
        return ResponseEntity.ok(facultyMapper.map(result));
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FacultyDTO>> allFaculties(){
        List<Faculty> faculties = facultyService.allFaculties();
        return ResponseEntity.ok(facultyMapper.map(faculties));
    }

}