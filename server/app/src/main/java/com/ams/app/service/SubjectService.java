package com.ams.app.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.model.Subject;
import com.ams.app.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SubjectService {
    
    private SubjectRepository subjectRepository;
    
    public Subject findById(Long sid) {
        try{
            Subject subject = subjectRepository.findById(sid);
            if(subject == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            return subject;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public Subject editSubject(Long id, Subject subject){
        try{
            Subject result = subjectRepository.findById(id);
            if(result == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            subject.setId(id);
            Subject saved = subjectRepository.save(subject);
            return saved;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public Subject save(Subject subject) {
        try{
            Subject result = subjectRepository.save(subject);
            Subject saved = subjectRepository.findById(result.getId());
            return saved;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public String deleteSubject(Long id){
        try{
            Subject subject = subjectRepository.findById(id);
                if(subject == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
                }
                subjectRepository.deleteById(id.intValue());
                return "Deleted";
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }
    public List<Subject> allSubjects(){
         try{
            return subjectRepository.findAll();
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
}
