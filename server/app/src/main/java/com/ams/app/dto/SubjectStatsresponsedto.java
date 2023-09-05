package com.ams.app.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SubjectStatsresponsedto {
    private String date;
    private String status;
    private Timestamp time;
    
    public SubjectStatsresponsedto(String date, String status, Timestamp time) {
        this.date = date;
        this.status = status;
        this.time = time;
    }
}