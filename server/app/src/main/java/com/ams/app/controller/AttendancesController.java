package com.ams.app.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ams.app.dto.AttendanceReportDTO;
import com.ams.app.dto.AttendrecordDTO;
import com.ams.app.dto.RequestDTO;
import com.ams.app.mapper.AttendrecordMapper;
import com.ams.app.model.AttendRecord;
import com.ams.app.service.AttendrecordService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/attendance")
public class AttendancesController {

    private AttendrecordService attendrecordService;

    private AttendrecordMapper attendrecordMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<AttendrecordDTO> getAttendrecord(@PathVariable Long id){
        AttendrecordDTO attendrecorddto =  attendrecordMapper.map(attendrecordService.findById(id));
        return ResponseEntity.ok(attendrecorddto);
    }
    
    @PostMapping("/")
    public ResponseEntity<AttendrecordDTO> addAttendrecord(@RequestBody AttendrecordDTO attendrecordDTO){
        AttendRecord attendrecord =  attendrecordMapper.map(attendrecordDTO);
        AttendrecordDTO result = attendrecordMapper.map(attendrecordService.save(attendrecord));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendrecordDTO> editAttendrecord(@PathVariable Long id, @RequestBody AttendrecordDTO attendrecordDTO){
        AttendRecord attendrecord =  attendrecordMapper.map(attendrecordDTO);
        AttendrecordDTO result = attendrecordMapper.map(attendrecordService.editAttendrecord(id, attendrecord));
        return ResponseEntity.ok(result);
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendrecord(@PathVariable Long id){
        return ResponseEntity.ok(attendrecordService.deleteAttendrecord(id));
    }

    @PostMapping("/all")
    public ResponseEntity<List<AttendrecordDTO>> searchAttendrecords(@RequestBody RequestDTO request) {
        List<AttendRecord> results = attendrecordService.searchAttendrecords(request);
        return ResponseEntity.ok(attendrecordMapper.map(results));
    }

    @PostMapping("/mark/{attendRecordId}/{studentId}")
    public ResponseEntity<String> markAttendance(@PathVariable Long attendRecordId, @PathVariable Long studentId){
        SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = (User) securityContext.getAuthentication().getPrincipal();
		String role = user.getAuthorities().stream().findFirst().get().getAuthority();
		if(role.equals("FACULTY")){
            String result = attendrecordService.markAttendance(studentId,attendRecordId,true);
            return ResponseEntity.ok(result);
        }else{
            String result = attendrecordService.markAttendance(studentId,attendRecordId,false);
            return ResponseEntity.ok(result);
        }
    }
    @PostMapping("/unmark/{attendRecordId}/{studentId}")
    public ResponseEntity<String> umMarkAttendance(@PathVariable Long attendRecordId,@PathVariable Long studentId){
        String result = attendrecordService.unmarkAttendance(studentId,attendRecordId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/finalize/{attendRecordId}")
    public ResponseEntity<String> finalizeAttendance(@PathVariable Long attendRecordId){
        String result = attendrecordService.finalizeAttendance(attendRecordId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/report/{aid}")
    public ResponseEntity<List<AttendanceReportDTO>> attendanceReport(@PathVariable Long aid){
        List<AttendanceReportDTO> result = attendrecordService.attendanceReport(aid);
        return ResponseEntity.ok(result);
    }

}