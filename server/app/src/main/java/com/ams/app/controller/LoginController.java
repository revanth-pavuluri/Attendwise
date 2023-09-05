package com.ams.app.controller;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ams.app.mapper.FacultyMapper;
import com.ams.app.mapper.StudentMapper;
import com.ams.app.model.Faculty;
import com.ams.app.model.Student;
import com.ams.app.service.FacultyService;
import com.ams.app.service.MyUserDetailsService;
import com.ams.app.service.StudentService;

@RestController
public class LoginController {
	
	@Autowired
	StudentService studentService;
    
	@Autowired
	FacultyService facultyService;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	StudentMapper studentMapper;

	@Autowired
	FacultyMapper facultyMapper;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object Userdata(HttpServletResponse response) {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = (User) securityContext.getAuthentication().getPrincipal();
		String role = user.getAuthorities().stream().findFirst().get().getAuthority();
		
		if(role.equals("STUDENT")){
			Student s =  studentService.findByUsername(user.getUsername());
            System.out.println("Student Login successful");
			return studentMapper.modelToDto(s);
		
		}else if(role.equals("FACULTY")){
			Faculty f =  facultyService.findByUsername(user.getUsername());
            System.out.println("Faculty Login successful");
			return facultyMapper.modelToDto(f);
		}
		return "Login error occured";
}
}
