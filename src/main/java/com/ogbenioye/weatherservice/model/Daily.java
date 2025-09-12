package com.ogbenioye.weatherservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Table(name = "weather_daily")
@Getter @Setter
@NoArgsConstructor
public class Daily {
    @Id @UuidGenerator
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private WeatherReport weatherReport;
    @JsonProperty("dt")
    private Long date;
    private Long sunrise;
    private Long sunset;
    private Long moonrise;
    private Long moonset;
    private String summary;
    @Embedded
    private Temperature temp;
    private Double pressure;
    private Double humidity;
    private Double wind_speed;
    private Double wind_deg;
    private Double wind_gust;
    @ElementCollection
    private List<Weather> weather;
    private Long clouds;
    private Double pop;
    private Double rain;
    private Double uvi;

    @Transient
    public LocalDateTime getDate() {
        return Instant.ofEpochSecond(date)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
