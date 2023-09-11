package com.ams.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ams.app.model.Faculty;
import com.ams.app.model.Student;
import com.ams.app.repository.FacultyRepository;
import com.ams.app.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MyUserDetailsService implements UserDetailsService {
    
	private StudentRepository studentRepository;
  	
	private FacultyRepository facultyRepository;
   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Student student = studentRepository.findByUsername(username);

		if(student == null) {
            Faculty faculty = facultyRepository.findByUsername(username);
            if(faculty == null){
			    throw new UsernameNotFoundException("Invalid username or password.");
            }else{
                return new User(faculty.getUsername(), faculty.getPassword(), mapRolesToAuthorities("FACULTY"));
            }
		}else{
            return new User(student.getUsername(), student.getPassword(), mapRolesToAuthorities("STUDENT"));
        }
    }
    
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String roles){
		List<GrantedAuthority> rol = Arrays.stream(roles.split(","))
		.map(SimpleGrantedAuthority::new)
		.collect(Collectors.toList());
		return rol;
	}
}