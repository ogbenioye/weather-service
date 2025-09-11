package com.ogbenioye.weatherservice.dto;

import com.ogbenioye.weatherservice.model.ApiKey;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class LoginResponse {
    public LoginResponse(String email, String firstName, String lastName, List<ApiKey> apiKeys) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.apiKeys = apiKeys;
    }

    private String email;
    private String firstName;
    private String lastName;
    private List<ApiKey> apiKeys = new ArrayList<>();
}
