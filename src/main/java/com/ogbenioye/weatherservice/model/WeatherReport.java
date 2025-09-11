package com.ogbenioye.weatherservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "weatherReports")
@Getter @Setter
public class WeatherReport {
    @Id @UuidGenerator
    private String id;
    private String latitude;
    private String longitude;
    private String region;
    private String timezone;
    private List<Daily> daily;
}