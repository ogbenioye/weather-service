package com.ogbenioye.weatherservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
