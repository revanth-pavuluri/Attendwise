package com.ams.app.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ams.app.dto.Facultydto;
import com.ams.app.mapper.FacultyMapper;
import com.ams.app.model.Faculty;

import com.ams.app.repository.FacultyRepository;

@Service
public class FacultyService {
    
    @Autowired
    private FacultyRepository Frepo;
    @Autowired
    private FacultyMapper facultyMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Faculty findByUsername(String username){
         return Frepo.findByUsername(username);
    }
    public ResponseEntity<Facultydto> findById(int sid) {
        try{
            Facultydto s = facultyMapper.modelToDto(Frepo.findById(sid));
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            return ResponseEntity.ok(s);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    public ResponseEntity<Faculty> editFaculty(int id, Faculty faculty){
        try{
            Faculty s = Frepo.findById(id);
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            faculty.setId(id);
            Faculty saved = Frepo.save(faculty);
            return ResponseEntity.ok(saved);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public ResponseEntity<Faculty> save(Faculty faculty) {
        try{
            faculty.setPassword(passwordEncoder.encode(faculty.getPassword()));
            Faculty s = Frepo.save(faculty);
            
            return ResponseEntity.ok(s);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public ResponseEntity<String> deleteFaculty(int id){
        try{
            Faculty s = Frepo.findById(id);
                if(s == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
                }
                Frepo.deleteById(id);
                return ResponseEntity.ok("Deleted");
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }
    public ResponseEntity<List<Facultydto>> allFaculties(){
         try{
            return ResponseEntity.ok(facultyMapper.modelsToDtos(Frepo.findAll()));
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
}
