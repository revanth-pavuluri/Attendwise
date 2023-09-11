package com.ams.app.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.dto.RequestDTO;
import com.ams.app.dto.StatsresponseDTO;
import com.ams.app.dto.StudentDashboardRequestDTO;
import com.ams.app.dto.StudentDashboardResponseDTO;
import com.ams.app.dto.SubjectStatsResponseDTO;
import com.ams.app.mapper.StudentMapper;
import com.ams.app.model.AbsentData;
import com.ams.app.model.AttendRecord;
import com.ams.app.model.PresentData;
import com.ams.app.model.Student;
import com.ams.app.model.Subject;
import com.ams.app.model.AttendRecord.AttendanceStatus;
import com.ams.app.repository.AbsentDataRepository;
import com.ams.app.repository.AttendRecordRepository;
import com.ams.app.repository.PresentDataRepository;
import com.ams.app.repository.StudentRepository;
import com.ams.app.repository.SubjectRepository;
import com.ams.app.specification.CommonFilterSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class StudentService {
    
    private StudentRepository studentRepository;
    
    private AttendRecordRepository attendRecordRepository;
    
    private AbsentDataRepository absentDataRepository;
    
    private PresentDataRepository presentDataRepository;
   
    private StudentMapper studentMapper;

    private SubjectRepository subjectRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Student findByUsername(String username){
         return studentRepository.findByUsername(username);
    }
    
    public Student findById(Long studentId) {
        try{
            Student student = studentRepository.findById(studentId);
            if(student == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            return student;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(), e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    public Student editStudent(Long id, Student student){
        try{
            Student result = studentRepository.findById(id);
            if(result == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
            }
            student.setId(id);
            student.setPassword(result.getPassword());
            Student saved = studentRepository.save(student);
            return saved;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }   
    }

    
    public Student save(Student student) {
        try{
            student.setPassword(passwordEncoder.encode("Pass@123"));
            Student saved =  studentRepository.save(student);
            return saved;
            
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    public String deleteStudent(Long id){
        try{
            Student student = studentRepository.findById(id);
                if(student == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
                }
                studentRepository.deleteById(id.intValue());
                return "Deleted";
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }
    public List<Student> allStudents(){
         try{
            return studentRepository.findAll();
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    
    public List<StatsresponseDTO> stats(Long studentId){
      try{
        List<StatsresponseDTO> response = new ArrayList<>();
        Student student = studentRepository.findById(studentId);
        if (student == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid attempt");
        }
        List<AttendRecord> records = attendRecordRepository.findByClassNameAndStatus(student.getClassName(),AttendanceStatus.FINALIZED);
        Map<String, List<Long>> rec = new HashMap<>();
        for (AttendRecord attendrecord : records) {
            if (rec.containsKey(attendrecord.getSubject().getName()+"_"+attendrecord.getSubject().getId())) {
                // If the subject name is already a key in the map, append the attendrecord ID to the existing list
                List<Long> idList = rec.get(attendrecord.getSubject().getName()+"_"+attendrecord.getSubject().getId());
                idList.add(attendrecord.getId());
            } else {
                // If the subject name is not in the map, create a new list with the attendrecord ID and put it in the map
                List<Long> idList = new ArrayList<>();
                idList.add(attendrecord.getId());
                rec.put(attendrecord.getSubject().getName()+"_"+attendrecord.getSubject().getId(), idList);
            }
        }

        for (Map.Entry<String, List<Long>> entry : rec.entrySet()) {
            String subjectName = entry.getKey();
            List<Long> attendRecordIds = entry.getValue();
            long count = absentDataRepository.countByAttendrecordContainsAndStudent(attendRecordIds, student);
            float percentage = ((1 - ((float)count/(float)(attendRecordIds.size())))) * 100;
            String[] subject = subjectName.split("_");
            StatsresponseDTO rep = new StatsresponseDTO(subject[0], percentage, Integer.parseInt(subject[1]));
            response.add(rep);
        }
        return response;
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    public List<SubjectStatsResponseDTO> subjectStats(Long studentId, Long subjectId){
      try{
            
            Student student = studentRepository.findById(studentId);
            if (student == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid attempt");
            }
            Subject subject = subjectRepository.findById(subjectId);
            List<AttendRecord> records = attendRecordRepository.findByClassNameAndStatusAndSubject(student.getClassName(), AttendanceStatus.FINALIZED, subject);
            List<Long> aids = new ArrayList<>();
            for (AttendRecord attendrecord : records) {
                aids.add(attendrecord.getId());
            }
            List<Long> absentdata = absentDataRepository.findAllbyAttendrecordContainsAndStudent(aids, student);
            List<SubjectStatsResponseDTO> response = new ArrayList<>();
            for (AttendRecord attendrecord : records) {
                String status = "Present";
                if(absentdata.contains(attendrecord.getId())){
                    status = "Absent";
                }
                SubjectStatsResponseDTO rep = new SubjectStatsResponseDTO(attendrecord.getDate(),status,attendrecord.getCreatedOn());
                response.add(rep);
            }
        
            return response;
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    public List<StudentDashboardResponseDTO> dashboard(StudentDashboardRequestDTO data){
         try{
            List<StudentDashboardResponseDTO> responsedata = new ArrayList<>();
            Student student = studentRepository.findById(data.getStudentId());
            List<AttendRecord> records = attendRecordRepository.findByDateAndClassName(data.getDate(), student.getClassName());
            for (AttendRecord attendrecord : records) {
                 StudentDashboardResponseDTO rep = studentMapper.map(attendrecord);
                 PresentData p = presentDataRepository.findByAttendRecordIdAndStudentId(attendrecord.getId(), student.getId());
                 if(attendrecord.getStatus() == AttendanceStatus.ACTIVE){
                    if(p != null){
                        rep.setStudentStatus("Present");
                    }else{
                        rep.setStudentStatus("Not yet Marked");
                    }
                 }else if(attendrecord.getStatus() == AttendanceStatus.EXPIRED) {
                    if(p != null){
                        rep.setStudentStatus("Present");
                    }else{
                        rep.setStudentStatus("Absent");
                    }
                 }else if(attendrecord.getStatus() == AttendanceStatus.FINALIZED){
                    AbsentData a = absentDataRepository.findByAttendRecordIdAndStudentId(attendrecord.getId(),student.getId());
                    if(a == null){
                        rep.setStudentStatus("Present");
                    }else{
                        rep.setStudentStatus("Absent");
                    }
                 }
                responsedata.add(rep);
            }
            return responsedata;
         }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
    

    
    private CommonFilterSpecification<Student> StudentFilterSpecification;

    public List<Student> searchStudents(RequestDTO request) {
        try{
        Specification<Student> searchSpecification = StudentFilterSpecification.
        getSearchSpecification(request.getSearch(), request.getOperator());
            
        return studentRepository.findAll(searchSpecification);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
