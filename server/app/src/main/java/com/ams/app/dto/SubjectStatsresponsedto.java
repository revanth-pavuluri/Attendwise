package com.ams.app.dto;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectStatsResponseDTO {
    private Date date;
    private String status;
    private Timestamp time;
}