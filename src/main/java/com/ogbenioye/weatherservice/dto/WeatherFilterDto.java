package com.ogbenioye.weatherservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class WeatherFilterDto {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String regionName;
}
