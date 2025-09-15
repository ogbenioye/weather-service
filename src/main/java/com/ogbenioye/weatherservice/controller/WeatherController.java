package com.ogbenioye.weatherservice.controller;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.dto.WeatherFilterDto;
import com.ogbenioye.weatherservice.dto.WeatherResponse;
import com.ogbenioye.weatherservice.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/report")
    public ApiResponse<List<WeatherResponse>> getWeatherReport(@RequestBody WeatherFilterDto filter) {
        return weatherService.getWeatherReport(filter);
    }
}
