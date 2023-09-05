package com.ams.app.dto;
import lombok.Data;
import java.util.List;

@Data
public class Requestdto {
    private List<Searchrequestdto> search;
    private Operators operator;
    public enum Operators{
        AND, OR;
    }
}