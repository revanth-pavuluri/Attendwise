package com.ams.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.ams.app.dto.SubjectDTO;
import com.ams.app.model.Subject;

@Component
@Mapper(componentModel = "spring")
public interface SubjectMapper {
    
    SubjectDTO map(Subject subject);
    
    Subject map(SubjectDTO subject);

    List<SubjectDTO> map(List<Subject> subjects);
}
