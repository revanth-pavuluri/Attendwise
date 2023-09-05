package com.ams.app.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.dto.Requestdto;
import com.ams.app.dto.Statsresponsedto;
import com.ams.app.dto.Studentdashrequestdto;
import com.ams.app.dto.Studentdashresponsedto;
import com.ams.app.dto.Studentdto;
import com.ams.app.dto.SubjectStatsresponsedto;
import com.ams.app.mapper.StudentMapper;
import com.ams.app.model.Absentdata;
import com.ams.app.model.Attendrecord;
import com.ams.app.model.Presentdata;
import com.ams.app.model.Student;
import com.ams.app.repository.AbsentdataRepository;
import com.ams.app.repository.AttendrecordRepository;
import com.ams.app.repository.PresentdataRepository;
import com.ams.app.repository.StudentRepository;
import com.ams.app.repository.SubjectRepository;
import com.ams.app.specification.CommonFilterSpecification;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository Srepo;
    
    @Autowired
    private AttendrecordRepository Arepo;

    @Autowired
    private AbsentdataRepository Abrepo;
    
    @Autowired
    private PresentdataRepository Prerepo;

   @Autowired
   private StudentMapper studentMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Student findByUsername(String username){
         return Srepo.findByUsername(username);
    }
    public ResponseEntity<Studentdto> findById(int sid) {
        try{
            Studentdto s = studentMapper.modelToDto(Srepo.findById(sid));
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            return ResponseEntity.ok(s);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(), e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    public ResponseEntity<Studentdto> editStudent(int id, Studentdto student){
        try{
            Student s = Srepo.findById(id);
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            Student stud = studentMapper.dtoToModel(student);
            stud.setId(id);
            stud.setPassword(s.getPassword());
            Student saved = Srepo.save(stud);
            return ResponseEntity.ok(studentMapper.modelToDto(saved));
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }   
    }

    
    public ResponseEntity<Studentdto> save(Studentdto studentdto) {
        try{
            Student student = studentMapper.dtoToModel(studentdto);
            student.setPassword(passwordEncoder.encode("Pass@123"));
            Student s =  Srepo.save(student);
            return ResponseEntity.ok(studentMapper.modelToDto(s));
            
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    public ResponseEntity<String> deleteStudent(int id){
        try{
            Student s = Srepo.findById(id);
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
    public ResponseEntity<List<Studentdto>> allStudents(){
         try{
            return ResponseEntity.ok(studentMapper.modelsToDtos(Srepo.findAll()));
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    
    public ResponseEntity<List<Statsresponsedto>> stats(int sid){
      try{
        List<Statsresponsedto> response = new ArrayList<>();
        Student s = Srepo.findById(sid);
        if (s==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid attempt");
        }
        List<Attendrecord> records = Arepo.findByClassnameAndStatus(s.getClassname(),2);
        Map<String, List<Integer>> rec = new HashMap<>();
        for (Attendrecord attendrecord : records) {
            if (rec.containsKey(attendrecord.getSubject().getName()+"_"+attendrecord.getSubject().getId())) {
                // If the subject name is already a key in the map, append the attendrecord ID to the existing list
                List<Integer> idList = rec.get(attendrecord.getSubject().getName()+"_"+attendrecord.getSubject().getId());
                idList.add(attendrecord.getId());
            } else {
                // If the subject name is not in the map, create a new list with the attendrecord ID and put it in the map
                List<Integer> idList = new ArrayList<>();
                idList.add(attendrecord.getId());
                rec.put(attendrecord.getSubject().getName()+"_"+attendrecord.getSubject().getId(), idList);
            }
        }

        for (Map.Entry<String, List<Integer>> entry : rec.entrySet()) {
            String subjectName = entry.getKey();
            List<Integer> attendRecordIds = entry.getValue();
            long count = Abrepo.countByAttendrecordContainsAndStudent(attendRecordIds, s);
            float percentage = ((1 - ((float)count/(float)(attendRecordIds.size())))) * 100;
            String[] subject = subjectName.split("_");
            Statsresponsedto rep = new Statsresponsedto(subject[0], percentage, Integer.parseInt(subject[1]));
            response.add(rep);
        }
        return ResponseEntity.ok(response);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    public ResponseEntity<List<SubjectStatsresponsedto>> subjectStats(int sid, int subid){
      try{
            
            Student s = Srepo.findById(sid);
            if (s==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid attempt");
            }
            List<Attendrecord> records = Arepo.findByClassnameAndStatusAndSubject(s.getClassname(), 2, subid);
            List<Integer> aids = new ArrayList<>();
            for (Attendrecord attendrecord : records) {
                aids.add(attendrecord.getId());
            }
            List<Integer> absentdata = Abrepo.findAllbyAttendrecordContainsAndStudent(aids, s);
            List<SubjectStatsresponsedto> response = new ArrayList<>();
            for (Attendrecord attendrecord : records) {
                String status = "Present";
                if(absentdata.contains(attendrecord.getId())){
                    status = "Absent";
                }
                SubjectStatsresponsedto rep = new SubjectStatsresponsedto(attendrecord.getDate(),status,attendrecord.getCreatedon());
                response.add(rep);
            }
        
            return ResponseEntity.ok(response);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    public ResponseEntity<List<Studentdashresponsedto>> dashboard(Studentdashrequestdto data){
         try{
            List<Studentdashresponsedto> responsedata = new ArrayList<>();
            Student s = Srepo.findById(data.getSid());
            List<Attendrecord> records = Arepo.findByDateAndClassname(data.getDate(),s.getClassname());
            for (Attendrecord attendrecord : records) {
                 Studentdashresponsedto rep = studentMapper.maptoDashboarddto(attendrecord);
                 Presentdata p = Prerepo.findByAidAndSid(attendrecord.getId(),s.getId());
                 if(attendrecord.getStatus() == 0){
                    if(p != null){
                        rep.setStudentstatus("Present");
                    }else{
                        rep.setStudentstatus("Not yet Marked");
                    }
                 }else if(attendrecord.getStatus() == 1) {
                    if(p != null){
                        rep.setStudentstatus("Present");
                    }else{
                        rep.setStudentstatus("Absent");
                    }
                 }else if(attendrecord.getStatus() == 2){
                    Absentdata a = Abrepo.findByAidAndSid(attendrecord.getId(),s.getId());
                    if(a == null){
                        rep.setStudentstatus("Present");
                    }else{
                        rep.setStudentstatus("Absent");
                    }
                 }
                responsedata.add(rep);
            }
            return ResponseEntity.ok(responsedata);
         }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
    

    @Autowired
    private CommonFilterSpecification<Student> StudentFilterSpecification;

    public ResponseEntity<List<Studentdto>> searchStudents(Requestdto request) {
        try{
        Specification<Student> searchSpecification = StudentFilterSpecification.
        getSearchSpecification(request.getSearch(), request.getOperator());
            
        return ResponseEntity.ok(studentMapper.modelsToDtos(Srepo.findAll(searchSpecification)));
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
