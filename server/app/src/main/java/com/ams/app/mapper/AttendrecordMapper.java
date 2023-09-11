package com.ams.app.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.ams.app.dto.AttendrecordDTO;
import com.ams.app.model.AttendRecord;

@Component
@Mapper(componentModel = "spring")
public interface AttendrecordMapper {	

    AttendrecordDTO map(AttendRecord Attendrecord);
    
    AttendRecord map(AttendrecordDTO Attendrecorddto);

	List<AttendrecordDTO> map(List<AttendRecord> Attendrecord);
}