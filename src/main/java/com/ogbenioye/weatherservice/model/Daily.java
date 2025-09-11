package com.ogbenioye.weatherservice.model;

import java.time.LocalDateTime;
import java.util.List;

public class Daily {
    private LocalDateTime date;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private LocalDateTime moonrise;
    private LocalDateTime moonset;
    private String summary;
    private Temperature temp;
    private Double pressure;
    private Double humidity;
    private Double wind_speed;
    private Double wind_deg;
    private Double wind_gust;
    private List<Weather> weather;
    private Long clouds;
    private Double pop;
    private Double rain;
    private Double uvi;
}
