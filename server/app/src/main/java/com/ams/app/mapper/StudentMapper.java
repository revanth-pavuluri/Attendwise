package com.ams.app.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import com.ams.app.dto.AttendanceReportDTO;
import com.ams.app.dto.StudentDashboardResponseDTO;
import com.ams.app.dto.StudentDTO;
import com.ams.app.model.AttendRecord;
import com.ams.app.model.Student;

@Component
@Mapper(componentModel = "spring")
public interface StudentMapper {	

    StudentDTO map(Student student);

	@Mapping(target = "password", ignore = true,defaultValue = "")
	Student map(StudentDTO studentDto);

	List<StudentDTO> map(List<Student> student);

	@Mapping(source = "id", target = "attendRecordId")
    @Mapping(target="studentStatus", ignore=true)
    StudentDashboardResponseDTO map(AttendRecord attendRecord);

    @Mapping(source = "id", target = "studentId")
    @Mapping(target="studentStatus", ignore=true)
    AttendanceReportDTO maptoAttendancereportdto(Student student);
}