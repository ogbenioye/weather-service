package com.ogbenioye.weatherservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

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
    @OneToMany(mappedBy = "weatherReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> daily;
}