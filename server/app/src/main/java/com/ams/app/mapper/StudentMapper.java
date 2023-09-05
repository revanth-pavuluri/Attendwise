package com.ams.app.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.ams.app.dto.AttendanceReportdto;
import com.ams.app.dto.Studentdashresponsedto;
import com.ams.app.dto.Studentdto;
import com.ams.app.model.Attendrecord;
import com.ams.app.model.Student;

@Component
@Mapper(componentModel = "spring")
public interface StudentMapper {	
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	//@Mapping(source = "password", ignore = true, target = "password")
    Studentdto modelToDto(Student student);
	@Mapping(target = "password", ignore = true,defaultValue = "")
	Student dtoToModel(Studentdto studentDto);

	List<Studentdto> modelsToDtos(List<Student> student);

	@Mapping(source = "id", target = "aid")
    @Mapping(source = "faculty.name", target = "facultyname")
    @Mapping(source = "subject.code", target = "subjectcode")
    @Mapping(source = "subject.name", target = "subjectname")
    @Mapping(target="studentstatus", ignore=true)
    Studentdashresponsedto maptoDashboarddto(Attendrecord attendRecord);

    @Mapping(source = "id", target = "sid")
    @Mapping(target="studentstatus", ignore=true)
    AttendanceReportdto maptoAttendancereportdto(Student student);
}