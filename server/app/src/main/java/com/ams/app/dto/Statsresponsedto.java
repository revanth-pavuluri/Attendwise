package com.ams.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsresponseDTO {
    private String subjectName;
    private float percentage;
    private int subjectId;
}
