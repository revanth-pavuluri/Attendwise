package com.ams.app.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.model.Faculty;
import com.ams.app.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class FacultyService {
    
    private FacultyRepository facultyRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Faculty findByUsername(String username){
         return facultyRepository.findByUsername(username);
    }
    
    public Faculty findById(Long sid) {
        try{
            Faculty faculty = facultyRepository.findById(sid);
            if(faculty == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            return faculty;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    public Faculty editFaculty(Long id, Faculty faculty){
        try{
            Faculty result = facultyRepository.findById(id);
            if(result == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            faculty.setId(id);
            Faculty saved = facultyRepository.save(faculty);
            return saved;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public Faculty save(Faculty faculty) {
        try{
            faculty.setPassword(passwordEncoder.encode(faculty.getPassword()));
            Faculty result = facultyRepository.save(faculty);
            
            return result;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public String deleteFaculty(Long id){
        try{
            Faculty result = facultyRepository.findById(id);
                if(result == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
                }
                facultyRepository.deleteById(id.intValue());
                return "Deleted";
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }
    public List<Faculty> allFaculties(){
         try{
            return facultyRepository.findAll();
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
}
