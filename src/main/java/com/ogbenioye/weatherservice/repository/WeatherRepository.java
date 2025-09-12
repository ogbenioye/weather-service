package com.ogbenioye.weatherservice.repository;

import com.ogbenioye.weatherservice.model.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherReport,String> {
    //get all weather by region or get all regions return daily,
    List<WeatherReport> findAllByRegionIgnoreCase(@Param("region") String region);
    Optional<WeatherReport> findByRegion(String region);
}
