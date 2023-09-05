package com.ams.app.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.model.Subject;
import com.ams.app.repository.SubjectRepository;

@Service
public class SubjectService {
    
    @Autowired
    private SubjectRepository Srepo;
    
    public ResponseEntity<Subject> findById(int sid) {
        try{
            Subject s = Srepo.findById(sid);
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            return ResponseEntity.ok(s);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public ResponseEntity<Subject> editSubject(int id, Subject subject){
        try{
            Subject s = Srepo.findById(id);
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            subject.setId(id);
            Subject saved = Srepo.save(subject);
            return ResponseEntity.ok(saved);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public ResponseEntity<Subject> save(Subject subject) {
        try{
            Subject s = Srepo.save(subject);
            Subject saved = Srepo.findById(s.getId());
            return ResponseEntity.ok(saved);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public ResponseEntity<String> deleteSubject(int id){
        try{
            Subject s = Srepo.findById(id);
                if(s == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
                }
                Srepo.deleteById(id);
                return ResponseEntity.ok("Deleted");
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }
    public ResponseEntity<List<Subject>> allSubjects(){
         try{
            return ResponseEntity.ok(Srepo.findAll());
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
}
