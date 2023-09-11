package com.ams.app.dto;
import lombok.Data;

@Data
public class SearchrequestDTO{
    String column;
    String value;
    Operations operation;

    public enum Operations{
        EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, STARTS, ENDS, ASC, DESC;
    }

}