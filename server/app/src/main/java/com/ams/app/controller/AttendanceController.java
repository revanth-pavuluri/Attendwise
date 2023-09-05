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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.app.dto.AttendanceReportdto;
import com.ams.app.dto.Attendrecorddto;
import com.ams.app.dto.Requestdto;

import com.ams.app.service.AttendrecordService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendrecordService attendrecordService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Attendrecorddto> getAttendrecord(@PathVariable int id){
        return attendrecordService.findById(id);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Attendrecorddto> addAttendrecord(@RequestBody Attendrecorddto attendrecord){
        return attendrecordService.save(attendrecord);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Attendrecorddto> editAttendrecord(@PathVariable int id, @RequestBody Attendrecorddto attendrecord){
        return attendrecordService.editAttendrecord(id, attendrecord);
    }   

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendrecord(@PathVariable int id){
        return attendrecordService.deleteAttendrecord(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Attendrecorddto>> allAttendrecords(){
        return attendrecordService.allAttendrecords();
    }

    @PostMapping("/all/filter")
    public ResponseEntity<List<Attendrecorddto>> searchAttendrecords(@RequestBody Requestdto request) {
        return attendrecordService.searchAttendrecords(request);
    }

    @PostMapping("/mark/{aid}/{sid}")
    public ResponseEntity<String> markAttendance(@PathVariable int aid,@PathVariable int sid){
        SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = (User) securityContext.getAuthentication().getPrincipal();
		String role = user.getAuthorities().stream().findFirst().get().getAuthority();
		if(role.equals("FACULTY")){
            return attendrecordService.markAttendance(sid,aid,true);
        }else{
            return attendrecordService.markAttendance(sid,aid,false);
        }
    }
    @PostMapping("/unmark/{aid}/{sid}")
    public ResponseEntity<String> umMarkAttendance(@PathVariable int aid,@PathVariable int sid){
        return attendrecordService.unmarkAttendance(sid, aid);
    }
    @GetMapping("/f/{fid}")
    public ResponseEntity<List<Attendrecorddto>> searchAttendrecordsbyFaculty(@PathVariable int fid) {
        return attendrecordService.findByFacultyId(fid);
    }

    @PostMapping("/finalize/{aid}")
    public ResponseEntity<String> finalizeAttendance(@PathVariable int aid){
        return attendrecordService.finalizeAttendance(aid);
    }
    
    @GetMapping("/report/{aid}")
    public ResponseEntity<List<AttendanceReportdto>> attendanceReport(@PathVariable int aid){
        return attendrecordService.attendanceReport(aid);
    }

}