package com.ams.app.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.ams.app.dto.FacultyDTO;
import com.ams.app.model.Faculty;

@Component
@Mapper(componentModel = "spring")
public interface FacultyMapper {	

    FacultyDTO map(Faculty Faculty);
	
	@Mapping(target = "password", ignore = true, defaultValue = "")
	Faculty map(FacultyDTO FacultyDto);

	List<FacultyDTO> map(List<Faculty> Faculty);
}