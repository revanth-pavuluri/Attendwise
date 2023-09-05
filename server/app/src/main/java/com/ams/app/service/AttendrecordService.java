package com.ams.app.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ams.app.dto.AttendanceReportdto;
import com.ams.app.dto.Attendrecorddto;
import com.ams.app.dto.Requestdto;
import com.ams.app.dto.Studentdashrequestdto;
import com.ams.app.dto.Studentdashresponsedto;
import com.ams.app.mapper.AttendrecordMapper;
import com.ams.app.mapper.StudentMapper;
import com.ams.app.model.Absentdata;
import com.ams.app.model.Attendrecord;
import com.ams.app.model.Student;
import com.ams.app.model.Presentdata;
import com.ams.app.repository.AbsentdataRepository;
import com.ams.app.repository.AttendrecordRepository;
import com.ams.app.repository.StudentRepository;
import com.ams.app.specification.CommonFilterSpecification;
import com.ams.app.repository.PresentdataRepository;

@Service
public class AttendrecordService {
    
    @Autowired
    private AttendrecordRepository Arepo;
    @Autowired
    private AttendrecordMapper attendrecordMapper;
    @Autowired
    AbsentdataRepository absentdataRepository;
     @Autowired
    PresentdataRepository presentrepo;
    @Autowired
    StudentRepository Srepo;
    @Autowired
    StudentMapper studentMapper;
   
