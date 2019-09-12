package com.mordor.exception;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {
    private String title;
    private HttpStatus status;
    private String detail;
    private Timestamp timeStamp;
    private String message;
    private List<String> errors;
    
    public ApiError(HttpStatus status, String message, Timestamp timeStamp, List<String> errors) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.errors = errors;
    } 
}