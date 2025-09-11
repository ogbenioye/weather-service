package com.ogbenioye.weatherservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class ApiResponse<T> {

    public ApiResponse(boolean success, String message, HttpStatus statusCode, T data) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    private boolean success;
    private String message;
    private HttpStatus statusCode;
    private T data;
}
