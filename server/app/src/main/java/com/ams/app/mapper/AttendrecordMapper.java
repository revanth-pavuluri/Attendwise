package com.ams.app.mapper;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import com.ams.app.dto.Attendrecorddto;
import com.ams.app.model.Attendrecord;

@Component
@Mapper(componentModel = "spring")
public interface AttendrecordMapper {	
	AttendrecordMapper INSTANCE = Mappers.getMapper(AttendrecordMapper.class);

    @Mapping(source="faculty.name", target="facultyname")
    @Mapping(source="faculty.id", target="facultyid")
    @Mapping(source="subject.name", target="subjectcode")
    @Mapping(source="subject.name", target="subjectname")
    @Mapping(source="subject.id", target="subjectid")
    Attendrecorddto modelToDto(Attendrecord Attendrecord);
    
    @InheritInverseConfiguration
    Attendrecord dtoToModel(Attendrecorddto Attendrecorddto);

    @Mapping(source="faculty.name", target="facultyname")
    @Mapping(source="faculty.id", target="facultyid")
    @Mapping(source="subject.name", target="subjectcode")
    @Mapping(source="subject.name", target="subjectname")
    @Mapping(source="subject.id", target="subjectid")
	List<Attendrecorddto> modelsToDtos(List<Attendrecord> Attendrecord);
}