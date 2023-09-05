package com.ams.app.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.ams.app.dto.Facultydto;
import com.ams.app.model.Faculty;

@Component
@Mapper(componentModel = "spring")
public interface FacultyMapper {	
	FacultyMapper INSTANCE = Mappers.getMapper(FacultyMapper.class);

    Facultydto modelToDto(Faculty Faculty);
	@Mapping(target = "password", ignore = true,defaultValue = "")
	Faculty dtoToModel(Facultydto FacultyDto);

	List<Facultydto> modelsToDtos(List<Faculty> Faculty);
}