package com.ams.app.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ams.app.dto.AttendanceReportDTO;
import com.ams.app.dto.RequestDTO;
import com.ams.app.mapper.StudentMapper;
import com.ams.app.model.AbsentData;
import com.ams.app.model.AttendRecord;
import com.ams.app.model.Student;
import com.ams.app.model.AttendRecord.AttendanceStatus;
import com.ams.app.model.PresentData;
import com.ams.app.repository.AbsentDataRepository;
import com.ams.app.repository.AttendRecordRepository;
import com.ams.app.repository.StudentRepository;
import com.ams.app.specification.CommonFilterSpecification;
import lombok.RequiredArgsConstructor;
import com.ams.app.repository.PresentDataRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AttendrecordService {
    
    private AttendRecordRepository attendrecordRepository;
    
    private AbsentDataRepository absentdataRepository;
    
    private PresentDataRepository presentDataRepository;
    
    private StudentRepository studentRepository;
    
    private StudentMapper studentMapper;

    private CommonFilterSpecification<AttendRecord> StudentFilterSpecification;
   
    public AttendRecord findById(Long id) {
        try{
            AttendRecord attendrecord = attendrecordRepository.findById(id);
            if(attendrecord == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            return attendrecord;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public AttendRecord editAttendrecord(Long id, AttendRecord attendrecord){
        try{
            AttendRecord result = attendrecordRepository.findById(id);
            if(result == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            attendrecord.setId(id);
            AttendRecord saved = attendrecordRepository.save(attendrecord);
            return saved;
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public AttendRecord save(AttendRecord attendrecord) {
        try{
            return attendrecordRepository.save(attendrecord);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public String deleteAttendrecord(Long id){
        try{
            AttendRecord attendrecord = attendrecordRepository.findById(id);
                if(attendrecord == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
                }
                attendrecordRepository.deleteById(id.intValue());
                return "Deleted";
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }  
    }

    

    public String markAttendance(Long sid, Long aid, boolean byfaculty){
        try{
            AttendRecord record = attendrecordRepository.findById(aid);
            Student student = studentRepository.findById(sid);
            if(record == null || student==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }else if(byfaculty){
                if(record.getStatus() == AttendanceStatus.FINALIZED){
                    AbsentData absentdata = absentdataRepository.findByAttendRecordIdAndStudentId(aid, sid);
                    absentdataRepository.delete(absentdata);
                }else{
                    PresentData present = new PresentData(student, record);
                    presentDataRepository.save(present);
                }
            }
            else if(record.getStatus() == AttendanceStatus.EXPIRED){
                throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Request expired");
            } else if(record.getStatus() == AttendanceStatus.FINALIZED){
                throw new ResponseStatusException(HttpStatus.LOCKED,"Attendance finalized");
            }else{
                PresentData present = new PresentData(student, record);
                presentDataRepository.save(present);
            }
            return sid+" marked";
        }catch(ResponseStatusException e){
            System.out.println(e.getReason());
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public String unmarkAttendance(Long sid, Long aid){
        try{
            AttendRecord record = attendrecordRepository.findById(aid);
            Student student = studentRepository.findById(sid);
            if(record == null || student == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }else if(record.getStatus() == AttendanceStatus.FINALIZED){
                AbsentData absentdata = new AbsentData(student, record);
                absentdataRepository.save(absentdata);
            }else{
                PresentData present = presentDataRepository.findByAttendRecordIdAndStudentId(aid, sid);
                presentDataRepository.delete(present);
            }
            return sid+" unmarked";
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }




    public List<AttendRecord> searchAttendrecords(RequestDTO request) {
        try{
            Specification<AttendRecord> searchSpecification = StudentFilterSpecification.
            getSearchSpecification(request.getSearch(), request.getOperator());
            
            return attendrecordRepository.findAll(searchSpecification);

        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    public String finalizeAttendance(Long id){
        try{
            AttendRecord attendrecord = attendrecordRepository.findById(id);
                if(attendrecord == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
                }
            
            List<Student> students = studentRepository.findByClassName(attendrecord.getClassName());
            List<PresentData> presentdata = presentDataRepository.findByAttendRecordId(id);
            List<AbsentData> absents = new ArrayList<>();
            // Create a set of student IDs who are present
            Set<Long> presentStudentIds = presentdata.stream()
                    .map(present -> present.getStudent().getId())
                    .collect(Collectors.toSet());

            // Iterate through the students and check if they are absent
            for (Student student : students) {
                if (!presentStudentIds.contains(student.getId())) {
                    AbsentData ab = new AbsentData(student,attendrecord);
                    absents.add(ab);
                }
            }
            absentdataRepository.saveAll(absents);
            presentDataRepository.deleteAll(presentdata);
            attendrecord.setStatus(AttendanceStatus.FINALIZED);
            attendrecordRepository.save(attendrecord);
            return "Finalized";
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }

     public List<AttendanceReportDTO> attendanceReport(Long aid){
         try{
            AttendRecord attendrecord = attendrecordRepository.findById(aid);
            if(attendrecord == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            List<AttendanceReportDTO> responsedata = new ArrayList<>();
            List<Student> students = studentRepository.findByClassName(attendrecord.getClassName());
            if(attendrecord.getStatus() == AttendanceStatus.ACTIVE || attendrecord.getStatus() == AttendanceStatus.EXPIRED){
                
                List<PresentData> presentdata = presentDataRepository.findByAttendRecordId(aid);
                // Create a set of student IDs who are present
                Set<Long> presentStudentIds = presentdata.stream()
                        .map(present -> present.getStudent().getId())
                        .collect(Collectors.toSet());
                for (Student student : students) {
                    AttendanceReportDTO rep = studentMapper.maptoAttendancereportdto(student);
                    if(attendrecord.getStatus() == AttendanceStatus.ACTIVE){
                        if(presentStudentIds.contains(student.getId())){
                            rep.setStudentStatus("Present");
                        }else{
                            rep.setStudentStatus("Not yet Marked");
                        }
                    }else if(attendrecord.getStatus() == AttendanceStatus.EXPIRED) {
                        if(presentStudentIds.contains(student.getId())){
                            rep.setStudentStatus("Present");
                        }else{
                            rep.setStudentStatus("Absent");
                        }
                    }
                    responsedata.add(rep);
                }
            }else if (attendrecord.getStatus() == AttendanceStatus.FINALIZED){
                List<AbsentData> absentdata = absentdataRepository.findByAttendRecordId(aid);
                // Create a set of student IDs who are absent
                Set<Long> absentStudentIds = absentdata.stream()
                        .map(absent -> absent.getStudent().getId())
                        .collect(Collectors.toSet());
                for (Student student : students) {
                    AttendanceReportDTO rep = studentMapper.maptoAttendancereportdto(student);
                    if(absentStudentIds.contains(student.getId())){
                            rep.setStudentStatus("Absent");
                        }else{
                            rep.setStudentStatus("Present");
                        }
                    responsedata.add(rep);
                }  
            }
            return responsedata;
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
   
    
}