    public ResponseEntity<Attendrecorddto> findById(int sid) {
        try{
            Attendrecorddto s = attendrecordMapper.modelToDto(Arepo.findById(sid));
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            return ResponseEntity.ok(s);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public ResponseEntity<List<Attendrecorddto>> findByFacultyId(int fid) {
        try{
            List<Attendrecorddto> s = attendrecordMapper.modelsToDtos(Arepo.findByFaculty(fid));
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            return ResponseEntity.ok(s);
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public ResponseEntity<Attendrecorddto> editAttendrecord(int id, Attendrecorddto attendrecord){
        try{
            Attendrecord s = Arepo.findById(id);
            if(s == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            Attendrecord record = attendrecordMapper.dtoToModel(attendrecord);
            record.setId(id);
            Attendrecord saved = Arepo.save(record);
            return ResponseEntity.ok(attendrecordMapper.modelToDto(saved));
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public ResponseEntity<Attendrecorddto> save(Attendrecorddto attendrecord) {
        try{
            Attendrecord s = Arepo.save(attendrecordMapper.dtoToModel(attendrecord));
            s.setId(0);
            return ResponseEntity.ok(attendrecordMapper.modelToDto(s));
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    public ResponseEntity<String> deleteAttendrecord(int id){
        try{
            Attendrecord s = Arepo.findById(id);
                if(s == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
                }
                Arepo.deleteById(id);
                return ResponseEntity.ok("Deleted");
            }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }
    public ResponseEntity<List<Attendrecorddto>> allAttendrecords(){
         try{
            return ResponseEntity.ok(attendrecordMapper.modelsToDtos(Arepo.findAll()));
         }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }

    public ResponseEntity<String> markAttendance(int sid, int aid, boolean byfaculty){
        try{
            Attendrecord record = Arepo.findById(aid);
            Student student = Srepo.findById(sid);
            if(record == null || student==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }else if(byfaculty){
                if(record.getStatus() == 2){
                    Absentdata absentdata = absentdataRepository.findByAidAndSid(aid, sid);
                    absentdataRepository.delete(absentdata);
                }else{
                    Presentdata present = new Presentdata(student, record);
                    presentrepo.save(present);
                }
            }
            else if(record.getStatus() == 1){
                //0 - active
                //1 - expired
                //2 - finalized
                throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Request expired");
            } else if(record.getStatus() == 2){
                throw new ResponseStatusException(HttpStatus.LOCKED,"Attendance finalized");
            }else{
                Presentdata present = new Presentdata(student, record);
                presentrepo.save(present);
            }
            return ResponseEntity.ok(sid+" marked");
        }catch(ResponseStatusException e){
            System.out.println(e.getReason());
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    public ResponseEntity<String> unmarkAttendance(int sid, int aid){
        try{
            Attendrecord record = Arepo.findById(aid);
            Student student = Srepo.findById(sid);
            if(record == null || student == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }else if(record.getStatus() == 2){
                Absentdata absentdata = new Absentdata(student, record);
                absentdataRepository.save(absentdata);
            }else{
                Presentdata present = presentrepo.findByAidAndSid(aid, sid);
                presentrepo.delete(present);
            }
            return ResponseEntity.ok(sid+" unmarked");
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }   
    }

    @Autowired
    private CommonFilterSpecification<Attendrecord> StudentFilterSpecification;

    public ResponseEntity<List<Attendrecorddto>> searchAttendrecords(Requestdto request) {
        try{
        Specification<Attendrecord> searchSpecification = StudentFilterSpecification.
        getSearchSpecification(request.getSearch(), request.getOperator());
            
        return ResponseEntity.ok(attendrecordMapper.modelsToDtos(Arepo.findAll(searchSpecification)));
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    public ResponseEntity<String> finalizeAttendance(int id){
        try{
            Attendrecord attendrecord = Arepo.findById(id);
                if(attendrecord == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
                }
            
            List<Student> students = Srepo.findByClassname(attendrecord.getClassname());
            List<Presentdata> presentdata = presentrepo.findByAid(id);
            List<Absentdata> absents = new ArrayList<>();
            // Create a set of student IDs who are present
            Set<Integer> presentStudentIds = presentdata.stream()
                    .map(present -> present.getStudent().getId())
                    .collect(Collectors.toSet());

            // Iterate through the students and check if they are absent
            for (Student student : students) {
                if (!presentStudentIds.contains(student.getId())) {
                    Absentdata ab = new Absentdata(student,attendrecord);
                    absents.add(ab);
                }
            }
            absentdataRepository.saveAll(absents);
            presentrepo.deleteAll(presentdata);
            attendrecord.setStatus(2);
            Arepo.save(attendrecord);
            return ResponseEntity.ok("Finalized");
        }catch(ResponseStatusException e){
            throw new ResponseStatusException(e.getStatus(),e.getReason());
        }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
    }

     public ResponseEntity<List<AttendanceReportdto>> attendanceReport(int aid){
         try{
            Attendrecord attendrecord = Arepo.findById(aid);
            if(attendrecord == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
            }
            List<AttendanceReportdto> responsedata = new ArrayList<>();
            List<Student> students = Srepo.findByClassname(attendrecord.getClassname());
            if(attendrecord.getStatus() == 0 || attendrecord.getStatus() == 1){
                
                List<Presentdata> presentdata = presentrepo.findByAid(aid);
                // Create a set of student IDs who are present
                Set<Integer> presentStudentIds = presentdata.stream()
                        .map(present -> present.getStudent().getId())
                        .collect(Collectors.toSet());
                for (Student student : students) {
                    AttendanceReportdto rep = studentMapper.maptoAttendancereportdto(student);
                    if(attendrecord.getStatus() == 0){
                        if(presentStudentIds.contains(student.getId())){
                            rep.setStudentstatus("Present");
                        }else{
                            rep.setStudentstatus("Not yet Marked");
                        }
                    }else if(attendrecord.getStatus() == 1) {
                        if(presentStudentIds.contains(student.getId())){
                            rep.setStudentstatus("Present");
                        }else{
                            rep.setStudentstatus("Absent");
                        }
                    }
                    responsedata.add(rep);
                }
            }else if (attendrecord.getStatus() == 2){
                List<Absentdata> absentdata = absentdataRepository.findByAid(aid);
                // Create a set of student IDs who are absent
                Set<Integer> absentStudentIds = absentdata.stream()
                        .map(absent -> absent.getStudent().getId())
                        .collect(Collectors.toSet());
                for (Student student : students) {
                    AttendanceReportdto rep = studentMapper.maptoAttendancereportdto(student);
                    if(absentStudentIds.contains(student.getId())){
                            rep.setStudentstatus("Absent");
                        }else{
                            rep.setStudentstatus("Present");
                        }
                    responsedata.add(rep);
                }  
            }
            return ResponseEntity.ok(responsedata);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
    }
   
    
}
