package com.ams.app.dto;
import lombok.Data;
import java.util.List;

@Data
public class RequestDTO {
    private List<SearchrequestDTO> search;
    private Operators operator;
    public enum Operators{
        AND, OR;
    }
}