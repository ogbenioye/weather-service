package com.ogbenioye.weatherservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {
    private final WebClient webclient = WebClient.create("https://api.openweathermap.org/data/3.0");
    //crawl api and save to db
    public void  getDailyReport() {
        var response = webclient.get().uri("/onecall?lat={lat}&lon={lon}&appid={apiKey}" );
    }

    //get report with filters
}
