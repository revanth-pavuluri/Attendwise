package com.ams.app.dto;

import lombok.Data;

@Data
public class Statsresponsedto {
    private String subjectname;
    private float percentage;
    private int subid;
    
    public Statsresponsedto(String subjectname, float percentage, int subid) {
        this.subjectname = subjectname;
        this.percentage = percentage;
        this.subid = subid;
    }
}
