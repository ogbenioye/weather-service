package com.ogbenioye.weatherservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "regions")
@NoArgsConstructor
@Getter @Setter
public class Region {
    @Id @UuidGenerator
    private String id;
    private String region;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt =  LocalDateTime.now();
    private LocalDateTime updatedAt =  LocalDateTime.now();

        public Region(String region, Double latitude, Double longitude) {
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
