package com.ogbenioye.weatherservice.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Weather {
    private Long id;
    private String main;
    private String description;
    private String icon;
}
