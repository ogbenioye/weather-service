package com.ogbenioye.weatherservice.dto;

import com.ogbenioye.weatherservice.model.Daily;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WeatherResponse {
    private String region;
    private List<Daily> dailyReport;

    public WeatherResponse(String region, List<Daily> dailyReports) {
        this.region = region;
        this.dailyReport = dailyReports;
    }
}
